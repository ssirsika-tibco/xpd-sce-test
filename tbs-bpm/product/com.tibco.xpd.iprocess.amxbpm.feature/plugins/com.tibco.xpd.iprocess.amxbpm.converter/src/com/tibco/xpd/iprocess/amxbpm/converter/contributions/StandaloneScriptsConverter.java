/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.studioiprocess.scriptconverter.NestedLineList;
import com.tibco.xpd.xpdl2.Process;

/**
 * Script Converter that scans through the passed script line and checks if they
 * have call to Standalone scripts. If yes then inlines all such calls
 * (CALL("scriptName")/SCRIPT("scriptName",args...)) by the value of the actual
 * script called.
 * <p>
 * Additional Info: A Standalone script can be called using 2 methods
 * <p>
 * 1. CALL("scriptName") - where scriptName is the name of the Standalone Script
 * we wish to invoke
 * <p>
 * 2. SCRIPT("scriptName",args1,args2,..) -where scriptName is the name of the
 * Standalone Script we wish to invoke , and args are the arguments we wish to
 * pass to the Standalone Script.
 * 
 * @author kthombar
 * @since 20-May-2014
 */
public class StandaloneScriptsConverter {
    private final String STANDALONE_SCRIPT_CALL_METHOD = "CALL"; //$NON-NLS-1$

    private final String STANDALONE_SCRIPT_SCRIPT_METHOD = "SCRIPT"; //$NON-NLS-1$

    /**
     * @param standaloneScriptBeforeConversionMap
     *            {@link Map} containing the Standalone Script Task Name mapped
     *            to the JavaScript in it.(prior to conversion)
     */
    private Map<String, String> standaloneScriptBeforeConversionMap;

    /**
     * {@link Map} containing the Standalone Script Task Name mapped to the
     * JavaScript in it.(after the conversion is over)
     */
    private Map<String, String> standaloneScriptAfterConversionMap;

    /**
     * boolean value that keeps track if a Standalone scripts is called via the
     * "SCRIPT("scriptName",args...)" method, if yes then this variable is set
     * <code>true</code> otherwise <code>false</code>
     */
    private static boolean isCallingMethodScript = false;

    /**
     * Stores the converted Script
     */
    private String convertedString;

    private Process process;

    /**
     * Script Converter that scans through the Scripts and checks if they have
     * call to Standalone scripts. If yes then inlines all such calls
     * (CALL("scriptName")/SCRIPT("scriptName",args...)) by the value of the
     * actual script called.
     * 
     * @param standaloneScriptBeforeConversionMap
     *            {@link Map} containing the Standalone Script Task Name mapped
     *            to the JavaScript in it.(prior to conversion)
     * @param standaloneScriptAfterConversionMap
     *            {@link Map} containing the Standalone Script Task Name mapped
     *            to the inlined Standalone Scripts in it.(after the conversion)
     * @param process
     *            the Process whose Standalone Scripts are scanned.
     */
    public StandaloneScriptsConverter(
            Map<String, String> standaloneScriptBeforeConversionMap,
            Map<String, String> standaloneScriptAfterConversionMap,
            Process process) {

        this.standaloneScriptBeforeConversionMap =
                standaloneScriptBeforeConversionMap;
        this.standaloneScriptAfterConversionMap =
                standaloneScriptAfterConversionMap;
        this.process = process;
    }

    /**
     * Looks through the passed script and checks if it calls Standalone Script,
     * if yes then inlines these standalone scripts. .The conversion will fail
     * and will be aborted under the following circumstances:
     * <p>
     * 1. Cyclic Call : There is a never ending reccursive cyclic call between
     * the Standalone Scripts.
     * <p>
     * 2. Standalone Script not found: If the Standalone Script that was called
     * is not found.
     * 
     * @param scriptToConvert
     *            the script to convert
     * @return {@link IStatus} of the conversion. We return {@link IStatus#OK}
     *         if the conversion is successful OR {@link IStatus#ERROR} if the
     *         conversion fails , reasons for failure include
     *         <p>
     *         1. Cyclic Call : There is a never ending reccursive cyclic call
     *         between the Standalone Scripts.
     *         <p>
     *         2. Standalone Script not found: If the Standalone Script that was
     *         called is not found.
     */
    public IStatus convertScripts(String scriptToConvert) {

        MultiStatus scriptMultiStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        NestedLineList nestedLines = new NestedLineList(2);

        String calledScriptName = getCalledScriptName(scriptToConvert);

        boolean isScriptCall = isCallingMethodScript;

        if (calledScriptName != null) {
            if (!standaloneScriptAfterConversionMap
                    .containsKey(calledScriptName.toUpperCase())) {

                String standaloneScriptBeforeConversion =
                        standaloneScriptBeforeConversionMap
                                .get(calledScriptName.toUpperCase());

                if (standaloneScriptBeforeConversion == null) {
                    /*
                     * If no such standalone script found then return with
                     * warning status.
                     */
                    nestedLines
                            .add(String
                                    .format(Messages.StandaloneScriptsConverter_NoStandaloneScriptFound_newLine,
                                            calledScriptName));
                    nestedLines.add(scriptToConvert);

                    convertedString = nestedLines.toString();

                    scriptMultiStatus
                            .add(new Status(
                                    IStatus.WARNING,
                                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                                    String.format(Messages.StandaloneScriptsConverter_NoStandaloneScriptFoundStatus_warning,
                                            process.getName(),
                                            calledScriptName)));

                    return scriptMultiStatus;
                }

                IStatus conversionStatus =
                        doConversion(calledScriptName.toUpperCase(),
                                standaloneScriptBeforeConversion,
                                new HashSet<String>());

                if (IStatus.ERROR == conversionStatus.getSeverity()) {
                    return conversionStatus;
                }

                scriptMultiStatus.add(conversionStatus);
            }

            IndentScripts indentedScript =
                    new IndentScripts(scriptToConvert,
                            standaloneScriptAfterConversionMap
                                    .get(calledScriptName.toUpperCase()),
                            isScriptCall);

            nestedLines.add(indentedScript.getIndentedString());

        } else {
            nestedLines.add(scriptToConvert);
        }

        convertedString = nestedLines.toString();

        return scriptMultiStatus;
    }

    /**
     * 
     * @return the converted script (the script which has Standalone scripts
     *         inlined in them)
     */
    public String getConvertedString() {
        return convertedString;
    }

    /**
     * Reccurrsively inlines the Standalone Scripts at the place from where they
     * are called.The inlined Scripts are Stored in
     * {@link StandaloneScriptsConverter#standaloneScriptAfterConversionMap}
     * 
     * @param standaloneScriptName
     *            the Name of the Standalone Script Object
     * @param standaloneScriptValue
     *            the Script in the Standalone Script Object
     * @param alreadyProcessedScripts
     *            List of already Processed Scripts so that we can keep a track
     *            and avoid cyclic Script call
     * @return {@link IStatus} of the conversion. We return {@link IStatus#OK}
     *         if the conversion is successful OR {@link IStatus#ERROR} if the
     *         conversion fails , reasons for failure include
     *         <p>
     *         1. Cyclic Call : There is a never ending reccursive cyclic call
     *         between the Standalone Scripts.
     *         <p>
     *         2. Standalone Script not found: If the Standalone Script that was
     *         called is not found.
     */
    private IStatus doConversion(String standaloneScriptName,
            String standaloneScriptValue, Set<String> alreadyProcessedScripts) {

        MultiStatus scriptMultiStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        if (alreadyProcessedScripts.contains(standaloneScriptName)) {
            /*
             * if we visit a Script that was already scanned that indicates
             * there was a cyclic call, return error immediately
             */
            return new Status(
                    IStatus.ERROR,
                    IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                    String.format(Messages.StandaloneScriptsConverter_CyclicDependencyError_msg,
                            standaloneScriptName));
        }

        Set<String> newAlreadyProcessedScripts =
                new HashSet<String>(alreadyProcessedScripts);
        /*
         * add all the scripts already visited, if we visit them again that
         * indicates there was a cyclic call.
         */
        newAlreadyProcessedScripts.add(standaloneScriptName);

        NestedLineList nestedLines = new NestedLineList(2);

        if (standaloneScriptValue != null) {
            /*
             * Adding null check.
             */
            JavaScriptLineList lineScripts =
                    new JavaScriptLineList(standaloneScriptValue);

            for (String eachLine : lineScripts) {

                String calledScriptName = getCalledScriptName(eachLine);
                boolean isScriptCall = isCallingMethodScript;

                if (calledScriptName != null) {

                    if (!standaloneScriptAfterConversionMap
                            .containsKey(calledScriptName.toUpperCase())) {

                        String standaloneScriptInMap =
                                standaloneScriptBeforeConversionMap
                                        .get(calledScriptName.toUpperCase());

                        if (standaloneScriptInMap != null) {

                            IStatus conversionStatus =
                                    doConversion(calledScriptName,
                                            standaloneScriptInMap,
                                            newAlreadyProcessedScripts);

                            if (IStatus.ERROR == conversionStatus.getSeverity()) {
                                return conversionStatus;
                            }

                            scriptMultiStatus.add(conversionStatus);

                        } else {
                            /*
                             * If a standalone script calls other standalone
                             * script and no such script is present then return
                             * error immediately
                             */
                            nestedLines
                                    .add(String
                                            .format(Messages.StandaloneScriptsConverter_NoStandaloneScriptFound_newLine,
                                                    calledScriptName));
                            nestedLines.add(eachLine);
                            scriptMultiStatus
                                    .add(new Status(
                                            IStatus.WARNING,
                                            IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                                            String.format(Messages.StandaloneScriptsConverter_NoStandaloneScriptFoundStatus_warning,
                                                    process.getName(),
                                                    calledScriptName)));

                        }

                    }

                    String inlinedStandaloneScript =
                            standaloneScriptAfterConversionMap
                                    .get(calledScriptName.toUpperCase());

                    if (inlinedStandaloneScript != null) {
                        /* Indent the scripts. */
                        IndentScripts indentedScript =
                                new IndentScripts(eachLine,
                                        inlinedStandaloneScript, isScriptCall);

                        nestedLines.add(indentedScript.getIndentedString());

                    }
                } else {
                    nestedLines.add(eachLine);
                }
            }
        }

        if (standaloneScriptAfterConversionMap.get(standaloneScriptName
                .toUpperCase()) == null) {
            /* add the inlined script to the map */
            standaloneScriptAfterConversionMap.put(standaloneScriptName
                    .toUpperCase(), nestedLines.toString());
        }

        return scriptMultiStatus;
    }

    /**
     * Removes Comments '//' from the passed Scripts.
     * 
     * @param inputString
     *            the String from which the comments are to be removed
     * @return String from which comments are removed.
     */
    private String removeComments(String inputString) {

        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < inputString.length(); idx++) {

            char charAt = inputString.charAt(idx);

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

            if (charAt == '/') {
                if (inputString.charAt(idx + 1) == '/') {
                    inputString = inputString.substring(0, idx).trim();
                    break;
                }
            }
        }
        return inputString;
    }

    /**
     * Gets the name of Standalone Script(if any) called from the passed input
     * String.
     * 
     * @param inputScriptLine
     *            each Script Line in which we wish to fetch the call to other
     *            Standalone Script.
     * @return the Name of the Standalone Script that is called from the
     *         'inputScriptLine' , if there is no call to Standalone Script then
     *         returns <code>null</code>.
     */
    public String getCalledScriptName(String inputScriptLine) {

        isCallingMethodScript = false;

        inputScriptLine = removeComments(inputScriptLine);

        if (inputScriptLine.isEmpty()) {
            return null;
        }

        /* Keeps tracks of double quotes found in the String to convert */
        boolean isDoubleQuotes = false;
        /* Keeps tracks of single quotes found in the String to convert */
        boolean isSingleQuotes = false;

        for (int idx = 0; idx < inputScriptLine.length(); idx++) {

            char charAt = inputScriptLine.charAt(idx);

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

            if (idx > 0
                    && (Character.isAlphabetic(inputScriptLine.charAt(idx - 1)) || Character
                            .isDigit(inputScriptLine.charAt(idx - 1)))) {
                /*
                 * SCRIPT/CALL should not be preceeded by an alphabet or digit.
                 * There might be scenarios where the ending of a word is script
                 * or call. example method having name myscript("");
                 */
                continue;
            }

            if (idx + STANDALONE_SCRIPT_CALL_METHOD.length() < inputScriptLine
                    .length()) {
                /* check if the text immediately following idx is 'CALL' */
                String stringAfterIdx =
                        inputScriptLine.substring(idx, idx
                                + STANDALONE_SCRIPT_CALL_METHOD.length());

                if (stringAfterIdx
                        .equalsIgnoreCase(STANDALONE_SCRIPT_CALL_METHOD)) {
                    /* check if 'CALL' is immediately followed by '(' */
                    int openBracketIndex =
                            openBracketIndex(inputScriptLine, idx
                                    + STANDALONE_SCRIPT_CALL_METHOD.length());
                    if (openBracketIndex != -1) {

                        /*
                         * Find and return the Standalone Script name, else
                         * continue running through the script
                         */
                        String scriptName =
                                getScriptName(inputScriptLine, openBracketIndex);
                        if (scriptName != null) {
                            isCallingMethodScript = false;
                            return scriptName;
                        }
                    }
                }
            }

            if (idx + STANDALONE_SCRIPT_SCRIPT_METHOD.length() < inputScriptLine
                    .length()) {
                /* check if the text immediately following idx is 'SCRIPT' */
                String stringAfterIdx =
                        inputScriptLine.substring(idx, idx
                                + STANDALONE_SCRIPT_SCRIPT_METHOD.length());

                if (stringAfterIdx
                        .equalsIgnoreCase(STANDALONE_SCRIPT_SCRIPT_METHOD)) {
                    /* check if 'SCRIPT' is immediately followed by '(' */
                    int openBracketIndex =
                            openBracketIndex(inputScriptLine, idx
                                    + STANDALONE_SCRIPT_SCRIPT_METHOD.length());
                    if (openBracketIndex != -1) {

                        /*
                         * Find and return the Standalone Script name, else
                         * continue running through the script
                         */
                        String scriptName =
                                getScriptName(inputScriptLine, openBracketIndex);

                        if (scriptName != null) {
                            isCallingMethodScript = true;
                            return scriptName;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Logic explained : The Syntax to call a Standalone Script is 'CALL
     * ("scriptName") / SCRIPT ("scriptName", args1...)' . If we see both this
     * methods , then we can find the scripName inside Quotes "" which are
     * immediately following the Open bracket '(' after the method call.
     * 
     * @param inputScriptLine
     *            the input String in which we wish to find the Stnadalone
     *            Script call.
     * @param openBracketIndex
     *            the index of the opening bracket '('
     * @return the Name of the Standalone Script that is called from the
     *         'inputScriptLine' , if there is no call to Standalone Script then
     *         returns <code>null</code>.
     */
    private String getScriptName(String inputScriptLine, int openBracketIndex) {
        int idx = openBracketIndex + 1;
        /* index of opening double quote */
        int startingQuoteIndex = -1;
        /* index of closing double quote */
        int endingQuoteIndex = -1;
        /*
         * keeps track of the brackets as we want to be sure that the method
         * syntax is complete.
         */
        int bracketTracker = 1;
        boolean foundClosingBracked = false;
        String scriptName = null;

        for (; idx < inputScriptLine.length(); idx++) {
            char charAt = inputScriptLine.charAt(idx);
            if (startingQuoteIndex == -1) {
                /*
                 * After the opening bracket we either expect a whitespace or a
                 * double quote "
                 */
                if (Character.isWhitespace(charAt)) {
                    continue;
                } else if (charAt == '"') {
                    /*
                     * keep track of the starting double quote
                     */
                    startingQuoteIndex = idx;
                    continue;
                } else {
                    return null;
                }
            } else if (endingQuoteIndex == -1) {
                /*
                 * After finding the starting quote we search for the ending
                 * quote, the Standalone Script name can have a latter or a
                 * digit in it, so we go on skipping them untill we find the
                 * ending double quote ".
                 */
                if (Character.isLetter(charAt) || Character.isDigit(charAt)) {
                    continue;
                } else if (charAt == '"') {
                    endingQuoteIndex = idx;
                    continue;
                } else {
                    /*
                     * If we find anything else return immediately.
                     */
                    return null;
                }
            }

            if (startingQuoteIndex != -1 && endingQuoteIndex != -1) {
                if (charAt == '(') {
                    bracketTracker++;
                } else if (charAt == ')') {
                    bracketTracker--;
                    if (bracketTracker == 0) {
                        /*
                         * if we are here that indicates that the method has an
                         * ending closing bracket and its syntax is correct
                         */
                        foundClosingBracked = true;
                    }
                }
            }
        }
        if (foundClosingBracked && startingQuoteIndex != -1
                && endingQuoteIndex != -1) {
            /*
             * if we have found the starting and ending double quote and are
             * sure that the method syntx is correct then we return the
             * Standalone script name.
             */
            scriptName =
                    inputScriptLine.substring(startingQuoteIndex + 1,
                            endingQuoteIndex);
        }
        return scriptName;
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
     * Indents the Scripts and adds comment tags '//' to them as required.
     * 
     * 
     * @author kthombar
     * @since 03-Jun-2014
     */
    private class IndentScripts {

        private String indentedScript;

        /**
         * Indents the Scripts and adds comment tags '//' to them as required.
         * 
         * @param scriptLine
         *            the line containing the CALL/SCRIPT method call
         * @param convertedScript
         *            the converted script
         * @param isScriptCall
         *            </code>true</code> if the Standalone Scrips were invoked
         *            by the SCRIPT("",args) method , <code>false</code> if the
         *            Standalone scripts were invoked by the CALL("") method.
         */
        public IndentScripts(String scriptLine, String convertedScript,
                boolean isScriptCall) {
            doIndentation(scriptLine, convertedScript, isScriptCall);
        }

        /**
         * does the actual indentation of Scripts based on whether they had a
         * CALL method or a SCRIPT method which invoked the Standalone scripts.
         * 
         * @param scriptLine
         *            the line containing the CALL/SCRIPT method call
         * @param convertedScript
         *            the converted script
         * @param isScriptCall
         *            </code>true</code> if the Standalone Scrips were invoked
         *            by the SCRIPT("",args) method , <code>false</code> if the
         *            Standalone scripts were invoked by the CALL("") method.
         */
        private void doIndentation(String scriptLine, String convertedScript,
                boolean isScriptCall) {

            NestedLineList nestedLines = new NestedLineList(2);

            if (isScriptCall) {
                nestedLines.add(scriptLine);
                nestedLines
                        .add(Messages.StandaloneScriptsConverter_ConvertScriptManually_msg);
                nestedLines
                        .add(Messages.StandaloneScriptsConverter_InvokedScript_msg);
                JavaScriptLineList lineList =
                        new JavaScriptLineList(convertedScript);

                for (String eachLine : lineList) {
                    nestedLines.add("  // " + eachLine); //$NON-NLS-1$
                }

            } else {
                nestedLines.add("  // " + scriptLine); //$NON-NLS-1$
                nestedLines
                        .add(Messages.StandaloneScriptsConverter_InvokedScript_msg);
                JavaScriptLineList lineList =
                        new JavaScriptLineList(convertedScript);

                for (String eachLine : lineList) {
                    nestedLines.add("   " + eachLine); //$NON-NLS-1$
                }
            }

            nestedLines.add("  // ========================================"); //$NON-NLS-1$

            indentedScript = nestedLines.toString();

        }

        /**
         * 
         * @return the indented Script
         */
        public String getIndentedString() {
            return indentedScript;
        }
    }

}
