package com.tolk_to_my.notifications.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class PushNotification {

    @SerializedName("to")
    private String token;
    @SerializedName("data")
    private Notification notification;

    public PushNotification() {
    }

    public PushNotification(Notification notification, String token) {
        this.token = token;
        this.notification = notification;
    }

    @NotNull
    @Override
    public String toString() {
        return "{" +
                "to='" + token + '\'' +
                ", data=" + notification +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}

