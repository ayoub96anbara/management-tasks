package com.anbara.jwtsecurity.security;

import com.anbara.jwtsecurity.dao.UserRepository;
import com.anbara.jwtsecurity.entities.AppUser;
import com.anbara.jwtsecurity.service.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private final AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=accountService.findUserByUsername(username);

        Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getRoles().forEach(
                r->authorities.add(new SimpleGrantedAuthority(r.getNameRole()))
        );
        return new User(appUser.getUsername(),appUser.getPassword(),authorities);
    }
}
