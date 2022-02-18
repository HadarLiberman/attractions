package com.example.attractionsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


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

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String user_id = HomeFragmentArgs.fromBundle(getArguments()).getUserId();

        btn0 = view.findViewById(R.id.btn_0);
        btn1 = view.findViewById(R.id.btn_1);
        btn2 = view.findViewById(R.id.btn_2);
        btn3 = view.findViewById(R.id.btn_3);
        btn4 = view.findViewById(R.id.btn_4);
        btn5 = view.findViewById(R.id.btn_5);
        btn6 = view.findViewById(R.id.btn_6);
        btn7 = view.findViewById(R.id.btn_7);
        btn8 = view.findViewById(R.id.btn_8);

        btn0.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(0,user_id)));
        btn1.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(1,user_id)));
        btn2.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(2,user_id)));
        btn3.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(3,user_id)));
        btn4.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(4,user_id)));
        btn5.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(5,user_id)));
        btn6.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(6,user_id)));
        btn7.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(7,user_id)));
        btn8.setOnClickListener(Navigation.createNavigateOnClickListener(HomeFragmentDirections.actionHomeFragmentToAttractionListRvFragment(8,user_id)));

        Log.d("TAG", "finish load btns");
        return view;
    }
}