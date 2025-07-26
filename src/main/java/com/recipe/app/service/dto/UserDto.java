package com.recipe.app.service.dto;

import com.recipe.app.persistence.entity.UserRoleEntity;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String firstname;
    private String surname;
    private String email;
    private UserRoleEntity roles;
}
