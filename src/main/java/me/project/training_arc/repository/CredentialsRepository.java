package me.project.training_arc.repository;

import me.project.training_arc.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {

    Optional<Credentials> findByLogin(String login);
    Optional<Credentials> findById(int id);

}
