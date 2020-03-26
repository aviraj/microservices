package com.poc.authservice;

import com.poc.authservice.model.LoginRequest;
import javax.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginRequest request) {
        if (request != null
                && request.getUsername() != null
                && !request.getUsername().isEmpty()
                && request.getPassword() != null
                && !request.getPassword().isEmpty()) {
            if (request.getUsername().equals("admin") && request.getPassword().equals("admin123")) {
                return new ResponseEntity<>("token123456", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/checkToken")
    ResponseEntity<String> checkToken(@RequestParam String token) {
        if (token != null
                && !token.isEmpty()) {
            if (token.equals("token123456")) {
                return new ResponseEntity<>("Token is valid", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Token is invalid", HttpStatus.BAD_REQUEST);
    }
}
