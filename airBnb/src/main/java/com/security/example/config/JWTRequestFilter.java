package com.security.example.config;

import com.security.example.entity.PropertyUser;
import com.security.example.repository.PropertyUserRepository;
import com.security.example.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

//date of class-30/03/24(august batch) and
//date of class-01/04/24(august batch)

//date of class-03/04/2024(vvi/ 3rd October batch)
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final PropertyUserRepository propertyUserRepository;

    public JWTRequestFilter(JWTService jwtService, PropertyUserRepository propertyUserRepository) {
        this.jwtService = jwtService;
        this.propertyUserRepository = propertyUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the token from the Authorization header
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer  ")) {
            // Extract the token value
            String token = tokenHeader.substring(8, tokenHeader.length() - 1);
            // Get the username from the token using the JWT service
            String username = jwtService.getUsername(token);
            // Find the user in the repository based on the username
            Optional<PropertyUser> optionalUser = propertyUserRepository.findByUsername(username);

            if (optionalUser.isPresent()) {
                //converting into an entity class
                PropertyUser propertyUser = optionalUser.get();
                // Keep track of the current user logged in
                // (work of these three lines create session)
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (propertyUser, null,
                                Collections.singleton(new SimpleGrantedAuthority(propertyUser.getUserRole())));
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } /* Create an authentication token for the server to understand the current user
             these lines of code helps to track which users are logged in, these 3 lines of code creates a session
        */
        }

        /* Continue with the filter chain */
        filterChain.doFilter(request, response);
    }
}