package com.example.attractionsapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Attraction {
    @PrimaryKey
    @NonNull
    String id="";
    String userId="";
    String title="";
    String desc="";
    String category="";
    String subCategory="";
    String location="";
    String date="";

    public Attraction(){}
    public Attraction(String userId, String id, String title, String desc, String category, String subCategory, String location, String date) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.subCategory = subCategory;
        this.location = location;
        this.date = date;
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
