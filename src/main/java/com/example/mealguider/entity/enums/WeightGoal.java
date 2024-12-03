package com.example.mealguider.entity.enums;

import lombok.Getter;

@Getter
public enum WeightGoal {
    LOSE_WEIGHT("Lose Weight"),
    MAINTAIN_WEIGHT("Maintain Weight"),
    GAIN_WEIGHT("Gain Weight");

    private final String displayName;

    WeightGoal(String displayName) {
        this.displayName = displayName;
    }
}
