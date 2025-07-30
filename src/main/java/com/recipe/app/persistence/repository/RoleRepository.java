package com.recipe.app.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.recipe.app.persistence.entity.UserRoleEntity;
import com.recipe.app.persistence.entity.UserRoleId;
import java.util.Optional;


public interface RoleRepository extends CrudRepository<UserRoleEntity, UserRoleId> {
    Optional<UserRoleEntity> findByRole(String role);
}
