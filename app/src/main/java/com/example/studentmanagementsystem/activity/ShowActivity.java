package com.example.studentmanagementsystem.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.adapter.StudentAdapter;
import com.example.studentmanagementsystem.util.CustomComparator;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.studentmanagementsystem.util.Constants.CODE_TO_ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_UPDATE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_VIEW_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE;
import static com.example.studentmanagementsystem.util.Constants.ITEMS;
import static com.example.studentmanagementsystem.util.Constants.UPDATE;
import static com.example.studentmanagementsystem.util.Constants.VIEW;

public class
ShowActivity extends AppCompatActivity {

    private StudentAdapter studentAdapter;
    private ArrayList<Student> studentArrayList;
    private int thisPosition;
    RecyclerView rvStudent;
    Button btnAdd;

    //getting and storing position of student to be used further
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public int getThisPosition() {
        return thisPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentArrayList = new ArrayList<>();
        setContentView(R.layout.activity_main);

        rvStudent = findViewById(R.id.studentlist);
        btnAdd = findViewById(R.id.addButton);

        rvStudent.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(this.studentArrayList);
        rvStudent.setAdapter(studentAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, AddStudentActivity.class);
                intent.putExtra("ADDED", 111);
                if (studentArrayList.size() > 0) {
                    intent.putParcelableArrayListExtra("ARRAY_LIST", studentArrayList);
                }
                startActivityForResult(intent, CODE_TO_ADD_STUDENT);
            }
        });

//        final StudentAdapter studentAdapter = new StudentAdapter(this.studentArrayList);
//        rvStudent.setAdapter(studentAdapter);
        studentAdapter.setOnStudentClickListener(new StudentAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(final int position) {

                android.app.AlertDialog.Builder options = new android.app.AlertDialog.Builder(ShowActivity.this);
                options.setItems(ITEMS, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Student stu = studentArrayList.get(position);
                        setThisPosition(position);

                        switch (which) {
                            //VIEW CASE
                            case VIEW:
                                Intent forView = new Intent(ShowActivity.this, AddStudentActivity.class);
                                forView.putExtra("VIEW", stu);
                                startActivityForResult(forView, CODE_TO_VIEW_STUDENT);
                                Toast.makeText(ShowActivity.this, "View", Toast.LENGTH_SHORT).show();
                                break;

                            //UPDATE CASE
                            case UPDATE:
                                Intent forEdit = new Intent(ShowActivity.this, AddStudentActivity.class);
                                forEdit.putExtra("UPDATE", stu);
                                startActivityForResult(forEdit, CODE_TO_UPDATE_STUDENT);
                                Toast.makeText(ShowActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                                break;

                            //DELETE CASE
                            case DELETE:
                                final android.app.AlertDialog.Builder deleteDialog = new android.app.AlertDialog.Builder(ShowActivity.this);
                                deleteDialog.setMessage("Do you want to delete info of this student ?");
                                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        studentArrayList.remove(position);
                                        studentAdapter.notifyDataSetChanged();
                                        Toast.makeText(ShowActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //CODE TO ADD STUDENT
        if (requestCode == CODE_TO_ADD_STUDENT && resultCode == RESULT_OK) {
            Student s = data.getParcelableExtra("ADDED_STUDENT");
            studentArrayList.add(s);
            studentAdapter.notifyDataSetChanged();
        }

        //CODE TO UPDATE STUDENT
        else if (requestCode == CODE_TO_UPDATE_STUDENT && resultCode == RESULT_OK) {
            int position = getThisPosition();
            Student studentToAdd = data.getParcelableExtra("UPDATED_STUDENT");
            Student student = new Student(studentToAdd.getRollNo(), studentToAdd.getStudentName());
            studentArrayList.remove(position);
            studentArrayList.add(position, student);
            studentAdapter.notifyDataSetChanged();
        }
    }

    //TOP MENU CODE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);

        MenuItem switchItem = menu.findItem(R.id.switchItem);
        MenuItem sortByNameItem = menu.findItem(R.id.sortByName);
        MenuItem sortByRollNoItem = menu.findItem(R.id.sortByRollNo);

        //SORT BY NAME
        sortByNameItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new CustomComparator.SortByName());
                studentAdapter.notifyDataSetChanged();
                Toast.makeText(ShowActivity.this, "Sorted by Name", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //SORT BY ROLL NO
        sortByRollNoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new CustomComparator.SortByRollNo());
                studentAdapter.notifyDataSetChanged();
                Toast.makeText(ShowActivity.this, "Sorted by RollNo", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //LAYOUT CHANGING

        switchItem.setActionView(R.layout.switch_layout);
        Switch switchLayout = menu.findItem(R.id.switchItem).getActionView().findViewById(R.id.menuSwitch);
        switchLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rvStudent.setLayoutManager(new GridLayoutManager(ShowActivity.this, 2));
                } else {
                    rvStudent.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
                }
            }
        });

        return super.onCreateOptionsMenu(menu);

    }
}
