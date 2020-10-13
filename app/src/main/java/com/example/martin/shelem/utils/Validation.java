package com.example.martin.shelem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean emailIsValid(String email) {
        Pattern regexPattern;
        Matcher regMatcher;
        regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        regMatcher   = regexPattern.matcher(email);
        if(regMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
