package com.training.webcrud.controllers;

import com.training.webcrud.dtos.LoginDTO;
import com.training.webcrud.dtos.LoginResponseDTO;
import com.training.webcrud.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity makeLogin(@Valid @RequestBody LoginDTO loginDTO){
        LoginResponseDTO login = loginService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @GetMapping
    public ResponseEntity getStatus(){
        return ResponseEntity.ok(null);
    }
}
