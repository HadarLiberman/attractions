package com.example.attractionsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.User;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;


public class UserProfileFragment extends Fragment {

    View view;
    TextView name, email;
    UserViewModel userViewModel;
    User curUser;
    Button editBtn;
    ImageView profileImage;
    EditText nameEdit;
    Button logoutBtn;
    String user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        user_id = HomeFragmentArgs.fromBundle(getArguments()).getUserId();
        editBtn = view.findViewById(R.id.profileF_editInfoBtn);
        name = view.findViewById(R.id.profileF_name);
        email = view.findViewById(R.id.profileF_mail);
        profileImage=view.findViewById(R.id.profile_imageView);
        nameEdit=view.findViewById(R.id.profile_name_et);
        logoutBtn=view.findViewById(R.id.profile_logout_btn);
        profileImage = view.findViewById(R.id.profile_imageView);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserById(user_id, new UserViewModel.GetUserListener() {
            @Override
            public void onComplete(User user) {
                curUser = user;
                name.setText((user != null) ? user.getName() : null);
                email.setText((user != null) ? user.getEmail() : null);
                if (user.getImageUrl() != null) {
                    Picasso.get()
                            .load(user.getImageUrl())
                            .into(profileImage);
                }
            }
        });

        logoutBtn.setOnClickListener((v)->{
            userViewModel.logOut();
            Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToLogInFragment());
        });

        editBtn.setOnClickListener((v)->{
//            Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToEditProfileFragment(user_id));
            save();
        });

        return view;
    }

    private void save() {
        curUser.setName(nameEdit.getText().toString());
        Model.instance.addUser(curUser, new Model.AddUserListener() {
            @Override
            public void onComplete() {
                Snackbar mySnackbar = Snackbar.make(view, "update succeed :)", BaseTransientBottomBar.LENGTH_LONG);
                mySnackbar.show();
                Navigation.findNavController(view).navigateUp();
            }

        });

    }

}