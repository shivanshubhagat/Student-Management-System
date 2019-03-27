package com.example.studentmanagementsystem.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.studentmanagementsystem.backgroundDbHandler.BackgroundAsyncTask;
import com.example.studentmanagementsystem.backgroundDbHandler.BackgroundIntentService;
import com.example.studentmanagementsystem.backgroundDbHandler.BackgroundService;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;
import com.example.studentmanagementsystem.util.Constants;
import com.example.studentmanagementsystem.util.ValidUtil;

import java.util.ArrayList;

import static com.example.studentmanagementsystem.util.Constants.USE_ASYNC_TASK;
import static com.example.studentmanagementsystem.util.Constants.USE_INTENT_SERVICE;
import static com.example.studentmanagementsystem.util.Constants.USE_SERVICE;

public class ViewStudentFragment extends Fragment {

    Context mContext;
    DatabaseHelper databaseHelper;
    View view;
    Button btnAdd;
    EditText editTextName, editTextRollNo;
    private boolean errorHandling;
    private int selectButtonOperation = 2;
    private ArrayList<Student> studentList = new ArrayList<>();
    private Bundle bundle;
    public final static String[] ITEM_DAILOG = {"AsyncTask", "Service", "Intent Service"};
    private int select;
    private CommunicationFragments mListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewStudentFragment newInstance(String param1, String param2) {
        ViewStudentFragment fragment = new ViewStudentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_student, container, false);
        databaseHelper = new DatabaseHelper(mContext);

        initValues();

        //set click listener to button
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

    private void addButtonOnClick() {
        String name = editTextName.getText().toString().trim();
        String rollNo = editTextRollNo.getText().toString().trim();

        // validation for name check used set error to edit text
        if (!ValidUtil.isValidName(name)) {
            editTextName.setError("Invalid Name");
            errorHandling = false;
        }

        // validation for Roll No check used set error to edit text
        if (!ValidUtil.isValidId(rollNo)) {
            editTextRollNo.setError("Invalid Roll No");
            errorHandling = false;
        }
        //check duplicte Roll No
        else if (ValidUtil.isCheckValidId(studentList, rollNo)) {
            editTextRollNo.setError("Roll No already exists");
            errorHandling = false;
        }
        //check if error is present or not
        if (errorHandling) {
            Student student = new Student(rollNo,name);
            bundle.putParcelable("Student", student);
            generateAlertDialog(student, "Added",null);
        }
    }


    private void generateAlertDialog(final Student studentToHandle, final String operationOnStudent,final String oldIdOfStudent) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        if (selectButtonOperation == 1)//edit
            mBuilder.setTitle("Updated");
        else
            mBuilder.setTitle("Added");

        //setting SingleChoiceItem onClick
        mBuilder.setSingleChoiceItems(ITEM_DAILOG, -1, new DialogInterface.OnClickListener() {
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
                        backgroundAsyncTasks.execute(studentToHandle, operationOnStudent);
                        break;
                }
                mListener.communication(bundle);
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    private void editButtonOnClick() {
        String Name = editTextName.getText().toString().trim();

        // validation for first name check used set error to edit text
        if (!ValidUtil.isValidName(Name)) {
            editTextName.setError("Invalid Name");
            errorHandling = false;
        }

        //check if error is present or not
        if (errorHandling) {
            bundle.putString("Name", Name);
        }
    }


    private void initValues() {
        editTextName = view.findViewById(R.id.nameEditText);
        editTextRollNo = view.findViewById(R.id.rollNoEditText);
        btnAdd = view.findViewById(R.id.saveButton);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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
