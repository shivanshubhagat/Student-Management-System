package com.example.studentmanagementsystem.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.example.studentmanagementsystem.fragment.ViewStudentFragment;
import com.example.studentmanagementsystem.fragment.StudentListFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StudentListFragment tab1 = new StudentListFragment();
                return tab1;
            case 1:
                ViewStudentFragment tab2 = new ViewStudentFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return ("Student List");
            case 1:
                return ("Add/Update");
        }
        return  null;
    }

    @Override
    public int getCount() {
        return 2;
    }

}

