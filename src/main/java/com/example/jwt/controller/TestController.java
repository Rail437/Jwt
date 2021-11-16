package com.example.jwt.controller;

import com.example.jwt.jwt.JwtProvider;
import com.example.jwt.model.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtProvider jwtProvider;

    @GetMapping("/")
    public String hello(){
        return "Hello!";
    }

    @GetMapping("/test")
    public String test(Principal principal){
        return principal.toString();
    }

    @PostMapping("/update/token")
    public TokenDto updateToken(Authentication authentication){
        String access_token = jwtProvider.createToken(authentication);
        String refresh_token = jwtProvider.createTokenOnMonth(authentication);
        return new TokenDto(access_token,refresh_token);
    }
}
