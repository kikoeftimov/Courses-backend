package com.example.courses.backend.demo.controller;

import com.example.courses.backend.demo.model.Course;
import com.example.courses.backend.demo.repository.CoursesRepository;
import com.example.courses.backend.demo.service.CoursesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    private final CoursesService coursesService;
    private final CoursesRepository coursesRepository;

    public CoursesController(CoursesService coursesService, CoursesRepository coursesRepository) {
        this.coursesService = coursesService;
        this.coursesRepository = coursesRepository;
    }

    @GetMapping
    public List<Course> findAll(){
        return this.coursesService.findAll();
    }

//    @GetMapping
//    public Page<Course> findAll(Pageable pageable){
//        return this.coursesRepository.findAll(pageable);
//    }


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

//    @GetMapping(params = "term")
//    public List<Course> searchCourses(@RequestParam String term){
//        return coursesService.searchCourses(term);
//    }

    @GetMapping("/search/{searchText}")
    public List<Course> searchCourses(@PathVariable String searchText){
        return coursesService.searchCourses(searchText);
    }
}
