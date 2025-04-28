package me.project.training_arc.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.project.training_arc.dto.ClientDto;

import java.sql.Time;

@Entity
@Data
@Getter
@Setter
@Table(name = "client")
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

    public Client(String login, int age){
        this.login = login;
        this.age = age;
    }

    public Client(ClientDto dao) {
        this.login = dao.login();
        this.age = dao.age();
    }

    public Client() {

    }


}
