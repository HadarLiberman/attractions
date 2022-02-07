package com.example.attractionsapp.model;

import com.example.attractionsapp.UserAttractionDetailsFragment;

import java.util.LinkedList;
import java.util.List;

public class Model {

    public static final Model instance = new Model();

    private Model(){
        for(int i=0; i<5;i++){
            UserAttraction attraction = new UserAttraction("A3"+i,"i","Dead Sea","The geological wonder of the Dead Sea is one of the must-do tourist attractions in the Middle East."
                    ,"Trips","south","South", null);
            data.add(attraction);
        }

    }

    List<UserAttraction> data = new LinkedList<>();

    public List<UserAttraction> getAttractions(){
        return data;
    }

    public void addAttraction(UserAttraction attraction){
        data.add(attraction);
    }

    public UserAttraction getAttractionById(String attractionId) {
        for (UserAttraction s:data
        ) {
            if (s.getAttractionId().equals(attractionId)){
                return s;
            }
        }
        return null;
    }

}
