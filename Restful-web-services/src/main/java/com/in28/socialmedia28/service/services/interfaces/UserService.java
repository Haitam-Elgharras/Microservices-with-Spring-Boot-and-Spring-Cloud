package com.in28.socialmedia28.service.services.interfaces;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.dao.entities.UserV2;
import com.in28.socialmedia28.service.dtos.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    UserDto createUser(User user);
    UserV2 createUserV2(String firstName, String lastName, LocalDate birthDate);
    void deleteUser(Long id);
    User updateUser(Long id, String name, LocalDate birthDate);
    User getUser(Long id);
    UserV2 getUserV2(Long id);
    List<UserDto> getUsers();
}
