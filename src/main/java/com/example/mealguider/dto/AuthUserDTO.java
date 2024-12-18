package com.example.mealguider.dto;

import com.example.mealguider.entity.User;

public record AuthUserDTO(
        Long id,
        String email
) {
    public AuthUserDTO(User user) {
        this(
                user.getId(),
                user.getEmail()
        );
    }
}
