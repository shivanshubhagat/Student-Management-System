package com.example.studentmanagementsystem.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.backgroundDbHandler.BackgroundAsyncTask;
import com.example.studentmanagementsystem.backgroundDbHandler.BackgroundIntentService;
import com.example.studentmanagementsystem.backgroundDbHandler.BackgroundService;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;
import com.example.studentmanagementsystem.util.ValidUtil;

import java.util.ArrayList;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.SAVING_OPTIONS;
import static com.example.studentmanagementsystem.util.Constants.UPDATE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.USE_ASYNC_TASK;
import static com.example.studentmanagementsystem.util.Constants.USE_INTENT_SERVICE;
import static com.example.studentmanagementsystem.util.Constants.USE_SERVICE;

public class ViewStudentFragment extends Fragment {

    private Context mContext;
    private DatabaseHelper databaseHelper;
    private View view;
    private Button btnAdd;
    private EditText editTextName, editTextRollNo;
    private boolean errorHandling;
    private int selectButtonOperation = 2;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Bundle bundle = new Bundle();
    private int select;
    private CommunicationFragments mListener;
    private String mode = ADD_STUDENT;
    private  int oldIdOfStudent;
    public ViewStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    private void initValues() {
        editTextName = view.findViewById(R.id.nameEditText);
        editTextRollNo = view.findViewById(R.id.rollNoEditText);
        btnAdd = view.findViewById(R.id.saveButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_student, container, false);
        databaseHelper = new DatabaseHelper(mContext);

        initValues();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorHandling = true;
                switch (selectButtonOperation) {
                    case 1: //edit
                        editButtonOnClick();
                        break;
                    case 2: //add
                        addButtonOnClick();
                        break;
                }
            }
        });
        return view;
    }

    private void editButtonOnClick() {
        Bundle bundle = new Bundle();
        String name = editTextName.getText().toString().trim();
        String roll = editTextRollNo.getText().toString();
        errorHandling = true;
        if (!ValidUtil.isValidName(name)) {
            editTextName.requestFocus();
            editTextName.setError("Invalid Name");
            errorHandling = false;
        }
        else if (errorHandling) {
            Student student = new Student(Integer.parseInt(roll), name);
            bundle.putString("Update", "Update");
            bundle.putParcelable("Student", student);
            generateAlertDialog(student, "Update", oldIdOfStudent);
            editTextRollNo.setEnabled(true);
            editTextName.setEnabled(true);
        }
    }

    private void addButtonOnClick() {
        String name = editTextName.getText().toString().trim();
        String rollNo = editTextRollNo.getText().toString();
        errorHandling = true;

        // validation for name check used set error to edit text
        if (!ValidUtil.isValidName(name)) {
            editTextName.requestFocus();
            editTextName.setError("Invalid Name");
            errorHandling = false;
        }

        // validation for Roll No check used set error to edit text
        if (!ValidUtil.isValidId(rollNo)) {
            editTextRollNo.requestFocus();
            editTextRollNo.setError("Invalid Roll No");
            errorHandling = false;
        }
        //check duplicate Roll No
        else if (!ValidUtil.isUniqueRollNo(studentList, Integer.parseInt(rollNo))) {
            editTextRollNo.requestFocus();
            editTextRollNo.setError("Roll No already exists");
            errorHandling = false;
        }
        else{
            if (errorHandling) {
                Student student = new Student(Integer.parseInt(rollNo), name);
                bundle.putParcelable("Student", student);
                generateAlertDialog(student, "Add", 0);
                editTextRollNo.setEnabled(true);
                editTextName.setEnabled(true);
            }
        }
    }

    private void generateAlertDialog(final Student studentToHandle, final String operationOnStudent, final int oldIdOfStudent) {
        editTextRollNo.setEnabled(false);
        editTextName.setEnabled(false);
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        if (selectButtonOperation == 1)
            mBuilder.setTitle("Updated");
        else
            mBuilder.setTitle("Added");

        //setting SingleChoiceItem onClick
        mBuilder.setSingleChoiceItems(SAVING_OPTIONS, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //set which choice is selected
                select = which;
                dialog.dismiss();

                switch (select) {
                    case USE_SERVICE:
                        Intent intentForService = new Intent(mContext,
                                BackgroundService.class);
                        intentForService.putExtra("studentForDb", studentToHandle);
                        intentForService.putExtra("operation", operationOnStudent);
                        intentForService.putExtra("oldIdOfStudent", oldIdOfStudent);
                        mContext.startService(intentForService);
                        break;

                    case USE_INTENT_SERVICE:
                        Intent intentForIntentService = new Intent(mContext,
                                BackgroundIntentService.class);
                        intentForIntentService.putExtra("studentForDb", studentToHandle);
                        intentForIntentService.putExtra("operation", operationOnStudent);
                        intentForIntentService.putExtra("oldIdOfStudent", oldIdOfStudent);
                        mContext.startService(intentForIntentService);
                        break;

                    case USE_ASYNC_TASK:
                        BackgroundAsyncTask backgroundAsyncTasks = new BackgroundAsyncTask(mContext);
                        backgroundAsyncTasks.execute(studentToHandle, operationOnStudent, 0);
                        break;
                }

                Bundle bundle = new Bundle();
                bundle.putParcelable("Student", studentToHandle);
                bundle.putString("operationOnStudent", operationOnStudent);

                mListener.communicateAdd(bundle);
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    public void viewMode(Student student) {
        editTextName.setText(student.getStudentName());
        editTextRollNo.setText(String.valueOf(student.getRollNo()));
        editTextName.setEnabled(false);
        editTextRollNo.setEnabled(false);
        editTextName.setTextColor(Color.BLACK);
        editTextRollNo.setTextColor(Color.BLACK);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btnAdd.setVisibility(View.GONE);
    }

    public void updateStudent(Bundle bundle) {
        this.bundle = bundle;
        mode=bundle.getString("operationOnStudent");
        if (bundle.getString("operationOnStudent").equals("Update")) {

            Student student= bundle.getParcelable("Student");
            oldIdOfStudent = student.getRollNo();
            editTextName.setText(student.getStudentName());
            editTextRollNo.setText(String.valueOf(student.getRollNo()));
            editTextRollNo.setTextColor(Color.BLACK);
            editTextRollNo.setEnabled(false);
            editTextName.setEnabled(true);
            editTextName.requestFocus();
            selectButtonOperation = 1;

        } else if (bundle.getString("operationOnStudent").equals("Add")) {
            editTextName.setEnabled(true);
            editTextRollNo.setEnabled(true);
            studentList = bundle.getParcelableArrayList("student Array list");
            selectButtonOperation = 2;
        }
    }





    public void clearText(Bundle bundle) {
        if(!bundle.getString("operationOnStudent").equals(UPDATE_STUDENT))
        {
            editTextRollNo.setEnabled(true);
            editTextRollNo.getText().clear();
            editTextName.getText().clear();
            selectButtonOperation=2;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunicationFragments) {
            mListener = (CommunicationFragments) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
