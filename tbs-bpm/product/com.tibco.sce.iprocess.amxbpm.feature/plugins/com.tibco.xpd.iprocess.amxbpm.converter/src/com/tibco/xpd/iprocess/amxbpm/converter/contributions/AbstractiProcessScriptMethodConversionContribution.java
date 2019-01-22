/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.ipm.iProcessExt.StandaloneScript;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.studioiprocess.scriptconverter.NestedLineList;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Abstract class that runs through all the Standalone and Expression Scripts,
 * fetches(line by line) if any of these Scripts have used the the method name
 * passed to the constructor
 * {@link #AbstractiProcessScriptMethodConversionContribution(String, int)} , if
 * we find a Script line containing the method name then that line is passed to
 * the method {@link #convertEachScriptLine(String, int, int, int, List)} which
 * the user is expected to implement and convert the passed line.
 * <p>
 * Example : If we wish to fetch a method (say myMethod(params....)) , then
 * simply pass the constructor
 * {@link #AbstractiProcessScriptMethodConversionContribution(String, int)} the
 * <b>name</b> of the method (i.e. 'myMethod') and the number of parameters that
 * this method takes, all the standalone and expression scripts will be scanned
 * line by line and if this method (i.e. myMethod) is found in any of the lines
 * then it will be passed to
 * {@link #convertEachScriptLine(String, int, int, int, List)} which is to be
 * implemented by the implementers of this class and return the converted
 * String.
 * 
 * @author kthombar
 * @since 02-Jun-2014
 */
public abstract class AbstractiProcessScriptMethodConversionContribution extends
        AbstractProcessScriptConversion {

    /**
     * the method whose is to be fetched in the Standalone and Expression
     * Scripts.
     */
    protected String scriptUtilMethodName;

    protected int numberForParamstheMethodTakes;

    /**
     * * Constructor that initializes the Method name which is to be fetched in
     * the Standalone and Expression Scripts, and the number of parameter that
     * this method takes.
     * <p>
     * Implementers of this class are expected to provide this constructor the
     * name of the method which they wish to find in the Standalone or
     * Expression Scripts and the number of parameters that this method takes. *
     * <p>
     * Example : If we wish to fetch a method (say myMethod(params....)) , then
     * simply pass the constructor
     * {@link #AbstractiProcessScriptMethodConversionContribution(String, int)}
     * the <b>name</b> of the method (i.e. 'myMethod') and the number of
     * parameters that this method takes, all the standalone and expression
     * scripts will be scanned line by line and if this method (i.e. myMethod)
     * is found in any of the lines then it will be passed to
     * {@link #convertEachScriptLine(String, int, int, int, List)} which is to
     * be implemented by the implementers of this class and return the converted
     * String.
     * 
     * @param scriptMethodToConvert
     *            the Script method name
     * @param numberForParamstheMethodTakes
     *            the number of parameters that this method takes.
     */
    public AbstractiProcessScriptMethodConversionContribution(
            String scriptMethodToConvert, int numberForParamstheMethodTakes) {
        /* Initialize with the method name which is to be fetched. */
        this.scriptUtilMethodName = scriptMethodToConvert.toLowerCase();
        /* the number of parameters the method takes. */
        this.numberForParamstheMethodTakes = numberForParamstheMethodTakes;
    }

    /**
     * Scans through Standalone scripts fetching if they have used the method
     * passed to {@link #scriptUtilMethodName}
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertStandaloneScripts(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList, java.util.List)
     * 
     * @param process
     *            the current process
     * @param standaloneScripts
     *            all the Standalone Scripts in this process
     * @param allProcessRelevantData
     *            all data accessible to the process.
     * @return the status of conversion.
     */
    @Override
    protected IStatus convertStandaloneScripts(Process process,
            EList<StandaloneScript> standaloneScripts,
            List<ProcessRelevantData> allProcessRelevantData) {

        for (StandaloneScript standaloneScript : standaloneScripts) {

            String standaloneScriptName = standaloneScript.getName();
            if (standaloneScriptName != null && !standaloneScriptName.isEmpty()) {

                String standaloneScriptValue = standaloneScript.getValue();

                if (standaloneScriptValue != null) {
                    /*
                     * If we have Standalone Scripts then convert them.
                     */
                    String newChangedScripts =
                            changeScriptMethodsByNewMethods(standaloneScriptValue);
                    /*
                     * Set the value of Standalone Scripts to that of the
                     * converted Scripts.
                     */
                    standaloneScript.setValue(newChangedScripts);
                }
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * Scans through Expression scripts fetching if they have used the method
     * passed to {@link #scriptUtilMethodName}
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertScripts(com.tibco.xpd.xpdl2.Process,
     *      com.tibco.xpd.xpdl2.Expression, java.util.List)
     * 
     * @param process
     *            the current process
     * @param expression
     * @param allProcessRelevantData
     *            all data accessible to the process.
     * @return the status of conversion.
     */
    @Override
    protected IStatus convertScripts(Process process, Expression expression,
            List<ProcessRelevantData> allProcessRelevantData) {

        if (ScriptGrammarFactory.JAVASCRIPT.equals(expression
                .getScriptGrammar())) {

            String javaScripts = expression.getText();

            if (javaScripts != null && !javaScripts.isEmpty()) {

                String newChangedScripts =
                        changeScriptMethodsByNewMethods(javaScripts);
                /*
                 * Set the converted Scripts.
                 */
                setConvertedScriptToExpression(expression, newChangedScripts);
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * Scans through all the scripts line by line fetching if any of the lines
     * has the method name passed to {@link #scriptUtilMethodName} , if there is
     * a line with the expected method name then passes it to
     * {@link #convertEachScriptLine(String, int, int, int, List)} for
     * conversion.
     * 
     * @param scripts
     *            the script to scan through.
     * @return the converted Scripts.
     */
    protected String changeScriptMethodsByNewMethods(String scripts) {

        NestedLineList javaScriptLines = new NestedLineList(2);

        JavaScriptLineList javaScriptLineList = new JavaScriptLineList(scripts);

        for (String eachLine : javaScriptLineList) {

            if (eachLine.toLowerCase().contains(scriptUtilMethodName)) {

                String eachConvertedLine =
                        reccursivelyConvertEachScriptLine(eachLine);

                if (eachConvertedLine != null && !eachConvertedLine.isEmpty()) {

                    javaScriptLines.add(eachConvertedLine);

                } else {

                    javaScriptLines.add(eachLine);
                }
            } else {
                javaScriptLines.add(eachLine);
            }
        }
        return javaScriptLines.toString();
    }

    /**
     * This method reccursively Scans the passed script checking if it contains
     * the expected method {@link #scriptUtilMethodName}, if yes then it passes
     * then necessary indexes and parameters to
     * {@link #convertEachScriptLine(String, int, int, int, List)} which is
     * expected to convert the Script.
     * 
     * @param eachScriptLine
     *            the Script line to convert
     * @return the Converted Script
     */
    protected String reccursivelyConvertEachScriptLine(String eachScriptLine) {

        StringBuffer semiColonOrCommentRemoved = new StringBuffer();

        String convertedString =
                removeSemiColonAndComments(eachScriptLine,
                        semiColonOrCommentRemoved);

        if (convertedString.isEmpty()
                || convertedString.length() < scriptUtilMethodName.length()) {
            /*
             * after removing semi colon or comments if the script is empty or
             * the length of String is less than that of the method we wish to
             * fetch then return immediately.
             */
            return eachScriptLine;
        }

        while (true) {
            /**
             * indexTracker that keeps track of start index of method in
             * indexTracker[0] , index of first open bracket after method in
             * indexTracker[1] and closing bracket index in indexTracker[2]
             */
            int indexTracker[] = { -1, -1, -1 };
            /*
             * Get the conversion related data, i.e. the method start index ,
             * the open bracket index , close bracket index and the parameters
             * in the method.
             */
            List<String> listOfParametersInOrder =
                    getConversionRelatedData(convertedString, indexTracker);

            if (listOfParametersInOrder == null) {
                /*
                 * If list of parameters is null that indicates that we dont
                 * have any other Method to convert, so append back the removed
                 * semicolon or comment and break.
                 */
                convertedString =
                        convertedString + semiColonOrCommentRemoved.toString();
                break;
            }
            String eachScriptLineConverted =
                    convertEachScriptLine(convertedString,
                            indexTracker[0],
                            indexTracker[1],
                            indexTracker[2],
                            listOfParametersInOrder);

            convertedString = eachScriptLineConverted;
        }
        return convertedString;
    }

    /**
     * Implementers are expected to implement this method and perform necessary
     * operation on the Script and return the converted Script.
     * 
     * @param stringtoConvert
     *            the string/Script to convert
     * @param startOfMethodIndex
     *            the start index of the method call
     * @param startBracketIndex
     *            the index of start/ open bracket
     * @param endBracketIndex
     *            the index of end/closing bracket.
     * @param listOfParametersInOrder
     *            the list of parameters that the method has in order(from left
     *            to right)
     * @return the converted Script.
     */
    protected abstract String convertEachScriptLine(String stringtoConvert,
            int startOfMethodIndex, int startBracketIndex, int endBracketIndex,
            List<String> listOfParametersInOrder);

    /**
     * Provides with all the necessary conversion related data i.e.
     * <p>
     * 1. The start index of method.
     * <p>
     * 2. The index of first opening bracket after the method call
     * <p>
     * 3. the index of the closing bracket of the method
     * <p>
     * 4. All the parameters in the method(in order)
     * 
     * @param stringToConvert
     *            the string that we wish to convert
     * @param indexTracker
     *            indexTracker that keeps track of start index of method in
     *            indexTracker[0] , index of first open bracket after method in
     *            indexTracker[1] and closing bracket index in indexTracker[2]
     * @return All the parameters in the method(in order from left to right), if
     *         no method found , or the number of parameters that we found is
     *         not equal to number of parameters expected then return
     *         <code>null</code>
     */
    private List<String> getConversionRelatedData(String stringToConvert,
            int[] indexTracker) {
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
                /*
                 * if we are inside of double or single quotes then we should
                 * not keep track of brackets.
                 */
                continue;
            }

            if (idx + scriptUtilMethodName.length() < stringToConvert.length()) {
                /*
                 * check if the text immediately following idx is the expected
                 * method name
                 */
                String stringAfterIdx =
                        stringToConvert.substring(idx, idx
                                + scriptUtilMethodName.length());

                if (stringAfterIdx.equalsIgnoreCase(scriptUtilMethodName)) {

                    /*
                     * check if method name is immediately followed by '(' , if
                     * yes then get its index
                     */
                    int openBracketIndex =
                            openBracketIndex(stringToConvert, idx
                                    + scriptUtilMethodName.length());

                    if (openBracketIndex != -1) {

                        /*
                         * get the closing bracket index
                         */
                        int closeBracketIndex =
                                closeBracketIndex(stringToConvert,
                                        openBracketIndex);

                        if (closeBracketIndex != -1) {
                            /*
                             * now as we have the method start index , the open
                             * and close bracket index, populate the array.
                             */
                            indexTracker[0] = idx;
                            indexTracker[1] = openBracketIndex;
                            indexTracker[2] = closeBracketIndex;

                            String stringInBrackets =
                                    stringToConvert.substring(openBracketIndex,
                                            closeBracketIndex + 1);

                            /*
                             * get the parameters in the method.
                             */
                            List<String> parametersInMethod =
                                    getParametersInMethodInOrder(stringInBrackets);
                            /*
                             * The number of parameters in the method should
                             * match with the number of parameters we expect.
                             */
                            if (parametersInMethod.size() == numberForParamstheMethodTakes) {
                                return parametersInMethod;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param methodCall
     *            the method call, i.e. if we have method named
     *            myMethod(param1,param2,param3) then methodCall will have
     *            (param1,param2,param3)
     * @return list of parameters that the method has in order. (from left to
     *         right)
     */
    private List<String> getParametersInMethodInOrder(String methodCall) {

        List<String> listOfParametersInOrder = new LinkedList<String>();

        int bracketLevel = 0;
        /**
         * Keeps track of the start index of every parameter in the method call.
         */
        int parameterStartIndex = 0;

        /* Keeps tracks of double quotes found in the String to convert */
        boolean isDoubleQuotes = false;
        /* Keeps tracks of single quotes found in the String to convert */
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < methodCall.length(); idx++) {

            char charAt = methodCall.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {
                // Ignore escaped next character in string.
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {
                // Ignore escaped next character in string.
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
                /*
                 * if we are inside of double or single quotes then we should
                 * not keep track of brackets.
                 */
                continue;
            }

            if (charAt == '(') {

                bracketLevel++;

                if (bracketLevel == 1) {
                    /*
                     * the first opening bracket
                     */
                    parameterStartIndex = idx;

                }
            } else if (charAt == ')') {

                bracketLevel--;

                if (bracketLevel == 0) {
                    /*
                     * Have found the closing bracket.
                     */
                    listOfParametersInOrder.add(methodCall
                            .substring(parameterStartIndex + 1, idx));
                    break;
                }
            }

            if (charAt == ',' && bracketLevel == 1) {
                /*
                 * each time we encounter a comma and we are in the first
                 * bracket level the string between the parameter start index
                 * and the current index is our parameter.
                 */
                listOfParametersInOrder.add(methodCall
                        .substring(parameterStartIndex + 1, idx));

                parameterStartIndex = idx;
            }
        }
        return listOfParametersInOrder;
    }

    /**
     * 
     * @param inputString
     *            String in which the index of opening bracket is to be found
     * @param idx
     *            the index to start from.
     * @return index of the opening bracket found. If opening bracket is not the
     *         first character that we find from the passed Index then return
     *         -1.
     */
    private int openBracketIndex(String inputString, int idx) {

        for (; idx < inputString.length(); idx++) {
            char charAt = inputString.charAt(idx);
            /*
             * We wish to check if the method "SCRIPT" or "CALL" is immediately
             * followed by opening bracket '('
             */
            if (Character.isWhitespace(charAt)) {
                /*
                 * We allow spaced between the method name and the openig
                 * bracket.
                 */
                continue;
            } else if (charAt == '(') {
                return idx;
            } else {
                /*
                 * if anything apart from '(' or whitespace is found then return
                 * -1
                 */
                return -1;
            }
        }
        return -1;
    }

    /**
     * @param inputString
     *            String in which the index of closing bracket is to be found
     * 
     * @param idx
     *            the index to start from.
     * 
     * @return index of the closing bracket found. If no closing bracket is
     *         found then return -1.
     */
    private int closeBracketIndex(String inputString, int idx) {

        int bracketTracker = 0;
        boolean isDoubleQuotes = false;
        /* Keeps tracks of single quotes found in the String to convert */
        boolean isSingleQuotes = false;

        for (; idx < inputString.length(); idx++) {
            char charAt = inputString.charAt(idx);

            if (charAt == '\\' && isDoubleQuotes) {
                // Ignore escaped next character in string.
                idx++;
            } else if (charAt == '\\' && isSingleQuotes) {
                // Ignore escaped next character in string.
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
                /*
                 * if we are inside of double or single quotes then we should
                 * not keep track of brackets.
                 */
                continue;
            }

            if (charAt == '(') {

                bracketTracker++;

            } else if (charAt == ')') {

                bracketTracker--;

                if (bracketTracker == 0) {

                    /* closing bracket found, return index */
                    return idx;
                }
            }
        }
        return -1;
    }

    /**
     * Removes any semiColon ';' or comments '//' found in the input string
     * which are outside single '' or double "" quotes. The String after the
     * semicolon or comments is stored in the parameter
     * semiColonOrCommentRemoved so that it can be appended back after the
     * conversion.
     * 
     * @param originalString
     *            the original String whose semicolon or comments are to be
     *            removed.
     * @param semiColonOrCommentRemoved
     *            the buffer that will store the removed semicolon or commnent.
     * @return String with removed Semicolon or comments.
     */
    private String removeSemiColonAndComments(String originalString,
            StringBuffer semiColonOrCommentRemoved) {

        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < originalString.length(); idx++) {

            char charAt = originalString.charAt(idx);

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
                semiColonOrCommentRemoved.append(originalString.substring(idx,
                        originalString.length()));
                originalString = originalString.substring(0, idx).trim();
                break;

            } else if (charAt == '/') {
                if (originalString.charAt(idx + 1) == '/') {
                    semiColonOrCommentRemoved.append(originalString
                            .substring(idx, originalString.length()));
                    originalString = originalString.substring(0, idx).trim();
                    break;
                }
            }
        }
        return originalString;
    }
}
