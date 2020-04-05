package com.training.webcrud.services;

import com.training.webcrud.config.JwtBean;
import com.training.webcrud.config.security.PrincipalService;
import com.training.webcrud.dtos.LoginDTO;
import com.training.webcrud.dtos.LoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private JwtBean jwtBean;

    @Autowired
    private PrincipalService principalService;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        UserDetails userDetails = principalService.loadUserByUsername(loginDTO.getUsername());
        return new LoginResponseDTO(jwtBean.generateToken(userDetails.getUsername(), null));
    }
}
