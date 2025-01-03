package com.example.mealguider.service;

import com.example.mealguider.dto.NutritionDTO;
import com.example.mealguider.dto.RecipeDTO;
import com.example.mealguider.dto.UserSettingsDTO;
import com.example.mealguider.entity.Nutrition;
import com.example.mealguider.entity.Recipe;
import com.example.mealguider.entity.User;
import com.example.mealguider.entity.enums.Category;
import com.example.mealguider.entity.enums.Difficulty;
import com.example.mealguider.repository.RecipeRepository;
import com.example.mealguider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AIService aiService;

    public RecipeDTO generateRecipeForUser(UserSettingsDTO userSettings) {
        return aiService.generateRecipe(userSettings);
    }

    public Recipe saveRecipe(Long id, RecipeDTO recipeDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Create a new recipe
        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.name());
        recipe.setDescription(recipeDTO.description());
        recipe.setInstructions(recipeDTO.instructions());
        recipe.setCategory(recipeDTO.category() != null
                ? Category.valueOf(recipeDTO.category().toUpperCase())
                : null);
        recipe.setDifficulty(recipeDTO.difficulty() != null
                ? Difficulty.valueOf(recipeDTO.difficulty().toUpperCase())
                : null);
        recipe.setTime(recipeDTO.time());
        recipe.setIngredients(recipeDTO.ingredients());
        recipe.setUser(user);

        // Set nutrition
        Nutrition nutrition = new Nutrition();
        nutrition.setCalories(recipeDTO.nutrition().calories());
        nutrition.setProtein(recipeDTO.nutrition().protein());
        nutrition.setCarbs(recipeDTO.nutrition().carbs());
        nutrition.setFat(recipeDTO.nutrition().fat());
        nutrition.setRecipe(recipe);

        recipe.setNutrition(nutrition);

        return recipeRepository.save(recipe);
    }

    public List<RecipeDTO> getRecipesByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return recipeRepository.findAllByUser(user).stream()
                .map(RecipeDTO::new)
                .toList();
    }

    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipeRepository.deleteById(id);
    }
}
