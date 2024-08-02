package com.in28.socialmedia28.service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class UserDto {

        private Long id;

        @JsonProperty("full name")
        private String name;

        @JsonIgnore
        private String password;

        @JsonFormat(pattern = "dd/MM/yyyy")
        private String birthDate;
}
