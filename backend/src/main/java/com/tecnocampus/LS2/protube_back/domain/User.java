package com.tecnocampus.LS2.protube_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {
    String userName;
    @Id
    String gmail;
    String password;

    public User(String userName, String gmail, String password) {
        this.userName = userName;
        this.gmail = gmail;
        this.password = password;
    }

    public User() {}
}
