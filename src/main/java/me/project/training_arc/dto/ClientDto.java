package me.project.training_arc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ClientDto(
        @NotBlank(message = "еблан")
        String login,
        @Positive(message = "сука")
        int age){
}
