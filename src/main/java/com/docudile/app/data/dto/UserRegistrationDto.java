package com.docudile.app.data.dto;

import java.io.Serializable;

/**
 * Created by franc on 2/7/2016.
 */
public class UserRegistrationDto implements Serializable {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String office;

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

}
