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

    public UserSettings saveUserSettings(UserSettingsDTO userSettingsDTO) {
        UserSettings userSettings = mapToEntity(userSettingsDTO);

        return userSettingsRepository.save(userSettings);
    }

    private UserSettings mapToEntity(UserSettingsDTO dto) {
        User user = userRepository.findById(dto.user().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.user().getId()));

        UserSettings userSettings = new UserSettings();
        userSettings.setId(dto.id());
        userSettings.setAge(dto.age());
        userSettings.setWeight(dto.weight());
        userSettings.setHeight(dto.height());
        userSettings.setGender(dto.gender() != null ? Gender.valueOf(dto.gender()) : null);
        userSettings.setWorkoutRate(dto.workoutRate());
        userSettings.setWeightGoal(dto.weightGoal() != null ? WeightGoal.valueOf(dto.weightGoal()) : null);
        userSettings.setUser(user);

        return userSettings;
    }
}
