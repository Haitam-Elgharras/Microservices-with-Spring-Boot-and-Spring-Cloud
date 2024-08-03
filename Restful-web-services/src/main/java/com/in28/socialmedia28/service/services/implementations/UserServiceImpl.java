package com.in28.socialmedia28.service.services.implementations;

import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.dao.entities.UserV2;
import com.in28.socialmedia28.dao.repositories.UserRepository;
import com.in28.socialmedia28.dao.repositories.UserV2Repository;
import com.in28.socialmedia28.service.dtos.UserDto;
import com.in28.socialmedia28.service.mappers.UserMapper;
import com.in28.socialmedia28.service.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserV2Repository userV2Repository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserV2Repository userV2Repository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userV2Repository = userV2Repository;
        this.userMapper = userMapper;
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
    public List<UserDto> getUsers() {
        List<User> users =  userRepository.findAll();
        log.info("Users: {}", users);
        return userMapper.usersToUserDtos(users);
    }

    @Override
    public UserDto createUser(User user) {
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
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

