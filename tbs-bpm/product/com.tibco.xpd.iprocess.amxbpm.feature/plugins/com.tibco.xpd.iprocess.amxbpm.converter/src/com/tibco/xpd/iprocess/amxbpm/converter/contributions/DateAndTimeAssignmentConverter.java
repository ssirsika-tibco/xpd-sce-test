/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Set;

import com.tibco.xpd.iprocess.amxbpm.converter.contributions.DateAndTimeAssignmentConversionContribution.DATE_TIME_DATA_TYPE;

/**
 * Converter do deal with the Date and Time field assignment, so that Date and
 * Time field are not assigned by reference.
 * <p>
 * Suppose the script Date assignment was dateField = dateField1; then after
 * conversion the script will be converted to dateField =
 * DateTimeUtil.createDate(dateField1.toXMLFormat());
 * <p>
 * Similarly Time script timeField = timeField1 will be converted to timeField =
 * DateTimeUtil.createTime(timeField1.toXMLFormat())
 * 
 * @author kthombar
 * @since 31-Jul-2014
 */
public class DateAndTimeAssignmentConverter {

    /**
     * Stores the final converted String
     */
    private String convertedString = null;

    /**
     * Stores the input string which we wish to convert
     */
    private String stringToConvert = null;

    /**
     * Enum literal which helps to keep track of which field type we are trying
     * to convert.
     */
    private DATE_TIME_DATA_TYPE dateOrTime;

    /**
     * The Date or Time Field names which we are trying to convert.
     */
    private Set<String> timeOrDateFieldNames;

    /**
     * Stores the removed Semi colon or comment.
     */
    private String semiColonOrComment = null;

    /**
     * * Converter do deal with the Date and Time field assignment, so that Date
     * and Time field are not assigned by reference.
     * <p>
     * Suppose the script Date assignment was dateField = dateField1; then after
     * conversion the script will be converted to dateField =
     * DateTimeUtil.createDate(dateField1.toXMLFormat());
     * <p>
     * Similarly Time script timeField = timeField1 will be converted to
     * timeField = DateTimeUtil.createTime(timeField1.toXMLFormat())
     * 
     * @param stringToConvert
     *            the String we wish to convert
     * @param dateOrTime
     *            Enum to keep track of which type of field we are converting
     *            (either Date or Time)
     * @param timeOrDateFieldNames
     *            the Date or Time field names.
     */
    public DateAndTimeAssignmentConverter(String stringToConvert,
            DATE_TIME_DATA_TYPE dateOrTime, Set<String> timeOrDateFieldNames) {

        this.stringToConvert = stringToConvert;
        this.dateOrTime = dateOrTime;
        this.timeOrDateFieldNames = timeOrDateFieldNames;

        /*
         * remove the string after first semi-colon or comment as we do not wish
         * to convert it
         */
        String currentString = removeSemiColonAndComments(this.stringToConvert);

        /* convert the String */
        String stringAfterConversion = convertString(currentString);

        if (stringAfterConversion == null) {
            /*
             * If the converted String is null the return the original input
             * string , because null String indicates that there are nothing to
             * convert or the Script was not as expected.
             */
            convertedString = stringToConvert;
        } else {
            /*
             * append back the removed semi-colon or comments to the converted
             * string.
             */
            appendSemiColonAndComments(stringAfterConversion);
        }
    }

    /**
     * gets the converted Script.
     * 
     * @param inputScript
     *            the input String (after semicolon and comments removed.)
     * @return the converted Script or <code>null</code> if the passed Script is
     *         empty or if the Script does not have the expected Date and Time
     *         fields.
     */
    private String convertString(String inputScript) {

        StringBuffer stringAfterConversion = new StringBuffer();

        if (inputScript.isEmpty()) {
            return null;
        }

        /*
         * As we only deal with simple Date and Time field assignment (i.e.
         * field1 = field2), so the best way to deal with the input string is to
         * split it across the assignment operator.
         */
        String[] splittedString = inputScript.split("="); //$NON-NLS-1$

        if (splittedString.length != 2) {
            /*
             * we are splitting across assignment operator so the string will
             * have only 2 parts, LHS and RHS
             */
            return null;
        }
        /* string before '=' */
        String stringBeforeAssignmentOperator = splittedString[0].trim();
        /* string after '=' */
        String stringAfterAssignmentOperator = splittedString[1].trim();

        if (timeOrDateFieldNames.contains(stringBeforeAssignmentOperator)
                && timeOrDateFieldNames.contains(stringAfterAssignmentOperator)) {
            /*
             * Check if both the string before and after '=' is the expected
             * Date or Time field name
             */
            stringAfterConversion.append(stringBeforeAssignmentOperator);
            stringAfterConversion.append(" = "); //$NON-NLS-1$

            if (DATE_TIME_DATA_TYPE.DATE.equals(dateOrTime)) {

                stringAfterConversion.append("DateTimeUtil.createDate("); //$NON-NLS-1$

            } else if (DATE_TIME_DATA_TYPE.TIME.equals(dateOrTime)) {

                stringAfterConversion.append("DateTimeUtil.createTime("); //$NON-NLS-1$

            }

            stringAfterConversion.append(stringAfterAssignmentOperator);
            stringAfterConversion.append(".toXMLFormat()"); //$NON-NLS-1$
            stringAfterConversion.append(")"); //$NON-NLS-1$

        } else {
            return null;
        }

        return stringAfterConversion.toString();
    }

    /**
     * Removes any semiColon ';' or comments '//' found in the input string
     * which are outside single '' or double "" quotes. The String after the
     * semicolon or comments is stored in
     * {@link DateAndTimeAssignmentConverter#semiColonOrComment} so that it can
     * be appended back after the conversion.
     * 
     * @param string
     * @return String which is free from any semicolons and comments which are
     *         outside of quotes.
     */
    private String removeSemiColonAndComments(String string) {

        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < string.length(); idx++) {

            char charAt = string.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {

                /* Ignore escaped next character in string. */
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {

                /* Ignore escaped next character in string. */
                idx++;
            } else if (charAt == '"') {

                if (!isDoubleQuotes) {

                    isDoubleQuotes = true;

                } else {

                    isDoubleQuotes = false;
                }
            } else if (charAt == '\'') {

                if (!isSingleQuotes) {

                    isSingleQuotes = true;

                } else {

                    isSingleQuotes = false;
                }
            }

            if (isDoubleQuotes || isSingleQuotes) {
                continue;
            }

            if (charAt == ';') {
                semiColonOrComment = string.substring(idx, string.length());
                string = string.substring(0, idx).trim();
                break;

            } else if (charAt == '/') {
                if (string.charAt(idx + 1) == '/') {
                    semiColonOrComment = string.substring(idx, string.length());
                    string = string.substring(0, idx).trim();
                    break;
                }
            }
        }
        return string;

    }

    /**
     * appends back the semicolon ';' and/or comment '//' that was removed by
     * {@link DateAndTimeAssignmentConverter#removeSemiColonAndComments(String)}
     * 
     * @param newString
     */
    private void appendSemiColonAndComments(String newString) {

        StringBuffer finalConvertedString = new StringBuffer();

        finalConvertedString.append(newString.trim());

        if (semiColonOrComment != null) {
            /*
             * if the original string had a semicolon ';' or comment '//' at the
             * end then append it
             */
            finalConvertedString.append(semiColonOrComment);
        }

        convertedString = finalConvertedString.toString();

    }

    /**
     * 
     * @return the converted Script.
     */
    public String getConvertedScript() {
        return convertedString;
    }

}
