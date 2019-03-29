package com.example.mylifeline;

/**
 * Created by Rishabh Gupta on 29-03-2019
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private ArrayList<HospitalListItem> mStaffList;
    private android.content.Context ctx;


    public myAdapter(ArrayList<HospitalListItem> hospitalListItems, Context ctx) {
        mStaffList = hospitalListItems;
        this.ctx = ctx;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View view = li.inflate(R.layout.layout_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final HospitalListItem currentItem = mStaffList.get(i);
        viewHolder.textName.setText(currentItem.getmName());
        viewHolder.address.setText(currentItem.getmAddress());
        viewHolder.icon.setText(currentItem.getmName().substring(0, 1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, RegisteredHospitalsDetails.class);
                intent.putExtra("hospitalName", currentItem.getmName());
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
        public TextView address;
        public TextView icon;
        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            textName = itemView.findViewById(R.id.tvName);
            address = itemView.findViewById(R.id.tvAddress);
            icon = itemView.findViewById(R.id.tvIcon);


        }


    }


}
