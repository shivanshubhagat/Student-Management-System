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

    ArrayList<Student> studentAdapterArrayList;
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

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_layout, parent, false);
        return new studentViewHolder(view, mListener, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull final studentViewHolder holder, final int position) {
        holder.name.setText(studentAdapterArrayList.get(holder.getAdapterPosition()).getStudentName());
        holder.rollNo.setText(studentAdapterArrayList.get(holder.getAdapterPosition()).getRollNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"View", "Edit", "Delete"};

                AlertDialog.Builder options = new AlertDialog.Builder(holder.context);

                options.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(holder.context, items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        switch (which) {
                            case 0:
                                Intent forView = new Intent(holder.context, AddStudentActivity.class);
                                forView.putExtra("STUDENT_POSITION_VIEW", position);
                                holder.context.startActivity(forView);
                                Toast.makeText(holder.context, "View", Toast.LENGTH_SHORT).show();

                                break;

                            case 1:
                                Intent forEdit = new Intent(holder.context, AddStudentActivity.class);
                                forEdit.putExtra("STUDENT_POSITION_UPDATE",position);
                                holder.context.startActivity(forEdit);
                                Toast.makeText(holder.context, "Edit", Toast.LENGTH_SHORT).show();

                                break;
                            case 2:
                                final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(holder.context);
                                deleteDialog.setMessage("Do you want to delete info of this student ?");
                                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        studentAdapterArrayList.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(holder.context, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                deleteDialog.show();
                                break;
                        }
                    }
                });
                AlertDialog mAlert = options.create();
                mAlert.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (studentAdapterArrayList != null) {
            return studentAdapterArrayList.size();
        }
        return 0;
    }

    public static class studentViewHolder extends RecyclerView.ViewHolder {
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
