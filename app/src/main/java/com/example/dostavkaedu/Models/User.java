package com.example.dostavkaedu.Models;

public class User
{
    private String name, password; // у меня сразу в нижнем было регистре

    public User() {}

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

}
