package com.example.studentmanagementsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {

    Button addButton;
    EditText nameEditText, rollNoEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        addButton = findViewById(R.id.saveButton);
        nameEditText = findViewById(R.id.nameEditText);
        rollNoEditText = findViewById(R.id.rollNoEditText);

        if (getIntent().hasExtra("VIEW")) {
            Student stu = getIntent().getParcelableExtra("VIEW");
            nameEditText.setText(stu.getStudentName());
            nameEditText.setEnabled(false);
            nameEditText.setTextColor(Color.parseColor("#000000"));
            rollNoEditText.setText(stu.getRollNo());
            rollNoEditText.setEnabled(false);
            rollNoEditText.setTextColor(Color.parseColor("#000000"));
            addButton.setVisibility(View.GONE);

        } else if (getIntent().hasExtra("UPDATE")) {
            Student stu = getIntent().getParcelableExtra("UPDATE");
            nameEditText.setText(stu.getStudentName());
            nameEditText.setEnabled(true);
            rollNoEditText.setText(stu.getRollNo());
            rollNoEditText.setEnabled(true);
        }

    }

    public void onClickAddButton(View v)
    {
        Intent returnIntent = getIntent();
        String name = nameEditText.getText().toString().trim();
        String rollNo = rollNoEditText.getText().toString().trim();
//        ArrayList<Student> rollNo =


        if(name.length()==0) {
            nameEditText.requestFocus();
            nameEditText.setError("Length can't be Zero");
        }
        else if(!name.matches("\\b[a-zA-Z]+\\s[a-zA-Z]+\\b")) {
            nameEditText.requestFocus();
            nameEditText.setError("Enter a Valid Name");
        }
        else if (!rollNo.matches("(?:\\b|-)([1-9]{1,2}[0]?|100)\\b")) {
            rollNoEditText.requestFocus();
            rollNoEditText.setError("Roll No can be between 1-100");
        }

        else {

            if (getIntent().hasExtra("UPDATE"))
            {
                Student stu = getIntent().getParcelableExtra("UPDATE");
                stu.setRollNo(rollNo);
                stu.setStudentName(name);
                returnIntent.putExtra("UPDATED_STUDENT",stu);
                setResult(RESULT_OK,returnIntent);
                Toast.makeText(this, "Student Updated", Toast.LENGTH_LONG).show();
            }
            else if (getIntent().hasExtra("ADDED"))
            {
                Student stu = new Student(rollNo, name);
                returnIntent.putExtra("ADDED_STUDENT",stu);
                setResult(RESULT_OK,returnIntent);
                Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();
            }
        finish();
        }
    }
}
