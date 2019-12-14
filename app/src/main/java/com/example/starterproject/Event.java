package com.example.starterproject;


public class Event {
    public String title;   // Stores the Title of the Event
    public String date;    // Stores the Date of the Event
    public String time;    // Stores the Time of the Event
    public String description;   // Stores the Description of the Event
    public String coordinates;   // Stores the Coordinates of the Event

    // Default Constructor
    public Event(){
    }

    // Non-Default Constructor
    public Event(String title, String date, String time, String description, String coordinates){
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.coordinates = coordinates;
    }

    //GETTERS AND SETTERS FOR THE CLASS

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