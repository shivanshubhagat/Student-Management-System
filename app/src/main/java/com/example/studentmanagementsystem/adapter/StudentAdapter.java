package com.example.studentmanagementsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.studentViewHolder> {

    //arraylist for student adapter
    private ArrayList<Student> studentAdapterArrayList;
    private OnStudentClickListener mListener;

    //after clicking on student in ShowActivity
    public interface OnStudentClickListener {
        void onStudentClick(int position);
    }

    //setting listener from clicked student
    public void setOnStudentClickListener(OnStudentClickListener listener) {
        mListener = listener;
    }

    //assigning arraylist
    public StudentAdapter(ArrayList<Student> studentAdapterArrayList) {
        this.studentAdapterArrayList = studentAdapterArrayList;
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_layout, parent, false);
        return new studentViewHolder(view, mListener);
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

    //student view holder class
    public class studentViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView name;
        TextView rollNo;

        //constructor which onClick method listens to position
        public studentViewHolder(@NonNull View itemView, final OnStudentClickListener listener) {
            super(itemView);
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
