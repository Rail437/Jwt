package com.example.jwt.exception;

public class TokenInvalidException extends RuntimeException{
    public TokenInvalidException(String message) {
        super(message);
    }
}
