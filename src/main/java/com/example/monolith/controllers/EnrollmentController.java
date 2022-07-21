package com.example.monolith.controllers;


import com.example.monolith.dto.enrollmentDto.EnrollmentResponse;
import com.example.monolith.services.impl.EnrollmentServiceImpl;
import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.utility.Exceptions.StudentNotAssignedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/enrollments")
@AllArgsConstructor
public class EnrollmentController {


    EnrollmentServiceImpl enrollmentService;


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/{id}")
    public List<EnrollmentResponse> getEnrollment(@PathVariable Long id) {
            return enrollmentService.getAllByStudent(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping
    public List<EnrollmentResponse> getAll() {
        return enrollmentService.getAll();
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @DeleteMapping(value = "/{cId}/{sId}")
    public EnrollmentResponse delete(@PathVariable Long cId, @PathVariable Long sId) {
            return enrollmentService.delete(cId, sId);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PostMapping(value = "/{cId}/{sId}")
    public EnrollmentResponse enroll(@PathVariable Long sId, @PathVariable Long cId) {
            return enrollmentService.enroll(sId, cId);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/{cId}/{sId}")
    public EnrollmentResponse getByCourseAndStudent(@PathVariable Long cId, @PathVariable Long sId) {
            return enrollmentService.getByCourseAndStudent(cId, sId);
    }



    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @PatchMapping(value = "/add/{cId}/{sId}/{grade}")
    public EnrollmentResponse addGrade(@PathVariable Long cId, @PathVariable Long sId, @PathVariable double grade) {
        return enrollmentService.addGrade(cId, sId, grade);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/average/{sId}")
    public double getStudentTotalAvg(@PathVariable Long sId) {
        return enrollmentService.getStudentTotalAvg(sId);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/cavg/{cId}")
    public double getCourseAverage(@PathVariable Long cId) {
        return enrollmentService.getCourseTotalAvg(cId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/all")
    public List<EnrollmentResponse> showByCourseAndTeachers() {
        return enrollmentService.showAllStudentsAndTeachers();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    @GetMapping(value = "/sorted")
    public TreeMap<String, TreeMap<String, Double>> sorted() {
        return enrollmentService.showAllGroupedByCourseAndAvg();
    }

}
