package com.example.mealguider.entity;

import com.example.mealguider.entity.enums.Gender;
import com.example.mealguider.entity.enums.WeightGoal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    private int workoutRate;

    @Enumerated(EnumType.STRING)
    private WeightGoal weightGoal;

    @ElementCollection
    @CollectionTable(name = "user_settings_allergies", joinColumns = @JoinColumn(name = "user_settings_id"))
    @Column(name = "allergy")
    private List<String> allergies;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
}
