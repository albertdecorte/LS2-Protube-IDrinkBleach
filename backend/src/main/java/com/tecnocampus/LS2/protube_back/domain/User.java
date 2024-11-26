package com.tecnocampus.LS2.protube_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_protube")
public class User {
    private String userName;

    @Id
    private String email;

    private String password;

    public User(String userName, String gmail, String password) {
        this.userName = userName;
        this.email = gmail;
        this.password = password;
    }

    public User() {}

    // Getters i Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String gmail) { this.email = gmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
