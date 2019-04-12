package com.example.hospitaladmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hospitaladmin.AppointmentItem;
import com.example.hospitaladmin.AppointmentListDetails;
import com.example.hospitaladmin.R;

import java.util.ArrayList;

/**
 * Created by Rishabh Gupta on 23-12-2017.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private ArrayList<AppointmentItem> mappointmentList;
    private android.content.Context ctx;

    public AppointmentAdapter(ArrayList<AppointmentItem> appointmentItem, Context ctx) {
        mappointmentList = appointmentItem;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);

        View view = li.inflate(R.layout.staff_appointments_cards, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentAdapter.ViewHolder viewHolder, final int i) {
        final AppointmentItem currentItem = mappointmentList.get(i);
        viewHolder.textName.setText(currentItem.getMname());
        viewHolder.textTime.setText(currentItem.getMtime());
        viewHolder.textProblem.setText(currentItem.getMproblem());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AppointmentListDetails.class);
                intent.putExtra("name",currentItem.getMname());
                intent.putExtra("problem",currentItem.getMproblem());
                ctx.startActivity(intent);
            }
        });

    }





    @Override
    public int getItemCount() {
        return mappointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName,textTime,textProblem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.appointmentName);
            textTime = itemView.findViewById(R.id.appointmentTime);
            textProblem = itemView.findViewById(R.id.appointmentProblem);

        }


    }
}
