package com.example.monolith.controllers;

import com.example.monolith.dto.studentDto.StudentRequest;
import com.example.monolith.dto.studentDto.StudentResponse;
import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.services.impl.StudentServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = StudentController.class)
class StudentControllerTest {

    @MockBean
    StudentServiceImpl studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;
    StudentResponse studentResponse;

    StudentRequest studentRequest;


    @BeforeEach
    void setup(){


        studentResponse = StudentResponse.builder()
                .age(30)
                .id(1L)
                .name("teodor")
                .build();

        studentRequest =  StudentRequest.builder()
                .age(30)
                .name("teodor")
                .build();

    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void get() throws Exception {
        when(studentService.get(1L)).thenReturn(studentResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/students/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.name",is("teodor")))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.age",is(30)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAll() throws Exception {
        when(studentService.getAll()).thenReturn(List.of(studentResponse));
        mockMvc.perform(MockMvcRequestBuilders.get("/students")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void delete() throws Exception {
        when(studentService.delete(1L)).thenReturn(studentResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("teodor")))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void save() throws Exception {
        when(studentService.save(studentRequest)).thenReturn(studentResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf())
                        .content(gson.toJson(studentRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("teodor")))
                .andExpect(jsonPath("$.age", is(30)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionGet() throws Exception {
        doThrow(ObjectNotFoundException.class).when(studentService).get(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/students/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionDelete() throws Exception {
        doThrow(ObjectNotFoundException.class).when(studentService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionSave() throws Exception {
        doThrow(ObjectAlreadyExistException.class).when(studentService).save(ArgumentMatchers.any(StudentRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .content(gson.toJson(studentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }



}