package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.Category;
import com.example.courses.backend.demo.model.exceptions.CategoryNotFoundException;
import com.example.courses.backend.demo.repository.CategoryRepository;
import com.example.courses.backend.demo.service.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category update(Long id, Category category) {
        Category c = this.findById(id);
        c.setName(category.getName());
        return this.categoryRepository.save(c);
    }

    @Override
    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }
}
