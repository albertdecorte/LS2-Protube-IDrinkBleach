package com.tecnocampus.LS2.protube_back.application.DTO;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    String userNameDTO;
    @Id
    String gmailDTO;
    String passwordDTO;

    public String getEmail() {
        return gmailDTO;
    }
}
