package com.example.monolith.services.impl;

import com.example.monolith.dto.courseDto.CourseRequest;
import com.example.monolith.entity.Course;
import com.example.monolith.entity.Teacher;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.mapper.Impl.CourseMapperImpl;
import com.example.monolith.repository.CourseRepository;
import com.example.monolith.repository.TeacherRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseServiceImplTest {
    private CourseRepository courseRepository;
    private TeacherRepository teacherRepository;
    private CourseServiceImpl courseService;
    private CourseMapperImpl courseMapper = new CourseMapperImpl();
    private Course course;
    private Teacher teacher;
    private CourseRequest request;

    @BeforeEach
    public void setup() {
        courseRepository = mock(CourseRepository.class);
        teacherRepository = mock(TeacherRepository.class);
        courseService = new CourseServiceImpl(courseRepository, teacherRepository, courseMapper);
        course = Course.builder()
                .duration(30)
                .name("Tester")
                .id(1L).build();

        teacher = Teacher
                .builder()
                .name("Tester")
                .id(1L)
                .build();

        request = CourseRequest.builder()
                .duration(30)
                .name("Tester")
                .build();

    }

    @Test
    void get() throws ObjectNotFoundException {
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(course));
        when(courseRepository.existsById(any())).thenReturn(true);
        assertAll(
                () -> assertEquals(courseService.get(1L).getName(), course.getName()),
                () -> assertTrue(courseService.get(1L).getName().equals(course.getName())),
                () -> assertNotNull(courseService.get(1L)));
    }

    @Test
    void getThrows() {
        when(courseRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> courseService.get(1L));

    }

    @Test
    void getAll() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        assertAll(
                () -> assertNotNull(courseService.getAll()),
                () -> assertEquals(courseService.getAll().size(), 1),
                () -> assertSame(courseService.getAll().get(0).getId(), 1L));

    }


    @Test
    void deleteThrows() {
        when(courseRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> courseService.delete(1L));
    }


    @Test
    void assignTeacher() throws ObjectNotFoundException {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        assertEquals(courseService.assignTeacher(1L,1L).getTeacher(),teacher);
    }

    @Test
    void save(){
        when(courseRepository.existsByName(anyString())).thenReturn(false);
        assertAll(()->assertEquals(courseService.save(request).getName(),course.getName()),
                ()->assertEquals(courseService.save(request).getDuration(),30),
                ()->assertNotNull(courseService.save(request).getName()),
                ()->assertNotEquals(courseService.save(request).getName(),"Mocking"));

    }
}