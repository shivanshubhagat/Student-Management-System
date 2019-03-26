package com.example.studentmanagementsystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.studentmanagementsystem.fragment.AddStudentFragment;
import com.example.studentmanagementsystem.fragment.StudentListFragment;

public class mPagerAdapter extends FragmentStatePagerAdapter {

    //defining the titles of the tabs
    private String[] mTabTitles = new String[]{"Students", "Add/Update"};

    //constructor
    public mPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StudentListFragment tab1 = new StudentListFragment();
                return tab1;
            case 1:
                AddStudentFragment tab2 = new AddStudentFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }


}

