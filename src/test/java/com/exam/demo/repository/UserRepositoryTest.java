package com.exam.demo.repository;

import com.exam.demo.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(
                "user1",
                "name1",
                "firstSurname",
                "secondSurname",
                "address",
                "email@gmail.com",
                Collections.emptyList());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByUserId_WhenUserExists_ShouldReturnUser() {
        userRepository.save(sampleUser);

        User foundUser = userRepository.findByUserId("user1");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.userId()).isEqualTo("user1");
        assertThat(foundUser.name()).isEqualTo("name1");
    }

    @Test
    void findByUserId_WhenUserDoesNotExist_ShouldReturnNull() {
        User foundUser = userRepository.findByUserId("non_existent_id");

        assertThat(foundUser).isNull();
    }
}