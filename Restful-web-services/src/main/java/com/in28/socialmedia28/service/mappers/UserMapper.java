package com.in28.socialmedia28.service.mappers;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.service.dtos.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    List<UserDto> usersToUserDtos(List<User> users);
    List<User> userDtosToUsers(List<UserDto> userDtos);
}
