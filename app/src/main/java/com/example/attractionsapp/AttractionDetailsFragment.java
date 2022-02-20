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
import com.squareup.picasso.Picasso;


public class AttractionDetailsFragment extends Fragment {

    String attractionId;
    String user_id;

    TextView titleTv;
    TextView descTv;
    TextView locationTv;
    TextView categoryTv;
    TextView myPost;

    ImageView attractionImg;
    ImageView editBtn;
    ImageView deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);

        attractionId = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
        user_id = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getUserId();

        Model.instance.getAttractionById(attractionId, new Model.GetAttractionById() {
            @Override
            public void onComplete(Attraction attraction) {
                if(attraction == null){
                    Navigation.findNavController(view).navigateUp();
                }
                titleTv.setText(attraction.getTitle());
                descTv.setText(attraction.getDesc());
                locationTv.setText(attraction.getLocation());
                categoryTv.setText(attraction.getCategory());

                if (!attraction.getUserId().equals(user_id)) {
                    editBtn.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);
                    myPost.setVisibility(View.INVISIBLE);
                }
                if (attraction.getUri() != null) {
                    Picasso.get()
                            .load(attraction.getUri())
                            .into(attractionImg);
                }
                deleteBtn.setOnClickListener((v) -> {

                    Model.instance.modelFirebase.delete(attraction);
                    Model.instance.deleteAttraction(attraction);
                    Navigation.findNavController(v).navigateUp();

                });
            }
        });


        titleTv = view.findViewById(R.id.details_title_tv);
        descTv = view.findViewById(R.id.details_desc_tv);
        locationTv = view.findViewById(R.id.details_location_tv);
        categoryTv = view.findViewById(R.id.details_category_tv);
        attractionImg=view.findViewById(R.id.details_image_imv);
        myPost = view.findViewById(R.id.detailes_mypost_tv);
        editBtn = view.findViewById(R.id.details_edit_btn);

        editBtn.setOnClickListener((v) -> {
            Navigation.findNavController(v).navigate(AttractionDetailsFragmentDirections.actionUserAttractionDetailsFragment2ToUpdateAttractionFragment(attractionId, user_id));
        });

        deleteBtn = view.findViewById(R.id.details_delete_btn);


        return view;
    }


}
