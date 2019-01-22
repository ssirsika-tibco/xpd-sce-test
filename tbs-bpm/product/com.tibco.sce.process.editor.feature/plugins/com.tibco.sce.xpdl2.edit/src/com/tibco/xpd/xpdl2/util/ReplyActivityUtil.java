/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.internal.Messages;

/**
 * Utilities to help with send task, throw message intermediate event or throw
 * message end event that can configured to be the response to a message
 * received by an upstream activity (start message, receive task, intermediate
 * catch message event).
 * 
 * @author aallway
 * @since 3.2
 */
public class ReplyActivityUtil {

    public static final String REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL =
            Messages.ReplyActivityUtil_ReplyTo_label;

    /*
     * Note: This class cannot access 'TaskImplementationTypeDefinitions' hence
     * defining "WebService" Task Impl Type defination over here and making
     * TaskImplementationTypeDefinitions to reference this constant.
     */
    /** Web service identifier. */
    public static final String TASK_IMPL_TYPE_DEF_WEB_SERVICE = "WebService"; //$NON-NLS-1$

    /**
     * Check whether the given activity is a send task, throw message
     * intermediate event or throw message end event that is configured to be
     * the response to a message received by an upstream activity (start
     * message, receive task, intermediate catch message event).
     * 
     * @return true if activity is a configured as reply activity.
     */
    public static boolean isReplyActivity(Activity activity) {
        String receiveActivityId =
                getRequestActivityIdForReplyActivity(activity);

        if (receiveActivityId != null) {
            // If the attribute is defined at all then this is a reply activity
            // Though it my be empty if user has not complete configuration.
            return true;
        }

        return false;
    }

    /**
     * If the given activity is a send task, throw message intermediate event or
     * throw message end event that is configured to be the response to a
     * message received by an upstream activity (start message, receive task,
     * intermediate catch message event) then return the Activity that is the
     * incoming message activity that it is the reply to.
     * 
     * @param replyActivity
     * 
     * @return the Activity that is the incoming message activity that the given
     *         activity is the reply to or null if (a) the activity is not a
     *         reply activity or (b) the request activity cannot be located.
     */
    public static Activity getRequestActivityForReplyActivity(
            Activity replyActivity) {
        Activity requestActivity = null;

        if (replyActivity != null) {
            String requestActivityId =
                    getRequestActivityIdForReplyActivity(replyActivity);

            if (requestActivityId != null && requestActivityId.length() > 0) {
                // Should be in the same activity container so we'll check there
                // first.
                requestActivity =
                        locateRequestActivity(replyActivity, requestActivityId);
            }
        }
        return requestActivity;
    }

    /**
     * @param replyActivity
     * @param requestActivityId
     * @return
     */
    private static Activity locateRequestActivity(Activity replyActivity,
            String requestActivityId) {
        Activity requestActivity = null;

        if (replyActivity != null && replyActivity.getProcess() != null
                && requestActivityId != null) {
            requestActivity =
                    replyActivity.getFlowContainer()
                            .getActivity(requestActivityId);
            if (requestActivity == null) {
                // Check the whole process (maybe its been moved into / out of
                // embedded sub-proc
                Collection<Activity> activities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(replyActivity
                                .getProcess());
                for (Activity act : activities) {
                    if (requestActivityId.equals(act.getId())) {
                        requestActivity = act;
                        break;
                    }
                }
            }
        }

        return requestActivity;
    }

    /**
     * @param editingDomain
     * @param replyActivity
     * @param requestActivityId
     * 
     * @return The command to set the given replyActivity as a reply to the
     *         message received by the given receive activity.
     * 
     *         <li><b>Note:</b> The message information details (under TaskSend
     *         / TriggerResultMessage) of the replyActivity are not explicitly
     *         copied from requestActivity here - <b>however</b> there is a
     *         postExecution command insertion that keeps reply in synch with
     *         request so this will add the message details when the command is
     *         executed.
     */
    public static Command getSetRequestActivityForReplyActivityCommand(
            EditingDomain editingDomain, Activity replyActivity,
            String requestActivityId) {
        OtherAttributesContainer replyActAttributeContainer =
                getReplyToActivityAttributeContainer(replyActivity);

        if (replyActAttributeContainer != null) {
            /*
             * XPD-7721: This method sets the Mode to 'Reply To Incoming Request
             * Activity' but the Command title wrongly said 'Set To One Way
             * Message'
             */
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ReplyActivityUtil_SetReplyToIncomingRequestCommand_title);

            /*
             * XPD-7811: If the Implementation and ImplementationType is not
             * WebService then create a command to set it to Web-Service.
             */
            CompoundCommand setImplTypeToWebServiceCommand =
                    getSetReplyActImplTypeToWebServiceCommand(replyActivity,
                            editingDomain);

            if (!setImplTypeToWebServiceCommand.isEmpty()) {
                cmd.append(setImplTypeToWebServiceCommand);
            }

            //
            // Just set the ReplyToActivityId attribute and let
            // MaintainWebServiceReplyActivity pre-commit command contributor
            // deal with the rest (i.e. copying web svc details from the request
            // id).
            //
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(editingDomain,
                            replyActAttributeContainer,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ReplyToActivityId(),
                            requestActivityId));

            //
            // If the reply activity has its default label then change it to
            // 'Reply To <request activity>"
            //
            String label = Xpdl2ModelUtil.getDisplayName(replyActivity);
            boolean replaceLabel = false;
            if (label == null || label.length() == 0) {
                replaceLabel = true;
            } else if (label != null
                    && label.startsWith(REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL)) {
                // Currently a reply to something, then replace with latest
                replaceLabel = true;

            } else if (label != null
                    && replyActivity.getImplementation() instanceof Task) {
                if (label.startsWith(Messages.ReplyActivityUtil_SendTask_label)) {
                    replaceLabel = true;
                }

            } else if (label != null && replyActivity.getEvent() != null) {
                if (label
                        .startsWith(Messages.ReplyActivityUtil_ThrowEvent_label)
                        || label.startsWith(Messages.ReplyActivityUtil_EndEvent_label)) {
                    replaceLabel = true;
                }
            }

            if (replaceLabel) {
                Activity requestActivity =
                        locateRequestActivity(replyActivity, requestActivityId);

                if (requestActivity != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    replyActivity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    getReplyToLabel(requestActivity,
                                            replyActivity)));
                }
            }
            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * 
     * @param replyActivity
     *            the reply actiivty.
     * @param editingDomain
     * @return Command to set the Implementation and ImplementationType of the
     *         pased reply activity to Web-Service (only if it not already
     *         web-service), else return empty command.
     */
    private static CompoundCommand getSetReplyActImplTypeToWebServiceCommand(
            Activity replyActivity, EditingDomain editingDomain) {

        CompoundCommand cmd = new CompoundCommand();

        if (replyActivity.getImplementation() instanceof Task) {
            Task tsk = (Task) replyActivity.getImplementation();

            TaskSend taskSend = tsk.getTaskSend();

            if (taskSend != null) {

                ImplementationType implementation =
                        taskSend.getImplementation();

                String implType =
                        (String) Xpdl2ModelUtil.getOtherAttribute(taskSend,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ImplementationType());

                if (!isImplAndImplTypeWebService(implementation, implType)) {

                    cmd.append(SetCommand
                            .create(editingDomain,
                                    taskSend,
                                    Xpdl2Package.eINSTANCE
                                            .getTaskSend_Implementation(),
                                    ImplementationType.WEB_SERVICE_LITERAL));

                    cmd.append(SetCommand.create(editingDomain,
                            taskSend,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType(),
                            TASK_IMPL_TYPE_DEF_WEB_SERVICE));
                }
            }
        } else if (replyActivity.getEvent() instanceof IntermediateEvent) {

            IntermediateEvent intermediateEvent =
                    (IntermediateEvent) replyActivity.getEvent();

            ImplementationType implementation =
                    intermediateEvent.getImplementation();

            String implType =
                    (String) Xpdl2ModelUtil
                            .getOtherAttribute(intermediateEvent,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ImplementationType());

            if (!isImplAndImplTypeWebService(implementation, implType)) {

                cmd.append(SetCommand.create(editingDomain,
                        intermediateEvent,
                        Xpdl2Package.eINSTANCE
                                .getIntermediateEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));

                cmd.append(SetCommand.create(editingDomain,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        TASK_IMPL_TYPE_DEF_WEB_SERVICE));
            }

        } else if (replyActivity.getEvent() instanceof EndEvent) {

            EndEvent endEvent = (EndEvent) replyActivity.getEvent();

            ImplementationType implementation = endEvent.getImplementation();

            String implType =
                    (String) Xpdl2ModelUtil.getOtherAttribute(endEvent,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());

            if (!isImplAndImplTypeWebService(implementation, implType)) {

                cmd.append(SetCommand.create(editingDomain,
                        endEvent,
                        Xpdl2Package.eINSTANCE.getEndEvent_Implementation(),
                        ImplementationType.WEB_SERVICE_LITERAL));

                cmd.append(SetCommand.create(editingDomain,
                        endEvent,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        TASK_IMPL_TYPE_DEF_WEB_SERVICE));

            }
        }
        return cmd;
    }

    /**
     * 
     * @param implementation
     * @param implType
     * @return <code>true</code> if the passed implementationa and
     *         implementation type are WebService else returns
     *         <code>false</code>
     */
    private static boolean isImplAndImplTypeWebService(
            ImplementationType implementation, String implType) {

        if (implementation != null && implType != null && !implType.isEmpty()) {

            return implementation.getLiteral()
                    .equals(ImplementationType.WEB_SERVICE_LITERAL)
                    && implType.equals(TASK_IMPL_TYPE_DEF_WEB_SERVICE);

        }

        return false;
    }

    /**
     * @param requestActivity
     * @param replyActivity
     * @return unique "reply to :" label name
     */
    public static String getReplyToLabel(Activity requestActivity,
            Activity replyActivity) {
        String label;
        String reqActName = null;

        reqActName = getRequestActivityLabel(requestActivity);

        if (reqActName != null && reqActName.length() > 0
                && replyActivity.getProcess() != null) {
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(requestActivity
                            .getProcess());

            label = REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL + ": " + reqActName; //$NON-NLS-1$
            int idx = 1;
            while (actNameExists(activities, replyActivity, label)) {
                idx++;

                label =
                        REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL
                                + " (" + idx + "): " + reqActName; //$NON-NLS-1$ //$NON-NLS-2$
            }

        } else {
            label = REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL + ":" + reqActName; //$NON-NLS-1$
        }

        return label;
    }

    /**
     * @param requestActivity
     * @return
     */
    public static String getRequestActivityLabel(Activity requestActivity) {
        String reqActName = ""; //$NON-NLS-1$
        if (requestActivity != null) {
            reqActName = Xpdl2ModelUtil.getDisplayName(requestActivity);
            if (reqActName == null || reqActName.length() == 0) {
                if (requestActivity.getEvent() != null) {
                    reqActName = Messages.ReplyActivityUtil_UnnamedEvent_label;
                } else {
                    reqActName = Messages.ReplyActivityUtil_UnnamedTask_label;
                }
            }
        }

        return reqActName;
    }

    /**
     * Check whether activity with the given label (other than the given
     * notActivity) already exists.
     * 
     * @param activities
     * @param notActivity
     * @param label
     * 
     * @return true if label already exists.
     */
    private static boolean actNameExists(Collection<Activity> activities,
            Activity notActivity, String label) {
        for (Activity act : activities) {
            if (act != notActivity) {
                String name = Xpdl2ModelUtil.getDisplayName(act);
                if (label.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param editingDomain
     * @param replyActivity
     * 
     * @return the command to unset the request activity that the given reply
     *         activity references (effectively setting it back to
     *         "Send One Way Message" configuration. <b>Note:</b> The message
     *         details will be wiped.
     */
    public static Command getUnsetRequestActivityForReplyActivityCommand(
            EditingDomain editingDomain, Activity replyActivity) {

        OtherAttributesContainer replyActAttributeContainer =
                getReplyToActivityAttributeContainer(replyActivity);

        if (replyActAttributeContainer != null) {
            boolean wasReply = ReplyActivityUtil.isReplyActivity(replyActivity);

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.ReplyActivityUtil_SetSendOneWayMessage_menu);

            //
            // BY far and a way the safest way to do this is to replace the
            // entire TaskSend element.
            //
            cmd.append(RemoveCommand.create(editingDomain,
                    replyActAttributeContainer));

            if (replyActivity.getImplementation() instanceof Task) {
                Task task = (Task) replyActivity.getImplementation();

                TaskSend taskSend = createNewTaskSend();

                cmd.append(SetCommand.create(editingDomain,
                        task,
                        Xpdl2Package.eINSTANCE.getTask_TaskSend(),
                        taskSend));

            } else if (replyActivity.getEvent() instanceof IntermediateEvent) {
                /*
                 * XPD-7721: Setting Throw message events config to 'Send One
                 * Way Request' should set the Implementation to 'Unspecified'
                 */
                IntermediateEvent intermediateEvent =
                        (IntermediateEvent) replyActivity.getEvent();

                cmd.append(SetCommand.create(editingDomain,
                        intermediateEvent,
                        Xpdl2Package.eINSTANCE
                                .getIntermediateEvent_Implementation(),
                        ImplementationType.UNSPECIFIED_LITERAL));

                cmd.append(SetCommand.create(editingDomain,
                        intermediateEvent,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        null));

                TriggerResultMessage trm = createNewTriggerResultMessage();
                cmd.append(SetCommand.create(editingDomain, replyActivity
                        .getEvent(), Xpdl2Package.eINSTANCE
                        .getIntermediateEvent_TriggerResultMessage(), trm));

            } else if (replyActivity.getEvent() instanceof EndEvent) {
                /*
                 * XPD-7721: Setting Throw message events config to 'Send One
                 * Way Request' should set the Implementation to 'Unspecified'
                 */
                EndEvent endEvent = (EndEvent) replyActivity.getEvent();

                cmd.append(SetCommand.create(editingDomain,
                        endEvent,
                        Xpdl2Package.eINSTANCE.getEndEvent_Implementation(),
                        ImplementationType.UNSPECIFIED_LITERAL));

                cmd.append(SetCommand.create(editingDomain,
                        endEvent,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ImplementationType(),
                        null));

                TriggerResultMessage trm = createNewTriggerResultMessage();
                cmd.append(SetCommand.create(editingDomain, replyActivity
                        .getEvent(), Xpdl2Package.eINSTANCE
                        .getEndEvent_TriggerResultMessage(), trm));
            }

            //
            // When changing from reply activity (which gets all assoc params
            // copied) to send one way message then remove the associated
            // parameters.
            if (wasReply) {
                Object assocParam =
                        Xpdl2ModelUtil
                                .getOtherElement(replyActivity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AssociatedParameters());

                if (assocParam != null) {
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    replyActivity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedParameters(),
                                    assocParam));
                }
            }

            //
            // If the reply to activity is prefixed with "Reply To:" return it
            // to its original default name.
            //
            String label = Xpdl2ModelUtil.getDisplayName(replyActivity);
            if (label != null) {
                if (label.startsWith(REPLY_ACTIVITY_UTIL_REPLY_TO_LABEL)) {

                    if (replyActivity.getImplementation() instanceof Task) {
                        label = Messages.ReplyActivityUtil_SendTask_label;
                    } else if (replyActivity.getEvent() instanceof EndEvent
                            || replyActivity.getEvent() instanceof IntermediateEvent) {
                        label = Messages.ReplyActivityUtil_ThrowEvent_label;
                    } else {
                        label = ""; //$NON-NLS-1$
                    }

                    cmd.append(Xpdl2ModelUtil
                            .getSetOtherAttributeCommand(editingDomain,
                                    replyActivity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName(),
                                    label));
                }
            }

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * If the given activity is a send task, throw message intermediate event or
     * throw message end event that is configured to be the response to a
     * message received by an upstream activity (start message, receive task,
     * intermediate catch message event) then return the id of the incoming
     * message activity that it is the reply to.
     * 
     * @param activity
     * @return then return the id of the incoming message activity that the
     *         given activity is the reply to (null if this is not a reply
     *         activity "" if it is but is not yet completely configured)
     */
    public static String getRequestActivityIdForReplyActivity(Activity activity) {
        OtherAttributesContainer replyActAttributeContainer =
                getReplyToActivityAttributeContainer(activity);

        if (replyActAttributeContainer != null) {
            String receiveActivityId =
                    (String) Xpdl2ModelUtil
                            .getOtherAttribute(replyActAttributeContainer,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ReplyToActivityId());
            return receiveActivityId;
        }

        return null;
    }

    /**
     * @param process
     * 
     * @return a list of all the incoming request receipt activities in the
     *         process.
     */
    public static List<Activity> getAllIncomingRequestActivities(Process process) {
        ArrayList<Activity> reqActs = new ArrayList<Activity>();

        if (process != null) {
            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity act : activities) {
                if (isIncomingRequestActivity(act)) {
                    reqActs.add(act);
                }
            }
        }

        return reqActs;
    }

    /**
     * @param act
     * @return true if the activity is of a type that can receive an incoming
     *         request (message start event, catch message intermediate event,
     *         receive task).
     */
    public static boolean isIncomingRequestActivity(Activity act) {
        Event event = act.getEvent();
        if (event != null
                && event.getEventTriggerTypeNode() instanceof TriggerResultMessage) {
            TriggerResultMessage trm =
                    (TriggerResultMessage) event.getEventTriggerTypeNode();

            // It's a message event.
            if (event instanceof StartEvent) {
                // All message start events are catches.
                return true;
            } else if (event instanceof IntermediateEvent) {
                // Intermediate ,message events can be catch or throw - we only
                // want catch.
                if (!CatchThrow.THROW.equals(trm.getCatchThrow())) {
                    return true;
                }
            }
        } else if (act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();

            if (task.getTaskReceive() != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param requestActivity
     * 
     * @return A list of activities configured to be replies to the given
     *         incoming request message.
     */
    public static List<Activity> getReplyActivities(Activity requestActivity) {
        ArrayList<Activity> replyActs = new ArrayList<Activity>();

        Process process = requestActivity.getProcess();
        if (process != null) {
            String lookForId = requestActivity.getId();

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Iterator iterator = activities.iterator(); iterator.hasNext();) {
                Activity act = (Activity) iterator.next();

                // Get the request act id (if activity is coonfigured as a reply
                // activity)
                String requestActId = getRequestActivityIdForReplyActivity(act);

                if (requestActId != null && requestActId.equals(lookForId)) {
                    replyActs.add(act);
                }
            }
        }

        return replyActs;
    }

    /**
     * @param requestActivity
     * 
     * @return true if the given requestActivity has reply activities
     */
    public static boolean hasReplyActivities(Activity requestActivity) {

        Process process = requestActivity.getProcess();
        if (process != null) {
            String lookForId = requestActivity.getId();

            Collection<Activity> activities =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Iterator iterator = activities.iterator(); iterator.hasNext();) {
                Activity act = (Activity) iterator.next();

                // Get the request act id (if activity is configured as a reply
                // activity)
                String requestActId = getRequestActivityIdForReplyActivity(act);

                if (requestActId != null && requestActId.equals(lookForId)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * If the given activity is a send task, throw message intermediate event or
     * throw message end event that is configured to be the response to a
     * message received by an upstream activity (start message, receive task,
     * intermediate catch message event) then return the element that should
     * contain the xpdExt:ReplyToActivityid
     * 
     * @param activity
     * 
     * @return the element that should contain the xpdExt:ReplyToActivityid or
     *         null if not defined
     */
    private static OtherAttributesContainer getReplyToActivityAttributeContainer(
            Activity activity) {
        OtherAttributesContainer replyActAttributeContainer = null;

        if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            replyActAttributeContainer = task.getTaskSend();
        } else if (activity.getEvent() != null) {
            EObject eventTriggerTypeNode =
                    activity.getEvent().getEventTriggerTypeNode();
            if (eventTriggerTypeNode instanceof TriggerResultMessage) {
                replyActAttributeContainer =
                        (OtherAttributesContainer) eventTriggerTypeNode;
            }
        }
        return replyActAttributeContainer;
    }

    /**
     * @return A fresh TaskSend element in it's default state.
     */
    private static TaskSend createNewTaskSend() {
        TaskSend taskSend = Xpdl2Factory.eINSTANCE.createTaskSend();
        taskSend.setImplementation(ImplementationType.UNSPECIFIED_LITERAL);
        taskSend.setMessage(Xpdl2Factory.eINSTANCE.createMessage());
        return taskSend;
    }

    /**
     * @return A fresh TriggerResultMessage element (<b>without</b> the
     *         WebServiceOperation config).
     */
    private static TriggerResultMessage createNewTriggerResultMessage() {
        TriggerResultMessage trm =
                Xpdl2Factory.eINSTANCE.createTriggerResultMessage();
        trm.setCatchThrow(CatchThrow.THROW);
        trm.setMessage(Xpdl2Factory.eINSTANCE.createMessage());

        return trm;
    }

    /**
     * Get the 'Reply Immediate With Process Id' flag. This will only be called
     * for Start Message Event.
     * 
     * @return true if flag is set, false otherwise.
     */
    public static boolean isStartMessageWithReplyImmediate(Activity activity) {
        Event ev = activity.getEvent();

        if (ev instanceof StartEvent) {

            EObject eventTriggerTypeNode = ev.getEventTriggerTypeNode();
            if (eventTriggerTypeNode instanceof TriggerResultMessage) {

                TriggerResultMessage triggerResultMessage =
                        (TriggerResultMessage) eventTriggerTypeNode;

                Object val =
                        Xpdl2ModelUtil.getOtherAttribute(triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_ReplyImmediately());
                if (val != null && val instanceof Boolean) {
                    return ((Boolean) val).booleanValue();
                }
            }

        }

        return false;
    }

    /**
     * @param activity
     * 
     * @return <code>true</code> if the activity has reply activities or its a
     *         start message with reply-immediate set, <code>false</code>
     *         otherwise.
     */
    public static boolean isRequestActivityWithReply(Activity activity) {

        if (ReplyActivityUtil.hasReplyActivities(activity)
                || ReplyActivityUtil.isStartMessageWithReplyImmediate(activity)) {

            return true;
        }

        return false;
    }
}
