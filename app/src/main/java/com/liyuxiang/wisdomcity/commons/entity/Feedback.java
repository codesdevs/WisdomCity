package com.liyuxiang.wisdomcity.commons.entity;

public class Feedback {
    private String title;
    private String content;

    @Override
    public String toString() {
        return "Feedback{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public Feedback(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
