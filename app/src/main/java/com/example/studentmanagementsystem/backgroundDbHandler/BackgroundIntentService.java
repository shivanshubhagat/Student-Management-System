package com.example.studentmanagementsystem.backgroundDbHandler;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.studentmanagementsystem.database.DatabaseHelper;
import com.example.studentmanagementsystem.model.Student;

import static com.example.studentmanagementsystem.util.Constants.ADD_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.DELETE_STUDENT;
import static com.example.studentmanagementsystem.util.Constants.FILTER_ACTION_KEY;
import static com.example.studentmanagementsystem.util.Constants.UPDATE_STUDENT;


public class BackgroundIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundIntentService(String name) {
        super(name);
    }

    public BackgroundIntentService() {
        super("nothing");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();
        int oldIdOfStudent = 0;

        intent.setAction(FILTER_ACTION_KEY);
        String echoMessage = "Broadcast Receiver";
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage",echoMessage));

        if(intent.hasExtra("oldIdOfStudent")) {
            oldIdOfStudent = intent.getIntExtra("oldIdOfStudent",0);
        }
        String operationOnStudent = intent.getStringExtra("operation");

        Student studentForDb = intent.getParcelableExtra("studentForDb");

        switch (operationOnStudent) {
            case ADD_STUDENT:
                databaseHelper.addStudentToDB(studentForDb);
                break;
            case UPDATE_STUDENT:
                databaseHelper.updateStudentInDB(studentForDb,oldIdOfStudent);
                break;
            case DELETE_STUDENT:
                databaseHelper.deleteStudentFromDB(studentForDb.getRollNo());
                break;
        }
    }
}