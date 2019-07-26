/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDestinationUtil;
import com.tibco.xpd.processeditor.xpdl2.extensions.EventType;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl;
import com.tibco.xpd.processeditor.xpdl2.properties.ActivityPickerControl.IActivityPickerListener;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.EventTriggerTypeSignalSection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.AbstractSignalTypeSection;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.SignalTypeExtensionPoint;
import com.tibco.xpd.processeditor.xpdl2.properties.event.signal.extensions.SignalTypeExtensionPointManager;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.EventHandlerFlowStrategy;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.util.DecisionFlowUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * StartEventGeneralSection
 * 
 * @author aallway
 */

public class StartEventGeneralSection extends BaseEventGeneralSection {

    /**
     * Group to contain the event sub process start event specific controls.
     */
    private Group eventSubProcStartEventGroup;

    /**
     * Container for interruption type controls.
     */
    private Composite interruptionTypeContainer;

    /**
     * Radio button to interrupt process flow.
     */
    private Button interruptProcessFlowBut;

    /**
     * Radio button to continue process flow.
     */
    private Button continueProcessFlowBut;

    /**
     * Container for concurrent flow controls.
     */
    private Composite concurrentFlowsContainer;

    /**
     * Container fow initialiser controls.
     */
    private Composite initialisersContainer;

    /**
     * Radio button to serialize concurrent flows.
     */
    private Button serializeConcurrentFlows;

    /**
     * Radio button to allow concurrent flows.
     */
    private Button allowConcurrentFlows;

    /**
     * Initialisers label.
     */
    private Label initialisersLabel;

    /**
     * Picker control to pick initialiser activities.
     */
    private ActivityPickerControl initialiserPickerControl;

    private Composite additionalControlsRoot;

    /**
     * Stores all the signal type contributed extensions
     */
    private List<SignalTypeExtensionPoint> signalTypeExtensions = null;

    /**
     * StartEventGeneralSection constructor.
     */
    public StartEventGeneralSection() {
        super(EventType.START);
        /*
         * Get the SignalType extensions
         */
        SignalTypeExtensionPointManager extensionPointManager =
                new SignalTypeExtensionPointManager();
        signalTypeExtensions = extensionPointManager.getSignalTypeExtensions();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.properties.general.BaseEventGeneralSection
     * #getEventTriggerTypeSections()
     */
    @Override
    public List<EventTriggerTypeSection> getEventTriggerTypeSections() {
        List<EventTriggerTypeSection> trigTypeSections =
                new ArrayList<EventTriggerTypeSection>();

        EventTriggerTypeSection tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_NONE_LITERAL, null,
                        Messages.TriggerResultType_StartNone);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                        new EventTriggerTypeMessageSection(),
                        Messages.TriggerResultType_Message_menu);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_TIMER_LITERAL,
                        new EventTriggerTypeTimerSection(),
                        Messages.TriggerResultType_Timer);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_CONDITIONAL_LITERAL,
                        new EventTriggerTypeConditionalSection(),
                        Messages.TriggerResultType_Conditional);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL,
                        new EventTriggerTypeSignalSection(),
                        Messages.StartEventGeneralSection_TriggerResultType_Signal);
        trigTypeSections.add(tt);

        tt =
                new EventTriggerTypeSection(
                        EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL, null,
                        Messages.TriggerResultType_Multiple_menu);
        trigTypeSections.add(tt);

        return trigTypeSections;
    }

    /*
     * XPD-7263: Removed the shouldShowSolutionDesignForm() method from here as
     * we were doing specific checks which were un-necessary, rather let the
     * super class decide if we want to show rhe solution design form based on
     * the entry in the 'eventImplementation' extension point
     */

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.processwidget.properties.general.BaseEventGeneralSection
     * #getTriggerTypeLabelText()
     */
    @Override
    protected String getTriggerTypeLabelText() {
        return Messages.StartEventGeneralSection_TriggerType;
    }

    @Override
    public boolean select(Object toTest) {
        if (super.select(toTest)) {
            Activity eventAct = (Activity) getBaseSelectObject(toTest);
            Process process = Xpdl2ModelUtil.getProcess(eventAct);
            if (!DecisionFlowUtil.isDecisionFlow(process)
                    && eventAct.getEvent() instanceof StartEvent) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.BaseEventGeneralSection#createAdditionalTypeControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Composite createAdditionalTypeControls(Composite parent,
            XpdFormToolkit toolkit) {

        additionalControlsRoot = toolkit.createComposite(parent);

        GridLayout layout = new GridLayout(1, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        additionalControlsRoot.setLayout(layout);

        /*
         * 1. Group to contain event subprocess start event interruption
         * controls.
         */
        eventSubProcStartEventGroup =
                new Group(additionalControlsRoot, SWT.SHADOW_ETCHED_IN);
        eventSubProcStartEventGroup
                .setText(Messages.StartEventSection_EventSubProcessGroupText);
        eventSubProcStartEventGroup.setBackground(additionalControlsRoot
                .getBackground());
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);

        eventSubProcStartEventGroup.setLayoutData(gridData);
        GridLayout gLayout = new GridLayout(1, false);
        gLayout.horizontalSpacing = 0;
        gLayout.verticalSpacing = 0;
        eventSubProcStartEventGroup.setLayout(gLayout);

        /*
         * 2. Create controls to handle event sub process start event
         * interruption behaviour.
         */
        createStartEventInterruptionControls(toolkit);

        /*
         * 3. Create controls to handle event sub process start event
         * concurrence behaviour.
         */
        createConcurrentFlowsControls(toolkit);

        /*
         * 4. Create controls to handle event sub process start event
         * initialisation behaviour.
         */
        createInititialisersControls(toolkit);

        return additionalControlsRoot;

    }

    /**
     * Create controls to handle event sub process start event interruption
     * behaviour.
     * 
     * @param parent
     * @param toolkit
     */
    private void createStartEventInterruptionControls(XpdFormToolkit toolkit) {

        /*
         * 1. Container to enclose "Interrupt Process Flow" and
         * "Continue Process Flow" radio buttons.
         */
        interruptionTypeContainer =
                toolkit.createComposite(eventSubProcStartEventGroup, SWT.NONE);

        GridData gdContainer = new GridData(GridData.FILL_HORIZONTAL);
        gdContainer.horizontalSpan = 2;
        interruptionTypeContainer.setLayoutData(gdContainer);

        interruptionTypeContainer.setLayout(new GridLayout(2, false));

        /*
         * 1.1. "Interrupt Process Flow" radio button.
         */
        interruptProcessFlowBut =
                toolkit.createButton(interruptionTypeContainer,
                        Messages.StartEventSection_EventSubProcessInterruptProcFlow_Text,
                        SWT.RADIO);
        interruptProcessFlowBut
                .setToolTipText(Messages.StartEventSection_EventSubProcessInterruptProcFlow_TooltipText);
        interruptProcessFlowBut.setLayoutData(new GridData(GridData.BEGINNING));
        manageControl(interruptProcessFlowBut);

        /*
         * 1.2. "Continue Process Flow" radio button.
         */
        continueProcessFlowBut =
                toolkit.createButton(interruptionTypeContainer,
                        Messages.StartEventSection_EventSubProcessContinueProcFlow_Text,
                        SWT.RADIO);
        continueProcessFlowBut
                .setToolTipText(Messages.StartEventSection_EventSubProcessContinueProcFlow_TooltipText);
        GridData gd = new GridData(GridData.BEGINNING);
        gd.horizontalIndent = 27;
        continueProcessFlowBut.setLayoutData(gd);
        manageControl(continueProcessFlowBut);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.BaseEventGeneralSection#doGetDetailsCommand(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    protected Command doGetDetailsCommand(Object obj) {

        CompoundCommand cmd = new CompoundCommand();

        cmd.append(super.doGetDetailsCommand(obj));

        EditingDomain ed = getEditingDomain();
        Activity startEventAct = getActivity();

        if (startEventAct != null
                && ed != null
                && EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                        .getFlowType(startEventAct))) {

            if (EventObjectUtil.isEventSubProcessStartEvent(startEventAct)) {

                /*
                 * Get command to set xpdExt:NonInterruptingEvent attribute.
                 */
                if (obj == interruptProcessFlowBut) {

                    Event ev = startEventAct.getEvent();

                    if (ev instanceof StartEvent) {

                        StartEvent startEv = (StartEvent) ev;
                        cmd.setLabel(Messages.StartEventSection_EventSubProcess_NonInterrupt_Command_Label);
                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        startEv,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_NonInterruptingEvent(),
                                        new Boolean(false)));

                    }

                } else if (obj == continueProcessFlowBut) {

                    Event ev = startEventAct.getEvent();

                    if (ev instanceof StartEvent) {

                        StartEvent startEv = (StartEvent) ev;

                        cmd.setLabel(Messages.StartEventSection_EventSub_Process_Interrupt_Command_Label);
                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        startEv,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_NonInterruptingEvent(),
                                        new Boolean(true)));

                    }

                }

                Object triggerNode =
                        startEventAct.getEvent().getEventTriggerTypeNode();

                boolean isEventSubProcessStartRequestEvent =
                        EventObjectUtil.isEventSubProcessStartRequestEvent(startEventAct);

                if (triggerNode instanceof TriggerResultMessage
                        || triggerNode instanceof TriggerResultSignal || isEventSubProcessStartRequestEvent) {

                    /*
                     * Sid ACE-2019 if this is a start request activity then
                     * then the concurrency flag goes on the <xpdl2:StartEvent>
                     * element. Otherwise for message/signal it goes on the
                     * specific trigger type node.
                     */
                    Object attrParent;

                    if (isEventSubProcessStartRequestEvent) {
                        attrParent = startEventAct.getEvent();
                    } else {
                        attrParent = triggerNode;
                    }

                    /*
                     * Get command to set xpdExt:EventHandlerFlowStrategy
                     * attribute.
                     */
                    if (obj == serializeConcurrentFlows) {

                        cmd.setLabel(Messages.EventTriggerTypeMessageSection_SetEventHandlerFlowStrategy_menu);

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        (OtherAttributesContainer) attrParent,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_EventHandlerFlowStrategy(),
                                        EventHandlerFlowStrategy.SERIALIZE_CONCURRENT));

                    } else if (obj == allowConcurrentFlows) {

                        cmd.setLabel(Messages.EventTriggerTypeMessageSection_SetEventHandlerFlowStrategy_menu);

                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(ed,
                                        (OtherAttributesContainer) attrParent,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_EventHandlerFlowStrategy(),
                                        EventHandlerFlowStrategy.ALLOW_CONCURRENT));
                    }
                }
            }
        }

        return cmd;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.event.BaseEventGeneralSection#doRefreshDetailsSection()
     * 
     */
    @Override
    protected void doRefreshDetailsSection() {

        super.doRefreshDetailsSection();

        Activity activity = getActivity();

        if (activity != null) {

            /*
             * Hide eventSubProcStartEventGroup if it's not an event subprocess
             * start event.
             */
            if (!EventObjectUtil.isEventSubProcessStartEvent(activity)) {

                hideAdditionalControlsSection(true);

            } else {

                /*
                 * XPD-7075 : Event sub process event handler controls are now
                 * applicable for Global Signal events as well, hence passing
                 * the call to the signal type section to decide if they need
                 * the event handler controls.
                 */
                boolean isEventSubProcessEventHandlerControlsApplicabale =
                        false;
                boolean isFlowControlsApplicable = false;
                boolean shouldDisableFlowControls = false;
                boolean isInitialiserActivityControlsApplicable = false;

                for (SignalTypeExtensionPoint eachSignalExtPoint : signalTypeExtensions) {
                    /*
                     * get the abstract signal type section.
                     */
                    AbstractSignalTypeSection signalTypeSection =
                            eachSignalExtPoint.getSignalTypeSection();

                    isEventSubProcessEventHandlerControlsApplicabale =
                            signalTypeSection
                                    .isEventSubProcessEventHandlerControlsApplicabale(activity);

                    if (isEventSubProcessEventHandlerControlsApplicabale) {
                        /*
                         * If event handler control is applicable, check if both
                         * flow container and initializer container should be
                         * created.
                         */
                        isFlowControlsApplicable =
                                signalTypeSection.isFlowControlsApplicable();
                        isInitialiserActivityControlsApplicable =
                                signalTypeSection
                                        .isInitialiserActivityControlsApplicable();
                        shouldDisableFlowControls =
                                signalTypeSection.shouldDisableFlowControls();

                        break;
                    }
                }

                /*
                 * Set back to visible if it's an event subprocess start event.
                 */
                hideAdditionalControlsSection(false);

                /*
                 * Show/hide concurrentFlowsContainer and initialisersContainer
                 * according to the event subprocess start event being an
                 * message event or not AND whether the parent process has BPM
                 * destination enabled or not.
                 */

                /*
                 * Sid ACE-2019 show serialise/allow concurrent flows for Start
                 * Request Events also
                 */
                boolean isEventSubProcessStartRequestEvent =
                        EventObjectUtil.isEventSubProcessStartRequestEvent(activity);

                boolean isEventSubProcessMessageStartEvent =
                        EventObjectUtil
                                .isEventSubProcessMessageStartEvent(activity);
                if ((isEventSubProcessMessageStartEvent || isEventSubProcessEventHandlerControlsApplicabale
                        || isEventSubProcessStartRequestEvent)
                        && ProcessDestinationUtil
                                .isBPMDestinationSelected(activity.getProcess())) {

                    if (isEventSubProcessMessageStartEvent || (isEventSubProcessStartRequestEvent
                            && !Xpdl2ModelUtil.isPageflowOrSubType(activity.getProcess()))) {

                        hideUIComposite(concurrentFlowsContainer,
                                false,
                                GridData.FILL_HORIZONTAL);

                        /*
                         * XPD-7049: Saket: Disable flow controls if needed.
                         */
                        if (shouldDisableFlowControls) {
                            allowConcurrentFlows.setEnabled(false);
                            serializeConcurrentFlows.setEnabled(false);
                        } else {
                            allowConcurrentFlows.setEnabled(true);
                            serializeConcurrentFlows.setEnabled(true);
                        }

                        if (isEventSubProcessMessageStartEvent) {
                            hideUIComposite(initialisersContainer,
                                false,
                                GridData.FILL_HORIZONTAL);
                        } else {
                            hideUIComposite(initialisersContainer, true, GridData.FILL_HORIZONTAL);
                        }

                    } else {
                        if (isFlowControlsApplicable) {

                            /*
                             * show flow container if applicable
                             */
                            hideUIComposite(concurrentFlowsContainer,
                                    false,
                                    GridData.FILL_HORIZONTAL);

                            /*
                             * XPD-7049: Saket: Disable flow controls if needed.
                             */
                            if (shouldDisableFlowControls) {
                                allowConcurrentFlows.setEnabled(false);
                                serializeConcurrentFlows.setEnabled(false);
                            } else {
                                allowConcurrentFlows.setEnabled(true);
                                serializeConcurrentFlows.setEnabled(true);
                            }

                        } else {
                            /*
                             * hide flow container not applicable
                             */
                            hideUIComposite(concurrentFlowsContainer,
                                    true,
                                    GridData.FILL_HORIZONTAL);
                        }
                        if (isInitialiserActivityControlsApplicable) {
                            /*
                             * show initialiser activity container if
                             * applicable.
                             */
                            hideUIComposite(initialisersContainer,
                                    false,
                                    GridData.FILL_HORIZONTAL);
                        } else {
                            /*
                             * hide initialiser activity container if not
                             * applicable.
                             */
                            hideUIComposite(initialisersContainer,
                                    true,
                                    GridData.FILL_HORIZONTAL);
                        }
                    }
                } else {
                    hideUIComposite(concurrentFlowsContainer,
                            true,
                            GridData.FILL_HORIZONTAL);
                    hideUIComposite(initialisersContainer,
                            true,
                            GridData.FILL_HORIZONTAL);
                }

                /*
                 * Handle interruption radio controls.
                 */
                boolean nonInterruptingEvent = Xpdl2ModelUtil.getOtherAttributeAsBoolean(activity
                        .getEvent(), XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_NonInterruptingEvent());
                continueProcessFlowBut.setSelection(nonInterruptingEvent);
                interruptProcessFlowBut.setSelection(!nonInterruptingEvent);

                /*
                 * Handle serialize/concurrent controls and initialiser
                 * controls.
                 */

                if (activity.getEvent() instanceof StartEvent) {

                    EObject eventTriggerTypeNode =
                            activity.getEvent().getEventTriggerTypeNode();
                    /*
                     * XPD-7075: Event Sub process event handler controls are
                     * applicable for message start event and Global start
                     * signals.
                     */
                    if (eventTriggerTypeNode instanceof TriggerResultMessage
                            || eventTriggerTypeNode instanceof TriggerResultSignal
                            || isEventSubProcessStartRequestEvent) {

                        /*
                         * Sid ACE-2019 if this is a start request activity then
                         * then the concurrency flag goes on the
                         * <xpdl2:StartEvent> element. Otherwise for
                         * message/signal it goes on the specific trigger type
                         * node.
                         */
                        Object attrParent;

                        if (isEventSubProcessStartRequestEvent) {
                            attrParent = activity.getEvent();
                        } else {
                            attrParent = eventTriggerTypeNode;
                        }

                        EventHandlerFlowStrategy flowStrategy =
                                (EventHandlerFlowStrategy) Xpdl2ModelUtil
                                        .getOtherAttribute((OtherAttributesContainer) attrParent,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_EventHandlerFlowStrategy());

                        if (EventHandlerFlowStrategy.ALLOW_CONCURRENT
                                .equals(flowStrategy)) {
                            serializeConcurrentFlows.setSelection(false);
                            allowConcurrentFlows.setSelection(true);

                        } else if (EventHandlerFlowStrategy.SERIALIZE_CONCURRENT
                                .equals(flowStrategy)) {
                            serializeConcurrentFlows.setSelection(true);
                            allowConcurrentFlows.setSelection(false);

                        } else {
                            serializeConcurrentFlows.setSelection(false);
                            allowConcurrentFlows.setSelection(false);
                        }

                        /*
                         * Initializers don't apply to Start Request events as
                         * they're related to business data correlation - start
                         * request events are correlated on process-id.
                         */
                        if (!isEventSubProcessStartRequestEvent) {
                            // Get current initialiser activities
                            List<Activity> currentInitialisingActivities = Collections.<Activity> emptyList();

                            EventHandlerInitialisers evtHdlInitialisers = (EventHandlerInitialisers) Xpdl2ModelUtil
                                    .getOtherElement((OtherElementsContainer) eventTriggerTypeNode,
                                            XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers());

                            if (evtHdlInitialisers != null) {
                                currentInitialisingActivities = getEvtHandlerInitialisingActivities(evtHdlInitialisers);
                            }

                            initialiserPickerControl.setActivities(activity.getProcess(),
                                    currentInitialisingActivities);
                        }
                    }
                }
            }
        }

    }

    /**
     * Concurrency handling configuration for event handlers.
     * 
     * @param toolkit
     * @param root
     */
    private void createConcurrentFlowsControls(XpdFormToolkit toolkit) {

        concurrentFlowsContainer =
                toolkit.createComposite(eventSubProcStartEventGroup, SWT.NONE);
        GridData gdLabel2 = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
        gdLabel2.verticalIndent = 7;
        GridData gdContainer2 = new GridData(GridData.FILL_HORIZONTAL);
        gdContainer2.horizontalSpan = 2;

        concurrentFlowsContainer.setLayoutData(gdContainer2);
        concurrentFlowsContainer.setLayout(new GridLayout(1, false));

        serializeConcurrentFlows =
                toolkit.createButton(concurrentFlowsContainer,
                        Messages.EventTriggerTypeMessageSection_SerializeConcurrent_button,
                        SWT.RADIO);
        serializeConcurrentFlows
                .setToolTipText(Messages.EventTriggerTypeMessageSection_SerializeConcurrent_tooltip);
        serializeConcurrentFlows.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));
        manageControl(serializeConcurrentFlows);

        allowConcurrentFlows =
                toolkit.createButton(concurrentFlowsContainer,
                        Messages.EventTriggerTypeMessageSection_AllowConcurrent_label,
                        SWT.RADIO);
        allowConcurrentFlows
                .setToolTipText(Messages.EventTriggerTypeMessageSection_AllowConcurrent_tooltip);
        allowConcurrentFlows.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        manageControl(allowConcurrentFlows);
    }

    /**
     * Add configuration to event handler activities to specify a list of
     * activities after which the event handler will be initialised.
     * 
     * textbox for delimited list of initialisers + dialog button for
     * initialisers selection
     * 
     * @param toolkit
     * @param parent
     */
    private void createInititialisersControls(XpdFormToolkit toolkit) {

        initialisersContainer =
                toolkit.createComposite(eventSubProcStartEventGroup, SWT.NONE);
        GridData gdContainer = new GridData(GridData.FILL_HORIZONTAL);
        initialisersContainer.setLayoutData(gdContainer);

        GridLayout gl = new GridLayout(2, false);
        gl.marginHeight = gl.marginWidth = 7;
        initialisersContainer.setLayout(gl);

        initialisersLabel =
                toolkit.createLabel(initialisersContainer,
                        Messages.EventTriggerTypeMessageSection_Initializers_label1,
                        SWT.NONE);
        initialisersLabel
                .setToolTipText(Messages.EventTriggerTypeMessageSection_EventHandlerInitializersLabel_tooltip);
        GridData gdLabel = new GridData(SWT.NONE, SWT.BEGINNING, true, false);
        gdLabel.horizontalSpan = 2;
        initialisersLabel.setLayoutData(gdLabel);

        initialiserPickerControl =
                new ActivityPickerControl(
                        initialisersContainer,
                        toolkit,
                        Messages.ActivityPickerInstantiator_EventHandlerInitialiser_ActivityPicker_title2,
                        SWT.MULTI) {

                    @Override
                    protected IFilter createActivityFilter() {

                        return new IFilter() {
                            @Override
                            public boolean select(Object toTest) {
                                boolean ret = false;

                                if (toTest instanceof Activity) {
                                    Activity activity = (Activity) toTest;

                                    /*
                                     * XPD-6757: Saket: Don't show the event
                                     * subprocess containing the event and event
                                     * itself in the initializer picker.
                                     */
                                    if (activity != getActivity()
                                            && activity != getContainingEventSubProcessActivity(getActivity())) {

                                        if (activity.getEvent() != null) {
                                            /*
                                             * If it's an event then it mustn't
                                             * be an event handler or an event
                                             * attached to task.
                                             */
                                            if (!Xpdl2ModelUtil
                                                    .isEventHandlerActivity(activity)
                                                    && !Xpdl2ModelUtil
                                                            .isEventAttachedToTask(activity)) {
                                                ret = true;
                                            }

                                        } else {
                                            // is a task

                                            if (TaskObjectUtil
                                                    .getTaskTypeStrict(activity) != null) {
                                                ret = true;
                                            }
                                        }
                                    }
                                }
                                return ret;
                            }
                        };
                    }
                };

        initialiserPickerControl.setLayoutData(new GridData(
                GridData.FILL_HORIZONTAL));

        initialiserPickerControl
                .addActivitiesPickedListener(new IActivityPickerListener() {

                    @Override
                    public void activitiesPicked(
                            Collection<Activity> selectedActivities) {
                        EditingDomain ed = getEditingDomain();
                        Activity eventAct = getActivity();

                        if (eventAct != null && eventAct.getEvent() != null

                        && ed != null) {

                            EObject eventTriggerTypeNode =
                                    eventAct.getEvent()
                                            .getEventTriggerTypeNode();
                            /*
                             * XPD-7075: Event Sub process event handler
                             * controls are applicable for message start event
                             * and Global start signals.
                             */
                            if (eventTriggerTypeNode instanceof TriggerResultMessage
                                    || eventTriggerTypeNode instanceof TriggerResultSignal) {

                                Command cmd =
                                        selectCorrelationInitialisersCommand(ed,
                                                eventTriggerTypeNode,
                                                eventAct,
                                                selectedActivities);

                                if (cmd != null) {
                                    ed.getCommandStack().execute(cmd);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Get event sub-process activity containing the specified activity.
     * 
     * @param act
     * @return Event sub-process activity containing the specified activity.
     */
    private Activity getContainingEventSubProcessActivity(Activity act) {

        /*
         * Get the process.
         */
        Process proc = act.getProcess();

        /*
         * Get all activity sets.
         */
        List<ActivitySet> allActivitySets = proc.getActivitySets();

        /*
         * Traverse through each activity set to see if the start event activity
         * is in there.
         */
        for (ActivitySet eachActivitySet : allActivitySets) {

            /*
             * Check if the start event activity is in this activity set.
             */
            if (eachActivitySet.getActivity(act.getId()) != null) {

                /*
                 * Check if this particular activity set is an event subprocess.
                 */

                Activity embSubProcActivityForActSet =
                        Xpdl2ModelUtil.getEmbSubProcActivityForActSet(proc,
                                eachActivitySet.getId());

                return embSubProcActivityForActSet;
            }

        }

        return null;
    }

    /**
     * Select initialiser activities via a picker dialog and update the
     * <b>EventHandlerInitialisers</b> element to reflect the selection
     * 
     * @param ed
     *            Editing domain
     * @param triggerTypeNode
     * @param activity
     * @return COmmand to reset initialisers or null if nothing to do.
     */
    private CompoundCommand selectCorrelationInitialisersCommand(
            EditingDomain ed, EObject triggerTypeNode, Activity activity,
            Collection<Activity> selectedInitialisingActivities) {

        if (activity.getProcess() != null) {

            // Get any current activities
            List<Activity> currentInitialisingActivities =
                    Collections.<Activity> emptyList();

            EventHandlerInitialisers evtHdlInitialisers =
                    (EventHandlerInitialisers) Xpdl2ModelUtil
                            .getOtherElement((OtherElementsContainer) triggerTypeNode,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_EventHandlerInitialisers());

            if (evtHdlInitialisers != null) {
                currentInitialisingActivities =
                        getEvtHandlerInitialisingActivities(evtHdlInitialisers);
            }

            // Any model amendments

            if (!isEqualsActivityLists(selectedInitialisingActivities,
                    currentInitialisingActivities)) {

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.EventTriggerTypeMessageSection_EventHandlerInitializersSetCommand_name);

                removeEventHandlerInitialisers(ed,
                        triggerTypeNode,
                        evtHdlInitialisers,
                        cmd);

                if (!selectedInitialisingActivities.isEmpty()) {
                    evtHdlInitialisers =
                            createEventHandlerInitialisers(ed,
                                    triggerTypeNode,
                                    cmd);

                    for (ActivityRef actRef : getListOfActivityRef(selectedInitialisingActivities)) {
                        evtHdlInitialisers.getActivityRef().add(actRef);
                    }
                }

                return cmd;
            }
        }

        return null;
    }

    /**
     * @param ed
     * @param evtHdlInitialisers
     * @param cCmd
     *            CompoundCommand
     */
    private void removeEventHandlerInitialisers(EditingDomain ed,
            EObject triggerTypeNode,
            EventHandlerInitialisers evtHdlInitialisers, CompoundCommand cCmd) {

        if (evtHdlInitialisers != null) {
            cCmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    (OtherElementsContainer) triggerTypeNode,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_EventHandlerInitialisers(),
                    evtHdlInitialisers));
        }
    }

    /**
     * Construct a list of <code>ActivityRef</code>s from a collection of
     * <code>Activity</code>s.
     * 
     * @param activities
     * @return List of <code>ActivityRef</code>s
     */
    private List<ActivityRef> getListOfActivityRef(
            Collection<Activity> activities) {

        // create list of EObjects for activities
        List<ActivityRef> activityRefs = new ArrayList<ActivityRef>();

        for (Activity activity : activities) {
            ActivityRef activityRef =
                    XpdExtensionFactory.eINSTANCE.createActivityRef();
            activityRef.setIdRef(activity.getId());
            activityRefs.add(activityRef);
        }

        return activityRefs;
    }

    /**
     * @param arrActivities1
     * @param arrActivities2
     * @return @return true if lists contain the exactly the same elements.
     *         Element order is ignored.
     */
    boolean isEqualsActivityLists(Collection<Activity> arrActivities1,
            Collection<Activity> arrActivities2) {

        boolean arrActivities1IsNull = arrActivities1 == null;
        boolean arrActivities2IsNull = arrActivities2 == null;

        if (arrActivities1IsNull ^ arrActivities2IsNull) {
            return false;
        } else if (arrActivities1IsNull && arrActivities2IsNull) {
            return true;
        }

        if (arrActivities1 != null && arrActivities2 != null
                && (arrActivities1.size() == arrActivities2.size())) {

            int elemNum = arrActivities1.size();

            Comparator<Activity> comp = new Comparator<Activity>() {

                @Override
                public int compare(Activity o1, Activity o2) {
                    if ((o1 != null) || (o2 != null)) {
                        return o1.getId().compareTo(o2.getId());
                    }
                    return 0;
                }

            };

            // put in sorted collections
            if (elemNum >= 1) {
                Queue<Activity> q1 = new PriorityQueue<Activity>(elemNum, comp);
                for (Activity act : arrActivities1)
                    q1.offer(act);
                Queue<Activity> q2 = new PriorityQueue<Activity>(elemNum, comp);
                for (Activity act : arrActivities2)
                    q2.offer(act);

                // compare element-by-element
                while (!q1.isEmpty() && !q2.isEmpty()) {
                    String q1_ID = q1.remove().getId();
                    String q2_ID = q2.remove().getId();
                    if (!q1_ID.equals(q2_ID)) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    /**
     * Create event handler initialisers.
     * 
     * @param ed
     *            Editing domain
     * @param triggerTypeNode
     *            TriggerResultMessage to attach EventHandlerInitialiser to
     */
    private EventHandlerInitialisers createEventHandlerInitialisers(
            EditingDomain ed, EObject triggerTypeNode, CompoundCommand cCmd) {

        // Add ext model <EventHandlerInitialisers> to ##OTHER
        EventHandlerInitialisers model =
                XpdExtensionFactory.eINSTANCE.createEventHandlerInitialisers();
        cCmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                (OtherElementsContainer) triggerTypeNode,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_EventHandlerInitialisers(),
                model));

        return model;
    }

    /**
     * Return the activities explicitly mentioned that an Event Handler may be
     * initialised by. Null if no Event Handler Initialising element present.
     * 
     * @param evtHdlInitialisers
     * @return the activities explicitly mentioned that an Event Handler may be
     *         initialised by. Null if no Event Handler Initialising element
     *         present.
     */
    private List<Activity> getEvtHandlerInitialisingActivities(
            EventHandlerInitialisers evtHdlInitialisers) {

        List<Activity> ret = Collections.<Activity> emptyList();

        if (evtHdlInitialisers != null) {

            List<ActivityRef> activityRefs =
                    evtHdlInitialisers.getActivityRef();
            if (activityRefs != null && !activityRefs.isEmpty()) {

                ret = new ArrayList<Activity>();
                for (ActivityRef activityRef : activityRefs) {
                    ret.add(activityRef.getActivity());
                }
            }

        }

        return ret;
    }

    /**
     * Control the presence/absence of an entire group in the UI
     * 
     * @param comp
     *            group to hide/show
     * @param hide
     */
    private void hideUIComposite(Composite comp, boolean hide, int gridDataStyle) {
        GridData data = new GridData(gridDataStyle);
        if (hide) {
            data.heightHint = 1;
        }

        comp.setVisible(!hide);
        comp.setLayoutData(data);

        /**
         * XPD-7138 - Noticed layotu issue start event event sub-process when
         * changing from message to timer event and back again.
         */
        getLHSRootComposite().layout(true);
    }
}
