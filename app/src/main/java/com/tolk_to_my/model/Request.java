package com.tolk_to_my.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Request implements Serializable {

    private String patientName;
    private String patientBirthday;
    private String patientId;
    private String patientToken;
    private String disability;
    private String doctorGender;
    private String doctorName;
    private String doctorToken;
    private String parentToken;
    private String communicationType;
    private String token;

    public Request() {
    }

    @NotNull
    @Override
    public String toString() {
        return "Request{" +
                "patientName='" + patientName + '\'' +
                ", patientBirthday='" + patientBirthday + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientToken='" + patientToken + '\'' +
                ", disability='" + disability + '\'' +
                ", doctorGender='" + doctorGender + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorToken='" + doctorToken + '\'' +
                ", parentToken='" + parentToken + '\'' +
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
