package com.docudile.app.data.dto;

import java.io.Serializable;

/**
 * Created by franc on 2/8/2016.
 */
public class AuthDTO implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
