package com.example.monolith.mapper.Impl;

import com.example.monolith.dto.courseDto.CourseRequest;
import com.example.monolith.dto.courseDto.CourseResponse;
import com.example.monolith.entity.Course;
import com.example.monolith.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseMapperImpl implements CourseMapper<Course,CourseRequest> {

    @Override
    public CourseResponse courseEntityToCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .teacher(course.getTeacher())
                .duration(course.getDuration())
                .build();
    }


    @Override
    public List<CourseResponse> allEntityToAllResponse(List<Course> courses) {
        List<CourseResponse> responses = courses.stream().map(this::courseEntityToCourseResponse).toList();
        return responses;
    }

    @Override
    public Course courseRequestToCourseEntity(CourseRequest course) {
        return Course.builder()
                .duration(course.getDuration())
                .name(course.getName())
                .teacher(course.getTeacher()).build();
    }


}
