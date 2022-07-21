package com.example.monolith.controllers;

import com.example.monolith.dto.enrollmentDto.EnrollmentResponse;

import com.example.monolith.entity.Course;
import com.example.monolith.entity.Student;

import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import com.example.monolith.utility.Exceptions.InvalidGradeException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.utility.Exceptions.StudentNotAssignedException;
import com.example.monolith.services.impl.EnrollmentServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.TreeMap;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = EnrollmentController.class)
class EnrollmentControllerTest {

    @MockBean
    EnrollmentServiceImpl enrollmentService;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    private EnrollmentResponse enrollmentResponse;


    @BeforeEach
    void setup() {

        enrollmentResponse = EnrollmentResponse.builder()
                .course(Course.builder()
                        .id(1L)
                        .build())
                .grades(List.of(4.0))
                .students(Student.builder()
                        .id(1L)
                        .build())
                .build();

    }


    @Test
    void getEnrollment() {

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAll() throws Exception {
        when(enrollmentService.getAll()).thenReturn(List.of(enrollmentResponse));
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void delete() throws Exception {
        when(enrollmentService.delete(anyLong(), anyLong())).thenReturn(enrollmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/enrollments/1/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.course.id", is(1)))
                .andExpect(jsonPath("$.students.id", is(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void enroll() throws Exception {
        when(enrollmentService.enroll(anyLong(), anyLong())).thenReturn(enrollmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/enrollments/1/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.course.id", is(1)))
                .andExpect(jsonPath("$.students.id", is(1)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getByCourseAndStudent() throws Exception {
        when(enrollmentService.getByCourseAndStudent(anyLong(), anyLong())).thenReturn(enrollmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/1/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.course.id", is(1)))
                .andExpect(jsonPath("$.students.id", is(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void addGrade() throws Exception {
        when(enrollmentService.addGrade(anyLong(), anyLong(), anyDouble())).thenReturn(enrollmentResponse);
        mockMvc.perform(MockMvcRequestBuilders.patch("/enrollments/add/1/1/4")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.course.id", is(1)))
                .andExpect(jsonPath("$.students.id", is(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getStudentTotalAvg() throws Exception {
        when(enrollmentService.getStudentTotalAvg(anyLong())).thenReturn(4.5);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/average/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$", is(4.5)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getCourseAverage() throws Exception {
        when(enrollmentService.getCourseTotalAvg(anyLong())).thenReturn(4.5);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/cavg/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$", is(4.5)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void showByCourseAndTeachers() throws Exception {
        List enroll = List.of(List.of(enrollmentResponse));
        when(enrollmentService.showAllStudentsAndTeachers()).thenReturn(enroll);
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/all")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void sorted() throws Exception {
        TreeMap<String, TreeMap<String, Double>> sorted = new TreeMap<>();
        sorted.put("enroll", new TreeMap<>());
        sorted.get("enroll").put("enrollment", 4.4);
        when(enrollmentService.showAllGroupedByCourseAndAvg()).thenReturn(sorted);

        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/sorted")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$").value(hasKey("enroll")))
                .andExpect(jsonPath("$.enroll").value(hasKey("enrollment")))
                .andExpect(jsonPath("$.enroll.enrollment", is(4.4)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowResponseExceptionGetEnrollment() throws Exception {
        doThrow(StudentNotAssignedException.class).when(enrollmentService).getAllByStudent(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowResponseExceptionGetEnrollmentTwo() throws Exception {
        doThrow(EmptyDatabaseException.class).when(enrollmentService).getAllByStudent(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionDelete() throws Exception {
        doThrow(StudentNotAssignedException.class).when(enrollmentService).delete(anyLong(),anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/enrollments/1/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isConflict());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionEnroll() throws Exception {
        doThrow(ObjectNotFoundException.class).when(enrollmentService).enroll(anyLong(),anyLong());
        mockMvc.perform(MockMvcRequestBuilders.post("/enrollments/1/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionAddGrade() throws Exception {
        doThrow(InvalidGradeException.class).when(enrollmentService).addGrade(anyLong(),anyLong(),anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.patch("/enrollments/add/1/1/4.5")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionAddGradeTwo() throws Exception {
        doThrow(StudentNotAssignedException.class).when(enrollmentService).addGrade(anyLong(),anyLong(),anyDouble());
        mockMvc.perform(MockMvcRequestBuilders.patch("/enrollments/add/1/1/4.5")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionStudentTotalAvg() throws Exception {
        doThrow(EmptyDatabaseException.class).when(enrollmentService).getStudentTotalAvg(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/average/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionStudentTotalAvgTwo() throws Exception {
        doThrow(InvalidGradeException.class).when(enrollmentService).getStudentTotalAvg(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/average/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionCourseTotalAvg() throws Exception {
        doThrow(EmptyDatabaseException.class).when(enrollmentService).getCourseTotalAvg(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/cavg/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void ThrowStatusExceptionCourseTotalAvgTwo() throws Exception {
        doThrow(InvalidGradeException.class).when(enrollmentService).getCourseTotalAvg(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollments/cavg/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotAcceptable());
    }


}