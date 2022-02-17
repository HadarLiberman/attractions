package com.example.attractionsapp.Util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.attractionsapp.R;

public class DeleteAttractionDialog extends DialogFragment {

    private static final String TAG = "DeletePhotoDialog";

    public interface OnSelectedListener{
        void getAnswer(boolean answer);
    }

    OnSelectedListener onSelectedListener;
    boolean answer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_deletephoto, container, false);

        Button delete = view.findViewById(R.id.delete_deletebtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: user choose deleting attraction.");
                answer = true;
                onSelectedListener.getAnswer(true);
                getDialog().dismiss();
            }
        });

        Button cancel = view.findViewById(R.id.delete_cancelbtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: user choose cancel");
                answer = false;
                onSelectedListener.getAnswer(false);
                getDialog().dismiss();
            }
        });


        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//            //send the uri to PostFragment & dismiss dialog
//            onSelectedListener.getAnswer(answer);
//            getDialog().dismiss();
//    }

    @Override
    public void onAttach(Context context) {
        try{
            Log.d(TAG, "onAttach");
            onSelectedListener = (DeleteAttractionDialog.OnSelectedListener) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
        super.onAttach(context);
    }


}
