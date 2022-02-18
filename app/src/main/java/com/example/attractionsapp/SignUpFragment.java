package com.example.attractionsapp;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.room.PrimaryKey;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @NonNull
    EditText name;
    @NonNull
    EditText password;
    @PrimaryKey
    @NonNull
    EditText email;


    Button login_btn;
    Button submit_btn;



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
        email = view.findViewById(R.id.signup_email_edt);
        password = view.findViewById(R.id.signup_password_edt);
        login_btn = view.findViewById(R.id.signup_login_btn);
        submit_btn=view.findViewById(R.id.signup_submit_btn);

        submit_btn.setTypeface(Typeface.SANS_SERIF);
        submit_btn.setOnClickListener(v -> {
            save();
        });


        login_btn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment());
        });
        return view;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==100&&( grantResults.length>0)&&(grantResults[0]+grantResults[1]== PackageManager.PERMISSION_GRANTED)
//        ){
//            getCurrentLocation();
//        }else{
//            Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
//        }
//    }


    public void save() {
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
        Snackbar mySnackbar = Snackbar.make(view, "signUp succeed, Nice to meet you :)", BaseTransientBottomBar.LENGTH_LONG);
        mySnackbar.show();
        Model.instance.addUser(user, () -> {
            Navigation.findNavController(view).navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment());
        });
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


}