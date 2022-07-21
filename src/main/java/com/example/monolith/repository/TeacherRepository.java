package com.example.monolith.repository;

import com.example.monolith.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    boolean existsByNameAndAndDegree(String name, String degree);

    boolean existsByUserName(String username);

    boolean existsByName(String name);

    Optional<Teacher> findByUserName(String userName);

}
