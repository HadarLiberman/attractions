package com.example.attractionsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.attractionsapp.model.User;
import com.squareup.picasso.Picasso;


public class HomeFragment extends Fragment {

    // category
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;

    //location
    ImageButton btn6;
    ImageButton btn7;
    ImageButton btn8;

    ImageView profile;
    UserViewModel userViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String user_id = HomeFragmentArgs.fromBundle(getArguments()).getUserId();
        profile=view.findViewById(R.id.home_profile_imv);
        btn0 = view.findViewById(R.id.btn_0);
        btn1 = view.findViewById(R.id.btn_1);
        btn2 = view.findViewById(R.id.btn_2);
        btn3 = view.findViewById(R.id.btn_3);
        btn4 = view.findViewById(R.id.btn_4);
        btn5 = view.findViewById(R.id.btn_5);
        btn6 = view.findViewById(R.id.btn_6);
        btn7 = view.findViewById(R.id.btn_7);
        btn8 = view.findViewById(R.id.btn_8);

        btn0.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Eating and Drinking",user_id)));
        btn1.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Trips",user_id)));
        btn2.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Sport",user_id)));
        btn3.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Events",user_id)));
        btn4.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Gardens and Parks",user_id)));
        btn5.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Shopping",user_id)));
        btn6.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("North",user_id)));
        btn7.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("Central Israel",user_id)));
        btn8.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment("South",user_id)));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserById(user_id, new UserViewModel.GetUserListener() {
            @Override
            public void onComplete(User user) {

                if (user.getImageUrl() != null) {
                    Picasso.get()
                            .load(user.getImageUrl())
                            .into(profile);
                }
            }
        });

        profile.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(user_id)));

        return view;
    }
}