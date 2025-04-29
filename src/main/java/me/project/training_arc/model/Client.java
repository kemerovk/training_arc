package me.project.training_arc.model;


import jakarta.persistence.*;
import lombok.*;
import me.project.training_arc.dto.SignUpRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;

@Entity
@Data
@Getter
@Setter
@Table(name = "client")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
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
    private Time creationTime;


    public Client(SignUpRequest request) {
        this.login = request.login();
        this.age = request.age();
        this.email = request.email();
        this.minioPath = request.minioPath();
        this.creationTime = request.creationTime();
    }
}
