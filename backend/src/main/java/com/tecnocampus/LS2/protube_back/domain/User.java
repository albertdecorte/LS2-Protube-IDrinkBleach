package com.tecnocampus.LS2.protube_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_protube")
public class User {
    private String userName;

    @Id
    private String gmail;

    private String password;

    public User(String userName, String gmail, String password) {
        this.userName = userName;
        this.gmail = gmail;
        this.password = password;
    }

    public User() {}

    // Getters i Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getGmail() { return gmail; }
    public void setGmail(String gmail) { this.gmail = gmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
