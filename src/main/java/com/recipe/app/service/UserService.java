package com.recipe.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recipe.app.persistence.entity.UserEntity;
import com.recipe.app.persistence.entity.UserRoleEntity;
import com.recipe.app.persistence.repository.RoleRepository;
import com.recipe.app.persistence.repository.UserRepository;
import com.recipe.app.service.dto.SignupDto;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity register(SignupDto dto) {
        // 1) Validar unicidad
        if (userRepository.existsById(dto.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DataIntegrityViolationException("Email was already used");
        }

        // 2) Crear la entidad y setear campos
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setFirstname(dto.getFirstname());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 4) Crear UserRoleEntity
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUsername(user.getUsername());
        userRole.setRole("BASIC");
        userRole.setGrantedDate(LocalDateTime.now());
        userRole.setUser(user);

        // 5) Asignar la lista de roles
        user.setRoles(List.of(userRole));

        // 6) Guardar (cascade insertará la relación)
        return userRepository.save(user);
    }   
}
