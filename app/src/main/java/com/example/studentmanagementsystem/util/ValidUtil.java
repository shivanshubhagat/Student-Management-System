package com.example.studentmanagementsystem.util;

import com.example.studentmanagementsystem.model.Student;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for validation using Regex
 *
 * 2 method
 * @first for validation name
 * @second for Validation Id
 */
public final class ValidUtil {
    private final static String NAME_REGEX="\\b[a-zA-Z]+\\s[a-zA-Z]+\\b";
    private final static String ROLL_NO_REGEX ="^([1-9][0-9]{0,2}|1000)$";

    public static boolean isValidName(final String NAME){
        final Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(NAME);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean isValidId(final String ID){
        final Pattern pattern = Pattern.compile(ROLL_NO_REGEX);
        Matcher matcher = pattern.matcher(String.valueOf(ID));
        return (matcher.matches());
    }



    /**
     * This method used to check enter Roll No is duplicate or not
     * Used for validation purposes
     *
     * @param rollNo of string type
     * @return true if Roll no is present false if not present
     */
    public static boolean isUniqueRollNo(ArrayList<Student> studentArrayList, final int rollNo){
        for(Student validStudent:studentArrayList){
            if(validStudent.getRollNo() == rollNo){
                return false;
            }
        }
        return true;
    }
}