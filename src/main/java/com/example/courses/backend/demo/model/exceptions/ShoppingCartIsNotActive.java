package com.example.courses.backend.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ShoppingCartIsNotActive extends RuntimeException{
    public ShoppingCartIsNotActive(String username) {
        super(String.format("Shopping cart for user %s is not active", username));
    }
}
