package com.security.example.service;

import com.security.example.dto.LoginDto;
import com.security.example.dto.PropertyUserDto;
import com.security.example.entity.PropertyUser;
import com.security.example.repository.PropertyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PropertyUserRepository userRepository;
    @Autowired
    private JWTService jwtService;


    public PropertyUser addUser(PropertyUserDto dto) {
        PropertyUser user = new PropertyUser();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        //user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(10)));
        
        user.setUserRole(dto.getUserRole());
        return userRepository.save(user);
    }

    public String verifyLogin(LoginDto dto) {
        Optional<PropertyUser> optionalUser = userRepository.findByUsername(dto.getUsername());
        if (optionalUser.isPresent()) {
            PropertyUser user = optionalUser.get();
            if (BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            	String jwtToken = jwtService.generateToken(user);
                return jwtToken;
            }
        }
        return null;
    }
}
