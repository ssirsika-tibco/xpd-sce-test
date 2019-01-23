/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.ipm.iProcessExt.StandaloneScript;
import com.tibco.xpd.ipm.iProcessExt.impl.ProcessPropertiesImpl;
import com.tibco.xpd.iprocess.amxbpm.converter.IProcessAMXBPMConverterPlugin;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Abstract class that runs all the expected "SCRIPT" conversions on the
 * Expression Scripts and the Standalone Scripts.
 * <p>
 * We iterate through all the {@link Process}/es searching for
 * {@link Expression} and {@link StandaloneScript} and run expected Script
 * conversions on them.
 * 
 * @author kthombar
 * @since 21-May-2014
 */
public abstract class AbstractProcessScriptConversion extends
        AbstractIProcessToBPMContribution {

    /**
     * 
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public final IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        IProcessExtPackage iProcessExtPackage = getIProcessExtPackage();

        MultiStatus multiStatus =
                new MultiStatus(IProcessAMXBPMConverterPlugin.PLUGIN_ID, 0,
                        "", null); //$NON-NLS-1$

        try {
            monitor.beginTask("", processes.size()); //$NON-NLS-1$                      

            for (Process eachProcess : processes) {

                /* get all the process data */
                List<ProcessRelevantData> allProcessRelevantData =
                        ProcessInterfaceUtil
                                .getAllProcessRelevantData(eachProcess);

                Object extensionElement =
                        getExtensionElement(eachProcess,
                                iProcessExtPackage
                                        .getDocumentRoot_ProcessProperties());

                if (extensionElement instanceof ProcessPropertiesImpl) {
                    /* Check if we have Standalone Scripts */

                    ProcessPropertiesImpl processProperties =
                            (ProcessPropertiesImpl) extensionElement;
                    EList<StandaloneScript> standaloneScripts =
                            processProperties.getStandaloneScripts();

                    if (!standaloneScripts.isEmpty()) {
                        /*
                         * If we have Standalone Scripts then all the
                         * conversions that are run on normal scripts should
                         * also be run on standalone scripts
                         */
                        IStatus standaloneScriptConversionStatus =
                                convertStandaloneScripts(eachProcess,
                                        standaloneScripts,
                                        allProcessRelevantData);

                        if (IStatus.ERROR == standaloneScriptConversionStatus
                                .getSeverity()) {
                            /*
                             * If we have an error , return immediately
                             */
                            return standaloneScriptConversionStatus;
                        } else if (IStatus.WARNING == standaloneScriptConversionStatus
                                .getSeverity()) {
                            /*
                             * go on collecting all the warnings so that we can
                             * show them in the logs.
                             */
                            multiStatus.add(standaloneScriptConversionStatus);
                        }
                    }
                }

                for (Iterator iterator = eachProcess.eAllContents(); iterator
                        .hasNext();) {

                    EObject eo = (EObject) iterator.next();
                    if (eo instanceof Expression) {
                        /*
                         * Iterate through all the expressions and run
                         * conversions on them
                         */
                        Expression expression = (Expression) eo;
                        IStatus scriptConversionStatus =
                                convertScripts(eachProcess,
                                        expression,
                                        allProcessRelevantData);

                        if (IStatus.ERROR == scriptConversionStatus
                                .getSeverity()) {
                            /*
                             * If we have an error , return immediately
                             */
                            return scriptConversionStatus;
                        } else if (IStatus.WARNING == scriptConversionStatus
                                .getSeverity()) {
                            /*
                             * go on collecting all the warnings so that we can
                             * show them in the logs.
                             */
                            multiStatus.add(scriptConversionStatus);
                        }
                    }
                }

                monitor.worked(1);
            }

            return multiStatus;

        } finally {
            monitor.done();
        }
    }

    /**
     * Runs the expected conversion on the Standalone Scripts.
     * 
     * @param process
     * @param standaloneScripts
     *            list of all standalone scripts in the process
     * @param allProcessRelevantData
     *            all the process data
     * @return {@link IStatus} which gives the status of the conversion
     */
    protected abstract IStatus convertStandaloneScripts(Process process,
            EList<StandaloneScript> standaloneScripts,
            List<ProcessRelevantData> allProcessRelevantData);

    /**
     * Runs the expected conversion on the Expression Scripts. We should use(are
     * expected to use)
     * {@link AbstractProcessScriptConversion#setConvertedScriptToExpression(Expression, String)}
     * to set the Expression values.
     * 
     * @param process
     * @param expression
     *            the expression on which the conversions are expected to be
     *            run.
     * @param allProcessRelevantData
     *            all the process data
     * @return {@link IStatus} which gives the status of the conversion
     */
    protected abstract IStatus convertScripts(Process process,
            Expression expression,
            List<ProcessRelevantData> allProcessRelevantData);

    /**
     * Sets the Converted Scripts to the Expression
     * 
     * @param expression
     *            the {@link Expression} whose scripts are to be set
     * @param convertedScript
     *            the Converted Script which is to be added to the expression.
     */
    protected final void setConvertedScriptToExpression(Expression expression,
            String convertedScript) {

        if (convertedScript != null) {

            int textIndex = -1;
            for (int i = 0; i < expression.getMixed().size(); i++) {
                if (XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text()
                        .equals(expression.getMixed().getEStructuralFeature(i))) {
                    textIndex = i;
                    break;
                }
            }
            /* Add the converted Script */
            if (textIndex != -1) {
                expression.getMixed().setValue(textIndex, convertedScript);
            }
        }
    }

}
