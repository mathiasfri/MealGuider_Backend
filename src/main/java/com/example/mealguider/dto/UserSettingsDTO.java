package com.example.mealguider.dto;

import com.example.mealguider.entity.User;
import com.example.mealguider.entity.UserSettings;

public record UserSettingsDTO (
        Long id,
        int age,
        int weight,
        int height,
        String gender,
        int workoutRate,
        String weightGoal,
        User user
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
                userSettings.getUser()
        );
    }
}
