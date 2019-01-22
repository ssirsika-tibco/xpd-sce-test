/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processwidget.adapters;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * @author wzurek
 */
public interface EventAdapter extends FlowNodeAdapter {

    /**
     * Reserved namespace to indicate web service message implementations. This
     * is only for internal use in the standard web service extensions. Other
     * extending plug-ins should specify their own namespace.
     */
    final String WEB_SERVICE_IMPLEMENTATION_NAMESPACE =
            "http://www.tibco.com/XPDL/webService"; //$NON-NLS-1$

    /**
     * Avaliable modes for timer trigger operation.
     */
    public static enum TimerTriggerMode {
        DATETIME, CYCLE;
    };

    /**
     * Type of the event (location on the diagram): Start, Intermediate, End
     * 
     * @return the event flow type
     */
    EventFlowType getFlowType();

    /**
     * Type of the event (@see EventType)
     * 
     * @return the type of the event
     */
    EventTriggerType getEventTriggerType();

    /**
     * Return <code>true</code> if the event activity is contained directly
     * under a process and NOT in an embedded/event sub-process,
     * <code>false</code> otherwise.
     * 
     * @return <code>true</code> if the event activity is contained directly
     *         under a process and NOT in an embedded/event sub-process,
     *         <code>false</code> otherwise.
     */
    boolean isEventContainedDirectlyUnderProcess();

    /**
     * Create command to set event type
     * 
     * @param ed
     * @param eventType
     * @return
     */
    Command getSetEventTriggerTypeCommand(EditingDomain ed,
            EventTriggerType eventType);

    /**
     * Get the Id of the link event that this link event links to.
     * 
     * @return
     */
    String getLinkEventId();

    /**
     * Set the event that this link event links to
     * 
     * @param ed
     * @param linkEventId
     * @return
     */
    Command getSetEventLinkIdCommand(EditingDomain ed, String linkEventId,
            String processId);

    /**
     * Set the inverse Catch Throw, this is used when link events are dropped on
     * sequence flows.
     * 
     * @param ed
     * @param newNodeObject
     * @return
     */
    Command getSetInverseCatchThrowOnLinkCommand(EditingDomain ed);

    /**
     * Get the command that will attach (or DETACH) this intermediate event from
     * the border of a task.
     * 
     * @param ed
     * @param taskId
     *            Task to attach to. If <b>null</b> then it should be detached.
     * @param positionOnBorder
     * @return
     */
    Command getSetTaskBorderAttachmentCommand(EditingDomain ed, String taskId,
            Double positionOnBorder);

    /**
     * For intermediate event, return task model object whose border this
     * intermediate event is attached to.
     * 
     * @return Object representing the task that this event is attached to
     *         border of or <b>null</b> if not attached to task border. T
     */
    Object getBorderAttachmentTask();

    /**
     * Return whether event is attached to the border of a task.
     * 
     * @return
     */
    boolean isAttachedToTask();

    /**
     * Return the position of the attachment on the border of the task that this
     * intermediate event is attached to.
     * 
     * @return
     */
    Double getTaskBorderAttachmentPosition();

    /**
     * Get Error Code for Error result types.
     * 
     * @return
     */
    String getErrorCode();

    /**
     * Get Signal Namer for Signal event types.
     * 
     * @return
     */
    String getSignalName();

    /**
     * 
     * @return <code>true</code> if the signal event is a Local Signal Event
     *         else return <code>false</code>
     */
    boolean isLocalSignalEvent();

    /**
     * @param ed
     * @param signalName
     * @return command to set the signal event that this link event links to
     *         given name.
     */
    Command getSetSignalNameCommand(EditingDomain ed, String signalName);

    /**
     * Return a link events that have this event as given event as a target
     * 
     * @param task
     * @return
     */
    Collection<EObject> getSourceLinkEvents();

    /**
     * @param ed
     * @return The command to set this throw message intermedaite / end event
     *         task as the reply to the given request activity - may also be
     *         called to set the request activity for a throw error end event.
     */
    Command getSetRequestActivityCommand(EditingDomain ed,
            String requestActivityId);

    /**
     * @return The ids of activities that are configured to reply to this
     *         receive task.
     */
    Collection<String> getReplyActivityIds();

    /**
     * @return The id of the activity that this event is configured as a reply
     *         OR throw fault error (null or "" if it is not)
     */
    String getRequestActivityId();

    /**
     * @return true if the event implements a proces interface method.
     */
    boolean isImplementingEvent();

    /**
     * @return List of Throw Error Event activities that throw errors for this
     *         incoming request activity.
     */
    Collection<String> getThrowErrorIdsForRequestActivity();

    /**
     * @return true if the event has its web service operation generated
     */
    boolean isAutoGenerated();

    /**
     * @return The text form of the condition if the activity has a deadline
     *         specified. This is used for display on the tooltip of the event.
     */
    String getDeadlineCondition();

    /**
     * @return the type of the trigger timer - possible values are Cycle,
     *         Date/Time
     */
    String getTriggerTimerType();

    /**
     * @return <code>true</code> if this event is a non-cancelling event.
     */
    boolean isNonCancellingEvent();

    /**
     * @return <code>true</code> if this event is a non-interrupting event.
     */
    boolean isNonInterruptingEvent();
}
