package com.example.studentmanagementsystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;

import static com.example.studentmanagementsystem.util.Constants.VALID_NAME;
import static com.example.studentmanagementsystem.util.Constants.VALID_ROLL_NO;

public class AddStudentActivity extends AppCompatActivity {

    Button addButton;
    EditText nameEditText, rollNoEditText;
    ArrayList<Parcelable> holdStudentList;

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
            nameEditText.setFocusable(false);
            rollNoEditText.setFocusable(false);

        } else if (getIntent().hasExtra("UPDATE")) {
            Student stu = getIntent().getParcelableExtra("UPDATE");
            nameEditText.setFocusable(true);
            nameEditText.setText(stu.getStudentName());
            nameEditText.setEnabled(true);
            rollNoEditText.setText(stu.getRollNo());
            rollNoEditText.setEnabled(true);
        }

    }

    public void onClickAddButton(View v) {


        String name = nameEditText.getText().toString().trim();
        String rollNo = rollNoEditText.getText().toString().trim();


        Intent holdIntentForList = getIntent();
        boolean validateRoll = true;
        if (holdIntentForList.hasExtra("ARRAY_LIST")) {
            holdStudentList = holdIntentForList.getParcelableArrayListExtra("ARRAY_LIST");

            for (int thisRoll = 0; thisRoll < holdStudentList.size(); thisRoll++) {
                Student tempStudent = (Student) holdStudentList.get(thisRoll);
                if (tempStudent.getRollNo().equals(rollNo)) {
                    validateRoll = false;
                }
            }
        }
        if (name.length() == 0) {
            nameEditText.requestFocus();
            nameEditText.setError("Length can't be Zero");
        }
        else if (!name.matches(VALID_NAME)) {
            nameEditText.requestFocus();
            nameEditText.setError("Enter a Valid Name");
        }
        else if (!rollNo.matches(VALID_ROLL_NO)) {
            rollNoEditText.requestFocus();
            rollNoEditText.setError("Roll No can be between 1-100");
        }
        else if (!validateRoll) {
            rollNoEditText.requestFocus();
            rollNoEditText.setError("Roll No. already exists");
        }
        else {
            Intent returnIntent = getIntent();

            if (getIntent().hasExtra("UPDATE")) {

                Student stu = getIntent().getParcelableExtra("UPDATE");
                stu.setRollNo(rollNo);
                stu.setStudentName(name);
                getIntent().putExtra("UPDATED_STUDENT", stu);
                setResult(RESULT_OK, getIntent());
                Toast.makeText(this, "Student Updated", Toast.LENGTH_LONG).show();
            } else if (getIntent().hasExtra("ADDED")) {
                Student stu = new Student(rollNo, name);
                returnIntent.putExtra("ADDED_STUDENT", stu);
                setResult(RESULT_OK, returnIntent);
                Toast.makeText(this, "Student Added", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }
}
