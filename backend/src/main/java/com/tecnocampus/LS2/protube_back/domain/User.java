package com.tecnocampus.LS2.protube_back.domain;

import jakarta.persistence.Id;

public class User {
    String userName;
    @Id
    String gmail;
    String password;
}
