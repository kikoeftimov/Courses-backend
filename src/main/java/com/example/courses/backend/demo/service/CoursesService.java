package com.example.courses.backend.demo.service;

import com.example.courses.backend.demo.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CoursesService {

    List<Course> findAll();

    Page<Course> findPageable(Pageable pageable);

    Course findById(Long id);

    Course save(Course course, MultipartFile image) throws IOException;

    Course update(Long id, Course course, MultipartFile image) throws IOException;

    void deleteById(Long id);

    List<Course> searchCourses(String term);
}
