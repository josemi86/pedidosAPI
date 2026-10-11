package com.exam.service;

import com.exam.client.MockApiClient;
import com.exam.model.User;
import com.exam.model.dto.UserRequestDto;
import com.exam.repository.UserRepository;
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
                request.userId(),
                request.name(),
                request.firstSurname(),
                request.secondSurname(),
                request.address(),
                request.email(),
                mockApiClient.fetchUserOrders(request.userId())
        );
        userRepository.save(newUser);
    }

}