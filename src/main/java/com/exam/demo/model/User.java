package com.exam.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String userId;
    private String firstSurname;
    private String secondSurname;
    private String address;
    private String email;
    private List<Order> orders;

    public User(String userId, String name, String firstSurname, String secondSurname, String address, String email, List<Order> orders) {
        this.userId = userId;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.address = address;
        this.email = email;
        this.orders = orders;
    }

}