package com.tecnocampus.LS2.protube_back.api;

import com.tecnocampus.LS2.protube_back.application.DTO.UserDTO;
import com.tecnocampus.LS2.protube_back.application.services.UserService;
import com.tecnocampus.LS2.protube_back.domain.User;
import com.tecnocampus.LS2.protube_back.persistance.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepo) {
        this.userService = userService;

    }

    @PostMapping
    public ResponseEntity<?> addUsers(@RequestBody UserDTO userDTO) {
        if (userService.userExists(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("L'usuari ja existeix.");
        }
        userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> userExists(@PathVariable String email) {
        boolean exists = userService.userExists(email);
        return ResponseEntity.ok(exists);
    }
}
