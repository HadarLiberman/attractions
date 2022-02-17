package com.example.attractionsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.Attraction;

import java.util.UUID;

public class AttractionDetailsFragment extends Fragment {
    TextView titleTv;
    TextView descTv;
    TextView locationTv;
    TextView categoryTv;
    ImageView backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);


        String attractionId = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
        Model.instance.getAttractionById(attractionId, new Model.GetAttractionById() {
            @Override
            public void onComplete(Attraction attraction) {
                titleTv.setText(attraction.getTitle());
                descTv.setText(attraction.getDesc());
                locationTv.setText(attraction.getLocation());
                categoryTv.setText(attraction.getCategory());
            }
        });

        // Find the view components by Id
        titleTv = view.findViewById(R.id.details_title_tv);
        descTv = view.findViewById(R.id.details_desc_tv);
        locationTv = view.findViewById(R.id.details_location_tv);
        categoryTv = view.findViewById(R.id.details_category_tv);
        backBtn = view.findViewById(R.id.details_back_btn);

        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });
        return view;
    }
}
