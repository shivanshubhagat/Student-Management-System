package com.example.studentmanagementsystem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.studentmanagementsystem.MainActivity.studentArrayList;

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

        if (getIntent().hasExtra("STUDENT_POSITION_VIEW")) {
            int position = getIntent().getIntExtra("STUDENT_POSITION_VIEW", 0);
            nameEditText.setText(studentArrayList.get(position).getStudentName());
            nameEditText.setEnabled(false);
            nameEditText.setTextColor(Color.parseColor("#000000"));
            rollNoEditText.setText(studentArrayList.get(position).getRollNo());
            rollNoEditText.setEnabled(false);
            rollNoEditText.setTextColor(Color.parseColor("#000000"));

            addButton.setVisibility(View.GONE);
        } else if (getIntent().hasExtra("STUDENT_POSITION_UPDATE")) {
            int position = getIntent().getIntExtra("STUDENT_POSITION_UPDATE", 0);
            nameEditText.setText(studentArrayList.get(position).getStudentName());
            nameEditText.setEnabled(true);
            rollNoEditText.setText(studentArrayList.get(position).getRollNo());
            rollNoEditText.setEnabled(true);
        }

    }

    public void onClickAddButton(View v)
    {
        String name = nameEditText.getText().toString().trim();
        String rollNo = rollNoEditText.getText().toString().trim();

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
            if (!MainActivity.studentArrayList.isEmpty() )
            {
                for (Student holdStudent: MainActivity.studentArrayList) {
                    if(rollNo.equals(holdStudent.getRollNo()))
                    {
                        rollNoEditText.requestFocus();
                        rollNoEditText.setError("Roll No already Exists");
                    }
                }
            }
        }

        else{
        if (getIntent().hasExtra("STUDENT_POSITION_UPDATE")) {
            int position = getIntent().getIntExtra("STUDENT_POSITION_UPDATE", 0);
            Student studentToUpdate = studentArrayList.get(position);
            studentToUpdate.setStudentName(name);
            studentToUpdate.setRollNo(rollNo);
            Toast toast = Toast.makeText(this, "Student Updated", Toast.LENGTH_LONG);
        } else {
            Student stu = new Student(rollNo, name);
            MainActivity.studentArrayList.add(stu);
            Toast toast = Toast.makeText(this, "ADDED", Toast.LENGTH_LONG);
        }
        MainActivity.studentadapter.notifyDataSetChanged();
        finish();
    }
    }
}
