package com.example.studentmanagementsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewHolder> {

    private ArrayList<Student> studentAdapterArrayList;
    private OnStudentClickListener mListener;

    public interface OnStudentClickListener {
        void onStudentClick(int position);
    }

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        mListener = listener;
    }

    public StudentAdapter(ArrayList<Student> studentAdapterArrayList) {
        this.studentAdapterArrayList = studentAdapterArrayList;
    }


    public void refreshData(ArrayList<Student> studentAdapterArrayList){
        this.studentAdapterArrayList = studentAdapterArrayList;
notifyDataSetChanged();

    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_layout, parent, false);
        return new studentViewHolder(view, mListener, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final studentViewHolder holder, final int position) {
        holder.name.setText(studentAdapterArrayList.get(position).getStudentName());
        holder.rollNo.setText(studentAdapterArrayList.get(position).getRollNo());
    }

    @Override
    public int getItemCount() {
        if (studentAdapterArrayList != null) {
            return studentAdapterArrayList.size();
        }
        return 0;
    }

    public class studentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView name;
        TextView rollNo;
        Context context;

        public studentViewHolder(@NonNull View itemView, final OnStudentClickListener listener, Context ctx) {
            super(itemView);
            context = ctx;
            imgIcon = itemView.findViewById(R.id.img_icon);
            name = itemView.findViewById(R.id.name);
            rollNo = itemView.findViewById(R.id.rollno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onStudentClick(position);
                        }
                    }
                }
            });
        }
    }
}
