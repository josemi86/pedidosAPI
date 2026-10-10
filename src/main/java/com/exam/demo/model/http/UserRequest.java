package com.exam.demo.model.http;

import jakarta.validation.constraints.*;

public class UserRequest {
    @NotNull(message = "El userId es requerido.")
    private String userId;

    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;

    @NotBlank(message = "El primer apellido no puede estar vacío.")
    private String firstSurname;

    private String secondSurname;

    private String address;

    @Email(message = "Por favor, introduce una dirección de correo válida")
    private String email;

    // Getters y Setters}
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}