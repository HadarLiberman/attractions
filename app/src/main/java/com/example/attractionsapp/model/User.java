package com.example.attractionsapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    final public static String COLLECTION_NAME = "users";

    String id;
    String name;
    String email;
    String password;
    Long updateDate=new Long(0);
    List<Attraction> attractions=new ArrayList<>();


    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }


    public User(){}
    public User(String name,String email,String password){
        this.name=name;
        this.email=email;
        this.password=password;
        this.id=email;

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }



    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);

        result.put("email", email);
        result.put("password", password);
        result.put("updateDate", FieldValue.serverTimestamp());

        return result;
    }

    public void fromMap(Map<String, Object> map) {
        this.id = (String) map.get("id");

        this.email= (String) map.get("email");
        this.password= (String) map.get("password");
        Timestamp ts = (Timestamp) map.get("updateDate");
        this.updateDate = ts.getSeconds();

    }

}
