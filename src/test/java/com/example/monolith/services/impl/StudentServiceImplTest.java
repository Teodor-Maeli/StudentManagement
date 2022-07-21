package com.example.monolith.services.impl;

import com.example.monolith.dto.studentDto.StudentRequest;
import com.example.monolith.entity.Student;
import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.mapper.Impl.StudentMapperImpl;
import com.example.monolith.repository.CourseRepository;
import com.example.monolith.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    StudentRepository studentRepository;
    CourseRepository courseRepository;

    StudentMapperImpl studentMapper = new StudentMapperImpl();
    StudentServiceImpl studentService;
    Student student;
    StudentRequest request;

    @BeforeEach
    void setup() {
        studentRepository = mock(StudentRepository.class);
        courseRepository = mock(CourseRepository.class);
        studentService = new StudentServiceImpl(studentRepository, courseRepository, studentMapper);

        request = StudentRequest.builder()
                .name("Ivan")
                .password("pass")
                .age(20)
                .build();

        student = Student.builder()
                .name("Ivan")
                .age(20)
                .id(1L)
                .build();

    }


    @Test
    void get() {
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
        assertAll(
                () -> assertEquals(studentService.get(1L).getName(), student.getName()),
                () -> assertTrue(studentService.get(1L).getName().equals(student.getName())),
                () -> assertNotNull(studentService.get(1L)));
    }

    @Test
    void getAll() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        assertAll(
                () -> assertNotNull(studentService.getAll()),
                () -> assertEquals(studentService.getAll().size(), 1),
                () -> assertSame(studentService.getAll().get(0).getId(), 1L));
    }


    @Test
    void deleteThrows() {
        when(studentRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ObjectNotFoundException.class,()->studentService.delete(1L));
    }

    @Test
    void save() throws ObjectAlreadyExistException {
        when(studentRepository.existsByAgeAndName(anyInt(),anyString())).thenReturn(false);
        assertAll(()->assertEquals(studentService.save(request).getName(),student.getName()),
                ()->assertEquals(studentService.save(request).getAge(),20),
                ()->assertNotNull(studentService.save(request).getName()),
                ()->assertNotEquals(studentService.save(request).getName(),"Mocking"));

    }

    @Test
    void saveThrows(){
        when(studentRepository.existsByAgeAndName(anyInt(),anyString())).thenReturn(true);
        assertThrows(ObjectAlreadyExistException.class,()->studentService.save(request));
    }



}