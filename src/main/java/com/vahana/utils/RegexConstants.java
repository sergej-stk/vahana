package com.vahana.utils;

public class RegexConstants {
    //+49 170 1234567
    //+1-800-123 4567
    //(+44) 20 7946 0958
    //+33 1 70 12 34 56
    //+49-40-123 4567
    //(+1) 123 456 7890
    //+41 (0) 44 567 8901
    public static final String PHONE_NUMBER_REGEX = "^\\+?[0-9]{1,3}?[ \\-]?\\(?[0-9]{2,4}\\)?[ \\-]?[0-9]{3,6}[ \\-]?[0-9]{3,6}$";
}
