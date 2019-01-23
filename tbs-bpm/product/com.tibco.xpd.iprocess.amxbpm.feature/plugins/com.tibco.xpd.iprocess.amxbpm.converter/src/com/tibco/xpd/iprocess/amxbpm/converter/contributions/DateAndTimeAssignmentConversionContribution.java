/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.ipm.iProcessExt.StandaloneScript;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.studioiprocess.scriptconverter.NestedLineList;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Conversion Contribution do deal with the Date and Time field assignment, so
 * that Date and Time field are not assigned by reference.
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
public class DateAndTimeAssignmentConversionContribution extends
        AbstractProcessScriptConversion {

    /**
     * Enum to switch/distinguish between DATE and TIME Fields.
     */
    public enum DATE_TIME_DATA_TYPE {
        DATE, TIME;
    }

    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertStandaloneScripts(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList, java.util.List)
     * 
     * @param process
     * @param standaloneScripts
     * @param allProcessRelevantData
     * @return
     */
    @Override
    protected IStatus convertStandaloneScripts(Process process,
            EList<StandaloneScript> standaloneScripts,
            List<ProcessRelevantData> allProcessRelevantData) {

        Set<String> dateFieldNames = new HashSet<String>();
        Set<String> timeFieldNames = new HashSet<String>();

        /*
         * populate the Date and Time Sets with the Date and Time field names
         * within the scope of the process
         */
        populateDateAndTimeFieldNames(dateFieldNames,
                timeFieldNames,
                allProcessRelevantData);

        for (StandaloneScript standaloneScript : standaloneScripts) {

            String standaloneScriptName = standaloneScript.getName();

            if (standaloneScriptName != null && !standaloneScriptName.isEmpty()) {

                String standaloneScriptValue = standaloneScript.getValue();

                if (standaloneScriptValue != null) {
                    /*
                     * If we have Standalone Scripts then convert them.
                     */
                    String convertedJavaScripts =
                            convertDateAndTimeFields(standaloneScriptValue,
                                    dateFieldNames,
                                    timeFieldNames);
                    /*
                     * Set the value of Standalone Scripts to that of the
                     * converted Scripts.
                     */
                    standaloneScript.setValue(convertedJavaScripts);
                }
            }
        }

        return Status.OK_STATUS;
    }

    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertScripts(com.tibco.xpd.xpdl2.Process,
     *      com.tibco.xpd.xpdl2.Expression, java.util.List)
     * 
     * @param process
     * @param expression
     * @param allProcessRelevantData
     * @return
     */
    @Override
    protected IStatus convertScripts(Process process, Expression expression,
            List<ProcessRelevantData> allProcessRelevantData) {

        if (ScriptGrammarFactory.JAVASCRIPT.equals(expression
                .getScriptGrammar())) {

            Set<String> dateFieldNames = new HashSet<String>();
            Set<String> timeFieldNames = new HashSet<String>();

            /*
             * populate the Date and Time Sets with the Date and Time field
             * names within the scope of the process
             */
            populateDateAndTimeFieldNames(dateFieldNames,
                    timeFieldNames,
                    allProcessRelevantData);

            String javaScripts = expression.getText();

            if (javaScripts != null && !javaScripts.isEmpty()) {

                String convertedJavaScripts =
                        convertDateAndTimeFields(javaScripts,
                                dateFieldNames,
                                timeFieldNames);
                /*
                 * Set the converted Scripts.
                 */
                setConvertedScriptToExpression(expression, convertedJavaScripts);

            }
        }

        return Status.OK_STATUS;
    }

    /**
     * 
     * @param javaScripts
     *            the Script to convert
     * @param dateFieldNames
     *            all the Date field names in the scope or process
     * @param timeFieldNames
     *            all the Time field names in the scope or process
     * @return the Converted Date and Time Script
     */
    private String convertDateAndTimeFields(String javaScripts,
            Set<String> dateFieldNames, Set<String> timeFieldNames) {

        NestedLineList javaScriptLines = new NestedLineList(2);

        JavaScriptLineList javaScriptLine = new JavaScriptLineList(javaScripts);

        for (String eachScript : javaScriptLine) {
            /* the date and time script converted */
            DateAndTimeAssignmentConverter converter = null;

            if (doesScriptHaveDateField(eachScript, dateFieldNames)) {
                /* Check if the Script has Date field, if yes then convert it */
                converter =
                        new DateAndTimeAssignmentConverter(eachScript,
                                DATE_TIME_DATA_TYPE.DATE, dateFieldNames);

            } else if (doesScriptHaveTimeField(eachScript, timeFieldNames)) {
                /* Check if the Script has Time field, if yes then convert it */
                converter =
                        new DateAndTimeAssignmentConverter(eachScript,
                                DATE_TIME_DATA_TYPE.TIME, timeFieldNames);

            }

            if (converter != null) {

                String convertedString = converter.getConvertedScript();

                if (convertedString != null && !convertedString.isEmpty()) {

                    javaScriptLines.add(convertedString);
                } else {

                    javaScriptLines.add(eachScript);
                }
            } else {
                /*
                 * if the converter was null that indicates we did not find the
                 * expected Date or Time Script, hence dont change the original
                 * Script.
                 */
                javaScriptLines.add(eachScript);
            }
        }

        return javaScriptLines.toString();
    }

    /**
     * 
     * @param scriptLine
     *            the Script Line in which we wish to fetch the Time Fields
     * @param dateFieldNames
     *            the Time fields to find in the Script.
     * @return <code>true</code> if we find the passed Time fields in the Script
     *         Line else return <code>false</code>
     */
    private boolean doesScriptHaveTimeField(String scriptLine,
            Set<String> timeFieldNames) {

        return doesScriptContainField(scriptLine, timeFieldNames);
    }

    /**
     * 
     * @param scriptLine
     *            the Script Line in which we wish to fetch the Date Fields
     * @param dateFieldNames
     *            the Date fields to find in the Script.
     * @return <code>true</code> if we find the passed Date fields in the Script
     *         Line else return <code>false</code>
     */
    private boolean doesScriptHaveDateField(String scriptLine,
            Set<String> dateFieldNames) {

        return doesScriptContainField(scriptLine, dateFieldNames);
    }

    /**
     * 
     * @param scriptLine
     *            the Script Line in which we wish to fetch the Date or Time
     *            Fields
     * @param dateOrTimeFieldNames
     *            the Date or Time fields to find in the Script.
     * @return <code>true</code> if we find the passed Date or Time fields in
     *         the Script Line else return <code>false</code>
     */
    private boolean doesScriptContainField(String scriptLine,
            Set<String> dateOrTimeFieldNames) {

        for (String string : dateOrTimeFieldNames) {
            if (scriptLine.contains(string)) {
                return true;
            }
        }
        return false;

    }

    /**
     * Populates the passed parameters (i.e. dateFieldNames and timeFieldNames)
     * with the names of process datas having type Date and Type Time resp.
     * 
     * @param dateFieldNames
     *            should be populated with names of all the Process Data which
     *            are of type Date
     * @param timeFieldNames
     *            should be populated with names of all the Process Data which
     *            are of type Time
     * @param allProcessRelevantData
     *            the Process Data.
     */
    private void populateDateAndTimeFieldNames(Set<String> dateFieldNames,
            Set<String> timeFieldNames,
            List<ProcessRelevantData> allProcessRelevantData) {

        for (ProcessRelevantData processRelevantData : allProcessRelevantData) {

            DataType dataType = processRelevantData.getDataType();

            if (dataType instanceof BasicType) {

                BasicType basicType = (BasicType) dataType;

                BasicTypeType type = basicType.getType();

                if (BasicTypeType.DATE_LITERAL.equals(type)) {
                    /* populate the Set if we have a Date field */
                    dateFieldNames.add(processRelevantData.getName());

                } else if (BasicTypeType.TIME_LITERAL.equals(type)) {
                    /* populate the Set if we have a Time field */
                    timeFieldNames.add(processRelevantData.getName());
                }
            }
        }
    }
}
