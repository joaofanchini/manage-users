package com.training.webcrud.config.security;

import com.training.webcrud.data.entities.User;
import com.training.webcrud.data.repositories.UserRepository;
import com.training.webcrud.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ForbiddenException("validation.userNotFound"));

        return new Principal(user.getUsername(), user.getPassword());

    }
}
