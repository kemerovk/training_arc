package me.project.training_arc.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private int age;


    public Client(String name, int age){
        this.name = name;
        this.age = age;
    }

    public Client() {

    }


}
