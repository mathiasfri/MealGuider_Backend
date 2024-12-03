package com.example.mealguider.repository;

import com.example.mealguider.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsRepository extends JpaRepository<Recipe, Long> {
}