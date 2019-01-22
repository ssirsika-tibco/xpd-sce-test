/* 
 ** 
 **  MODULE:             $RCSfile: StringUtils.java $ 
 **                      $Revision: 1.0 $ 
 **                      $Date: 2005-08-21 $ 
 ** 
 **  DESCRIPTION:           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc, All Rights Reserved.
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */
package com.tibco.xpd.simulation.preprocess.util;

public class StringUtils {

    private StringUtils() {
    }

    /**
     * Change to upper case the first letter in every world and change to lower
     * case all other letters.
     * 
     * @param s
     *            strng to change case.
     * @return string with upper first and other lower letters in each word.
     */
    public static String toUpperFirstInEveryWord(String s) {
        if (s == null) {
            return null;
        }
        int length = s.length();
        if (length == 0) {
            return s;
        }
        StringBuffer resultVal = new StringBuffer(length);
        resultVal.append(Character.toUpperCase(s.charAt(0)));
        for (int i = 1; i < length; i++) {
            if (s.charAt(i - 1) == ' ') {
                resultVal.append(Character.toUpperCase(s.charAt(i)));
            } else {
                resultVal.append(Character.toLowerCase(s.charAt(i)));
            }
        }
        return resultVal.toString();
    }

    public static String remainOnlyLetersOrDigits(String s) {
        if (s == null) {
            return null;
        }
        int length = s.length();
        if (length == 0) {
            return s;
        }
        StringBuffer resultVal = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                resultVal.append(s.charAt(i));
            }
        }
        return resultVal.toString();
    }

    public static String toVariableLikeString(String s) {
        return toLowerFirstChar(remainOnlyLetersOrDigits(toUpperFirstInEveryWord(s)));
    }
    
    private static String toLowerFirstChar(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0,1).toLowerCase() + s.substring(1);
    }
}
