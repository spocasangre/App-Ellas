package com.example.project.models.dtos;

import javax.validation.constraints.NotNull;

public class UserLoginDTO {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String fToken;

    public UserLoginDTO(String email, String password, String ftoken) {
        super();
        this.email = email;
        this.password = password;
        this.fToken = ftoken;
    }

    public String getfToken() {
        return this.fToken;
    }

    public void setfToken(String fToken) {
        this.fToken = fToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
