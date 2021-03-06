package com.tolk_to_my.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class FamilyMember implements Serializable {

    private String name;
    private String id;
    private String age;
    private String birthday;
    private String gender;
    private String disability;
    private String parent_token;
    private String token;

    public FamilyMember() {
    }

    @NotNull
    @Override
    public String toString() {
        return "FamilyMember{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", age='" + age + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", disability='" + disability + '\'' +
                ", parent_token='" + parent_token + '\'' +
                ", token='" + token + '\'' +
                '}';
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getParent_token() {
        return parent_token;
    }

    public void setParent_token(String parent_token) {
        this.parent_token = parent_token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
