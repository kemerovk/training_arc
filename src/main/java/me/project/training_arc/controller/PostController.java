package me.project.training_arc.controller;

import me.project.training_arc.dto.PostDto;
import me.project.training_arc.model.Client;
import me.project.training_arc.model.Post;
import me.project.training_arc.service.ClientService;
import me.project.training_arc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private ClientService clientService;

    @GetMapping("get_all_posts_by_author")
    public List<Post> getAllPostsByAuthor(Client author) {
        return postService.getAllPostsByAuthor(author);
    }

    @PostMapping("post")
    public Post addPost(@RequestBody PostDto post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Client author = clientService.findByLogin(username);

        Post newPost = new Post();
        newPost.setAuthor(author);
        newPost.setNewDetails(post);
        return postService.addPost(newPost);
    }

    @PatchMapping("edit/{id}")
    public void editPost(@PathVariable int id, PostDto post) {
        Post oldPost = postService.getPostById(id);
        oldPost.setNewDetails(post);
        postService.updatePost(oldPost);
    }

    @DeleteMapping("delete/{id}")
    public void deletePost(@PathVariable int id) {
        postService.deletePost(id);
    }

}
