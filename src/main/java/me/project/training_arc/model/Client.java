package me.project.training_arc.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import me.project.training_arc.dto.SignUpRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String login;

    @Column
    private int age;

    @Column
    private String email;

    @Column(name = "minio_path")
    private String minioPath;

    @Column(name = "created_at")
    private Date creationTime;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    public Client(SignUpRequest request) {
        this.login = request.login();
        this.age = request.age();
        this.email = request.email();
        this.minioPath = request.minioPath();
        this.creationTime = request.creationTime();
    }
}
