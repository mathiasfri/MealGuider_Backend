package com.example.mealguider.service;

import com.example.mealguider.dto.ChatGPTRequest;
import com.example.mealguider.dto.ChatGPTResponse;
import com.example.mealguider.dto.RecipeDTO;
import com.example.mealguider.dto.UserSettingsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class AIService {
    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    public RecipeDTO generateRecipe(UserSettingsDTO userSettings) {
        String prompt = buildPrompt(userSettings);
        List<ChatGPTRequest.Message> messages = List.of(
                new ChatGPTRequest.Message("system", "You are an assistant that generates personalized recipes."),
                new ChatGPTRequest.Message("user", prompt)
        );
        var requestBody = new ChatGPTRequest(messages);
        System.out.println("Prompt: " + prompt);

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Create HTTP entity
        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ChatGPTResponse> response = restTemplate.postForEntity(apiUrl, entity, ChatGPTResponse.class);

        if (response.getBody() == null || response.getBody().getChoices().isEmpty()) {
            throw new RuntimeException("Failed to get a response from ChatGPT");
        }

        String rawResponse = response.getBody().getChoices().getFirst().getMessage().getContent();
        System.out.println("AI Response: " + rawResponse);

        return parseResponseToRecipeDTO(rawResponse);
    }

    private RecipeDTO parseResponseToRecipeDTO(String aiResponse) {
        try {
            // Validate the response starts with '{'
            if (!aiResponse.trim().startsWith("{")) {
                throw new RuntimeException("AI response is not a valid JSON object: " + aiResponse);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(aiResponse, RecipeDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing AI response: " + e.getMessage(), e);
        }
    }

    private String buildPrompt(UserSettingsDTO userSettings) {
        return String.format("""
        You are an AI responsible for generating personalized recipes for users based on their health and dietary preferences.
        Each recipe must be nutritionally accurate and formatted to map directly to our application's `Recipe` and `Nutrition` entity structures.
        The user’s details are provided below, and you must strictly adhere to these instructions.
        All measurements should be in metric units.

        ---

        ### User Details
        - **Age**: %d
        - **Weight**: %d kg
        - **Height**: %d cm
        - **Gender**: %s
        - **Workout Rate**: %d days/week
        - **Weight Goal**: %s
        - **Allergies**: %s

        ---

        ### Recipe Generation Requirements
        1. **Recipe Name**: Create a descriptive and appealing name.
        2. **Description**: Write a short description of the dish.
        3. **Ingredients**: Provide a list of ingredients required. Avoid allergens listed under "Allergies."
        4. **Instructions**: Provide step-by-step instructions to prepare the dish.
        5. **Category**: Assign a category (e.g., "Breakfast," "Lunch," "Dinner," "Snack," or "Dessert").
        6. **Difficulty**: Assign a difficulty level (e.g., "Easy," "Medium," "Hard").
        7. **Time**: Specify the total preparation and cooking time in minutes.

        ---

        ### Nutritional Information
        1. **Servings**: Number of servings the recipe yields.
        2. **Calories**: Total calories per serving.
        3. **Protein**: Protein content per serving (grams).
        4. **Fat**: Fat content per serving (grams).
        5. **Carbs**: Carbohydrate content per serving (grams).

        Ensure the nutritional values align with the user's weight goal and workout rate:
        - **Lose Weight**: Moderate calorie count, high protein, low fat and carbs.
        - **Maintain Weight**: Balanced macronutrients.
        - **Gain Weight**: Higher calorie count with increased protein and carbs.

        ---

        ### Response Format
        Strictly return the response as a valid JSON object with the following structure:
        {
          "name": "Recipe Name",
          "description": "Short description of the dish",
          "ingredients": ["Ingredient 1", "Ingredient 2", "..."],
          "instructions": "Step-by-step preparation instructions",
          "category": "Breakfast/Lunch/Dinner/Snack",
          "difficulty": "Easy/Medium/Hard",
          "time": total_time_in_minutes,
          "nutrition": {
            "servings": number_of_servings,
            "calories": calories_per_serving,
            "protein": protein_per_serving,
            "fat": fat_per_serving,
            "carbs": carbs_per_serving
          }
        }
        Do not include any other text or comments outside of this JSON format.
    """,
                userSettings.age(),
                userSettings.weight(),
                userSettings.height(),
                userSettings.gender(),
                userSettings.workoutRate(),
                userSettings.weightGoal(),
                String.join(", ", userSettings.allergies()));
    }
}
