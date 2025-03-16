package me.project.training_arc.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.project.training_arc.dao.ClientDAO;

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


    public Client(String login, int age){
        this.login = login;
        this.age = age;
    }

    public Client(ClientDAO dao) {
        this.login = dao.login();
        this.age = dao.age();
    }

    public Client() {

    }


}
