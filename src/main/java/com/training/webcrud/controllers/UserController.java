package com.training.webcrud.controllers;

import com.training.webcrud.dtos.UserDTO;
import com.training.webcrud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody UserDTO userDto) {
        userService.create(userDto);
        return ResponseEntity.noContent().build();
    }
}
