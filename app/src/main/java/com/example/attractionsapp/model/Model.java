package com.example.attractionsapp.model;

import android.net.Uri;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.attractionsapp.MyApplication;

import java.time.LocalDateTime;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public static final Model instance = new Model();



    Executor executor = Executors.newFixedThreadPool(1);
    Handler mainThread= HandlerCompat.createAsync(Looper.getMainLooper());
    public ModelFirebase modelFirebase=new ModelFirebase();

    public enum AttrationListLoadingStage{
        loading,
        loaded
    }



    MutableLiveData<AttrationListLoadingStage> attrationListLoadingStage=new MutableLiveData<AttrationListLoadingStage>();


    public LiveData<AttrationListLoadingStage> getAttrationListLoadingStage() {
        return attrationListLoadingStage;
    }

    private Model(){
         attrationListLoadingStage.setValue(AttrationListLoadingStage.loaded);
    }


    List<Attraction> data = new LinkedList<>();

    MutableLiveData<List<Attraction>> attractionsList=new MutableLiveData<List<Attraction>>();
    public LiveData<List<Attraction>> getAll(){
        if(attractionsList.getValue()==null) {
            refreshAttractionList();
        }
        return attractionsList;
    }

    public void refreshAttractionList() {
        attrationListLoadingStage.setValue(AttrationListLoadingStage.loading);

        // get last local update date
        Long lastUpdateDate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("AttractionsLastUpdateDate",0);


        modelFirebase.getAttractions(lastUpdateDate, new ModelFirebase.GetAttractionsListener() {
            @Override
            public void onComplete(List<Attraction> list) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Long lud=new Long(0);
                        for(Attraction attraction:list ) {
                            AppLocalDb.db.attractionDao().insertAll(attraction);
                            if(lud < attraction.getUpdateDate()){
                                lud=attraction.getUpdateDate();

                            }
                        }
                        MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                                .edit()
                                .putLong("AttractionsLastUpdateDate",lud)
                                .commit();
                        List<Attraction> atList=AppLocalDb.db.attractionDao().getAll();
                        attractionsList.postValue(atList);
                        attrationListLoadingStage.postValue(AttrationListLoadingStage.loaded);

                    }
                });


            }
        });

    }


    public interface AddAttractionListener{
        void onComplete();
    }
    public void addAttraction(Attraction attraction, AddAttractionListener listener){
      modelFirebase.addAttraction(attraction,listener);
    }

    public void updateAttraction(final Attraction attraction, final AddAttractionListener listener) {
        modelFirebase.updateAttraction(attraction, listener);
    }

    public void deleteAttraction(final String attractionID) {
        modelFirebase.delete(attractionID);
    }

    public interface  GetAttractionById{
        void  onComplete(Attraction attraction);
    }

    public Attraction getAttractionById(String attractionId, GetAttractionById listener) {
         modelFirebase.getAttractionById(attractionId,listener);
         return null;
    }

    public interface AddUserListener {
        void onComplete();
    }

    public void addUser(final User user, final AddUserListener listener) {
        modelFirebase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void updateUser(final User user, final AddUserListener listener) {
        modelFirebase.updateUser(user, listener);
    }


}
