package com.example.studentmanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    static StudentAdapter studentadapter;
    static ArrayList<Student> studentArrayList;
    RecyclerView studentList;
    Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentArrayList = new ArrayList<Student>();
        studentadapter = new StudentAdapter(studentArrayList);

        setContentView(R.layout.activity_main);

        studentList = (RecyclerView) findViewById(R.id.studentlist);
        btnadd = findViewById(R.id.addButton);

        studentList.setLayoutManager(new LinearLayoutManager(this));
        studentList.setAdapter(studentadapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Student s = (Student) data.getSerializableExtra("studentObject");
                studentArrayList.add(s);
                studentadapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);

        MenuItem switchItem = menu.findItem(R.id.switchItem);
        MenuItem sortByNameItem = menu.findItem(R.id.sortByName);
        MenuItem sortByRollNoItem = menu.findItem(R.id.sortByRollNo);

        sortByNameItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList,new SortByName());
                studentadapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Sorted by Name",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        sortByRollNoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList,new SortByRollNo());
                studentadapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this,"Sorted by RollNo",Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        switchItem.setActionView(R.layout.swtich_layout);
        Switch switchLayout = menu.findItem(R.id.switchItem).getActionView().findViewById(R.id.menuSwitch);
        switchLayout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    studentList.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                }
                else
                {
                    studentList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    public class SortByName implements Comparator<Student>
    {

        @Override
        public int compare(Student o1, Student o2) {
            return (o1.getStudentName().compareTo(o2.getStudentName()));
        }
    }

    public class SortByRollNo implements Comparator<Student>
    {

        @Override
        public int compare(Student o1, Student o2) {
            return (o1.getRollNo().compareTo(o2.getRollNo()));
        }
    }
}
