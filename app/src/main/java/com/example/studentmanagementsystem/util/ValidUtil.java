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
    private final static String NAME_REGEX="^[a-zA-Z]{3,25}$";
    private final static String ID_REGEX="^[1-9][0-9]*$";

    public static boolean isValidName(final String NAME){

        final Pattern pattern = Pattern.compile(NAME_REGEX);;

        Matcher matcher = pattern.matcher(NAME);

        return matcher.matches();
    }

    public static boolean isValidId(final String ID){
        // String regex="^[1-9][0-9]*$";
        final Pattern pattern = Pattern.compile(ID_REGEX);;

        Matcher matcher = pattern.matcher(ID);

        return (matcher.matches());

    }

    /**
     * This method used to check enter Roll No is duplicate or not
     * Used for validation purposes
     *
     * @param rollNo of string type
     * @return true if Roll no is present false if not present
     */
    public static boolean isCheckValidId(ArrayList<Student> sudentArrayList, final String rollNo){
        for(Student validStudent:sudentArrayList){
            if(validStudent.getRollNo().equals(rollNo)){
                return true;
            }
        }
        return false;
    }
}