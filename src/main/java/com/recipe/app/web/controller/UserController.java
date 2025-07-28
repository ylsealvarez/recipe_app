package com.recipe.app.web.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public UserDto me(Authentication authentication) {

        String username = authentication.getName();
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserDto dto = new UserDto();
        dto.setUsername(userEntity.getUsername());
        dto.setFirstname(userEntity.getFirstname());
        dto.setSurname(userEntity.getSurname());
        dto.setEmail(userEntity.getEmail());
        dto.setAddress(userEntity.getAddress());
        dto.setPhoneNumber(userEntity.getPhoneNumber());

        var roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(s -> s.replaceFirst("^ROLE_", ""))
                .collect(Collectors.toSet());
        dto.setRoles(roles);
        // …mapear más campos si los necesitas
        return dto;
    }
}
