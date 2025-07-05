package me.project.training_arc.dto;

import java.time.LocalDateTime;

public record PostDto (int id,
                       String title,
                       String content,
                       String pathToPic,
                       String author,
                       LocalDateTime postDate){
}
