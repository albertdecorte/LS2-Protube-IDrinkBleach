/*package com.tecnocampus.LS2.protube_back.tests;

import com.tecnocampus.LS2.protube_back.api.UserController;
import com.tecnocampus.LS2.protube_back.application.DTO.UserDTO;
import com.tecnocampus.LS2.protube_back.application.services.UserService;
import com.tecnocampus.LS2.protube_back.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserNameDTO("testUser");
        userDTO.setGmailDTO("test@gmail.com");
        userDTO.setPasswordDTO("password");

        User user = new User("testUser", "test@gmail.com", "password");

        when(userService.addUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userNameDTO\": \"testUser\", \"gmailDTO\": \"test@gmail.com\", \"passwordDTO\": \"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("userName").value("testUser"));
    }
}*/

