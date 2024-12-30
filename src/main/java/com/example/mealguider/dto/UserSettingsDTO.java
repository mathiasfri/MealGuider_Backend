package com.example.mealguider.dto;

import com.example.mealguider.entity.UserSettings;

import java.util.List;

public record UserSettingsDTO(
        Long id,
        int age,
        int weight,
        int height,
        String gender,
        int workoutRate,
        String weightGoal,
        List<String> allergies,
        Long userId
) {
    public UserSettingsDTO(UserSettings userSettings) {
        this(
                userSettings.getId(),
                userSettings.getAge(),
                userSettings.getWeight(),
                userSettings.getHeight(),
                userSettings.getGender().name(),
                userSettings.getWorkoutRate(),
                userSettings.getWeightGoal().name(),
                userSettings.getAllergies(),
                userSettings.getUser().getId()
        );
    }
}

