//package com.example.courses.backend.demo.controller;
//
//import com.example.courses.backend.demo.model.Dto.LoginDto;
//import com.example.courses.backend.demo.model.User;
//import com.example.courses.backend.demo.service.AuthService;
//import com.example.courses.backend.demo.service.UserService;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("/rest")
//public class UserController {
//
//    private final AuthService authService;
//    private final UserService userService;
//
//    public UserController(AuthService authService, UserService userService) {
//        this.authService = authService;
//        this.userService = userService;
//    }
//
//    @PostMapping("/signup")
//    public void signUpUser(@RequestParam String username,
//                             @RequestParam String password,
//                             @RequestParam String repeatedPassword){
//        try {
//            this.authService.signUpUser(username, password, repeatedPassword);
//        }catch (RuntimeException ex){
//            System.out.println(ex.getLocalizedMessage());
//        }
//    }
//    @PostMapping("/login")
//    public UserDetails login(@RequestParam String username){
//        return this.userService.loadUserByUsername(username);
//    }
//}
