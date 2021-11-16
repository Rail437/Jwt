package com.example.jwt.jwt;

import com.example.jwt.auth.ApplicationUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ApplicationUserService applicationUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernamePasswordAuthRequest usernamePasswordAuthRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(usernamePasswordAuthRequest.getUsername(), usernamePasswordAuthRequest.getPassword());

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            log.error("Unexpected error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtProvider.createToken(authResult);
        String tokenOnMonth = jwtProvider.createTokenOnMonth(authResult);
        applicationUserService.saveBearerToken(authResult.getName(),tokenOnMonth);
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
        response.addHeader(HttpHeaders.AUTHORIZATION, tokenOnMonth);
    }
}
