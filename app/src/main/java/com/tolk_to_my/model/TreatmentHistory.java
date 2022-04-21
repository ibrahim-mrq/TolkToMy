package com.tolk_to_my.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class TreatmentHistory implements Serializable {

    private String sugar = "";
    private String pressure = "";
    private String heart = "";
    private String weight = "";
    private String height = "";
    private String temperature = "";

    private String notes = "";
    private String tips = "";
    private String treatment = "";

    private String patientName = "";
    private String patientBirthday = "";
    private String patientId = "";
    private String patientToken = "";

    private String disability = "";

    private String doctorGender = "";
    private String doctorName = "";
    private String doctorToken = "";

    private String createData = "";
    private String parentToken = "";
    private String token = "";

    public TreatmentHistory() {
    }

    @NotNull
    @Override
    public String toString() {
        return "Patient{" +
                ", sugar='" + sugar + '\'' +
                ", pressure='" + pressure + '\'' +
                ", heart='" + heart + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", temperature='" + temperature + '\'' +
                ", notes='" + notes + '\'' +
                ", tips='" + tips + '\'' +
                ", treatment='" + treatment + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientBirthday='" + patientBirthday + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientToken='" + patientToken + '\'' +
                ", disability='" + disability + '\'' +
                ", doctorGender='" + doctorGender + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorToken='" + doctorToken + '\'' +
                ", createData='" + createData + '\'' +
                ", parentToken='" + parentToken + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
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

    public String getPatientToken() {
        return patientToken;
    }

    public void setPatientToken(String patientToken) {
        this.patientToken = patientToken;
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

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
    }

    public String getParentToken() {
        return parentToken;
    }

    public void setParentToken(String parentToken) {
        this.parentToken = parentToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
