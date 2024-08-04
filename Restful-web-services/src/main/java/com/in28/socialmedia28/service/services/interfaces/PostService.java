package com.in28.socialmedia28.service.services.interfaces;

import com.in28.socialmedia28.service.dtos.PostDto;
import jakarta.validation.Valid;

import java.util.List;

public interface PostService {

    PostDto createPost(@Valid PostDto post);

    List<PostDto> getAllPosts();
    List<PostDto> getAllPostsByUserId(Long userId);
    PostDto getPostById(Long id);
    PostDto updatePost(Long id, PostDto postDto);
    void deletePost(Long id);
}
