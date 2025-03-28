package me.project.training_arc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ClientDto(
        @NotBlank(message = "дурак")
        String login,
        @Positive(message = "блин")
        int age){
}
