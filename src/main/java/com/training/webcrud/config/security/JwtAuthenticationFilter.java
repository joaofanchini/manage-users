package com.training.webcrud.config.security;

import com.training.webcrud.config.JwtBean;
import com.training.webcrud.exceptions.ForbiddenException;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final static String PREFIX = "X-TOKEN ";

    @Autowired
    private JwtBean jwtBean;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null) {
            if (authorization.isEmpty() || authorization.length() < PREFIX.length())
                throw new ForbiddenException("validation.notHavePrefixAuthorizationHeader");

            String prefixAuthorizationHeader = authorization.substring(0, PREFIX.length());
            if (!prefixAuthorizationHeader.equals(PREFIX))
                throw new ForbiddenException("validation.invalidPrefixHeader");

            String token = authorization.substring(PREFIX.length());

            Jwt jwt = jwtBean.validateToken(token);
            Map claims = (Map) jwt.getBody();
            String username = (String) claims.get("sub");

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
