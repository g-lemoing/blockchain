package com.hackathon.blockchain.dto;

public class LoginResponseDto {
    private String jwtToken;
    private String username;

    public LoginResponseDto(String jwtToken, String username) {
        this.jwtToken = jwtToken;
        this.username = username;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
