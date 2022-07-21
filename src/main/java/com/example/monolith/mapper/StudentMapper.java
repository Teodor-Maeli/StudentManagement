package com.example.monolith.mapper;

import com.example.monolith.dto.studentDto.StudentRequest;
import com.example.monolith.dto.studentDto.StudentResponse;
import com.example.monolith.entity.Student;

import java.util.List;

public interface StudentMapper<T,T1> {

    public StudentResponse studentEntityToStudentResponse(T type);

    public List<StudentResponse> AllEntityToAllResponse(List<T> type);

    public Student studentRequestToStudentEntity(T1 Type);

}
