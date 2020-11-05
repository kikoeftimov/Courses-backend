package com.example.courses.backend.demo.service;

import com.example.courses.backend.demo.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(Long id);

    Category update(Long id, Category category);

    Category save(Category category);

    void deleteById(Long id);
}
