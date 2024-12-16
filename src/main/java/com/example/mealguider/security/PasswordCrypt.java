package com.example.mealguider.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCrypt {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String crypt(String password) {
        return encoder.encode(password);
    }

    public static boolean check(String password, String hash) {
        return encoder.matches(password, hash);
    }
}
