package com.example.studentmanagementsystem.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.adapter.PagerAdapter;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.fragment.ViewStudentFragment;
import com.example.studentmanagementsystem.fragment.StudentListFragment;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;

import java.util.ArrayList;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT_CODE;
import static com.example.studentmanagementsystem.util.Constants.SHOW_STUDENT_CODE;

public class ShowStudentListActivity extends AppCompatActivity implements CommunicationFragments {

    // private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Student> studentArrayList;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    protected DatabaseHelper databaseHelper;
    PagerAdapter mPagerAdapter;

    @Override //done
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);

        mViewPager = findViewById(R.id.view_pager_show_student);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        tabLayout = findViewById(R.id.tab_layout_show_student);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void onChangeTab() {
        Log.d("aaaaaa", "position: " );
        if (mViewPager.getCurrentItem() == 0) {

            Log.d("aaaaaa", "1 chla");
            mViewPager.setCurrentItem(1);
        }

        else if (mViewPager.getCurrentItem() == 1) {

            Log.d("aaaaaa", "2 chla");
            mViewPager.setCurrentItem(0);
        }
    }

    //DEKHNA
    public ArrayList<Student> onRefreshStudentList() {
        studentArrayList = databaseHelper.getStudentsFromDB();
        return studentArrayList;
    }

    //DEKHNA
    public void /*<---boolean*/ onStudentdelete(Student student) {
    }

    //DEKHNA
    public void onEditData(Intent intent) {

//        String tag = "android:switcher:" + R.id.view_pager_show_student + ":" + ADD_STUDENT_CODE;
//        ViewStudentFragment addStudentFragment = (ViewStudentFragment) getSupportFragmentManager().findFragmentByTag(tag);
//        if (addStudentFragment != null) {
//            addStudentFragment.onStudentEdit(intent);
//        }

    }

    public void onAddData(Intent intent) {
        String tag = "android:switcher:" + R.id.view_pager_show_student + ":" + ADD_STUDENT_CODE;
        ViewStudentFragment viewStudentFragment = (ViewStudentFragment) getSupportFragmentManager().findFragmentByTag(tag);
//        if (viewStudentFragment != null) {
//            viewStudentFragment.onStudentAdd(intent);
//        }
    }

    @Override
    public void communication(Bundle bundle) {

    }
}

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