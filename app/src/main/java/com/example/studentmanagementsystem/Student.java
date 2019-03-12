package com.example.studentmanagementsystem;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Scanner;

public class Student implements Parcelable {
    //params
    private String rollNo;
    private String studentName;

    //Constructor 1
    public Student(String rollNo, String studentName) {
        this.rollNo = rollNo;
        this.studentName = studentName;
    }

    // Constructor 2 (Parcelable)
    protected Student(Parcel in) {
        rollNo = in.readString();
        studentName = in.readString();
    }
    //Setter and getter
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

    //parcelable functions
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rollNo);
        dest.writeString(studentName);
    }
}

