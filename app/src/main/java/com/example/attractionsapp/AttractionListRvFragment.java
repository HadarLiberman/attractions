package com.example.attractionsapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.attractionsapp.model.Model;
import com.example.attractionsapp.model.Attraction;

import java.util.List;
import java.util.UUID;

public class AttractionListRvFragment extends Fragment {

    MyAdapter adapter;
    List<Attraction> data;
    SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attractions_list_rv,container,false);
        swipeRefresh= view.findViewById(R.id.attractionlist_swiperefresh);
        swipeRefresh.setOnRefreshListener(()->refresh());
        RecyclerView list = view.findViewById(R.id.user_attractions_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                String stId = data.get(position).getId();
                Navigation.findNavController(v).navigate(AttractionListRvFragmentDirections.actionUserAttractionListRvFragmentToAttractionDetailsFragment(stId.toString()));
            }
        });

        Button add = view.findViewById(R.id.userlistrv_addAttraction_btn);
        add.setOnClickListener(Navigation.createNavigateOnClickListener(AttractionListRvFragmentDirections.actionUserAttractionListRvFragmentToCreateAttractionFragment()));

        refresh();

        return view;
    }
    private void refresh() {
        swipeRefresh.setRefreshing(true);
        Model.instance.getAttractions((list)->{
            Log.d("list---------------", list.toString());
            data=list;
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);

        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView decsTv;
        ImageButton editBtn;
        ImageView imagev;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            //Update values for the new row
            titleTv = itemView.findViewById(R.id.details_title_tv);
            decsTv = itemView.findViewById(R.id.details_desc_tv);
            editBtn = itemView.findViewById(R.id.details_edit_btn);
            imagev = itemView.findViewById(R.id.details_image_imv);

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
            View view = getLayoutInflater().inflate(R.layout.attraction_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view , listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Attraction attraction = data.get(position);
            holder.titleTv.setText(attraction.getTitle());
            holder.decsTv.setText(attraction.getDesc());
//            if(attraction.getUri() != null){
//                holder.imagev.setImageURI(attraction.getUri());
//            }else if(attraction.getBitmap() != null){
//                holder.imagev.setImageBitmap(attraction.getBitmap());
//            }
        }

        @Override
        public int getItemCount() {
            if(data==null){
                return 0;
            }
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
