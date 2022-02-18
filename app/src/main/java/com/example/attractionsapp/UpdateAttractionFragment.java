package com.example.attractionsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class UpdateAttractionFragment extends Fragment {

    private static final String TAG = "UpdateAttractionFragment";

    EditText titleEt;
    EditText descEt;
    Spinner categorySpinner;
    Spinner locationSpinner;
    Button saveBtn;
    ImageView postImage;

    Attraction attraction;

    //vars
    private Bitmap bitmap = null;
    private Uri uri = null;
    private boolean hasValues = false;
    int posCat;
    int posLoc;

    AttractionListRvViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_update_attraction, container, false);

//        String attractionId = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
//        attraction = Model.instance.getAttractionById(attractionId);

        String attractionId = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
        Log.d("TAG","attractionId" + attractionId);

        viewModel = new ViewModelProvider(this).get(AttractionListRvViewModel.class);
        List<Attraction> list = viewModel.getData().getValue();
        Log.d("TAG", "lis: " + list.toString());

        for (Attraction atr : list) {
            if (atr.getId().equals(attractionId))
                attraction = atr;
        }

        titleEt = view.findViewById(R.id.newAccount_name_edt);
        descEt = view.findViewById(R.id.createAttraction_description_edt);
        postImage = view.findViewById(R.id.post_image);
        categorySpinner = view.findViewById(R.id.createAttraction_category_spinner);
        locationSpinner = view.findViewById(R.id.createAttraction_location_spinner);
        saveBtn = view.findViewById(R.id.create_save_btn);

        titleEt.setText(attraction.getTitle());
        descEt.setText(attraction.getDesc());

        // hide keyboard
        descEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        titleEt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        // create category spinner
        final List<String> category=new ArrayList<String>();
        category.add("");
        category.add("Eating and Drinking");
        category.add("Trips");
        category.add("Sport");
        category.add("Events");
        category.add("Gardens and Parks");
        category.add("Shopping");

        ArrayAdapter<String> dataAdapter_category = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, category);
        dataAdapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter_category);
        posCat = dataAdapter_category.getPosition(attraction.getCategory());
        categorySpinner.setSelection(posCat);



        // create location spinner
        final List<String> location =new ArrayList<String>();
        location.add("");
        location.add("South");
        location.add("Central Israel");
        location.add("North");

        ArrayAdapter<String> dataAdapter_location = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, location);
        dataAdapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(dataAdapter_location);
        posLoc = dataAdapter_location.getPosition(attraction.getLocation());
        locationSpinner.setSelection(posLoc);

//        if(attraction.getUri() != null){
//            postImage.setImageURI(attraction.getUri());
//        }else if(attraction.getBitmap() != null){
//            postImage.setImageBitmap(attraction.getBitmap());
//        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        saveBtn.setOnClickListener((v)->{
            isHasValues();
            if (hasValues){
                save();
                Model.instance.addAttraction(attraction, () -> {
                    Navigation.findNavController(v).navigateUp();
                });
            }
        });

        return view;
    }

    @SuppressLint("LongLogTag")
    private void save(){

        Log.d(TAG, "onClick: attempting to post...");

        String title = titleEt.getText().toString();
        String desc = descEt.getText().toString();
        String location = locationSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();
        Date date =  Calendar.getInstance().getTime();

        attraction.setTitle(title);
        attraction.setDesc(desc);
        attraction.setLocation(location);
        attraction.setCategory(category);

        Log.d("TAG", "added new attraction on date: " + date);
    }

    private void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    private boolean isEmpty(String string){
        return string.equals("");
    }

    private void isHasValues(){
        if(!isEmpty(titleEt.getText().toString())
                && !isEmpty(descEt.getText().toString())
                && !isEmpty(locationSpinner.getSelectedItem().toString())
                && !isEmpty(categorySpinner.getSelectedItem().toString())){
            hasValues = true;
        }else{
            Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}