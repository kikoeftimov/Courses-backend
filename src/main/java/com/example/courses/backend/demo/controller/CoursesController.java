package com.example.courses.backend.demo.controller;

import com.example.courses.backend.demo.model.Course;
import com.example.courses.backend.demo.service.CoursesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesService coursesService;

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping
    public List<Course> findAll(){
        return this.coursesService.findAll();
    }

    @PostMapping
    public Course save(@RequestBody Course course, @RequestParam(required = false) MultipartFile image) throws IOException{
        return this.coursesService.save(course, image);
    }

    @GetMapping("/{id}")
    public Course findById(@PathVariable Long id){
        return this.coursesService.findById(id);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course, @RequestParam(required = false) MultipartFile image) throws IOException{
        return this.coursesService.update(id,course,image);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        this.coursesService.deleteById(id);
    }

    @GetMapping(params = "term")
    public List<Course> searchCourses(@RequestParam String term){
        return coursesService.searchCourses(term);
    }
}
