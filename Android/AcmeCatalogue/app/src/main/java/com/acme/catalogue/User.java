package com.acme.catalogue;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Djinodji on 2/24/2016.
 */
@Root(strict=false)
public class User {
    @Element
    private String username;
    @Element
    private String password;
    @Element
    private String email;
    @Element
    private String photo;
    @Element
    private int id;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
