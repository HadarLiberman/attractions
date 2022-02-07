package com.example.attractionsapp.model;

import java.time.LocalDateTime;

public class UserAttraction {
    String userId;
    String attractionId;
    String title;
    String desc;
    String category;
    String subCategory;
    String location;
    LocalDateTime date;

    public UserAttraction(String userId,String attractionId, String title, String desc, String category, String subCategory, String location, LocalDateTime date) {
        this.userId = userId;
        this.attractionId = attractionId;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.subCategory = subCategory;
        this.location = location;
        this.date = date;
    }

    public UserAttraction(){
        this.userId = "";
        this.attractionId = "";
        this.title = "";
        this.desc = "";
        this.category = "";
        this.subCategory = "";
        this.location = "";
        this.date = null;
    }

    public String getAttractionId() { return attractionId; }

    public void setAttractionId(String attractionId) { this.attractionId = attractionId; }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
