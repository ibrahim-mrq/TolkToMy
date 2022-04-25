package com.tolk_to_my.notifications.model;

import org.jetbrains.annotations.NotNull;

public class Notification {

    private String title;
    private String body;

    public Notification() {
    }

    public Notification(String title, String body) {
        this.title = title;
        this.body = body;
    }

    @NotNull
    @Override
    public String toString() {
        return "{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
