package com.example.hospitaladmin.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hospitaladmin.R;
import com.example.hospitaladmin.StaffListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<StaffListItem> mStaffList;


    public Adapter(ArrayList<StaffListItem> staffListItems) {
        mStaffList = staffListItems;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        StaffListItem currentItem = mStaffList.get(i);
        viewHolder.textName.setText(currentItem.getmName());
        viewHolder.textExpertise.setText(currentItem.getmExpertise());
        Picasso.get().load(currentItem.getmImageUrl()).into(viewHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return mStaffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textExpertise;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.listName);
            textExpertise = itemView.findViewById(R.id.listExpertise);
            imageView = itemView.findViewById(R.id.listImage);
        }


    }


}
