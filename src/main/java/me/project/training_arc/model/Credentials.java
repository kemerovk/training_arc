package me.project.training_arc.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "credentials")
@Data
public class Credentials {

    @Id
    private String login;

    @Column
    private String password;

    public Credentials(String login, String encode) {
        this.login = login;
        this.password = encode;
    }

    public Credentials() {

    }
}
