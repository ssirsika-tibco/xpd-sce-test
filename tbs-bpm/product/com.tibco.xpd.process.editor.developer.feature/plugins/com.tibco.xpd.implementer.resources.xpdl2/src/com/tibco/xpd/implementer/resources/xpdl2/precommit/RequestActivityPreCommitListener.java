package com.tibco.xpd.implementer.resources.xpdl2.precommit;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.resources.AbstractActivityPreCommitContributor;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Pre-commit listener for Request Activity.
 * 
 * 
 * @author kthombar
 * @since Dec 15, 2014
 */
public class RequestActivityPreCommitListener extends
        AbstractActivityPreCommitContributor implements
        IProcessPreCommitContributor {

    private static boolean loggingOn = false;

    @Override
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        CompoundCommand cmd = new CompoundCommand();

        for (ENotificationImpl notification : notifications) {
            /*
             * logging pre-commit listener so that changes are easy to track.
             */
            if (loggingOn) {
                outputNotfication(notification);
            }

            /*
             * Remove the "Correlate Immediately" config from model if the
             * activity is no longer is an correlating activity.
             */
            removeCorrelateImmediatelyConfigFromNonCorrelatingActivities(notification,
                    event.getEditingDomain(),
                    cmd);

            /*
             * Remove the "Reply Immediately with Process Id" config from model
             * if the start message event is an "Correlating Activity".
             */
            removeReplyWithProcessIdConfigFromCorrelatingStartMessageEvents(notification,
                    event.getEditingDomain(),
                    cmd);

            /*
             * Remove the "Correlation Timeout" config from model if the
             * activity is no longer a correlating activity.
             */
            removeCorrelationTimeoutConfigFromNonCorrelatingActivities(notification,
                    event.getEditingDomain(),
                    cmd);

        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Removes the "Correlation Timeout" config from model if the activity is no
     * longer a correlating activity.
     * 
     * @param notifications
     * @param editingDomain
     * @param cmd
     */
    private void removeCorrelationTimeoutConfigFromNonCorrelatingActivities(
            ENotificationImpl notification,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {

        /*
         * If the activity is dragged
         */
        Activity activity = isActivityContentChange(notification);

        if (activity == null) {
            /*
             * if the connection to the activity is removed.
             */
            activity = isRemoveConnectionTo(notification);
        }

        if (activity != null) {

            if (!Xpdl2ModelUtil.isCorrelatingActivity(activity)) {

                /*
                 * check if the "Correlation Timeout" attribute is present
                 */
                Object corrTimeout =
                        Xpdl2ModelUtil.getOtherElement(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CorrelationTimeout());

                if (corrTimeout != null) {
                    /*
                     * remove the attribute.
                     */
                    cmd.append(Xpdl2ModelUtil
                            .getRemoveOtherElementCommand(editingDomain,
                                    activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_CorrelationTimeout(),
                                    corrTimeout));

                    // Logging
                    log("** Precommit Listener 'RequestActivityPreCommitListener' removed the 'CorrelationTimeout'(Activity/CorrelationTimeout) element from the activity '" //$NON-NLS-1$
                            + activity.getName()
                            + "' as it was no longer applicable. **"); //$NON-NLS-1$ 
                }
            }
        }
    }

    /**
     * Removes the "Reply Immediately with Process Id" config from model if the
     * start message event is is an "Correlating Activity".
     * 
     * @param notifications
     * @param editingDomain
     * @param cmd
     */
    private void removeReplyWithProcessIdConfigFromCorrelatingStartMessageEvents(
            ENotificationImpl notification,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {
        /*
         * If the activity is dragged to embedded or event sub process.
         */
        Activity activity = isActivityContentChange(notification);

        if (activity != null) {

            if (activity.getEvent() instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) activity.getEvent();

                TriggerResultMessage triggerResultMessage =
                        startEvent.getTriggerResultMessage();
                if (triggerResultMessage != null
                        && !(activity.eContainer() instanceof Process)) {
                    /*
                     * We want to remove the config if the start message event
                     * is not one that starts a process (i.e. is at top level of
                     * process
                     */
                    /*
                     * check if the "Reply Immediately with Process Id"
                     * attribute is present
                     */
                    boolean isAttributeSet =
                            triggerResultMessage
                                    .eIsSet(XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ReplyImmediately());

                    if (isAttributeSet) {
                        /*
                         * Remove the attribute only if it is present.
                         */
                        cmd.append(Xpdl2ModelUtil
                                .getSetOtherAttributeCommand(editingDomain,
                                        triggerResultMessage,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ReplyImmediately(),
                                        null));

                        // Logging
                        log("** Precommit Listener 'RequestActivityPreCommitListener' removed the 'Reply Immediately'(Activity/Event/StartEvent/TriggerResultMessage/ReplyImmediate) attribute from the activity '" //$NON-NLS-1$
                                + activity.getName()
                                + "' as it was no longer applicable. **"); //$NON-NLS-1$ 
                    }

                }
            }
        }
    }

    /**
     * Removes the "Correlate Immediately" config from model if the activity is
     * no longer is an correlating activity.
     * 
     * @param notifications
     * @param editingDomain
     * @param cmd
     */
    private void removeCorrelateImmediatelyConfigFromNonCorrelatingActivities(
            ENotificationImpl notification,
            TransactionalEditingDomain editingDomain, CompoundCommand cmd) {
        /*
         * If the activity is dragged
         */
        Activity activity = isActivityContentChange(notification);

        if (activity == null) {
            /*
             * if the connection to the activity is removed.
             */
            activity = isRemoveConnectionTo(notification);
        }

        if (activity != null) {

            if (!Xpdl2ModelUtil.isCorrelatingActivity(activity)) {
                /*
                 * Go ahead and remove the attribute from the model only if the
                 * activity is no longer is an correlating activity.
                 */
                Event event = activity.getEvent();

                if (event != null) {

                    TriggerResultMessage triggerResultMessage =
                            EventObjectUtil.getTriggerResultMessage(activity);

                    /*
                     * XPD-7539 Message Events can be REST sdervce as well as
                     * WebService; only want to deal with WebService
                     */
                    if (triggerResultMessage != null
                            && EventObjectUtil
                                    .isWebServiceRelatedEvent(activity)) {

                        boolean isAttributeSet =
                                triggerResultMessage
                                        .eIsSet(XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CorrelateImmediately());

                        if (isAttributeSet) {
                            /*
                             * Remove the attribute only if it is present.
                             */

                            cmd.append(Xpdl2ModelUtil
                                    .getSetOtherAttributeCommand(editingDomain,
                                            triggerResultMessage,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CorrelateImmediately(),
                                            null));

                            // Logging
                            log("** Precommit Listener 'RequestActivityPreCommitListener' removed the 'CorrelateImmediately' attribute from the activity '" //$NON-NLS-1$
                                    + activity.getName()
                                    + "' as it was no longer applicable. **"); //$NON-NLS-1$ 
                        }
                    }
                } else {
                    Implementation impl = activity.getImplementation();

                    if (impl instanceof Task) {

                        Task task = (Task) impl;

                        TaskReceive taskReceive = task.getTaskReceive();

                        if (taskReceive != null) {

                            boolean isAttributeSet =
                                    taskReceive
                                            .eIsSet(XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_CorrelateImmediately());

                            if (isAttributeSet) {
                                /*
                                 * Remove the attribute only if it is present.
                                 */

                                cmd.append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                taskReceive,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_CorrelateImmediately(),
                                                null));

                                // Logging
                                log("** Precommit Listener 'RequestActivityPreCommitListener' removed the 'CorrelateImmediately' attribute from the activity '" //$NON-NLS-1$
                                        + activity.getName()
                                        + "' as it was no longer applicable. **"); //$NON-NLS-1$ 
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param msg
     *            the String to log.
     */
    private static void log(String msg) {
        if (loggingOn) {
            System.out.println(msg);
        }
    }
}
