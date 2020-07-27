package com.anbara.jwtsecurity.dao;

import com.anbara.jwtsecurity.entities.AppRole;
import com.anbara.jwtsecurity.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole,Long> {
    Optional<AppRole> findByNameRole(String nameRole);
}
