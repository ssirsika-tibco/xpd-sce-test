/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.ipm.iProcessExt.StandaloneScript;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.studioiprocess.scriptconverter.NestedLineList;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Contribution that inlines all the Standlone Scripts to the place from which
 * they were invoked. This contribution should be the last most 'Script'
 * contribution and should be before the iProcessExt removal contribution
 * 
 * @author kthombar
 * @since 18-May-2014
 */
public class StandaloneInlineScriptConversionContribution extends
        AbstractProcessScriptConversion {

    /**
     * 
     */
    private final String SCRIPT_Method = "script"; //$NON-NLS-1$

    /**
     * 
     */
    private final String CALL_METHOD = "call"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertStandaloneScripts(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList, java.util.List)
     * 
     * @param process
     * @param standaloneScripts
     * @param allProcessRelevantData
     * @return the Status of conversion
     */
    @Override
    protected IStatus convertStandaloneScripts(Process process,
            EList<StandaloneScript> standaloneScripts,
            List<ProcessRelevantData> allProcessRelevantData) {

        MultiStatus scriptMultiStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        Map<String, String> standaloneScriptBeforeConversionMap =
                new HashMap<String, String>();

        for (StandaloneScript standaloneScript : standaloneScripts) {
            String standaloneScriptName = standaloneScript.getName();
            if (standaloneScriptName != null && !standaloneScriptName.isEmpty()) {

                String standaloneScriptValue = standaloneScript.getValue();

                if (standaloneScriptValue != null) {
                    /*
                     * converting script name to upper case as iProcess scripts
                     * are case insensitive
                     */
                    standaloneScriptBeforeConversionMap
                            .put(standaloneScriptName.toUpperCase(),
                                    standaloneScriptValue);
                }
            }
        }

        if (!standaloneScriptBeforeConversionMap.isEmpty()) {

            IStatus inlineStandaloneScriptCallStatus =
                    inlineStandaloneScriptCall(process,
                            standaloneScriptBeforeConversionMap);

            if (IStatus.ERROR == inlineStandaloneScriptCallStatus.getSeverity()) {

                return new Status(
                        IStatus.ERROR,
                        IProcessAMXBPMConverterPlugin.PLUGIN_ID,
                        String.format(Messages.StandaloneInlineScriptConversionContribution_ProblemsDuringConversion_msg,
                                process.getName(),
                                inlineStandaloneScriptCallStatus.getMessage()));
            }

            scriptMultiStatus.add(inlineStandaloneScriptCallStatus);
        }

        return scriptMultiStatus;
    }

    /**
     * Inlines the Standalone Script to the place from where they are called.
     * 
     * @param process
     *            the Process whose Scripts are to be scanned to see if they
     *            call any Standalone Scripts
     * @param standaloneScriptBeforeConversionMap
     *            the Map of Standalone scripts before conversion.
     * @return the Status of conversion
     */
    private IStatus inlineStandaloneScriptCall(Process process,
            Map<String, String> standaloneScriptBeforeConversionMap) {

        MultiStatus scriptMultiStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        /* map that will store already converted inlined script per process */
        Map<String, String> standaloneScriptAfterConversionMap =
                new HashMap<String, String>();

        StandaloneScriptsConverter converter =
                new StandaloneScriptsConverter(
                        standaloneScriptBeforeConversionMap,
                        standaloneScriptAfterConversionMap, process);

        for (Iterator iterator = process.eAllContents(); iterator.hasNext();) {

            EObject eo = (EObject) iterator.next();
            if (eo instanceof Expression) {

                Expression expression = (Expression) eo;

                if (ScriptGrammarFactory.JAVASCRIPT.equals(expression
                        .getScriptGrammar())) {

                    String javaScripts = expression.getText();

                    if (javaScripts != null && !javaScripts.isEmpty()) {

                        StringBuffer inlinedStandaloneScripts =
                                new StringBuffer();

                        IStatus status =
                                inlineScripts(javaScripts,
                                        converter,
                                        inlinedStandaloneScripts);

                        if (IStatus.ERROR == status.getSeverity()) {
                            return status;
                        }

                        scriptMultiStatus.add(status);

                        String inlinedScript =
                                inlinedStandaloneScripts.toString();

                        if (inlinedScript != null && !inlinedScript.isEmpty()) {
                            setConvertedScriptToExpression(expression,
                                    inlinedScript);
                        }
                    }
                }
            }
        }

        return scriptMultiStatus;

    }

    /**
     * Inlines the Standalone Scripts.
     * <p>
     * If the Standalone Script is invoked by the 'CALL("scriptName")' method
     * then we comment out the Call method and insert the invoked script below
     * it.
     * <p>
     * If the Standalone Script is invoked by the
     * 'SCRIPT_Method("scriptName",args1,args2..)' method, then we keep the
     * method call as it is and insert the invoked script below it but comment
     * it out as human intervention is required.
     * 
     * @param javaScripts
     *            the javaScripts
     * @param converter
     * @param inlinedStandaloneScripts
     *            should be populated with the inlined Scripts
     * @param process
     *            the process whose scripts are scanned.
     * @return the Status of conversion
     */
    private IStatus inlineScripts(String javaScripts,
            StandaloneScriptsConverter converter,
            StringBuffer inlinedStandaloneScripts) {

        MultiStatus scriptMultiStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        NestedLineList javaScriptLines = new NestedLineList(2);

        JavaScriptLineList javaScriptLineList =
                new JavaScriptLineList(javaScripts);

        for (String eachLine : javaScriptLineList) {

            if (eachLine.toLowerCase().contains(CALL_METHOD)
                    || eachLine.toLowerCase().contains(SCRIPT_Method)) {

                IStatus standaloneScriptConverterStatus =
                        converter.convertScripts(eachLine);

                if (IStatus.ERROR == standaloneScriptConverterStatus
                        .getSeverity()) {

                    return standaloneScriptConverterStatus;
                }

                scriptMultiStatus.add(standaloneScriptConverterStatus);

                javaScriptLines.add(converter.getConvertedString());

            } else {
                javaScriptLines.add(eachLine);
            }

        }

        inlinedStandaloneScripts.append(javaScriptLines.toString());

        return scriptMultiStatus;
    }

    /**
     * @see com.tibco.xpd.iprocess.amxbpm.converter.contributions.AbstractProcessScriptConversion#convertScripts(com.tibco.xpd.xpdl2.Process,
     *      com.tibco.xpd.xpdl2.Expression, java.util.List)
     * 
     * @param process
     * @param expression
     * @param allProcessRelevantData
     * @return the Status of conversion
     */
    @Override
    protected IStatus convertScripts(Process process, Expression expression,
            List<ProcessRelevantData> allProcessRelevantData) {

        /*
         * We do not wish to do anything over here.
         */
        return Status.OK_STATUS;
    }
}