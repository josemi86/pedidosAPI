package com.exam.controller;

import com.exam.model.User;
import com.exam.model.dto.UserRequestDto;
import com.exam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getUserById_WhenUserExists_ShouldReturn200AndUser() throws Exception {
        String userId = "user1";
        User mockUser = new User(
                "user1",
                "name1",
                "firstSurname",
                "secondSurname",
                "address",
                "email@gmail.com",
                Collections.emptyList());

        when(userService.getUserById(userId)).thenReturn(mockUser);

        mockMvc.perform(get("/api/v1/users/{user_id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userService, times(1)).getUserById(userId);
    }


    @Test
    void createUser_WithValidRequest_ShouldReturn211AndSuccessMessage() throws Exception {
        UserRequestDto requestDto = new UserRequestDto(
                "userId",
                "name",
                "firstSurname",
                "secondSurname",
                "address",
                "email@gmail.com");
        doNothing().when(userService).createUser(any(UserRequestDto.class));

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.userId").value("userId"));

        verify(userService, times(1)).createUser(any(UserRequestDto.class));
    }

    @Test
    void createUser_WithInvalidRequest_ShouldReturn400BadRequest() throws Exception {
        UserRequestDto invalidRequestDto = new UserRequestDto(
                "user1",
                "name1",
                "firstSurname",
                "secondSurname",
                "address",
                "email");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequestDto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserRequestDto.class));
    }
}