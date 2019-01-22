/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Activator;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.utils.ProcessDeveloperUtil;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateSendTaskMappings;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.commands.AbstractLateExecuteCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Implementation class that does the additional stuff required for generating a
 * business service from incoming request activities in a business process
 * 
 * @author bharge
 * @since 25 Feb 2015
 */
public class BizServiceOnIncomingRequestActivityCreator extends
        AbstractProcessCreator {

    Activity requestActivity;

    /**
     * @param rootCategoryId
     * @param defaultFragmentId
     * @param processType
     */
    public BizServiceOnIncomingRequestActivityCreator(String rootCategoryId,
            String defaultFragmentId, ProcessWidgetType processType,
            Activity activity) {

        super(rootCategoryId, defaultFragmentId, processType);
        this.requestActivity = activity;
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

        List<DataField> dataFieldsList = new ArrayList<DataField>();
        PortTypeOperation reqActivityPortTypeOp =
                Xpdl2ModelUtil.getPortTypeOperation(requestActivity);

        if (null != reqActivityPortTypeOp) {
            /**
             * XPD-957: generate data fields for pageflow as per the parameters
             * in the user defined wsdl configured on message start event
             */
            List<FormalParameter> formalParams = null;
            try {

                formalParams =
                        GenerateProcessUtil
                                .getFormalParamForPageflow(requestActivity);
            } catch (CoreException e) {

                /*
                 * If Business Service cannot be generated show an error dialog
                 * and throw Core Exception.
                 */
                MessageDialog
                        .openError(PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getShell(),
                                Messages.JavaScriptConceptUtil_CannotGenerateBusinessServiceErrorDialog_title,
                                e.getMessage());
                Activator
                        .getDefault()
                        .getLogger()
                        .error(e,
                                Messages.JavaScriptConceptUtil_CannotGenerateBusinessServiceErrorDialog_title);

                /*
                 * Sid XPD-7697 Make parent command unexecutable to ensure it is
                 * aborted.
                 */
                return UnexecutableCommand.INSTANCE;
            }

            if (null != formalParams && !formalParams.isEmpty()) {

                for (FormalParameter formalParameter : formalParams) {

                    ModeType mode = formalParameter.getMode();
                    if (ModeType.IN_LITERAL.equals(mode)
                            || ModeType.INOUT_LITERAL.equals(mode)) {

                        DataField dataField =
                                GenerateProcessUtil
                                        .createDataFieldForAssociatedData(formalParameter);
                        dataFieldsList.add(dataField);
                    }
                }
            }

        } else {
            /**
             * generate data fields for business service as per the parameters
             * in the business process
             */
            dataFieldsList =
                    GenerateProcessUtil.createDataFields(requestActivity);
        }
        process.getDataFields().addAll(dataFieldsList);

        /*
         * Creates Associated Parameters for all the user tasks in the given
         * process
         */
        AssociateParametersToUserTaskCommand assocParamsToUserTaskCmd =
                new AssociateParametersToUserTaskCommand(editingDomain, process);
        postCreateCmd.append(assocParamsToUserTaskCmd);

        /**
         * add the wsdl information and data mappings for the send task present
         * in the newly generated process
         */

        AbstractLateExecuteCommand addWsdlAndMappingsCmd =
                new AddWsdlInfoAndSendTaskMappingsCommand(editingDomain,
                        process);

        postCreateCmd.append(addWsdlAndMappingsCmd);

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
     * the wsdl information and data mappings for the send task present in the
     * newly generated business service
     * 
     * @author bharge
     * @since 24 Nov 2011
     */
    private final class AddWsdlInfoAndSendTaskMappingsCommand extends
            AbstractLateExecuteCommand {

        /**
         * @param editingDomain
         * @param contextObject
         */
        public AddWsdlInfoAndSendTaskMappingsCommand(
                EditingDomain editingDomain, Object contextObject) {
            super(editingDomain, contextObject);
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

            Process parentBizProcess = requestActivity.getProcess();
            Process pageflowProcess = (Process) contextObject;

            cmpdCmd.append(GenerateProcessUtil
                    .setPublishAsBusinessService(editingDomain, pageflowProcess));

            for (Activity activity : pageflowProcess.getActivities()) {

                if (isSendTask(activity)) {

                    ProcessDeveloperUtil.generateWsdlInfo(editingDomain,
                            cmpdCmd,
                            parentBizProcess,
                            activity,
                            requestActivity);

                    cmpdCmd.append(new UpdateSendTaskMappings(editingDomain,
                            activity));

                    /* Set send task name to "Invoke ProcessName ActivityName" */
                    String sendTaskName =
                            Xpdl2ModelUtil
                                    .getDisplayNameOrName(requestActivity);

                    cmpdCmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    String.format(Messages.GenerateStartEventPageflowAction_InvokeBusinessProcessTaskLabel_label,
                                            sendTaskName)));

                    if (null != cmpdCmd && cmpdCmd.canExecute()) {
                        return cmpdCmd;
                    }
                }
            }

            return null;
        }

        /**
         * @param activity
         * @return
         */
        private boolean isSendTask(Activity activity) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                if (null != task.getTaskSend()) {
                    return true;
                }
            }
            return false;
        }
    }

}
