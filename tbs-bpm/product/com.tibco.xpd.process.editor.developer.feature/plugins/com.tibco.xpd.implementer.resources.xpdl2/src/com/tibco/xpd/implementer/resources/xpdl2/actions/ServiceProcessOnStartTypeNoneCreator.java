/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.preCommit.AddSubProcInputMappingsFromFieldsCommand;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.ServiceProcessConfiguration;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation class that does the additional stuff required for generating a
 * service process from start type none in a business process
 * 
 * @author bharge
 * @since 24 Feb 2015
 */
public class ServiceProcessOnStartTypeNoneCreator extends
        AbstractProcessCreator {

    Activity startActivity;

    /**
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param processType
     */
    public ServiceProcessOnStartTypeNoneCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType,
            Activity activity) {

        super(rootCategoryId, defaultFragmentId, processType);
        this.startActivity = activity;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.actions.AbstractProcessCreator#postProcessCreate(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.edit.domain.EditingDomain,
     *      com.tibco.xpd.xpdl2.Package)
     * 
     * @param process
     * @param editingDomain
     * @param xpdlPackage
     * @return
     */
    @Override
    protected Command postProcessCreate(Process process,
            EditingDomain editingDomain, Package xpdlPackage) {

        CompoundCommand postCreateCmd = new CompoundCommand();

        /**
         * generate data fields for service process as per the parameters in the
         * business process
         */
        List<DataField> dataFieldsList =
                GenerateProcessUtil.createDataFields(startActivity);
        process.getDataFields().addAll(dataFieldsList);

        /*
         * Adds the sub-process reference from the call sub-process activity in
         * the generated service process to the start event none activity's
         * parent process.
         */
        AbstractLateExecuteCommand addSubProcRefCommand =
                new AddSubProcessReferenceCommand(editingDomain, process);

        postCreateCmd.append(addSubProcRefCommand);
        return postCreateCmd;
    }

    /**
     * Adds the sub-process reference from the call sub-process activity in the
     * generated service process to the start type none activity's parent
     * process.
     * 
     * @author bharge
     * @since 20 Feb 2015
     */
    private class AddSubProcessReferenceCommand extends
            AbstractLateExecuteCommand {

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AddSubProcessReferenceCommand(EditingDomain editingDomain,
                Process serviceProcess) {

            super(editingDomain, serviceProcess);
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand#createCommand(org.eclipse.emf.edit.domain.EditingDomain,
         *      java.lang.Object)
         * 
         * @param editingDomain
         * @param contextObject
         * @return
         */
        @Override
        protected Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            CompoundCommand cmpdCmd = new CompoundCommand();

            Process parentBizProcess = startActivity.getProcess();

            Process serviceProcess = (Process) contextObject;

            /*
             * Go through all service process activities to get hold of the call
             * sub-process activity.
             */
            for (Activity activity : serviceProcess.getActivities()) {

                /*
                 * Check if the current activity is a call sub-process activity.
                 */
                if (isCallSubProcessActivity(activity)) {

                    SubFlow sf = (SubFlow) activity.getImplementation();
                    /*
                     * Set label and name of task to "call process label"
                     */
                    String taskLabel =
                            String.format(Messages.GeneratedProcess_SubprocActivity_label_prefix,
                                    Xpdl2ModelUtil
                                            .getDisplayNameOrName(parentBizProcess));
                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    taskLabel));
                    cmpdCmd.append(SetCommand.create(editingDomain,
                            activity,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            NameUtil.getInternalName(taskLabel, false)));

                    /*
                     * Update call sub-process mappings.
                     */
                    cmpdCmd.append(new AddSubProcInputMappingsFromFieldsCommand(
                            editingDomain, activity));
                    /*
                     * Add reference to the parent business process from the
                     * call sub-process activity in the pageflow.
                     */
                    cmpdCmd.append(SetCommand.create(editingDomain,
                            sf,
                            Xpdl2Package.eINSTANCE.getSubFlow_ProcessId(),
                            parentBizProcess.getId()));
                    /*
                     * Set the execution mode of the call sub-process activity
                     * to Async-Detached.
                     */
                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    sf,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AsyncExecutionMode(),
                                    AsyncExecutionMode.DETACHED));
                    /* Set the suspend resume with parent to false */
                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    sf,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SuspendResumeWithParent(),
                                    false));
                    /*
                     * Set the start strategy of the call sub-process activity
                     * to Schedule-start.
                     */
                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    sf,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_StartStrategy(),
                                    SubProcessStartStrategy.SCHEDULE_START));
                    /* Set the service process configuration */
                    ServiceProcessConfiguration serviceProcessConfiguration =
                            XpdExtensionFactory.eINSTANCE
                                    .createServiceProcessConfiguration();
                    serviceProcessConfiguration
                            .setDeployToPageflowRuntime(false);
                    serviceProcessConfiguration.setDeployToProcessRuntime(true);

                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherElementCommand(editingDomain,
                                    serviceProcess,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ServiceProcessConfiguration(),
                                    serviceProcessConfiguration));

                    if (null != cmpdCmd && cmpdCmd.canExecute()) {

                        return cmpdCmd;
                    }
                }
            }

            return null;
        }

        /**
         * Checks if the activity implementation is subflow
         * 
         * @param activity
         * @return <code>true</code> if activity implementation is subflow
         */
        private boolean isCallSubProcessActivity(Activity activity) {

            if (activity.getImplementation() instanceof SubFlow) {

                return true;
            }
            return false;
        }
    }
}
