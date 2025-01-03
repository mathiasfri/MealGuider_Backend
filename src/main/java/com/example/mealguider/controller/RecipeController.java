package com.example.mealguider.controller;

import com.example.mealguider.dto.RecipeDTO;
import com.example.mealguider.dto.UserSettingsDTO;
import com.example.mealguider.entity.Recipe;
import com.example.mealguider.service.PdfService;
import com.example.mealguider.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<RecipeDTO> generateRecipe(@RequestBody UserSettingsDTO userSettings) {
        return ResponseEntity.ok(recipeService.generateRecipeForUser(userSettings));
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<RecipeDTO> saveRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.saveRecipe(id, recipeDTO);

        return ResponseEntity.ok(new RecipeDTO(recipe));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<RecipeDTO>> listRecipes(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipesByUserId(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        byte[] pdfBytes = pdfService.generatePdf(recipe);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + recipe.getName() + ".pdf")
                .body(pdfBytes);
    }
}
