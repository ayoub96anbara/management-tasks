package com.anbara.jwtsecurity.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class RegisterForm {
    private String username;
    private String password;
    private String confirmPassword;

}
