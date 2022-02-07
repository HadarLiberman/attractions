package com.example.attractionsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.UserAttraction;

public class UserAttractionDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_attraction_details, container, false);

        String attractionIdId = UserAttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
        UserAttraction userAttraction = Model.instance.getAttractionById(attractionIdId);

        TextView titleTv = view.findViewById(R.id.details_title_tv);
        TextView descTv = view.findViewById(R.id.details_desc_tv);
        TextView locationTv = view.findViewById(R.id.details_location_tv);
        TextView categoryTv = view.findViewById(R.id.details_category_tv);
        TextView subCategoryTv = view.findViewById(R.id.details_subCategory_tv);

        titleTv.setText(userAttraction.getTitle());
        descTv.setText(userAttraction.getDesc());
        locationTv.setText(userAttraction.getLocation());
        categoryTv.setText(userAttraction.getCategory());
        subCategoryTv.setText(userAttraction.getSubCategory());

        ImageView backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }
}
