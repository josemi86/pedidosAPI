package com.exam.demo.service;

import com.exam.demo.model.User;
import com.exam.demo.model.http.UserRequest;
import com.exam.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void createUser(@Valid UserRequest request) {
        User newUser = new User(
                request.getUserId(),
                request.getName(),
                request.getFirstSurname(),
                request.getSecondSurname(),
                request.getAddress(),
                null
        );
        userRepository.save(newUser);
    }
}