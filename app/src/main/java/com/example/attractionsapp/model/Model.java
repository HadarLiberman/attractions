package com.example.attractionsapp.model;

import android.net.Uri;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();

    private Model(){
        for(int i=0; i<5;i++){
            Uri uri = Uri.parse("android.resource://com.example.attractionsapp/drawable/south");
            Attraction attraction = new Attraction("A3"+i,"i","Dead Sea","The geological wonder of the Dead Sea is one of the must-do tourist attractions in the Middle East."
                    ,"Trips","South", Calendar.getInstance().getTime(),uri ,null);
            data.add(attraction);
        }

    }

    List<Attraction> data = new LinkedList<>();

    public List<Attraction> getAttractions(){
        return data;
    }

    public void addAttraction(Attraction attraction){
        data.add(attraction);
    }

    public Attraction getAttractionById(String attractionId) {
        for (Attraction s:data
        ) {
            if (s.getId().equals(attractionId)){
                return s;
            }
        }
        return null;
    }

    public void deleteAttractionbyId(String attractionId){
        data.remove(attractionId);
    }

}
