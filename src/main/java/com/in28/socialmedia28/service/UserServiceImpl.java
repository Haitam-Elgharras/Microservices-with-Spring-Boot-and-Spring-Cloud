package com.in28.socialmedia28.service;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.dao.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
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

