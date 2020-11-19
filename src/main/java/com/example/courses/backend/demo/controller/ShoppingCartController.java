package com.example.courses.backend.demo.controller;

import com.example.courses.backend.demo.model.Dto.ChargeRequest;
import com.example.courses.backend.demo.model.ShoppingCart;
import com.example.courses.backend.demo.security.services.UserDetailsImpl;
import com.example.courses.backend.demo.service.ShoppingCartService;
import com.stripe.exception.StripeException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping
    public ShoppingCart createCart(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        return this.shoppingCartService.createCart(username);
    }

    @PatchMapping("/{courseId}")
    public ShoppingCart addCourseToCart(@PathVariable Long courseId, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println(userPrincipal);
        String username = userPrincipal.getUsername();
        try {
            return this.shoppingCartService.addCourseToCart(username, courseId);
        }
        catch (RuntimeException e){
            e.getLocalizedMessage();
        }
        return null;
    }

    @DeleteMapping("/{courseId}")
    public ShoppingCart deleteCourseFromCart(@PathVariable Long courseId, Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        try {
            return this.shoppingCartService.removeCourseFromCart(username,courseId);
        }
        catch (RuntimeException e){
            e.getLocalizedMessage();
        }
        return null;
    }

    @PatchMapping("/cancel")
    public ShoppingCart cancelCart(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        return this.shoppingCartService.cancelCart(username);
    }


    @PatchMapping("/clear")
    public ShoppingCart clearCart(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        return this.shoppingCartService.clearCart(username);
    }

    @GetMapping("/charge")
    public ShoppingCart getCheckoutPage(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        return this.shoppingCartService.getActiveShoppingCart(username);
    }

    @PostMapping("/charge")
    public ShoppingCart checkoutCart(Authentication authentication,ChargeRequest chargeRequest) throws StripeException {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        try {
            return this.shoppingCartService.checkoutCart(username, chargeRequest);
        }catch(RuntimeException e){
            e.getLocalizedMessage();
        }
        return null;
    }

}
