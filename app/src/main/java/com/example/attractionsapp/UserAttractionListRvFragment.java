package com.example.attractionsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.UserAttraction;

import java.util.List;

public class UserAttractionListRvFragment extends Fragment {


    List<UserAttraction> data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_attractions_list,container,false);
        data = Model.instance.getAttractions();

        RecyclerView list = view.findViewById(R.id.user_attractions_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                String stId = data.get(position).getAttractionId();
                Navigation.findNavController(v).navigate(UserAttractionListRvFragmentDirections.actionUserAttractionListRvFragmentToUserAttractionDetailsFragment2(stId));
            }
        });

//        ImageButton add = view.findViewById(R.id.userlistrv_addAttraction_btn);

        //TODO new Fragment

//        add.setOnClickListener(Navigation.createNavigateOnClickListener(StudentListRvFragmentDirections.actionGlobalAboutFragment()));
//        setHasOptionsMenu(true);
        return view;
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
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });

//            Button add = itemView.findViewById(R.id.userlistrv_addAttraction_btn);
//            add.setOnClickListener((v)->{
//
//            });
        }
    }

    interface OnItemClickListener{
        void onItemClick(View v,int position);
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
            View view = getLayoutInflater().inflate(R.layout.user_attraction_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view , listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            UserAttraction userAttraction = data.get(position);
            holder.titleTv.setText(userAttraction.getTitle());
            holder.decsTv.setText(userAttraction.getDesc());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.student_list_menu,menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.menu_add){
//            Log.d("TAG","ADD...");
//            return true;
//        }else {
//            return super.onOptionsItemSelected(item);
//        }
//    }

}
