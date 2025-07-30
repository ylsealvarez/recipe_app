package com.recipe.app.persistence.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.recipe.app.persistence.entity.UserEntity;

public interface UserRepository extends ListCrudRepository<UserEntity, String>{
    boolean existsByEmail(String email);   
}
