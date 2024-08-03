package com.in28.socialmedia28.service.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonFilter("UserFilter")
public class UserDto {

        private Long id;

        @JsonProperty("full name")
        private String name;

        @JsonIgnore
        private String password;

        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDate birthDate;

        @NotNull(message = "VIP status is mandatory")
        private Boolean isVIP = false;

        private String vipCode;
}
