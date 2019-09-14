package com.finki.timesheets.model;

import java.util.Date;

public class AuthToken {

    private String token;
    private String username;
    private Date expiresAt;


    public AuthToken(){

    }

    public AuthToken(String token, String username, Date expiresAt){
        this.token = token;
        this.username = username;
        this.expiresAt = expiresAt;
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
