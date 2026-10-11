package com.exam.demo.service;

import com.exam.demo.client.MockApiClient;
import com.exam.demo.model.Item;
import com.exam.demo.model.Order;
import com.exam.demo.model.User;
import com.exam.demo.model.dto.UserRequestDto;
import com.exam.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MockApiClient mockApiClient;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        String userId = "user1";
        User mockUser = new User(
                "user1",
                "name1",
                "firstSurname",
                "secondSurname",
                "address",
                "email@gmail.com",
                Collections.emptyList());
        when(userRepository.findByUserId(userId)).thenReturn(mockUser);

        User result = userService.getUserById(userId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockUser);
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnNull() {
        String userId = "unknown_user";
        when(userRepository.findByUserId(userId)).thenReturn(null);

        User result = userService.getUserById(userId);

        assertThat(result).isNull();
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    void createUser_WithValidRequest_ShouldFetchOrdersAndSaveUser() {
        String userId = "user_test_01";
        UserRequestDto request = new UserRequestDto(
                userId,
                "Juan",
                "Pérez",
                "Gómez",
                "Av. Siempre Viva 123",
                "juan.perez@email.com"
        );
        List<Order> mockOrders = List.of(new Order(
                "order1",
                List.of(new Item("item1", 2, "deliveryStatus1")),
                "channel1"));
        when(mockApiClient.fetchUserOrders(userId)).thenReturn(mockOrders);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userService.createUser(request);

        verify(mockApiClient, times(1)).fetchUserOrders(userId);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.userId()).isEqualTo(userId);
        assertThat(savedUser.name()).isEqualTo("Juan");
        assertThat(savedUser.firstSurname()).isEqualTo("Pérez");
        assertThat(savedUser.secondSurname()).isEqualTo("Gómez");
        assertThat(savedUser.address()).isEqualTo("Av. Siempre Viva 123");
        assertThat(savedUser.email()).isEqualTo("juan.perez@email.com");
        assertThat(savedUser.orders()).isEqualTo(mockOrders);
    }

    @Test
    void createUser_WhenUserHasNoOrders_ShouldSaveUserWithEmptyOrderList() {
        String userId = "user_no_orders";
        UserRequestDto request = new UserRequestDto(
                userId,
                "Ana",
                "Silva",
                "Cruz",
                "Calle Falsa 456",
                "ana.silva@email.com"
        );
        when(mockApiClient.fetchUserOrders(userId)).thenReturn(Collections.emptyList());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        userService.createUser(request);

        verify(mockApiClient, times(1)).fetchUserOrders(userId);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.orders()).isEmpty();
    }
}