package com.example.heychat;

public class msgModelClass {
    String message;
    String senderemail;

    public msgModelClass() {
    }

    public msgModelClass(String message, String senderemail) {
        this.message = message;
        this.senderemail = senderemail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderemail;
    }

    public void setSenderid(String senderemail) {
        this.senderemail = senderemail;
    }
}
