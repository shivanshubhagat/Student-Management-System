/* Activity on which students details are asked from user and a button is used to give
    furthermore options to add the students to database via 3 ways, from service,
    intent service and async task.
*/

package com.example.studentmanagementsystem.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;

import static com.example.studentmanagementsystem.util.Constants.VALID_NAME;
import static com.example.studentmanagementsystem.util.Constants.VALID_ROLL_NO;

public class AddStudentActivity extends AppCompatActivity {

    Button buttonAddStudent;
    EditText editTextName, editTextRollNo;
    ArrayList<Student> listHoldStudent;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        buttonAddStudent = findViewById(R.id.saveButton);
        editTextName = findViewById(R.id.nameEditText);
        editTextRollNo = findViewById(R.id.rollNoEditText);
        editTextName.setFocusable(true);

        databaseHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("VIEW")) {
            Student stu = getIntent().getParcelableExtra("VIEW");

            editTextName.setText(stu.getStudentName());
            editTextRollNo.setText(stu.getRollNo());

            editTextName.setTextColor(Color.BLACK);
            editTextRollNo.setTextColor(Color.BLACK);

            editTextName.setEnabled(false);
            editTextRollNo.setEnabled(false);

            editTextName.setFocusable(false);
            editTextRollNo.setFocusable(false);

            buttonAddStudent.setVisibility(View.GONE);

        } else if (getIntent().hasExtra("UPDATE")) {
            Student stu = getIntent().getParcelableExtra("UPDATE");

            editTextName.setEnabled(true);
            editTextRollNo.setEnabled(true);

            editTextName.setText(stu.getStudentName());
            editTextRollNo.setText(stu.getRollNo());
        }
    }

    public void onClickAddButton(View v) {
        String name = editTextName.getText().toString().trim();
        String rollNo = editTextRollNo.getText().toString().trim();
        Intent holdIntentForList = getIntent();

        //Name cant be empty
        if (name.length() == 0) {
            editTextName.requestFocus();
            editTextName.setError("Length can't be Zero");
        }
        //two words Name
        else if (!name.matches(VALID_NAME)) {
            editTextName.requestFocus();
            editTextName.setError("Enter a Valid Name");
        }
        //Valid Roll no between 1-100
        else if (!rollNo.matches(VALID_ROLL_NO)) {
            editTextRollNo.requestFocus();
            editTextRollNo.setError("Roll No can be between 1-100");
        }
        //Check Unique Roll No
        else if (holdIntentForList.hasExtra("ARRAY_LIST")) {
            listHoldStudent = holdIntentForList.getParcelableArrayListExtra("ARRAY_LIST");
            for (int thisRoll = 0; thisRoll < listHoldStudent.size(); thisRoll++) {
                Student tempStudent = listHoldStudent.get(thisRoll);
                if (tempStudent.getRollNo().equals(rollNo)) {
                    editTextRollNo.requestFocus();
                    editTextRollNo.setError("Roll No. already exists");
                }
            }
        } else
            {
            Intent returnIntent = getIntent();
            if (returnIntent.hasExtra("UPDATE")) {
                Student stu = returnIntent.getParcelableExtra("UPDATE");
                int oldRollNo = Integer.parseInt(stu.getRollNo());
                if (!databaseHelper.rollNoExistsEdit(Integer.parseInt(String.valueOf(rollNo)),oldRollNo))
                {
                    stu.setRollNo(rollNo);
                    stu.setStudentName(name);
                    databaseHelper.updateStudentInDB(stu,oldRollNo);
                    returnIntent.putExtra("UPDATED_STUDENT", stu);
                    setResult(RESULT_OK, returnIntent);
                    Toast.makeText(this, "Student Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    editTextRollNo.setError("Roll No already Exists");
                    editTextRollNo.requestFocus();
                }


            } else if (returnIntent.hasExtra("ADDED")) {
                if (!databaseHelper.rollNoExistsAdd(Integer.parseInt(String.valueOf(rollNo))))
                {
                    Student stu = new Student(rollNo, name);
                    databaseHelper.addStudentToDB(stu);
                    returnIntent.putExtra("ADDED_STUDENT", stu);
                    setResult(RESULT_OK, returnIntent);
                    Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    editTextRollNo.setError("Roll No already Exists");
                    editTextRollNo.requestFocus();
                }
            }
        }
    }

}
