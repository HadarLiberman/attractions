package com.example.attractionsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.attractionsapp.Util.SelectPhotoDialog;
import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;





public class CreateAttractionFragment extends Fragment implements SelectPhotoDialog.OnPhotoSelectedListener{


    @SuppressLint("LongLogTag")
    @Override
    public void getImagePath(Uri imagePath) {

        bitmap = null;
        uri = imagePath;
        uploadPhoto.setImageURI(imagePath);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void getImageBitmap(Bitmap bitmap) {
        uploadPhoto.setImageBitmap(bitmap);
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
    View view;

    ImageView uploadPhoto;
    ImageButton gallery_btn;
    ImageButton camera_btn;
    Bitmap imageBitmap;


    private Bitmap bitmap = null;
    private Uri uri = null;
    private boolean hasValues = false;
    String user_id;
    private static final int REQUEST_CAMERA = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_create_attraction, container, false);
        user_id = CreateAttractionFragmentArgs.fromBundle(getArguments()).getUserId();


        titleEt = view.findViewById(R.id.signup_name_edt);
        descEt = view.findViewById(R.id.createAttraction_description_edt);


        uploadPhoto = view.findViewById(R.id.post_image);
        categorySpinner = view.findViewById(R.id.createAttraction_category_spinner);
        locationSpinner = view.findViewById(R.id.createAttraction_location_spinner);
        saveBtn = view.findViewById(R.id.login_login_btn);
        gallery_btn=view.findViewById(R.id.main_gallery_btn);
        camera_btn=view.findViewById(R.id.main_camera_btn);


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

        gallery_btn.setOnClickListener(v->{
            openGallery();
        });

        camera_btn.setOnClickListener(v->{
            openCamera();
        });
        saveBtn.setOnClickListener((v)->{

            isHasValues();
            if (hasValues) {
                Attraction attraction = save();
                if (imageBitmap == null) {
                    Model.instance.addAttraction(attraction, () -> {
                        Navigation.findNavController(v).navigateUp();
                    });
                } else {
//                Navigation.findNavController(v).navigateUp();
                    Model.instance.saveImageAttr(imageBitmap, attraction.getId() + ".jpg", url -> {
                        attraction.setUri(url);
                        Model.instance.addAttraction(attraction, () -> {
                            Snackbar mySnackbar = Snackbar.make(view, "attraction added", BaseTransientBottomBar.LENGTH_LONG);
                            mySnackbar.show();
                            Navigation.findNavController(view).navigateUp();
                        });

                    });

                }

            }
        });

        return view;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                uploadPhoto.setImageBitmap(imageBitmap);

            }
        }

    }

    private void openGallery() {

    }


    @SuppressLint("LongLogTag")
    private Attraction save(){


        String title = titleEt.getText().toString();
        String desc = descEt.getText().toString();
        String location = locationSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();

        Attraction newAttraction;
        if(uri != null){
            newAttraction = new Attraction(user_id,title,desc,category,location,uri.toString());
        } else{
            newAttraction = new Attraction(user_id,title,desc,category,location,"");
        }


        return newAttraction;

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
                && !isEmpty(categorySpinner.getSelectedItem().toString()))
               {
            hasValues = true;
        }else{
            Toast.makeText(getActivity(), "You must fill out all the fields", Toast.LENGTH_SHORT).show();
        }
    }


}