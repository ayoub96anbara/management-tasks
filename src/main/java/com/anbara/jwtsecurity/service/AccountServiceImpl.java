package com.anbara.jwtsecurity.service;

import com.anbara.jwtsecurity.dao.RoleRepository;
import com.anbara.jwtsecurity.dao.UserRepository;
import com.anbara.jwtsecurity.entities.AppRole;
import com.anbara.jwtsecurity.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService{
    @Autowired
    private  UserRepository userRepository;    @Autowired

    private  RoleRepository roleRepository;    @Autowired

    private  PasswordEncoder passwordEncoder;

//    public AccountServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
     AppUser appUser=   userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username+" not found"));
     AppRole appRole= roleRepository.findByNameRole(roleName).orElseThrow(()->new RuntimeException(roleName+" not found"));
     appUser.getRoles().add(appRole);
     //userRepository.save(appUser); // comme la methode est transactional, des qu'il fait commit automatiqument il sait
                                     // qu il fait ajouter un role, automatiqument il ajout au table d'association
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
}
