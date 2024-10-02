package com.tecnocampus.LS2.protube_back.application.DTO;

import jakarta.persistence.Id;

public class UserDTO {
    String userNameDTO;
    @Id
    String gmailDTO;
    String passwordDTO;
}
