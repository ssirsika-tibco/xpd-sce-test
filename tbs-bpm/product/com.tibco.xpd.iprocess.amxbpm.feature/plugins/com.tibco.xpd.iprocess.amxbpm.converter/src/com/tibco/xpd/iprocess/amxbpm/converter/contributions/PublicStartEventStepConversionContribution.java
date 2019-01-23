/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.iprocess.amxbpm.converter.contributions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.swt.graphics.Rectangle;

import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution;
import com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.ProcessWidget.ProcessWidgetType;
import com.tibco.xpd.processwidget.ProcessWidgetColors;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.processwidget.WidgetRGB;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.SequenceFlowType;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.Visibility;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This Conversion Contribution is intended to handle public Steps and Events.
 * We do not want to change the api of any other incoming request activity.
 * Hence if there is any Public Step/Event with public field definition that
 * refers to Data Field and not Parameters then, Find start event type None --
 * If there are any formal parameters in process then explicitly associate them
 * with start step/. -- Else if there are no formal parameters in process then
 * set the "No interface data association required" flag.
 * 
 * Convert Data Field referenced by any Public Field Definition, to
 * FormalParameter.
 * 
 * Public Steps :Create a Start Message Event for each Public Step, associate
 * the converted Formal Parameter for the defined Public Fields with the Start
 * message Event , when there are no defined public fields set the
 * "No interface data association required" flag.Link the Start Message Event to
 * the Public Step Activity.
 * 
 * Public Event :
 * 
 * In-Flow Public Event : Transformed to the Receive Task is configured for web
 * service and set to generate WSDL, associate the converted Formal Parameter
 * for the defined Public Fields with the Start message Event , when there are
 * no defined public fields set the "No interface data association required"
 * flag.
 * 
 * In-Flow Public event with deadline:Transformed to the Receive Task [with
 * attached Timer Event] is configured for web service and set to generate WSDL,
 * associate the converted Formal Parameter for the defined Public Fields with
 * the Start message Event , when there are no defined public fields set the
 * "No interface data association required" flag.
 * 
 * Standalone Public Event : Configure the transformed Receive Task, with web
 * service configured to generate wsdl. Associate the converted Formal Parameter
 * for the defined Public Fields with the Start message Event , when there are
 * no defined public fields set the "No interface data association required"
 * flag.
 * 
 * Standalone Public Event with deadline:Removes the timer event attached to the
 * transformed Receive Task.Convert the transformed Receive Task to Standalone
 * Message Event Handler , with web service configured to generate wsdl.
 * Associate the converted Formal Parameter for the defined Public Fields with
 * the Start message Event , when there are no defined public fields set the
 * "No interface data association required" flag.
 * 
 * Configure all receive tasks, which are not Configured.
 * 
 * 
 * 
 * 
 * @author aprasad
 * @since 22-May-2014
 */
public class PublicStartEventStepConversionContribution extends
        AbstractIProcessToBPMContribution {

    /**
     * 
     */
    private static final String PUBLIC_FIELD_SEPARATOR = "@"; //$NON-NLS-1$

    /**
     * 
     */
    private static final String REQUIRED = "Required="; //$NON-NLS-1$

    /**
     * 
     */
    private static final String BLANK = ""; //$NON-NLS-1$

    /**
     * 
     */
    private static final String STEP_SEPARATOR = "\n"; //$NON-NLS-1$

    /**
     * Attribute for Public Data Field name
     */
    private static final String PUBLIC_FIELD = "PublicField="; //$NON-NLS-1$

    /**
     * Attribute name for Step/Event Object name.
     */
    private static final String OBJECT_NAME = "ObjectName="; //$NON-NLS-1$

    /**
     * Name of ExtendedAttribute for Public Start Steps
     */
    private static final String PUBLIC_START_STEP_EXTENDED_ATTR =
            "iProcessPublicStartSteps"; //$NON-NLS-1$

    /**
     * Name of ExtendedAttribute for Public Start Events
     */
    private static final String PUBLIC_START_EVENT_EXTENDED_ATTR =
            "iProcessPublicEventSteps"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.customer.api.iprocess.amxbpm.conversion.AbstractIProcessToBPMContribution#convert(java.util.List,
     *      java.util.List, org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param processes
     * @param processInterfaces
     * @param monitor
     * @return IStatus representing Error/Warning/Info related to this
     *         Conversion.
     */
    @Override
    public IStatus convert(List<Process> processes,
            List<ProcessInterface> processInterfaces, IProgressMonitor monitor) {

        monitor.beginTask("", processes.size()); //$NON-NLS-1$

        try {

            for (Process process : processes) {

                convertProcess(process);
                monitor.worked(1);

            }
        } finally {
            monitor.done();
        }

        return Status.OK_STATUS;
    }

    /**
     * Converts the process for the Public Steps and Events.
     * 
     * @param process
     *            , to convert.
     */
    private void convertProcess(Process process) {

        /* get public Steps details for this process */
        Collection<PublicStartStepOrEvent> publicSteps =
                getPublicStartStepsForProcess(process, true);

        /* get public Events details for this process */
        Collection<PublicStartStepOrEvent> publicEvents =
                getPublicStartStepsForProcess(process, false);

        /* Process Data Fields */
        EList<DataField> procDataFields = process.getDataFields();
        /* Convert to Map */
        Map<String, DataField> procDataFieldsMap =
                convertToDataFieldsMap(procDataFields);

        /* Convert to Map of Activities in Process */
        Map<String, Activity> processActivitiesMap =
                getAsActivitiesMap(Xpdl2ModelUtil
                        .getAllActivitiesInProc(process));

        /*
         * When Public Steps/Events have associated Public fields that refer to
         * Data Field and not FormalParameter
         */
        if (containsPublicFields(publicSteps, procDataFieldsMap)
                || containsPublicFields(publicEvents, procDataFieldsMap)) {

            /*
             * If process contains FormalParameters , assign them to all the
             * None Start events
             */
            doExplicitParameterAssignmentForStartNoneEvents(process);
        }

        /* Convert Public Data Fields to In Mode Parameters */

        /*
         * 1. Get all Public Data Fields, associated with Public Start
         * Step/Events in the process
         */
        Set<DataField> publicDataFields =
                getAllPublicDataFields(publicSteps,
                        publicEvents,
                        procDataFieldsMap);

        /* Convert Public DataField(s) to In mode Formal Parameter */
        convertPublicDataFieldsToParamter(publicDataFields, process);

        Map<String, FormalParameter> procParamMap =
                getAsParametersMap(process.getFormalParameters());

        /* All other steps above are common for both the Conversions */
        /* Start XPD-6112: Create start message events for public start steps. */
        for (PublicStartStepOrEvent publicStep : publicSteps) {

            EList<Activity> activities = process.getActivities();
            if (activities != null) {

                createAndAddToProcessStartMsgEventForPubStartStep(publicStep,
                        processActivitiesMap.get(publicStep.getName()),
                        process,
                        procParamMap);
            }

        }

        /* End XPD-6112 */

        /*
         * Start XPD-6111: For Public Events, Convert receive task to
         * intermediate message event and associate Public Field Paratmeters
         * linked to the Public Step with the Message Event Handler.
         */

        for (PublicStartStepOrEvent publicEvent : publicEvents) {

            // get the Intermediate Activity for this Public Event
            Activity intermediateActivity =
                    processActivitiesMap.get(publicEvent.getName());

            if (intermediateActivity != null) {

                if (isStandaloneActivity(intermediateActivity)) {
                    /*
                     * Public Standalone iProcess Events generate receive Tasks,
                     * Configure them for Web Service set to generate WSDL,and
                     * Associate required Public Fields
                     */
                    configureStandaloneIntermediateTaskWebServiceDefault(publicEvent,
                            intermediateActivity,
                            process,
                            procParamMap);

                } else {
                    /*
                     * Public InFlow iProcess Events generate receive Tasks,
                     * configure them to generate WSDL, and associate defined
                     * Public Field FormalParameters.
                     */

                    associatePublicFields(intermediateActivity,
                            publicEvent,
                            procParamMap);

                    IpmImportUtil
                            .addWebServiceOperationSetToGenerateWSDL(intermediateActivity,
                                    process);

                }
            }
        }

        /*
         * Finally Configure all Receive task's Implementation to Web Service
         * default. And convert to intermediate catch vent handler if stndalone.
         */
        for (Activity activity : Xpdl2ModelUtil.getAllActivitiesInProc(process)) {

            Implementation impl = activity.getImplementation();

            if (impl instanceof Task) {

                Task task = (Task) impl;

                TaskReceive taskReceive = task.getTaskReceive();

                if (taskReceive != null) {
                    // Configure the receive task if not already done.

                    if (taskReceive.getImplementation() == ImplementationType.UNSPECIFIED_LITERAL) {

                        if (isStandaloneActivity(activity)) {
                            /*
                             * Public Standalone iProcess Events generate
                             * receive Tasks, Configure them for Web Service set
                             * to generate WSDL,and Associate required Public
                             * Fields
                             */
                            configureStandaloneIntermediateTaskWebServiceDefault(null,
                                    activity,
                                    process,
                                    procParamMap);

                        } else {
                            IpmImportUtil
                                    .addWebServiceOperationSetToGenerateWSDL(activity,
                                            process);
                            /*
                             * No Need to set Interface with Parameters, leave
                             * it with with implicit all Process data.
                             */
                        }
                    }
                }

            }

        }
        /* End XPD-6111 */

    }

    /**
     * @param formalParameters
     * @return
     */
    private Map<String, FormalParameter> getAsParametersMap(
            Collection<FormalParameter> formalParameters) {

        Map<String, FormalParameter> paramMap =
                new HashMap<String, FormalParameter>();

        for (FormalParameter formalParameter : formalParameters) {

            paramMap.put(formalParameter.getName(), formalParameter);
        }
        return paramMap;
    }

    /**
     * Checks if the given Activity is standalone, by checking its incoming
     * transitions.
     * 
     * @param intermediateActivity
     * @return true if the given activity has no incoming transitions, false
     *         otherwise.
     */
    private boolean isStandaloneActivity(Activity intermediateActivity) {

        EList<Transition> incomingTransitions =
                intermediateActivity.getIncomingTransitions();

        if (incomingTransitions == null || incomingTransitions.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Converts Intermediate Task representing the Public Event, to
     * IntermediateMessageHandler.Creates an instance Intermediate Message Event
     * Handler ,and replaces the Intermediate Task in the process with this new
     * Message Handler created.
     * 
     * @param publicEvent
     *            , Public event details containing name and Public Fields. OR
     *            <code>null</code> if we are converting a receive task that is
     *            not named as a public event,
     * @param receiveTaskActivity
     *            , Intermediate Task representing the Public Event.
     * @param process
     *            , Parent {@link Process}.
     * @param convertedPublicFieldParameters
     *            , Public Fields converted to {@link FormalParameter}(s)
     */
    private void configureStandaloneIntermediateTaskWebServiceDefault(
            PublicStartStepOrEvent publicEvent, Activity receiveTaskActivity,
            Process process,
            Map<String, FormalParameter> convertedPublicFieldParameters) {

        String rcvTaskActId = receiveTaskActivity.getId();
        FlowContainer flowContainer = receiveTaskActivity.getFlowContainer();

        Activity msgEventHandler =
                createIntermediateMessageEventHandler(publicEvent,
                        receiveTaskActivity);

        /*
         * Update all Transitions originating/ending from this intermediate
         * Activity to originate/end at this Message Event handler
         */

        String messageEventHandlerId = msgEventHandler.getId();

        EList<Transition> transitions = flowContainer.getTransitions();
        if (transitions != null) {
            for (Transition trasition : transitions) {

                if (rcvTaskActId.equals(trasition.getFrom())) {
                    trasition.setFrom(messageEventHandlerId);
                }

                if (rcvTaskActId.equals(trasition.getTo())) {
                    trasition.setTo(messageEventHandlerId);
                }
            }
        }

        /*
         * Associate all the Public Fields attached to the Public Event with the
         * new created Message Event Handler Activity.
         */

        associatePublicFields(msgEventHandler,
                publicEvent,
                convertedPublicFieldParameters);

        /* Add Web Service Operation to the Message Event Handler Activity */
        IpmImportUtil.addWebServiceOperationSetToGenerateWSDL(msgEventHandler,
                process);

        /* Replace Intermediate Task with the new Message Event Handler */

        /* Remove Timer Event attached to the receive task , if ANY */
        removeTimerEvent(receiveTaskActivity);

        /*
         * If event handler has no outgoing flow then create a flow to a new end
         * event to make process valid.
         */
        if (msgEventHandler.getOutgoingTransitions().isEmpty()) {
            Rectangle evtBnds = Xpdl2ModelUtil.getObjectBounds(msgEventHandler);
            NodeGraphicsInfo ngi =
                    Xpdl2ModelUtil.getNodeGraphicsInfo(msgEventHandler);

            Activity endEvent =
                    ElementsFactory
                            .createEvent(new Point(evtBnds.x
                                    + (evtBnds.width * 3), evtBnds.y
                                    + (evtBnds.height / 2)),
                                    new Dimension(evtBnds.width, evtBnds.height),
                                    ngi.getLaneId(),
                                    EventFlowType.FLOW_END_LITERAL,
                                    EventTriggerType.EVENT_NONE_LITERAL,
                                    ProcessWidgetColors
                                            .getInstance(ProcessWidgetType.BPMN_PROCESS)
                                            .getGraphicalNodeColor(null,
                                                    ProcessWidgetColors.END_EVENT_FILL)
                                            .toString(),
                                    ProcessWidgetColors
                                            .getInstance(ProcessWidgetType.BPMN_PROCESS)
                                            .getGraphicalNodeColor(null,
                                                    ProcessWidgetColors.END_EVENT_LINE)
                                            .toString(),
                                    msgEventHandler.getProcess());

            flowContainer.getActivities().add(endEvent);

            Transition newFlow =
                    ElementsFactory
                            .createTransition(msgEventHandler,
                                    endEvent,
                                    SequenceFlowType.UNCONTROLLED_LITERAL,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    ProcessWidgetColors
                                            .getInstance(ProcessWidgetType.BPMN_PROCESS)
                                            .getGraphicalNodeColor(null,
                                                    ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE)
                                            .toString());
            flowContainer.getTransitions().add(newFlow);

        }

    }

    /**
     * Searches and Removes Timer Event if any, attached to this Receive task
     * Activity.
     * 
     * @param intermediateActivity
     */
    private void removeTimerEvent(Activity receiveTaskActivity) {

        Collection<Activity> attachedEvents =
                Xpdl2ModelUtil.getAttachedEvents(receiveTaskActivity);

        /* XPD-6513 Also Remove the Deadline Activity */
        Xpdl2ModelUtil.setOtherAttribute(receiveTaskActivity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ActivityDeadlineEventId(),
                null);

        Process process = receiveTaskActivity.getProcess();
        if (process != null) {
            for (Activity attachedEvent : attachedEvents) {

                EventTriggerType eventTriggerType =
                        EventObjectUtil.getEventTriggerType(attachedEvent);

                if (EventTriggerType.EVENT_TIMER_LITERAL
                        .equals(eventTriggerType)) {
                    // Remove Attached End Event and transitions
                    EList<Transition> outgoingTransitions =
                            attachedEvent.getOutgoingTransitions();

                    if (outgoingTransitions != null
                            && outgoingTransitions.size() > 0) {

                        for (Transition outTrans : outgoingTransitions) {
                            Activity outActivity =
                                    outTrans.getFlowContainer()
                                            .getActivity(outTrans.getTo());
                            /* Delete the linke end event if it is one. */
                            if (outActivity != null
                                    && outActivity.getEvent() instanceof EndEvent) {
                                outActivity.getFlowContainer().getActivities()
                                        .remove(outActivity);
                            }

                            /*
                             * Always remove the transition because we are
                             * deleting the source activity for it!
                             */
                            outTrans.getFlowContainer().getTransitions()
                                    .remove(outTrans);
                        }

                    }

                    attachedEvent.getFlowContainer().getActivities()
                            .remove(attachedEvent);

                    /* As we are removing the Timer Remove Activity deadline */
                    Xpdl2ModelUtil.setOtherAttribute(receiveTaskActivity,
                            Xpdl2Package.eINSTANCE.getActivity_Deadline(),
                            null);

                }
            }
        }
    }

    /**
     * Creates and returns Intermediate Message Event Handler, representing the
     * Intermediate Task Activity.
     * 
     * @param publicEvent
     *            public event definition OR <code>null</code> if we are
     *            converting a receive task that is not named as a public event,
     * @param intermediateActivity
     * @return Intermediate Message Event Handler, representing the Intermediate
     *         Task Activity.
     */
    private Activity createIntermediateMessageEventHandler(
            PublicStartStepOrEvent publicEvent, Activity intermediateActivity) {

        /*
         * XPD-6513 Modify same activity to Message Event handler and not create
         * a new one.
         */
        Activity messageEventHandler = intermediateActivity;
        /* Reset or Remove intermediate Task details */
        intermediateActivity.setImplementation(null);

        /* Create and Configure Intermediate Message Event */
        IntermediateEvent intermediateEvent =
                Xpdl2Factory.eINSTANCE.createIntermediateEvent();

        intermediateEvent.setTrigger(TriggerType.MESSAGE_LITERAL);

        intermediateEvent
                .setTriggerResultMessage((TriggerResultMessage) ElementsFactory
                        .createEventDetail(EventFlowType.FLOW_INTERMEDIATE_LITERAL,
                                EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                                intermediateActivity.getProcess()));

        Xpdl2ModelUtil.setOtherAttribute(intermediateEvent,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ImplementationType(),
                TaskImplementationTypeDefinitions.WEB_SERVICE);

        intermediateEvent
                .setImplementation(ImplementationType.WEB_SERVICE_LITERAL);

        messageEventHandler.setEvent(intermediateEvent);

        /* End creation of Intermediate Message Event */
        messageEventHandler.setTransaction(null);

        /* XPDL21 Cater to event Message Throw for activity resource patterns. */

        ActivityResourcePatterns patterns =
                XpdExtensionFactory.eINSTANCE.createActivityResourcePatterns();

        AllocationStrategy strategy =
                XpdExtensionFactory.eINSTANCE.createAllocationStrategy();

        patterns.setAllocationStrategy(strategy);
        strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);

        Xpdl2ModelUtil.setOtherElement(messageEventHandler,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ActivityResourcePatterns(),
                patterns);

        String name =
                String.format(publicEvent != null ? publicEvent.getName()
                        : intermediateActivity.getName());

        /* Fix Internal name to contain valid characters */
        messageEventHandler.setName(NameUtil.getInternalName(name, true));

        Xpdl2ModelUtil.setOtherAttribute(messageEventHandler,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                Xpdl2ModelUtil.getDisplayNameOrName(intermediateActivity));

        Xpdl2ModelUtil.setOtherAttribute(messageEventHandler,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);

        Xpdl2ModelUtil.getNodeGraphicsInfo(messageEventHandler);

        return messageEventHandler;
    }

    /**
     * Returns a Map<String,Activity> containing {@link Activity} against its
     * name.
     * 
     * @param activities
     *            ,Collection of {@link Activity} for which the Map created is
     *            returned.
     * @return a Map<String,Activity> containing {@link Activity} and against
     *         its name.
     */
    private Map<String, Activity> getAsActivitiesMap(
            Collection<Activity> activities) {

        Map<String, Activity> activitiesMap = new HashMap<String, Activity>();

        for (Activity activity : activities) {

            activitiesMap.put(activity.getName(), activity);
        }
        return activitiesMap;
    }

    /**
     * Creates Start Message Event for the given Public Start Step and, links it
     * to the Public Start Step.The name of the Public Start Step, is based on
     * the Public Start Step name.The method also associates the Public Fields
     * linked to the Public Start Step, to the new Start Event created.The
     * 
     * @param publicStep
     *            , details of Public Step for which the Start Event is created.
     * @param publicStepActivity
     *            , the Public Step {@link Activity}.
     * @param process
     *            , parent {@link Process}.
     * @param convertedPublicFieldParameters
     *            , Map containing converted Public Field
     *            {@link FormalParameter}.
     * @return Start Event {@link Activity} created for the Public Step.
     */
    private Activity createAndAddToProcessStartMsgEventForPubStartStep(
            PublicStartStepOrEvent publicStep, Activity publicStepActivity,
            Process process,
            Map<String, FormalParameter> convertedPublicFieldParameters) {

        /* Lane container of the Public Step Activity */
        EObject container = Xpdl2ModelUtil.getContainer(publicStepActivity);

        /* Bounds of the Public Step Activity */
        Rectangle objectBounds =
                Xpdl2ModelUtil.getObjectBounds(publicStepActivity);

        /*
         * Location of the new Start Event Activity relative to the Public Step
         * Activity
         */
        int y = objectBounds.y - (objectBounds.height + 20);
        if (y < ((ProcessWidgetConstants.START_EVENT_SIZE / 2) + 1)) {
            y = ((ProcessWidgetConstants.START_EVENT_SIZE / 2) + 1);
        }
        Point location =
                new Point((objectBounds.x + objectBounds.width / 2), y);

        WidgetRGB fillColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.START_EVENT_FILL);
        WidgetRGB lineColor =
                ProcessWidgetColors.getInstance().getGraphicalNodeColor(null,
                        ProcessWidgetColors.START_EVENT_LINE);

        Activity startEventAct =
                ElementsFactory.createEvent(location,
                        new org.eclipse.draw2d.geometry.Dimension(
                                ProcessWidgetConstants.START_EVENT_SIZE,
                                ProcessWidgetConstants.START_EVENT_SIZE),
                        ((Lane) container).getId(),
                        EventFlowType.FLOW_START_LITERAL,
                        EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL,
                        fillColor.toString(),
                        lineColor.toString(),
                        null);

        String name = String.format("Start At %1s", //$NON-NLS-1$
                publicStep.getName());

        /* Fix Internal name to contain valid characters */
        startEventAct.setName(NameUtil.getInternalName(name, true));

        Xpdl2ModelUtil.setOtherAttribute(startEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                name);

        Xpdl2ModelUtil.setOtherAttribute(startEventAct,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Visibility(),
                Visibility.PUBLIC);

        Xpdl2ModelUtil.getNodeGraphicsInfo(startEventAct);

        /*
         * Add Transition from the new Start Event Activity to the Public Step
         * Activity
         */
        Transition transition =
                ElementsFactory
                        .createTransition(startEventAct,
                                publicStepActivity,
                                SequenceFlowType.UNCONTROLLED_LITERAL,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                ProcessWidgetColors
                                        .getInstance(ProcessWidgetType.BPMN_PROCESS)
                                        .getGraphicalNodeColor(null,
                                                ProcessWidgetColors.UNCONTROLLED_SEQ_FLOW_LINE)
                                        .toString());

        publicStepActivity.getFlowContainer().getTransitions().add(transition);

        /*
         * Associate all the Public Fields attached to the Public Step with the
         * new created Start Event Activity.
         */

        associatePublicFields(startEventAct,
                publicStep,
                convertedPublicFieldParameters);

        /* /Add Generating Service */
        IpmImportUtil.addWebServiceOperationSetToGenerateWSDL(startEventAct,
                process);

        publicStepActivity.getFlowContainer().getActivities()
                .add(startEventAct);
        return startEventAct;
    }

    /**
     * Associates the Public Fields linked to the Public Step with the given
     * Start Event {@link Activity}.
     * 
     * @param activity
     *            , to associate the Public Field {@link FormalParameter}.
     * @param publicStep
     *            public step def OR <code>null</code> if we are converting a
     *            receive task that is not named as a public event, , Public
     *            Step containing Public Fields association.
     * @param convertedPublicFieldParameters
     *            ,converted {@link FormalParameter} for the Public Fields.
     */
    private void associatePublicFields(Activity activity,
            PublicStartStepOrEvent publicStep,
            Map<String, FormalParameter> convertedPublicFieldParameters) {

        AssociatedParameters assocParams =
                getActivityAssociatedParameters(activity);

        if (assocParams != null) {

            List<FormalParameter> parametersToAssociate =
                    new ArrayList<FormalParameter>();

            if (publicStep != null) {
                for (String publicFieldName : publicStep.getPublicFields()
                        .keySet()) {

                    FormalParameter param =
                            convertedPublicFieldParameters.get(publicFieldName);

                    if (!parametersToAssociate.contains(param)) {
                        parametersToAssociate.add(param);
                    }
                }
            }

            if (!parametersToAssociate.isEmpty()) {

                associateParametersWithGivenActivity(activity,
                        parametersToAssociate,
                        publicStep.getPublicFields(),
                        assocParams);
            } else {
                /*
                 * When the Public Step does not contain any Public Field, set
                 * "No interface data association required" flag.
                 */
                assocParams.setDisableImplicitAssociation(true);
            }
        }
    }

    /**
     * Converts given {@link DataField}(s) to {@link FormalParameter}(s).Creates
     * Formal Parameter equivalent to the Data Field ,adds the new (In mode)
     * Formal Parameter to the Process and removes the DataField from the
     * Process.
     * 
     * @param publicDataFields
     *            , {@link Collection} of public {@link DataField} (s).
     * @param process
     * @return Map of converted {@link FormalParameter}(s) against the
     *         {@link FormalParameter} name.
     */
    private Map<String, FormalParameter> convertPublicDataFieldsToParamter(
            Collection<DataField> publicDataFields, Process process) {

        Map<String, FormalParameter> convertedPublicFormalParameters =
                new HashMap<String, FormalParameter>();

        for (DataField dataField : publicDataFields) {

            /* Create Formal Parameter equivalent to the DataField */
            FormalParameter parameter = createParameter(dataField);

            /* Add Formal Parameter to the Process */
            process.getFormalParameters().add(parameter);

            /* Remove Data Field from the Process */
            process.getDataFields().remove(dataField);

            /* Collect the new FormalParameter */
            convertedPublicFormalParameters.put(parameter.getName(), parameter);
        }

        return convertedPublicFormalParameters;
    }

    /**
     * Creates and Returns In mode Formal Parameter equivalent to the given Data
     * Field.
     * 
     * @param dataField
     *            , Data Field to create equivalent Formal Parameter for.
     * @return, In mode Formal Parameter equivalent to the Data Fields.
     */
    private FormalParameter createParameter(DataField dataField) {

        DataField copyOfDataField = EcoreUtil.copy(dataField);

        FormalParameter parameter =
                Xpdl2Factory.eINSTANCE.createFormalParameter();

        parameter.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(),
                copyOfDataField.getId());

        if (copyOfDataField.getName() != null) {
            parameter.setName(copyOfDataField.getName());
        }

        EAttribute ea =
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName();
        if (ea != null) {
            Xpdl2ModelUtil.setOtherAttribute(parameter,
                    ea,
                    Xpdl2ModelUtil.getOtherAttribute(copyOfDataField, ea));
        }

        if (copyOfDataField.isSetReadOnly()) {
            parameter.setReadOnly(copyOfDataField.isReadOnly());
        }

        if (copyOfDataField.getDataType() != null) {
            parameter.setDataType(copyOfDataField.getDataType());
        }

        if (copyOfDataField.getDescription() != null) {
            parameter.setDescription(copyOfDataField.getDescription());
        }

        if (copyOfDataField.getLength() != null) {
            parameter.setLength(copyOfDataField.getLength());
        }
        if (copyOfDataField.isIsArray()) {
            parameter.setIsArray(copyOfDataField.isIsArray());
        }

        /*
         * Sett mandatory to true because we'll be using them for receive tasks
         * etc and therefore will have to mandatory else we'll get a problem
         * marker.
         */

        parameter.setRequired(Boolean.TRUE);

        /* Parameter should be of IN mode */
        parameter.setMode(ModeType.IN_LITERAL);

        return parameter;
    }

    /**
     * Returns Collection of {@link DataField}(s) in Process, used as Public
     * Fields i.e associated with any Public Step/Event.
     * 
     * @param publicSteps
     *            , {@link Collection} of Public Step(s).
     * @param publicEvents
     *            {@link Collection} of Public Event(s).
     * @param procDataFieldsMap
     *            , Map<dataFieldName, {@link DataField}> of DataField(s) in the
     *            Process.
     * @return Set of unique {@link DataField}(s) in Process, which are used as
     *         Public Field, i.e are associated with a Public Step/Event.
     */
    private Set<DataField> getAllPublicDataFields(
            Collection<PublicStartStepOrEvent> publicSteps,
            Collection<PublicStartStepOrEvent> publicEvents,
            Map<String, DataField> procDataFieldsMap) {

        Set<DataField> publicDataFields = new HashSet<DataField>();

        Collection<PublicStartStepOrEvent> allPublicStepsAndEvents =
                new ArrayList<PublicStartStepOrEvent>();

        allPublicStepsAndEvents.addAll(publicSteps);
        allPublicStepsAndEvents.addAll(publicEvents);

        for (PublicStartStepOrEvent stepOrEvent : allPublicStepsAndEvents) {

            for (String publicField : stepOrEvent.getPublicFields().keySet()) {

                DataField dataField = procDataFieldsMap.get(publicField);
                /* Collect given Public Field if it is a DataField */
                if (dataField != null) {
                    publicDataFields.add(dataField);
                }
            }

        }

        return publicDataFields;
    }

    /**
     * Associate all {@link FormalParameter}(s) in the {@link Process} with each
     * contained Start Event of type NONE.If there are no
     * {@link FormalParameter}(s) in the {@link Process}, set
     * "No interface data association required" flag for all Start Events of
     * Type NONE.
     * 
     * @param process
     *            to handle.
     */
    private void doExplicitParameterAssignmentForStartNoneEvents(Process process) {
        /*- Find start event type None -- If there are any formal parameters in process
        then explicitly associate them with start step/. -- Else if there are no
        formal parameters in process then set the
        "No interface data association required" flag*/

        /* 1. Find all Start Event of type NONE */
        Collection<Activity> allStartEventsInProcess =
                getAllStartEventsOfTypeNone(process);

        // 2. Get list of all Parameters in the Process.
        EList<FormalParameter> procFormalParameters =
                process.getFormalParameters();
        /*
         * For Each NONE Start Event 3. If there are Parameters in process,
         * associate the Process Parameters. 4. Else set
         * "No interface data association required" flag
         */

        for (Activity activity : allStartEventsInProcess) {

            AssociatedParameters associatedParameters =
                    getActivityAssociatedParameters(activity);

            if (associatedParameters != null) {
                /* There are parameters in process, associate them */
                if (procFormalParameters != null
                        && !procFormalParameters.isEmpty()) {

                    associateParametersWithGivenActivity(activity,
                            procFormalParameters,
                            null,
                            associatedParameters);
                } else {
                    /*
                     * Set "No interface data association required" flag
                     * DisableImplicitAssociation
                     */

                    associatedParameters.setDisableImplicitAssociation(true);
                }
            }
        }

    }

    /**
     * @param activity
     * @return
     */
    private AssociatedParameters getActivityAssociatedParameters(
            Activity activity) {
        Object otherElement =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());

        if (otherElement == null) {
            otherElement =
                    XpdExtensionFactory.eINSTANCE.createAssociatedParameters();

            Xpdl2ModelUtil.setOtherElement(activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_AssociatedParameters(),
                    otherElement);
        }

        AssociatedParameters associatedParameters = null;

        if (otherElement instanceof AssociatedParameters) {

            associatedParameters = (AssociatedParameters) otherElement;
        }
        return associatedParameters;
    }

    /**
     * Associate given {@link FormalParameter}(s) with the {@link Activity}.This
     * method can be used for both general {@link FormalParameter} association
     * and Public Field Formal Parameter association. In former the argument
     * publicFieldData should be passed as null.publicFieldData is only used to
     * read the 'required' attribute for Public Field Parameters.
     * 
     * @param activity
     *            , {@link Activity} to associate the {@link FormalParameter}
     *            with.
     * @param procFormalParameters
     *            , {@link FormalParameter}(s) to associate with the Activity.
     * @param publicFieldData
     *            , Public Data Fields data, to read the 'required' attribute
     *            from, for general Parameter Association this can be left as
     *            Null.
     */
    private void associateParametersWithGivenActivity(Activity activity,
            Collection<FormalParameter> procFormalParameters,
            Map<String, Boolean> publicFieldData,
            AssociatedParameters assocParams) {

        for (FormalParameter formalParameter : procFormalParameters) {

            AssociatedParameter associatedParameter =
                    createAssociatedParameter(formalParameter);

            /*
             * When this is Public Field, read and set value for 'required' as
             * in the Public Field
             * 
             * Sid ** All input parameters to web service requests are required
             * in AMX BPM so we'll have to ignore original iprocess setting else
             * we'll just get error markers.
             */
            // if (publicFieldData != null) {
            //
            // Boolean required =
            // publicFieldData.get(formalParameter.getName());
            //
            // associatedParameter.setMandatory(required);
            // }

            assocParams.getAssociatedParameter().add(associatedParameter);
        }
    }

    /**
     * Creates and returns the {@link AssociatedParameter} for the given
     * {@link FormalParameter}.
     * 
     * @param formalParameter
     * @return {@link AssociatedParameter} created for given
     *         {@link FormalParameter}
     */
    private AssociatedParameter createAssociatedParameter(
            FormalParameter formalParameter) {

        if (formalParameter == null) {
            return null;
        }

        AssociatedParameter parameter =
                XpdExtensionFactory.eINSTANCE.createAssociatedParameter();

        parameter.setMode(formalParameter.getMode());
        parameter.setDescription(formalParameter.getDescription());
        parameter.setFormalParam(formalParameter.getName());
        parameter.setMandatory(formalParameter.isRequired());

        return parameter;
    }

    /**
     * Collects and returns all Start Events of type NONE in the given
     * {@link Process}.
     * 
     * @param process
     *            to look for the Start Event of NONE type.
     * @return Collection of Start Events of type None in the {@link Process}.
     *         Returns an Empty Collection when there are no NONE Start Event.
     */
    private Collection<Activity> getAllStartEventsOfTypeNone(Process process) {

        Collection<Activity> startNoneEvents = new LinkedList<Activity>();

        for (Activity activity : process.getActivities()) {

            Event event = activity.getEvent();
            /* Start Event */
            if (event instanceof StartEvent) {

                /* Start Event of type NONE */
                if (TriggerType.NONE_LITERAL.equals(((StartEvent) event)
                        .getTrigger())) {

                    startNoneEvents.add(activity);
                }
            }

        }
        return startNoneEvents;
    }

    /**
     * Creates and returns {@link Collection} of {@link PublicStartStepOrEvent}
     * representing the public start steps of the given {@link Process}.
     * 
     * @param process
     *            , to read the Public Start Steps/Events for.
     * @param step
     *            , true to look for Steps, false to look for Events.
     * @return {@link Collection} of {@link PublicStartStepOrEvent} representing
     *         the public start steps of the given {@link Process}.
     */
    private Collection<PublicStartStepOrEvent> getPublicStartStepsForProcess(
            Process process, boolean step) {

        EList<ExtendedAttribute> extendedAttributes =
                process.getExtendedAttributes();

        Collection<PublicStartStepOrEvent> publicStart =
                new ArrayList<PublicStartEventStepConversionContribution.PublicStartStepOrEvent>();
        String publicStepsOREventsDetailsString = null;

        /*
         * Collect ExtensionAttribute for PublicSteps and PublicEvents to remove
         * after reading
         */
        Collection<ExtendedAttribute> extAttribToRemove =
                new ArrayList<ExtendedAttribute>();

        for (ExtendedAttribute extendedAttribute : extendedAttributes) {
            /* Look out for ExtendedAttribute for Public Start Step/Event */
            if ((step
                    && PUBLIC_START_STEP_EXTENDED_ATTR.equals(extendedAttribute
                            .getName()) || (!step && PUBLIC_START_EVENT_EXTENDED_ATTR
                    .equals(extendedAttribute.getName())))) {

                publicStepsOREventsDetailsString =
                        getExtendedAttribValue(extendedAttribute);

                publicStart
                        .addAll(readPublicStepsOrEvents(publicStepsOREventsDetailsString,
                                process));

                extAttribToRemove.add(extendedAttribute);

            }

        }
        /*
         * Now All Extended Attributes for Public Steps and Public Events are
         * read, remove them
         */
        extendedAttributes.removeAll(extAttribToRemove);

        return publicStart;
    }

    /**
     * @param extendedAttribute
     * @return String value of the {@link ExtendedAttribute}., null when not
     *         found.
     */
    private String getExtendedAttribValue(ExtendedAttribute extendedAttribute) {
        FeatureMap mixed = extendedAttribute.getMixed();

        if (mixed != null && !mixed.isEmpty()) {

            Object value = mixed.get(0).getValue();
            if (value instanceof String) {
                return (String) value;
            }
        }
        return null;
    }

    /**
     * Creates Map of {@link DataField} name and {@link DataField}.
     * 
     * @param procDataFields
     * 
     * @return {@link Map} of {@link DataField} name and {@link DataField}.
     */
    private Map<String, DataField> convertToDataFieldsMap(
            EList<DataField> procDataFields) {

        Map<String, DataField> dataFieldMap = new HashMap<String, DataField>();

        for (DataField dataField : procDataFields) {

            dataFieldMap.put(dataField.getName(), dataField);
        }

        return dataFieldMap;
    }

    /**
     * Checks if there exist any Public Step/Event with Public Field
     * Association, returns true if exists , false otherwise.
     * 
     * @param procDataFieldsMap
     *            , Map of <name, {@link DataField}> pair for all the
     *            {@link DataField}(s) in {@link Process}.
     * @param publicStepsOrEvents
     *            , Collection of {@link PublicStartStepOrEvent} representing
     *            Public Steps or Events.
     * @return true if there exists at least one Public Step/Event with Public
     *         Field association, false if publicStepsOrEvents is null.
     */
    private boolean containsPublicFields(
            Collection<PublicStartStepOrEvent> publicStepsOrEvents,
            Map<String, DataField> procDataFieldsMap) {

        if (publicStepsOrEvents == null) {
            return false;
        }

        for (PublicStartStepOrEvent publicStartStepOrEvent : publicStepsOrEvents) {

            Set copyOfDataFieldNames =
                    new TreeSet(publicStartStepOrEvent.getPublicFields()
                            .keySet());

            copyOfDataFieldNames.retainAll(procDataFieldsMap.keySet());

            if (!copyOfDataFieldNames.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses the String to read details of the Public Start Steps/Events and
     * creates an instance of {@link PublicStartStepOrEvent} for each public
     * Step/Event in the String.
     * 
     * @param publicStepsDetailsString
     *            , String containing the PublicStep/Event details.
     * @param process
     *            , Parent {@link Process}.
     * @return {@link Collection} of {@link PublicStartStepOrEvent} , created by
     *         parsing the String.
     */
    private Collection<PublicStartStepOrEvent> readPublicStepsOrEvents(
            String publicStepsDetailsString, Process process) {

        /* To Collect the Public Start Step */
        Collection<PublicStartStepOrEvent> publicSteps =
                new LinkedList<PublicStartStepOrEvent>();

        /* Split the String to separate Strings for each Public Step */
        String[] stepsStringArray = publicStepsDetailsString.split("#"); //$NON-NLS-1$

        for (String stepDetailsArray : stepsStringArray) {
            // Skip empty Strings resulting from Splits
            if (stepDetailsArray.trim().length() == 0) {
                continue;
            }

            /*
             * Split String to individual String elements representing the Step
             * name, datafield name and datafield's required property, where in
             * first element represents the Step name and later pair of entries
             * represents the datafield name and its required property.
             */
            String[] stepDetails =
                    stepDetailsArray.trim().split(STEP_SEPARATOR);

            /*
             * first element is the Step Name later each Pair of entries [2 set
             * of entries] represent data field and its 'required' property,
             * alternate
             */
            if (stepDetails.length > 0) {

                String stepName = stepDetails[0].replace(OBJECT_NAME, BLANK);

                int publicFieldsCount = stepDetails.length;
                int i = 1;// Skip name element

                Map<String, Boolean> publicFields =
                        new HashMap<String, Boolean>();

                for (; i < publicFieldsCount; i++) {

                    String[] dataFieldDetails =
                            stepDetails[i].split(PUBLIC_FIELD_SEPARATOR);
                    String publicFieldName = null;
                    String required = "false";// have a default value //$NON-NLS-1$

                    for (String detail : dataFieldDetails) {

                        if (detail.startsWith(PUBLIC_FIELD)) {

                            publicFieldName =
                                    detail.replace(PUBLIC_FIELD, BLANK);

                        } else if (detail.startsWith(REQUIRED)) {

                            required = detail.replace(REQUIRED, BLANK);
                        }

                    }
                    /* Collect Public Field */
                    Boolean requiredBool = Boolean.parseBoolean(required);
                    if (publicFieldName != null) {
                        publicFields.put(publicFieldName, requiredBool);
                    }

                }

                /*
                 * create and collect PublicStartStepOrEvent representing this
                 * Public Start Step
                 */
                publicSteps.add(new PublicStartStepOrEvent(stepName,
                        publicFields));
            }
        }
        return publicSteps;
    }

    /**
     * Class to hold data for a Public Start Step/Event.
     * 
     * @author aprasad
     * @since 22-May-2014
     */
    class PublicStartStepOrEvent {

        private String stepName;

        /**
         * Map <filedName, required>
         */
        private Map<String, Boolean> publicFields;

        /**
         * @param stepName
         * @param publicFields
         */
        public PublicStartStepOrEvent(String stepName,
                Map<String, Boolean> publicFields) {
            super();
            this.stepName = stepName;
            this.publicFields = publicFields;
        }

        /**
         * @return the stepName
         */
        public String getName() {
            return stepName;
        }

        /**
         * @param stepName
         *            the stepName to set
         */
        public void setStepName(String stepName) {
            this.stepName = stepName;
        }

        /**
         * @return the publicFields
         */
        public Map<String, Boolean> getPublicFields() {

            if (publicFields == null) {
                return Collections.EMPTY_MAP;
            }
            return publicFields;
        }

        /**
         * @param stepName
         */
        public PublicStartStepOrEvent(String stepName) {
            super();
            this.stepName = stepName;
        }

        /**
         * @param publicFields
         *            the dataFields to set
         */
        public void setPublicFields(Map<String, Boolean> publicFields) {
            this.publicFields = publicFields;
        }

    }
}
