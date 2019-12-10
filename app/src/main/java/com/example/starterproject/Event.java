package com.example.starterproject;

import com.esri.arcgisruntime.geometry.Point;

public class Event {
    public String title;
    public String date;
    public String time;
    public String description;
    public String coordinates;

    public Event(){
    }

    public Event(String title, String date, String time, String description, String coordinates){
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.coordinates = coordinates;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCoordinates() { return coordinates; }

    public void setCoordinates(String coordinates) { this.coordinates = coordinates; }
}