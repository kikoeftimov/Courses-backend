package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.Course;
import com.example.courses.backend.demo.model.Dto.ChargeRequest;
import com.example.courses.backend.demo.model.Enum.CartStatus;
import com.example.courses.backend.demo.model.ShoppingCart;
import com.example.courses.backend.demo.model.Transaction;
import com.example.courses.backend.demo.model.User;
import com.example.courses.backend.demo.model.exceptions.*;
import com.example.courses.backend.demo.repository.ShoppingCartRepository;
import com.example.courses.backend.demo.repository.TransactionsRepository;
import com.example.courses.backend.demo.repository.UserRepository;
import com.example.courses.backend.demo.service.CoursesService;
import com.example.courses.backend.demo.service.PaymentService;
import com.example.courses.backend.demo.service.ShoppingCartService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CoursesService coursesService;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final TransactionsRepository transactionsRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, CoursesService coursesService, UserRepository userRepository, PaymentService paymentService, TransactionsRepository transactionsRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.coursesService = coursesService;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public ShoppingCart createCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        if (this.shoppingCartRepository.existsByUserUsernameAndStatus(user.getUsername(), CartStatus.CREATED)) {
            throw new ShoppingCartAlreadyCreated(username);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findActiveShoppingCartByUsername(String username) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(username, CartStatus.CREATED).
                orElseThrow(() -> new ShoppingCartIsNotActive(username));
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        return this.shoppingCartRepository.findByUserUsernameAndStatus(username, CartStatus.CREATED).
                orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    User user = this.userRepository.findByUsername(username)
                            .orElseThrow(() -> new UserNotFoundException(username));
                    shoppingCart.setUser(user);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }


    @Override
    public ShoppingCart addCourseToCart(String username, Long courseId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Course course = this.coursesService.findById(courseId);
        for (Course c1: shoppingCart.getCourses()) {
            if(c1.getId().equals(courseId)){
                throw new CourseAlreadyInCartException(courseId);
            }
        }
        shoppingCart.getCourses().add(course);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeCourseFromCart(String username, Long courseId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        shoppingCart.setCourses(shoppingCart.getCourses()
                .stream()
                .filter(course -> !course.getId().equals(courseId))
                .collect(Collectors.toList()));
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCart cancelCart(String username) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(username, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActive(username));
        shoppingCart.setFinished(LocalDateTime.now());
        shoppingCart.setStatus(CartStatus.CANCELED);
        return this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart checkoutCart(String username, ChargeRequest chargeRequest) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(username, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActive(username));

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));;

        List<Course> courses = shoppingCart.getCourses();
        float price=0;

        for(Course c : courses){
            price += c.getPrice();
        }

        String charge = null;

        try {
            charge = this.paymentService.charge(chargeRequest);
        }catch (StripeException e) {
            throw new TransactionFailedException(username, e.getMessage());
        }
        finally {
            Transaction transaction = new Transaction();
            transaction.setShoppingCart(shoppingCart);
            transaction.setAmount((int) price);
            transaction.setUser(user);
            List<String> helper = new ArrayList<>();
            for (Course c : shoppingCart.getCourses() ) {
                helper.add(c.getName());
                System.out.println(transaction.getNames());
            }

            transaction.setNames(helper);
            LocalDateTime timeNow = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = timeNow.format(formatter);
            transaction.setLocalDateTime(formattedDate);
            shoppingCart.setCourses(courses);

            transaction.setEmail(user.getEmail());

            shoppingCart.setFinished(LocalDateTime.now());
            shoppingCart.setStatus(CartStatus.FINISHED);
            this.transactionsRepository.save(transaction);
        }
        return this.shoppingCartRepository.save(shoppingCart);

    }

    @Override
    public ShoppingCart clearCart(String username) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserUsernameAndStatus(username, CartStatus.CREATED)
                .orElseThrow(() -> new ShoppingCartIsNotActive(username));

        shoppingCart.setCourses(new ArrayList<>());
        return this.shoppingCartRepository.save(shoppingCart);
    }

//    @Override
//    public ShoppingCart createCart(Long id) {
//        User user = this.userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//        if(this.shoppingCartRepository.existsByUserIdAndStatus(user.getId(), CartStatus.CREATED)){
//            throw new ShoppingCartAlreadyCreated(id);
//        }
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.setUser(user);
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
//
//    @Override
//    public ShoppingCart findActiveShoppingCartByUserId(Long id) {
//        return this.shoppingCartRepository.findByUserIdAndStatus(id, CartStatus.CREATED)
//                .orElseThrow(() -> new ShoppingCartIsNotActiveException(id));
//    }
//
//    @Override
//    public ShoppingCart getActiveShoppingCart(Long id) {
//        return this.shoppingCartRepository.findByUserIdAndStatus(id, CartStatus.CREATED)
//                .orElseGet(() -> {
//                    ShoppingCart shoppingCart = new ShoppingCart();
//                    User user = this.userRepository.findById(id)
//                            .orElseThrow(() -> new UserNotFoundException(id));
//                    shoppingCart.setUser(user);
//                    return this.shoppingCartRepository.save(shoppingCart);
//                });
//    }
//
//
//    @Override
//    public ShoppingCart addCourseToCart(Long id, Long courseId) {
//        ShoppingCart shoppingCart = this.getActiveShoppingCart(id);
//        Course course = this.coursesService.findById(courseId);
//        for(Course c : shoppingCart.getCourses()){
//            if(c.getId().equals(courseId)){
//                throw new CourseAlreadyInCartException(course.getName());
//            }
//        }
//        shoppingCart.getCourses().add(course);
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
//
//    @Override
//    public ShoppingCart removeCourseFromCart(Long id, Long courseId) {
//        ShoppingCart shoppingCart = this.getActiveShoppingCart(id);
//        shoppingCart.setCourses(shoppingCart.getCourses()
//                                .stream()
//                                .filter(course -> !course.getId().equals(courseId))
//                                .collect(Collectors.toList()));
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
//
//    @Override
//    public ShoppingCart cancelCart(Long id) {
//        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserIdAndStatus(id, CartStatus.CREATED)
//                .orElseThrow(() -> new ShoppingCartIsNotActive(id));
//        shoppingCart.setFinished(LocalDateTime.now());
//        shoppingCart.setStatus(CartStatus.CANCELED);
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
//
//    @Override
//    public ShoppingCart checkoutCart(Long id) {
//        return null;
//    }
//
//    @Override
//    public ShoppingCart clearCart(Long id) {
//        ShoppingCart shoppingCart = this.shoppingCartRepository.findByUserIdAndStatus(id, CartStatus.CREATED)
//                .orElseThrow(() -> new ShoppingCartIsNotActive(id));
//
//        shoppingCart.setCourses(new ArrayList<>());
//        return this.shoppingCartRepository.save(shoppingCart);
//    }
}
