package com.example.heychat;

public class UserModel {
    String email,pass,username;

    public UserModel(String email, String pass, String username){
        this.email = email;
        this.pass = pass;
        this.username = username;
    }

    public String getEmail(){
        return email;
    }
    public String getPass(){
        return pass;
    }
    public String getUsername(){
        return username;
    }

}
