package com.training.webcrud.services;

import com.training.webcrud.data.entities.User;
import com.training.webcrud.data.repositories.UserRepository;
import com.training.webcrud.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(UserDTO userDTO){
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());

        userRepository.save(user);
    }
}
