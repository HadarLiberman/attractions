package com.example.attractionsapp;


import androidx.lifecycle.ViewModel;

import com.example.attractionsapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.annotations.NonNull;

public class UserViewModel extends ViewModel {

    public User user = null;

    public UserViewModel() {
    }

    public interface GetUserListener {
        void onComplete(User user);
    }

    public void getUser(final GetUserListener listener) {
        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        user = new User();
                        user.fromMap(task.getResult().getData());
                    }
                }
                listener.onComplete(user);
            }
        });
    }
    public void getUserById(String email,final GetUserListener listener) {
        FirebaseFirestore.getInstance().collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        user = new User();
                        user.fromMap(task.getResult().getData());
                    }
                }
                listener.onComplete(user);
            }
        });
    }

    public void logOut(){
        FirebaseAuth.getInstance().signOut();
    }

    String Name() {
        return (user != null) ? user.getName() : "User_Name";
    }

    String getUserEmail() {
        return (user != null) ? user.getId() : "User_Email";
    }


}

