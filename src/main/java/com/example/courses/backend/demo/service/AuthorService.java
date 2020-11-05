package com.example.courses.backend.demo.service;

import com.example.courses.backend.demo.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findById(Long id);

    Author save(Author author);

    Author update(Long id, Author author);

    void deleteById(Long id);
}
