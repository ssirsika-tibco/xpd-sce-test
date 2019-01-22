/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.preCommit.AddPortTypeCommand;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateJavaScriptMappingsCommand;
import com.tibco.xpd.processeditor.xpdl2.preCommit.UpdateMappingsCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * The web service details section responsible showing up on the Start event,
 * receive task and intermediate catch event.
 * 
 * @author rsomayaj
 * 
 */
public class APIReqActivitiesWebServiceSection extends WebServiceDetailsSection {

    private Button setDefaultButton;

    /**
     * Check Box for the option 'Reply Immediately with Process ID'. Only
     * available for Start Message events. Unchecked by default.
     */
    protected Button btnReplyImmediatelyWithProcessId;

    /**
     * Check box for "Correlate Immediately", available on correlating
     * activities.
     */
    protected Button btnCorrelateImmediately;

    private Composite replyImmediateContainer;

    private Composite correlateImmediatelyContainer;

    /*
     * Allows us to control forced refreshing of tabs when user changed
     * ReplyImmediate option (in order that the Output Process Id mapping tab is
     * shown.
     */
    private Boolean previousReplyImmediateValue = null;

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        /*
         * Reset boolean tag back to null so we can detect firtst doRefresh()
         * after set Input.
         */
        previousReplyImmediateValue = null;

        super.setInput(items);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#isBWImplementation()
     * 
     * @return
     */
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
         * button "Set Default" to it.
         */
        Composite operationContainer = importWsdlButton.getParent();

        /*
         * the layout of the operation container allows creation of 3 controls,
         * hence make it 4 as we need to add one more button.
         */
        GridLayout layout = (GridLayout) operationContainer.getLayout();
        layout.numColumns = 4;

        /*
         * Add Set Default button to the operation section.
         */
        setDefaultButton =
                tk.createButton(operationContainer,
                        Messages.APIReqActivitiesWebServiceSection_BtnSetDefault_label,
                        SWT.PUSH,
                        ""); //$NON-NLS-1$
        setDefaultButton.setLayoutData(new GridData(SWT.CENTER, SWT.NONE,
                false, false));
        manageControl(setDefaultButton);

        /*
         * Create reply with process id control.
         */
        createReplyWithProcessIdControl(parent, tk);

        /*
         * Create correlate immediately control
         */
        createCorrelateImmediatelyControl(parent, tk);
    }

    /**
     * Creates the "Correlate Immediately" control.
     * 
     * @param parent
     * @param tk
     */
    private void createCorrelateImmediatelyControl(Composite parent,
            XpdFormToolkit tk) {
        /*
         * Filler to fill the first column.
         */
        Composite filler = tk.createComposite(parent);
        GridData gridData = new GridData();
        gridData.heightHint = 1;
        filler.setLayoutData(gridData);

        correlateImmediatelyContainer = tk.createComposite(parent);
        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.marginBottom = 5;
        correlateImmediatelyContainer.setLayout(gl);
        correlateImmediatelyContainer.setVisible(true);

        /*
         * hide the control initially as it should be visible only for
         * correlating activities.
         */
        gridData =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.VERTICAL_ALIGN_CENTER);
        gridData.heightHint = 0;
        correlateImmediatelyContainer.setLayoutData(gridData);
        correlateImmediatelyContainer.setVisible(false);

        btnCorrelateImmediately =
                tk.createButton(correlateImmediatelyContainer,
                        Messages.APIReqActivitiesWebServiceSection_CorrelateImmediatelyButton_label,
                        SWT.CHECK);

        btnCorrelateImmediately
                .setToolTipText(Messages.APIReqActivitiesWebServiceSection_CorrelateImmediatelyButton_tooltip);

        btnCorrelateImmediately.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));

        manageControl(btnCorrelateImmediately);
    }

    /**
     * Creates the "Reply with process id" control
     * 
     * @param parent
     * @param tk
     */
    private void createReplyWithProcessIdControl(Composite parent,
            XpdFormToolkit tk) {
        /*
         * ** Start changes ABPM-470 & XPD-3704 add checkbox option for 'Reply
         * Immediate with Procedd ID'
         */
        Composite filler = tk.createComposite(parent);
        GridData gridData = new GridData();
        gridData.heightHint = 1;
        filler.setLayoutData(gridData);

        /*
         * Use a container so we can take control of gap between reply
         * immediuate and surroundings when it is shown.
         */
        replyImmediateContainer = tk.createComposite(parent);
        GridLayout gl = new GridLayout(1, false);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        gl.marginBottom = 5;
        replyImmediateContainer.setLayout(gl);

        /*
         * set this to up to hide this control only visible for Start Message
         * events
         */
        gridData =
                new GridData(GridData.FILL_HORIZONTAL
                        | GridData.VERTICAL_ALIGN_CENTER);
        gridData.heightHint = 0;
        replyImmediateContainer.setLayoutData(gridData);
        replyImmediateContainer.setVisible(false);

        /* And the button itself. */
        btnReplyImmediatelyWithProcessId =
                tk.createButton(replyImmediateContainer,
                        Messages.APIReqActivitiesWebServiceSection_BtnStartMessageEventReplyImmediate,
                        SWT.CHECK);

        btnReplyImmediatelyWithProcessId.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));

        manageControl(btnReplyImmediatelyWithProcessId);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#doGetCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public Command doGetCommand(Object obj) {
        final Activity activity = (Activity) getInput();
        if (obj == setDefaultButton) {

            final CompoundCommand cmd = new CompoundCommand();
            EditingDomain ed = getEditingDomain();

            WebServiceOperation wso =
                    activityMessage.getWebServiceOperation(getActivityInput());

            if (wso != null) {
                EObject[] emptyArray = new EObject[] {};
                Command performersCommand =
                        TaskObjectUtil.getSetPerformersCommand(ed,
                                getActivityInput(),
                                emptyArray);
                // Only add it if it's not empty
                if (performersCommand != null && performersCommand.canExecute()) {
                    cmd.append(performersCommand);
                }
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        wso,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                        "")); //$NON-NLS-1$
                cmd.append(activityMessage.getClearWebServiceCommand(ed,
                        getActivityInput()));
            }

            cmd.append(new AddPortTypeCommand(ed, activity.getProcess(),
                    activity));
            cmd.append(UpdateMappingsCommandFactory
                    .getUpdateMappingsCommand(ed, activity));
            List<Activity> replyActivities =
                    ReplyActivityUtil.getReplyActivities(activity);
            for (Activity repAct : replyActivities) {
                cmd.append(new UpdateJavaScriptMappingsCommand(ed, repAct));
            }

            return cmd;
        }/*
          * Start changes ABPM-470 & XPD-3704 set/reset replyImmediate flag for
          * the start message activity
          */
        else if (btnReplyImmediatelyWithProcessId != null
                && obj == btnReplyImmediatelyWithProcessId) {
            EditingDomain ed = getEditingDomain();

            Boolean replyImmediate =
                    new Boolean(btnReplyImmediatelyWithProcessId.getSelection());
            TriggerResultMessage triggerResultMessage =
                    EventObjectUtil.getTriggerResultMessage(activity);
            CompoundCommand cmd = new CompoundCommand();
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    triggerResultMessage,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ReplyImmediately(),
                    replyImmediate));

            cmd.setLabel(Messages.APIReqActivitiesWebServiceSection_CmdSetStartMessageEventReplyImmediate);

            return cmd;
        } else if (btnCorrelateImmediately != null
                && obj == btnCorrelateImmediately) {

            /*
             * get the selection
             */
            boolean correlateImmediately =
                    btnCorrelateImmediately.getSelection();

            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.APIReqActivitiesWebServiceSection_SetUnsetCorrelateImmediatelyCommand_label);

            Event event = activity.getEvent();
            if (event != null) {
                /*
                 * If the activity is an event then add the correlate
                 * immediately other attribute to TriggerMessageResult
                 */
                TriggerResultMessage triggerResultMessage =
                        EventObjectUtil.getTriggerResultMessage(activity);

                cmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(getEditingDomain(),
                                triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CorrelateImmediately(),
                                correlateImmediately));
            } else {
                /*
                 * If the Activity is a Receive Task then add the correlate
                 * immediately other attribute to "TaskReceive"
                 */
                Implementation impl = activity.getImplementation();

                if (impl instanceof Task) {

                    Task task = (Task) impl;

                    TaskReceive taskReceive = task.getTaskReceive();

                    if (taskReceive != null) {

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(getEditingDomain(),
                                        taskReceive,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CorrelateImmediately(),
                                        correlateImmediately));
                    }
                }
            }
            return cmd;
        }
        // ** End changes ABPM-470 & XPD-3704
        return super.doGetCommand(obj);
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.WebServiceDetailsSection#doRefresh()
     * 
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void doRefresh() {
        if (getInput() instanceof Activity) {
            boolean disabledFlag = false;
            Activity activity = (Activity) getInput();

            if (Xpdl2ModelUtil.isProcessAPIActivity(activity)
                    && !ReplyActivityUtil.isReplyActivity(activity)) {
                setDefaultButton.setVisible(true);
            } else {
                setDefaultButton.setVisible(false);
                disabledFlag = true;
            }

            if (Xpdl2ModelUtil.isGeneratedRequestActivity(activity)) {
                disabledFlag = true;
            } else if (activity.getProcess() == null
                    || !ProcessDestinationUtil
                            .shouldGenerateWSDLForProcessDestinations(activity
                                    .getProcess())) {
                disabledFlag = true;
            }

            setDefaultButton.setEnabled(!disabledFlag);

            // ** Start changes ABPM-470 & XPD-3704
            Boolean newIsReplyImmediate =
                    adjustReplyImmediateOptionVisibility(activity);

            /*
             * If the reply immediate status has changed since last time then we
             * need to force a refresh of the property tabs to get eclipse to
             * re-ask other tab-sections if they want to show or not (so that
             * tabs conditional on isReplyImnmediate get to switch on/ off
             * accordingly.
             * 
             * Don't need to do in on first ever entry though (because those
             * sections will be asked according to filter according to the
             * original state at the point when object is first selected.
             */
            if (previousReplyImmediateValue != null) {

                if (previousReplyImmediateValue != newIsReplyImmediate) {
                    previousReplyImmediateValue = newIsReplyImmediate;
                    Display.getDefault().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            refreshTabs();
                        }
                    });
                }

            } else {
                /*
                 * First doRefresh after set input - save current state for next
                 * time in.
                 */
                previousReplyImmediateValue = newIsReplyImmediate;
            }

            // ** End changes ABPM-470 & XPD-3704

            /*
             * XPD-4822: Saket: We have decided to disable 'reply immediately
             * with process id' option for any message event activity that
             * implements a process interface message event as it was never
             * intended to be used with process-interface-message-event feature
             * (and hence has never worked).
             */
            if (ProcessInterfaceUtil.isEventImplemented(activity)) {
                btnReplyImmediatelyWithProcessId.setEnabled(false);
            } else {
                btnReplyImmediatelyWithProcessId.setEnabled(true);
            }

            if (ProcessDestinationUtil.isBPMDestinationSelected(activity
                    .getProcess())
                    && Xpdl2ModelUtil.isCorrelatingActivity(activity)) {
                /*
                 * "Correlate Immediately" control should only be visible for
                 * Correlating Activities in processes having "BPM" destination
                 * enabled.
                 */
                if (!correlateImmediatelyContainer.getVisible()) {
                    /*
                     * If not already visible then make it visible
                     */

                    // ((GridData)
                    // correlateImmediatelyContainer.getLayoutData()).heightHint
                    // =
                    // SWT.DEFAULT;
                    GridData gridData =
                            new GridData(GridData.FILL_HORIZONTAL
                                    | GridData.VERTICAL_ALIGN_CENTER);
                    gridData.heightHint = SWT.DEFAULT;
                    correlateImmediatelyContainer.setLayoutData(gridData);
                    correlateImmediatelyContainer.setVisible(true);
                    correlateImmediatelyContainer.getParent().layout(true);
                }
                /*
                 * Set the correlate immediately button value.
                 */
                btnCorrelateImmediately
                        .setSelection(isActivityConfiguredToCorrelateImmediately(activity));

            } else {
                if (correlateImmediatelyContainer.getVisible()) {
                    /*
                     * If the activity is not a correlating activity and if it
                     * does not belong to a Process having "BPM" destination
                     * then hide the control.
                     */

                    // ((GridData)
                    // correlateImmediatelyContainer.getLayoutData()).heightHint
                    // =
                    // 0;
                    GridData gridData =
                            new GridData(GridData.FILL_HORIZONTAL
                                    | GridData.VERTICAL_ALIGN_CENTER);
                    gridData.heightHint = 0;
                    correlateImmediatelyContainer.setLayoutData(gridData);
                    correlateImmediatelyContainer.setVisible(false);
                    correlateImmediatelyContainer.getParent().layout(true);
                }
            }
        }
        super.doRefresh();
        return;
    }

    /**
     * 
     * 
     * 
     * @param activity
     *            the activity under inspection.
     * @return <code>true</code> if the passed correlating activity has
     *         "Correlate Immediately" enabled, else <code>false</code>
     */
    private boolean isActivityConfiguredToCorrelateImmediately(Activity activity) {

        boolean isCorrelateImmediately = false;

        Event event = activity.getEvent();

        if (event != null) {
            /*
             * If passed activity is an event, then get the
             * "Correlate Immediately" info from its TriggerMessageResult.
             */
            TriggerResultMessage triggerResultMessage =
                    EventObjectUtil.getTriggerResultMessage(activity);

            if (triggerResultMessage != null) {

                isCorrelateImmediately =
                        Xpdl2ModelUtil
                                .getOtherAttributeAsBoolean(triggerResultMessage,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CorrelateImmediately());
            }
        } else {
            /*
             * If the passed Activity is an Receive Task then get the
             * "Correlate Immediately" info from its TaskReceive xpdl element.
             */
            Implementation impl = activity.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskReceive taskReceive = task.getTaskReceive();

                if (taskReceive != null) {

                    isCorrelateImmediately =
                            Xpdl2ModelUtil
                                    .getOtherAttributeAsBoolean(taskReceive,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CorrelateImmediately());
                }
            }
        }
        return isCorrelateImmediately;
    }

    /**
     * @param activity
     * @param startEvent
     * 
     * @return currentState of isReplyImmediate
     */
    @SuppressWarnings("deprecation")
    private Boolean adjustReplyImmediateOptionVisibility(Activity activity) {
        Boolean isReplyImmediate = Boolean.FALSE;

        StartEvent startEvent =
                (activity.getEvent() instanceof StartEvent) ? (StartEvent) activity
                        .getEvent() : null;

        /*
         * 'Reply Immediate With process Id' Option is only applicable to Start
         * Message Events
         * 
         * XPD-3766 - and only applicable to BPM destination.
         * 
         * XPD-7046: Reply immediately option is not applicable for message
         * start events direly under the process.
         */
        boolean shouldShowOption =
                (startEvent != null)
                        && (startEvent.getTriggerResultMessage() != null)
                        && (activity.eContainer() instanceof Process)
                        && ProcessDestinationUtil
                                .isBPMDestinationSelected(activity.getProcess());
        if (shouldShowOption) {
            // If Not Already Visible
            if (!replyImmediateContainer.getVisible()) {
                ((GridData) replyImmediateContainer.getLayoutData()).heightHint =
                        SWT.DEFAULT;
                replyImmediateContainer.setVisible(true);
                replyImmediateContainer.getParent().layout(true);
            }

            isReplyImmediate =
                    ReplyActivityUtil
                            .isStartMessageWithReplyImmediate(activity);
            btnReplyImmediatelyWithProcessId.setSelection(isReplyImmediate);

        } else {// hide otherwise, if Visible.
            if (replyImmediateContainer.getVisible()) {//
                ((GridData) replyImmediateContainer.getLayoutData()).heightHint =
                        0;
                replyImmediateContainer.setVisible(false);
                replyImmediateContainer.getParent().layout(true);
            }
        }

        return isReplyImmediate;
    }

}
