package com.example.studentmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class
MainActivity extends AppCompatActivity {

    private StudentAdapter studentadapter;
    private ArrayList<Student> studentArrayList;
    private int thisposition;
    RecyclerView studentList;
    Button btnadd;
    private static final int ADD_STUDENT_CODE = 12,
            CODE_TO_VIEW_STUDENT = 101, CODE_TO_UPDATE_STUDENT = 102;

    public void setThisposition(int thisposition) {
        this.thisposition = thisposition;
    }

    public int getThisposition() {
        return thisposition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentArrayList = new ArrayList<Student>();
        setContentView(R.layout.activity_main);

        studentList = (RecyclerView) findViewById(R.id.studentlist);
        btnadd = findViewById(R.id.addButton);

        studentList.setLayoutManager(new LinearLayoutManager(this));
        studentadapter = new StudentAdapter(this.studentArrayList);
        studentList.setAdapter(studentadapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                intent.putExtra("ADDED", 111);
//                if(!studentArrayList.isEmpty())
//                {
//                    intent.putExtra("ARRAY_LIST",studentArrayList);
//                }
                startActivityForResult(intent, ADD_STUDENT_CODE);
            }
        });

        final StudentAdapter studentAdapter = new StudentAdapter(this.studentArrayList);
        studentList.setAdapter(studentAdapter);
        studentAdapter.setOnStudentClickListener(new StudentAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(final int position) {

                final String[] items = {"View", "Edit", "Delete"};
                final int view = 0, update = 1, delete = 2;
                android.app.AlertDialog.Builder options = new android.app.AlertDialog.Builder(MainActivity.this);
                options.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Student stu = studentArrayList.get(position);
                        setThisposition(position);

                        switch (which) {
                            case view:
                                Intent forView = new Intent(MainActivity.this, AddStudentActivity.class);
                                forView.putExtra("VIEW", stu);
                                startActivityForResult(forView, CODE_TO_VIEW_STUDENT);
                                Toast.makeText(MainActivity.this, "View", Toast.LENGTH_SHORT).show();
                                break;

                            case update:
                                Intent forEdit = new Intent(MainActivity.this, AddStudentActivity.class);
                                forEdit.putExtra("UPDATE", stu);
                                startActivityForResult(forEdit, CODE_TO_UPDATE_STUDENT);
                                Toast.makeText(MainActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                                break;

                            case delete:
                                final android.app.AlertDialog.Builder deleteDialog = new android.app.AlertDialog.Builder(MainActivity.this);
                                deleteDialog.setMessage("Do you want to delete info of this student ?");
                                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        studentArrayList.remove(position);
                                        studentAdapter.notifyDataSetChanged();
                                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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

        if (requestCode == ADD_STUDENT_CODE && resultCode == RESULT_OK) {
            Student s = data.getParcelableExtra("ADDED_STUDENT");
            studentArrayList.add(s);
            studentadapter.notifyDataSetChanged();

        } else if (requestCode == CODE_TO_UPDATE_STUDENT && resultCode == RESULT_OK) {
            int position = getThisposition();
            Student studentToAdd = data.getParcelableExtra("UPDATE");
//            studentArrayList.remove(position);
//            studentArrayList.add(position,studentToAdd);
            studentArrayList.get(position).setRollNo(studentToAdd.getRollNo());
            studentArrayList.get(position).setStudentName(studentToAdd.getStudentName());
            studentadapter.refreshData(studentArrayList);
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

        sortByNameItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new SortByName());
                studentadapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Sorted by Name", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        sortByRollNoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new SortByRollNo());
                studentadapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Sorted by RollNo", Toast.LENGTH_SHORT).show();
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
                    studentList.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                } else {
                    studentList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    //MENU COMPARATORS

    public class SortByName implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return (o1.getStudentName().compareTo(o2.getStudentName()));
        }
    }

    public class SortByRollNo implements Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return (o1.getRollNo().compareTo(o2.getRollNo()));
        }
    }
}
