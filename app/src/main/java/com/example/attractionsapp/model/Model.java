package com.example.attractionsapp.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public static final Model instance = new Model();
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Handler mainThread= HandlerCompat.createAsync(Looper.getMainLooper());

//    private Model(){
//        for(int i=0; i<5;i++){
//            Attraction attraction = new Attraction("A3"+i,"i","Dead Sea","The geological wonder of the Dead Sea is one of the must-do tourist attractions in the Middle East."
//                    ,"Trips","South");
//            data.add(attraction);
//        }
//
//    }

    List<Attraction> data = new LinkedList<>();

//    public List<Attraction> getAttractions(){
//        return data;
//    }
//
//    public void addAttraction(Attraction attraction){
//        data.add(attraction);
//    }

    public interface GetAttractionsListener{
        void onComplete(List<Attraction> list);
    }

    public void getAttractions(GetAttractionsListener listener){

        executor.execute(()->{
//            try{
//                Thread.sleep(3000);
//            } catch(InterruptedException e){
//                e.printStackTrace();
//            }
            List<Attraction> list= AppLocalDb.db.attractionDao().getAll();
            mainThread.post(()->{
                listener.onComplete(list);

            });
        });

    }


    public interface AddAttractionListener{
        void onComplete();
    }
    public void addAttraction(Attraction attraction, AddAttractionListener listener){
        executor.execute(()->{
//            try{
//                Thread.sleep(3000);
//            } catch(InterruptedException e){
//                e.printStackTrace();
//            }
          //Attraction second=new Attraction("1","1","hiii","iii","wer","wer");
            AppLocalDb.db.attractionDao().insertAll(attraction); ;
            mainThread.post(()->{
                Log.d("TAG", "added new attraction");
                listener.onComplete();

            });
        });

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

}
