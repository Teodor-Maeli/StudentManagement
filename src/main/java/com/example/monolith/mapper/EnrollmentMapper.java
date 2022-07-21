package com.example.monolith.mapper;

import com.example.monolith.dto.enrollmentDto.EnrollmentResponse;
import com.example.monolith.entity.Enrollment;

import java.util.List;

public interface EnrollmentMapper<T> {

    public EnrollmentResponse enrollmentEntityToEnrollmentResponse(T type);

    public List<EnrollmentResponse> AllEnrollmentsToAllResponse(List<T> type);



}
