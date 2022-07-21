package com.example.monolith.controllers;


import com.example.monolith.dto.teacherDto.TeacherRequest;
import com.example.monolith.dto.teacherDto.TeacherResponse;
import com.example.monolith.services.impl.TeacherServiceImpl;
import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/teachers")
@AllArgsConstructor
public class TeacherController {

    TeacherServiceImpl teacherServiceImpl;



    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/{id}")
    public TeacherResponse getTeachers(@PathVariable Long id) {
            return teacherServiceImpl.get(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping
    public List<TeacherResponse> getAllTeachers() {
            return teacherServiceImpl.getAll();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public TeacherResponse deleteTeacher(@PathVariable Long id) {
            return teacherServiceImpl.delete(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PostMapping
    public TeacherResponse save(@RequestBody TeacherRequest teacher) {
        return teacherServiceImpl.save(teacher);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PatchMapping(value = "/{id}/{degree}")
    public TeacherResponse update(@PathVariable Long id, @PathVariable String degree) {
            return teacherServiceImpl.updateDegree(id, degree);
    }


}
