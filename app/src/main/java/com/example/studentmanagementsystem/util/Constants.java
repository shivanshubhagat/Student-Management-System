package com.example.studentmanagementsystem.util;

public class Constants {
    //constants for dialogue switch
    public final static int VIEW_CASE = 0;
    public final static String VIEW = "VIEW";

    public final static int UPDATE_CASE = 1;
    public final static String UPDATE = "UPDATE";

    public final static int DELETE_CASE = 2;
    public final static String DELETE = "Deleted";
    public final static String[] OPTIONS = {"View", "Update", "Delete"};

    public final static String[] ITEMS = {"View", "Edit", "Delete"};

    //start activity for result codes
    public static final int CODE_TO_ADD_STUDENT = 12;
    public static final int CODE_TO_VIEW_STUDENT = 101;
    public static final int CODE_TO_UPDATE_STUDENT = 102;

    //regex codes
    public static final String VALID_NAME ="\\b[a-zA-Z]+\\s[a-zA-Z]+\\b";
    public static final String VALID_ROLL_NO="(?:\\b|-)([1-9]{1,2}[0]?|100)\\b";

    //DATABASE
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "studentDatabase.db";
    public static final String TABLE_STUDENT = "students";
    public static final String COLUMN_ROLL_NO = "rollNo";
    public static final String COLUMN_STUDENT_NAME = "name";

    //Strings
    public static final String ADDED = "ADDED";
    public static final String ADDED_STUDENT = "ADDED_STUDENT";
    public static final String UPDATED_STUDENT = "UPDATED_STUDENT";
    public static final String SORTED_BY_NAME = "Sorted by Name";
    public static final String SORTED_BY_ROLL_NO = "Sorted by RollNo";

    //constants for dialogue switch on data handling for students
    public final static int USE_SERVICE = 0;
    public final static int USE_INTENT_SERVICE = 1;
    public final static int USE_ASYNC_TASK = 2;
    public final static String[] SAVING_OPTIONS = {"Service", "Intent Service", "Async Task"};

    //BR KEY
    public final static String FILTER_ACTION_KEY = "any key";

    //operation on student
    public final static String ADD_STUDENT = "Add";
    public final static String UPDATE_STUDENT = "Update";
    public final static String DELETE_STUDENT = "Delete";

    public final static int SHOW_STUDENT_CODE = 0;
    public final static int ADD_STUDENT_CODE = 1;
}
