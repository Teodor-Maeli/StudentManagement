package com.example.monolith.controllers;

import com.example.monolith.dto.teacherDto.TeacherRequest;
import com.example.monolith.dto.teacherDto.TeacherResponse;

import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import com.example.monolith.services.impl.TeacherServiceImpl;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = TeacherController.class)
class TeacherControllerTest {

    @MockBean
    TeacherServiceImpl teacherService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;
    TeacherResponse teacherResponse;

    TeacherRequest teacherRequest;

    @BeforeEach
    void setup() {

        teacherResponse = TeacherResponse.builder()
                .id(1L)
                .degree("Professor")
                .name("Teodor")
                .build();

        teacherRequest = TeacherRequest.builder()
                .name("Teodor")
                .degree("Professor")
                .build();
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getTeachers() throws Exception {
        when(teacherService.get(anyLong())).thenReturn(teacherResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Teodor")))
                .andExpect(jsonPath("$.degree", is("Professor")));

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void getAllTeachers() throws Exception {
        when(teacherService.getAll()).thenReturn(List.of(teacherResponse));
        mockMvc.perform(MockMvcRequestBuilders.get("/teachers")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void deleteTeacher() throws Exception {
        when(teacherService.delete(1L)).thenReturn(teacherResponse);

        mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Teodor")))
                .andExpect(jsonPath("$.degree", is("Professor")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void save() throws Exception {
        when(teacherService.save(teacherRequest)).thenReturn(teacherResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf())
                        .content(gson.toJson(teacherRequest))
                        .accept(MediaType.ALL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Teodor")))
                .andExpect(jsonPath("$.degree", is("Professor")))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void update() throws Exception {
        when(teacherService.updateDegree(1L, "Professor")).thenReturn(teacherResponse);

        mockMvc.perform(MockMvcRequestBuilders.patch("/teachers/1/Professor")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf())
                        .content(gson.toJson(teacherRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Teodor")))
                .andExpect(jsonPath("$.degree", is("Professor")))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionSave() throws Exception {
        doThrow(ObjectAlreadyExistException.class).when(teacherService).save(any(TeacherRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/teachers")
                        .content(gson.toJson(teacherRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotAcceptable())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionGet() throws Exception {
        doThrow(ObjectNotFoundException.class).when(teacherService).get(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionDelete() throws Exception {
        doThrow(ObjectNotFoundException.class).when(teacherService).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/1")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    @WithMockUser(username = "test", password = "test", roles = "ADMIN")
    void testThrowsResponseStatusExceptionUpdate() throws Exception {
        doThrow(ObjectNotFoundException.class).when(teacherService).updateDegree(anyLong(),anyString());

        mockMvc.perform(MockMvcRequestBuilders.patch("/teachers/1/string")
                        .param("username", "test")
                        .param("password", "test")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }



}