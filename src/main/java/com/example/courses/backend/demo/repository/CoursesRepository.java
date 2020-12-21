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

    @Query("select c from Course c where c.name like :searchText or c.description like :searchText or c.author.firstName like :searchText or c.author.lastName like :searchText or c.category.name like :searchText")
    List<Course> searchCourses(@Param("searchText")String searchText);

    @Query("select c from Course c ORDER BY c.name ASC")
    Page<Course> findPageable(Pageable pageable);
}
