package com.example.mylifeline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.ViewHolder> {

    private ArrayList<BookedListItem> mDoctorsList;
    private android.content.Context context;

    public BookedAdapter(ArrayList<BookedListItem> DoctorsList, Context context) {
        mDoctorsList = DoctorsList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.booked_cards, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        BookedListItem currentItem  = mDoctorsList.get(i);

        viewHolder.doctorName.setText(currentItem.getmDoctorNAme());
        viewHolder.time.setText(currentItem.getmTime());
        viewHolder.date.setText(currentItem.getmDate());
        viewHolder.problem.setText(currentItem.getmProblem());
        viewHolder.status.setText(currentItem.getmStatus());


    }


    @Override
    public int getItemCount() {
        return mDoctorsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView doctorName,date,time,problem,status;
        View itemView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            doctorName = itemView.findViewById(R.id.doctorName);
            date = itemView.findViewById(R.id.bookedDate);
            time = itemView.findViewById(R.id.bookedTime);
            problem = itemView.findViewById(R.id.bookedProblem);
            status = itemView.findViewById(R.id.bookedStatus);
        }
    }
}
