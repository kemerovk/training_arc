package me.project.training_arc.service;

import me.project.training_arc.model.Client;
import me.project.training_arc.model.Post;
import me.project.training_arc.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    public void updatePost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(int id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAllPostsByAuthor(Client author) {
        return postRepository.findAllByAuthor(author);
    }

    public Post getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }
}
