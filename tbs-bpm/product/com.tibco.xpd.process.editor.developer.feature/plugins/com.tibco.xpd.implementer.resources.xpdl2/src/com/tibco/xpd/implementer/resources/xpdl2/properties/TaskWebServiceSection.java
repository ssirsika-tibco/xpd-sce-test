/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wst.wsdl.Service;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.Messages;
import com.tibco.xpd.implementer.resources.xpdl2.wizards.srvctask.ServiceTaskWsdlCreationWizard;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateServiceTaskMappings;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.ErrorThrowerInfo;
import com.tibco.xpd.xpdExtension.ErrorThrowerType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Property section for assigning a web service to a service task.
 * 
 * @author aallway
 */
public class TaskWebServiceSection extends WebServiceDetailsSection {

    protected Button generateWsdlButton;

    @Override
    protected boolean isBWImplementation() {
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#createOperationSelectionAndClearBtns(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param tk
     */
    @Override
    protected void createOperationSelectionAndClearBtns(Composite parent,
            XpdFormToolkit tk) {
        /*
         * XPD-6972: Kapil - Initially we were un-necessarily creating the
         * operation section with the select and clear buttons again from this
         * class, rather we let the super class create this control and re-use
         * it over here.
         */

        /*
         * Create the operation control
         */
        super.createOperationSelectionAndClearBtns(parent, tk);

        /*
         * get the operations container as we would like to add an additional
         * button "Generate" to it.
         */
        Composite operationContainer = importWsdlButton.getParent();

        /*
         * the layout of the operation container allows creation of 3 controls,
         * hence make it 4 as we need to add one more button.
         */
        GridLayout layout = (GridLayout) operationContainer.getLayout();
        layout.numColumns = 4;

        generateWsdlButton =
                tk.createButton(operationContainer,
                        Messages.TaskWebServiceSection_GenerateBtn_label,
                        SWT.PUSH,
                        Messages.TaskWebServiceSection_0);
        generateWsdlButton.setLayoutData(new GridData(SWT.CENTER, SWT.NONE,
                false, false));
        manageControl(generateWsdlButton);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public Command doGetCommand(Object obj) {
        if (obj == generateWsdlButton) {
            // Raise a wizard which prompts to generate a wsdl
            Display display = getSite().getShell().getDisplay();
            final CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.TaskWebServiceSection_updateServiceDetails_command_label);
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    // If activity has any parameters / datafields associated as
                    // Array, then the WSDL wont be generated correctly.
                    if (hasActivityArrayParams()) {
                        MessageDialog
                                .openError(getSite().getShell(),
                                        Messages.TaskWebServiceSection_WSDLGenErr_label,
                                        Messages.TaskWebServiceSection_WSDLGenErrDialog_shortdesc);
                    } else {
                        WorkingCopy wc =
                                WorkingCopyUtil.getWorkingCopyFor(getInput());
                        IProject project =
                                wc.getEclipseResources().get(0).getProject();
                        ServiceTaskWsdlCreationWizard wizard =
                                new ServiceTaskWsdlCreationWizard(
                                        Xpdl2ModelUtil
                                                .getPackage(getActivityInput()),
                                        project, getActivityInput());
                        WizardDialog dialog =
                                new WizardDialog(getSite().getShell(), wizard);
                        dialog.open();
                        if (dialog.getReturnCode() == Dialog.OK) {
                            String portTypeName =
                                    getActivityInput().getProcess().getName();
                            /*
                             * XPD-5911: if a port type name has leading
                             * digit(s) then prefix with underscore
                             */
                            if (portTypeName != null
                                    && Character.isDigit(portTypeName.charAt(0))) {

                                portTypeName = "_" + portTypeName; //$NON-NLS-1$
                            }
                            String activityName = getActivityInput().getName();
                            /*
                             * XPD-5911: if a activity name has leading digit(s)
                             * then prefix with underscore
                             */
                            if (activityName != null
                                    && Character.isDigit(activityName.charAt(0))) {

                                activityName = "_" + activityName; //$NON-NLS-1$
                            }
                            String operationName = activityName;
                            String wsdlFileName = wizard.getWSDLFileName();

                            Service service = wizard.getService();
                            WsdlServiceKey key;

                            if (service != null) {
                                key =
                                        new WsdlServiceKey(service.getQName()
                                                .getLocalPart(), portTypeName,
                                                operationName, portTypeName,
                                                operationName, wsdlFileName);
                            } else {
                                key =
                                        new WsdlServiceKey(null, null, null,
                                                portTypeName, operationName,
                                                wsdlFileName);
                            }

                            updateWsdlServiceKeyWithTransportName(project, key);

                            cmd.append(activityMessage
                                    .getAssignWebServiceCommand(getEditingDomain(),
                                            getActivityInput().getProcess(),
                                            getActivityInput(),
                                            wsdlFileName,
                                            true,
                                            key));
                            cmd.append(getUpdateMappingsCommand());

                            /*
                             * Update any error events with the catch error code
                             * (fault)
                             */
                            if (TaskObjectUtil
                                    .getTaskTypeStrict(getActivityInput()) == TaskType.SERVICE_LITERAL) {
                                addCommandToUpdateCatchErrorEvents(cmd,
                                        getActivityInput());
                            }
                        }
                    }
                }

            });
            return cmd;
        }
        return super.doGetCommand(obj);
    }

    /**
     * @return update mappings command which applies to the task after the WSDL
     *         is created. These mappings are predicted and will be assured
     *         since the WSDL is generated from these parameters.
     */
    protected Command getUpdateMappingsCommand() {
        return new UpdateServiceTaskMappings(getEditingDomain(),
                getActivityInput());
    }

    /**
     * @return true if the activity which is the input of the section has any
     *         array parameters or datafields.
     */
    private boolean hasActivityArrayParams() {
        Activity activityInput = getActivityInput();
        List<ProcessRelevantData> associatedProcessRelevantDataForActivity =
                ProcessInterfaceUtil
                        .getAssociatedProcessRelevantDataForActivity(activityInput);

        for (ProcessRelevantData processRelevantData : associatedProcessRelevantDataForActivity) {
            if (processRelevantData.isIsArray()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update any error events attached to the given service task with the
     * generated faults as the catch error code.
     * 
     * @param ccmd
     * @param serviceTask
     */
    private void addCommandToUpdateCatchErrorEvents(CompoundCommand ccmd,
            Activity serviceTask) {

        Collection<Activity> events =
                Xpdl2ModelUtil.getAttachedEvents(serviceTask);
        for (Activity event : events) {
            if (EventObjectUtil.getEventTriggerType(event) == EventTriggerType.EVENT_ERROR_LITERAL) {
                ResultError resError =
                        Xpdl2Factory.eINSTANCE.createResultError();
                String faultName =
                        event.getName() != null && !event.getName().isEmpty() ? event
                                .getName() : event.getId();

                /*
                 * Set the ErrorCode to the name of the generated fault.
                 */
                resError.setErrorCode(faultName);

                ErrorThrowerInfo errorThrowerInfo =
                        XpdExtensionFactory.eINSTANCE.createErrorThrowerInfo();
                errorThrowerInfo
                        .setThrowerType(ErrorThrowerType.PROCESS_ACTIVITY);
                errorThrowerInfo.setThrowerId(serviceTask.getId());
                errorThrowerInfo.setThrowerContainerId(serviceTask.getProcess()
                        .getId());

                Xpdl2ModelUtil.setOtherElement(resError,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ErrorThrowerInfo(),
                        errorThrowerInfo);

                /*
                 * Also always create a catch error mappings container up-front
                 * (mainly for the benefit of the mapper).
                 */
                CatchErrorMappings catchErrorMappings =
                        XpdExtensionFactory.eINSTANCE
                                .createCatchErrorMappings();
                catchErrorMappings.setMessage(Xpdl2Factory.eINSTANCE
                        .createMessage());

                Xpdl2ModelUtil.setOtherElement(resError,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_CatchErrorMappings(),
                        catchErrorMappings);

                ccmd.append(SetCommand.create(getEditingDomain(), event
                        .getEvent(), Xpdl2Package.eINSTANCE
                        .getIntermediateEvent_ResultError(), resError));
            }
        }
    }
}
