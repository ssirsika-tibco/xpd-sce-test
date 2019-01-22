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
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.ipm.iProcessExt.StandaloneScript;
import com.tibco.xpd.n2.resources.script.IProcessScriptToBPMJavaScriptConverter;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.studioiprocess.scriptconverter.AbstractIProcessToJavaScriptConverter;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Converter that converts all the IProcess scripts to JavaScripts.
 * 
 * 
 * @author kthombar
 * @since 07-Apr-2014
 */
public class IProcessToJavascriptConverterContribution extends
        AbstractProcessScriptConversion {
    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertStandaloneScripts(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList)
     * 
     * @param process
     * @param standaloneScripts
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
                    Set<String> availableDataNames = new HashSet<String>();
                    /*
                     * If we have Standalone Scripts then convert them.
                     */
                    if (allProcessRelevantData != null) {
                        for (ProcessRelevantData data : allProcessRelevantData) {
                            availableDataNames.add(data.getName());
                        }
                    }

                    AbstractIProcessToJavaScriptConverter scriptConverter =
                            new IProcessScriptToBPMJavaScriptConverter(
                                    standaloneScriptValue, availableDataNames);

                    String javaScript =
                            scriptConverter.getJavaScriptApproximation();

                    /*
                     * Set the value of Standalone Scripts to that of the
                     * converted Scripts.
                     */
                    standaloneScript.setValue(javaScript);
                }
            }
        }

        return Status.OK_STATUS;
    }

    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertScripts(com.tibco.xpd.xpdl2.Process,
     *      com.tibco.xpd.xpdl2.Expression)
     * 
     * @param process
     * @param expression
     */
    @Override
    protected IStatus convertScripts(Process process, Expression expression,
            List<ProcessRelevantData> allProcessRelevantData) {

        if (AbstractIProcessToJavaScriptConverter.IPROCESSSCRIPT_GRAMMAR
                .equals(expression.getScriptGrammar())) {
            expression.setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
            /*
             * If expression is a simple (non user defined script) data mapping
             * then we should ONLY change the grammar (otherwise script
             * converter will append a semicolon and invalidate the mapping.
             */
            if (!isSimpleMappingExpression(expression)) {

                String sourceIProcessScript = expression.getText();

                if (sourceIProcessScript != null
                        && !sourceIProcessScript.isEmpty()) {

                    Set<String> dataNames =
                            getAvailableDataNames(expression,
                                    allProcessRelevantData);

                    AbstractIProcessToJavaScriptConverter scriptConverter =
                            new IProcessScriptToBPMJavaScriptConverter(
                                    sourceIProcessScript, dataNames);

                    String javaScript =
                            scriptConverter.getJavaScriptApproximation();
                    /*
                     * Set the converted Scripts.
                     */
                    setConvertedScriptToExpression(expression, javaScript);
                }
            }
        }
        return Status.OK_STATUS;
    }

    /**
     * @param expression
     * 
     * @return <code>true</code> if the expression represents a simple type
     *         mapping.
     */
    private boolean isSimpleMappingExpression(Expression expression) {
        /* Simple type mapping must have DataMapping as parent element. */
        if (expression.eContainer() instanceof DataMapping) {
            DataMapping dataMapping = (DataMapping) expression.eContainer();

            /*
             * And the DataMapping must not also contain a script information
             * (which denotes the xpdl2:Actual (expression) as being a complex
             * user defined script.
             */
            Object scriptInfo =
                    Xpdl2ModelUtil.getOtherElement(dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());

            if (scriptInfo == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to fetch the available data names from the expression provided.
     * 
     * @param expression
     * @param allProcessRelevantData
     * @return set of data names
     */
    private Set<String> getAvailableDataNames(Expression expression,
            List<ProcessRelevantData> allProcessRelevantData) {
        Set<String> availableDataNames = new HashSet<String>();

        if (expression != null) {
            List<ProcessRelevantData> allData = null;

            EObject activity =
                    Xpdl2ModelUtil.getAncestor(expression, Activity.class);
            if (activity != null) {
                allData =
                        ProcessInterfaceUtil
                                .getAllAvailableRelevantDataForActivity((Activity) activity);
            } else {
                EObject process =
                        Xpdl2ModelUtil.getAncestor(expression, Process.class);
                if (process != null) {
                    allData = allProcessRelevantData;
                }
            }

            if (allData != null) {
                for (ProcessRelevantData data : allData) {
                    availableDataNames.add(data.getName());
                }
            }
        }
        return availableDataNames;
    }

}
