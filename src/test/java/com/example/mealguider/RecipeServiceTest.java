package com.example.mealguider;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.mealguider.dto.NutritionDTO;
import com.example.mealguider.dto.RecipeDTO;
import com.example.mealguider.entity.Recipe;
import com.example.mealguider.entity.User;
import com.example.mealguider.repository.RecipeRepository;
import com.example.mealguider.repository.UserRepository;
import com.example.mealguider.service.AIService;
import com.example.mealguider.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AIService aiService;

    @InjectMocks
    private RecipeService recipeService;

    private User user;
    private RecipeDTO recipeDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);

        NutritionDTO nutritionDTO = new NutritionDTO(1L, 2, 100, 10, 20, 30);
        recipeDTO = new RecipeDTO(1L, "Test Recipe", "Description", "Instructions", null, null, 30, List.of("Ingredient1", "Ingredient2"), nutritionDTO);
    }

    @Test
    public void testSaveRecipe() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(i -> i.getArguments()[0]);

        Recipe savedRecipe = recipeService.saveRecipe(1L, recipeDTO);

        assertNotNull(savedRecipe);
        assertEquals("Test Recipe", savedRecipe.getName());
        assertEquals(user, savedRecipe.getUser());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void testDeleteRecipe() {
        when(recipeRepository.existsById(1L)).thenReturn(true);

        recipeService.deleteRecipe(1L);

        verify(recipeRepository, times(1)).deleteById(1L);
    }
}