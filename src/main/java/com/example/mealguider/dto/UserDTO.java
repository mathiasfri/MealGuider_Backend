package com.example.mealguider.dto;

import com.example.mealguider.entity.Recipe;
import com.example.mealguider.entity.User;
import com.example.mealguider.entity.UserSettings;

import java.util.List;

public record UserDTO(
        Long id,
        String email,
        String password,
        List<String> allergies,
        UserSettings settings,
        List<Recipe> recipes
) {
    public UserDTO(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getAllergies(),
                user.getSettings(),
                user.getRecipes()
        );
    }
}

