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

import android.widget.ProgressBar;

import android.widget.ImageView;

import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.attractionsapp.Util.SelectPhotoDialog;
import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




public class CreateAttractionFragment extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener{

    private static final String TAG = "CreateAttractionFragment";

    @SuppressLint("LongLogTag")
    @Override
    public void getImagePath(Uri imagePath) {
        Log.d(TAG, "getImagePath: setting the image to imageview with uri");
        //assign to global variable
        bitmap = null;
        uri = imagePath;
        uploadPhoto.setImageURI(imagePath);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void getImageBitmap(Bitmap bitmap) {
        Log.d(TAG, "getImageBitmap: setting the image to imageview");
        uploadPhoto.setImageBitmap(bitmap);
        //assign to a global variable
        uri = null;
        this.bitmap = bitmap;
        uploadPhoto.setImageBitmap(bitmap);
    }

    List<Attraction> data;

    EditText titleEt;
    EditText descEt;
    Spinner categorySpinner;
    Spinner locationSpinner;
    Button saveBtn;

    ImageView uploadPhoto;

    //vars
    private Bitmap bitmap = null;
    private Uri uri = null;
    private boolean hasValues = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_attraction, container, false);
        //data = Model.instance.getAttractions();

        titleEt = view.findViewById(R.id.createAttraction_title_edt);
        descEt = view.findViewById(R.id.createAttraction_description_edt);


        uploadPhoto = view.findViewById(R.id.post_image);
        categorySpinner = view.findViewById(R.id.createAttraction_category_spinner);
        locationSpinner = view.findViewById(R.id.createAttraction_location_spinner);
        saveBtn = view.findViewById(R.id.create_save_btn);


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



        // create location spinner
        final List<String> location =new ArrayList<String>();
        location.add("");
        location.add("South");
        location.add("Central Israel");
        location.add("North");

        ArrayAdapter<String> dataAdapter_location = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, location);
        dataAdapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(dataAdapter_location);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();

        saveBtn.setOnClickListener((v)->{

            save();
            isHasValues();
            if (hasValues){
                save();
                Navigation.findNavController(v).navigateUp();
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

        String image = ""; // TODO add image
        Attraction newAttraction;
        if(uri != null){
            newAttraction = new Attraction("7",title,desc,category,location);
        } else{
            newAttraction = new Attraction("7",title,desc,category,location);
        }


        Model.instance.addAttraction(newAttraction,()->{
            Navigation.findNavController(titleEt).navigateUp();

        });



        Log.d("TAG", "added new attraction: " + newAttraction);
    }

    private void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void init(){

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: opening dialog to choose new photo");
                SelectPhotoDialog dialog = new SelectPhotoDialog();
                dialog.show(getFragmentManager(), "SelectPhoto");
                dialog.setTargetFragment(CreateAttractionFragment.this, 1);
            }
        });
    }


    private boolean isEmpty(String string){
        return string.equals("");
    }

    private void isHasValues(){
        if(!isEmpty(titleEt.getText().toString())
                && !isEmpty(descEt.getText().toString())
                && !isEmpty(locationSpinner.getSelectedItem().toString())
                && !isEmpty(categorySpinner.getSelectedItem().toString())
                &&  (bitmap != null || uri != null)){
            hasValues = true;
        }else{
            Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        }
    }


}