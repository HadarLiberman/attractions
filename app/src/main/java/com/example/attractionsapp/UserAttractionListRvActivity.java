package com.example.attractionsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.Attraction;

import java.util.List;

public class UserAttractionListRvActivity extends AppCompatActivity {

    List<Attraction> data;
    MyAdapter adapter;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_attractions_list_rv);

        //data = Model.instance.getAttractions();

        RecyclerView list = findViewById(R.id.user_attractions_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));

        adapter =  new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG","row was clicked! " + position);
            }
        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView decsTv;
        ImageButton editBtn;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            //Update values for the new row
            titleTv = itemView.findViewById(R.id.details_title_tv);
            decsTv = itemView.findViewById(R.id.details_desc_tv);
            editBtn = itemView.findViewById(R.id.details_edit_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos =getAdapterPosition();

                    listener.onItemClick(pos);
                }
            });
        }
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        //create row object
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.attraction_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view , listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Attraction attraction = data.get(position);
            holder.titleTv.setText(attraction.getTitle());
            holder.decsTv.setText(attraction.getDesc());
        }

        @Override
        public int getItemCount() {
            if(data==null){
                return 1;
            }
            return data.size();
        }
    }
}
