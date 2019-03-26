package com.example.studentmanagementsystem.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.example.studentmanagementsystem.adapter.mPagerAdapter;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.fragment.AddStudentFragment;
import com.example.studentmanagementsystem.fragment.StudentListFragment;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.studentmanagementsystem.util.CustomComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT_CODE;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_UPDATE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.CODE_TO_VIEW_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE;
import static com.example.studentmanagementsystem.util.Constants.OPTIONS;
import static com.example.studentmanagementsystem.util.Constants.SHOW_STUDENT_CODE;
import static com.example.studentmanagementsystem.util.Constants.UPDATE;
import static com.example.studentmanagementsystem.util.Constants.VIEW;

public class
ShowStudentListActivity extends AppCompatActivity implements StudentListFragment {

    // private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Student> studentArrayList;
    private int thisPosition;
    private ViewPager mViewPager;
    Button btnAdd;
    protected DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);

        databaseHelper = new DatabaseHelper(this);

        studentArrayList = new ArrayList<>();

        btnAdd = findViewById(R.id.addButton);

        TabLayout tabLayout = findViewById(R.id.tab_layout_show_student);
        mViewPager = findViewById(R.id.view_pager_show_student);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new mPagerAdapter(getSupportFragmentManager()));

        /*
        rvStudent = findViewById(R.id.studentlist);
        recyclerViewAdapter = new RecyclerViewAdapter(this.studentArrayList);
        rvStudent.setAdapter(recyclerViewAdapter);
        rvStudent.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddStudent = new Intent(ShowStudentListActivity.this, ViewStudentActivity.class);
                intentAddStudent.putExtra("ADDED", 111);
                startActivityForResult(intentAddStudent,CODE_TO_ADD_STUDENT);
            }
        });

        recyclerViewAdapter.setOnStudentClickListener(new RecyclerViewAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(final int position) {

                android.app.AlertDialog.Builder options = new android.app.AlertDialog.Builder(ShowStudentListActivity.this);
                options.setItems(OPTIONS, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Student stu = studentArrayList.get(position);
                        setThisPosition(position);

                        switch (which) {
                            //VIEW CASE
                            case VIEW:
                                Intent intentView = new Intent(ShowStudentListActivity.this, ViewStudentActivity.class);
                                intentView.putExtra("VIEW", stu);
                                startActivityForResult(intentView, CODE_TO_VIEW_STUDENT);
                                Toast.makeText(ShowStudentListActivity.this, "View", Toast.LENGTH_SHORT).show();
                                break;

                            //UPDATE CASE
                            case UPDATE:
                                Intent intentEdit = new Intent(ShowStudentListActivity.this, ViewStudentActivity.class);
                                intentEdit.putExtra("UPDATE", stu);
                                startActivityForResult(intentEdit, CODE_TO_UPDATE_STUDENT);
                                Toast.makeText(ShowStudentListActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                                break;

                            //DELETE CASE
                            case DELETE:
                                final android.app.AlertDialog.Builder deleteDialog = new android.app.AlertDialog.Builder(ShowStudentListActivity.this);
                                deleteDialog.setMessage("Do you want to delete info of this student ?");
                                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseHelper.deleteStudentFromDB(stu.getRollNo());
                                        studentArrayList.remove(position);
                                        recyclerViewAdapter.notifyDataSetChanged();
                                        Toast.makeText(ShowStudentListActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
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
        */


    }

    public ArrayList<Student> onRefreshStudentList() {
        studentArrayList = databaseHelper.getStudentsFromDB();
        return studentArrayList;
    }

    public boolean onStudentdelete(Student student) {

    }

    public void onChangeTab() {

        if (mViewPager.getCurrentItem() == SHOW_STUDENT_CODE) {
            mViewPager.setCurrentItem(ADD_STUDENT_CODE);
        } else {
            mViewPager.setCurrentItem(SHOW_STUDENT_CODE);
        }
    }

    public void onEditData(Intent intent) {

        String tag = "android:switcher:" + R.id.view_pager_show_student + ":" + ADD_STUDENT_CODE;
        AddStudentFragment addStudentFragment = (AddStudentFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (addStudentFragment != null) {
            addStudentFragment.onStudentEdit(intent);
        }

    }

    public void onAddData(Intent intent) {
        String tag = "android:switcher:" + R.id.view_pager_show_student + ":" + ADD_STUDENT_CODE;
        AddStudentFragment addStudentFragment = (AddStudentFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (addStudentFragment != null) {
            addStudentFragment.onStudentAdd(intent);
        }
    }

}

//////////////////////TO BE DONE IN FRAGMENT NOW //////////////////////////////////////////
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //CODE TO ADD STUDENT
        if (requestCode == CODE_TO_ADD_STUDENT && resultCode == RESULT_OK) {
            assert data != null;
            Student s = data.getParcelableExtra("ADDED_STUDENT");
            studentArrayList.add(s);
            recyclerViewAdapter.notifyDataSetChanged();
        }

        //CODE TO UPDATE STUDENT
        else if (requestCode == CODE_TO_UPDATE_STUDENT && resultCode == RESULT_OK) {
            int position = getThisPosition();
            studentArrayList.remove(position);
            assert data != null;
            Student studentReceived = data.getParcelableExtra("UPDATED_STUDENT");
            studentArrayList.add(studentReceived);
            recyclerViewAdapter.notifyDataSetChanged();
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
                recyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(ShowStudentListActivity.this, "Sorted by Name", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //SORT BY ROLL NO
        sortByRollNoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new CustomComparator.SortByRollNo());
                recyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(ShowStudentListActivity.this, "Sorted by RollNo", Toast.LENGTH_SHORT).show();
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
                    rvStudent.setLayoutManager(new GridLayoutManager(ShowStudentListActivity.this, 2));
                } else {
                    rvStudent.setLayoutManager(new LinearLayoutManager(ShowStudentListActivity.this));
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
*/