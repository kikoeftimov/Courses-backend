package com.example.courses.backend.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class PasswordDoesntMatchException extends RuntimeException {
    public PasswordDoesntMatchException(){
        super("Password doesn't match!!");
    }
}
