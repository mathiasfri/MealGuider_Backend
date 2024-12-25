package com.example.mealguider.dto;

public class ChatGPTRequest {
    private String prompt;
    private String model = "gpt-4"; // Specify the GPT model to use

    public ChatGPTRequest(String prompt) {
        this.prompt = prompt;
    }

    // Getters and setters
}
