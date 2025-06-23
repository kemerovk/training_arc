package me.project.training_arc.repository;

import me.project.training_arc.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByLogin(String login);
    Optional<Client> findByEmail(String email);




    @Modifying
    @Query("update Client c set c.minioPath = ?1 where c.login = ?2")
    int updateMinioPath(String minioPath, String login);

}
