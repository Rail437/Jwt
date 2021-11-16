package com.example.jwt.model;

import lombok.Data;
import lombok.Getter;

@Data
public class TokenDto {
    private String access_token;
    private String refresh_token;

    public TokenDto(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }
}
