package com.shreyas.repository.interfaces;

import com.shreyas.entity.Constants.RoleType;
import com.shreyas.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleName(RoleType roleName);
}
