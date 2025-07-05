package me.project.training_arc.repository;

import me.project.training_arc.model.Client;
import me.project.training_arc.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByAuthor(Client author);
    Optional<Post> findByTitle(String title);
    Optional<Post> findById(int id);

}
