package com.example.mealguider;

import com.example.mealguider.dto.UserDTO;
import com.example.mealguider.entity.User;
import com.example.mealguider.repository.UserRepository;
import com.example.mealguider.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        User user = new User(1L, "newUser", "1234", null, null);
        UserDTO userDTO = new UserDTO(user);

        when(userRepository.existsByEmail(userDTO.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.registerUser(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
