package me.project.training_arc.dto;


import java.sql.Time;

public record SignUpRequest (String login,
                             int age,
                             String email,
                             String password,
                             String minioPath,
                             Time creationTime) {
}
