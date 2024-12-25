package com.example.mealguider.service;

import com.example.mealguider.dto.RecipeDTO;
import com.example.mealguider.dto.UserSettingsDTO;
import com.example.mealguider.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AIService aiService;

    public RecipeDTO generateRecipeForUser(UserSettingsDTO userSettings) {
        return aiService.generateRecipe(userSettings);
    }
}
