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
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ModelFirebase() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }


    public interface GetAttractionsListener {
        void onComplete(List<Attraction> list);
    }

    public void getAttractions(Long lastUpdateDate, GetAttractionsListener listener) {
        db.collection(Attraction.COLLECTION_NAME)
//             .whereGreaterThanOrEqualTo("updateDate",new Timestamp(lastUpdateDate,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Attraction> list = new LinkedList<Attraction>();
                        if (task.isSuccessful()) {
                            Log.d("TAG", "is successful");
                            for (DocumentSnapshot doc : task.getResult()) {
                                Log.d("TAG", "get result " + doc.getData());
                                Attraction attraction = Attraction.create(doc.getData());
                                if (attraction != null) {
                                    list.add(attraction);
                                }
                            }
                        }
                        listener.onComplete(list);
                    }
                });
    }

    public void updateAttraction(Attraction attraction, Model.AddAttractionListener listener) {
        addAttraction(attraction, listener);
    }


    public void addAttraction(Attraction attraction, Model.AddAttractionListener listener) {

        Map<String, Object> json = attraction.toJson();
        db.collection(Attraction.COLLECTION_NAME)
                .document(attraction.getId().toString())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }

    public void getAttractionById(String attractionId, Model.GetAttractionById listener) {
        db.collection(Attraction.COLLECTION_NAME)
                .document(attractionId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Attraction attraction = null;
                        if (task.isSuccessful() & task.getResult() != null) {
                            attraction = Attraction.create(task.getResult().getData());
                            Log.d("TAG", "PICK ATT");
                        }
                        listener.onComplete(attraction);
                    }
                });

    }

    public void delete(String attractionId) {
        FirebaseFirestore.getInstance().collection(Attraction.COLLECTION_NAME).document(attractionId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }
}
