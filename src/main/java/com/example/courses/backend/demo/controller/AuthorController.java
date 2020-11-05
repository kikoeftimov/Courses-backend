package com.example.courses.backend.demo.controller;

import com.example.courses.backend.demo.model.Author;
import com.example.courses.backend.demo.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> findAll(){
        return this.authorService.findAll();
    }

    @PostMapping
    public Author save(@RequestBody Author author){
        return this.authorService.save(author);
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable Long id){
        return this.authorService.findById(id);
    }

    @PutMapping("/{id}")
    public Author update(@PathVariable Long id, Author author){
        return this.authorService.update(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        this.authorService.deleteById(id);
    }
}
