package com.example.attractionsapp.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

public class Attraction {
    String userId;
    String id;
    String title;
    String desc;
    String category;
    String location;
    Date date;
    //ImageView imageView;
    Uri uri;
    Bitmap bitmap;

    public Attraction(String userId, String id, String title, String desc, String category, String location, Date date, Uri uri, Bitmap bitmap) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.location = location;
        this.date = date;
       // this.imageView = imageView;
        this.uri = uri;
        this.bitmap = bitmap;
    }

    public Attraction(){
        this.userId = "";
        this.id = "";
        this.title = "";
        this.desc = "";
        this.category = "";
        this.location = "";
        this.date = null;
        //this.imageView = null;
        this.uri = null;
        this.bitmap = null;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

   // public ImageView getImageView() { return imageView; }

    // public void setImageView(ImageView imageView) { this.imageView = imageView; }

    public Uri getUri() { return uri; }

    public void setUri(Uri uri) { this.uri = uri; }

    public Bitmap getBitmap() { return bitmap; }

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
}
