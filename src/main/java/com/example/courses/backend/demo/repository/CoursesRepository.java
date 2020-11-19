package com.example.courses.backend.demo.repository;

import com.example.courses.backend.demo.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.name like :term or c.description like :term")
    List<Course> searchCourses(@Param("term")String term);

    @Query("select c from Course c ORDER BY c.name ASC")
    Page<Course> findPageable(Pageable pageable);
}
