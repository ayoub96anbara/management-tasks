package com.anbara.jwtsecurity.dao;

import com.anbara.jwtsecurity.entities.AppUser;
import com.anbara.jwtsecurity.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByUsername(String username);
}
