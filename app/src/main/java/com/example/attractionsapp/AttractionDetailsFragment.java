package com.example.attractionsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attractionsapp.Util.DeleteAttractionDialog;
import com.example.attractionsapp.model.Comment;
import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.Attraction;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;


public class AttractionDetailsFragment extends Fragment implements DeleteAttractionDialog.OnSelectedListener {

    private static final String TAG = "AttractionDetailsFragment";
    boolean deleteAnswer;
    String attractionId;
    String user_id;

    TextView titleTv;
    TextView descTv;
    TextView locationTv;
    TextView categoryTv;
    TextView myPost;

    ImageView editBtn;
    ImageView deleteBtn;

    TextView count_comments;
    ImageButton send_comment;
    EditText comment;
    Snackbar mySnackbar;

    List<Comment> comments;
    MyAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attraction_details, container, false);

        RecyclerView list = view.findViewById(R.id.details_comments_list_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        list.setAdapter(adapter);

        attractionId = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getAttractionId();
        user_id = AttractionDetailsFragmentArgs.fromBundle(getArguments()).getUserId();
        comments = Model.instance.modelFirebase.getAllCommentsOnAttraction(attractionId);

        Log.d("TAG", "user recived email from att list " + user_id);
        Log.d("TAG", "attractionId" + attractionId);

        Model.instance.getAttractionById(attractionId, new Model.GetAttractionById() {
            @Override
            public void onComplete(Attraction attraction) {
                if(attraction == null){
                    Navigation.findNavController(view).navigateUp();
                }
                titleTv.setText(attraction.getTitle());
                descTv.setText(attraction.getDesc());
                locationTv.setText(attraction.getLocation());
                categoryTv.setText(attraction.getCategory());
                Log.d("TAG", "USER ID FROM ATRR " + attraction.getUserId());
                Log.d("TAG", "USER ID FROM NAV " + user_id);
                Log.d("TAG", "res " + (attraction.getUserId() != user_id));

                if (!attraction.getUserId().equals(user_id)) {
                    editBtn.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);
                    myPost.setVisibility(View.INVISIBLE);
                }
                deleteBtn.setOnClickListener((v) -> {
                    // TODO try to fix the dialog
//            DeleteAttractionDialog dialog = new DeleteAttractionDialog();
//            dialog.show(getParentFragmentManager(), "DeleteAttraction");
//            dialog.setTargetFragment(AttractionDetailsFragment.this, 1);
                    Model.instance.modelFirebase.delete(attraction);
                    Model.instance.deleteAttraction(attraction);
                    Navigation.findNavController(v).navigateUp();
//            if(deleteAnswer){
//                Model.instance.modelFirebase.delete(attractionId);
////                deleteAttraction(attractionId);
//                Navigation.findNavController(v).navigateUp();
//            }
                });
            }
        });

        // Find the view components by Id
        titleTv = view.findViewById(R.id.details_title_tv);
        descTv = view.findViewById(R.id.details_desc_tv);
        locationTv = view.findViewById(R.id.details_location_tv);
        categoryTv = view.findViewById(R.id.details_category_tv);

        myPost = view.findViewById(R.id.detailes_mypost_tv);
        count_comments = view.findViewById(R.id.details_comment_count);
        send_comment = view.findViewById(R.id.details_post_comments_imb);
        comment = view.findViewById(R.id.details_comments_et);


        editBtn = view.findViewById(R.id.details_edit_btn);
        editBtn.setOnClickListener((v) -> {
            Navigation.findNavController(v).navigate(AttractionDetailsFragmentDirections.actionUserAttractionDetailsFragment2ToUpdateAttractionFragment(attractionId, user_id));
        });

        deleteBtn = view.findViewById(R.id.details_delete_btn);


        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentInput = comment.getText().toString();
                if(commentInput.isEmpty()){
                    mySnackbar = Snackbar.make(view, "Please write a comment", BaseTransientBottomBar.LENGTH_LONG);
                    mySnackbar.show();
                }
                else{
                    Comment newComment = new Comment(attractionId,user_id,commentInput);
                    Model.instance.addComment(newComment, new Model.AddCommentListener() {
                        @Override
                        public void onComplete() {
                            Log.d("TAG","new commentL "+newComment);
                        }
                    });
                }
            }
        });

        return view;
    }



    @SuppressLint("LongLogTag")
    @Override
    public void getAnswer(boolean answer) {
        Log.d(TAG, "getAnswer: " + answer);
        this.deleteAnswer = answer;
    }

    // comments

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView decsTv;
        TextView dateTv;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            //Update values for the new row
            nameTv = itemView.findViewById(R.id.comment_row_name_tv);
            decsTv = itemView.findViewById(R.id.comment_row_desc_tv);
            dateTv = itemView.findViewById(R.id.comment_row_date_tv);
        }
    }

    interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(AttractionDetailsFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        //create row object
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.comment_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view , listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            comments = Model.instance.modelFirebase.getAllCommentsOnAttraction(attractionId);
            Comment comment = comments.get(position);
            holder.nameTv.setText(comment.getUserId());
            holder.decsTv.setText(comment.getComment());
            holder.dateTv.setText(comment.getCreateDate().toString());
//            if(!(attraction.getUserId().equals(user_id))){
//                holder.mypost.setVisibility(View.INVISIBLE);
//            }
        }

        @Override
        public int getItemCount(){
            if(comments == null){
                return 0;
            }
            return comments.size();
        }
    }

}
