package com.example.attractionsapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;

import java.util.List;

public class AttractionListRvViewModel extends ViewModel {


    LiveData<List<Attraction>> data;
   public AttractionListRvViewModel(){
        data= Model.instance.getAll();
    }


    public LiveData<List<Attraction>> getData() {
        return data;
    }
}
