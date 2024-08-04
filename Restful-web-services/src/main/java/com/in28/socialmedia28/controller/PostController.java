package com.in28.socialmedia28.controller;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.exception.UserNotFoundException;
import com.in28.socialmedia28.service.dtos.PostDto;
import com.in28.socialmedia28.service.services.interfaces.PostService;
import com.in28.socialmedia28.service.services.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostsByUserId(@PathVariable Long userId) {
        User user = userService.getUser(userId);

        if (user == null)
            throw new UserNotFoundException("User with id " + userId + " not found");

        return ResponseEntity.ok(postService.getAllPostsByUserId(userId));
    }

    @DeleteMapping("/users/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Long userId,@Valid @RequestBody PostDto post) {
        User user = userService.getUser(userId);

        if (user == null)
            throw new UserNotFoundException("User with id " + userId + " not found");

        post.setUserId(userId);
        return ResponseEntity.ok(postService.createPost(post));
    }
}
