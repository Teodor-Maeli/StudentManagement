package com.example.monolith.mapper.Impl;

import com.example.monolith.dto.enrollmentDto.EnrollmentResponse;
import com.example.monolith.entity.Enrollment;
import com.example.monolith.mapper.EnrollmentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentMapperImpl implements EnrollmentMapper<Enrollment> {

    @Override
    public EnrollmentResponse enrollmentEntityToEnrollmentResponse(Enrollment enrollment) {

        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .course(enrollment.getCourse())
                .grades(enrollment.getGrades())
                .students(enrollment.getStudent())
                .build();
    }

    @Override
    public List<EnrollmentResponse> AllEnrollmentsToAllResponse(List<Enrollment> enrollments) {
        List<EnrollmentResponse> responses = enrollments.stream().map(this::enrollmentEntityToEnrollmentResponse).toList();
        return responses;
    }





}
