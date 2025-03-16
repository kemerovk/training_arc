package me.project.training_arc.repository;

import me.project.training_arc.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByLogin(String login);

    @Override
    Optional<Client> findById(Integer integer);
}
