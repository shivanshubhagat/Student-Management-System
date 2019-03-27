package com.example.studentmanagementsystem.backgroundDbHandler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.FILTER_ACTION_KEY;
import static com.example.studentmanagementsystem.util.Constants.UPDATE_STUDENT;

public class BackgroundService extends Service {

    public BackgroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        int oldIdOfStudent = 0 ;

        intent.setAction(FILTER_ACTION_KEY);
        String echoMessage = "Broadcast Receiver";

        if (intent.hasExtra("oldIdOfStudent")) {
            oldIdOfStudent = intent.getIntExtra("oldIdOfStudent",0);
        }

        String operationOnStudent = intent.getStringExtra("operation");
        Student studentForDb = intent.getParcelableExtra("studentForDb");

        switch (operationOnStudent) {
            case ADD_STUDENT:
                databaseHelper.addStudentToDB(studentForDb);
                Toast.makeText(this, "Student Added via Service", Toast.LENGTH_SHORT).show();
                break;
            case UPDATE_STUDENT:
                databaseHelper.updateStudentInDB(studentForDb, oldIdOfStudent);
                Toast.makeText(this, "Student Updated via Service", Toast.LENGTH_SHORT).show();
                break;
            case DELETE_STUDENT:
                databaseHelper.deleteStudentFromDB(studentForDb.getRollNo());
                Toast.makeText(this, "Student Deleted via Service", Toast.LENGTH_SHORT).show();
                break;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage",echoMessage));
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}