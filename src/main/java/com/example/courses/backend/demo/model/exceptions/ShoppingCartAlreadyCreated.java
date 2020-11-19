package com.example.courses.backend.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class ShoppingCartAlreadyCreated extends RuntimeException{
    public ShoppingCartAlreadyCreated(String username) {
        super(String.format("Shopping cart for user %s already created!", username));
    }
}
