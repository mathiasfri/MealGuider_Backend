package com.example.mealguider.controller;

import com.example.mealguider.dto.AuthUserDTO;
import com.example.mealguider.dto.UserDTO;
import com.example.mealguider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserDTO> login(@RequestBody UserDTO userDTO) {
        if (userService.login(userDTO).id() != null) {
            return ResponseEntity.ok(userService.login(userDTO));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
