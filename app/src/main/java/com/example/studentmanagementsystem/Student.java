package com.example.studentmanagementsystem;

import java.io.Serializable;
import java.util.Scanner;

public class Student implements Serializable {
    private String rollNo;
    private String studentName;

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    Student(String rollNo, String studentName) {
        this.rollNo = rollNo;
        this.studentName = studentName;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getStudentName() {
        return studentName;
    }

}

