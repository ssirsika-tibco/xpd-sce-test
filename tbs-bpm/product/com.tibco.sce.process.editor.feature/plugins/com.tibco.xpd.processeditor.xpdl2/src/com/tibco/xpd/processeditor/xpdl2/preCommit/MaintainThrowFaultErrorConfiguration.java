/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.resources.AbstractProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.ReplyActivityUtil;
import com.tibco.xpd.xpdl2.util.ThrowErrorEventUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * MaintainThrowFaultErrorConfiguration
 * <p>
 * Pre-commit listener that does a couple of things to look after Throw Error
 * End Events configured to throw fault for a selected request activity.
 * Currently this means...
 * 
 * <li>Reference to request activity is removed if request activity is removed.</li>
 * <li>Reference to request activity is removed if request activity becomes a
 * non-request activity.</li>
 * </p>
 * 
 * <p>
 * Note that data mappings for fault-message auto-generating Throw error end
 * events are handled by {@link PortTypeGenerationCmdContributor}
 * </p>
 * 
 * @author aallway
 * @since 3.3 (2 Dec 2009)
 */
public class MaintainThrowFaultErrorConfiguration extends
        AbstractProcessPreCommitContributor {

    /*
     * This is the varying set of features that change when web-service
     * operation can be set/cleared on an activity that's an incoming request
     * operation.
     */
    private Set<Object> possibleChangeWebServiceOperationFeatures;

    public MaintainThrowFaultErrorConfiguration() {
        possibleChangeWebServiceOperationFeatures = new HashSet<Object>();

        possibleChangeWebServiceOperationFeatures.add(Xpdl2Package.eINSTANCE
                .getActivity_Implementation());
        possibleChangeWebServiceOperationFeatures.add(Xpdl2Package.eINSTANCE
                .getTaskReceive_WebServiceOperation());
        possibleChangeWebServiceOperationFeatures
                .add(XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_PortTypeOperation());
        possibleChangeWebServiceOperationFeatures
                .add(XpdExtensionPackage.eINSTANCE
                        .getPortTypeOperation_OperationName());
        possibleChangeWebServiceOperationFeatures.add(Xpdl2Package.eINSTANCE
                .getTriggerResultMessage_WebServiceOperation());
        possibleChangeWebServiceOperationFeatures.add(Xpdl2Package.eINSTANCE
                .getWebServiceOperation_OperationName());
        possibleChangeWebServiceOperationFeatures.add(Xpdl2Package.eINSTANCE
                .getWebServiceOperation_Service());

    }

    @Override
    protected boolean allowContributionRecursion(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications) {
        /* MUST BE REALLY CAREFUL NOT TO INFINITE-RECURS in contributeCommand()! */
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor#contributeCommand
     * (org.eclipse.emf.transaction.ResourceSetChangeEvent,
     * java.util.Collection)
     */
    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {
        EditingDomain editingDomain = event.getEditingDomain();

        /*
         * Build a list of throw error event activities to remove request
         * activity reference from
         */
        Set<Activity> removeRequestActivityReferenceFrom =
                new HashSet<Activity>();

        /*
         * a list of throw error event activities to remove request activity
         * reference from
         */
        Set<Activity> removeFaultDetailFrom = new HashSet<Activity>();

        /*
         * A list of request activities changing auto-generated status
         */
        Set<Activity> changedGeneratedStatusRequestActivities =
                new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {
            // outputNotfication(notification);

            if (notification.getNotifier() instanceof EObject) {
                EObject notifier = (EObject) notification.getNotifier();

                int eventType = notification.getEventType();
                Object feature = notification.getFeature();
                Object oldValue = notification.getOldValue();
                Object newValue = notification.getNewValue();

                Process affectedProcess = Xpdl2ModelUtil.getProcess(notifier);
                Activity affectedActivity =
                        Xpdl2ModelUtil.getParentActivity(notifier);

                if (Notification.REMOVE == eventType
                        && Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                                .equals(feature)
                        && oldValue instanceof Activity) {
                    /*
                     * When activity removed, need to remove references from
                     * throw error events.
                     */
                    if (affectedProcess != null) {
                        List<Activity> errorEvents =
                                ThrowErrorEventUtil
                                        .getThrowErrorEvents(affectedProcess,
                                                ((Activity) oldValue).getId());
                        removeRequestActivityReferenceFrom.addAll(errorEvents);
                    }

                } else if (affectedActivity != null
                        && affectedProcess != null
                        && !ReplyActivityUtil
                                .isIncomingRequestActivity(affectedActivity)) {
                    /*
                     * Any change to an activity that causes is it to become NOT
                     * a request activity, we will have to remove the request
                     * activity reference.
                     */
                    List<Activity> errorEvents =
                            ThrowErrorEventUtil
                                    .getThrowErrorEvents(affectedProcess,
                                            affectedActivity.getId());
                    if (!errorEvents.isEmpty()) {

                        removeRequestActivityReferenceFrom.addAll(errorEvents);
                    }

                } else if (Notification.SET == eventType
                        && XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Generated().equals(feature)) {
                    if (affectedActivity != null
                            && ReplyActivityUtil
                                    .isIncomingRequestActivity(affectedActivity)) {
                        changedGeneratedStatusRequestActivities
                                .add(affectedActivity);
                    }

                } else if (Notification.SET == eventType) {
                    /*
                     * Various features can be set when web service operation is
                     * changed / cleared / set default - have to check for all
                     * of them!
                     */
                    if (affectedActivity != null
                            && possibleChangeWebServiceOperationFeatures
                                    .contains(feature)) {
                        /*
                         * It's a change to an incoming request activity that is
                         * a change in incoming request operation so reset the
                         * fault message info.
                         */
                        List<Activity> errorEvents =
                                ThrowErrorEventUtil
                                        .getThrowErrorEvents(affectedProcess,
                                                affectedActivity.getId());
                        if (!errorEvents.isEmpty()) {
                            removeFaultDetailFrom.addAll(errorEvents);
                        }
                    }
                }
            }
        }

        /*
         * Create commands to fix anything necessary up.
         */
        CompoundCommand cmd = new CompoundCommand();

        /*
         * First off all the throw error event activities that need JUST the
         * fault name and message mappings resetting (operation changed in
         * request activity)
         */
        for (Activity errorEventActivity : removeFaultDetailFrom) {
            /*
             * Don't bother if we're going to remove the whole reference later
             * anyway!
             */
            if (!removeRequestActivityReferenceFrom
                    .contains(errorEventActivity)) {

                Activity requestActivity =
                        ThrowErrorEventUtil
                                .getRequestActivity(errorEventActivity);
                if (requestActivity != null) {
                    /*
                     * If the operation changes for a request activity that is
                     * generating it's operation AND it hasn't just been changed
                     * to auto-generate then we DO NOT want to wipe out the
                     * user's carefully selected fault message for no reason :o)
                     */
                    if (!Xpdl2ModelUtil
                            .isGeneratedRequestActivity(requestActivity)
                            || changedGeneratedStatusRequestActivities
                                    .contains(requestActivity)) {

                        /* Also do not want to wipe implemented error event! */
                        if (!ProcessInterfaceUtil
                                .isEventImplemented(errorEventActivity)) {
                            /*System.out
                                    .println("Throw error event's Referenced Request Activity operations has changed, removing fault name & message details: " + errorEventActivity.getId()); //$NON-NLS-1$
                            */
                            Command c =
                                    ThrowErrorEventUtil
                                            .getConfigureAsFaultMessageErrorCommand(editingDomain,
                                                    errorEventActivity,
                                                    requestActivity.getId(),
                                                    null);
                            if (c != null) {
                                cmd.append(c);
                            }
                        }
                    }
                }
            }
        }

        /*
         * Then add commands to remove the request activity reference
         * altogether.
         */
        for (Activity errorEventActivity : removeRequestActivityReferenceFrom) {
            if (!Xpdl2ModelUtil.isEventImplemented(errorEventActivity)) {
                /*                System.out
                                        .println("Throw error event's Referenced Activity is Removed or is Request Activity no more, removing reference: " + errorEventActivity.getId()); //$NON-NLS-1$
                */
                Command c =
                        ThrowErrorEventUtil
                                .getConfigureAsFaultMessageErrorCommand(editingDomain,
                                        errorEventActivity,
                                        null,
                                        null);
                if (c != null) {
                    cmd.append(c);
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }
        return null;
    }

}
