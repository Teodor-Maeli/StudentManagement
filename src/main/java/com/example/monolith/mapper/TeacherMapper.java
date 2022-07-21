package com.example.monolith.mapper;

import com.example.monolith.dto.teacherDto.TeacherRequest;
import com.example.monolith.dto.teacherDto.TeacherResponse;
import com.example.monolith.entity.Teacher;

public interface TeacherMapper<T,T1> {

    public TeacherResponse teacherEntityToTeacherResponse(T type);

    public Teacher teacherRequestToTeacherEntity(T1 type);

}
