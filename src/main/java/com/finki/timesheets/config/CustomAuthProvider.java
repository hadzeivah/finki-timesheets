package com.finki.timesheets.config;

import com.finki.timesheets.service.impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private UserServiceImpl userService;

    CustomAuthProvider(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UserDetails loadedUser = this.userService.loadUserByUsername(authentication.getName());
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return new UsernamePasswordAuthenticationToken(
                loadedUser, authentication.getCredentials().toString(), new ArrayList<>());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}