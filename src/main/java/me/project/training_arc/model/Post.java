package me.project.training_arc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.project.training_arc.dto.PostDto;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "path_to_pic")
    private String pathToPic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author")
    private Client author;

    @Column(name = "date")
    private LocalDateTime postDate;

    public void setNewDetails(PostDto post) {
        this.title = post.title();
        this.content = post.content();
        this.pathToPic = post.pathToPic();
        this.postDate = LocalDateTime.now();
    }
}
