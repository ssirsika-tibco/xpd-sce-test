/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.ipm.iProcessExt.EAIStepDefinition;
import com.tibco.xpd.ipm.iProcessExt.IProcessExtPackage;
import com.tibco.xpd.iprocess.amxbpm.converter.internal.Messages;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Description;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * This Conversion Contribution does not do any specific changes related to
 * conversion to BPM, it only adds a FIXME to the converted Java Step, to inform
 * user that the EAI Java Steps are not automatically converted and should be
 * handled by the user.It also copies the Java Step Definition, to the
 * Description of the Service Task for User's reference.
 * 
 * Does the following
 * 
 * 1. Adds a FIXME: to the Service Task. 2. Copies EAI Java Service Step
 * Definition to the Service Task Description.
 * 
 * @author aprasad
 * @since 25-Jul-2014
 */
public class EAIJavaStepConversionContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * 
     */
    private static final String EAI_JAVA_STEP_TYPE = "EAIJAVA"; //$NON-NLS-1$

    private static final String IPROCESS_EAI_IMPLEMENTATION_TYPE =
            "iProcessEAI"; //$NON-NLS-1$

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

        /*
         * 1. Handle Each Process, Do not have handle Process Interface as
         * Process Interface can't contain EAI Step/Service Task
         */

        for (Process process : processes) {

            /* 2. get all Activities for the given Process */

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {

                TaskType taskType = TaskObjectUtil.getTaskTypeStrict(activity);

                if (TaskType.SERVICE_LITERAL.equals(taskType)) {
                    /* For Service Task */

                    /*
                     * we can skip type check for Task and null Check for
                     * TaskService as TaskType is already checked above
                     */
                    Task task = (Task) activity.getImplementation();
                    TaskService taskService = task.getTaskService();

                    Object otherAttribute =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());

                    if (otherAttribute instanceof String) {
                        String implementationName = (String) otherAttribute;

                        if (IPROCESS_EAI_IMPLEMENTATION_TYPE
                                .equals(implementationName)) {

                            Object otherElement =
                                    Xpdl2ModelUtil
                                            .getOtherElement(taskService,
                                                    IProcessExtPackage.eINSTANCE
                                                            .getDocumentRoot_EAIStepDefinition());

                            if (otherElement instanceof EAIStepDefinition) {

                                EAIStepDefinition eaiStepDef =
                                        (EAIStepDefinition) otherElement;

                                if (EAI_JAVA_STEP_TYPE.equals(eaiStepDef
                                        .getType())) {

                                    /* Add FIXME Annotation */

                                    IpmImportUtil
                                            .addFixMeToActivity(activity,
                                                    Messages.EAIJavaStepConversionContribution_EAIJavaStepFIXMEText);

                                    /*
                                     * Copy Step Definition to the Step
                                     * Description
                                     */

                                    StringBuffer stepDefValue =
                                            new StringBuffer(
                                                    eaiStepDef.getValue());

                                    if (stepDefValue.length() > 0) {

                                        Description activityDescription =
                                                activity.getDescription();

                                        if (activityDescription == null) {

                                            activityDescription =
                                                    Xpdl2Factory.eINSTANCE
                                                            .createDescription();

                                            activity.setDescription(activityDescription);

                                        } else {

                                            stepDefValue
                                                    .append(activityDescription
                                                            .getValue());
                                        }

                                        activityDescription
                                                .setValue(stepDefValue
                                                        .toString());
                                    }

                                }
                            }
                        }
                    }

                }

            }
        }

        return Status.OK_STATUS;
    }
}
