package com.security.example.controller;

import com.security.example.dto.JWTResponse;
import com.security.example.dto.LoginDto;
import com.security.example.dto.PropertyUserDto;
import com.security.example.entity.PropertyUser;
import com.security.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //    http://localhost:8080/api/v1/users/addUser

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody PropertyUserDto dto) {
        PropertyUser user = userService.addUser(dto);
        if (user != null) {
            return new ResponseEntity<>("SignUp successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //   http://localhost:8080/api/v1/users/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        String jwtToken = userService.verifyLogin(dto);
        if (jwtToken != null) {
            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setToken(jwtToken);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

    //   http://localhost:8080/api/v1/users/profile
    @GetMapping("/profile")
    public ResponseEntity<PropertyUser> getCurrentUserProfile(@AuthenticationPrincipal PropertyUser user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
