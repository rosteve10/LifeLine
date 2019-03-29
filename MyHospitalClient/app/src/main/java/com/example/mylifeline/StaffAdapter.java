package com.example.mylifeline;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {


    private ArrayList<StaffListItems> mStaffList;
    private android.content.Context ctx;

    public StaffAdapter(ArrayList<StaffListItems> staffListItems, Context ctx) {
        mStaffList = staffListItems;
        this.ctx = ctx;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View view = li.inflate(R.layout.staff_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final StaffListItems currentItem = mStaffList.get(i);
        viewHolder.textName.setText(currentItem.getmName());
        viewHolder.textExpertise.setText(currentItem.getmExpertise());
        Picasso.get().load(currentItem.getmImageUrl()).into(viewHolder.imageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, StaffDoctorsDetails.class);
                intent.putExtra("StaffMobile", currentItem.getmMobile());
                intent.putExtra("hospitalName", currentItem.getMhospitalName());
                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mStaffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView textExpertise;
        public ImageView imageView;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            textName = itemView.findViewById(R.id.listName);
            textExpertise = itemView.findViewById(R.id.listExpertise);
            imageView = itemView.findViewById(R.id.listImage);
        }


    }

}
