package com.skyman.weighttrainingpro.management.user.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    // instant variable
    private long count;             // 0. count
    private String name;            // 1. name
    private String email;           // 2. email
    private String salt;            // 3. salt
    private String password;        // 4. password

    public User() {

    }

    public User(long count, String name, String email, String salt, String password) {
        this.count = count;
        this.name = name;
        this.email = email;
        this.salt = salt;
        this.password = password;
    }

    // method : getter, setter
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
