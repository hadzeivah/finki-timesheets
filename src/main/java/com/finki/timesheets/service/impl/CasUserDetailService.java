package com.finki.timesheets.service.impl;

import com.finki.timesheets.repository.UserDao;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CasUserDetailService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    private static final Logger logger = LoggerFactory.getLogger(CasUserDetailService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException {
        AttributePrincipal principal = token.getAssertion().getPrincipal();
        Map attributes = principal.getAttributes();
        String username = (String) attributes.get("username");
        com.finki.timesheets.model.User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set getAuthority(com.finki.timesheets.model.User user) {
        Set authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }
}