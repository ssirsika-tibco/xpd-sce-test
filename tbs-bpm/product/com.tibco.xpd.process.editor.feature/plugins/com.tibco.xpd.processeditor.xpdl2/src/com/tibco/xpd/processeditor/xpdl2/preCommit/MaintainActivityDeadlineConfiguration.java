/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processwidget.adapters.EventTriggerType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener to maintain the Activity Deadline configuration. This
 * will ensure things like:
 * <ul>
 * <li>The first timer event added to an Activity boundary is set as the
 * deadline timer event</li>
 * <li>If timer event is changed to another event type then the deadline timer
 * event on the activity is cleared</li>
 * <li>If deadline timer event of an activity is moved then the deadline id is
 * reset</li>
 * <li>...etc</li>
 * </ul>
 * 
 * @author njpatel
 */
public class MaintainActivityDeadlineConfiguration implements
        IProcessPreCommitContributor {

    public MaintainActivityDeadlineConfiguration() {
    }

    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        List<Command> commands = new ArrayList<Command>(5);

        // Process each notification
        for (ENotificationImpl notification : notifications) {
            contributeCommand(event, notification, commands);
        }

        if (!commands.isEmpty()) {
            return commands.size() == 1 ? commands.get(0)
                    : new CompoundCommand(commands);
        }
        return null;
    }

    /**
     * Process the given notification.
     * 
     * @param event
     * @param notification
     * @param commands
     *            add {@link Command}s to this list if the model needs to be
     *            fixed as the result of the notification.
     */
    private void contributeCommand(ResourceSetChangeEvent event,
            ENotificationImpl notification, List<Command> commands) {

        switch (notification.getEventType()) {
        case ENotificationImpl.SET:
            processSetNotification(event, notification, commands);
            break;
        case ENotificationImpl.ADD:
        case ENotificationImpl.ADD_MANY:
            processAddNotification(event, notification, commands);
            break;
        case ENotificationImpl.REMOVE:
        case ENotificationImpl.REMOVE_MANY:
            processRemoveNotification(event, notification, commands);
            break;
        }

    }

    /**
     * Process the SET notification.
     * 
     * @param event
     * @param notification
     * @param commands
     *            add {@link Command}s to this list if the model needs to be
     *            fixed as the result of the notification.
     */
    private void processSetNotification(ResourceSetChangeEvent event,
            ENotificationImpl notification, List<Command> commands) {

        if (notification.getNotifier() instanceof IntermediateEvent) {
            IntermediateEvent triggerEvent =
                    (IntermediateEvent) notification.getNotifier();

            /*
             * Check if the Target of an Intermediate Event has changed - this
             * could be because an event has been dragged away or onto a border
             * of an activity.
             */
            if (notification.getFeature() == Xpdl2Package.eINSTANCE
                    .getIntermediateEvent_Target()) {
                Activity evActivity =
                        (Activity) (triggerEvent.eContainer() instanceof Activity ? triggerEvent
                                .eContainer() : null);
                Process process = Xpdl2ModelUtil.getProcess(triggerEvent);

                if (process != null && evActivity != null) {
                    processSetEventTargetNotification(process,
                            evActivity,
                            event,
                            notification,
                            commands);
                }
            } else if (notification.getFeature() == Xpdl2Package.eINSTANCE
                    .getIntermediateEvent_Trigger()) {
                Process process = Xpdl2ModelUtil.getProcess(triggerEvent);
                /*
                 * If the trigger type has been changed to/from Timer event then
                 * the attached to activity's deadline activity id should be
                 * updated if required.
                 */
                if (process != null
                        && triggerEvent.getTarget() != null
                        && (notification.getOldValue() == TriggerType.TIMER_LITERAL || notification
                                .getNewValue() == TriggerType.TIMER_LITERAL)) {
                    Activity task =
                            Xpdl2ModelUtil.getActivityById(process,
                                    triggerEvent.getTarget());
                    Activity evActivity =
                            (Activity) (triggerEvent.eContainer() instanceof Activity ? triggerEvent
                                    .eContainer() : null);

                    if (task != null && evActivity != null) {
                        processSetEventTriggerTypeTimerNotification(task,
                                evActivity,
                                event,
                                notification,
                                commands);
                    }
                }
            }

        }
    }

    /**
     * Process the trigger type change (to or from Timer event) of an
     * intermediate event attached to an task border.
     * 
     * @param task
     *            the activity this trigger event is attached to
     * @param evActivity
     *            the trigger event activity
     * @param event
     * @param notification
     * @param commands
     *            add {@link Command}s to this list if the model needs to be
     *            fixed as the result of the notification.
     */
    private void processSetEventTriggerTypeTimerNotification(Activity task,
            Activity evActivity, ResourceSetChangeEvent event,
            ENotificationImpl notification, List<Command> commands) {
        if (notification.getNewValue() == TriggerType.TIMER_LITERAL) {
            /*
             * If the trigger type of an Intermediate Event set on an Activity
             * border is set to a Timer type then, if this is the only timer
             * event, set it to the activities deadline event by default
             */
            if (getAttachedTimerEventsCount(task) == 1) {
                // Only this event attached to the activity
                commands.add(createSetActivityDeadlineCommand(event
                        .getEditingDomain(), task, evActivity.getId()));
            }
        } else if (notification.getOldValue() == TriggerType.TIMER_LITERAL) {
            /*
             * A timer event type has been changed to some other type - if this
             * was set as the Activity deadline then reset this.
             */
            String id = getDeadlineActivityId(task);

            if (id != null && id.equals(evActivity.getId())) {
                commands.add(createUnsetActivityDeadlineCommand(event
                        .getEditingDomain(), task));
            }
        }
    }

    /**
     * Process target set notification of an intermediate event. This will
     * update the deadline activity id for a timer event that has been moved
     * from or on the border of an activity.
     * 
     * @param process
     * @param evActivity
     *            the intermediate event activity
     * @param event
     * @param notification
     * @param commands
     *            add {@link Command}s to this list if the model needs to be
     *            fixed as the result of the notification.
     */
    private void processSetEventTargetNotification(Process process,
            Activity evActivity, ResourceSetChangeEvent event,
            ENotificationImpl notification, List<Command> commands) {
        /*
         * If an activity deadline timer event is moved away from the border of
         * an Activity then the Activity's deadline id should be unset
         */
        if (notification.getOldStringValue() != null) {
            Activity task =
                    Xpdl2ModelUtil.getActivityById(process,
                            notification.getOldStringValue());
            if (task != null) {
                String id = getDeadlineActivityId(task);

                if (id != null && id.equals(evActivity.getId())) {
                    commands.add(createUnsetActivityDeadlineCommand(event
                            .getEditingDomain(), task));
                }
            }
        }
        /*
         * If a timer event is dragged onto an Activity border, and its the
         * first timer event then it should be set as the activity deadline by
         * default.
         */
        if (notification.getNewStringValue() != null) {
            Activity task =
                    Xpdl2ModelUtil.getActivityById(process,
                            notification.getNewStringValue());
            if (task != null) {
                /*
                 * Check if the task has any timer events already set on its
                 * boundary - if it has then do nothing, otherwise set the new
                 * timer event as the activity deadline.
                 */
                if (EventTriggerType.EVENT_TIMER_LITERAL == EventObjectUtil
                        .getEventTriggerType(evActivity)
                        && getAttachedTimerEventsCount(task) == 1) {
                    commands.add(createSetActivityDeadlineCommand(event
                            .getEditingDomain(), task, evActivity.getId()));
                }
            }
        }
    }

    /**
     * Process the ADD notification.
     * 
     * @param event
     * @param notification
     * @param commands
     *            add {@link Command}s to this list if the model needs to be
     *            fixed as the result of the notification..
     */
    private void processAddNotification(ResourceSetChangeEvent event,
            ENotificationImpl notification, List<Command> commands) {
        /*
         * If the first timer event is added to an Activity border then set it
         * to the activity deadline
         */
        if (notification.getNewValue() instanceof Activity
                && notification.getNotifier() instanceof Process) {
            Activity newActivity = (Activity) notification.getNewValue();
            Activity task = EventObjectUtil.getTaskAttachedTo(newActivity);

            if (task != null
                    && EventObjectUtil.getEventTriggerType(newActivity) == EventTriggerType.EVENT_TIMER_LITERAL) {

                if (getAttachedTimerEventsCount(task) == 1) {
                    // This is the first timer event being added so set it as
                    // the activity deadline
                    commands.add(createSetActivityDeadlineCommand(event
                            .getEditingDomain(), task, newActivity.getId()));
                }
            }

        }
    }

    /**
     * Process the REMOVE notification.
     * 
     * @param event
     * @param notification
     * @param commands
     *            add {@link Command}s to this list if the model needs to be
     *            fixed as the result of the notification.
     */
    private void processRemoveNotification(ResourceSetChangeEvent event,
            ENotificationImpl notification, List<Command> commands) {
        /*
         * If the Activity's deadline timer event is being removed then clear
         * the activity deadline attribute
         */
        if (notification.getOldValue() instanceof Activity
                && notification.getNotifier() instanceof Process) {
            Activity oldActivity = (Activity) notification.getOldValue();
            String taskId = EventObjectUtil.getTaskIdAttachedTo(oldActivity);

            if (taskId != null
                    && EventObjectUtil.getEventTriggerType(oldActivity) == EventTriggerType.EVENT_TIMER_LITERAL) {
                Activity task =
                        Xpdl2ModelUtil.getActivityById((Process) notification
                                .getNotifier(), taskId);

                if (task != null) {
                    String id = getDeadlineActivityId(task);

                    if (id != null && id.equals(oldActivity.getId())) {
                        commands.add(createUnsetActivityDeadlineCommand(event
                                .getEditingDomain(), task));
                    }
                }
            }
        }
    }

    /**
     * Get the count of Timer Events attached to the given Activity.
     * 
     * @param activity
     * @return count
     */
    private int getAttachedTimerEventsCount(Activity activity) {
        Collection<Activity> events =
                Xpdl2ModelUtil.getAttachedEvents(activity);

        int timerEventCount = 0;
        for (Activity eventAct : events) {
            if (EventObjectUtil.getEventTriggerType(eventAct) == EventTriggerType.EVENT_TIMER_LITERAL) {
                ++timerEventCount;
            }
        }
        return timerEventCount;
    }

    /**
     * Get the command to SET the activity deadline on the given activity.
     * 
     * @param ed
     *            editing domain
     * @param activity
     *            Activity to set the activity deadline on
     * @param timerEventId
     *            id of the timer event (the activity deadline event),
     *            <code>null</code> to unset the activity deadline
     * @return
     */
    private Command createSetActivityDeadlineCommand(EditingDomain ed,
            Activity activity, String timerEventId) {
        return Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                activity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ActivityDeadlineEventId(),
                timerEventId);
    }

    /**
     * Get the command to UNSET the activity deadline on the given activity.
     * 
     * @param ed
     *            editing domain
     * @param activity
     *            Activity to set the activity deadline on
     * @return
     */
    private Command createUnsetActivityDeadlineCommand(EditingDomain ed,
            Activity activity) {
        return createSetActivityDeadlineCommand(ed, activity, null);
    }

    /**
     * Get the id of the deadline timer event for the given activity.
     * 
     * @param activity
     * @return id, or <code>null</code> if not set.
     */
    private String getDeadlineActivityId(Activity activity) {
        String id = null;

        Object value =
                Xpdl2ModelUtil.getOtherAttribute(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityDeadlineEventId());
        if (value instanceof String) {
            id = (String) value;
        }

        return id;
    }

}
