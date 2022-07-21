package com.example.monolith.mapper.Impl;

import com.example.monolith.dto.studentDto.StudentRequest;
import com.example.monolith.dto.studentDto.StudentResponse;
import com.example.monolith.entity.Student;
import com.example.monolith.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;

import static com.example.monolith.utility.enums.Authorities.STUDENT;

@Component
@AllArgsConstructor
public class StudentMapperImpl extends BaseMapper implements StudentMapper<Student,StudentRequest> {




    @Override
    public StudentResponse studentEntityToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .age(student.getAge())
                .enrollments(student.getEnrollments())
                .name(student.getName())
                .build();
    }

    @Override
    public List<StudentResponse> AllEntityToAllResponse(List<Student> students) {
        List<StudentResponse> response = students.stream().map(this::studentEntityToStudentResponse).toList();
        return response;
    }

    @Override
    public Student
    studentRequestToStudentEntity(StudentRequest student) {
            return Student.builder()
                    .age(student.getAge())
                    .enrollments(student.getEnrollments())
                    .name(student.getName())
                    .userName(student.getUsername())
                    .password(passwordEncoder().encode(student.getPassword()))
                    .roles(STUDENT.name())
                    .active(true)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build();
    }





}
