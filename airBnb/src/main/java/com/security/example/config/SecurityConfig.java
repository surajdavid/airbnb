package com.security.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig {

    // Declaring the JWTRequestFilter bean to be injected
    private final JWTRequestFilter jwtRequestFilter;

    // Constructor to inject JWTRequestFilter
    public SecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // Bean definition for SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disabling CSRF protection and CORS policy
        http.csrf().disable().cors().disable();

        // Adding JWTRequestFilter before AuthorizationFilter in the filter chain
        //It will run CustomFilterMethod first
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);

        // Configuring authorization rules
        http
                .authorizeHttpRequests().anyRequest().permitAll();
                // Allowing access to specific endpoints without authentication
//                .requestMatchers(POST,"/api/v1/users/addUser", "/api/v1/users/login")
//                .permitAll()
//                .requestMatchers(POST,"/api/v1/countries/addCountry").hasRole("ADMIN")
//                .requestMatchers(GET,"/api/v1/users/profile").hasAnyRole("ADMIN", "USER")
//                // Securing any other URLs
//                .anyRequest()
//                .authenticated();

        // Building the HttpSecurity object and returning the SecurityFilterChain
        return http.build();
    }
}
