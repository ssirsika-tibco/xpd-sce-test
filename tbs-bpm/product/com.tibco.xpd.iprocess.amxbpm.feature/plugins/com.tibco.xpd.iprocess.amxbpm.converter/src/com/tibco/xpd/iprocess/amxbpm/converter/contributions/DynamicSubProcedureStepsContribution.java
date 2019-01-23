/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.DynamicSubProcessTask;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.ipm.iProcessExt.TaskProperties;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.MultiInstanceScripts;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopMultiInstance;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.MIOrderingType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;

/**
 * Contribution to modify dynamic sub processes to make them suite BPM
 * environment.
 * 
 * @author sajain
 * @since Apr 28, 2014
 */
public class DynamicSubProcedureStepsContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {
        try {
            monitor.beginTask("", 1); //$NON-NLS-1$

            /*
             * Sid XPD-6230 No need to set task name if it's judst a repeat of
             * the contribution plugin.xml desc' as that is output by framework
             * anyway.
             */

            for (Process eachProcess : processes) {

                List<Activity> allProcessActivities = new ArrayList<Activity>();

                /*
                 * Fetch all activities from processes.
                 */
                allProcessActivities.addAll(eachProcess.getActivities());

                /*
                 * Fetch all activities from activity sets.
                 */
                EList<ActivitySet> allActivitySets =
                        eachProcess.getActivitySets();
                for (ActivitySet eachActivitySet : allActivitySets) {
                    allProcessActivities
                            .addAll(eachActivitySet.getActivities());
                }

                if (allProcessActivities != null
                        && !allProcessActivities.isEmpty()) {
                    convertDynamicSubprocess(allProcessActivities);
                }
            }

            monitor.worked(1);

            /*
             * XPD-6370: Main framework now reports status with each conversion
             * description. so no need to do it here also.
             */
            return Status.OK_STATUS;

        } finally {
            monitor.done();
        }
    }

    /**
     * Convert dynamic sub processes to make them suite BPM environment.
     * 
     * @param allProcessActivities
     */
    private void convertDynamicSubprocess(List<Activity> allProcessActivities) {

        for (Activity eachProcessActivity : allProcessActivities) {

            TaskType taskTypeStrict =
                    TaskObjectUtil.getTaskTypeStrict(eachProcessActivity);

            if (TaskType.SUBPROCESS_LITERAL.equals(taskTypeStrict)) {

                Implementation implementation =
                        eachProcessActivity.getImplementation();

                if (implementation instanceof SubFlow) {
                    SubFlow subFlow = (SubFlow) implementation;

                    if (subFlow != null) {

                        boolean isUsingProcessInterface =
                                isUsingProcessInteface(eachProcessActivity);

                        if (isUsingProcessInterface) {
                            /*
                             * It is a dynamic sub process.
                             */

                            /*
                             * 1. Set dynamic sub-process tasks to
                             * multi-instance-parallel.
                             */
                            Loop loop = eachProcessActivity.getLoop();
                            LoopMultiInstance loopMultiInstance = null;
                            if (null != loop) {
                                loop.setLoopType(LoopType.MULTI_INSTANCE_LITERAL);

                                loopMultiInstance = loop.getLoopMultiInstance();
                                if (null != loopMultiInstance) {
                                    loopMultiInstance
                                            .setMIOrdering(MIOrderingType.PARALLEL_LITERAL);
                                } else {
                                    loopMultiInstance =
                                            getXpdlFactory()
                                                    .createLoopMultiInstance();
                                    loopMultiInstance
                                            .setMIOrdering(MIOrderingType.PARALLEL_LITERAL);
                                    loop.setLoopMultiInstance(loopMultiInstance);
                                }
                            } else {
                                Loop newLoop = getXpdlFactory().createLoop();
                                newLoop.setLoopType(LoopType.MULTI_INSTANCE_LITERAL);
                                loopMultiInstance =
                                        getXpdlFactory()
                                                .createLoopMultiInstance();
                                loopMultiInstance
                                        .setMIOrdering(MIOrderingType.PARALLEL_LITERAL);
                                MultiInstanceScripts mIScripts =
                                        getXpdExtensionFactory()
                                                .createMultiInstanceScripts();
                                setExtensionElement(loopMultiInstance,
                                        getXpdExtensionPackage()
                                                .getDocumentRoot_MultiInstanceScripts(),
                                        mIScripts);

                                newLoop.setLoopMultiInstance(loopMultiInstance);

                                eachProcessActivity.setLoop(newLoop);
                            }

                            /*
                             * 2. Set multi-instance ‘loop expression’ (number
                             * of instances in the case of parallel) to
                             * RuntimeIdentifierArrayField.size().
                             */
                            Object procIdFieldAttr =
                                    getExtensionAttribute(subFlow,
                                            getXpdExtensionPackage()
                                                    .getDocumentRoot_ProcessIdentifierField());

                            if (procIdFieldAttr != null) {
                                String expressionText =
                                        procIdFieldAttr.toString() + ".size();"; //$NON-NLS-1$

                                Expression expression =
                                        getXpdlFactory()
                                                .createExpression(expressionText);

                                expression
                                        .setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);

                                eachProcessActivity.getLoop()
                                        .getLoopMultiInstance()
                                        .setMICondition(expression);
                            }

                            /*
                             * 3. Set “Allow Unqualified Sub-Process
                             * Identification” property of all dynamic
                             * sub-process tasks by default.
                             */
                            setExtensionAttribute(subFlow,
                                    getXpdExtensionPackage()
                                            .getDocumentRoot_AllowUnqualifiedSubProcessIdentification(),
                                    true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the specified activity refers a process interface.
     * 
     * @param act
     * @return
     */
    private boolean isUsingProcessInteface(Activity act) {
        Object taskPropElem =
                getExtensionElement(act,
                        IProcessExtPackage.eINSTANCE
                                .getDocumentRoot_TaskProperties());
        if (null != taskPropElem && taskPropElem instanceof TaskProperties) {
            TaskProperties taskProp = (TaskProperties) taskPropElem;
            DynamicSubProcessTask dynSubProcTsk =
                    taskProp.getDynamicSubProcessTask();
            if (dynSubProcTsk != null) {
                return !dynSubProcTsk.isIsGraftStep();
            }
        }
        return false;
    }

}
