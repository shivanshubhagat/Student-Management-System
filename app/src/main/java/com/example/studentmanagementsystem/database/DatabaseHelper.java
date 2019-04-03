package com.example.studentmanagementsystem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;

import static com.example.studentmanagementsystem.util.Constants.COLUMN_ROLL_NO;
import static com.example.studentmanagementsystem.util.Constants.COLUMN_STUDENT_NAME;
import static com.example.studentmanagementsystem.util.Constants.DATABASE_NAME;
import static com.example.studentmanagementsystem.util.Constants.DATABASE_VERSION;
import static com.example.studentmanagementsystem.util.Constants.TABLE_STUDENT;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_STUDENT + "(" + COLUMN_ROLL_NO + "  INTEGER PRIMARY KEY, " + COLUMN_STUDENT_NAME + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }

    public void addStudentToDB(Student student) {
        //assigning studentValues to table
        ContentValues studentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        studentValues.put(COLUMN_ROLL_NO, student.getRollNo());
        studentValues.put(COLUMN_STUDENT_NAME, student.getStudentName());
        db.insert(TABLE_STUDENT, null, studentValues);
        db.close();
    }

    public void deleteStudentFromDB(int studentRollNo) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_STUDENT, COLUMN_ROLL_NO + " = ?", new String[]{String.valueOf(studentRollNo)});
        db.close();
    }

    public void updateStudentInDB(Student studentToUpdate, int oldRollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " UPDATE " + TABLE_STUDENT + " SET " + COLUMN_ROLL_NO + " =  "
                + studentToUpdate.getRollNo() + " WHERE " + COLUMN_ROLL_NO
                + " = " + oldRollNo + ";";
        db.execSQL(query);
        //db.update(TABLE_STUDENT, studentValues, whereClause, new String[]{String.valueOf(oldRollNo)});
    }

    public ArrayList<Student> getStudentsFromDB() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_STUDENT, null, null, null, null, null, null);

        ArrayList<Student> studentListFromDB = new ArrayList<>();
        String studentName;
        int rollNo;

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return studentListFromDB;
        } else
            do {
                rollNo = cursor.getInt(0);
                studentName = cursor.getString(1);
                Student stu = new Student(rollNo, studentName);
                studentListFromDB.add(stu);
            } while (cursor.moveToNext());
        cursor.close();
        db.close();
        return studentListFromDB;
    }
}
