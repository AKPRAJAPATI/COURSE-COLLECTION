package com.publicarea.course;

public class mainModel {
    String course;
    String title;
    String uniqueKey;

    public mainModel(String course, String title) {
        this.course = course;
        this.title = title;
    }

    public mainModel() {
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
