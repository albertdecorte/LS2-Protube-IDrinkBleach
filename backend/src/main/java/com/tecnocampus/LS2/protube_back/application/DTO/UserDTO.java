package com.tecnocampus.LS2.protube_back.application.DTO;

import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class UserDTO {
    String userNameDTO;
    @Id
    String gmailDTO;
    String passwordDTO;
}
