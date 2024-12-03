package com.example.mealguider.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Nutrition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int servings;
    private int calories;
    private int protein;
    private int fat;
    private int carbs;

    @OneToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
}
