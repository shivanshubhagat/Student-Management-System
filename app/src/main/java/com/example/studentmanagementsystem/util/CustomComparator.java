package com.example.studentmanagementsystem.util;

import com.example.studentmanagementsystem.model.Student;

public class CustomComparator {
    //Sort by name class and overridden method
    public static class SortByName implements java.util.Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
            return (o1.getStudentName().compareTo(o2.getStudentName()));
        }
    }

    //Sort by roll no class and overridden method
    public static class SortByRollNo implements java.util.Comparator<Student> {

        @Override
        public int compare(Student o1, Student o2) {
           int r1 = o1.getRollNo();
           int r2 = o2.getRollNo();
           return r1 - r2;
        }
    }
}
