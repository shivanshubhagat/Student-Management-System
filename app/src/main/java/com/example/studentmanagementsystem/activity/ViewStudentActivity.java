/* Activity on which students details are asked from user and a button is used to give
    furthermore options to add the students to database via 3 ways, from service,
    intent service and async task.
*/

package com.example.studentmanagementsystem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.fragment.ViewStudentFragment;
import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.util.CommunicationFragments;

import static com.example.studentmanagementsystem.util.Constants.VIEW;

public class ViewStudentActivity extends AppCompatActivity implements CommunicationFragments {

    ViewStudentFragment viewStudentFragment;
    Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        student= getIntent().getParcelableExtra(VIEW);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        viewStudentFragment = new ViewStudentFragment();
        fragmentTransaction.add(R.id.frag_view,viewStudentFragment);
        fragmentTransaction.commit();

        }

    @Override
    protected void onStart() {
        super.onStart();
        viewStudentFragment.viewMode(student);
    }


    @Override
    public void communicateAdd(Bundle bundle) { }

    @Override
    public void communicateUpdate(Bundle bundle) { }

    }