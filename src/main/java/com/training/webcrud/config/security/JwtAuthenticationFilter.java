package com.training.webcrud.config.security;

import com.training.webcrud.config.JwtBean;
import com.training.webcrud.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String PREFIX = "X-TOKEN"; //Poderia ser Authorization ou algo assim

    @Autowired
    private JwtBean jwtBean;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null)
            throw new ForbiddenException("validation.notHaveAuthorizationHeader");

        if (authorization.isEmpty() || authorization.length() < PREFIX.length())
            throw new ForbiddenException("validation.notHavePrefixAuthorizationHeader");

        String prefixAuthorizationHeader = authorization.substring(0, PREFIX.length()).trim();
        if (prefixAuthorizationHeader.equals(PREFIX))
            throw new ForbiddenException("validation.invalidPrefixHeader");

        String token = authorization.substring(PREFIX.length());

        if (!jwtBean.validateToken(token))
            throw new ForbiddenException("validation.invalidToken");

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
