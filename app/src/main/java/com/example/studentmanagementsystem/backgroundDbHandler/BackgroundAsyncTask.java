package com.example.studentmanagementsystem.backgroundDbHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.UPDATE_STUDENT;

public class BackgroundAsyncTask extends AsyncTask<Object,Void,Void> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private SQLiteDatabase db;

    public BackgroundAsyncTask(Context context) {
        this.context=context;
    }

    @Override
    protected Void doInBackground(Object... objects) {

        Student studentForDb = (Student) objects[0];
        String operationOnStudent = (String) objects[1];
        int oldIdOfStudent = (int) objects[2];

        DatabaseHelper dbHelper=new DatabaseHelper(context);
        db=dbHelper.getWritableDatabase();

        switch (operationOnStudent){
            case ADD_STUDENT:
                dbHelper.addStudentToDB(studentForDb);
                break;
            case UPDATE_STUDENT:
                dbHelper.updateStudentInDB(studentForDb,oldIdOfStudent);
                break;
            case DELETE_STUDENT:
                dbHelper.deleteStudentFromDB(studentForDb.getRollNo());
            default:
                break;
        }
        db.close();
        return null;
    }
}
