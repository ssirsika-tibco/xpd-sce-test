/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil;
import com.tibco.xpd.analyst.resources.xpdl2.errorEvents.BpmnCatchableErrorUtil.ErrorCatchType;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceData;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ActivityInterfaceDataUtil;
import com.tibco.xpd.analyst.resources.xpdl2.utils.TaskImplementationTypeDefinitions;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.GetSignalPayloadException.GetSignalPayloadExceptionType;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.ElementsFactory;
import com.tibco.xpd.processwidget.adapters.EventAdapter.TimerTriggerMode;
import com.tibco.xpd.processwidget.adapters.EventFlowType;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.processwidget.adapters.TaskType;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationStrategyType;
import com.tibco.xpd.xpdExtension.AssociatedParameters;
import com.tibco.xpd.xpdExtension.ConstantPeriod;
import com.tibco.xpd.xpdExtension.EventHandlerInitialisers;
import com.tibco.xpd.xpdExtension.RescheduleTimerScript;
import com.tibco.xpd.xpdExtension.RescheduleTimerSelectionType;
import com.tibco.xpd.xpdExtension.RescheduleTimers;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.SignalType;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.Deadline;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Various utilities to help in getting / setting BPMN Event-related data.
 * <p>
 * Shared by xpdl2 process widget event adapter and event properties sections.
 * 
 * @author aallway
 * 
 */
public class EventObjectUtil {

    public static final String CONSTANTPERIOD_SCRIPTGRAMMAR = "ConstantPeriod"; //$NON-NLS-1$

    /**
     * Useful enum for various utils to specify the type of events required for
     * return.
     */
    public static enum ReturnCatchThrowTypes {
        CATCH_AND_THROW, CATCH_ONLY, THROW_ONLY
    }

    /**
     * Get the 'continue on timeout' flag.
     * <p>
     * Nominally, by default, BPMN specifies that when a Timer event attached to
     * task border times-out then the task it is attached to is interupted
     * (withdrawn).
     * <p>
     * In order to allow escalation without withdraw the process widget supports
     * an additional flag (continue on timeout), that when true, overrides this
     * behaviour and prevents task being interrupted.
     * <p>
     * This will only be called for Intermediate Timer Event on task boundary.
     * 
     * @return true if continue on timeout flag is set, false if it is not.
     */
    public static boolean getContinueOnTimeoutFlag(Activity eventAct) {
        Event ev = eventAct.getEvent();

        EObject o = ev.getEventTriggerTypeNode();
        if (o instanceof TriggerTimer) {
            TriggerTimer tt = (TriggerTimer) o;

            Object val =
                    Xpdl2ModelUtil.getOtherAttribute(tt,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ContinueOnTimeout());
            if (val instanceof Boolean) {
                return ((Boolean) val).booleanValue();
            }
        }

        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> If the event is a non-cancelling event (for now
     *         this is only timer and catch signal event.
     */
    public static boolean isNonCancellingEvent(Activity activity) {
        Event ev = activity.getEvent();

        if (ev != null) {
            EObject o = ev.getEventTriggerTypeNode();

            if (o instanceof TriggerTimer) {
                TriggerTimer tt = (TriggerTimer) o;

                Object val =
                        Xpdl2ModelUtil.getOtherAttribute(tt,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ContinueOnTimeout());
                if (val instanceof Boolean) {
                    return ((Boolean) val).booleanValue();
                }

            } else if (o instanceof TriggerResultSignal) {
                TriggerResultSignal trs = (TriggerResultSignal) o;

                Object val =
                        Xpdl2ModelUtil.getOtherAttribute(trs,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_NonCancelling());
                if (val instanceof Boolean) {
                    return ((Boolean) val).booleanValue();
                }
            }
        }

        return false;
    }

    /**
     * Evaluate whether the given event activity is a CATCH or THROW type event.
     * 
     * @param activity
     * @return CatchThrow.CATCH/THROW or <code>null</code> if not an event
     *         activity.
     */
    public static CatchThrow getCatchThrowType(Activity activity) {
        CatchThrow catchThrow = null;

        EventFlowType flowType = getFlowType(activity);
        EventTriggerType triggerType = getEventTriggerType(activity);

        if (triggerType != null) {
            if (EventTriggerType.EVENT_CANCEL_LITERAL.equals(triggerType)) {
                /* Cancel is catch for intermediate or throw for end. */
                if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                    catchThrow = CatchThrow.THROW;
                } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                        .equals(flowType)) {
                    catchThrow = CatchThrow.CATCH;
                }

            } else if (EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.THROW;

            } else if (EventTriggerType.EVENT_CONDITIONAL_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_ERROR_LITERAL.equals(triggerType)) {
                /* Error is catch for intermediate or throw for end. */
                if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                    catchThrow = CatchThrow.THROW;
                } else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                        .equals(flowType)) {
                    catchThrow = CatchThrow.CATCH;
                }

            } else if (EventTriggerType.EVENT_LINK_CATCH_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_LINK_THROW_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.THROW;

            } else if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.THROW;

            } else if (EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.THROW;

            } else if (EventTriggerType.EVENT_NONE_LITERAL.equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            } else if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.THROW;

            } else if (EventTriggerType.EVENT_TERMINATE_LITERAL
                    .equals(triggerType)) {
                catchThrow = CatchThrow.THROW;

            } else if (EventTriggerType.EVENT_TIMER_LITERAL.equals(triggerType)) {
                catchThrow = CatchThrow.CATCH;

            }
        }

        return catchThrow;
    }

    /**
     * 
     * @param activity
     * @return <code>true</code> if the passed activity is an is a Catch/Throw
     *         Local signal event.
     */
    public static boolean isLocalSignalEvent(Activity activity) {
        Event event = activity.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                Object otherAttribute =
                        Xpdl2ModelUtil.getOtherAttribute(signal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalType());

                if (otherAttribute instanceof SignalType) {
                    SignalType sigType = (SignalType) otherAttribute;
                    return SignalType.LOCAL.equals(sigType) ? true : false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public static EventTriggerType getEventTriggerType(Activity act) {
        Event event = act.getEvent();

        if (event instanceof StartEvent || event instanceof IntermediateEvent) {
            TriggerType trigger;
            if (event instanceof StartEvent) {
                trigger = ((StartEvent) event).getTrigger();
                switch (trigger.getValue()) {
                case TriggerType.CANCEL:
                    return EventTriggerType.EVENT_CANCEL_LITERAL;
                case TriggerType.COMPENSATION:
                    return EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL;
                case TriggerType.ERROR:
                    return EventTriggerType.EVENT_ERROR_LITERAL;
                case TriggerType.MESSAGE:
                    return EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL;
                case TriggerType.MULTIPLE:
                    return EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL;
                case TriggerType.CONDITIONAL:
                    return EventTriggerType.EVENT_CONDITIONAL_LITERAL;
                case TriggerType.TIMER:
                    return EventTriggerType.EVENT_TIMER_LITERAL;
                case TriggerType.SIGNAL:
                    return EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL;
                }
            } else {

                IntermediateEvent intermediateEvent = (IntermediateEvent) event;
                trigger = intermediateEvent.getTrigger();

                switch (trigger.getValue()) {
                case TriggerType.CANCEL:
                    return EventTriggerType.EVENT_CANCEL_LITERAL;
                case TriggerType.COMPENSATION:
                    Object objCatchThrow =
                            Xpdl2ModelUtil.getOtherAttribute(intermediateEvent
                                    .getTriggerResultCompensation(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_CatchThrow());
                    if (objCatchThrow instanceof CatchThrow) {
                        CatchThrow catchThrow = (CatchThrow) objCatchThrow;
                        if (CatchThrow.CATCH == catchThrow) {
                            return EventTriggerType.EVENT_COMPENSATION_CATCH_LITERAL;
                        } else if (CatchThrow.THROW == catchThrow) {
                            return EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL;
                        }
                    }
                case TriggerType.ERROR:
                    return EventTriggerType.EVENT_ERROR_LITERAL;
                case TriggerType.MESSAGE:
                    TriggerResultMessage triggerResultMessage =
                            intermediateEvent.getTriggerResultMessage();
                    if (triggerResultMessage != null) {
                        CatchThrow msgCatchThrow =
                                triggerResultMessage.getCatchThrow();
                        if (CatchThrow.CATCH == msgCatchThrow) {
                            return EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL;
                        } else if (CatchThrow.THROW == msgCatchThrow) {
                            return EventTriggerType.EVENT_MESSAGE_THROW_LITERAL;
                        }
                    }
                case TriggerType.MULTIPLE:
                    objCatchThrow =
                            Xpdl2ModelUtil.getOtherAttribute(intermediateEvent
                                    .getTriggerIntermediateMultiple(),
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_CatchThrow());
                    if (objCatchThrow instanceof CatchThrow) {
                        CatchThrow catchThrow = (CatchThrow) objCatchThrow;
                        if (CatchThrow.CATCH == catchThrow) {
                            return EventTriggerType.EVENT_MULTIPLE_CATCH_LITERAL;
                        } else if (CatchThrow.THROW == catchThrow) {
                            return EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL;
                        }
                    }
                case TriggerType.CONDITIONAL:
                    return EventTriggerType.EVENT_CONDITIONAL_LITERAL;
                case TriggerType.TIMER:
                    return EventTriggerType.EVENT_TIMER_LITERAL;
                case TriggerType.LINK:
                    TriggerResultLink triggerResultLink =
                            intermediateEvent.getTriggerResultLink();
                    if (triggerResultLink != null) {
                        CatchThrow linkCatchThrow =
                                triggerResultLink.getCatchThrow();
                        if (CatchThrow.CATCH == linkCatchThrow) {
                            return EventTriggerType.EVENT_LINK_CATCH_LITERAL;
                        } else if (CatchThrow.THROW == linkCatchThrow) {
                            return EventTriggerType.EVENT_LINK_THROW_LITERAL;
                        }
                    }
                case TriggerType.SIGNAL:
                    TriggerResultSignal triggerResultSignal =
                            intermediateEvent.getTriggerResultSignal();
                    if (triggerResultSignal != null) {
                        CatchThrow signalCatchThrow =
                                triggerResultSignal.getCatchThrow();
                        if (CatchThrow.CATCH == signalCatchThrow) {
                            return EventTriggerType.EVENT_SIGNAL_CATCH_LITERAL;
                        } else if (CatchThrow.THROW == signalCatchThrow) {
                            return EventTriggerType.EVENT_SIGNAL_THROW_LITERAL;
                        }
                    }
                }
            }

        } else if (event instanceof EndEvent) {
            ResultType result = ((EndEvent) event).getResult();

            switch (result.getValue()) {
            case ResultType.CANCEL:
                return EventTriggerType.EVENT_CANCEL_LITERAL;
            case ResultType.COMPENSATION:
                return EventTriggerType.EVENT_COMPENSATION_THROW_LITERAL;
            case ResultType.ERROR:
                return EventTriggerType.EVENT_ERROR_LITERAL;
            case ResultType.SIGNAL:
                return EventTriggerType.EVENT_SIGNAL_THROW_LITERAL;
            case ResultType.MESSAGE:
                return EventTriggerType.EVENT_MESSAGE_THROW_LITERAL;
            case ResultType.MULTIPLE:
                return EventTriggerType.EVENT_MULTIPLE_THROW_LITERAL;
            case ResultType.TERMINATE:
                return EventTriggerType.EVENT_TERMINATE_LITERAL;
            }
        }

        return EventTriggerType.EVENT_NONE_LITERAL;
    }

    public static String getExistingSetScriptGrammarId(Activity eventAct) {
        Deadline deadline = EventObjectUtil.getDeadline(eventAct);
        if (deadline == null) {
            return null;
        }
        Expression deadlineDuration = deadline.getDeadlineDuration();
        if (deadlineDuration == null) {
            return null;
        }
        return deadlineDuration.getScriptGrammar();
    }

    public static EventFlowType getFlowType(Activity act) {
        Event event = act.getEvent();

        EventFlowType result = null;

        if (event != null) {
            if (event instanceof StartEvent) {
                result = EventFlowType.FLOW_START_LITERAL;
            } else if (event instanceof IntermediateEvent) {
                result = EventFlowType.FLOW_INTERMEDIATE_LITERAL;
            } else if (event instanceof EndEvent) {
                result = EventFlowType.FLOW_END_LITERAL;
            } else {
                // default
                System.err.println("Unspecified event"); //$NON-NLS-1$
                result = EventFlowType.FLOW_START_LITERAL;
            }
        }

        return result;
    }

    /**
     * Get the target link activity id from an intermediate link event.
     * 
     * @param linkEvent
     * @return
     */
    public static String getLinkEventId(Activity linkEvent) {
        TriggerResultLink link = null;

        Event event = linkEvent.getEvent();

        if (event instanceof IntermediateEvent) {
            link = ((IntermediateEvent) event).getTriggerResultLink();
        }

        if (link != null) {
            return link.getName();
        }
        return null;

    }

    /**
     * Return <code>true</code> if the specified activity is a message start
     * event place inside an event subprocess, <code>false</code> otherwise.
     * 
     * @param activity
     * @return <code>true</code> if the specified activity is a message start
     *         event place inside an event subprocess, <code>false</code>
     *         otherwise.
     */
    public static boolean isEventSubProcessMessageStartEvent(Activity activity) {
        if (activity != null) {
            if (activity.getEvent() instanceof StartEvent) {

                EObject typeNode =
                        activity.getEvent().getEventTriggerTypeNode();

                boolean isCatchLinkEvent =
                        (typeNode instanceof TriggerResultLink && CatchThrow.CATCH
                                .equals(((TriggerResultLink) typeNode)
                                        .getCatchThrow()));

                if ((!isCatchLinkEvent)
                        && ((StartEvent) activity.getEvent())
                                .getTriggerResultMessage() != null) {
                    if (isEventSubProcessStartEvent(activity)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param activity
     * @return <code>true</code> if the start event is a start request (type
     *         none) event in an event sub-process.
     */
    public static boolean isEventSubProcessStartRequestEvent(Activity activity) {
        return EventObjectUtil.isEventSubProcessStartEvent(activity)
                && EventTriggerType.EVENT_NONE_LITERAL.equals(EventObjectUtil.getEventTriggerType(activity));
    }

    /**
     * Sid XPD-7124. Remove utility as is very simple for callers to interrogate
     * event types already and we don't need util for that.
     */

    /**
     * Return <code>true</code> if the specified activity is either an event
     * handler activity OR an event subprocess start event handler activity,
     * <code>false</code> otherwise.
     * 
     * @param act
     *            Activity to be checked.
     * @return <code>true</code> if the specified activity is either an event
     *         handler activity OR an event subprocess start event handler
     *         activity, <code>false</code> otherwise.
     */
    public static boolean isEventHandlerOrEventSubProcessStartEventActivity(
            Activity act) {

        return Xpdl2ModelUtil.isEventHandlerActivity(act)
                || isEventSubProcessStartEvent(act);
    }

    /**
     * Return <code>true</code> if the container of start event is an event sub
     * process, <code>false</code> otherwise.
     * 
     * @param act
     * @return <code>true</code> if the container of start event is an event sub
     *         process, <code>false</code> otherwise.
     */
    public static boolean isEventSubProcessStartEvent(Activity act) {

        /*
         * Check if it is a start event.
         */
        if (EventFlowType.FLOW_START_LITERAL.equals(EventObjectUtil
                .getFlowType(act))) {

            /*
             * Get the eContainer of the start event.
             */
            EObject container = act.eContainer();

            /*
             * Check if the start event activity is in this activity set.
             */
            if (container instanceof ActivitySet) {

                ActivitySet actSet = (ActivitySet) container;

                /*
                 * Check if this activity set is an event subprocess.
                 */

                Activity embSubProcActivityForActSet =
                        Xpdl2ModelUtil.getEmbSubProcActivityForActSet(act
                                .getProcess(), actSet.getId());

                return TaskType.EVENT_SUBPROCESS_LITERAL.equals(TaskObjectUtil
                        .getTaskTypeStrict(embSubProcActivityForActSet));
            }
        }

        return false;

    }

    /**
     * Return <code>true</code> if the specified start event activity is inside
     * an event sub process and is non-interrupting, <code>false</code>
     * otherwise.
     * 
     * @param act
     *            Start event activity.
     * @return <code>true</code> if the specified start event activity is inside
     *         an event sub process and is non-interrupting, <code>false</code>
     *         otherwise.
     */
    public static boolean isNonInterruptingEventSubProcessStartEvent(
            Activity act) {

        /*
         * Check if it's an event subprocess start event.
         */
        if (isEventSubProcessStartEvent(act)) {
            Event ev = act.getEvent();

            StartEvent startEv = (StartEvent) ev;

            /*
             * Check if it's non-interrupting.
             */

            return Xpdl2ModelUtil.getOtherAttributeAsBoolean(startEv,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_NonInterruptingEvent());
        }

        return false;
    }

    /**
     * Return a list of all error event activities in process
     * 
     * @param process
     * @return
     */
    public static Collection<Activity> getProcessErrorEvents(Process process) {
        Collection<Activity> events = EventObjectUtil.getProcessEvents(process);
        for (Iterator iterator = events.iterator(); iterator.hasNext();) {
            Activity event = (Activity) iterator.next();

            if (!(event.getEvent().getEventTriggerTypeNode() instanceof ResultError)) {
                iterator.remove();

            }
        }
        return events;
    }

    /**
     * Return a list of all event activities in process
     * 
     * @param process
     * @return
     */
    public static Collection<Activity> getProcessEvents(Process process) {
        List<Activity> events = new ArrayList<Activity>();

        events.addAll(getFlowContainerEvents(process));

        for (FlowContainer flowContainer : process.getActivitySets()) {
            events.addAll(getFlowContainerEvents(flowContainer));
        }
        return events;
    }

    // XPD-3661 Content assist Pop up in throw link event's property
    // section [Link To:],
    // should list only in scope catch link events.
    /**
     * returns the list of events for given flowcontainer[ActivitySet/Process]
     * 
     * @param flowContainer
     * @return list of event activities.
     */
    public static Collection<Activity> getFlowContainerEvents(
            FlowContainer flowContainer) {
        List<Activity> events = new ArrayList<Activity>();

        for (Iterator iterator = flowContainer.getActivities().iterator(); iterator
                .hasNext();) {
            Activity act = (Activity) iterator.next();

            if (act.getEvent() != null) {
                events.add(act);
            }
        }

        return events;
    }

    /**
     * Get a list of all link event activities in the process.
     * 
     * @param process
     * @return
     */
    public static Collection<Activity> getProcessLinkEvents(Process process) {
        Collection<Activity> events = getProcessEvents(process);
        for (Iterator iterator = events.iterator(); iterator.hasNext();) {
            Activity event = (Activity) iterator.next();

            if (!(event.getEvent().getEventTriggerTypeNode() instanceof TriggerResultLink)) {
                iterator.remove();

            }
        }
        return events;
    }

    /**
     * Get a list of all link event activities in the process.
     * 
     * @param process
     * @return
     */
    public static Collection<Activity> getProcessSignalEvents(Process process,
            ReturnCatchThrowTypes catchThrowTypes) {

        Collection<Activity> eventActs = getProcessEvents(process);

        for (Iterator iterator = eventActs.iterator(); iterator.hasNext();) {
            Activity activity = (Activity) iterator.next();

            Event event = activity.getEvent();

            if ((event.getEventTriggerTypeNode() instanceof TriggerResultSignal)) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) event.getEventTriggerTypeNode();

                if (ReturnCatchThrowTypes.CATCH_AND_THROW
                        .equals(catchThrowTypes)) {
                    // Caller wants both catch and throws
                    continue;
                }

                // Check whether catch/throw status is what user requested.
                boolean isThrow = false;

                if (event instanceof EndEvent) {
                    // Ends are always throws
                    isThrow = true;

                } else if (event instanceof IntermediateEvent) {
                    // Intermediate events are only throw if they say so.
                    if (CatchThrow.THROW.equals(signal.getCatchThrow())) {
                        isThrow = true;
                    }
                }

                if (ReturnCatchThrowTypes.CATCH_ONLY.equals(catchThrowTypes)
                        && !isThrow) {
                    continue;
                } else if (ReturnCatchThrowTypes.THROW_ONLY
                        .equals(catchThrowTypes) && isThrow) {
                    continue;
                }
            }

            // Doesn't fit criteria (wrong trigger type or Catch/Throw mode).
            iterator.remove();
        }
        return eventActs;
    }

    /**
     * Get the command to set the 'continue on timeout' flag.
     * <p>
     * Nominally, by default, BPMN specifies that when a Timer event attached to
     * task border times-out then the task it is attached to is interupted
     * (withdrawn).
     * <p>
     * In order to allow escalation without withdraw the process widget supports
     * an additional flag (continue on timeout), that when true, overrides this
     * behaviour and prevents task being interrupted.
     * <p>
     * This will only be called for Intermediate Timer Event on task boundary.
     * 
     * @param ed
     * @param isCOntinue
     * @return
     */
    public static Command getSetContinueOnTimeoutFlag(EditingDomain ed,
            Activity eventAct, boolean isContinue) {
        EObject o = eventAct.getEvent().getEventTriggerTypeNode();

        if (o instanceof TriggerTimer) {
            TriggerTimer tt = (TriggerTimer) o;

            return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    tt,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ContinueOnTimeout(),
                    new Boolean(isContinue));
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Get command to set the error code for an catch or throw error event.
     * 
     * @param ed
     * @param errorEvent
     * @param newValue
     * @return
     */
    public static Command getSetErrorCodeCommand(EditingDomain ed,
            Activity errorEvent, String newValue) {
        if (errorEvent.getEvent() instanceof EndEvent) {
            /* Use new utility for setting throw error end event error code. */
            return ThrowErrorEventUtil.getSetErrorCodeCommand(ed,
                    errorEvent,
                    newValue);
        }

        ResultError err = EventObjectUtil.getResultError(errorEvent);
        if (newValue != null) {
            newValue = newValue.trim();

            if (newValue.length() == 0) {
                newValue = null;
            }
        }

        return err == null ? UnexecutableCommand.INSTANCE : SetCommand
                .create(ed,
                        err,
                        Xpdl2Package.eINSTANCE.getResultError_ErrorCode(),
                        newValue);
    }

    /**
     * Get command to set the signal name for a signal event.
     * 
     * @param ed
     * @param signalEvent
     * @param newValue
     * @return
     */
    public static Command getSetSignalNameCommand(EditingDomain ed,
            Activity signalEvent, String newValue) {

        Event event = signalEvent.getEvent();
        if (event != null) {
            EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();

            if (eventTriggerTypeNode instanceof TriggerResultSignal) {
                TriggerResultSignal signal =
                        (TriggerResultSignal) eventTriggerTypeNode;

                if (newValue != null) {
                    newValue = newValue.trim();

                    if (newValue.length() == 0) {
                        newValue = null;
                    }
                }

                CompoundCommand cmd =
                        new CompoundCommand(
                                Messages.EventObjectUtil_SetSignalEventName_menu);
                cmd.append(SetCommand.create(ed,
                        signal,
                        Xpdl2Package.eINSTANCE.getTriggerResultSignal_Name(),
                        newValue));

                return cmd;
            }
        }

        return UnexecutableCommand.INSTANCE;

    }

    /**
     * Get the command to set the link id in the given link event.
     * 
     * @param ed
     * @param eventAct
     * @param linkEventId
     * @param processRef
     * @return
     */
    public static Command getSetEventLinkIdCommand(EditingDomain ed,
            Activity eventAct, String linkEventId, String processRef) {

        TriggerResultLink link = null;

        Event event = eventAct.getEvent();

        if (event instanceof IntermediateEvent) {
            link = ((IntermediateEvent) event).getTriggerResultLink();
        }
        if (link != null) {
            CompoundCommand cmd = new CompoundCommand();
            cmd.setLabel(Messages.Xpdl2EventAdapter_SetEventLink);

            cmd.append(SetCommand.create(ed,
                    link,
                    Xpdl2Package.eINSTANCE.getTriggerResultLink_Name(),
                    linkEventId));

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    public static Command getSetRuleNameCommand(EditingDomain ed,
            Activity eventAct, String newName) {
        return SetCommand.create(ed,
                EventObjectUtil.getTriggerConditional(eventAct),
                Xpdl2Package.eINSTANCE.getTriggerConditional_ConditionName(),
                newName);
    }

    public static Command getSetTimerTriggerModeCommand(EditingDomain ed,
            Activity eventAct, TimerTriggerMode mode) {
        TriggerTimer tt = EventObjectUtil.getTriggerTimer(eventAct);
        Activity act = eventAct;
        if (tt == null || act == null
                || mode == EventObjectUtil.getTimerTriggerMode(eventAct)) {
            return UnexecutableCommand.INSTANCE;
        }
        // We must be changing from one to the other so this logic can be quite
        // simple
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.Xpdl2EventAdapter_SetTriggerTimerMode);
        if (mode == TimerTriggerMode.CYCLE) {

            Expression timeCycleExpression;

            Expression timDateExpression = tt.getTimeDate();

            if (timDateExpression != null) {
                timeCycleExpression = EcoreUtil.copy(timDateExpression);

            } else {
                timeCycleExpression = Xpdl2Factory.eINSTANCE.createExpression();
                timeCycleExpression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                ""); //$NON-NLS-1$
            }
            cmd.append(SetCommand.create(ed,
                    tt,
                    Xpdl2Package.eINSTANCE.getTriggerTimer_TimeDate(),
                    null));

            cmd.append(SetCommand.create(ed,
                    tt,
                    Xpdl2Package.eINSTANCE.getTriggerTimer_TimeCycle(),
                    timeCycleExpression));
        } else {

            Expression timeDateExpression;

            Expression timeCycleExpression = tt.getTimeCycle();

            if (timeCycleExpression != null) {
                timeDateExpression = EcoreUtil.copy(timeCycleExpression);

            } else {
                timeDateExpression = Xpdl2Factory.eINSTANCE.createExpression();
                timeDateExpression
                        .getMixed()
                        .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                ""); //$NON-NLS-1$
            }
            cmd.append(SetCommand.create(ed,
                    tt,
                    Xpdl2Package.eINSTANCE.getTriggerTimer_TimeCycle(),
                    null));

            cmd.append(SetCommand.create(ed,
                    tt,
                    Xpdl2Package.eINSTANCE.getTriggerTimer_TimeDate(),
                    timeDateExpression));
        }

        return cmd;
    }

    public static Command getSetTriggerTimerDescCommand(EditingDomain ed,
            Activity eventAct, TimerTriggerMode mode, String newValue) {
        TriggerTimer tt = EventObjectUtil.getTriggerTimer(eventAct);
        if (tt == null)
            return UnexecutableCommand.INSTANCE;
        switch (mode) {
        case DATETIME:
            Expression timeDateExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            timeDateExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newValue);
            return SetCommand.create(ed,
                    tt,
                    Xpdl2Package.eINSTANCE.getTriggerTimer_TimeDate(),
                    timeDateExpression);
        case CYCLE:
            Expression timeCycleExpression =
                    Xpdl2Factory.eINSTANCE.createExpression();
            timeCycleExpression
                    .getMixed()
                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                            newValue);
            return SetCommand.create(ed,
                    tt,
                    Xpdl2Package.eINSTANCE.getTriggerTimer_TimeCycle(),
                    timeCycleExpression);
        default:
            return UnexecutableCommand.INSTANCE;
        }
    }

    public static TimerTriggerMode getTimerTriggerMode(Activity eventAct) {
        TriggerTimer tt = EventObjectUtil.getTriggerTimer(eventAct);
        if (tt == null)
            return TimerTriggerMode.DATETIME;
        if (tt.getTimeCycle() != null) {
            return TimerTriggerMode.CYCLE;
        } else {
            return TimerTriggerMode.DATETIME;
        }
    }

    public static TriggerConditional getTriggerConditional(Activity eventAct) {
        Event event = eventAct.getEvent();
        TriggerConditional triggerRule = null;
        if (event instanceof StartEvent) {
            triggerRule = ((StartEvent) event).getTriggerConditional();
        } else if (event instanceof IntermediateEvent) {
            triggerRule = ((IntermediateEvent) event).getTriggerConditional();
        }
        return triggerRule;
    }

    public static TriggerTimer getTriggerTimer(Activity eventAct) {
        Event event = eventAct.getEvent();
        TriggerTimer triggerTimer = null;
        if (event instanceof StartEvent) {
            triggerTimer = ((StartEvent) event).getTriggerTimer();
        } else if (event instanceof IntermediateEvent) {
            triggerTimer = ((IntermediateEvent) event).getTriggerTimer();
        }
        return triggerTimer;
    }

    /**
     * @param eventAct
     * 
     * @return If the event is a throw / catch signal event then this method
     *         returns the {@link TriggerResultSignal} associated with it else
     *         returns <code>null</code>
     */
    public static TriggerResultSignal getTriggerSignal(Activity eventAct) {
        Event event = eventAct.getEvent();
        TriggerResultSignal triggerSignal = null;
        if (event instanceof StartEvent) {
            triggerSignal = ((StartEvent) event).getTriggerResultSignal();
        } else if (event instanceof IntermediateEvent) {
            triggerSignal =
                    ((IntermediateEvent) event).getTriggerResultSignal();
        } else if (event instanceof EndEvent) {
            triggerSignal = ((EndEvent) event).getTriggerResultSignal();
        }
        return triggerSignal;
    }

    /**
     * Convenience method which returns {@link TriggerResultMessage} for a given
     * {@link Activity}. {@link TriggerResultMessage} is only available for
     * {@link Event}s of type Message.
     * 
     * @param eventAct
     * @return
     */
    public static TriggerResultMessage getTriggerResultMessage(Activity eventAct) {
        Event event = eventAct.getEvent();
        TriggerResultMessage triggerResultMessage = null;
        if (event instanceof StartEvent) {
            triggerResultMessage =
                    ((StartEvent) event).getTriggerResultMessage();
        } else if (event instanceof IntermediateEvent) {
            triggerResultMessage =
                    ((IntermediateEvent) event).getTriggerResultMessage();
        } else if (event instanceof EndEvent) {
            triggerResultMessage = ((EndEvent) event).getTriggerResultMessage();
        }
        return triggerResultMessage;
    }

    public static String getTriggerTimerDesc(Activity eventAct,
            TimerTriggerMode mode) {
        TriggerTimer tt = getTriggerTimer(eventAct);
        if (tt == null)
            return null;
        switch (mode) {
        case DATETIME:
            if (tt.getTimeDate() != null) {
                return tt.getTimeDate().getText();
            }
            return null;
        case CYCLE:
            if (tt.getTimeCycle() != null) {
                return tt.getTimeCycle().getText();
            }
            return null;
        default:
            return null;
        }
    }

    /**
     * Checks if the given event activity is attached to a task boundary
     * 
     * @param eventAct
     * @return <code>true</code> if the given event is attached to a task
     */
    public static boolean isAttachedToTask(Activity eventAct) {

        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                .equals(getFlowType(eventAct))) {
            String taskId =
                    ((IntermediateEvent) eventAct.getEvent()).getTarget();
            if (taskId != null) {
                return true;
            }
        }
        return false;
    }

    /** MR 37869 - begin */
    public static String getTaskIdAttachedTo(Activity eventAct) {
        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                .equals(getFlowType(eventAct))) {
            String taskId =
                    ((IntermediateEvent) eventAct.getEvent()).getTarget();
            if (null != taskId) {
                return taskId;
            }
        }
        return null;
    }

    /** MR 37869 - end */

    public static Activity getTaskAttachedTo(Activity eventAct) {
        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                .equals(getFlowType(eventAct))) {
            String taskId =
                    ((IntermediateEvent) eventAct.getEvent()).getTarget();
            if (null != taskId) {
                FlowContainer container = eventAct.getFlowContainer();
                if (container != null) {
                    return container.getActivity(taskId);
                }
            }
        }
        return null;
    }

    public static Deadline getDeadline(Activity eventAct) {
        EList list = eventAct.getDeadline();
        if (list != null && list.size() > 0) {
            return ((Deadline) list.get(0));
        }
        return null;
    }

    /**
     * Utility which returns {@link ResultError} for a given {@link Activity}.
     * {@link ResultError}s are only available if the element inspected is a
     * Intermediate Event of type Error of End Event of type Error.
     * 
     * @param errorEvent
     * @return
     */
    public static ResultError getResultError(Activity errorEvent) {
        Event event = errorEvent.getEvent();
        ResultError resultError = null;
        if (event instanceof IntermediateEvent) {
            resultError = ((IntermediateEvent) event).getResultError();
        } else if (event instanceof EndEvent) {
            resultError = ((EndEvent) event).getResultError();
        }
        return resultError;
    }

    /**
     * Clear out the Deadlines collection of a activity
     * 
     * @param ed
     * @param cmd
     * @param event
     */
    public static void removeAllDeadlines(EditingDomain ed,
            CompoundCommand cmd, Activity act) {
        EList deadlines = act.getDeadline();
        if (deadlines != null && deadlines.size() > 0) {
            cmd.append(RemoveCommand.create(ed,
                    act,
                    Xpdl2Package.eINSTANCE.getActivity_Deadline(),
                    deadlines));
        }
    }

    /**
     * @param ed
     * @param sourceObject
     * @param activity
     */
    public static Command getSetInverseCatchThrowOnLinkCommand(
            EditingDomain ed, Activity activity) {

        EventFlowType flowType = EventObjectUtil.getFlowType(activity);
        if (EventFlowType.FLOW_INTERMEDIATE_LITERAL.equals(flowType)) {
            IntermediateEvent intermediateEvent =
                    (IntermediateEvent) activity.getEvent();
            TriggerResultLink link = intermediateEvent.getTriggerResultLink();
            if (link != null) {
                CatchThrow catchThrow = link.getCatchThrow();
                if (CatchThrow.THROW.equals(catchThrow)) {
                    return SetCommand.create(ed,
                            link,
                            Xpdl2Package.eINSTANCE
                                    .getTriggerResultLink_CatchThrow(),
                            CatchThrow.CATCH);
                }
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    public static Command getChangeEventImplementationCommand(EditingDomain ed,
            Activity activity, String extImplementationType,
            String implementationTypeName) {
        CompoundCommand cmd = null;

        if (activity != null) {
            Event event = activity.getEvent();

            // Create the change service type command
            if (event != null) {
                if (event instanceof StartEvent) {
                    cmd =
                            getChangeEventStartImpl(ed,
                                    extImplementationType,
                                    implementationTypeName,
                                    activity);
                } else if (event instanceof IntermediateEvent) {
                    cmd =
                            getChangeEventIntermediateImpl(ed,
                                    extImplementationType,
                                    implementationTypeName,
                                    activity);
                } else if (event instanceof EndEvent) {
                    cmd =
                            getChangeEventEndImpl(ed,
                                    extImplementationType,
                                    implementationTypeName,
                                    activity);
                }
            }
        }

        // Clear all assignments
        if (cmd != null && activity.getAssignments() != null
                && !activity.getAssignments().isEmpty()) {
            cmd.append(SetCommand.create(ed,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Assignments(),
                    SetCommand.UNSET_VALUE));
        }

        return cmd;
    }

    /**
     * @param activity
     * 
     * @return Get implementation tyep of given event activity or null if not
     *         found.
     */
    public static ImplementationType getEventImplementationType(
            Activity activity) {
        if (activity.getEvent() instanceof StartEvent) {
            return ((StartEvent) activity.getEvent()).getImplementation();
        } else if (activity.getEvent() instanceof IntermediateEvent) {
            return ((IntermediateEvent) activity.getEvent())
                    .getImplementation();
        } else if (activity.getEvent() instanceof EndEvent) {
            return ((EndEvent) activity.getEvent()).getImplementation();
        }
        return null;
    }

    private static CompoundCommand getChangeEventStartImpl(EditingDomain ed,
            String extImplementationType, String implementationTypeName,
            Activity activity) {
        CompoundCommand cmd;
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_ChangeServiceTypeTask_menu);

        /*
         * Sid XPD-7359 - preserve CatchThrow attribute.
         */
        StartEvent oldEvent = (StartEvent) activity.getEvent();
        CatchThrow catchThrow = null;
        if (oldEvent.getTriggerResultMessage() != null) {
            if (oldEvent.getTriggerResultMessage().getCatchThrow() != null) {
                catchThrow = oldEvent.getTriggerResultMessage().getCatchThrow();
            }
        }

        // Create the StartEvent
        StartEvent event = Xpdl2Factory.eINSTANCE.createStartEvent();

        // Set implementation
        ImplementationType implementationType =
                ImplementationType.getByName(implementationTypeName);
        if (implementationType == null) {
            implementationType = ImplementationType.UNSPECIFIED_LITERAL;
        }
        event.setImplementation(implementationType);
        event.setTrigger(TriggerType.MESSAGE_LITERAL);

        TriggerResultMessage result =
                Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
        result.setCatchThrow(catchThrow);
        event.setTriggerResultMessage(result);

        // Set the message in and out
        result.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

        // set the new service name
        Xpdl2ModelUtil.setOtherAttribute(event, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ImplementationType(), extImplementationType);

        if (event != null) {
            // Add the set command to create new service task
            cmd.append(SetCommand.create(ed,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Event(),
                    event));
        }
        return cmd;
    }

    private static CompoundCommand getChangeEventIntermediateImpl(
            EditingDomain ed, String extImplementationType,
            String implementationTypeName, Activity activity) {
        CompoundCommand cmd;
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_ChangeServiceTypeTask_menu);

        IntermediateEvent oldEvent = (IntermediateEvent) activity.getEvent();
        CatchThrow catchThrow = CatchThrow.CATCH;
        if (oldEvent.getTriggerResultMessage() != null) {
            if (oldEvent.getTriggerResultMessage().getCatchThrow() != null) {
                catchThrow = oldEvent.getTriggerResultMessage().getCatchThrow();
            }
        }

        // Create the StartEvent
        IntermediateEvent event =
                Xpdl2Factory.eINSTANCE.createIntermediateEvent();

        // Set implementation
        ImplementationType implementationType =
                ImplementationType.getByName(implementationTypeName);
        if (implementationType == null) {
            implementationType = ImplementationType.UNSPECIFIED_LITERAL;
        }
        event.setImplementation(implementationType);
        event.setTrigger(TriggerType.MESSAGE_LITERAL);

        TriggerResultMessage result =
                Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
        event.setTriggerResultMessage(result);
        result.setCatchThrow(catchThrow);

        // Set the message in and out
        result.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

        // set the new service name
        Xpdl2ModelUtil.setOtherAttribute(event, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ImplementationType(), extImplementationType);

        if (event != null) {
            // Add the set command to create new service task
            cmd.append(SetCommand.create(ed,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Event(),
                    event));
        }
        return cmd;
    }

    private static CompoundCommand getChangeEventEndImpl(EditingDomain ed,
            String extImplementationType, String implementationTypeName,
            Activity activity) {
        CompoundCommand cmd;
        cmd = new CompoundCommand();
        cmd.setLabel(Messages.Xpdl2TaskAdapter_ChangeServiceTypeTask_menu);

        /*
         * Sid XPD-7359 - preserve CatchThrow attribute.
         */
        EndEvent oldEvent = (EndEvent) activity.getEvent();
        CatchThrow catchThrow = null;
        if (oldEvent.getTriggerResultMessage() != null) {
            if (oldEvent.getTriggerResultMessage().getCatchThrow() != null) {
                catchThrow = oldEvent.getTriggerResultMessage().getCatchThrow();
            }
        }

        // Create the StartEvent
        EndEvent event = Xpdl2Factory.eINSTANCE.createEndEvent();

        // Set implementation
        ImplementationType implementationType =
                ImplementationType.getByName(implementationTypeName);
        if (implementationType == null) {
            implementationType = ImplementationType.UNSPECIFIED_LITERAL;
        }
        event.setImplementation(implementationType);
        event.setResult(ResultType.MESSAGE_LITERAL);

        TriggerResultMessage result =
                Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
        result.setCatchThrow(catchThrow);
        event.setTriggerResultMessage(result);

        // Set the message in and out
        result.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

        // set the new service name
        Xpdl2ModelUtil.setOtherAttribute(event, XpdExtensionPackage.eINSTANCE
                .getDocumentRoot_ImplementationType(), extImplementationType);

        if (event != null) {
            // Add the set command to create new service task
            cmd.append(SetCommand.create(ed,
                    activity,
                    Xpdl2Package.eINSTANCE.getActivity_Event(),
                    event));
        }
        return cmd;
    }

    /**
     * Returns the command that unsets the Generated flag
     * 
     * @param ed
     * @param act
     * @return
     */
    public static Command getSetNotGeneratedCommand(EditingDomain ed,
            Activity act) {
        EventTriggerType eventTriggerType = getEventTriggerType(act);
        if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                .equals(eventTriggerType)) {
            TriggerResultMessage trm = null;
            Event event = act.getEvent();
            if (event instanceof StartEvent) {
                trm = ((StartEvent) event).getTriggerResultMessage();
            } else if (event instanceof IntermediateEvent) {
                trm = ((IntermediateEvent) event).getTriggerResultMessage();
            }
            return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    trm,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Generated(),
                    Boolean.FALSE);

        }
        return null;
    }

    public static Command getSetEventTriggerTypeCommand(EditingDomain ed,
            Activity activity, EventTriggerType eventType) {
        //
        // Just in case caller attempts to reset event type to same thing
        // (we don't want to reset all the detail under it.
        if (activity != null && activity.getEvent() != null) {
            EventTriggerType currEventType =
                    EventObjectUtil.getEventTriggerType(activity);

            if (!currEventType.equals(eventType)) {

                CompoundCommand cmd = new CompoundCommand();
                cmd.setLabel(Messages.Xpdl2EventAdapter_SetEventType);

                EventFlowType flowType = EventObjectUtil.getFlowType(activity);

                if (ReplyActivityUtil.isReplyActivity(activity)
                        && Xpdl2ModelUtil
                                .getDisplayName(activity)
                                .startsWith(ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL)) {
                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            "")); //$NON-NLS-1$
                }

                /**
                 * 
                 * START EVENT TYPE HANDLING...........
                 * 
                 */
                if (EventFlowType.FLOW_START_LITERAL.equals(flowType)) {
                    StartEvent event = (StartEvent) activity.getEvent();

                    TriggerType trigger = null;
                    EObject eventImpl =
                            ElementsFactory.createEventDetail(flowType,
                                    eventType,
                                    activity.getProcess());
                    EReference eventImplNode = null;

                    switch (eventType.getValue()) {
                    case EventTriggerType.EVENT_NONE:
                        trigger = TriggerType.NONE_LITERAL;
                        break;

                    case EventTriggerType.EVENT_MESSAGE_CATCH:
                        trigger = TriggerType.MESSAGE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getStartEvent_TriggerResultMessage();
                        break;

                    case EventTriggerType.EVENT_TIMER:
                        trigger = TriggerType.TIMER_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getStartEvent_TriggerTimer();
                        break;
                    case EventTriggerType.EVENT_CONDITIONAL:
                        trigger = TriggerType.CONDITIONAL_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getStartEvent_TriggerConditional();
                        break;
                    case EventTriggerType.EVENT_SIGNAL_CATCH:
                        trigger = TriggerType.SIGNAL_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getStartEvent_TriggerResultSignal();
                        break;
                    case EventTriggerType.EVENT_MULTIPLE_CATCH:
                        trigger = TriggerType.MULTIPLE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getStartEvent_TriggerMultiple();
                        break;
                    }

                    if (trigger != null) {
                        cmd.append(SetCommand.create(ed,
                                event,
                                Xpdl2Package.eINSTANCE.getStartEvent_Trigger(),
                                trigger));

                        // Remove existing event type specific child
                        switch (currEventType.getValue()) {
                        case EventTriggerType.EVENT_MESSAGE_CATCH:
								/*
								 * Sid XPD-6836 If changing from Catch message to plain incoming-request type, then copy
								 * any event-handler properties to the correct location for incoming request
								 */
								moveEventHandlerPropertiesToIncomingRequest(ed, cmd, activity, event,
										event.getEventTriggerTypeNode());

                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getStartEvent_TriggerResultMessage(),
                                    null));

                            /*
                             * When switching away from message type remove
                             * implementation type.
                             */
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getStartEvent_Implementation(),
                                    SetCommand.UNSET_VALUE));
                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            event,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType(),
                                            null));
                            /* clear performers */
                            EObject[] emptyArray = new EObject[] {};
                            cmd.append(TaskObjectUtil
                                    .getSetPerformersCommand(ed,
                                            activity,
                                            emptyArray));

                            break;
                        case EventTriggerType.EVENT_TIMER:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getStartEvent_TriggerTimer(),
                                    null));
                            // Timer uses the deadline, so null that too.
                            EventObjectUtil.removeAllDeadlines(ed,
                                    cmd,
                                    activity);
                            break;
                        case EventTriggerType.EVENT_CONDITIONAL:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getStartEvent_TriggerConditional(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_SIGNAL_CATCH:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getStartEvent_TriggerResultSignal(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_MULTIPLE_CATCH:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getStartEvent_TriggerMultiple(),
                                    null));
                            break;
                        }

                        // Remove any associated correlation data
                        EStructuralFeature correlation =
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedCorrelationFields();
                        Object other =
                                Xpdl2ModelUtil.getOtherElement(activity,
                                        correlation);
                        if (other != null) {
                            cmd.append(Xpdl2ModelUtil
                                    .getRemoveOtherElementCommand(ed,
                                            activity,
                                            correlation,
                                            other));
                        }

                        // Event has BOTH an enumerated trigger attribute AND
                        // a child element for the EventType.
                        // So as to make things consistent with the way we
                        // handle
                        // Task types we will also create an appropriate (empty)
                        // event type specific child node.
                        if (eventImplNode != null) {
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    eventImplNode,
                                    eventImpl));
                            // Timer uses the deadline on the activity, so
                            // initialise that too
                            if (eventType
                                    .equals(EventTriggerType.EVENT_TIMER_LITERAL)) {
                                EventObjectUtil.removeAllDeadlines(ed,
                                        cmd,
                                        activity);
                                cmd.append(AddCommand.create(ed,
                                        activity,
                                        Xpdl2Package.eINSTANCE
                                                .getActivity_Deadline(),
                                        ElementsFactory.createDeadline()));

                            } else if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                                    .equals(eventType)
                                    || EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                            .equals(eventType)) {
                                /*
                                 * When switching to message type add
                                 * implementation type.
                                 */
                                cmd.append(SetCommand
                                        .create(ed,
                                                event,
                                                Xpdl2Package.eINSTANCE
                                                        .getStartEvent_Implementation(),
                                                ImplementationType.WEB_SERVICE_LITERAL));
                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(ed,
                                                event,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType(),
                                                TaskImplementationTypeDefinitions.WEB_SERVICE));

                            }
                        }

                        appendSetResourcePatternsCommand(ed,
                                activity,
                                eventType,
                                cmd);
                        appendAdditionalSetEventTypeCommand(ed,
                                cmd,
                                activity,
                                eventType,
                                currEventType,
                                eventImpl);
                        return cmd;
                    }

                }
                /**
                 * 
                 * INTERMEDIATE EVENT TYPE HANDLING...........
                 * 
                 */
                else if (EventFlowType.FLOW_INTERMEDIATE_LITERAL
                        .equals(flowType)) {
                    IntermediateEvent event =
                            (IntermediateEvent) activity.getEvent();

                    TriggerType trigger = null;
                    EObject eventImpl =
                            ElementsFactory.createEventDetail(flowType,
                                    eventType,
                                    activity.getProcess());
                    EReference eventImplNode = null;

                    switch (eventType.getValue()) {
                    case EventTriggerType.EVENT_NONE:
                        trigger = TriggerType.NONE_LITERAL;
                        break;
                    case EventTriggerType.EVENT_CANCEL:
                        trigger = TriggerType.CANCEL_LITERAL;
                        break;

                    case EventTriggerType.EVENT_MESSAGE_CATCH:
                        trigger = TriggerType.MESSAGE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultMessage();
                        break;
                    case EventTriggerType.EVENT_MESSAGE_THROW:
                        trigger = TriggerType.MESSAGE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultMessage();
                        break;
                    case EventTriggerType.EVENT_TIMER:
                        trigger = TriggerType.TIMER_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerTimer();
                        break;
                    case EventTriggerType.EVENT_CONDITIONAL:
                        trigger = TriggerType.CONDITIONAL_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerConditional();
                        break;
                    case EventTriggerType.EVENT_LINK_CATCH:
                        trigger = TriggerType.LINK_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultLink();
                        break;
                    case EventTriggerType.EVENT_LINK_THROW:
                        trigger = TriggerType.LINK_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultLink();
                        break;
                    case EventTriggerType.EVENT_MULTIPLE_CATCH:
                        trigger = TriggerType.MULTIPLE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerIntermediateMultiple();
                        break;
                    case EventTriggerType.EVENT_MULTIPLE_THROW:
                        trigger = TriggerType.MULTIPLE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerIntermediateMultiple();
                        break;
                    case EventTriggerType.EVENT_ERROR:
                        trigger = TriggerType.ERROR_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_ResultError();
                        break;
                    case EventTriggerType.EVENT_COMPENSATION_CATCH:
                        trigger = TriggerType.COMPENSATION_LITERAL;

                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultCompensation();
                        break;
                    case EventTriggerType.EVENT_COMPENSATION_THROW:
                        trigger = TriggerType.COMPENSATION_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultCompensation();
                        break;

                    case EventTriggerType.EVENT_SIGNAL_CATCH:
                        trigger = TriggerType.SIGNAL_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultSignal();
                        break;
                    case EventTriggerType.EVENT_SIGNAL_THROW:
                        trigger = TriggerType.SIGNAL_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_TriggerResultSignal();
                        /*
                         * When changing to throw-signal event, we set the
                         * “disable implicit association” flag in order to
                         * prevent (by default) ALL data being included in every
                         * throw-signal payload.
                         */
                        if (!hasExplicitAssociations(activity)) {
                            cmd.append(setImplicitAssociationDisabledCommand(ed,
                                    activity,
                                    true));
                        }
                        break;
                    }

                    if (trigger != null) {
                        cmd.append(SetCommand.create(ed,
                                event,
                                Xpdl2Package.eINSTANCE
                                        .getIntermediateEvent_Trigger(),
                                trigger));

                        // Remove existing child.
                        switch (currEventType.getValue()) {
                        case EventTriggerType.EVENT_MESSAGE_CATCH:
								/*
								 * Sid XPD-6836 If changing from Catch message to plain incoming-request type, then copy
								 * any event-handler properties to the correct location for incoming request
								 */
								moveEventHandlerPropertiesToIncomingRequest(ed, cmd, activity, event,
										event.getEventTriggerTypeNode());

								// Intentionally fall through to message-throw code...
                        case EventTriggerType.EVENT_MESSAGE_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerResultMessage(),
                                    null));
                            /*
                             * When switching away from message type remove
                             * implementation type.
                             */
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_Implementation(),
                                    SetCommand.UNSET_VALUE));
                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            event,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType(),
                                            null));

                            break;
                        case EventTriggerType.EVENT_TIMER:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerTimer(),
                                    null));
                            // Timer uses the deadline, so null that too.
                            EventObjectUtil.removeAllDeadlines(ed,
                                    cmd,
                                    activity);
                            break;
                        case EventTriggerType.EVENT_CONDITIONAL:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerConditional(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_LINK_CATCH:
                        case EventTriggerType.EVENT_LINK_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerResultLink(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_MULTIPLE_CATCH:
                        case EventTriggerType.EVENT_MULTIPLE_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerIntermediateMultiple(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_ERROR:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_ResultError(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_COMPENSATION_CATCH:
                        case EventTriggerType.EVENT_COMPENSATION_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerResultCompensation(),
                                    null));
                            break;

                        case EventTriggerType.EVENT_SIGNAL_CATCH:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerResultSignal(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_SIGNAL_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getIntermediateEvent_TriggerResultSignal(),
                                    null));
                            /*
                             * When changing from throw signal event, unset the
                             * “disable implicit association” flag to do the
                             * opposite of what we did when setting TO throw
                             * signal.
                             */
                            cmd.append(setImplicitAssociationDisabledCommand(ed,
                                    activity,
                                    false));
                            break;
                        }

                        // Event has BOTH an enumerated trigger attribute AND
                        // a child element for the EventType.
                        // So as to make things consistent with the way we
                        // handle
                        // Task types we will also create an appropriate (empty)
                        // event type specific child node.
                        //
                        // It is this child that will be given to an
                        // implementation
                        // plug-in to fill in the specific detail.
                        if (eventImplNode != null) {
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    eventImplNode,
                                    eventImpl));
                            // Timer uses the deadline on the activity, so
                            // initialise that too
                            if (eventType
                                    .equals(EventTriggerType.EVENT_TIMER_LITERAL)) {
                                EventObjectUtil.removeAllDeadlines(ed,
                                        cmd,
                                        activity);
                                cmd.append(AddCommand.create(ed,
                                        activity,
                                        Xpdl2Package.eINSTANCE
                                                .getActivity_Deadline(),
                                        ElementsFactory.createDeadline()));

                            } else if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                                    .equals(eventType)
                                    || EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                            .equals(eventType)) {
                                /*
                                 * When switching to message type add
                                 * implementation type.
                                 */
                                cmd.append(SetCommand
                                        .create(ed,
                                                event,
                                                Xpdl2Package.eINSTANCE
                                                        .getIntermediateEvent_Implementation(),
                                                ImplementationType.WEB_SERVICE_LITERAL));
                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(ed,
                                                event,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType(),
                                                TaskImplementationTypeDefinitions.WEB_SERVICE));

                            }

                        }

                        appendSetResourcePatternsCommand(ed,
                                activity,
                                eventType,
                                cmd);
                        appendAdditionalSetEventTypeCommand(ed,
                                cmd,
                                activity,
                                eventType,
                                currEventType,
                                eventImpl);
                        return cmd;
                    }
                }
                /**
                 * 
                 * END EVENT TYPE HANDLING...........
                 * 
                 */
                else if (EventFlowType.FLOW_END_LITERAL.equals(flowType)) {
                    EndEvent event = (EndEvent) activity.getEvent();

                    ResultType result = null;
                    EObject eventImpl =
                            ElementsFactory.createEventDetail(flowType,
                                    eventType,
                                    activity.getProcess());
                    EReference eventImplNode = null;

                    switch (eventType.getValue()) {
                    case EventTriggerType.EVENT_NONE:
                        result = ResultType.NONE_LITERAL;
                        break;
                    case EventTriggerType.EVENT_CANCEL:
                        result = ResultType.CANCEL_LITERAL;
                        break;
                    case EventTriggerType.EVENT_TERMINATE:
                        result = ResultType.TERMINATE_LITERAL;
                        break;

                    case EventTriggerType.EVENT_MESSAGE_THROW:
                        result = ResultType.MESSAGE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getEndEvent_TriggerResultMessage();
                        break;
                    case EventTriggerType.EVENT_SIGNAL_THROW:
                        result = ResultType.SIGNAL_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getEndEvent_TriggerResultSignal();
                        /*
                         * When changing to throw-signal event, we set the
                         * “disable implicit association” flag in order to
                         * prevent (by default) ALL data being included in every
                         * throw-signal payload.
                         */
                        if (!hasExplicitAssociations(activity)) {
                            cmd.append(setImplicitAssociationDisabledCommand(ed,
                                    activity,
                                    true));
                        }
                        break;
                    case EventTriggerType.EVENT_MULTIPLE_THROW:
                        result = ResultType.MULTIPLE_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getEndEvent_ResultMultiple();
                        break;
                    case EventTriggerType.EVENT_ERROR:
                        result = ResultType.ERROR_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getEndEvent_ResultError();
                        break;
                    case EventTriggerType.EVENT_COMPENSATION_THROW:
                        result = ResultType.COMPENSATION_LITERAL;
                        eventImplNode =
                                Xpdl2Package.eINSTANCE
                                        .getEndEvent_TriggerResultCompensation();
                        break;
                    }

                    if (result != null) {
                        cmd.append(SetCommand.create(ed,
                                event,
                                Xpdl2Package.eINSTANCE.getEndEvent_Result(),
                                result));

                        // Remove existing event type specific child
                        switch (currEventType.getValue()) {
                        case EventTriggerType.EVENT_MESSAGE_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getEndEvent_TriggerResultMessage(),
                                    null));
                            /*
                             * When switching away from message type remove
                             * implementation type.
                             */
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getEndEvent_Implementation(),
                                    SetCommand.UNSET_VALUE));
                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(ed,
                                            event,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType(),
                                            null));

                            break;

                        case EventTriggerType.EVENT_SIGNAL_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getEndEvent_TriggerResultSignal(),
                                    null));
                            /*
                             * When changing from throw signal event, unset the
                             * “disable implicit association” flag to do the
                             * opposite of what we did when setting TO throw
                             * signal.
                             */
                            cmd.append(setImplicitAssociationDisabledCommand(ed,
                                    activity,
                                    false));
                            break;
                        case EventTriggerType.EVENT_ERROR:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getEndEvent_ResultError(),
                                    null));
                            break;
                        case EventTriggerType.EVENT_COMPENSATION_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getEndEvent_TriggerResultCompensation(),
                                    null));
                            break;

                        case EventTriggerType.EVENT_MULTIPLE_THROW:
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    Xpdl2Package.eINSTANCE
                                            .getEndEvent_ResultMultiple(),
                                    null));
                            break;
                        }

                        // Event has BOTH an enumerated trigger attribute AND
                        // a child element for the EventType.
                        // So as to make things consistent with the way we
                        // handle
                        // Task types we will also create an appropriate (empty)
                        // event type specific child node.
                        //
                        // It is this child that will be given to an
                        // implementation
                        // plug-in to fill in the specific detail.
                        if (eventImplNode != null) {
                            cmd.append(SetCommand.create(ed,
                                    event,
                                    eventImplNode,
                                    eventImpl));

                            if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                                    .equals(eventType)
                                    || EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                                            .equals(eventType)) {
                                /*
                                 * When switching to message type add
                                 * implementation type.
                                 */
                                cmd.append(SetCommand.create(ed,
                                        event,
                                        Xpdl2Package.eINSTANCE
                                                .getEndEvent_Implementation(),
                                        ImplementationType.WEB_SERVICE_LITERAL));
                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(ed,
                                                event,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ImplementationType(),
                                                TaskImplementationTypeDefinitions.WEB_SERVICE));

                            }

                        }

                        appendSetResourcePatternsCommand(ed,
                                activity,
                                eventType,
                                cmd);

                        appendAdditionalSetEventTypeCommand(ed,
                                cmd,
                                activity,
                                eventType,
                                currEventType,
                                eventImpl);

                        return cmd;
                    }

                }

            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
	 * Sid XPD-6836 Copy any event-handler properties from the current event trigger node to the correct location for
	 * incoming request
	 * 
	 * @param ed
	 * @param cmd
	 * @param activity
	 * @param eventTriggerTypeNode
	 */
	private static void moveEventHandlerPropertiesToIncomingRequest(EditingDomain ed, CompoundCommand cmd,
			Activity activity,
			Event eventElement,
			EObject eventTriggerTypeNode)
	{
		if (eventTriggerTypeNode instanceof TriggerResultMessage)
		{
			/* xpdExtEventHandlerInitialisers moves from TriggerResultMessage to xpdl:Activity */
			EventHandlerInitialisers initialisers = (EventHandlerInitialisers) Xpdl2ModelUtil.getOtherElement(
					(TriggerResultMessage) eventTriggerTypeNode,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers());

			if (initialisers != null)
			{
				/* Copy the element and add it directly to the activity (safer to copy than re-parenting) */
				EventHandlerInitialisers initialisersCopy = EcoreUtil.copy(initialisers);

				cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed, activity,
						XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerInitialisers(), initialisersCopy));
			}

			/*
			 * xpdExt:EventHandlerFlowStrategy attribute moves from TriggerResultMessage to either
			 * xpdl:Activity/xpdl:Event/xpdl:Startevent | xpdl:IntermediateEvent
			 */
			Object flowStrategy = Xpdl2ModelUtil.getOtherAttribute((TriggerResultMessage) eventTriggerTypeNode,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy());

			cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed, eventElement,
					XpdExtensionPackage.eINSTANCE.getDocumentRoot_EventHandlerFlowStrategy(), flowStrategy));
		}
	}

	/**
	 * @param ed
	 * @param eventType
	 * @param cmd
	 */
    private static void appendSetResourcePatternsCommand(EditingDomain ed,
            Activity activity, EventTriggerType eventType, CompoundCommand cmd) {

        ActivityResourcePatterns patterns = null;
        Object other =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());
        if (other instanceof ActivityResourcePatterns) {
            patterns = (ActivityResourcePatterns) other;
        }
        if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL.equals(eventType)
                || EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                        .equals(eventType)) {
            // XPDL21 event message, handle catch and throw.
            if (patterns == null) {
                patterns =
                        XpdExtensionFactory.eINSTANCE
                                .createActivityResourcePatterns();
                AllocationStrategy strategy =
                        XpdExtensionFactory.eINSTANCE
                                .createAllocationStrategy();
                patterns.setAllocationStrategy(strategy);
                strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns(),
                        patterns));
            } else {
                AllocationStrategy strategy = patterns.getAllocationStrategy();
                if (strategy == null) {
                    strategy =
                            XpdExtensionFactory.eINSTANCE
                                    .createAllocationStrategy();
                    strategy.setStrategy(AllocationStrategyType.SYSTEM_DETERMINED);
                    cmd.append(SetCommand.create(ed,
                            patterns,
                            XpdExtensionPackage.eINSTANCE
                                    .getActivityResourcePatterns_AllocationStrategy(),
                            strategy));
                } else {
                    if (!strategy.isSetStrategy()) {
                        cmd.append(SetCommand.create(ed,
                                strategy,
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Strategy(),
                                AllocationStrategyType.SYSTEM_DETERMINED));
                    }
                    if (strategy.isSetOffer()) {
                        cmd.append(SetCommand.create(ed,
                                strategy,
                                XpdExtensionPackage.eINSTANCE
                                        .getAllocationStrategy_Offer(),
                                SetCommand.UNSET_VALUE));
                    }
                }
            }
        } else if (patterns != null) {
            cmd.append(Xpdl2ModelUtil.getRemoveOtherElementCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ActivityResourcePatterns(),
                    patterns));
        }
    }

    private static void appendAdditionalSetEventTypeCommand(
            EditingDomain editingDomain, CompoundCommand ccmd,
            Activity activity, EventTriggerType newTriggerType,
            EventTriggerType currTriggerType, EObject eventImpl) {

        /*
         * Sort name out - if name is default (ish) for current type then change
         * to default name for new type.
         */
        String currentName = Xpdl2ModelUtil.getDisplayName(activity);
        String defaultNameForCurrentTriggerType =
                ElementsFactory
                        .getDefaultNameForEventType(getFlowType(activity),
                                currTriggerType);
        String defaultNameForNewTriggerType =
                ElementsFactory
                        .getDefaultNameForEventType(getFlowType(activity),
                                newTriggerType);

        if (ReplyActivityUtil.isReplyActivity(activity)
                && currentName
                        .startsWith(ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL)) {
            /* If it's a reply activity event with default name. */
            ccmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName(),
                            defaultNameForNewTriggerType));

        } else {
            /*
             * If current name is default name for current type then change
             * event name to match new type. Create a command that resets the
             * activity name to given name when executed (ensuring it's unique).
             */
            if (currentName.startsWith(defaultNameForCurrentTriggerType)) {
                String newName =
                        Xpdl2ModelUtil
                                .getUniqueDisplayNameInSet(defaultNameForNewTriggerType,
                                        Xpdl2ModelUtil
                                                .getAllActivitiesInProc(activity
                                                        .getProcess()),
                                        true);

                ccmd.append(Xpdl2ModelUtil
                        .getSetOtherAttributeCommand(editingDomain,
                                activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName(),
                                newName));
            }
        }

        if (EventTriggerType.EVENT_MESSAGE_THROW_LITERAL == newTriggerType) {
            appendWebServiceImplementationCommands(editingDomain,
                    ccmd,
                    activity,
                    newTriggerType);

            /*
             * Replace laebl with "Reply To: xxx" label if current = default for
             * current type.
             */
            if (currentName
                    .startsWith(ReplyActivityUtil.REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL)
                    || currentName.startsWith(defaultNameForCurrentTriggerType)) {
                if (eventImpl instanceof TriggerResultMessage) {
                    String requestActivityId =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute((TriggerResultMessage) eventImpl,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ReplyToActivityId());
                    if (requestActivityId != null
                            && requestActivityId.length() > 0) {
                        Activity requestActivity =
                                Xpdl2ModelUtil.getActivityById(activity
                                        .getProcess(), requestActivityId);
                        if (requestActivity != null) {
                            String replyToLabel =
                                    ReplyActivityUtil
                                            .getReplyToLabel(requestActivity,
                                                    activity);
                            if (replyToLabel != null
                                    && replyToLabel.length() > 0) {
                                ccmd.append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                activity,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_DisplayName(),
                                                replyToLabel));
                            }
                        }
                    }
                }
            }

        }

        return;
    }

    /**
     * 
     * @param editingDomain
     * @param activity
     * @param eventTriggerType
     */
    private static void appendWebServiceImplementationCommands(
            EditingDomain editingDomain, CompoundCommand ccmd,
            Activity activity, EventTriggerType eventTriggerType) {
        if (EventTriggerType.EVENT_MESSAGE_CATCH_LITERAL
                .equals(eventTriggerType)
                || EventTriggerType.EVENT_MESSAGE_THROW_LITERAL
                        .equals(eventTriggerType)) {
            Event event = activity.getEvent();

            if (event instanceof StartEvent) {
                ccmd.append(SetCommand.create(editingDomain,
                        event,
                        Xpdl2Package.eINSTANCE.getStartEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));
            } else if (event instanceof IntermediateEvent) {
                ccmd.append(SetCommand.create(editingDomain,
                        event,
                        Xpdl2Package.eINSTANCE
                                .getIntermediateEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));
            } else if (event instanceof EndEvent) {
                ccmd.append(SetCommand.create(editingDomain,
                        event,
                        Xpdl2Package.eINSTANCE.getEndEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));
            }

            ccmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            event,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType(),
                            TaskImplementationTypeDefinitions.WEB_SERVICE));
        }
        return;
    }

    /**
     * If the event is a timer event and has timer expression set to constant
     * period script then return the constant period definition element
     * 
     * @param activity
     * @return the constant period definition element or null if not a constant
     *         period timer
     */
    public static ConstantPeriod getTimerEventConstantPeriodScript(
            Activity activity) {
        if (EventTriggerType.EVENT_TIMER_LITERAL
                .equals(getEventTriggerType(activity))) {
            EList<Deadline> deadlines = activity.getDeadline();
            if (deadlines != null && deadlines.size() > 0) {
                Deadline deadline = deadlines.get(0);

                Expression deadlineDuration = deadline.getDeadlineDuration();
                if (deadlineDuration != null) {
                    if (CONSTANTPERIOD_SCRIPTGRAMMAR.equals(deadlineDuration
                            .getScriptGrammar())) {
                        Object cp =
                                deadlineDuration
                                        .getMixed()
                                        .get(XpdExtensionPackage.eINSTANCE.getDocumentRoot_ConstantPeriod(),
                                                false);
                        if (cp instanceof List) {
                            List list = (List) cp;
                            if (list.size() > 0) {
                                cp = list.get(0);
                            }
                        }

                        if (cp instanceof ConstantPeriod) {
                            return (ConstantPeriod) cp;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param event
     * @return <code>true</code> if the given event is web-service related.
     */
    public static boolean isWebServiceRelatedEvent(Activity activity) {
        /* Is a message event... */
        if (activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerResultMessage) {
            Object type =
                    Xpdl2ModelUtil.getOtherAttribute(activity.getEvent(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            if (TaskImplementationTypeDefinitions.WEB_SERVICE.equals(type)) {

                return true;
            }
        }
        /* Is a throw error end event related to a WSD fault message */
        else if (ThrowErrorEventUtil.isThrowFaultMessageErrorEvent(activity)) {
            return true;
        }
        /*
         * Is a catch error event on a web service task boundary which catches
         * specific error (i.e.e a fault message)
         */
        else if (EventObjectUtil.isAttachedToTask(activity)) {
            if (EventTriggerType.EVENT_ERROR_LITERAL.equals(EventObjectUtil
                    .getEventTriggerType(activity))) {

                if (ErrorCatchType.CATCH_SPECIFIC.equals(BpmnCatchableErrorUtil
                        .getCatchType(activity))) {

                    Object errorThrower =
                            BpmnCatchableErrorUtil.getErrorThrower(activity);
                    if (errorThrower instanceof Activity) {
                        if (ImplementationType.WEB_SERVICE_LITERAL
                                .equals(TaskObjectUtil
                                        .getTaskImplementationType((Activity) errorThrower))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Find all throw signal events that throw signals that can be caught by the
     * given catch signal event. In other words that... <li>Are in scope of
     * (visible to) the given catch signal (anything on the same
     * process/embedded-sub-process hierarchy tree 'thread').</li> <li>Throw THE
     * (if catches specific signal name) or A signal (if catch event catches
     * ALL).</li>
     * 
     * @param catchSignal
     * 
     * @return collection of throw signal event activities, empty collection if
     *         none found.
     */
    public static Collection<Activity> getThrowSignalsInScope(
            Activity catchSignal) {
        Collection<Activity> throwSignals = Collections.emptySet();

        /*
         * Sid XPD-6808: Event sub-processes are effectively treated as event
         * handlers (i.e. not contained in embedded sub-process in the BPEL) and
         * therefor the catch signals directly in event sub-proesses are at
         * 'whole process scope' and should be treated as such.
         */
        boolean isParentEventSubProcess = false;
        if (catchSignal.getFlowContainer() instanceof ActivitySet) {
            Activity embdSubProcActivity =
                    Xpdl2ModelUtil.getEmbSubProcActivityForActSet(catchSignal
                            .getProcess(), ((ActivitySet) catchSignal
                            .getFlowContainer()).getId());

            if (embdSubProcActivity != null) {
                isParentEventSubProcess =
                        Xpdl2ModelUtil.isEventSubProcess(embdSubProcActivity);
            }
        }

        if (catchSignal.getFlowContainer() instanceof Process
                || isParentEventSubProcess) {
            /*
             * If the catch is at top level of process then all throws are
             * visible because they are either siblings at same level of process
             * or they are in child embedded of the top level of process.
             */
            throwSignals =
                    (EventObjectUtil.getProcessSignalEvents(catchSignal
                            .getProcess(), ReturnCatchThrowTypes.THROW_ONLY));

        } else if (catchSignal.getFlowContainer() instanceof ActivitySet) {
            ActivitySet activitySet = (ActivitySet) catchSignal.eContainer();
            /*
             * If catch is in emb sub-proc then all the throws at this level are
             * visible, all the throws in acncetor emb's or process are visible
             * and all throws in child emb's of our parent emb are visible.
             */
            HashSet<Activity> inScope = new HashSet<Activity>();
            throwSignals = inScope;

            /*
             * Get the activity that is the emb-sub-proc parent of the catch
             * signal
             */
            Activity embeddedSubProcAct =
                    Xpdl2ModelUtil.getEmbSubProcActivityForActSet(catchSignal
                            .getProcess(), activitySet.getId());

            /*
             * Get all siblings (children of our parent) / and child emb
             * sub-process throw signal events
             */
            addSignalThrowers(inScope,
                    Xpdl2ModelUtil
                            .getAllActivitiesInEmbeddedSubProc(embeddedSubProcAct));

            /*
             * Go back thru the ANCESTORS of our parent emb-sub-process and
             * include their throw signals (because they are thrown ABOVE our
             * catch) BUT NOT children of other embedded sub-processes (becuase
             * they are thrown from 'beside' our catch.
             */
            EObject parent = embeddedSubProcAct.eContainer();
            while (parent != null) {
                if (parent instanceof ActivitySet) {
                    /*
                     * Add all the signal throwers above us but not drill down
                     * into embedded sub-processes that were next to our parent
                     * emb-sub-proc
                     */
                    addSignalThrowers(inScope,
                            ((ActivitySet) parent).getActivities());

                    Activity actForEmbSubProc =
                            Xpdl2ModelUtil
                                    .getEmbSubProcActivityForActSet(catchSignal
                                            .getProcess(),
                                            ((ActivitySet) parent).getId());

                    if (actForEmbSubProc != null) {
                        parent = actForEmbSubProc.eContainer();
                    }

                } else if (parent instanceof Process) {

                    Process process = ((Process) parent);
                    /*
                     * get all activities in scope of process. Note: XPD-6808 -
                     * Here an activity in the scope of process are all
                     * activities which are directly in the process + all those
                     * activities which are directly inside the event sub
                     * process contained by that process.
                     */
                    Set<Activity> activitiesInProcessScopeOfCatchEvent =
                            getAllActivitesInScopeOfProcess(process);

                    if (!activitiesInProcessScopeOfCatchEvent.isEmpty()) {
                        addSignalThrowers(inScope,
                                activitiesInProcessScopeOfCatchEvent);
                    }

                    parent = null;
                }
            }
        }

        /*
         * If the catch Signal event is not Catch all, then filter out any throw
         * signals that don't throw the same signal name.
         */
        String catchSignalName = getSignalName(catchSignal);
        if (catchSignalName != null && !catchSignalName.isEmpty()) {

            for (Iterator iterator = throwSignals.iterator(); iterator
                    .hasNext();) {
                Activity activity = (Activity) iterator.next();

                if (!catchSignalName.equals(getSignalName(activity))) {
                    iterator.remove();
                }
            }
        }

        return throwSignals;
    }

    /**
     * 
     * @param process
     *            the process under consideration (whose scope activities are to
     *            be fetched)
     * @return Set of all the Activities which are directly present in the
     *         process or directly present in the event sub process contained in
     *         the process.
     */
    private static Set<Activity> getAllActivitesInScopeOfProcess(Process process) {

        Set<Activity> activitiesInProcessScope = new HashSet<Activity>();
        /*
         * all Activities directly under the parent process will always be in
         * its scope
         */
        EList<Activity> activities = process.getActivities();

        for (Activity eachActivity : activities) {

            TaskType taskTypeStrict =
                    TaskObjectUtil.getTaskTypeStrict(eachActivity);

            if (TaskType.EVENT_SUBPROCESS_LITERAL.equals(taskTypeStrict)) {
                /*
                 * If activity is an event sub process then add all the
                 * activities present in it as they will also fall in the scope
                 * of the process.
                 */
                ActivitySet eventSubProcessActivitySet =
                        Xpdl2ModelUtil
                                .getEmbeddedSubProcessActivitySet(eachActivity);

                if (eventSubProcessActivitySet != null) {

                    activitiesInProcessScope.addAll(eventSubProcessActivitySet
                            .getActivities());
                }

            } else {

                activitiesInProcessScope.add(eachActivity);
            }
        }

        return activitiesInProcessScope;
    }

    /**
     * Add any signal throwers in the source list to the target list.
     * 
     * @param inScope
     * @param sourceList
     */
    private static void addSignalThrowers(Set<Activity> targetList,
            Collection<Activity> sourceList) {
        for (Activity activity : sourceList) {
            if (EventTriggerType.EVENT_SIGNAL_THROW_LITERAL
                    .equals(EventObjectUtil.getEventTriggerType(activity))) {
                targetList.add(activity);
            }
        }
        return;
    }

    /**
     * Get the event signal name from the given signal event activity.
     * 
     * @param activity
     * @return name of the signal or empty string if no signal name set
     *         (Catch-All)
     */
    public static String getSignalName(Activity activity) {
        String sigName = null;

        if (activity.getEvent() != null) {
            EObject trigTypeNode =
                    activity.getEvent().getEventTriggerTypeNode();
            if (trigTypeNode instanceof TriggerResultSignal) {
                sigName = ((TriggerResultSignal) trigTypeNode).getName();
            }
        }

        if (sigName == null) {
            sigName = ""; //$NON-NLS-1$
        }
        return sigName;
    }

    /**
     * The set of data associated with the throw-signal event(s) that throw the
     * signal caught by the given activity.
     * <p>
     * This method will check that there is a specific signal caught, that there
     * is at least one thrower of the signal in the scope of the catch signal
     * and if the are multiple throwers, that they all have the same payload.
     * 
     * @param catchSignalEvent
     * 
     * @return The data payload defined by the thrower(s) of the signal.
     * 
     * @throws GetSignalPayloadException
     */
    public static Collection<ActivityInterfaceData> getSignalPayload(
            Activity catchSignalEvent) throws GetSignalPayloadException {

        String signalName = getSignalName(catchSignalEvent);
        if (signalName == null || signalName.length() == 0) {
            throw new GetSignalPayloadException(
                    GetSignalPayloadExceptionType.NO_SIGNAL_SPECIFIED);
        }

        Collection<Activity> throwSignalsInScope =
                getThrowSignalsInScope(catchSignalEvent);

        if (throwSignalsInScope.size() == 0) {
            throw new GetSignalPayloadException(
                    GetSignalPayloadExceptionType.NO_SIGNAL_THROW_IN_SCOPE);
        }

        /*
         * Get the signal payload from the first throw signal in scope and then
         * compare it with all the others if the are multiple throwers
         */
        Collection<ActivityInterfaceData> signalPayload =
                validateSignalPayLoads(throwSignalsInScope);

        return signalPayload;
    }

    /**
     * This method extracted from {@link #getSignalPayload(Activity)} for
     * convenience during validation.
     * 
     * @param signalThrowers
     * 
     * @return the payload (data associated with the signal throwers, provided
     *         that they all have the same data associated with the same
     *         configuration (i.e. same payload definition). Otherwise throws
     *         {@link GetSignalPayloadException}
     * 
     * @throws GetSignalPayloadException
     * 
     */
    public static Collection<ActivityInterfaceData> validateSignalPayLoads(
            Collection<Activity> signalThrowers)
            throws GetSignalPayloadException {
        Collection<ActivityInterfaceData> signalPayload = null;

        boolean first = true;
        for (Activity throwSignalEvent : signalThrowers) {
            if (first == true) {
                signalPayload =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(throwSignalEvent);
                first = false;

            } else {
                /* Check that all other payloads have the same configuration. */
                Collection<ActivityInterfaceData> otherPayload =
                        ActivityInterfaceDataUtil
                                .getActivityInterfaceData(throwSignalEvent);

                if (signalPayload.size() != otherPayload.size()) {
                    throw new GetSignalPayloadException(
                            GetSignalPayloadExceptionType.INCONSISTENT_PAYLOADS);
                }

                for (ActivityInterfaceData signalData : signalPayload) {
                    boolean found = false;
                    for (ActivityInterfaceData otherData : otherPayload) {
                        if (signalData.equalsStrict(otherData)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        throw new GetSignalPayloadException(
                                GetSignalPayloadExceptionType.INCONSISTENT_PAYLOADS);
                    }
                }
            }
        }
        return signalPayload;
    }

    /**
     * @param catchSignalEvent
     * 
     * @return The xpdExt:SignalData extension element for the given activity or
     *         <code>null</code> if not set (or not a signal).
     */
    public static SignalData getSignalDataExtensionElement(
            Activity catchSignalEvent) {
        if (catchSignalEvent.getEvent() != null
                && catchSignalEvent.getEvent().getEventTriggerTypeNode() instanceof TriggerResultSignal) {
            TriggerResultSignal triggerResultSignal =
                    (TriggerResultSignal) catchSignalEvent.getEvent()
                            .getEventTriggerTypeNode();

            SignalData signalData =
                    (SignalData) Xpdl2ModelUtil
                            .getOtherElement(triggerResultSignal,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_SignalData());

            return signalData;
        }

        return null;
    }

    /**
     * Retrieve the list of timer events that have been explicitly or implicitly
     * selected for reschedule by the given catch signal event activity.
     * <p>
     * i.e.
     * <li>If reschedule type = All then this is all attached events.</li>
     * <li>If reschedule type = Deadline then this is the timer event currently
     * set as deadline timer (if there is one)</li>
     * <li>If reschedule type = Selected then this is the explicitly selected
     * timer events.</li>
     * <li>Otherwise an empty list is returned.</li>
     * 
     * @param signalEvent
     * 
     * @return List of timer event activities either explicitly or implicitly
     *         referenced for timer rescheduling or empty list.
     */
    public static Collection<Activity> getRescheduleTimerEvents(
            Activity signalEvent) {
        List<Activity> rescheduleTimerEvents = new ArrayList<Activity>();

        SignalData signalData =
                EventObjectUtil.getSignalDataExtensionElement(signalEvent);

        if (signalData != null) {
            RescheduleTimers rescheduleTimers =
                    signalData.getRescheduleTimers();

            if (rescheduleTimers != null) {
                RescheduleTimerSelectionType timerSelectionType =
                        rescheduleTimers.getTimerSelectionType();

                if (RescheduleTimerSelectionType.SELECTED
                        .equals(timerSelectionType)) {

                    /*
                     * Explicit selection.
                     */
                    for (ActivityRef timerEventRef : rescheduleTimers
                            .getTimerEvents()) {
                        Activity timerEvent = timerEventRef.getActivity();

                        if (timerEvent != null) {
                            rescheduleTimerEvents.add(timerEvent);
                        }
                    }

                } else {
                    /*
                     * Implicit selection.
                     */
                    Activity taskAttachedTo =
                            EventObjectUtil.getTaskAttachedTo(signalEvent);

                    if (taskAttachedTo != null) {
                        Collection<Activity> attachedEvents =
                                Xpdl2ModelUtil
                                        .getAttachedEvents(taskAttachedTo);

                        if (RescheduleTimerSelectionType.ALL
                                .equals(timerSelectionType)) {
                            /*
                             * Implicitly all timers.
                             */
                            for (Activity attachedEvent : attachedEvents) {
                                if (EventTriggerType.EVENT_TIMER_LITERAL
                                        .equals(EventObjectUtil
                                                .getEventTriggerType(attachedEvent))) {
                                    rescheduleTimerEvents.add(attachedEvent);
                                }
                            }

                        } else if (RescheduleTimerSelectionType.DEADLINE
                                .equals(timerSelectionType)) {
                            /*
                             * Implicitly just the timer set as activity
                             * deadline.
                             */
                            Object activityDeadlineEventId =
                                    Xpdl2ModelUtil
                                            .getOtherAttribute(taskAttachedTo,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getDocumentRoot_ActivityDeadlineEventId());

                            for (Activity attachedEvent : attachedEvents) {
                                if (EventTriggerType.EVENT_TIMER_LITERAL
                                        .equals(EventObjectUtil
                                                .getEventTriggerType(attachedEvent))) {
                                    if (attachedEvent.getId()
                                            .equals(activityDeadlineEventId)) {
                                        rescheduleTimerEvents
                                                .add(attachedEvent);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return rescheduleTimerEvents;
    }

    /**
     * Exception for use with
     * {@link EventObjectUtil#getThrowSignalPayload(Activity)}
     * 
     * @author aallway
     * @since 26 Apr 2012
     */
    public static class GetSignalPayloadException extends Exception {

        private static final long serialVersionUID = -8068126168831336243L;

        private GetSignalPayloadExceptionType type;

        GetSignalPayloadException(GetSignalPayloadExceptionType type) {
            super();
            this.type = type;
        }

        /**
         * @see java.lang.Throwable#getMessage()
         * 
         * @return
         */
        @Override
        public String getMessage() {
            if (GetSignalPayloadExceptionType.NO_SIGNAL_SPECIFIED.equals(type)) {
                return Messages.EventObjectUtil_NoSignalSelected_message;
            } else if (GetSignalPayloadExceptionType.NO_SIGNAL_THROW_IN_SCOPE
                    .equals(type)) {
                return Messages.EventObjectUtil_SignalNotThrowInScope_message;
            } else if (GetSignalPayloadExceptionType.INCONSISTENT_PAYLOADS
                    .equals(type)) {
                return Messages.EventObjectUtil_SignalThrownWithInconsistentData_message;
            }
            return ""; //$NON-NLS-1$
        }

        /**
         * @return the type of error encountered.
         */
        public GetSignalPayloadExceptionType getType() {
            return type;
        }

        public enum GetSignalPayloadExceptionType {
            /** The catch signal event is a catch all. */
            NO_SIGNAL_SPECIFIED,
            /** Signal not thrown by any event in scope on catch */
            NO_SIGNAL_THROW_IN_SCOPE,
            /**
             * Different in-scope throw signal events have different data
             * payload specifications (associated data)
             */
            INCONSISTENT_PAYLOADS
        }
    }

    /**
     * @param editingDomain
     * @param activity
     * 
     * @return Command to set the disable implicit association flag on given
     *         activity.
     */
    private static Command setImplicitAssociationDisabledCommand(
            EditingDomain editingDomain, Activity activity,
            boolean disableImplicitAssociation) {
        CompoundCommand cmd = new CompoundCommand();

        AssociatedParameters assocParams =
                (AssociatedParameters) Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_AssociatedParameters());

        if (disableImplicitAssociation) {
            if (assocParams == null) {
                assocParams =
                        XpdExtensionFactory.eINSTANCE
                                .createAssociatedParameters();
                cmd.append(Xpdl2ModelUtil
                        .getSetOtherElementCommand(editingDomain,
                                activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_AssociatedParameters(),
                                assocParams));
            }

            cmd.append(SetCommand
                    .create(editingDomain,
                            assocParams,
                            XpdExtensionPackage.eINSTANCE
                                    .getAssociatedParameters_DisableImplicitAssociation(),
                            Boolean.TRUE));

        } else {
            if (assocParams != null) {
                cmd.append(SetCommand
                        .create(editingDomain,
                                assocParams,
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedParameters_DisableImplicitAssociation(),
                                Boolean.FALSE));
            }
        }

        return cmd;
    }

    /**
     * @param activity
     * @return <code>true</code> if the activity has explicit data associations.
     */
    private static boolean hasExplicitAssociations(Activity activity) {
        EObject obj =
                activity.getOtherElement(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedParameters().getName());
        if (obj != null && obj instanceof AssociatedParameters) {
            AssociatedParameters associatedParams = (AssociatedParameters) obj;

            if (!associatedParams.getAssociatedParameter().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param activity
     * 
     * @return The xpdExt:RescheduleTimerScript of the given activity (if it has
     *         reschedule timer scripty set) else <code>null</code>
     */
    public static RescheduleTimerScript getRescheduleTimerScript(
            Activity activity) {
        if (activity != null
                && activity.getEvent() != null
                && activity.getEvent().getEventTriggerTypeNode() instanceof TriggerTimer) {

            TriggerTimer triggerTimer =
                    (TriggerTimer) activity.getEvent()
                            .getEventTriggerTypeNode();

            return (RescheduleTimerScript) Xpdl2ModelUtil
                    .getOtherElement(triggerTimer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_RescheduleTimerScript());
        }

        return null;
    }

}
