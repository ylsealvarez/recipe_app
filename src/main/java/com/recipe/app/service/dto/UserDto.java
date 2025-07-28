package com.recipe.app.service.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String firstname;
    private String surname;
    private String email;
    private String address;
    private String phoneNumber;
    private Set<String> roles; 
}
