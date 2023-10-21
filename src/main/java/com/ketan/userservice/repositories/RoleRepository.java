package com.ketan.userservice.repositories;

import com.ketan.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByRoleIn(List<String> roles);

    @Override
    <S extends Role> S save(S entity);
}