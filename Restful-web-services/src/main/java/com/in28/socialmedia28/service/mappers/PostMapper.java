package com.in28.socialmedia28.service.mappers;


import com.in28.socialmedia28.dao.entities.Post;
import com.in28.socialmedia28.service.dtos.PostDto;

import java.util.List;

public interface PostMapper {
    Post postDtoToPost(PostDto postDto);
    PostDto postToPostDto(Post post);
    List<PostDto> postsToPostDtos(List<Post> posts);
    List<Post> postDtosToPosts(List<PostDto> postDtos);
}
