package com.recipe.app.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe.app.persistence.entity.UserEntity;
import com.recipe.app.persistence.entity.UserRoleEntity;
import com.recipe.app.persistence.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService{
   
    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity = this.userRepository.findById(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        
        System.out.println(userEntity);
        String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);
        
            return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(roles)
                .build();
    }
    
}
