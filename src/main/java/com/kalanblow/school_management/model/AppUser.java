package com.kalanblow.school_management.model;

import com.kalanblow.school_management.model.shared.enums.Role;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class AppUser implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }
}
