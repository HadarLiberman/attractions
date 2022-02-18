package com.example.attractionsapp;

import android.content.Context;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.attractionsapp.model.Attraction;
import com.example.attractionsapp.model.Model;

import java.util.List;
import java.util.UUID;

public class AttractionListRvFragment extends Fragment {

    AttractionListRvViewModel viewModel;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    String user_id;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel=new ViewModelProvider(this).get(AttractionListRvViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attractions_list_rv,container,false);
        user_id = AttractionListRvFragmentArgs.fromBundle(getArguments()).getUserId();
        Log.d("TAG","user recived email from att home "+user_id);
        Integer selected_category = AttractionListRvFragmentArgs.fromBundle(getArguments()).getSelectedCategory();
        Log.d("TAG","SELECTED CATEGORY : "+ selected_category);


        swipeRefresh= view.findViewById(R.id.attractionlist_swiperefresh);
        swipeRefresh.setOnRefreshListener(()->Model.instance.refreshAttractionList());
        RecyclerView list = view.findViewById(R.id.user_attractions_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));


//         MyAdapter adapter = new MyAdapter();
//         adapter.notifyDataSetChanged();

        adapter = new MyAdapter();
        list.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                String stId = viewModel.getData().getValue().get(position).getId();
                Navigation.findNavController(v).navigate(AttractionListRvFragmentDirections.actionUserAttractionListRvFragmentToAttractionDetailsFragment(stId,user_id));
            }
        });

        Button add = view.findViewById(R.id.userlistrv_addAttraction_btn);
        add.setOnClickListener(Navigation.createNavigateOnClickListener(AttractionListRvFragmentDirections.actionUserAttractionListRvFragmentToCreateAttractionFragment(user_id)));
        viewModel.getData().observe(getViewLifecycleOwner(),list1->refresh());
        swipeRefresh.setRefreshing(Model.instance.getAttrationListLoadingStage().getValue()==Model.AttrationListLoadingStage.loading);
        Model.instance.getAttrationListLoadingStage().observe(getViewLifecycleOwner(), attrationListLoadingStage -> {
         if (attrationListLoadingStage==Model.AttrationListLoadingStage.loading){
            swipeRefresh.setRefreshing(true);

         }else{
            swipeRefresh.setRefreshing(false);
         }

        });
        return view;
    }
    public void refresh() {
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
//        Model.instance.getAttractions((list)->{
//            Log.d("list---------------", list.toString());
//            viewModel.setData(list);
//            adapter.notifyDataSetChanged();
//            swipeRefresh.setRefreshing(false);
//
//        });

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView decsTv;
        ImageView imagev;
        TextView mypost;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            //Update values for the new row
            titleTv = itemView.findViewById(R.id.details_title_tv);
            decsTv = itemView.findViewById(R.id.details_desc_tv);
            imagev = itemView.findViewById(R.id.details_image_imv);
            mypost=itemView.findViewById(R.id.detailes_mypost_tv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });

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
            Attraction attraction = viewModel.getData().getValue().get(position);
            holder.titleTv.setText(attraction.getTitle());
            holder.decsTv.setText(attraction.getDesc());
//            if(!attraction.getUserId().equals(user_id)){
//                holder.mypost.setText("");
//            }
//            if(attraction.getUri() != null){
//                holder.imagev.setImageURI(attraction.getUri());
//            }else if(attraction.getBitmap() != null){
//                holder.imagev.setImageBitmap(attraction.getBitmap());
//            }
        }

        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue()==null){
                return 0;
            }
            return viewModel.getData().getValue().size();
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
