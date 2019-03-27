/* Activity on which students details are asked from user and a button is used to give
    furthermore options to add the students to database via 3 ways, from service,
    intent service and async task.
*/

package com.example.studentmanagementsystem.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.fragment.ViewStudentFragment;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;

public class ViewStudentActivity extends AppCompatActivity implements CommunicationFragments {

    private Button buttonAddStudent;
    private EditText editTextName, editTextRollNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);




        buttonAddStudent = findViewById(R.id.saveButton);
        editTextName = findViewById(R.id.nameEditText);
        editTextRollNo = findViewById(R.id.rollNoEditText);
        editTextName.setFocusable(true);


        if (getIntent().getExtras() != null) {
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

        }
    }

    @Override
    public void communication(Bundle bundle) {

    }
}



        /*else if (getIntent().hasExtra("UPDATE")) {
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
        } else {
            Intent returnIntent = getIntent();
            if (returnIntent.hasExtra("UPDATE")) {
                Student studentToUpdate = returnIntent.getParcelableExtra("UPDATE");
                int oldRollNo = Integer.parseInt(studentToUpdate.getRollNo());
                if (!databaseHelper.rollNoExistsEdit(Integer.parseInt(String.valueOf(rollNo)), oldRollNo)) {
                    studentToUpdate.setRollNo(rollNo);
                    studentToUpdate.setStudentName(name);

                    generateDialog(studentToUpdate,"Update",oldRollNo);

                    returnIntent.putExtra("UPDATED_STUDENT", studentToUpdate);
                    setResult(RESULT_OK, returnIntent);
                } else {
                    editTextRollNo.setError("Roll No already Exists");
                    editTextRollNo.requestFocus();
                }


            } else if (returnIntent.hasExtra("ADDED")) {
                if (!databaseHelper.rollNoExistsAdd(Integer.parseInt(String.valueOf(rollNo)))) {
                    Student studentToAdd = new Student(rollNo, name);

                    generateDialog(studentToAdd, "Add", 0);

                    returnIntent.putExtra("ADDED_STUDENT", studentToAdd);
                    setResult(RESULT_OK, returnIntent);

                } else {
                    editTextRollNo.setError("Roll No already Exists");
                    editTextRollNo.requestFocus();
                }
            }
        }
    }*/

    /*private void generateDialog(final Student studentToHandle, final String operationOnStudent, final int oldIdOfStudent) {

        //Alert Dialog that has context of this activity.
        dialog = new AlertDialog.Builder(ViewStudentActivity.this);
        dialog.setTitle("Save Using :");
        //Sets the items of the Dialog.
        dialog.setItems(SAVING_OPTIONS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case USE_SERVICE:
                        Intent intentForService = new Intent(ViewStudentActivity.this,
                                BackgroundService.class);
                        intentForService.putExtra("studentForDb", studentToHandle);
                        intentForService.putExtra("operation", operationOnStudent);
                        intentForService.putExtra("oldIdOfStudent", oldIdOfStudent);
                        startService(intentForService);
                        break;

                    case USE_INTENT_SERVICE:
                        Intent intentForIntentService = new Intent(ViewStudentActivity.this,
                                BackgroundIntentService.class);
                        intentForIntentService.putExtra("studentForDb", studentToHandle);
                        intentForIntentService.putExtra("operation", operationOnStudent);
                        intentForIntentService.putExtra("oldIdOfStudent", oldIdOfStudent);
                        startService(intentForIntentService);
                        break;

                    case USE_ASYNC_TASK:
                        BackgroundAsyncTask backgroundAsyncTasks = new BackgroundAsyncTask(ViewStudentActivity.this);
                        backgroundAsyncTasks.execute(studentToHandle, operationOnStudent, oldIdOfStudent);
                        break;
                }
            }
        });
        AlertDialog mAlert = dialog.create();
        mAlert.show();
    }*/

   /* private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }
    */

    /*private void setBroadcastReceiver()
    {
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,intentFilter);
    }*/