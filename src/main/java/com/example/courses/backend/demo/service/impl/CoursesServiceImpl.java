package com.example.courses.backend.demo.service.impl;

import com.example.courses.backend.demo.model.Author;
import com.example.courses.backend.demo.model.Category;
import com.example.courses.backend.demo.model.Course;
import com.example.courses.backend.demo.model.exceptions.CourseAlreadyInCartException;
import com.example.courses.backend.demo.model.exceptions.CourseNotFoundException;
import com.example.courses.backend.demo.repository.CoursesRepository;
import com.example.courses.backend.demo.service.AuthorService;
import com.example.courses.backend.demo.service.CategoryService;
import com.example.courses.backend.demo.service.CoursesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository coursesRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public CoursesServiceImpl(CoursesRepository coursesRepository, CategoryService categoryService, AuthorService authorService) {
        this.coursesRepository = coursesRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }

    @Override
    public List<Course> findAll() {
        return this.coursesRepository.findAll();
    }

    @Override
    public Page<Course> findPageable(Pageable pageable) {
        return this.coursesRepository.findPageable(pageable);
    }

    @Override
    public Course findById(Long id) {
        return this.coursesRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course save(Course course, MultipartFile image) throws IOException {
        Category category = this.categoryService.findById(course.getCategory().getId());
        Author author = this.authorService.findById(course.getAuthor().getId());
        course.setCategory(category);
        course.setAuthor(author);

        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            course.setImageBase64(base64Image);
        }

        return this.coursesRepository.save(course);
    }

    @Override
    public Course update(Long id, Course course, MultipartFile image) throws IOException {
        Course c = this.findById(id);

        Category category = this.categoryService.findById(course.getCategory().getId());
        Author author = this.authorService.findById(course.getAuthor().getId());

        c.setName(course.getName());
        c.setDescription(course.getDescription());
        c.setPrice(course.getPrice());
        c.setCategory(category);
        c.setAuthor(author);

        if (image != null && !image.getName().isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64Image = String.format("data:%s;base64,%s", image.getContentType(), Base64.getEncoder().encodeToString(bytes));
            c.setImageBase64(base64Image);
        }
        return this.coursesRepository.save(c);
    }


    @Override
    public void deleteById(Long id) {
        Course course = this.findById(id);
        if(course.getShoppingCarts().size() > 0){
            throw new CourseAlreadyInCartException(course.getName());
        }
        this.coursesRepository.deleteById(id);
    }

    @Override
    public List<Course> searchCourses(String term) {
        return this.coursesRepository.searchCourses(term);
    }
}
