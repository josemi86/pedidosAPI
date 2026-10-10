package com.exam.demo.controller;

import com.exam.demo.model.User;
import com.exam.demo.model.dto.UserRequestDto;
import com.exam.demo.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "user_id")  @NotBlank(message = "El userId no puede estar vacío")  String userId) {
        User result = userService.getUserById(userId);
        return ResponseEntity.ok(result);
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDto request) {
            userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "userId", request.userId()
            ));
    }

}