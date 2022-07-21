package com.example.monolith.repository;

import com.example.monolith.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);
}
