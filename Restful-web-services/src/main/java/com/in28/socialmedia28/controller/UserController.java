package com.in28.socialmedia28.controller;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28.socialmedia28.advice.ErrorResponse;
import com.in28.socialmedia28.dao.entities.User;
import com.in28.socialmedia28.dao.entities.UserV2;
import com.in28.socialmedia28.exception.UserNotFoundException;
import com.in28.socialmedia28.service.dtos.UserDto;
import com.in28.socialmedia28.service.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController()
@RequestMapping("/api")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/v1/users")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/v1/users/dynamicFilter/normal")
    public MappingJacksonValue getUsersFilteredNormal() {
        List<UserDto> users = userService.getUsers();

//        MappingJacksonValue mappingVip = new MappingJacksonValue(users.stream().filter(UserDto::getIsVIP).toList());
//        SimpleBeanPropertyFilter filterVip = SimpleBeanPropertyFilter.serializeAll();
//        FilterProvider filtersVip = new SimpleFilterProvider().addFilter("UserFilter", filterVip);
//        mappingVip.setFilters(filtersVip);

        MappingJacksonValue mappingNormal = new MappingJacksonValue(users.stream().filter(userDto -> !userDto.getIsVIP()).toList());
        SimpleBeanPropertyFilter filterNormal = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "birthDate","password");
        FilterProvider filtersNormal = new SimpleFilterProvider().addFilter("UserFilter", filterNormal);
        mappingNormal.setFilters(filtersNormal);

        // we could not return both filters in the same response
        return mappingNormal;
    }

    @GetMapping("/v1/users/dynamicFilter/vip")
    public MappingJacksonValue getUsersFilteredVip() {
        List<UserDto> users = userService.getUsers();

        MappingJacksonValue mappingVip = new MappingJacksonValue(users.stream().filter(UserDto::getIsVIP).toList());
        SimpleBeanPropertyFilter filterVip = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider filtersVip = new SimpleFilterProvider().addFilter("UserFilter", filterVip);
        mappingVip.setFilters(filtersVip);

        // we could not return both filters in the same response
        return mappingVip;
    }


    @Operation(summary = "Get User by ID", description = "Get a user by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/v1/users/{id}")
    public EntityModel<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);

        asserUserExist(id, user);

        // add the user and links to get all users and update user
        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getUsers());
        WebMvcLinkBuilder linkToUpdateUser = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).updateUser(id, user));

        entityModel.add(linkToUsers.withRel("all-users"));
        entityModel.add(linkToUpdateUser.withRel("update-user"));

        return entityModel;
    }

    // Url versioning
    @Operation(summary = "Get User by ID", description = "Get a user by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })

    // parameter versioning
    // note that we have also headers versioning and media type versioning but try to avoid them
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserWithParam(@PathVariable Long id, @RequestParam(value = "version", defaultValue = "1") int version) {
        if (version == 1) {
            User user = userService.getUser(id);
            asserUserExist(id, user);
            return ResponseEntity.ok(user);
        } else if (version == 2) {
            UserV2 user = userService.getUserV2(id);
            if (user == null) {
                log.error("User with id {} not found", id);
                throw new UserNotFoundException("User not found");
            }
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.badRequest().body("Invalid version");
        }
    }

    @PostMapping("/v1/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user) {
        UserDto savedUser = userService.createUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        log.info("User created with id {}", savedUser);

        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping("/v2/users")
    public ResponseEntity<UserV2> createUserV2(@Valid @RequestBody UserV2 user) {
        UserV2 savedUser = userService.createUserV2(user.getFirstName(), user.getLastName(), user.getBirthDate());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/v1/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/v1/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user.getName(), user.getBirthDate());
    }

    @Operation(summary = "Get User i18n", description = "Get a user by its ID with internationalization", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/v1/users/{id}/i18n")
    public String getUserI18n(@PathVariable Long id) {
        // get the locale from the LocaleContextHolder that is specified by the user with accept-language on the header
        Locale locale = LocaleContextHolder.getLocale();
        log.info("Locale: {}", locale);

        User user = userService.getUser(id);
        asserUserExist(id, user);
        int age = LocalDate.now().getYear() - user.getBirthDate().getYear();

        return messageSource.getMessage("user.info", new Object[]{user.getName(), age}, "Default Message", locale);
    }

    private static void asserUserExist(Long id, User user) {
        if (user == null) {
            log.error("User with id {} not found", id);
            throw new UserNotFoundException("User not found");
        }
    }


}

