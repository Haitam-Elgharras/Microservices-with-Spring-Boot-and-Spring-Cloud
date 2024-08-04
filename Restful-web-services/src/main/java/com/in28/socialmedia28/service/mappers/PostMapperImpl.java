package com.in28.socialmedia28.service.mappers;

import com.in28.socialmedia28.dao.entities.Post;
import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.exception.UserNotFoundException;
import com.in28.socialmedia28.service.dtos.PostDto;
import com.in28.socialmedia28.service.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapperImpl implements PostMapper {
    private final UserService userService;

    @Override
    public Post postDtoToPost(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        User user = userService.getUser( postDto.getUserId() );
        if ( user == null ) {
            throw new UserNotFoundException( "User with id " + postDto.getUserId() + " not found" );
        }

        Post post = new Post();

        post.setId( postDto.getId() );
        post.setDescription( postDto.getDescription() );
        post.setUser( user );

        return post;
    }

    @Override
    public PostDto postToPostDto(Post post) {
        if ( post == null ) {
            return null;
        }

        if ( post.getUser() == null ) {
            throw new UserNotFoundException( "User with id " + post.getUser().getId() + " not found" );
        }

        PostDto postDto = new PostDto();

        postDto.setId( post.getId() );
        postDto.setDescription( post.getDescription() );
        postDto.setUserId( post.getUser().getId() );

        return postDto;
    }

    @Override
    public List<PostDto> postsToPostDtos(List<Post> posts) {
        if ( posts == null ) {
            return null;
        }

         return posts.stream().map(this::postToPostDto).toList();
    }

    @Override
    public List<Post> postDtosToPosts(List<PostDto> postDtos) {
        if ( postDtos == null ) {
            return null;
        }

        return postDtos.stream().map(this::postDtoToPost).toList();
    }

}
