package com.example.mealguider.service;

import com.example.mealguider.dto.UserDTO;
import com.example.mealguider.entity.User;
import com.example.mealguider.repository.UserRepository;
import com.example.mealguider.security.PasswordCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(PasswordCrypt.crypt(userDTO.password()));

        userRepository.save(user);
    }

    public boolean login(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.email());

        if (user == null) {
            return false;
        }

        return PasswordCrypt.check(userDTO.password(), user.getPassword());
    }
}
