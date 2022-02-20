package com.example.attractionsapp.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.attractionsapp.R;

/**
 * Created by User on 10/22/2017.
 */

public class SelectPhotoDialog extends DialogFragment {

    private static final String TAG = "SelectPhotoDialog";
    private static final int PICKFILE_REQUEST_CODE = 1234;
    private static final int CAMERA_REQUEST_CODE = 4321;

    public interface OnPhotoSelectedListener{
        void getImagePath(Uri imagePath);
        void getImageBitmap(Bitmap bitmap);
    }
    OnPhotoSelectedListener mOnPhotoSelectedListener;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_selectphoto, container, false);

        TextView selectPhoto = (TextView) view.findViewById(R.id.dialogChoosePhoto);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: accessing phones memory.");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        TextView takePhoto = (TextView) view.findViewById(R.id.dialogOpenCamera);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: starting camera.");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String picturePath;
        /*
            Results when selecting a new image from memory
         */
        if(requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImageUri != null) {
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                     picturePath = cursor.getString(columnIndex);
                    mOnPhotoSelectedListener.getImageBitmap(BitmapFactory.decodeFile(picturePath));
                    cursor.close();
                }
            }
            Log.d(TAG, "onActivityResult: image uri: " + selectedImageUri);
            //send the uri to PostFragment & dismiss dialog
//            mOnPhotoSelectedListener.getImageBitmap(BitmapFactory.decodeFile(picturePath));
            getDialog().dismiss();
        }
        /*
            Results when taking a new photo with camera
         */
        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Log.d(TAG, "onActivityResult: done taking new photo");
            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            //send the bitmap to PostFragment and dismiss dialog
            mOnPhotoSelectedListener.getImageBitmap(bitmap);
            getDialog().dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        try{
            mOnPhotoSelectedListener = (OnPhotoSelectedListener) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
        super.onAttach(context);
    }
}











