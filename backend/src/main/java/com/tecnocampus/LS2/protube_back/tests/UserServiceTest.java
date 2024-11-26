package com.tecnocampus.LS2.protube_back.tests;

import com.tecnocampus.LS2.protube_back.application.DTO.UserDTO;
import com.tecnocampus.LS2.protube_back.application.services.UserService;
import com.tecnocampus.LS2.protube_back.domain.User;
import com.tecnocampus.LS2.protube_back.persistance.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        // Create the UserDTO object to be used for the test
        UserDTO userDTO = new UserDTO();
        userDTO.setUserNameDTO("testUser");
        userDTO.setGmailDTO("test@gmail.com");
        userDTO.setPasswordDTO("password");

        // Convert UserDTO to User manually to match the logic in userDTOToUser
        User user = new User("testUser", "test@gmail.com", "password");

        // Mock the repository's save method to return the user object
        when(userRepository.save(org.mockito.ArgumentMatchers.any(User.class))).thenReturn(user);

        // Call the method under test
        User createdUser = userService.addUser(userDTO);

        // Assert that the User object was created correctly and returned
        assertEquals("testUser", createdUser.getUserName());
        assertEquals("test@gmail.com", createdUser.getEmail());
        assertEquals("password", createdUser.getPassword());
    }
}


