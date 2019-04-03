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
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.adapter.StudentAdapter;
import com.example.studentmanagementsystem.util.CustomComparator;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.studentmanagementsystem.util.Constants.ADDED;
import static com.example.studentmanagementsystem.util.Constants.ADDED_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_UPDATE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_VIEW_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE;
import static com.example.studentmanagementsystem.util.Constants.DELETE_CASE;
import static com.example.studentmanagementsystem.util.Constants.EDIT;
import static com.example.studentmanagementsystem.util.Constants.ITEMS;
import static com.example.studentmanagementsystem.util.Constants.SORTED_BY_NAME;
import static com.example.studentmanagementsystem.util.Constants.SORTED_BY_ROLL_NO;
import static com.example.studentmanagementsystem.util.Constants.UPDATE;
import static com.example.studentmanagementsystem.util.Constants.UPDATED_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.UPDATE_CASE;
import static com.example.studentmanagementsystem.util.Constants.VIEW;
import static com.example.studentmanagementsystem.util.Constants.VIEW_CASE;

/**
 * First Activity where recycler view is used and a list of students is shown
 * @params 7
 */
public class ShowActivity extends AppCompatActivity {

    private StudentAdapter studentAdapter;
    private ArrayList<Student> studentArrayList;
    private int thisPosition;
    private RecyclerView rvStudent;
    private Button btnAdd;
    private DatabaseHelper databaseHelper;
    private Student stu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database helper
        databaseHelper = new DatabaseHelper(this);
        studentArrayList = databaseHelper.getStudentsFromDB();

        //recycler view getting populated by student array list
        rvStudent = findViewById(R.id.studentlist);

        //recycler view adapter
        studentAdapter = new StudentAdapter(this.studentArrayList);

        rvStudent.setAdapter(studentAdapter);
        rvStudent.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter.notifyDataSetChanged();

        //add button for adding new student
        btnAdd = findViewById(R.id.addButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddStudent = new Intent(ShowActivity.this, AddStudentActivity.class);
                intentAddStudent.putExtra(ADDED, CODE_TO_ADD_STUDENT);
                startActivityForResult(intentAddStudent,CODE_TO_ADD_STUDENT);
            }
        });

        //on clicking of any student in student list
        studentAdapter.setOnStudentClickListener(new StudentAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(final int position) {

                //make alert dialog box with 3 options -->
                //view, update , delete
                android.app.AlertDialog.Builder options = new android.app.AlertDialog.Builder(ShowActivity.this);
                options.setItems(ITEMS, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stu = studentArrayList.get(position);
                        setThisPosition(position);

                        switch (which) {
                            //VIEW_CASE CASE
                            case VIEW_CASE:
                                Intent intentView = new Intent(ShowActivity.this, AddStudentActivity.class);
                                intentView.putExtra(VIEW, stu);
                                startActivityForResult(intentView, CODE_TO_VIEW_STUDENT);
                                Toast.makeText(ShowActivity.this, VIEW, Toast.LENGTH_SHORT).show();
                                break;

                            //UPDATE_CASE CASE
                            case UPDATE_CASE:
                                Intent intentEdit = new Intent(ShowActivity.this, AddStudentActivity.class);
                                intentEdit.putExtra(UPDATE, stu);

//                                intentEdit.putExtra("ARRAY_LIST", position);
//                                intentEdit.putParcelableArrayListExtra("ARRAY_LIST", studentArrayList);

                                startActivityForResult(intentEdit, CODE_TO_UPDATE_STUDENT);
                                Toast.makeText(ShowActivity.this, EDIT, Toast.LENGTH_SHORT).show();
                                break;

                            //DELETE_CASE CASE
                            //will make another dialog box to delete student with options yes or no
                            case DELETE_CASE:
                                final android.app.AlertDialog.Builder deleteDialog = new android.app.AlertDialog.Builder(ShowActivity.this);
                                deleteDialog.setMessage(R.string.delete_message);
                                deleteDialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseHelper.deleteStudentFromDB(stu.getRollNo());
                                        studentArrayList.remove(position);
                                        studentAdapter.notifyDataSetChanged();
                                        Toast.makeText(ShowActivity.this,DELETE, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                deleteDialog.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
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

    //getting and storing position of student
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }

    public int getThisPosition() {
        return thisPosition;
    }

    /*what to do on obtaining result from activity
     *either Add student or Update student in recycler view */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //CODE TO ADD STUDENT
        if (requestCode == CODE_TO_ADD_STUDENT && resultCode == RESULT_OK) {
            Student s = data.getParcelableExtra(ADDED_STUDENT);
            studentArrayList.add(s);
            studentAdapter.notifyDataSetChanged();
        }

        //CODE TO UPDATE_CASE STUDENT
        else if (requestCode == CODE_TO_UPDATE_STUDENT && resultCode == RESULT_OK) {
            int position = getThisPosition();
            studentArrayList.remove(position);
            Student studentReceived = data.getParcelableExtra(UPDATED_STUDENT);
            studentArrayList.add(studentReceived);
            studentAdapter.notifyDataSetChanged();
        }
    }

    //TOP TOOL BAR CODE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflating items on Bar
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
                Toast.makeText(ShowActivity.this, SORTED_BY_NAME, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //SORT BY ROLL NO
        sortByRollNoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new CustomComparator.SortByRollNo());
                studentAdapter.notifyDataSetChanged();
                Toast.makeText(ShowActivity.this, SORTED_BY_ROLL_NO, Toast.LENGTH_SHORT).show();
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