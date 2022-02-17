package com.example.attractionsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.attractionsapp.Util.DeleteAttractionDialog;
import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.Attraction;

public class AttractionDetailsFragment extends Fragment implements DeleteAttractionDialog.OnSelectedListener {

    private static final String TAG = "AttractionDetailsFragment";
    boolean deleteAnswer;
    String attractionId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);

        attractionId = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
        Attraction attraction = Model.instance.getAttractionById(attractionId);

        TextView titleTv = view.findViewById(R.id.details_title_tv);
        TextView descTv = view.findViewById(R.id.details_desc_tv);
        TextView locationTv = view.findViewById(R.id.details_location_tv);
        TextView categoryTv = view.findViewById(R.id.details_category_tv);
        ImageView imageView = view.findViewById(R.id.details_image_imv);


        titleTv.setText(attraction.getTitle());
        descTv.setText(attraction.getDesc());
        locationTv.setText(attraction.getLocation());
        categoryTv.setText(attraction.getCategory());
        imageView.setImageURI(attraction.getUri());

        ImageView backBtn = view.findViewById(R.id.details_back_btn);
        backBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigateUp();
        });

        ImageView editBtn = view.findViewById(R.id.details_edit_btn);
        editBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(AttractionDetailsFragmentDirections.actionUserAttractionDetailsFragment2ToUpdateAttractionFragment(attractionId));
        });

        ImageView deleteBtn = view.findViewById(R.id.details_delete_btn);
        deleteBtn.setOnClickListener((v)->{
            DeleteAttractionDialog dialog = new DeleteAttractionDialog();
            dialog.show(getParentFragmentManager(), "DeleteAttraction");
            dialog.setTargetFragment(AttractionDetailsFragment.this, 1);
            if(deleteAnswer){
//                deleteAttraction(attractionId);
                Navigation.findNavController(v).navigateUp();
            }
        });


        return view;
    }

    private void deleteAttraction(String id){
        Model.instance.deleteAttractionbyId(id);
    }


    @SuppressLint("LongLogTag")
    @Override
    public void getAnswer(boolean answer) {
        Log.d(TAG, "getAnswer: " +answer);
        this.deleteAnswer = answer;
    }
}
