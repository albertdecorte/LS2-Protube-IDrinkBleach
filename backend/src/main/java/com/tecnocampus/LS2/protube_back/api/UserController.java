package com.tecnocampus.LS2.protube_back.api;

import com.tecnocampus.LS2.protube_back.application.DTO.UserDTO;
import com.tecnocampus.LS2.protube_back.application.services.UserService;
import com.tecnocampus.LS2.protube_back.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/uesers")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUsers(@RequestBody UserDTO user){return userService.addUser(user);}
}
