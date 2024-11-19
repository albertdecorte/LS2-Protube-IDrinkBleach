package com.tecnocampus.LS2.protube_back.application.services;

import com.tecnocampus.LS2.protube_back.application.DTO.UserDTO;
import com.tecnocampus.LS2.protube_back.domain.User;
import com.tecnocampus.LS2.protube_back.persistance.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
    this.userRepository=userRepository;
    }

    public User userDTOToUser(UserDTO oldUser){
        User notDTO = new User(oldUser.getUserNameDTO(),oldUser.getGmailDTO(), oldUser.getPasswordDTO());
        return notDTO;
    }
//TODO:ADD USER
    public User addUser(UserDTO user) {
        User newUser = userDTOToUser(user);
        return userRepository.save(newUser);
    }
}
