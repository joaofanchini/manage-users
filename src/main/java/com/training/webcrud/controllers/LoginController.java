package com.training.webcrud.controllers;

import com.training.webcrud.dtos.LoginDTO;
import com.training.webcrud.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity makeLogin(@Valid @RequestBody LoginDTO loginDTO){
        loginService.login(loginDTO);
        return null;
    }
}
