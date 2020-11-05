package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.Dto.LoginDto;
import com.example.courses.backend.demo.model.Role;
import com.example.courses.backend.demo.model.User;
import com.example.courses.backend.demo.model.exceptions.PasswordDoesntMatchException;
import com.example.courses.backend.demo.model.exceptions.UserNotFoundException;
import com.example.courses.backend.demo.repository.RoleRepository;
import com.example.courses.backend.demo.repository.UserRepository;
import com.example.courses.backend.demo.service.AuthService;
import com.example.courses.backend.demo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @Override
    public User findUser() {
//        return this.userRepository.findById("current-user").
//                orElseGet(() -> {
//                    User user = new User();
//                    user.setUsername("current-user");
//                    return this.userRepository.save(user);
//                });

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getCurrentUsername() {
        return this.findUser().getUsername();
    }

    @Override
    public LoginDto login(String username, String password) {
        User user = this.userService.findById(username);
        if(user != null){
            if(user.getPassword().equals(passwordEncoder.encode(password))){
                LoginDto dto = new LoginDto();
                Role userRole = this.roleRepository.findByName("ROLE_USER");
                dto.setHesh("sdsdds");
                dto.setRole(userRole);
                return dto;
            }else{
                throw new PasswordDoesntMatchException();
            }
        }else {
            throw new UserNotFoundException(username);
        }

    }

    @Override
    public User signUpUser(String username, String password, String repeatedPassword) {
        if(!password.equals(repeatedPassword)){
            throw new PasswordDoesntMatchException();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role userRole = this.roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return this.userService.registerUser(user);
    }

    @PostConstruct
    public void init(){
        if(!this.userRepository.existsById("admin")){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(this.roleRepository.findAll());
            this.userRepository.save(admin);
        }
    }
}
