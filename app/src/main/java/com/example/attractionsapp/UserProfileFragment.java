package com.example.attractionsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.User;
import com.google.firebase.auth.FirebaseAuth;


import java.util.List;


public class UserProfileFragment extends Fragment {

    View view;
    TextView name, email;
    UserViewModel userViewModel;
    Button editBtn;
    ImageView profileImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("AnyGift - Profile");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        editBtn = view.findViewById(R.id.profileF_editInfoBtn);
        name = view.findViewById(R.id.profileF_name);
        email = view.findViewById(R.id.profileF_mail);
        profileImage=view.findViewById(R.id.profileF_imageView);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser(new UserViewModel.GetUserListener() {
            @Override
            public void onComplete(User user) {
                name.setText((user!=null)?user.getName():"null");
                email.setText((user!=null)?user.getEmail():"null");

//                if(user.getImageUrl()!=null) {
//                    Picasso.get().load(user.getImageUrl()).into(profileImage);
//                    profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    profileImage.setClipToOutline(true);
//                }


                List<Attraction> attractionsList = Model.instance.getAll().getValue();
                String userID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (Attraction attr: attractionsList
                ) {
                    if(attr.getUserId().compareTo(userID)==0) {
                        //bring user atraction
                    }
                }


            }
        });

//        editBtn.setOnClickListener((v) -> {
//            Navigation.findNavController(v).navigate(R.id.action_userProfileFragment_to_editProfileFragment);
//        });

        return view;
    }

}