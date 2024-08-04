package com.in28.socialmedia28.service.services.implementations;

import com.in28.socialmedia28.dao.entities.Post;
import com.in28.socialmedia28.dao.repositories.PostRepository;
import com.in28.socialmedia28.service.dtos.PostDto;
import com.in28.socialmedia28.service.mappers.PostMapper;
import com.in28.socialmedia28.service.services.interfaces.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public PostDto createPost(@Valid PostDto post) {
        Post newPost = postMapper.postDtoToPost(post);

        return postMapper.postToPostDto(postRepository.save(newPost));
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postMapper.postsToPostDtos(postRepository.findAll());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null)
            return null;

        return postMapper.postToPostDto(post);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElse(null);

        if (post == null)
            return null;

        post.setDescription(postDto.getDescription());
        postRepository.save(post);

        return postMapper.postToPostDto(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Long userId) {
        return postMapper.postsToPostDtos(postRepository.findAllByUserId(userId));
    }
}
