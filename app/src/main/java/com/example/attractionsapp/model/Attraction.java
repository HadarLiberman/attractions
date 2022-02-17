package com.example.attractionsapp.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import android.graphics.Bitmap;
import android.net.Uri;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Attraction {

    final public static String COLLECTION_NAME = "attractions";
    final public static String ID = "id";
    final public static String USER_ID = "userId";
    final public static String TITLE = "title";
    final public static String DESC = "desc";
    final public static String CATEGORY = "category";
    final public static String LOCATION = "location";
    final public static String URI = "uri";
    final public static String BITMAP = "bitmap";


    @PrimaryKey
    @NonNull
    String id;

    String userId = "";
    String title = "";
    String desc = "";
    String category = "";
    String location = "";
    //Uri uri = null;
    //Bitmap bitmap = null;

    public Attraction() {
    }

    public Attraction(String userId, String title, String desc, String category, String location) {
        //this.id = String.valueOf((userId + " " + title).hashCode());
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.location = location;
        //this.uri = uri;
        //this.bitmap = bitmap;

    }


    public String toString() {
        return "title : " + title + "\ndesc : " + desc + "\nid : " + id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

//    public Uri getUri() {
//        return uri;
//    }
//
//    public void setUri(Uri uri) {
//        this.uri = uri;
//    }
//
//    public Bitmap getBitmap() {
//        return bitmap;
//    }
//
//    public void setBitmap(Bitmap bitmap) {
//        this.bitmap = bitmap;
//    }


    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put(ID, id);
        json.put(USER_ID, userId);
        json.put(TITLE, title);
        json.put(DESC, desc);
        json.put(CATEGORY, category);
        json.put(LOCATION, location);
        //json.put(URI, uri);
        //json.put(BITMAP, bitmap);
        return json;
    }

    public static Attraction create(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String userId = (String) json.get(USER_ID);
        String title = (String) json.get(TITLE);
        String desc = (String) json.get(DESC);
        String category = (String) json.get(CATEGORY);
        String location = (String) json.get(LOCATION);
        //Uri uri = (Uri) json.get(URI);
       // Bitmap bitmap = (Bitmap) json.get(BITMAP);

        Attraction attraction = new Attraction(userId, title, desc, category, location);
        return attraction;
    }
}

   // public ImageView getImageView() { return imageView; }

    // public void setImageView(ImageView imageView) { this.imageView = imageView; }


