package com.in28.socialmedia28.service;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.dao.entities.UserV2;
import com.in28.socialmedia28.dao.repositories.UserRepository;
import com.in28.socialmedia28.dao.repositories.UserV2Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserV2Repository userV2Repository;

    public UserServiceImpl(UserRepository userRepository, UserV2Repository userV2Repository) {
        this.userRepository = userRepository;
        this.userV2Repository = userV2Repository;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserV2 getUserV2(Long id) {
        return userV2Repository.findById(id).orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String name, LocalDate birthDate) {
        User user = new User();
        user.setName(name);
        user.setBirthDate(birthDate);
        return userRepository.save(user);
    }

    @Override
    public UserV2 createUserV2(String firstName, String lastName, LocalDate birthDate) {
        UserV2 user = new UserV2();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthDate(birthDate);
        return userV2Repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, String name, LocalDate birthDate) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(name);
        user.setBirthDate(birthDate);
        return userRepository.save(user);
    }
}

