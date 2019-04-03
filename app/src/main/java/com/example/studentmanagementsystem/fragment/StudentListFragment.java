package com.example.studentmanagementsystem.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.activity.ViewStudentActivity;
import com.example.studentmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;
import com.example.studentmanagementsystem.util.CustomComparator;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE_CASE;
import static com.example.studentmanagementsystem.util.Constants.OPTIONS;
import static com.example.studentmanagementsystem.util.Constants.UPDATE_CASE;
import static com.example.studentmanagementsystem.util.Constants.VIEW;
import static com.example.studentmanagementsystem.util.Constants.VIEW_CASE;

public class StudentListFragment extends Fragment implements RecyclerViewAdapter.OnStudentClickListener{

    private Context mContext;
    private CommunicationFragments communicationFragmentsListener;
    private RecyclerView rvStudent;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Student> studentArrayList;
    private Button btnAdd;
    private int thisPosition;
    private DatabaseHelper databaseHelper;
    private String mode = ADD_STUDENT;

    public StudentListFragment() {
        // Required empty public constructor
    }

    public static StudentListFragment newInstance() {
        StudentListFragment fragment = new StudentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        studentArrayList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_show_student_list, container, false);

        //populate recycler view
        rvStudent = view.findViewById(R.id.rv_student_list);
        databaseHelper = new DatabaseHelper(mContext);
        init(view);
        handleRecyclerClick();
        return view;

    }

    private void init(View view) {
        studentArrayList = databaseHelper.getStudentsFromDB();
        rvStudent = view.findViewById(R.id.rv_student_list);
        recyclerViewAdapter = new RecyclerViewAdapter(this.studentArrayList);
        rvStudent.setLayoutManager(new LinearLayoutManager(mContext));
        rvStudent.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        btnAdd = view.findViewById(R.id.addButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("student Array list",studentArrayList);
                bundle.putString("operationOnStudent","Add");
                communicationFragmentsListener.communicateUpdate(bundle);
            }
        });
        setHasOptionsMenu(true);
        handleRecyclerClick();
    }

    public void handleRecyclerClick() {
        recyclerViewAdapter.setOnStudentClickListener(new RecyclerViewAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(final int position) {

                AlertDialog.Builder options = new AlertDialog.Builder(mContext);
                options.setItems(OPTIONS, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Student stu = studentArrayList.get(position);
                        setThisPosition(position);

                        switch (which) {
                            case VIEW_CASE:
                                Intent intentView = new Intent(mContext, ViewStudentActivity.class);
                                intentView.putExtra(VIEW, stu);
                                Toast.makeText(mContext, VIEW, Toast.LENGTH_SHORT).show();
                                startActivity(intentView);
                                break;

                            case UPDATE_CASE:
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("Student",stu);
                                bundle.putString("operationOnStudent", "Update");
                                communicationFragmentsListener.communicateUpdate(bundle);
                                break;

                            case DELETE_CASE:
                                final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(mContext);
                                deleteDialog.setMessage("Do you want to delete info of this student ?");
                                deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseHelper.deleteStudentFromDB(stu.getRollNo());
                                        studentArrayList.remove(position);
                                        recyclerViewAdapter.notifyDataSetChanged();
                                        Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
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
    }



    public void addStudent(Bundle bundle) {

        //Add new student.
        if(bundle.getString("operationOnStudent").equals("Add")) {

            Student student = bundle.getParcelable("Student");
            studentArrayList.add(student);
            recyclerViewAdapter.notifyDataSetChanged();

            //Update old student.
        }else if(bundle.getString("operationOnStudent").equals("Update")){

            Student newStudent = bundle.getParcelable("Student");
            Student oldStudent=studentArrayList.get(getThisPosition());
            studentArrayList.add(newStudent);
            studentArrayList.remove(oldStudent);
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onStudentClick(final int position) {
    }

    //getting and storing position of student to be used further
    public void setThisPosition(int thisPosition) {
        this.thisPosition = thisPosition;
    }
    public int getThisPosition() {
        return thisPosition;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CommunicationFragments) {
            communicationFragmentsListener = (CommunicationFragments) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        communicationFragmentsListener = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

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
                Toast.makeText(mContext, "Sorted by Name", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //SORT BY ROLL NO
        sortByRollNoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Collections.sort(studentArrayList, new CustomComparator.SortByRollNo());
                recyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, "Sorted by RollNo", Toast.LENGTH_SHORT).show();
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
                    rvStudent.setLayoutManager(new GridLayoutManager(mContext, 2));
                } else {
                    rvStudent.setLayoutManager(new LinearLayoutManager(mContext));
                }
            }
        });
        super.onCreateOptionsMenu(menu,menuInflater);
    }

}





