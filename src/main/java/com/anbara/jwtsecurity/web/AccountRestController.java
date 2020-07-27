package com.anbara.jwtsecurity.web;

import com.anbara.jwtsecurity.entities.AppUser;
import com.anbara.jwtsecurity.service.AccountService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    AppUser register(@RequestBody RegisterForm registerForm) {
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword()))
        {
            throw new RuntimeException("You must confirm your password");
        }
        AppUser  user=accountService.findUserByUsername(registerForm.getUsername());
        if (user!=null) {
            throw new RuntimeException("this user already exists");
        }
        AppUser appUser=new AppUser();
        appUser.setUsername(registerForm.getUsername());
        appUser.setPassword(registerForm.getPassword());
         accountService.saveUser(appUser);
         accountService.addRoleToUser(registerForm.getUsername(),"USER");

         return appUser;
    }

}
