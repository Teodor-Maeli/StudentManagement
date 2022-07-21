package com.example.monolith.controllers;

import com.example.monolith.services.impl.UserServiceImpl;
import com.example.monolith.dto.studentDto.StudentRequest;
import com.example.monolith.dto.teacherDto.TeacherRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class basicPagesController {

    UserServiceImpl userService;

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/index")
    public String index() {
        return "index.html";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @RequestMapping(value = "/register/students")
    public String signupStudent(@ModelAttribute StudentRequest createStudentAccount) {
        if (createStudentAccount.getAge() != null && createStudentAccount.getPassword() != null) {
            userService.createStudentAccount(createStudentAccount);
        }
        return "/students/studentsReg.html";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/register/teachers")
    public String signupTeacher(@ModelAttribute TeacherRequest createTeacherAccount) {
        if (createTeacherAccount.getPassword() != null && createTeacherAccount.getAge() != null) {
            userService.createTeacherAccount(createTeacherAccount);
        }
        return "/teachers/teachersReg.html";
    }


}
