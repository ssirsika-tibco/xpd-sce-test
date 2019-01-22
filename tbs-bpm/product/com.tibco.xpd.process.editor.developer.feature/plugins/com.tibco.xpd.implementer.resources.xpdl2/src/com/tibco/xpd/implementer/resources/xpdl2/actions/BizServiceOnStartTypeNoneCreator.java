/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateCallSubProcessMappings;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.AsyncExecutionMode;
import com.tibco.xpd.xpdExtension.SubProcessStartStrategy;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation class that does the additional stuff required for generating a
 * business service from start type none in a business process
 * 
 * @author bharge
 * @since 25 Feb 2015
 */
public class BizServiceOnStartTypeNoneCreator extends AbstractProcessCreator {

    Activity startActivity;

    /**
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param processType
     */
    public BizServiceOnStartTypeNoneCreator(String rootCategoryId,
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
         * generate data fields for business service as per the parameters in
         * the business process
         */
        List<DataField> dataFieldsList =
                GenerateProcessUtil.createDataFields(startActivity);
        process.getDataFields().addAll(dataFieldsList);

        /*
         * Creates Associated Parameters for all the user tasks in the given
         * process
         */
        AssociateParametersToUserTaskCommand assocParamsToUserTaskCmd =
                new AssociateParametersToUserTaskCommand(editingDomain, process);
        postCreateCmd.append(assocParamsToUserTaskCmd);

        /*
         * Publish the pageflow process as business service and adds the
         * sub-process reference from the call sub-process activity in the
         * generated business service to the request activity's parent process.
         */
        AbstractLateExecuteCommand addSubProcRefCommand =
                new AddSubProcessReferenceCommand(editingDomain, process);

        postCreateCmd.append(addSubProcRefCommand);

        return postCreateCmd;
    }

    /**
     * Goes thru all the user tasks in the given process and creates associated
     * parameters for them
     * 
     * 
     * @author bharge
     * @since 12 Aug 2014
     */
    private class AssociateParametersToUserTaskCommand extends
            AbstractLateExecuteCommand {

        Process process;

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AssociateParametersToUserTaskCommand(
                EditingDomain editingDomain, Object contextObject) {

            super(editingDomain, contextObject);
            if (contextObject instanceof Process) {

                this.process = (Process) contextObject;
            }
        }

        /**
         * @see com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand#execute()
         * 
         */
        @Override
        public Command createCommand(EditingDomain editingDomain,
                Object contextObject) {

            if (null != process) {

                CompoundCommand cmd = new CompoundCommand();

                Collection<Activity> allActivitiesInProc =
                        Xpdl2ModelUtil.getAllActivitiesInProc(process);
                for (Activity activity : allActivitiesInProc) {

                    if (TaskType.USER_LITERAL.equals(TaskObjectUtil
                            .getTaskTypeStrict(activity))) {

                        AssociatedParameters associatedParameters =
                                XpdExtensionFactory.eINSTANCE
                                        .createAssociatedParameters();

                        for (ProcessRelevantData data : process.getDataFields()) {

                            /*
                             * case ref types are not allowed as associated
                             * parameters for user tasks in pageflow processes
                             */
                            boolean caseRefTypeFieldInPageflow = false;
                            if (Xpdl2ModelUtil.isPageflowOrSubType(process)) {

                                if (data.getDataType() instanceof RecordType) {

                                    caseRefTypeFieldInPageflow = true;
                                }
                            }

                            if (!caseRefTypeFieldInPageflow) {

                                AssociatedParameter associatedParam =
                                        ProcessInterfaceUtil
                                                .createAssociatedParam(data);
                                associatedParam.setMandatory(true);

                                associatedParameters.getAssociatedParameter()
                                        .add(associatedParam);
                            }
                        }

                        if (!associatedParameters.getAssociatedParameter()
                                .isEmpty()) {

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherElementCommand(editingDomain,
                                            activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_AssociatedParameters(),
                                            associatedParameters));
                        }
                    }
                }
                return cmd;
            }
            return null;
        }
    }

    /**
     * This class publishes the pageflow process as business service and adds
     * the sub-process reference from the call sub-process activity in the
     * generated business service to the request activity's parent process.
     * 
     * @author sajain
     * @since Jan 10, 2015
     */
    private final class AddSubProcessReferenceCommand extends
            AbstractLateExecuteCommand {

        /**
         * Publish the pageflow process as business service and adds the
         * sub-process reference from the call sub-process activity in the
         * generated business service to the request activity's parent process.
         * 
         * @param editingDomain
         * @param pageflowProcess
         */
        public AddSubProcessReferenceCommand(EditingDomain editingDomain,
                Process pageflowProcess) {

            super(editingDomain, pageflowProcess);
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

            /*
             * Create compound command.
             */
            CompoundCommand cmpdCmd = new CompoundCommand();

            /*
             * Fetch parent business process (which initiated the generation of
             * business service).
             */
            Process parentBizProcess = startActivity.getProcess();

            /*
             * Fetch the generated pageflow process.
             */
            Process pageflowProcess = (Process) contextObject;

            /*
             * Publish the pageflow as business service.
             */
            cmpdCmd.append(GenerateProcessUtil
                    .setPublishAsBusinessService(editingDomain, pageflowProcess));

            /*
             * Go through all pageflow activities to get hold of the call
             * sub-process activity.
             */
            for (Activity activity : pageflowProcess.getActivities()) {

                /*
                 * Check if the current activity is a call sub-process activity.
                 */
                if (isCallSubProcessActivity(activity)) {

                    /*
                     * Get activity implementation.
                     */
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
                    cmpdCmd.append(new UpdateCallSubProcessMappings(
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

                    if (null != cmpdCmd && cmpdCmd.canExecute()) {
                        return cmpdCmd;
                    }
                }
            }

            return null;
        }

        /**
         * Return <code>true</code> if the specified activity is a call
         * sub-process activity.
         * 
         * @param activity
         * @return
         */
        private boolean isCallSubProcessActivity(Activity activity) {

            /*
             * Check if the activity implementation is subflow and then return
             * true, otherwise return false.
             */
            if (activity.getImplementation() instanceof SubFlow) {

                return true;
            }

            return false;
        }
    }
}
