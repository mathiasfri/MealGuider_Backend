package com.example.mealguider.controller;

import com.example.mealguider.dto.UserSettingsDTO;
import com.example.mealguider.entity.UserSettings;
import com.example.mealguider.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSettings")
public class UserSettingsController {
    @Autowired
    private UserSettingsService userSettingsService;

    @PostMapping("/save")
    public ResponseEntity<UserSettingsDTO> saveUserSettings(
            @RequestParam String email,
            @RequestBody UserSettingsDTO userSettingsDTO) {
        UserSettings userSettings = userSettingsService.saveUserSettings(email, userSettingsDTO);

        return ResponseEntity.ok(new UserSettingsDTO(userSettings));
    }
}
