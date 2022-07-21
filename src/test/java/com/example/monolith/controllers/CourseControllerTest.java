package com.example.monolith.controllers;

import com.example.monolith.dto.courseDto.CourseRequest;
import com.example.monolith.dto.courseDto.CourseResponse;
import com.example.monolith.entity.Teacher;
import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.services.impl.CourseServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CourseController.class)
class CourseControllerTest {

    @MockBean
    private CourseServiceImpl courseService;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;
    private CourseResponse courseResponse;
    private CourseRequest courseRequest;


    @BeforeEach
    void setup() {
        courseResponse = CourseResponse.builder()
                .id(1L)
                .name("Data")
                .duration(30)
                .teacher(Teacher.builder()
                        .id(1L)
                        .degree("Professor")
                        .name("drago")
                        .build())
                .build();

        courseRequest = CourseRequest.builder()
                .name("Data")
                .duration(30)
                .teacher(Teacher.builder()
                        .id(1L)
                        .degree("Professor")
                        .name("drago")
                        .build())
                .build();
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getCourse() throws Exception {
        when(courseService.get(1L)).thenReturn(courseResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Data")))
                .andExpect(jsonPath("$.duration", is(30)))
                .andExpect(jsonPath("$.teacher.name", is("drago")))
                .andExpect(jsonPath("$.teacher.degree", is("Professor")));

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAll() throws Exception {
        when(courseService.getAll()).thenReturn(List.of(courseResponse));

        mockMvc.perform(MockMvcRequestBuilders.get("/courses")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void delete() throws Exception {
        when(courseService.delete(1L)).thenReturn(courseResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Data")))
                .andExpect(jsonPath("$.duration", is(30)))
                .andExpect(jsonPath("$.teacher.name", is("drago")))
                .andExpect(jsonPath("$.teacher.degree", is("Professor")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void save() throws Exception {
        when(courseService.save(courseRequest)).thenReturn(courseResponse);

        mockMvc.perform(post("/courses")
                        .content(gson.toJson(courseRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Data")))
                .andExpect(jsonPath("$.duration", is(30)))
                .andExpect(jsonPath("$.teacher.name", is("drago")))
                .andExpect(jsonPath("$.teacher.degree", is("Professor")))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void assignTeacher() throws Exception {

        when(courseService.assignTeacher(1L, 2L)).thenReturn(courseResponse);
        mockMvc.perform(patch("/courses/assign/1/2")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Data")))
                .andExpect(jsonPath("$.duration", is(30)))
                .andExpect(jsonPath("$.teacher.name", is("drago")))
                .andExpect(jsonPath("$.teacher.degree", is("Professor")))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionDelete() throws Exception {
        doThrow(ObjectNotFoundException.class).when(courseService).delete(anyLong());

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
        doThrow(ObjectAlreadyExistException.class).when(courseService).save(courseRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .content(gson.toJson(courseRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionGetCourse() throws Exception {
        doThrow(ObjectNotFoundException.class).when(courseService).get(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/courses/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionAssignTeacher() throws Exception {
        doThrow(ObjectNotFoundException.class).when(courseService).assignTeacher(anyLong(),anyLong());

        mockMvc.perform(MockMvcRequestBuilders.patch("/courses/1/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }



}