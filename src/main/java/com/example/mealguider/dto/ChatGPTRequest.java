package com.example.mealguider.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGPTRequest {
    private String model = "gpt-4";
    private List<Message> messages;

    public ChatGPTRequest(List<Message> messages) {
        this.messages = messages;
    }

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
