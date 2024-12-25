package com.example.mealguider.dto;

import com.example.mealguider.entity.Nutrition;

public record NutritionDTO(
        Long id,
        int servings,
        int calories,
        int protein,
        int fat,
        int carbs
) {
    public NutritionDTO(Nutrition nutrition) {
        this(
                nutrition.getId(),
                nutrition.getServings(),
                nutrition.getCalories(),
                nutrition.getProtein(),
                nutrition.getFat(),
                nutrition.getCarbs()
        );
    }
}
