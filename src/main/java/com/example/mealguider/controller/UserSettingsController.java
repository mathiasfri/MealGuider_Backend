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

    @GetMapping("/{id}")
    public ResponseEntity<UserSettingsDTO> getUserSettingsByUserId(@PathVariable Long id) {
        UserSettingsDTO userSettingsDTO = userSettingsService.getUserSettingsByUserId(id);

        return ResponseEntity.ok(userSettingsDTO);
    }

    @PostMapping("/save/{id}")
    public ResponseEntity<UserSettingsDTO> saveUserSettings(
            @PathVariable Long id,
            @RequestBody UserSettingsDTO userSettingsDTO) {
        UserSettings userSettings = userSettingsService.saveUserSettings(id, userSettingsDTO);

        return ResponseEntity.ok(new UserSettingsDTO(userSettings));
    }
}
