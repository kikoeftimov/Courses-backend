package com.example.courses.backend.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class ShoppingCartIsNotActiveException extends RuntimeException{
    public ShoppingCartIsNotActiveException(Long id) {
        super(String.format("Shopping cart for user %d is not active", id));
    }
}
