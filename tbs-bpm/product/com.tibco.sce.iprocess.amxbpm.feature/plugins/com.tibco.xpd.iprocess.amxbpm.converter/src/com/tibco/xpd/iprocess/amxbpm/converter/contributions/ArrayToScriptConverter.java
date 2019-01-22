/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

/**
 * Main converter that removes any use of arrays[] by Script Util methods
 * ScriptUtil.getArrayElement and ScriptUtil.setArrayElement.
 * <p>
 * example
 * <p>
 * 1. Array [ count ] = a; will be replaced by ScriptUtil.setArrayElement(Array,
 * count , a);
 * <p>
 * 2. var a = Array [ count ]; will be replaced by var a =
 * ScriptUtil.getArrayElement(Array , count);
 * 
 * @author kthombar
 * @since 26-Apr-2014
 */
public class ArrayToScriptConverter {

    private String stringToConvert; /* the string to convert */

    private String convertedString; /* the converted string */

    private final String SCRIPT_UTIL_ARRAY_GETTER =
            "ScriptUtil.getArrayElement("; //$NON-NLS-1$

    private final String SCRIPT_UTIL_ARRAY_SETTER =
            "ScriptUtil.setArrayElement("; //$NON-NLS-1$  

    private final String COMMA = ","; //$NON-NLS-1$

    private final String CLOSING_BRACKET = ")"; //$NON-NLS-1$

    private String semiColonOrComment = null;

    /**
     * User should call this construct if he wishes to convert any array
     * assignment to its ScriptUtil equivalent
     * 
     * @param stringToConvert
     *            the String/Expression containing array assignment
     * @param arrayNames
     *            all the arrays available
     */
    public ArrayToScriptConverter(String stringToConvert) {
        this.stringToConvert = stringToConvert;
        String currentString = removeSemiColonAndComments(this.stringToConvert);
        while (true) {
            String newString = convertString(currentString);
            if (newString.equals(currentString)) {
                /* no changes made so must have finished. */
                appendSemiColonAndComments(newString);
                break;
            }
            currentString = newString;
        }
    }

    /**
     * appends back the semicolon ';' and/or comment '//' that was removed by
     * {@link ArrayToScriptConverter#removeSemiColonAndComments(String)}
     * 
     * @param newString
     */
    private void appendSemiColonAndComments(String newString) {

        /*
         * if the array name is null that indicates that we have successfully
         * converted all the arrays to their equivalent ScriptUtil methods and
         * so it is time to return the converted string.
         */
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
     * Removes any semiColon ';' or comments '//' found in the input string
     * which are outside single '' or double "" quotes. The String after the
     * semicolon or comments is stored in
     * {@link ArrayToScriptConverter#semiColonOrComment} so that it can be
     * appended back after the conversion.
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
     * The main conversion method.
     * <p>
     * This method does the following sequentially.
     * 
     * <p>
     * 1. Moves through the String untill it finds the first open bracket '['.
     * <p>
     * 2. After we get the first '[' we find the array name in the string prior
     * to '['
     * <p>
     * 3. Next we fetch the closing bracket ']' for opening bracket we found in
     * step 1
     * <p>
     * 4. the string between the opening bracket and the closing bracket is our
     * array key
     * <p>
     * 5. Next we see if the closing bracket is followed by an assignment
     * operator '=', if so that indicates that we are setting the value in
     * array, else we are getting the value from the array.
     * <p>
     * 6. Finally we form a converted String , and keep on repeating the steps 1
     * to 5 untill all the arrays are converted!
     * 
     * 
     * @param stringToConvert
     */
    @SuppressWarnings("nls")
    private String convertString(String stringToConvert) {

        if (stringToConvert.isEmpty()) {
            /* if string to convert is empty then return immediately. */
            return stringToConvert;
        }

        /*
         * stores the expression before the first array found
         */
        String expressionBeforeArray = null;
        /* stores the array name */
        String arrayName = null;
        /* keeps track of data being set in array or being fetched from array */
        boolean hasAssignmentOperator = false;
        /*
         * stores the expression after the end of the first array
         */
        String expressionAfterArray = null;
        /* stores the array key */
        String arrayKey = null;
        /*
         * keeps a track of the brackets , as we want to exactly find the
         * closing bracket for the opening bracket and ignore the intermediate
         * closing brackets.
         */
        int levelOfBracket = 0;
        /* keeps track of the index of the array key */
        int arrayKeyStartIndex = 0;
        /* Keeps tracks of double quotes found in the String to convert */
        boolean isDoubleQuotes = false;
        /* Keeps tracks of single quotes found in the String to convert */
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < stringToConvert.length(); idx++) {

            char charAt = stringToConvert.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {
                // Ignore escaped next character in string.
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {
                // Ignore escaped next character in string.
                idx++;
            }

            else if (charAt == '"') {
                if (!isDoubleQuotes) {
                    isDoubleQuotes = true;

                } else {
                    // Check if escaped

                    // Found end of string
                    isDoubleQuotes = false;

                }
            }

            else if (charAt == '\'') {
                if (!isSingleQuotes) {
                    isSingleQuotes = true;

                } else {
                    isSingleQuotes = false;
                }
            }

            if (isDoubleQuotes || isSingleQuotes) {
                /*
                 * if we are inside of double or single quotes then we should
                 * not keep track of brackets.
                 */
                continue;
            }

            if (charAt == '[') {

                if (levelOfBracket == 0) {
                    /*
                     * if this is the first array found then extract the array
                     * name, the expression before the array and keep track of
                     * the opening index.
                     */
                    String substring = stringToConvert.substring(0, idx);
                    arrayName = getArrayName(substring);
                    if (arrayName != null) {
                        int lastIndexOf = substring.lastIndexOf(arrayName);
                        if (lastIndexOf > 0) {
                            expressionBeforeArray =
                                    stringToConvert.substring(0, lastIndexOf);
                        }
                        arrayKeyStartIndex = idx + 1;

                        idx++;
                    }
                }
                levelOfBracket++;/* keep incrementing as we find more brackets */
            } else if (charAt == ']') {
                if (arrayName == null) {
                    /*
                     * If we get a closing bracket and we havent found the
                     * array, that indicates something went wrong, so break the
                     * operation.
                     */
                    break;
                }
                levelOfBracket--;
                if (levelOfBracket == 0) {
                    /*
                     * when bracketTracker == 0 , that indicates that we found
                     * the exact closing bracket for the opening bracket. Hence
                     * we extract the key and the expression after the bracket
                     * is over.
                     */
                    arrayKey =
                            stringToConvert.substring(arrayKeyStartIndex, idx);

                    for (int i = idx + 1; i < stringToConvert.length(); i++) {
                        char charAt2 = stringToConvert.charAt(i);
                        /*
                         * Check if the array is followed by an assignment
                         * operator '=' , if yes then that indicates we are
                         * inserting data in the array.
                         */
                        if (charAt2 == ' ') {
                            continue;
                        } else if (charAt2 == '=') {
                            if (stringToConvert.charAt(i + 1) != '=') {
                                expressionAfterArray =
                                        stringToConvert.substring(i + 1,
                                                stringToConvert.length());
                                hasAssignmentOperator = true;

                                break;
                            } else {
                                expressionAfterArray =
                                        stringToConvert.substring(idx + 1,
                                                stringToConvert.length());

                                break;
                            }
                        } else {
                            expressionAfterArray =
                                    stringToConvert.substring(idx + 1,
                                            stringToConvert.length());

                            break;
                        }
                    }
                    if (expressionAfterArray != null) {
                        break;
                    }
                }
            }
        }

        if (arrayName != null) {
            /*
             * if the array name was not null that indicates that there are some
             * arrays still to convert.
             */
            StringBuffer createScriptUtilString = new StringBuffer();

            if (expressionBeforeArray != null) {
                /* append the expression before array */
                createScriptUtilString.append(expressionBeforeArray);
            }

            if (arrayName != null && arrayKey != null) {
                if (hasAssignmentOperator) {
                    /*
                     * if we found an assignment operator then set the value to
                     * the array
                     */
                    createScriptUtilString.append(SCRIPT_UTIL_ARRAY_SETTER);
                    createScriptUtilString.append(arrayName.trim());
                    createScriptUtilString.append(" ");
                    createScriptUtilString.append(COMMA);
                    createScriptUtilString.append(" ");
                    createScriptUtilString.append(arrayKey.trim());
                    createScriptUtilString.append(" ");
                    createScriptUtilString.append(COMMA);
                    createScriptUtilString.append(" ");
                    createScriptUtilString.append(expressionAfterArray.trim());
                    createScriptUtilString.append(CLOSING_BRACKET);
                } else {
                    /*
                     * if no assignment operator was found then we get the data
                     * from array
                     */
                    createScriptUtilString.append(SCRIPT_UTIL_ARRAY_GETTER);
                    createScriptUtilString.append(arrayName.trim());
                    createScriptUtilString.append(" ");
                    createScriptUtilString.append(COMMA);
                    createScriptUtilString.append(" ");
                    createScriptUtilString.append(arrayKey.trim());
                    createScriptUtilString.append(CLOSING_BRACKET);

                    if (expressionAfterArray != null) {
                        createScriptUtilString.append(expressionAfterArray);
                    }
                }
            }
            /*
             * Recursively call this method untill all the arrays are converted!
             */
            return createScriptUtilString.toString();
        }

        return stringToConvert;
    }

    /**
     * 
     * @return the converter String which has all array assignments replaced by
     *         its ScriptUtil equivalent methods.
     */
    public String getConvertedString() {
        return convertedString;
    }

    /**
     * Checks for the array name in the passed string by traversing the string
     * in reverse order. Keeps traversing the string in reverse order as long as
     * it finds character (a-zA-Z0-9) , underscore "_", whitespace " " or
     * reaches the start of the string. If we come across any character which is
     * not expected(or have reached the start od string) that indicates we have
     * found the array name
     * 
     * @param substringBeforeOpeningBracket
     * @return
     */
    private String getArrayName(String substringBeforeOpeningBracket) {

        int stringLength = substringBeforeOpeningBracket.length();

        String arrayName = null;

        for (int idx = stringLength - 1; idx >= 0; idx--) {

            char charAt = substringBeforeOpeningBracket.charAt(idx);

            if (idx == 0) {
                arrayName = substringBeforeOpeningBracket.trim();
            } else if (!Character.isLetter(charAt)
                    && !Character.isDigit(charAt)
                    && !Character.isWhitespace(charAt) && charAt != '_') {

                arrayName =
                        substringBeforeOpeningBracket.substring(idx + 1,
                                stringLength).trim();
            }
            if (arrayName != null) {
                return arrayName;
            }
        }
        return arrayName;
    }

    /*---------------------------TESTS------------------------*/

    /**
     * This is for testing purpose.
     * 
     * @param args
     */
    @SuppressWarnings("nls")
    public static void main(String[] args) {

        /* Test 1 Quotes and comments in the same line */
        String inputString =
                "Array1[a+b]+ '//Not a comment' + MyArray[20] ==c;//Comment";

        // Array1[a+b] + "this is a quote \" tada! " + Array1[c+d] + 'Array1
        // [e]'==c;//Comment

        String expectedOutput =
                "ScriptUtil.getArrayElement(Array1 , a+b)+ '//Not a comment' + ScriptUtil.getArrayElement(MyArray , 20) ==c;//Comment";

        ArrayToScriptConverter converter =
                new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 2 only comments */

        inputString = "//if (LINETYPE [ LOOPCOUNT ] == null)";

        expectedOutput = "//if (LINETYPE [ LOOPCOUNT ] == null)";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 3 siemens example */

        inputString =
                "\"LINEALRT[0] from reset timer sub is: \" + IpeScriptUtil.STR(LINEALRT [ 0 ], 0);";

        expectedOutput =
                "\"LINEALRT[0] from reset timer sub is: \" + IpeScriptUtil.STR(ScriptUtil.getArrayElement(LINEALRT , 0), 0);";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 4 siemens example */

        inputString =
                "\"INST_TM[0] value:\" + IpeScriptUtil.TIMESTR(INST_TM [ 0 ]) + \" INST_DT[0] value :\" + IpeScriptUtil.DATESTR(INST_DT [ 0 ]);";

        expectedOutput =
                "\"INST_TM[0] value:\" + IpeScriptUtil.TIMESTR(ScriptUtil.getArrayElement(INST_TM , 0)) + \" INST_DT[0] value :\" + IpeScriptUtil.DATESTR(ScriptUtil.getArrayElement(INST_DT , 0));";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 5 */

        inputString = "LINETYPE [ LOOPCOUNT ] = null;";

        expectedOutput =
                "ScriptUtil.setArrayElement(LINETYPE , LOOPCOUNT , null);";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 6 */
        inputString =
                "if (LINE1TYPE == LINETYPE [ LOOPCOUNT ] && LINE1DATE == INST_DT [ LOOPCOUNT ] && LINE1TIME == INST_TM [ LOOPCOUNT ])";

        expectedOutput =
                "if (LINE1TYPE == ScriptUtil.getArrayElement(LINETYPE , LOOPCOUNT) && LINE1DATE == ScriptUtil.getArrayElement(INST_DT , LOOPCOUNT) && LINE1TIME == ScriptUtil.getArrayElement(INST_TM , LOOPCOUNT))";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 7 */

        inputString = "if (LINETYPE [ LOOPCOUNT ] == null)";

        expectedOutput =
                "if (ScriptUtil.getArrayElement(LINETYPE , LOOPCOUNT) == null)";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 8 */

        inputString =
                "if (LINE1TYPE == LINETYPE [ LOOPCOUNT ] && LINE1DATE == INST_DT [ LOOPCOUNT ] && LINE1TIME == INST_TM [ LOOPCOUNT ])";

        expectedOutput =
                "if (LINE1TYPE == ScriptUtil.getArrayElement(LINETYPE , LOOPCOUNT) && LINE1DATE == ScriptUtil.getArrayElement(INST_DT , LOOPCOUNT) && LINE1TIME == ScriptUtil.getArrayElement(INST_TM , LOOPCOUNT))";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 9 */

        inputString = "IpeScriptUtil.STR(LINEALRT [ 1 ], 0);";

        expectedOutput =
                "IpeScriptUtil.STR(ScriptUtil.getArrayElement(LINEALRT , 1), 0);";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 10 */

        inputString =
                "L1_WAIT_DATE = IpeScriptUtil.CALCDATE(INST_DT [ 1 ], 4, 0, 0, 0);";

        expectedOutput =
                "L1_WAIT_DATE = IpeScriptUtil.CALCDATE(ScriptUtil.getArrayElement(INST_DT , 1), 4, 0, 0, 0);";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 11 */

        inputString =
                "ARR[ A + B + ETC] = ARR2[ methodThatReturnsInt(x,y,z) + 1];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR , A + B + ETC , ScriptUtil.getArrayElement(ARR2 , methodThatReturnsInt(x,y,z) + 1));";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 12 */

        inputString = "ARR[ ARR2[ IDX ] ] = ARR3[ ARR2[IDX + 1]];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR , ScriptUtil.getArrayElement(ARR2 , IDX) , ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 1)));";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 13 */

        inputString =
                "ARR[ A + B + ARR2[ IDX + A + B ] ] = ARR3[ ARR2[IDX + 1] + A + B ];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR , A + B + ScriptUtil.getArrayElement(ARR2 , IDX + A + B) , ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 1) + A + B));";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '\n" + expectedOutput
                    + "' but was:- \n" + converter.getConvertedString());
        }

        /* Test 14 */

        inputString = "Method(ARR3[ ARR2[IDX + 1]], ARR3[ ARR2[IDX + 2]]);";

        expectedOutput =
                "Method(ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 1)), ScriptUtil.getArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR2 , IDX + 2)));";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /*
         * Now some tests with no spacing before the array names.
         */
        /* Test 15 */
        inputString = "ARR3[ARR3[10]] = a;";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR3 , ScriptUtil.getArrayElement(ARR3 , 10) , a);";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

        /* Test 16 */

        inputString = "ARR3[ 20 ] =ARR2[ count ];";

        expectedOutput =
                "ScriptUtil.setArrayElement(ARR3 , 20 , ScriptUtil.getArrayElement(ARR2 , count));";

        converter = new ArrayToScriptConverter(inputString);

        if (converter.getConvertedString().equals(expectedOutput)) {

            System.out.println("String: '" + inputString
                    + "' converted successfully");

        } else {
            System.out.println("Conversion Failed, Input String: '"
                    + inputString + "' ouput expected: '" + expectedOutput
                    + "' but was:- " + converter.getConvertedString());
        }

    }
}