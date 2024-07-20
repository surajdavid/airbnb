package com.security.example;

import com.security.example.entity.PropertyUser;
import com.security.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootTest
class ReservationApplicationTests {

    @Test
    void contextLoads() {
    }

}
