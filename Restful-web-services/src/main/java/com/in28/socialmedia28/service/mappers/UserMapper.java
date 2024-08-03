package com.in28.socialmedia28.service.mappers;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.service.dtos.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setPassword(user.getPassword());
        userDto.setIsVIP(user.getIsVIP());
        userDto.setVipCode(user.getVipCode());

        return userDto;
    }

    public User userDtoToUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());

        if (userDto.getBirthDate() != null)
            user.setBirthDate(userDto.getBirthDate());

        user.setPassword(userDto.getPassword());

        if (userDto.getIsVIP() != null)
            user.setIsVIP(userDto.getIsVIP());

        user.setVipCode(userDto.getVipCode());

        return user;
    }

    //list conversion
    public List<UserDto> usersToUserDtos(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    public List<User> userDtosToUsers(List<UserDto> userDtos) {
        if (userDtos == null) {
            return null;
        }

        return userDtos.stream().map(this::userDtoToUser).collect(Collectors.toList());
    }
}
