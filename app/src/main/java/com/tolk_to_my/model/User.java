package com.tolk_to_my.model;

import org.jetbrains.annotations.NotNull;

public class User {

    private String name;
    private String specialize;
    private String id;
    private String phone;
    private String birthday;
    private String email;
    private String password;
    private String gender;
    private String type;
    private String token;

    public User() {
    }

    public User(String name, String specialize, String id, String phone, String birthday,
                String email, String password, String gender, String type, String token) {
        this.name = name;
        this.specialize = specialize;
        this.id = id;
        this.phone = phone;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.type = type;
        this.token = token;
    }

    @NotNull
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", specialize='" + specialize + '\'' +
                ", id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialize() {
        return specialize;
    }

    public void setSpecialize(String specialize) {
        this.specialize = specialize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
