package me.project.training_arc.repository;

import me.project.training_arc.model.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> {

    Credentials findByLogin(String login);
}
