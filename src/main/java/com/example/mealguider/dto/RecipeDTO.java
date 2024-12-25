package com.example.mealguider.dto;

import com.example.mealguider.entity.Recipe;

import java.util.List;

public record RecipeDTO(
        Long id,
        String name,
        String description,
        String category,
        String difficulty,
        int time,
        List<String> ingredients,
        NutritionDTO nutrition
) {
    public RecipeDTO(Recipe recipe) {
        this(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getCategory().name(),
                recipe.getDifficulty().name(),
                recipe.getTime(),
                recipe.getIngredients(),
                new NutritionDTO(recipe.getNutrition())
        );
    }
}
