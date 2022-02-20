package com.example.attractionsapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ModelFirebase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DatabaseReference comments = FirebaseDatabase.getInstance().getReference().child("Comments");


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


    ///users
    public interface GetAllUsersListener {
        void onComplete(List<User> list);
    }

    public void getAllUsers(final GetAllUsersListener listener) {
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").get().addOnCompleteListener(task -> {
            List<User> data = new ArrayList<>();
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    User user = new User();
                    user.fromMap(doc.getData());
                    data.add(user);
                }
            }
            listener.onComplete(data);
        });
    }

    public interface GetAllCommentsListener {
        void onComplete(List<Comment> list);
    }

    public void getAllComments(final GetAllCommentsListener listener) {
        //FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("comments").get().addOnCompleteListener(task -> {
            List<Comment> data = new ArrayList<>();
            if (task.isSuccessful()) {
                for (DocumentSnapshot doc : task.getResult()) {
                    Comment comment = new Comment();
                    comment.fromMap(doc.getData());
                    data.add(comment);
                }
            }
            listener.onComplete(data);
        });
    }

    public void addComment(Comment comment, Model.AddCommentListener listener) {
        db.collection(Comment.COLLECTION_NAME).document(comment.getUserId())
                .set(comment.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "user added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "fail adding comment");
                listener.onComplete();
            }
        });
    }

    public List<Comment> getAllCommentsOnAttraction(String attractionId){
        List<Comment> result = new LinkedList<>();
        getAllComments(new GetAllCommentsListener() {
            @Override
            public void onComplete(List<Comment> list) {
                for (Comment com: list) {
                    if(com.getAttractionId().equals(attractionId)){
                        result.add(com);
                    }
                }
            }
        });
        return result;
    }


    public void addUser(User user, final Model.AddUserListener listener) {
        // FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(User.COLLECTION_NAME).document(user.getEmail())
                .set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "user added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "fail adding user");
                listener.onComplete();
            }
        });
    }
    public void updateUser(User user, Model.AddUserListener listener) {
        addUser(user,listener);
    }

    public void saveImage(Bitmap imageBitmap, String imageName, Model.SaveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child("attractions_photos/" + imageName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> listener.onComplete(null))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            Uri downloadUrl = uri;
                            listener.onComplete(downloadUrl.toString());
                        });
                    }
                });
    }

    public void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("attractions_photos").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

    public void uploadUserImage(Bitmap imageBmp, String name, final Model.UploadUserImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("userProfile_photos").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }


}
