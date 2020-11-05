package com.example.courses.backend.demo.controller;

import com.example.courses.backend.demo.model.Category;
import com.example.courses.backend.demo.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll(){
        return this.categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id){
        return this.categoryService.findById(id);
    }

    @PostMapping
    public Category saveCategory(@RequestBody Category category){
        return this.categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category,@PathVariable Long id){
        return this.categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        this.categoryService.deleteById(id);
    }
}
