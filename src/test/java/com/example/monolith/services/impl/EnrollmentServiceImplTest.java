package com.example.monolith.services.impl;

import com.example.monolith.entity.Course;
import com.example.monolith.entity.Enrollment;
import com.example.monolith.entity.Student;
import com.example.monolith.entity.Teacher;
import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import com.example.monolith.utility.Exceptions.InvalidGradeException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.utility.Exceptions.StudentNotAssignedException;
import com.example.monolith.mapper.Impl.EnrollmentMapperImpl;
import com.example.monolith.repository.CourseRepository;
import com.example.monolith.repository.EnrollmentRepository;
import com.example.monolith.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnrollmentServiceImplTest {

    CourseRepository courseRepository;
    StudentRepository studentRepository;
    EnrollmentRepository enrollmentRepository;
    EnrollmentMapperImpl enrollmentMapper = new EnrollmentMapperImpl();
    EnrollmentServiceImpl enrollmentService;
    Enrollment enrollment;
    Course course;
    Student student;

    Teacher teacher;

    @BeforeEach
    void setup() {
        courseRepository = mock(CourseRepository.class);
        studentRepository = mock(StudentRepository.class);
        enrollmentRepository = mock(EnrollmentRepository.class);
        enrollmentService = new EnrollmentServiceImpl(courseRepository, studentRepository, enrollmentRepository, enrollmentMapper);

        teacher = Teacher.builder()
                .id(1L)
                .build();

        course = Course.builder()
                .teacher(teacher)
                .name("Java")
                .id(1L)
                .build();

        student = Student.builder()
                .name("Ivan")
                .id(1L)
                .build();

        enrollment = Enrollment.builder()
                .course(course)
                .id(1L)
                .student(student)
                .grades(new ArrayList<>())
                .build();

    }


    @Test
    void getAllByStudent() throws StudentNotAssignedException, EmptyDatabaseException {
        when(enrollmentRepository.findAllByStudentId(anyLong())).thenReturn(Optional.of(Arrays.asList(enrollment)));
        assertAll(
                () -> assertEquals(enrollmentService.getAllByStudent(anyLong()).get(0), enrollmentMapper.enrollmentEntityToEnrollmentResponse(enrollment)),
                () -> assertNotNull(enrollmentService.getAllByStudent(anyLong()).get(0)),
                () -> assertTrue(enrollment.getGrades().isEmpty()),
                () -> assertFalse(enrollment.getId().equals(null)));
    }

    @Test
    void getAll() {
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));
        assertAll(() -> assertEquals(enrollmentService.getAll(), enrollmentMapper.AllEnrollmentsToAllResponse(Arrays.asList(enrollment))),
                () -> assertEquals(enrollmentService.getAll().size(), 1),
                () -> assertNotNull(enrollmentService.getAll()),
                () -> assertNotNull(enrollmentService.getAll().get(0).getCourse()));

    }

    @Test
    void addGrade() throws StudentNotAssignedException, InvalidGradeException {
        when(enrollmentRepository.findByCourseIdAndStudentId(1L, 1L)).thenReturn(Optional.ofNullable(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        String student = enrollmentService.addGrade(1L, 1L, 4).getStudents().getName();
        assertAll(
                () -> assertEquals(enrollmentService.addGrade(1L, 1L, 4), enrollmentMapper.enrollmentEntityToEnrollmentResponse(enrollment)),
                () -> assertNotNull(enrollmentService.addGrade(1L, 1L, 4)),
                () -> assertFalse(enrollmentService.addGrade(1L, 1L, 4).getGrades().isEmpty()),
                () -> assertTrue(student.startsWith("Iv")),
                () -> assertTrue(student.endsWith("an")));
    }

    @Test
    void enroll() throws ObjectNotFoundException {
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        when(courseRepository.existsById(anyLong())).thenReturn(true);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(course));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        String courseName = enrollmentService.enroll(1L, 1L).getCourse().getName();
        assertAll(
                () -> assertTrue(enrollmentService.enroll(1L, 1L).getCourse().getName().equals(course.getName())),
                () -> assertTrue(courseName.startsWith("Ja")),
                () -> assertTrue(courseName.endsWith("va")),
                () -> assertFalse(courseName.isBlank()));


    }

    @Test
    void getByCourseAndStudent() throws StudentNotAssignedException {
        when(enrollmentRepository.findByCourseIdAndStudentId(anyLong(), anyLong())).thenReturn(Optional.ofNullable(enrollment));
        Student student = enrollmentService.getByCourseAndStudent(anyLong(), anyLong()).getStudents();
        Course course = enrollmentService.getByCourseAndStudent(anyLong(), anyLong()).getCourse();
        assertAll(
                () -> assertNotNull(enrollmentService.getByCourseAndStudent(anyLong(), anyLong())),
                () -> assertTrue(course.getName().startsWith("Jav")),
                () -> assertTrue(course.getName().endsWith("a")),
                () -> assertFalse(student.getName().startsWith("Indie")),
                () -> assertFalse(student.getName().endsWith("L")));

    }

    @Test
    void getStudentTotalAvg() throws InvalidGradeException, EmptyDatabaseException {
        enrollment.getGrades().add(4.5);
        when(enrollmentRepository.findAllByStudentId(anyLong())).thenReturn(Optional.of(Arrays.asList(enrollment)));
        double avg = enrollmentService.getStudentTotalAvg(anyLong());
        assertAll(
                () -> assertEquals(enrollmentService.getStudentTotalAvg(anyLong()), 4.5),
                () -> assertNotEquals(4, avg, 0.0));

    }

    @Test
    void getCourseTotalAvg() throws InvalidGradeException, EmptyDatabaseException {
        enrollment.getGrades().add(4.5);
        when(enrollmentRepository.findAllByCourseId(anyLong())).thenReturn(Optional.of(Arrays.asList(enrollment)));
        double avg = enrollmentService.getCourseTotalAvg(anyLong());
        assertAll(
                () -> assertEquals(enrollmentService.getCourseTotalAvg(anyLong()), 4.5),
                () -> assertNotEquals(4, avg, 0.0));

    }

    @Test
    void showAllStudentsAndTeachers() {
        enrollment.getGrades().add(4.6);
        when(enrollmentRepository.findAll()).thenReturn(Arrays.asList(enrollment));
        assertAll(
                () -> assertNotNull(enrollmentService.showAllStudentsAndTeachers().get(0).getCourse()),
                () -> assertNotNull(enrollmentService.showAllStudentsAndTeachers().get(0).getStudents()),
                () -> assertNotNull(enrollmentService.showAllStudentsAndTeachers().get(0).getCourse().getTeacher()));
    }


    @Test
    void showAllGroupedByCourseAndAvg() {
        enrollment.getGrades().add(4.5);
        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));
        assertNotNull(enrollmentService.showAllGroupedByCourseAndAvg());
        assertAll(
                () -> assertTrue(enrollmentService.showAllGroupedByCourseAndAvg().containsKey("Java")),
                () -> assertTrue(enrollmentService.showAllGroupedByCourseAndAvg().get("Java").containsKey("Ivan")),
                () -> assertTrue(enrollmentService.showAllGroupedByCourseAndAvg().get("Java").get("Ivan").equals(4.5)));

    }

}