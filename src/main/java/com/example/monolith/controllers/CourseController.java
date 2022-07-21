package com.example.monolith.controllers;


import com.example.monolith.dto.courseDto.CourseRequest;
import com.example.monolith.dto.courseDto.CourseResponse;
import com.example.monolith.services.impl.CourseServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    CourseServiceImpl courseService;



    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}")
    public CourseResponse getCourse(@PathVariable Long id) {
        return courseService.get(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER','STUDENT')")
    @GetMapping
    public List<CourseResponse> getAll() {
        return courseService.getAll();
    }



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public CourseResponse delete(@PathVariable Long id) {
        return courseService.delete(id);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PostMapping
    public CourseResponse save(@RequestBody CourseRequest course) {
        return courseService.save(course);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PatchMapping(value = "/assign/{cId}/{tId}")
    public CourseResponse assignTeacher(@PathVariable Long cId, @PathVariable Long tId) {
        return courseService.assignTeacher(cId, tId);
    }


}
