package com.example.heychat;

public class Users {
    String mail, Username, password, userId, lastMessage, status;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(Users.class)
    }

    public Users(String id, String Username, String mail, String password, String status) {
        this.userId = id;
        this.Username = Username;
        this.mail = mail;
        this.password = password;
        this.status = status;
    }
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userId) {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
