package com.example.attractionsapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;



@Entity
public class Attraction {
    @PrimaryKey
    @NonNull
    String id="";
    String userId="";
    String title="";
    String desc="";
    String category="";

    String location="";
//    Date date="";

    public Attraction(){}
    public Attraction(String userId, String id, String title, String desc, String category,  String location) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.location = location;
//        this.date = date;
    }



    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
}
