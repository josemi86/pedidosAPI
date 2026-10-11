package com.exam.model.dto;

import jakarta.validation.constraints.*;

public record UserRequestDto (
        @NotNull(message = "El userId es requerido.")
        String userId,
        @NotBlank(message = "El nombre no puede estar vacío.")
        String name,
        @NotBlank(message = "El primer apellido no puede estar vacío.")
        String firstSurname,
        String secondSurname,
        @NotBlank(message = "La dirección no puede estar vacía.")
        String address,
        @Email(message = "Por favor, introduce una dirección de correo válida")
        String email) {
}