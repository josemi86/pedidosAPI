package com.exam.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public record User(
        @Id
        String userId,
        String name,
        String firstSurname,
        String secondSurname,
        String address,
        String email,
        List<Order> orders
) {}