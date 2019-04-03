package com.example.studentmanagementsystem.activity;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.adapter.PagerAdapter;
import com.example.studentmanagementsystem.fragment.ViewStudentFragment;
import com.example.studentmanagementsystem.fragment.StudentListFragment;
import com.example.studentmanagementsystem.util.CommunicationFragments;


import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT_CODE;
import static com.example.studentmanagementsystem.util.Constants.SHOW_STUDENT_CODE;


public class ShowStudentListActivity extends AppCompatActivity implements CommunicationFragments {

    // private RecyclerViewAdapter recyclerViewAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private String mode = ADD_STUDENT;
    private Bundle bundle = new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        bundle.putString("operationOnStudent", mode);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);

        mViewPager = findViewById(R.id.view_pager_show_student);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        tabLayout = findViewById(R.id.tab_layout_show_student);
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == ADD_STUDENT_CODE) {
                    ViewStudentFragment updateStudentFragment = (ViewStudentFragment) getSupportFragmentManager().getFragments().get(ADD_STUDENT_CODE);
                    updateStudentFragment.clearText(bundle);
                } else {
                    mode = ADD_STUDENT;
                    bundle.putString("operationOnStudent", ADD_STUDENT);
                }

            }


            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void changeTab() {
        if (mViewPager.getCurrentItem() == 0) {
            mViewPager.setCurrentItem(1);
        } else if (mViewPager.getCurrentItem() == 1) {
            mViewPager.setCurrentItem(0);
        }
    }

    @Override
    public void communicateAdd(Bundle bundle) {
        this.bundle = bundle;
        StudentListFragment addStudentFragment = (StudentListFragment) getSupportFragmentManager().getFragments().get(SHOW_STUDENT_CODE);
        addStudentFragment.addStudent(bundle);
        changeTab();
    }

    @Override
    public void communicateUpdate(Bundle bundle) {
        this.bundle = bundle;
        ViewStudentFragment updateStudentFragment = (ViewStudentFragment) getSupportFragmentManager().getFragments().get(ADD_STUDENT_CODE);
        if (updateStudentFragment != null) {
            updateStudentFragment.updateStudent(bundle);
            changeTab();
        }
    }
}
