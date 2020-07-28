package com.anbara.jwtsecurity.security;

import com.anbara.jwtsecurity.entities.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

//@Service
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private  AuthenticationManager authenticationManager;
//    @Autowired
//    private  ObjectMapper objectMapper;



    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser appUser = null;
        try {
            //appUser = objectMapper.readValue(request.getInputStream(), AppUser.class);
            appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        System.out.println("***********");
        System.out.println(appUser.getUsername());
        System.out.println(appUser.getPassword());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // je recupere le token
        User springUser= (User) authResult.getPrincipal();
        String jwt= Jwts.builder()
                .setSubject(springUser.getUsername())
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .claim("roles",springUser.getAuthorities())
                .compact();
        response.addHeader(SecurityConstants.HEADER_TOKEN,SecurityConstants.TOKEN_PREFIX+jwt);


    }

}
