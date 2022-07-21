package com.example.monolith.repository;

import com.example.monolith.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByAgeAndName(int age, String name);
    boolean existsByUserName(String username);

    boolean existsByName(String name);
    Optional<Student> findByUserName(String userName);
}
