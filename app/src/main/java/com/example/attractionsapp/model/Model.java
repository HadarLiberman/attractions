package com.example.attractionsapp.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import org.w3c.dom.Attr;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    public static final Model instance = new Model();
    ExecutorService executor = Executors.newFixedThreadPool(1);
    Handler mainThread= HandlerCompat.createAsync(Looper.getMainLooper());

    private Model(){
//        for(int i=0; i<5;i++){
//            Attraction attraction = new Attraction("A3"+i,"i","Dead Sea","The geological wonder of the Dead Sea is one of the must-do tourist attractions in the Middle East."
//                    ,"Trips","south","South", null);
//            data.add(attraction);
//        }

    }

    List<Attraction> data = new LinkedList<>();

//    public List<Attraction> getAttractions(){
//        return data;
//    }
    public interface GetAttractionsListener{
        void onComplete(List<Attraction> list);
}

    public void getAttractions(GetAttractionsListener listener){
        executor.execute(()->{
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
            AppLocalDb.db.attractionDao().insertAll(attraction);
            mainThread.post(()->{
                listener.onComplete();

            });
        });

    }

//    public void addAttraction(Attraction attraction){
//        data.add(attraction);
//    }

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
