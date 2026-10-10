package com.exam.demo.service;

import com.exam.demo.client.MockApiClient;
import com.exam.demo.model.User;
import com.exam.demo.model.dto.UserRequestDto;
import com.exam.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final MockApiClient mockApiClient;

    public UserService(UserRepository userRepository, MockApiClient mockApiClient) {
        this.userRepository = userRepository;
        this.mockApiClient = mockApiClient;
    }

    public User getUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void createUser(UserRequestDto request) {
        User newUser = new User(
                request.getUserId(),
                request.getName(),
                request.getFirstSurname(),
                request.getSecondSurname(),
                request.getAddress(),
                request.getEmail(),
                mockApiClient.fetchUserOrders(request.getUserId())
        );
        userRepository.save(newUser);
    }

}