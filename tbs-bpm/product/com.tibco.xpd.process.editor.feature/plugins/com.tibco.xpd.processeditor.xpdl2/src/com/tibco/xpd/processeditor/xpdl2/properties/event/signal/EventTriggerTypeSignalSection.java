/*
 * @copyright TIBCO Software Inc. (c) 2008 - 2013
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event.signal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.ISection;

import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl.IActivityPickerListener;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.SignalTypeExtensionPoint;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.SignalTypeExtensionPointManager;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.ActivityPickerFilters;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Signal Event general properties section.
 * 
 * @author aallway
 * @since 1.0
 */
public class EventTriggerTypeSignalSection extends
        AbstractFilteredTransactionalSection implements ISection {

    private Composite cancelNonCancelContainer;

    private Button cancelOnCatchSignal;

    private Button continueOnCatchSignal;

    private Composite root;

    private Label catchActionLabel;

    private Button rescheduleTimersNoneButton;

    private Button rescheduleTimersAllButton;

    private Button rescheduleTimersDeadlineButton;

    private Button rescheduleTimersSelectedButton;

    private ActivityPickerControl timerEventPickerControl;

    private Group rescheduleTimersGroup;

    /**
     * keeps track of the previously selected signal type button.
     */
    private Button previousSelectedSignalTypeButton = null;

    /**
     * keeps track if this is the first refresh since setInput() was called
     */
    private boolean firstRefreshSinceSetInput = true;

    /**
     * Pagebook for event handler controls
     */
    private PageBook eventHandlerPagebook;

    /**
     * event handler group
     */
    private Group eventHandlerGroup;

    private Boolean wasEventHandlerAtLastRefesh;

    /**
     * Stores all the signal type contributed extensions
     */
    private List<SignalTypeExtensionPoint> signalTypeExtensions = null;

    /**
     * group for signal types.
     */
    private Group signalNameGroup;

    /**
     * Wraps all the necessary signal type controls.
     */
    private List<SignalControlWrapper> signalTypeWrappers = null;

    public EventTriggerTypeSignalSection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
        /*
         * Get the SignalType extensions
         */
        SignalTypeExtensionPointManager extensionPointManager =
                new SignalTypeExtensionPointManager();
        signalTypeExtensions = extensionPointManager.getSignalTypeExtensions();
        /*
         * Initialize the wrapper.
         */
        signalTypeWrappers = new LinkedList<SignalControlWrapper>();
    }

    public EventTriggerTypeSignalSection(String intrumentationPrefixName) {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        wasEventHandlerAtLastRefesh = Boolean.FALSE;
        super.setInput(part, selection);
        /*
         * Call setInput for all the signaltype extensions.
         */
        for (SignalTypeExtensionPoint eachSignalTypeExtension : signalTypeExtensions) {

            AbstractSignalTypeSection signalTypeSection =
                    eachSignalTypeExtension.getSignalTypeSection();
            if (signalTypeSection != null) {

                signalTypeSection.setInput(part, selection);
            }
        }
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {

        super.setInput(items);
        /*
         * Call setInput for all the signaltype extensions.
         */
        for (SignalTypeExtensionPoint eachSignalTypeExtension : signalTypeExtensions) {

            AbstractSignalTypeSection signalTypeSection =
                    eachSignalTypeExtension.getSignalTypeSection();
            if (signalTypeSection != null) {

                signalTypeSection.setInput(items);
            }
        }
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
        root = toolkit.createComposite(parent);
        GridLayout rootLayout = new GridLayout(2, false);
        root.setLayout(rootLayout);

        /*
         * Create group for signal name.
         */
        signalNameGroup =
                toolkit.createGroup(root,
                        Messages.EventTriggerTypeSignalSection_SignalEventSignalNameGroup_label);
        GridLayout groupLayout = new GridLayout(2, false);
        signalNameGroup.setLayout(groupLayout);
        GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
        groupData.horizontalSpan = 2;
        signalNameGroup.setLayoutData(groupData);

        for (SignalTypeExtensionPoint eachSignalTypeExtension : signalTypeExtensions) {
            /*
             * For each extension point contributed create an LHS Signal Type
             * Radio button and an RHS Composite.
             */
            Button signalTypeRadioButton =
                    toolkit.createButton(signalNameGroup,
                            eachSignalTypeExtension.getLabel(),
                            SWT.RADIO);
            GridData buttonData = new GridData();
            signalTypeRadioButton.setLayoutData(buttonData);
            signalTypeRadioButton.setVisible(true);
            manageControl(signalTypeRadioButton);
            /*
             * Create RHS Composite.
             */
            Composite rhsComposite = toolkit.createComposite(signalNameGroup);
            GridLayout rhsCompositeLayout = new GridLayout(1, false);
            rhsCompositeLayout.marginHeight = -5;
            rhsComposite.setLayout(rhsCompositeLayout);
            rhsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            AbstractSignalTypeSection signalTypeSection =
                    eachSignalTypeExtension.getSignalTypeSection();

            if (signalTypeSection != null) {

                signalTypeRadioButton.setToolTipText(signalTypeSection
                        .getSignalTypeToolTip());
                /*
                 * Pass the RHS composite to createControl so that the
                 * contributing class can create the necessary controls and
                 * manage their lifecycle.
                 */
                Control createControls =
                        signalTypeSection.createControls(rhsComposite, toolkit);
                createControls.setLayoutData(new GridData(
                        GridData.FILL_HORIZONTAL));

                /*
                 * Wrap together the LHS signaltype button , the rhs composite
                 * and the signal type Section so that they can be easily used
                 * later.
                 */
                signalTypeWrappers
                        .add(new SignalControlWrapper(signalTypeRadioButton,
                                rhsComposite, signalTypeSection));
            }
        }

        /*
         * Catch-Signal Event-handler configuration control, Create Eventhandler
         * Group
         */
        eventHandlerGroup =
                toolkit.createGroup(root,
                        Messages.EventHandlerConfiguration_label);
        GridLayout eventHandlerGroupLayout = new GridLayout(1, true);
        eventHandlerGroup.setLayout(eventHandlerGroupLayout);
        GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
        gd1.horizontalSpan = 2;
        gd1.heightHint = 0;
        gd1.exclude = true;
        eventHandlerGroup.setLayoutData(gd1);
        eventHandlerGroup.setVisible(false);

        /*
         * create pagebook which will contain the event handler controls.
         */
        eventHandlerPagebook = new PageBook(eventHandlerGroup, SWT.NONE);

        eventHandlerPagebook.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        for (SignalControlWrapper signalWrapper : signalTypeWrappers) {
            /*
             * For each contributed extension call the
             * 'createAdditionalEventHandlerInitializerControls' method and let
             * the contributing class add controls as necessary.
             */
            AbstractSignalTypeSection signalTypeSection =
                    signalWrapper.getSignalTypeSection();

            if (signalTypeSection != null) {

                Composite createAdditionalEventHandlerInitializerControls =
                        signalTypeSection
                                .createAdditionalEventHandlerInitializerControls(eventHandlerPagebook,
                                        toolkit);

                if (createAdditionalEventHandlerInitializerControls != null) {

                    GridDataFactory
                            .fillDefaults()
                            .grab(true, true)
                            .applyTo(createAdditionalEventHandlerInitializerControls);

                    signalWrapper
                            .setEventHandlerPagebookPage(createAdditionalEventHandlerInitializerControls);
                }
            }
        }

        /*
         * Cancelling / Non cancelling event controls.
         */
        catchActionLabel =
                toolkit.createLabel(root,
                        Messages.EventTriggerTypeSignalSection_CatchAction_label);
        catchActionLabel.setVisible(false);

        cancelNonCancelContainer = toolkit.createComposite(root);
        GridLayout gl = new GridLayout(1, true);
        gl.marginLeft += 2;
        gl.marginHeight = 0;
        gl.marginTop = 2;
        gl.verticalSpacing += 2;
        cancelNonCancelContainer.setLayout(gl);

        /*
         * Individual container for buttons so that main container can contain
         * other controls like reschedule timers etc.
         */
        Composite cmp = toolkit.createComposite(cancelNonCancelContainer);
        gl = new GridLayout(2, true);
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        cmp.setLayout(gl);

        cmp.setLayoutData(new GridData());

        cancelOnCatchSignal =
                toolkit.createButton(cmp,
                        Messages.EventTriggerTypeSignalSection_CancelTaskOnSignal_button,
                        SWT.RADIO);

        GridData gd = new GridData();
        cancelOnCatchSignal.setLayoutData(gd);
        manageControl(cancelOnCatchSignal);

        continueOnCatchSignal =
                toolkit.createButton(cmp,
                        Messages.EventTriggerTypeSignalSection_ContinueTaskOnSignal_button,
                        SWT.RADIO);

        gd = new GridData();
        continueOnCatchSignal.setLayoutData(gd);

        manageControl(continueOnCatchSignal);

        /*
         * Reschedule timer controls.
         */
        rescheduleTimersGroup =
                toolkit.createGroup(cancelNonCancelContainer,
                        Messages.EventTriggerTypeSignalSection_RescheduleTimerEvents_group_label);
        gl = new GridLayout(2, false);
        rescheduleTimersGroup.setLayout(gl);

        /* Reschedule None... */
        rescheduleTimersNoneButton =
                toolkit.createButton(rescheduleTimersGroup,
                        Messages.EventTriggerTypeSignalSection_RescheduleTimersNone_radio_button,
                        SWT.RADIO);
        rescheduleTimersNoneButton
                .setToolTipText(Messages.EventTriggerTypeSignalSection_RescheduleTimersNone_radio_description);
        gd = new GridData();
        gd.horizontalSpan = 2;
        rescheduleTimersNoneButton.setLayoutData(gd);
        rescheduleTimersNoneButton.setData(null);
        manageControl(rescheduleTimersNoneButton);

        /* Reschedule All timers. */
        rescheduleTimersAllButton =
                toolkit.createButton(rescheduleTimersGroup,
                        Messages.EventTriggerTypeSignalSection_RescheduleTimersAll_radio_button,
                        SWT.RADIO);
        rescheduleTimersAllButton
                .setToolTipText(Messages.EventTriggerTypeSignalSection_RescheduleTimersAll_radio_description);
        gd = new GridData();
        gd.horizontalSpan = 2;
        rescheduleTimersAllButton.setLayoutData(gd);
        rescheduleTimersAllButton.setData(RescheduleTimerSelectionType.ALL);
        manageControl(rescheduleTimersAllButton);

        /* Reschedule Deadline timer. */
        rescheduleTimersDeadlineButton =
                toolkit.createButton(rescheduleTimersGroup,
                        Messages.EventTriggerTypeSignalSection_RescheduleTimersDeadline_radio_button,
                        SWT.RADIO);
        rescheduleTimersDeadlineButton
                .setToolTipText(Messages.EventTriggerTypeSignalSection_RescheduleTimersDeadline_radio_description);
        gd = new GridData();
        gd.horizontalSpan = 2;
        rescheduleTimersDeadlineButton.setLayoutData(gd);
        rescheduleTimersDeadlineButton
                .setData(RescheduleTimerSelectionType.DEADLINE);
        manageControl(rescheduleTimersDeadlineButton);

        /* Reschedule Deadline timer. */
        rescheduleTimersSelectedButton =
                toolkit.createButton(rescheduleTimersGroup,
                        Messages.EventTriggerTypeSignalSection_RescheduleTimersSelectedTimers_radio_button,
                        SWT.RADIO);
        rescheduleTimersSelectedButton
                .setToolTipText(Messages.EventTriggerTypeSignalSection_RescheduleTimersSelectedTimers_radio_description);
        rescheduleTimersSelectedButton.setLayoutData(new GridData());
        rescheduleTimersSelectedButton
                .setData(RescheduleTimerSelectionType.SELECTED);
        manageControl(rescheduleTimersSelectedButton);

        /* Picker for selected type. */
        timerEventPickerControl =
                new ActivityPickerControl(
                        rescheduleTimersGroup,
                        toolkit,
                        Messages.EventTriggerTypeSignalSection_RescheduleTimerEventPicker_dialog_title,
                        SWT.MULTI) {
                    @Override
                    protected IFilter createActivityFilter() {
                        return new ActivityPickerFilters.ValidTaskBoundaryTimerEventFilter(
                                EventObjectUtil
                                        .getTaskAttachedTo(getActivity()));
                    }
                };

        gd = new GridData(GridData.FILL_HORIZONTAL);
        timerEventPickerControl.setLayoutData(gd);

        timerEventPickerControl
                .addActivitiesPickedListener(new IActivityPickerListener() {
                    @Override
                    public void activitiesPicked(
                            Collection<Activity> selectedActivities) {
                        setSelectedRescheduleTimerEvents(selectedActivities);
                    }
                });

        /*
         * Hide all the non-cancelling signal controls by default.
         */
        showCancelNonCancelButtons(false);

        return root;
    }

    /**
     * Make the cancel on signal / continue on signal radio buttopns invisible.
     * 
     * @param show
     */
    private void showCancelNonCancelButtons(boolean show) {
        if (show && !cancelNonCancelContainer.getVisible()) {
            GridData gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
            gd.verticalIndent = 2;
            catchActionLabel.setLayoutData(gd);

            gd = new GridData(GridData.FILL_HORIZONTAL);
            rescheduleTimersGroup.setLayoutData(gd);

            gd =
                    new GridData(GridData.FILL_HORIZONTAL
                            | GridData.FILL_VERTICAL);
            cancelNonCancelContainer.setLayoutData(gd);

            cancelNonCancelContainer.setVisible(true);
            catchActionLabel.setVisible(true);

            root.layout(true);

        } else if (!show && cancelNonCancelContainer.getVisible()) {
            GridData gd = new GridData();
            gd.heightHint = 0;
            catchActionLabel.setLayoutData(gd);

            gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 0;
            rescheduleTimersGroup.setLayoutData(gd);

            gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = 0;
            cancelNonCancelContainer.setLayoutData(gd);

            cancelNonCancelContainer.setVisible(false);
            catchActionLabel.setVisible(false);

            root.layout(true);

        }
    }

    @Override
    protected Command doGetCommand(Object obj) {
        Activity activity = getActivity();

        if (activity != null) {
            TriggerResultSignal triggerSignal =
                    EventObjectUtil.getTriggerSignal(activity);

            if (triggerSignal != null) {
                if (obj == cancelOnCatchSignal) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.EventTriggerTypeSignalSection_SetCancelTaskOnSignal_menu);

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(getEditingDomain(),
                                    triggerSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_NonCancelling(),
                                    Boolean.FALSE));

                    /* Also unset the reschedule timer info (if there is any) */
                    Command unsetRescheduleCmd =
                            getSetRescheduleTimerTypeCommand(activity, null);

                    if (unsetRescheduleCmd != null) {
                        cmd.append(unsetRescheduleCmd);
                    }

                    return cmd;

                } else if (obj == continueOnCatchSignal) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.EventTriggerTypeSignalSection_SetContinueTaskOnSignal_menu);
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(getEditingDomain(),
                                    triggerSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_NonCancelling(),
                                    Boolean.TRUE));
                    return cmd;

                } else if (obj == rescheduleTimersNoneButton
                        || obj == rescheduleTimersAllButton
                        || obj == rescheduleTimersDeadlineButton
                        || obj == rescheduleTimersSelectedButton) {
                    /*
                     * Handle setting reschedule timer type.
                     */
                    return getSetRescheduleTimerTypeCommand(activity,
                            (RescheduleTimerSelectionType) ((Button) obj)
                                    .getData());
                } else {

                    CompoundCommand ccmd = new CompoundCommand();
                    /*
                     * Scan through the wrapper.
                     */
                    for (SignalControlWrapper signalTypeControls : signalTypeWrappers) {
                        /*
                         * get the LHS signalType button and the contributed
                         * section.
                         */
                        Button lhsButton = signalTypeControls.getLhsButton();

                        AbstractSignalTypeSection abstractSignalTypeSection =
                                signalTypeControls.getSignalTypeSection();

                        if (abstractSignalTypeSection != null) {

                            if (obj == lhsButton) {
                                /*
                                 * If the obj selected is the LHS button then
                                 * fire the necessary command in the contributed
                                 * class.
                                 */
                                Command signalTypeSelectedCommad =
                                        abstractSignalTypeSection
                                                .getSetSelectedSignalTypeCommand(obj);

                                if (signalTypeSelectedCommad != null) {

                                    ccmd.append(signalTypeSelectedCommad);
                                }
                                /*
                                 * Pass the control to getCommand for the
                                 * contributed class to perform additional
                                 * stuff.
                                 */
                                Command command =
                                        abstractSignalTypeSection
                                                .getCommand(obj);

                                if (command != null) {
                                    ccmd.append(command);
                                }
                            }
                        }
                    }

                    if (!ccmd.isEmpty()) {
                        return ccmd;
                    }
                }
            }
        }
        return null;
    }

    /**
     * For reschedule timer mode 'Selected' set the set of timer events that
     * should be rescheduled.
     * 
     * @param selectedTimerEvents
     */
    protected void setSelectedRescheduleTimerEvents(
            Collection<Activity> selectedTimerEvents) {

        /*
         * In order for selected timer events controls to be enabled the
         * reschedule time rtype must already be set so the
         * xpdExt:SignalData/xpdExt:RescheduleTimers element must already exist.
         * 
         * So no need to mess checking seeing if it needs to be created etc.
         */
        SignalData signalData =
                EventObjectUtil.getSignalDataExtensionElement(getActivity());

        if (signalData != null) {
            RescheduleTimers rescheduleTimers =
                    signalData.getRescheduleTimers();

            if (rescheduleTimers != null) {
                LateExecuteCompoundCommand cmd =
                        new LateExecuteCompoundCommand();
                cmd.setLabel(Messages.EventTriggerTypeSignalSection_SetRescheduledTimerEvents_menu);

                /*
                 * Remove existing timer event references
                 */
                EList<ActivityRef> currentTimerEvents =
                        rescheduleTimers.getTimerEvents();
                if (currentTimerEvents.size() > 0) {
                    cmd.append(RemoveCommand.create(getEditingDomain(),
                            currentTimerEvents));
                }

                /*
                 * Add the new references.
                 */
                if (selectedTimerEvents.size() > 0) {
                    for (Activity timerEvent : selectedTimerEvents) {
                        ActivityRef activityRef =
                                XpdExtensionFactory.eINSTANCE
                                        .createActivityRef();
                        activityRef.setIdRef(timerEvent.getId());

                        cmd.append(AddCommand.create(getEditingDomain(),
                                rescheduleTimers,
                                XpdExtensionPackage.eINSTANCE
                                        .getRescheduleTimers_TimerEvents(),
                                activityRef));
                    }
                }

                /*
                 * Finally, execute it.
                 */
                if (!cmd.isEmpty()) {
                    getEditingDomain().getCommandStack().execute(cmd);
                }
            }
        }
    }

    /**
     * Set the reschedule timers model from radio button selection.
     * 
     * @param activity
     * @param rescheduleTimerSelectionType
     *            Reschedule timer type or <code>null</code> for "None"
     */
    private Command getSetRescheduleTimerTypeCommand(Activity activity,
            RescheduleTimerSelectionType rescheduleTimerType) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.EventTriggerTypeSignalSection_SetRescheduleTimersType_menu);

        SignalData signalData =
                EventObjectUtil.getSignalDataExtensionElement(activity);

        RescheduleTimers rescheduleTimers = null;

        if (signalData != null) {
            rescheduleTimers = signalData.getRescheduleTimers();
        }

        /*
         * Set to none = Set to not reschedule any timers (remove
         * xpdExt:SignalData/xpdExt:RescheduleTimers element altogether.
         */
        if (rescheduleTimerType == null) {
            if (rescheduleTimers != null) {
                cmd.append(SetCommand.create(getEditingDomain(),
                        signalData,
                        XpdExtensionPackage.eINSTANCE
                                .getSignalData_RescheduleTimers(),
                        SetCommand.UNSET_VALUE));
            }

        } else {
            if (rescheduleTimers == null
                    || !rescheduleTimerType.equals(rescheduleTimers
                            .getTimerSelectionType())) {
                /* Create containers if necessary. */
                if (signalData == null) {
                    signalData =
                            CatchSignalMappingUtil
                                    .getOrCreateSignalDataElement(getEditingDomain(),
                                            activity,
                                            cmd);

                }

                if (rescheduleTimers == null) {
                    rescheduleTimers =
                            XpdExtensionFactory.eINSTANCE
                                    .createRescheduleTimers();

                    cmd.append(SetCommand.create(getEditingDomain(),
                            signalData,
                            XpdExtensionPackage.eINSTANCE
                                    .getSignalData_RescheduleTimers(),
                            rescheduleTimers));
                }

                /* Set the reschedule type. */
                cmd.append(SetCommand.create(getEditingDomain(),
                        rescheduleTimers,
                        XpdExtensionPackage.eINSTANCE
                                .getRescheduleTimers_TimerSelectionType(),
                        rescheduleTimerType));

                /*
                 * For everything other than selected-timers type, remove the
                 * selected timers.
                 */
                if (!RescheduleTimerSelectionType.SELECTED
                        .equals(rescheduleTimerType)) {
                    EList<ActivityRef> timerEvents =
                            rescheduleTimers.getTimerEvents();

                    if (timerEvents.size() > 0) {
                        cmd.append(RemoveCommand.create(getEditingDomain(),
                                timerEvents));
                    }
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    @Override
    protected void doRefresh() {
        wasEventHandlerAtLastRefesh = Boolean.FALSE;

        Activity activity = getActivity();

        /*
         * Cancelling / Non cancelling event controls.
         */
        TriggerResultSignal triggerSignal =
                EventObjectUtil.getTriggerSignal(activity);

        if (triggerSignal != null) {
            /*
             * Only show cancel/continue buttons for catch attached to task
             * boundary.
             */
            boolean isCatchSignal =
                    CatchThrow.CATCH.equals(triggerSignal.getCatchThrow());

            signalNameGroup
                    .setText(Messages.EventTriggerTypeSignalSection_SignalEventSignalNameGroup_label);

            if (isCatchSignal && EventObjectUtil.isAttachedToTask(activity)) {

                boolean isNonCancelling =
                        EventObjectUtil.isNonCancellingEvent(activity);

                setRescheduleTimerControls(activity);

                if (isNonCancelling) {
                    cancelOnCatchSignal.setSelection(false);
                    continueOnCatchSignal.setSelection(true);

                    setEnabled(rescheduleTimersGroup, true);

                } else {
                    continueOnCatchSignal.setSelection(false);
                    cancelOnCatchSignal.setSelection(true);

                    setEnabled(rescheduleTimersGroup, false);
                }

                showCancelNonCancelButtons(true);

            } else {
                continueOnCatchSignal.setSelection(false);
                cancelOnCatchSignal.setSelection(false);

                showCancelNonCancelButtons(false);
            }

            /*
             * if its catch signal event-handler (i.e., catch signal without
             * incoming flow), then show the event-handler configuration control
             */
            if (isCatchSignal
                    && Xpdl2ModelUtil.isEventHandlerActivity(activity)) {
                wasEventHandlerAtLastRefesh = Boolean.TRUE;

                if (!eventHandlerGroup.getVisible()) {
                    GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
                    gd1.verticalIndent = 2;
                    gd1.horizontalSpan = 2;
                    eventHandlerGroup.setLayoutData(gd1);
                    eventHandlerGroup.setVisible(true);
                    eventHandlerGroup.getParent().layout();
                }

            } else {
                wasEventHandlerAtLastRefesh = Boolean.FALSE;

                if (eventHandlerGroup.getVisible()) {
                    GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
                    gd1.heightHint = 0;
                    gd1.exclude = true;
                    eventHandlerGroup.setLayoutData(gd1);
                    eventHandlerGroup.setVisible(false);
                    eventHandlerGroup.getParent().layout();
                }
            }

        } else {
            showCancelNonCancelButtons(false);
        }

        for (SignalControlWrapper signalTypeControls : signalTypeWrappers) {

            AbstractSignalTypeSection abstractSignalTypeSection =
                    signalTypeControls.getSignalTypeSection();

            if (abstractSignalTypeSection != null) {
                /*
                 * Get if the Signal is selected.
                 */
                Button lhsButton = signalTypeControls.getLhsButton();

                boolean selectedSignalType =
                        abstractSignalTypeSection.isSelectedSignalType();

                if (selectedSignalType) {
                    if (lhsButton != previousSelectedSignalTypeButton) {

                        previousSelectedSignalTypeButton = lhsButton;

                        if (!firstRefreshSinceSetInput) {
                            /*
                             * refresh tabs if the signal type is changed and if
                             * its not the first refresh since setInput()
                             */
                            refreshTabs();
                        }
                        /*
                         * show the appropriate event handler page.
                         */
                        eventHandlerPagebook.showPage(signalTypeControls
                                .getEventHandlerPagebookPage());

                        eventHandlerGroup.layout(true);
                    }
                }
                /*
                 * Based on the Signal Selection, select/deselect the lhs button
                 * and disable the RHS composite.
                 */
                lhsButton.setSelection(selectedSignalType);

                Composite rhsComposite = signalTypeControls.getRhsComposite();
                setEnabled(rhsComposite, selectedSignalType);

                /*
                 * using the contributed classes select() method to decide
                 * wheather to show a particular signal type control or not.
                 */
                boolean shouldShowControl =
                        abstractSignalTypeSection.select(getActivity());

                if (shouldShowControl && !lhsButton.getVisible()) {

                    GridData gdToHideControls = new GridData();
                    lhsButton.setLayoutData(gdToHideControls);
                    lhsButton.setVisible(true);

                    rhsComposite.setLayoutData(new GridData(
                            GridData.FILL_HORIZONTAL));
                    rhsComposite.setVisible(true);

                    root.layout(true);

                } else if (!shouldShowControl && lhsButton.getVisible()) {

                    GridData gdToHideControls = new GridData();
                    gdToHideControls.widthHint = 0;
                    gdToHideControls.heightHint = 0;

                    lhsButton.setLayoutData(gdToHideControls);
                    lhsButton.setVisible(false);

                    rhsComposite.setLayoutData(gdToHideControls);
                    rhsComposite.setVisible(false);

                    root.layout(true);

                }
            }
            /*
             * Fianlly call the refresh of the currently active signal section
             * so that it can refresh its own controls.
             */
            abstractSignalTypeSection.refresh();
        }
        firstRefreshSinceSetInput = false;
        return;
    }

    /**
     * Set reschedule timer controls from model.
     * 
     * @param activity
     */
    private void setRescheduleTimerControls(Activity activity) {
        RescheduleTimerSelectionType rescheduleType = null;

        Collection<ActivityRef> timerEvents = Collections.emptyList();

        SignalData signalData =
                EventObjectUtil.getSignalDataExtensionElement(activity);

        if (signalData != null) {
            RescheduleTimers rescheduleTimers =
                    signalData.getRescheduleTimers();

            if (rescheduleTimers != null) {
                rescheduleType = rescheduleTimers.getTimerSelectionType();

                timerEvents = rescheduleTimers.getTimerEvents();
            }
        }

        rescheduleTimersNoneButton.setSelection(rescheduleType == null);
        rescheduleTimersAllButton.setSelection(RescheduleTimerSelectionType.ALL
                .equals(rescheduleType));
        rescheduleTimersDeadlineButton
                .setSelection(RescheduleTimerSelectionType.DEADLINE
                        .equals(rescheduleType));
        rescheduleTimersSelectedButton
                .setSelection(RescheduleTimerSelectionType.SELECTED
                        .equals(rescheduleType));

        timerEventPickerControl.setActivityRefs(activity.getProcess(),
                timerEvents);

        timerEventPickerControl
                .setEnabled(RescheduleTimerSelectionType.SELECTED
                        .equals(rescheduleType));
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        if (super.shouldRefresh(notifications)) {
            return true;
        }

        /*
         * Need to refresh if current "isEventHandler" != old existing
         * "isEventHandler" (i.e. incoming flow added / removed.
         */
        if (getActivity() != null) {
            if (Xpdl2ModelUtil.isEventHandlerActivity(getActivity()) != wasEventHandlerAtLastRefesh) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        super.dispose();
        root.dispose();
        for (SignalTypeExtensionPoint eachSignalTypeExtension : signalTypeExtensions) {
            eachSignalTypeExtension.getSignalTypeSection().dispose();
        }
    }

    /**
     * Get the selected input object as an activity
     * 
     * @return activity for event or null on error.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            Activity act = (Activity) o;

            return act;
        }
        return null;
    }

    /**
     * Wrapper that wraps up the LHS signal type button , the RHS composite, the
     * event handler pagebook controls and the Section contributed by the
     * 'signalType' extension point.
     * 
     * 
     * @author kthombar
     * @since Jan 30, 2015
     */
    private class SignalControlWrapper {
        private Button lhsButton;

        private Composite rhsControl;

        private AbstractSignalTypeSection signalTypeSection;

        private Control eventHandlerPagebookPage;

        /**
         * 
         * @param lhsButton
         * @param rhsControl
         * @param signalTypeSection
         */
        public SignalControlWrapper(Button lhsButton, Composite rhsControl,
                AbstractSignalTypeSection signalTypeSection) {
            this.lhsButton = lhsButton;
            this.rhsControl = rhsControl;
            this.signalTypeSection = signalTypeSection;
        }

        /**
         * set the event handler pagebook control for the signal type.
         * 
         * @param eventHandlerPagebookPage
         */
        public void setEventHandlerPagebookPage(Control eventHandlerPagebookPage) {
            this.eventHandlerPagebookPage = eventHandlerPagebookPage;

        }

        /**
         * 
         * @return the event handler pagebook contro.
         */
        public Control getEventHandlerPagebookPage() {
            return eventHandlerPagebookPage;
        }

        /**
         * 
         * @return the LHS Signal Type button
         */
        public Button getLhsButton() {
            return lhsButton;
        }

        /**
         * 
         * @return the RHS composite
         */
        public Composite getRhsComposite() {
            return rhsControl;
        }

        /**
         * 
         * @return the {@link AbstractSignalTypeSection} contributed by the
         *         extension.
         */
        public AbstractSignalTypeSection getSignalTypeSection() {
            return signalTypeSection;
        }
    }
}
