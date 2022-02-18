package com.example.attractionsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.annotations.NonNull;


public class LogInFragment extends Fragment {

    EditText email_et,password_et;
    Button login_btn;
    String email_user;
    String password_user;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Snackbar mySnackbar;
    View view;

    private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    ActionCodeSettings actionCodeSettings =
            ActionCodeSettings.newBuilder()
                    // URL you want to redirect back to. The domain (www.example.com) for this
                    // URL must be whitelisted in the Firebase Console.
                    .setUrl("https://www.example.com/finishSignUp?cartId=1234")
                    // This must be true
                    .setHandleCodeInApp(true)
                    .setIOSBundleId("com.example.ios")
                    .setAndroidPackageName(
                            "com.example.attractionsapp",
                            true, /* installIfNotAvailable */
                            "12"    /* minimumVersion */)
                    .build();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_log_in, container, false);

        email_et=view.findViewById(R.id.signup_email_edt);
        password_et=view.findViewById(R.id.signup_password_edt);
        login_btn=view.findViewById(R.id.signup_login_btn);

        login_btn.setOnClickListener(v -> {checkUser();});

        return view;
    }

    public void checkUser() {

        email_user=email_et.getText().toString();
        password_user=password_et.getText().toString();
        if(TextUtils.isEmpty(email_user) && email_user.matches(emailPattern))
        {
            email_et.setError("please enter  correct   email");
            return;
        }
        if(TextUtils.isEmpty(password_user))
        {
            password_et.setError("please enter correct  password");
            return;
        }



        //authenticate the user
        mAuth.signInWithEmailAndPassword(email_user,password_user).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
//                    signIn_btn.setEnabled(false);
//                    signUp_btn.setEnabled(false);
//                    forgotP_btn.setEnabled(false);
//                    pb.setVisibility(View.INVISIBLE);
                    mySnackbar = Snackbar.make(view, "Login successful :)", BaseTransientBottomBar.LENGTH_LONG);
                    mySnackbar.show();
                    Log.d("TAG","login successful");

                    Navigation.findNavController(view).navigate(R.id.homeFragment);
                }
                else
                    //Log.d("TAG","Login failed");
                    Toast.makeText(getContext(),"Error! "+ task.getException().getMessage() ,Toast.LENGTH_LONG).show();
//                pb.setVisibility(View.INVISIBLE);
            }
        });
    }
}