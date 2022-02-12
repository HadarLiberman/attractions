package com.example.attractionsapp.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Attraction {
    final public static String COLLECTION_NAME="attractions";
    final public static String ID="id";
    final public static String USER_ID="userId";
    final public static String TITLE="title";
    final public static String DESC="desc";
    final public static String CATEGORY="category";
    final public static String LOCATION="location";





    @PrimaryKey
    @NonNull
    String id="";

    String userId="";
    String title="";
    String desc="";
    String category="";
    String location="";


    public Attraction(){}
    public Attraction( String userId,String title, String desc, String category, String location) {
        this.id = String.valueOf((userId+ " " +title).hashCode());
        this.userId = userId;
        this.title = title;
        this.desc = desc;
        this.category = category;
        this.location = location;

    }



    //    public Attraction(){
//        this.userId = "";
//        this.id = "";
//        this.title = "";
//        this.desc = "";
//        this.category = "";
//        this.location = "";
//        this.date = null;
//    }
    public String toString(){
        return "title : " + title + "\ndesc : " + desc + "\nid : " + id;
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


    public Map<String, Object> toJson() {
        Map<String, Object> json =new HashMap<String, Object>();
        json.put(ID,id);
        json.put(USER_ID,userId);
        json.put(TITLE,title);
        json.put(DESC,desc);
        json.put(CATEGORY,category);
        json.put(LOCATION,location);

        return json;
    }

    public static Attraction create(Map<String, Object> json) {
        String id= (String) json.get(ID);
        String userId= (String) json.get(USER_ID);
        String title= (String) json.get(TITLE);
        String desc= (String) json.get(DESC);
        String category= (String) json.get(CATEGORY);
        String location= (String) json.get(LOCATION);
        Attraction attraction=new Attraction(userId,title,desc,category,location);
        return attraction;
    }
}
