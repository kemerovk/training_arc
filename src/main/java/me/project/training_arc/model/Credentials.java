package me.project.training_arc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credentials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    // не забывать, что это внешний ключ
    @Id
    private int id;

    @Column
    private String login;

    @Column
    private String password;


}
