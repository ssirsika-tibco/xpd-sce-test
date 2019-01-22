/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventAdapter.TimerTriggerMode;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Section for Timer Start and Intermediate events. Allows selection of datetime
 * or cycle, analyst can enter a description of either.
 */
public class EventTriggerTypeTimerSection extends
        AbstractFilteredTransactionalSection implements ISection {
    private String instrumentationPrefixName;

    private Text descText;

    private Button dateTimeButton;

    private Button cycleButton;

    private TimerTriggerMode currentMode = null;

    private Button withdrawnOnTimeout;

    private Button continueOnTimeout;

    private Composite timeoutComp;

    private Composite root;

    private Button activityDeadlineButton;

    public EventTriggerTypeTimerSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    public EventTriggerTypeTimerSection(String instrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
        this.instrumentationPrefixName = instrumentationPrefixName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doCreateControls(org.eclipse
     * .swt.widgets.Composite, com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        GridData gData;

        root = toolkit.createComposite(parent);
        root.setLayout(new GridLayout(1, false));

        activityDeadlineButton =
                toolkit.createButton(root,
                        Messages.EventTriggerTypeTimerSection_useAsActivityDeadline_button,
                        SWT.CHECK);
        manageControl(activityDeadlineButton);

        gData = new GridData(GridData.FILL_HORIZONTAL);
        gData.horizontalIndent = 5;
        activityDeadlineButton.setLayoutData(gData);

        /* Don't show unless BPM destination is selected. */
        gData.heightHint = 0;
        activityDeadlineButton.setVisible(false);

        timeoutComp = toolkit.createComposite(root);
        GridLayout tcLayout = new GridLayout(2, false);
        timeoutComp.setLayout(tcLayout);
        timeoutComp
                .setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

        withdrawnOnTimeout =
                toolkit.createButton(timeoutComp,
                        Messages.EventTriggerTypeTimerSection_WithdrawOnTimeout_button,
                        SWT.RADIO,
                        instrumentationPrefixName + "WithdrawOnTimeout"); //$NON-NLS-1$
        withdrawnOnTimeout.setData("name", "buttonEventWithdrawOnTimeout"); //$NON-NLS-1$ //$NON-NLS-2$
        manageControl(withdrawnOnTimeout);

        continueOnTimeout =
                toolkit.createButton(timeoutComp,
                        Messages.EventTriggerTypeTimerSection_ContinueOnTimeout_button,
                        SWT.RADIO,
                        instrumentationPrefixName + "ContinueOnTimeout"); //$NON-NLS-1$
        continueOnTimeout.setData("name", "buttonEventContinueOnTimeout"); //$NON-NLS-1$ //$NON-NLS-2$
        manageControl(continueOnTimeout);

        Composite buttonComp = toolkit.createComposite(root);
        tcLayout = new GridLayout(2, false);
        buttonComp.setLayout(tcLayout);
        buttonComp.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

        dateTimeButton =
                toolkit.createButton(buttonComp,
                        Messages.EventTriggerTypeTimerSection_DateTime,
                        SWT.RADIO,
                        instrumentationPrefixName + "DateTime"); //$NON-NLS-1$
        dateTimeButton.setData("name", "buttonEventDateTime"); //$NON-NLS-1$ //$NON-NLS-2$
        dateTimeButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
                false));
        manageControl(dateTimeButton);

        cycleButton =
                toolkit.createButton(buttonComp,
                        Messages.EventTriggerTypeTimerSection_Cycle,
                        SWT.RADIO,
                        instrumentationPrefixName + "Cycle"); //$NON-NLS-1$
        cycleButton.setData("name", "buttonEventCycle"); //$NON-NLS-1$ //$NON-NLS-2$
        cycleButton
                .setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
        manageControl(cycleButton);

        Composite textComp = toolkit.createComposite(root);
        tcLayout = new GridLayout(2, false);
        textComp.setLayout(tcLayout);
        textComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        toolkit.createLabel(textComp,
                Messages.EventTriggerTypeTimerSection_Description,
                SWT.NONE);
        descText =
                toolkit.createText(textComp,
                        "", SWT.NONE, instrumentationPrefixName + "TimerDescription"); //$NON-NLS-1$ //$NON-NLS-2$
        descText.setData("name", "textEventTimerDescription"); //$NON-NLS-1$ //$NON-NLS-2$
        gData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gData.widthHint = LAYOUT_DATA_SHORT_WIDTH_HINT;
        descText.setLayoutData(gData);
        manageControl(descText);

        return root;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean doRefresh = super.shouldRefresh(notifications);

        if (!doRefresh) {
            /*
             * If this is an event activity that is attached to an Activity
             * boundary then check if that Activity's activity deadline id has
             * changed and if it affects this section.
             */
            Activity activity = getActivity();
            if (activity != null) {
                Activity task = EventObjectUtil.getTaskAttachedTo(activity);

                if (task != null) {
                    for (Notification notification : notifications) {
                        if (notification.getNotifier() != null
                                && notification.getNotifier().equals(task)
                                && notification.getFeature() == XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ActivityDeadlineEventId()) {

                            // If the activity deadline id setting affects this
                            // trigger event the refresh section
                            if (activity.getId()
                                    .equals(notification.getOldValue())
                                    || activity.getId()
                                            .equals(notification.getNewValue())) {
                                doRefresh = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return doRefresh;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.properties.AbstractXpdSection#doGetCommand(java.lang
     * .Object)
     */
    @Override
    protected Command doGetCommand(Object obj) {
        Command cmd = null;

        Activity eventAct = getActivity();

        EditingDomain ed = getEditingDomain();
        if (ed != null && eventAct != null) {

            if (obj == dateTimeButton && ((Button) obj).getSelection()) {
                cmd =
                        EventObjectUtil.getSetTimerTriggerModeCommand(ed,
                                eventAct,
                                TimerTriggerMode.DATETIME);

            } else if (obj == cycleButton && ((Button) obj).getSelection()) {
                cmd =
                        EventObjectUtil.getSetTimerTriggerModeCommand(ed,
                                eventAct,
                                TimerTriggerMode.CYCLE);

            } else if (obj == withdrawnOnTimeout
                    && ((Button) obj).getSelection()) {
                cmd =
                        EventObjectUtil.getSetContinueOnTimeoutFlag(ed,
                                eventAct,
                                false);

            } else if (obj == continueOnTimeout
                    && ((Button) obj).getSelection()) {
                cmd =
                        EventObjectUtil.getSetContinueOnTimeoutFlag(ed,
                                eventAct,
                                true);

            } else if (obj == descText) {
                cmd =
                        EventObjectUtil.getSetTriggerTimerDescCommand(ed,
                                eventAct,
                                getSelectedMode(),
                                descText.getText());
            } else if (obj == activityDeadlineButton) {
                Activity task = EventObjectUtil.getTaskAttachedTo(eventAct);
                if (task != null) {
                    cmd =
                            Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            task,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ActivityDeadlineEventId(),
                                            activityDeadlineButton
                                                    .getSelection() ? eventAct
                                                    .getId() : null);
                }
            }
        }
        return cmd;
    }

    /**
     * Mode as currently selected on UI.
     * 
     * @return
     */
    private TimerTriggerMode getSelectedMode() {
        if (dateTimeButton.getSelection()) {
            return TimerTriggerMode.DATETIME;
        } else {
            return TimerTriggerMode.CYCLE;
        }
    }

    /**
     * Show the options available when the event is attached to the border of an
     * Activity.
     * 
     * @param show
     *            <code>true</code> to show the options, <code>false</code> to
     *            hide them.
     */
    private void showAttachedEventOptions(Activity eventAct, boolean show) {
        if (show) {
            boolean changed = false;

            if (!timeoutComp.getVisible()) {
                GridData gd = (GridData) timeoutComp.getLayoutData();
                gd.heightHint = SWT.DEFAULT;
                gd.widthHint = SWT.DEFAULT;
                timeoutComp.setVisible(true);

                changed = true;
            }

            /* Don't show unless BPM destination is selected. */
            if (ProcessDestinationUtil.isBPMDestinationSelected(eventAct
                    .getProcess())) {
                if (!activityDeadlineButton.getVisible()) {
                    GridData gd =
                            (GridData) activityDeadlineButton.getLayoutData();
                    gd.heightHint = SWT.DEFAULT;
                    activityDeadlineButton.setVisible(true);
                    changed = true;
                }
            } else {
                if (activityDeadlineButton.getVisible()) {
                    GridData gd =
                            (GridData) activityDeadlineButton.getLayoutData();
                    gd.heightHint = 0;
                    activityDeadlineButton.setVisible(false);
                    changed = true;
                }
            }

            if (changed) {
                root.pack();
            }

        } else {
            boolean changed = false;

            if (timeoutComp.getVisible()) {
                GridData gd = (GridData) timeoutComp.getLayoutData();
                gd.heightHint = 0;
                gd.widthHint = 0;
                timeoutComp.setVisible(false);
                changed = true;
            }

            if (activityDeadlineButton.getVisible()) {
                GridData gd = (GridData) activityDeadlineButton.getLayoutData();
                gd.heightHint = 0;
                activityDeadlineButton.setVisible(false);

                changed = true;
            }

            if (changed) {
                root.pack();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#doRefresh()
     */
    @Override
    protected void doRefresh() {
        // If controls have been disposed then unregister adapter
        if (descText.isDisposed()) {
            return;
        }

        Activity eventAct = getActivity();

        if (eventAct != null) {
            if (EventObjectUtil.getTimerTriggerMode(eventAct) != currentMode) {
                currentMode = EventObjectUtil.getTimerTriggerMode(eventAct);
                switch (currentMode) {
                case DATETIME:
                    dateTimeButton.setSelection(true);
                    cycleButton.setSelection(false);
                    break;
                case CYCLE:
                    cycleButton.setSelection(true);
                    dateTimeButton.setSelection(false);
                    break;
                }
            }

            if (EventObjectUtil.isAttachedToTask(eventAct)) {
                boolean contOnTimeout =
                        EventObjectUtil.getContinueOnTimeoutFlag(eventAct);
                withdrawnOnTimeout.setSelection(!contOnTimeout);
                continueOnTimeout.setSelection(contOnTimeout);

                /* Check if this event is the activity's deadline timer */
                boolean isActivityDeadline = false;
                Activity task = EventObjectUtil.getTaskAttachedTo(eventAct);
                if (task != null) {
                    Object id =
                            Xpdl2ModelUtil
                                    .getOtherAttribute(task,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ActivityDeadlineEventId());
                    isActivityDeadline =
                            (id instanceof String && id
                                    .equals(eventAct.getId()));
                }
                activityDeadlineButton.setSelection(isActivityDeadline);

                showAttachedEventOptions(eventAct, true);
            } else {
                // Not attached to task border, hide the withdraw/continue on
                // timeout buttons
                showAttachedEventOptions(eventAct, false);
            }

            updateText(descText, EventObjectUtil.getTriggerTimerDesc(eventAct,
                    getSelectedMode()));
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    public Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }
}
