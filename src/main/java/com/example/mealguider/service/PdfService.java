package com.example.mealguider.service;

import com.example.mealguider.entity.Recipe;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(Recipe recipe) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                // Add content to the PDF
                contentStream.showText("Recipe: " + recipe.getName());
                contentStream.newLine();
                contentStream.showText("Description: " + recipe.getDescription());
                contentStream.newLine();
                contentStream.showText("Instructions: " + recipe.getInstructions());
                contentStream.newLine();
                contentStream.showText("Category: " + recipe.getCategory());
                contentStream.newLine();
                contentStream.showText("Difficulty: " + recipe.getDifficulty());
                contentStream.newLine();
                contentStream.showText("Time: " + recipe.getTime());
                contentStream.newLine();
                contentStream.showText("Ingredients:");
                contentStream.newLine();

                for (String ingredient : recipe.getIngredients()) {
                    contentStream.showText("- " + ingredient);
                    contentStream.newLine();
                }

                // Add nutrition information
                contentStream.showText("Nutrition: ");
                contentStream.newLine();
                contentStream.showText("Calories: " + recipe.getNutrition().getCalories());
                contentStream.newLine();
                contentStream.showText("Protein: " + recipe.getNutrition().getProtein());
                contentStream.newLine();
                contentStream.showText("Carbs: " + recipe.getNutrition().getCarbs());
                contentStream.newLine();
                contentStream.showText("Fat: " + recipe.getNutrition().getFat());
                contentStream.newLine();

                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF ", e);
        }
    }
}
