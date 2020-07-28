package com.anbara.jwtsecurity.service;

import com.anbara.jwtsecurity.entities.AppRole;
import com.anbara.jwtsecurity.entities.AppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface AccountService {
    AppUser saveUser(AppUser user);
    AppRole saveRole(AppRole role);
    void addRoleToUser(String username,String roleName);
    AppUser findUserByUsername(String username);


}
