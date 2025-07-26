package com.recipe.app.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.app.persistence.entity.UserEntity;
import com.recipe.app.persistence.repository.UserRepository;
import com.recipe.app.service.dto.UserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public UserDto me(@AuthenticationPrincipal String username) {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserDto dto = new UserDto();
        dto.setUsername(userEntity.getUsername());
        dto.setFirstname(userEntity.getFirstname());
        dto.setSurname(userEntity.getSurname());
        dto.setEmail(userEntity.getEmail());
        // …mapear más campos si los necesitas
        return dto;
    }
}
