package com.in28.socialmedia28.service;

import com.in28.socialmedia28.dao.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User createUser(String name, LocalDate birthDate);
    void deleteUser(Long id);
    User updateUser(Long id, String name, LocalDate birthDate);
    User getUser(Long id);
    List<User> getUsers();
}
