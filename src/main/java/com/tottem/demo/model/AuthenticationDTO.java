package com.tottem.demo.model;

public record AuthenticationDTO(String login, String senha, UserRole role) {
    
}
