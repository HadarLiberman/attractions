package com.example.attractionsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.PrimaryKey;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.annotations.NonNull;


public class SignUpFragment extends Fragment {
    User user;
    View view;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final int REQUEST_CAMERA = 1;
    @NonNull
    EditText name;
    @NonNull
    EditText password;
    @PrimaryKey
    @NonNull
    EditText email;


    Button login_btn;
    Button submit_btn;
    ImageButton uploadPicButton;
    ImageView profileImage;
    Bitmap imageBitmap;

    String name_usr;
    String email_usr;
    String password_usr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Attraction - SignUp");
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_signup, container, false);
        name = view.findViewById(R.id.signup_name_edt);
        email = view.findViewById(R.id.login_email_edt);
        password = view.findViewById(R.id.login_password_edt);
        login_btn = view.findViewById(R.id.login_login_btn);
        submit_btn=view.findViewById(R.id.signup_submit_btn);
        profileImage=view.findViewById(R.id.signup_img);
        profileImage.setTag("");
        uploadPicButton=view.findViewById(R.id.signup_camera_bt);
        submit_btn.setTypeface(Typeface.SANS_SERIF);
        submit_btn.setOnClickListener(v -> {
            save();
        });

        uploadPicButton.setOnClickListener(v->{
            openCam();
        });

//        uploadPicButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editImage();
//            }
//        });
        login_btn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment());
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        return view;
    }

    private void openCam() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CAMERA);
    }

//    private void editImage() {
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Choose your profile picture");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(takePicture, 0);
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(pickPhoto, 1);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA){
            if (resultCode == Activity.RESULT_OK){
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                profileImage.setImageBitmap(imageBitmap);
            }
        }
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 0:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
//                        profileImage.setImageBitmap(selectedImage);
//                        profileImage.setTag("img");
//                    }
//                    break;
//                case 1:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Uri selectedImage = data.getData();
//                        Log.d("TAG","selectedImage "+selectedImage);
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                Log.d("TAG","picture path "+picturePath);
//
//                                profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                Log.d("TAG","picture path decode "+BitmapFactory.decodeFile(picturePath));
//                                profileImage.setTag("img");
//                                cursor.close();
//                            }
//                        }
//                    }
//                    break;
//            }
//        }
    }
    public void save() {
        Log.d("TAG","inside save");
        submit_btn.setEnabled(false);
//        login_btn.setEnabled(false);
        name_usr = name.getText().toString();
        email_usr = email.getText().toString();
        password_usr = password.getText().toString();

        if (name_usr == null ) {
            name.setError("You must enter your name");
            submit_btn.setEnabled(true);
            return;
        }

        if (password_usr.length() < 6) {
            password.setError("Password must be at least 6 characters");
            submit_btn.setEnabled(true);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email_usr,password_usr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    updateProfile();
                }
            }
        });

        user = new User(name_usr, email_usr, password_usr);
        if (imageBitmap == null){
            Model.instance.addUser(user,()->{
                Navigation.findNavController(view).navigateUp();
            });
        }else {
            Model.instance.saveImage(imageBitmap, name_usr+".jpg",url->{
                user.setImageUrl(url);
            Model.instance.addUser(user, () -> {
                Snackbar mySnackbar = Snackbar.make(view, "signUp succeed, Nice to meet you :)", BaseTransientBottomBar.LENGTH_LONG);
                mySnackbar.show();
                Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment(email_usr));
                     });
            });

        }





//        Log.d("TAG","user that entered "+user.getEmail());
//        BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
//        Log.d("BITAG", String.valueOf(drawable.getBitmap()));
//        Bitmap bitmap = drawable.getBitmap();
//        Model.instance.uploadImage(bitmap, name_usr, new Model.UploadImageListener() {
//            @Override
//            public void onComplete(String url) {
//                if (url == null) {
//                    displayFailedError();
//                } else {
//                    user.setImageUrl(url);
//                    Model.instance.addUser(user, () -> {
//                        Log.d("TAG","USER EMAIL "+email_usr);
//                        Snackbar mySnackbar = Snackbar.make(view, "signUp succeed, Nice to meet you :)", BaseTransientBottomBar.LENGTH_LONG);
//                        mySnackbar.show();
//                        Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment(email_usr));
//                    });
//                }
//            }
//        });
//        Model.instance.addUser(user, () -> {
//            Log.d("TAG","USER EMAIL "+email_usr);
//            Snackbar mySnackbar = Snackbar.make(view, "signUp succeed, Nice to meet you :)", BaseTransientBottomBar.LENGTH_LONG);
//            mySnackbar.show();
//            Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment(email_usr));
//        });

    }
    void popMsg(String Msg) {
        Snackbar mySnackbar = Snackbar.make(view, Msg, BaseTransientBottomBar.LENGTH_LONG);
        mySnackbar.show();

    }
    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    public void updateProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name_usr)
                .build();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    private void hideKeyboard(View view) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}