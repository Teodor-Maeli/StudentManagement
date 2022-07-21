package com.example.monolith.dto.studentDto;

import com.example.monolith.entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String name;
    private int age;
    private List<Enrollment> enrollments;



}
