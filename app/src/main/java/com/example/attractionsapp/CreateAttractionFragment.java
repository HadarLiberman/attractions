package com.example.attractionsapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateAttractionFragment extends Fragment{

    List<Attraction> data;

    EditText titleEt;
    EditText descEt;
    Spinner categorySpinner;
    Spinner locationSpinner;
    Button saveBtn;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_attraction, container, false);
        //data = Model.instance.getAttractions();

        titleEt = view.findViewById(R.id.createAttraction_title_edt);
        descEt = view.findViewById(R.id.createAttraction_description_edt);
        progressBar = view.findViewById(R.id.createAttraction_progressBar);
        progressBar.setVisibility(View.GONE);

        descEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        categorySpinner = view.findViewById(R.id.createAttraction_category_spinner);

        final List<String> category=new ArrayList<String>();
        category.add("Eating and Drinking");
        category.add("Trips");
        category.add("Sport");
        category.add("Events");
        category.add("Gardens and Parks");
        category.add("Shopping");

        ArrayAdapter<String> dataAdapter_category = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, category);
        dataAdapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter_category);

        locationSpinner = view.findViewById(R.id.createAttraction_location_spinner);

        final List<String> location =new ArrayList<String>();
        location.add("South");
        location.add("Central Israel");
        location.add("North");


        ArrayAdapter<String> dataAdapter_location = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, location);
        dataAdapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(dataAdapter_location);


        saveBtn = view.findViewById(R.id.create_save_btn);

        saveBtn.setOnClickListener((v)->{
            save();
            //Navigation.findNavController(v).navigateUp();
        });



        // Inflate the layout for this fragment
        return view;
    }

    private void save(){
        progressBar.setVisibility(View.VISIBLE);
        saveBtn.setEnabled(false);
        String title = titleEt.getText().toString();
        String desc = descEt.getText().toString();
        String location = locationSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String image = ""; // TODO add image

        Attraction newAttraction = new Attraction("","",title,desc,category,location);
        Model.instance.addAttraction(newAttraction,()->{
            Navigation.findNavController(titleEt).navigateUp();

        });

        Log.d("TAG", "added new attraction: " + newAttraction);
    }

    public void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }




}