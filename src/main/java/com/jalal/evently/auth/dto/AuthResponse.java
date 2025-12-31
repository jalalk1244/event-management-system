package com.jalal.evently.auth.dto;

import com.jalal.evently.auth.entity.Role;

public class AuthResponse {
    private String token;
    private String email;
    private Role role;

    public AuthResponse() {}

    public AuthResponse(String token, String email, Role role) {
        this.token = token;
        this.email = email;
        this.role  = role;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRole() { return role.name();}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
