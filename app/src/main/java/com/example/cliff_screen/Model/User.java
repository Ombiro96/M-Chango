package com.example.cliff_screen.Model;

public class User {
    String name, id, email, idNumber, phoneNumber, type, Profile_picture_url;

    public User() {
    }

    public User(String name, String id, String email, String idNumber, String phoneNumber, String type, String Profile_picture_url) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.Profile_picture_url = Profile_picture_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile_picture_url() {
        return Profile_picture_url;
    }

    public void setProfile_picture_url(String profile_picture_url) {
        this.Profile_picture_url = Profile_picture_url;
    }
}
