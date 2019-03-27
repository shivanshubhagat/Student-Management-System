package com.example.studentmanagementsystem.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.activity.ShowStudentListActivity;
import com.example.studentmanagementsystem.activity.ViewStudentActivity;
import com.example.studentmanagementsystem.adapter.RecyclerViewAdapter;
import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;

import java.util.ArrayList;

import static com.example.studentmanagementsystem.util.Constants.DELETE;
import static com.example.studentmanagementsystem.util.Constants.DELETE_CASE;
import static com.example.studentmanagementsystem.util.Constants.OPTIONS;
import static com.example.studentmanagementsystem.util.Constants.UPDATE;
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
    protected DatabaseHelper databaseHelper;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        studentArrayList = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_student_list, container, false);

        //populate recycler view
        rvStudent = view.findViewById(R.id.rv_student_list);
        databaseHelper = new DatabaseHelper(mContext);
        studentArrayList = databaseHelper.getStudentsFromDB();
        recyclerViewAdapter = new RecyclerViewAdapter(this.studentArrayList);
        rvStudent.setLayoutManager(new LinearLayoutManager(mContext));
        rvStudent.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        //add button on activity will change tab
        btnAdd = view.findViewById(R.id.addButton);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("student Array list",studentArrayList);
                bundle.putString("Add","Add");
                communicationFragmentsListener.communicateAdd(bundle);
            }
        });
        handleRecyclerClick();
        return view;

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
                            //VIEW_CASE CASE
                            case VIEW_CASE:
                                Intent intentView = new Intent(mContext, ViewStudentActivity.class);
                                intentView.putExtra(VIEW, stu);
                                Toast.makeText(mContext, VIEW, Toast.LENGTH_SHORT).show();
                                startActivity(intentView);
                                break;

                            //UPDATE_CASE CASE
                            case UPDATE_CASE:
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("Student",stu);
                                bundle.putString("Update", "Update");
                                communicationFragmentsListener.communicateUpdate(bundle);
                                break;

                            //DELETE CASE
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

        /**
         * Method delete student from the database
         *
         * @param student
         * @return true is successfully deleted
         */
        boolean onStudentDelete(Student student);

        /**
         * Method fetches data from the database and pass them in the adapter
         *
         * @return List of students from database
         */
        ArrayList<Student> onRefreshStudentList();

        /**
         * Method change the fragment to another fragment and call method in that fragment
         * used for communication in fragments to pass editData
         *
         * @param intent
         */
        void onEditData(Intent intent);

        /**
         * Method change the fragment to another fragment and call method in that fragment
         * used for communication in fragments to pass addData
         *
         * @param intent
         */
        void onAddData(Intent intent);
    }
}



//    private void sortByName() {
//        Collections.sort(studentArrayList, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                return o1.getStudentName().compareToIgnoreCase(o2.getStudentName());
//            }
//        });
//        recyclerViewAdapter.notifyDataSetChanged();
//
//    }
//
//    public void sortByRollNo() {
//        Collections.sort(studentArrayList, new Comparator<Student>() {
//            @Override
//            public int compare(Student o1, Student o2) {
//                return (Integer.parseInt(String.valueOf(o1.getRollNo()))) - (Integer.parseInt(String.valueOf(o2.getRollNo())));
//            }
//        });
//        recyclerViewAdapter.notifyDataSetChanged();
//    }
//





