package com.example.attractionsapp.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public interface GetAttractionsListener{
        void onComplete(List<Attraction> list);
    }
    public void getAttractions(Long lastUpdateDate, GetAttractionsListener listener) {
     db.collection(Attraction.COLLECTION_NAME)
             .whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdateDate,0))
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     List<Attraction> list=new LinkedList<Attraction>();
                     if(task.isSuccessful()){
                         for(QueryDocumentSnapshot doc: task.getResult()){
                             Attraction attraction=Attraction.create(doc.getData());
                             if(attraction!=null){
                                 list.add(attraction);
                             }

                         }

                     }
                     listener.onComplete(list);
                 }
             });

    }

    public void addAttraction(Attraction attraction, Model.AddAttractionListener listener) {

        Map<String, Object> json = attraction.toJson();
        db.collection(Attraction.COLLECTION_NAME)
                .document(attraction.getId().toString())
                .set(json)
                .addOnSuccessListener(unused ->listener.onComplete())
                .addOnFailureListener(e->listener.onComplete());

    }

    public void getAttractionById(String attractionId, Model.GetAttractionById listener) {
        db.collection(Attraction.COLLECTION_NAME)
                .document(attractionId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot >() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Attraction attraction = null;
                        if (task.isSuccessful() & task.getResult()!=null){
                                attraction = Attraction.create(task.getResult().getData());
                                Log.d("TAG","PICK ATT");
                            }
                        listener.onComplete(attraction);
                    }
                });

    }
}
