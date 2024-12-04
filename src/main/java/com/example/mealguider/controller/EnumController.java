package com.example.mealguider.controller;

import com.example.mealguider.service.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enum")
public class EnumController {
    @Autowired
    private EnumService enumService;

    @GetMapping("/genders")
    public List<String> getGenders() {
        return enumService.getGenders();
    }

    @GetMapping("/weightGoals")
    public List<String> getWeightGoals() {
        return enumService.getWeightGoals();
    }
}
