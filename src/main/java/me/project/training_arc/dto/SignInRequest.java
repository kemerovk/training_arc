package me.project.training_arc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank(message = "Логин не может быть пустым")
        String login,

        @Size(min = 3, max = 20, message = "длина пароля должна быть от 3 до 20 символов")
        String password
) { }
