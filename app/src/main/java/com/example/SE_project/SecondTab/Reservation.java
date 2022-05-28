package com.example.SE_project.SecondTab;

public class Reservation {
    String name = "", time = "";


    public Reservation() {

    }

    public Reservation(String name, String time) {
        this.name = name;
        this.time= time;

    }

    public void setName(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setTime(String time) {
        this.time=time;

    }

    public String getTime() {
        return time;
    }
}
