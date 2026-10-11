package com.exam.repository;

import com.exam.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataMongoTest(excludeAutoConfiguration = {
        org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration.class,
        org.springframework.boot.data.mongodb.autoconfigure.DataMongoAutoConfiguration.class
})
class UserRepositoryTest {

    @MockitoBean
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
                Collections.emptyList()
        );
    }

    @Test
    void findByUserId_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findByUserId("user1")).thenReturn(sampleUser);

        User foundUser = userRepository.findByUserId("user1");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.userId()).isEqualTo("user1");
        assertThat(foundUser.name()).isEqualTo("name1");
    }

    @Test
    void findByUserId_WhenUserDoesNotExist_ShouldReturnNull() {
        when(userRepository.findByUserId("non_existent_id")).thenReturn(null);

        User foundUser = userRepository.findByUserId("non_existent_id");

        assertThat(foundUser).isNull();
    }
}
