package com.example.courses.backend.demo.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
public class CourseAlreadyInCartException extends RuntimeException{
    public CourseAlreadyInCartException(Long id) {
        super(String.format("Course %d already in shopping cart!", id));
    }
}
