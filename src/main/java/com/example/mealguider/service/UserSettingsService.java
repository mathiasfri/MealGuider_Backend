package com.example.mealguider.service;

import com.example.mealguider.dto.UserSettingsDTO;
import com.example.mealguider.entity.User;
import com.example.mealguider.entity.UserSettings;
import com.example.mealguider.entity.enums.Gender;
import com.example.mealguider.entity.enums.WeightGoal;
import com.example.mealguider.repository.UserRepository;
import com.example.mealguider.repository.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {
    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private UserRepository userRepository;

    public UserSettingsDTO getUserSettingsByUserId(Long userId) {
        UserSettings userSettings = userSettingsRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("UserSettings not found for user with ID: " + userId));

        return new UserSettingsDTO(userSettings);
    }

    public UserSettings saveUserSettings(Long id, UserSettingsDTO userSettingsDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));


        // Fetch existing UserSettings for the user, if exists
        UserSettings userSettings = user.getSettings();
        if (userSettings == null) {
            userSettings = new UserSettings();
            userSettings.setUser(user);
        }

        userSettings.setAge(userSettingsDTO.age());
        userSettings.setWeight(userSettingsDTO.weight());
        userSettings.setHeight(userSettingsDTO.height());
        userSettings.setGender(userSettingsDTO.gender() != null
                ? Gender.valueOf(userSettingsDTO.gender().toUpperCase())
                : null);
        userSettings.setWorkoutRate(userSettingsDTO.workoutRate());
        userSettings.setWeightGoal(userSettingsDTO.weightGoal() != null
                ? WeightGoal.valueOf(userSettingsDTO.weightGoal().toUpperCase().replace(" ", "_"))
                : null);
        userSettings.setAllergies(userSettingsDTO.allergies());

        return userSettingsRepository.save(userSettings);
    }

}
