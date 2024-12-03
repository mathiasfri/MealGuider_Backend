package com.example.mealguider.entity;

import com.example.mealguider.entity.enums.Gender;
import com.example.mealguider.entity.enums.WeightGoal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private int weight;
    private int height;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String workoutRate;

    @Enumerated(EnumType.STRING)
    private WeightGoal weightGoal;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
