package com.example.studentmanagementsystem.util;

public class Constants {
    //constants for dialogue switch
    public final static int VIEW = 0;
    public final static int UPDATE = 1;
    public final static int DELETE = 2;
    public final static String[] ITEMS = {"View", "Edit", "Delete"};


    //start activity for result codes
    public static final int CODE_TO_ADD_STUDENT = 12;
    public static final int CODE_TO_VIEW_STUDENT = 101;
    public static final int CODE_TO_UPDATE_STUDENT = 102;

    //regex codes
    public static final String VALID_NAME ="\\b[a-zA-Z]+\\s[a-zA-Z]+\\b";
    public static final String VALID_ROLL_NO="(?:\\b|-)([1-9]{1,2}[0]?|100)\\b";
}
