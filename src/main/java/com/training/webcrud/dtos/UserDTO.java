package com.training.webcrud.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    @NotEmpty(message = "validation.notNullOrEmpty")
    private String username;
    @NotEmpty(message = "validation.notNullOrEmpty")
    private String password;
}
