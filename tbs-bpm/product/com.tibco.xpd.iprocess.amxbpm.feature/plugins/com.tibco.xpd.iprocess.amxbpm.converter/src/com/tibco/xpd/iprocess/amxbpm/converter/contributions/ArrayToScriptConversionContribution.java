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
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Contribution that removes any use of arrays[] by Script Util methods
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
 * @since 25-Apr-2014
 */
public class ArrayToScriptConversionContribution extends
        AbstractProcessScriptConversion {

    /**
     * 
     * replaces Standalone Scripts using arrays[] by Script Util methods
     * ScriptUtil.getArrayElement and ScriptUtil.setArrayElement.
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertStandaloneScripts(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param standaloneScripts
     * @return
     */
    @Override
    protected IStatus convertStandaloneScripts(Process process,
            EList<StandaloneScript> standaloneScripts,
            List<ProcessRelevantData> allProcessRelevantData) {

        /*
         * get all the names of the data that is in the scope of the process and
         * whose type is Array.
         */
        Set<String> allArrayDataTypeNames =
                getAllArrayDataTypeNames(allProcessRelevantData);

        for (StandaloneScript standaloneScript : standaloneScripts) {
            String standaloneScriptName = standaloneScript.getName();
            if (standaloneScriptName != null && !standaloneScriptName.isEmpty()) {

                String standaloneScriptValue = standaloneScript.getValue();

                if (standaloneScriptValue != null) {
                    /*
                     * If we have Standalone Scripts then convert them.
                     */
                    String convertedJavaScripts =
                            convertArray(standaloneScriptValue,
                                    allArrayDataTypeNames);
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
     * removes any use of arrays[] by Script Util methods
     * ScriptUtil.getArrayElement and ScriptUtil.setArrayElement.
     * 
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertScripts(com.tibco.xpd.xpdl2.Process,
     *      com.tibco.xpd.xpdl2.Expression)
     * 
     * @param process
     * @param expression
     * @return
     */
    @Override
    protected IStatus convertScripts(Process process, Expression expression,
            List<ProcessRelevantData> allProcessRelevantData) {

        /*
         * get all the names of the data that is in the scope of the process and
         * whose type is Array.
         */
        Set<String> allArrayDataTypeNames =
                getAllArrayDataTypeNames(allProcessRelevantData);

        if (ScriptGrammarFactory.JAVASCRIPT.equals(expression
                .getScriptGrammar())) {

            String javaScripts = expression.getText();

            if (javaScripts != null && !javaScripts.isEmpty()) {

                String convertedJavaScripts =
                        convertArray(javaScripts, allArrayDataTypeNames);
                /*
                 * Set the converted Scripts.
                 */
                setConvertedScriptToExpression(expression, convertedJavaScripts);

            }
        }

        return Status.OK_STATUS;
    }

    /**
     * removes any use of arrays[] by Script Util methods
     * ScriptUtil.getArrayElement and ScriptUtil.setArrayElement.
     * 
     * @param javaScripts
     * @param allArrayDataTypeNames
     * @return
     */
    private String convertArray(String javaScripts,
            Set<String> allArrayDataTypeNames) {

        NestedLineList javaScriptLines = new NestedLineList(2);

        JavaScriptLineList javaScriptLine = new JavaScriptLineList(javaScripts);

        for (String eachScript : javaScriptLine) {

            if (doesScriptHaveArray(eachScript, allArrayDataTypeNames)) {

                ArrayToScriptConverter converter =
                        new ArrayToScriptConverter(eachScript);

                String convertedString = converter.getConvertedString();

                if (convertedString != null && !convertedString.isEmpty()) {

                    javaScriptLines.add(convertedString);
                } else {

                    javaScriptLines.add(eachScript);
                }

            } else {
                javaScriptLines.add(eachScript);
            }
        }

        return javaScriptLines.toString();
    }

    /**
     * 
     * @param eachScript
     * @param allArrayDataTypeNames
     * @return <code>true</code> if the script contains array name, else
     *         <code>false</code>
     */
    private boolean doesScriptHaveArray(String eachScript,
            Set<String> allArrayDataTypeNames) {

        for (String string : allArrayDataTypeNames) {
            if (eachScript.contains(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param allProcessRelevantData
     * @return all {@link ProcessRelevantData} names which are of array type.
     */
    private Set<String> getAllArrayDataTypeNames(
            List<ProcessRelevantData> allProcessRelevantData) {

        Set<String> arrayDataNames = new HashSet<String>();

        for (ProcessRelevantData processRelevantData : allProcessRelevantData) {
            if (processRelevantData.isIsArray()) {
                arrayDataNames.add(processRelevantData.getName());
            }
        }
        return arrayDataNames;
    }

}