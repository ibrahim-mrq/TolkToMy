package com.tolk_to_my.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Patient implements Serializable {

    private String patientName;
    private String patientBirthday;
    private String disability;
    private String doctorGender;
    private String doctorName;
    private String doctorToken;
    private String parentToken;
    private String patientToken;
    private String communicationType;
    private String token;

    public Patient() {
    }

    @NotNull
    @Override
    public String toString() {
        return "Order{" +
                "patientName='" + patientName + '\'' +
                "patientBirthday='" + patientBirthday + '\'' +
                "disability='" + disability + '\'' +
                ", doctorGender='" + doctorGender + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorToken='" + doctorToken + '\'' +
                ", parentToken='" + parentToken + '\'' +
                ", patientToken='" + patientToken + '\'' +
                ", communicationType='" + communicationType + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(String patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getDoctorGender() {
        return doctorGender;
    }

    public void setDoctorGender(String doctorGender) {
        this.doctorGender = doctorGender;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorToken() {
        return doctorToken;
    }

    public void setDoctorToken(String doctorToken) {
        this.doctorToken = doctorToken;
    }

    public String getParentToken() {
        return parentToken;
    }

    public void setParentToken(String parentToken) {
        this.parentToken = parentToken;
    }

    public String getPatientToken() {
        return patientToken;
    }

    public void setPatientToken(String patientToken) {
        this.patientToken = patientToken;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
