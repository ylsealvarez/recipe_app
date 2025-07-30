package com.recipe.app.service.dto;

import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String firstname;
    private String surname;
    private String email;
    private String address;
    private String phoneNumber;   
}
