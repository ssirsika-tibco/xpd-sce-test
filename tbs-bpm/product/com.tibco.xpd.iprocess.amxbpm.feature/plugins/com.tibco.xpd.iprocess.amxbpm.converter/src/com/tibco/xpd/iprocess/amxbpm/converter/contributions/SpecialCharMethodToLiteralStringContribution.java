/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.List;

/**
 * 
 * Contribution that replace all the iProcess SPECIALCHARS("string") method by
 * the String Literal contained in this method.
 * <p>
 * example - if the IPM had the following script text :=
 * SPECIALCHARS("Your test results are\r\n English=80 \r\n Maths=90") then after
 * conversion to AMX-BPM the script will be
 * <p>
 * text = "Your test results are\r\n English=80 \r\n Maths=90";
 * 
 * @author kthombar
 * @since 30-Jun-2014
 */
public class SpecialCharMethodToLiteralStringContribution extends
        AbstractiProcessScriptMethodConversionContribution {

    /**
     * Calls the super constructor with the method name to convert
     */
    public SpecialCharMethodToLiteralStringContribution() {
        super("IPEConversionUtil.SPECIALCHARS", 1); //$NON-NLS-1$
    }

    /**
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractiProcessScriptMethodConversionContribution#convertEachScriptLine(java.lang.String,
     *      int, int, int, java.util.List)
     * 
     * @param stringtoConvert
     * @param startOfMethodIndex
     * @param startBracketIndex
     * @param endBracketIndex
     * @param listOfParametersInOrder
     * @return
     */
    @Override
    protected String convertEachScriptLine(String stringtoConvert,
            int startOfMethodIndex, int startBracketIndex, int endBracketIndex,
            List<String> listOfParametersInOrder) {
        /* get the string before the method call */
        String stringBeforeSpecialcharMethodCall =
                stringtoConvert.substring(0, startOfMethodIndex);
        /* get the string after the method call */
        String stringAfterSpecialcharMethodCall =
                stringtoConvert.substring(endBracketIndex + 1,
                        stringtoConvert.length());

        StringBuffer buffer = new StringBuffer();

        buffer.append(stringBeforeSpecialcharMethodCall);
        /* the first parameter is the literal String */
        buffer.append(removeAdditionalBackslash(listOfParametersInOrder.get(0)));
        buffer.append(stringAfterSpecialcharMethodCall);
        return buffer.toString();
    }

    /**
     * Removes the additional backslashes. Example : if the String is as follows
     * "Hi \\n my name \\r is ruth" , then this method will return
     * "Hi \n my name \r is ruth"
     * 
     * @param literalString
     * @return String with any additional backslashes removed.
     */
    private String removeAdditionalBackslash(String literalString) {

        String trimmedLiteralString = literalString.trim();
        int stringLength = trimmedLiteralString.length();
        StringBuffer sb = new StringBuffer();

        if (trimmedLiteralString.charAt(0) == '"'
                && trimmedLiteralString.charAt(stringLength - 1) == '"') {

            /*
             * we only wish to work on string that start and end with double
             * quotes '"' . This is because there can be a Script Util method
             * within a Script Util method. i.e.
             * SPECIALCHARS(SPECIALCHARS("hello")) , so will work only when we
             * have the "hello" string with us.
             */
            for (int idx = 0; idx < stringLength; idx++) {
                char charAt = trimmedLiteralString.charAt(idx);
                sb.append(charAt);
                if ((idx < stringLength - 1) && charAt == '\\'
                        && trimmedLiteralString.charAt(idx + 1) == '\\') {
                    idx++;
                }
            }

        } else {
            return trimmedLiteralString;
        }

        return sb.toString();
    }
}
