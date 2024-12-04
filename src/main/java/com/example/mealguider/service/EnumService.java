package com.example.mealguider.service;

import com.example.mealguider.entity.enums.Category;
import com.example.mealguider.entity.enums.Gender;
import com.example.mealguider.entity.enums.WeightGoal;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class EnumService {
    public List<String> getGenders() {
        return Arrays.stream(Gender.values())
                .map(Gender::getDisplayName)
                .toList();
    }

    public List<String> getWeightGoals() {
        return Arrays.stream(WeightGoal.values())
                .map(WeightGoal::getDisplayName)
                .toList();
    }
}
