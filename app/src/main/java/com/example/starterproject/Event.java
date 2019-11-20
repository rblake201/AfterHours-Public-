package com.example.starterproject;

public class Event {
    public String title;
    public String time;
    public String description;

    public Event(){
    }

    public Event(String title, String time, String description){
        this.title = title;
        this.time = time;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}