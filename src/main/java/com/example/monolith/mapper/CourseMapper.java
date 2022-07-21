package com.example.monolith.mapper;

import com.example.monolith.dto.courseDto.CourseRequest;
import com.example.monolith.dto.courseDto.CourseResponse;
import com.example.monolith.entity.Course;

import java.util.List;

public interface CourseMapper<T,T1> {

    public CourseResponse courseEntityToCourseResponse(T type);


    public List<CourseResponse> allEntityToAllResponse(List<T> type);

    public Course courseRequestToCourseEntity(T1 type);


}
