package com.example.lab5;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // проверяет, что поля текущего класса
    // полностью соответсвуют полям передаваемого класса в параметре
    public boolean isEqual(User user){
        return this.name.equals(user.name) && this.password.equals(user.password);
    }
}