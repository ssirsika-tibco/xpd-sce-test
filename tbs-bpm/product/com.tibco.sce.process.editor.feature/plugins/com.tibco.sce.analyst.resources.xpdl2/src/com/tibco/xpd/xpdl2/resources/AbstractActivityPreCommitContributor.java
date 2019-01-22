/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.xpdl2.resources;

import org.eclipse.emf.common.notify.Notification;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * Process pre-commit listener with helper methods for activity-based pre-commit
 * operations.
 * 
 * @author aallway
 * @since 24 May 2012
 */
public abstract class AbstractActivityPreCommitContributor extends
        AbstractProcessPreCommitContributor {

    /**
     * Checks if the notification indicates a connect-FROM-activity
     * 
     * @param notification
     * 
     * @return The activity that has just been is connected FROM.
     */
    public Activity isAddFromConnection(Notification notification) {
        Xpdl2Package xpdlEmfPkg = Xpdl2Package.eINSTANCE;
        Object feature = notification.getFeature();
        int eventType = notification.getEventType();
        Object newValue = notification.getNewValue();
        Object notifier = notification.getNotifier();

        if (Notification.ADD == eventType
                && xpdlEmfPkg.getFlowContainer_Transitions().equals(feature)
                && (newValue instanceof Transition)
                && (notifier instanceof FlowContainer)) {
            /*
             * Transition added return the source activity.
             */
            Transition transition = (Transition) newValue;
            FlowContainer flowContainer = (FlowContainer) notifier;

            if (transition.getFrom() != null) {
                return flowContainer.getActivity(transition.getFrom());
            }

        } else if (Notification.SET == eventType
                && xpdlEmfPkg.getTransition_From().equals(feature)
                && (notifier instanceof Transition)) {
            /*
             * Transition FROM set return the new source of the connection.
             */
            Transition transition = (Transition) notifier;

            if (transition.getFrom() != null
                    && transition.getFlowContainer() != null) {
                return transition.getFlowContainer()
                        .getActivity(transition.getFrom());
            }

        }

        return null;
    }

    /**
     * Checks if the notification indicates a disconnect-FROM-activity
     * 
     * @param notification
     * 
     * @return The activity that has just been is disconnected FROM.
     */
    public Activity isRemoveConnectionFrom(Notification notification) {
        Xpdl2Package xpdlEmfPkg = Xpdl2Package.eINSTANCE;
        Object feature = notification.getFeature();
        int eventType = notification.getEventType();
        Object oldValue = notification.getOldValue();
        Object notifier = notification.getNotifier();

        if (Notification.REMOVE == eventType
                && xpdlEmfPkg.getFlowContainer_Transitions().equals(feature)
                && (oldValue instanceof Transition)
                && (notifier instanceof FlowContainer)) {
            /*
             * Transition deleted, get the activity it was connected from.
             */
            Transition transition = (Transition) oldValue;
            FlowContainer flowContainer = (FlowContainer) notifier;

            if (transition.getFrom() != null) {
                return flowContainer.getActivity(transition.getFrom());
            }

        } else if (Notification.SET == eventType
                && xpdlEmfPkg.getTransition_From().equals(feature)
                && (notifier instanceof Transition)) {
            /*
             * Transition FROM set return the old source of the connection.
             */
            Transition transition = (Transition) notifier;

            if (oldValue instanceof String
                    && transition.getFlowContainer() != null) {
                return transition.getFlowContainer()
                        .getActivity((String) oldValue);
            }

        }

        return null;
    }

    /**
     * Checks if the notification indicates a connect-TO-activity
     * 
     * @param notification
     * 
     * @return The activity that has just been is connected TO.
     */
    public Activity isAddToConnection(Notification notification) {
        Xpdl2Package xpdlEmfPkg = Xpdl2Package.eINSTANCE;
        Object feature = notification.getFeature();
        int eventType = notification.getEventType();
        Object newValue = notification.getNewValue();
        Object notifier = notification.getNotifier();

        if (Notification.ADD == eventType
                && xpdlEmfPkg.getFlowContainer_Transitions().equals(feature)
                && (newValue instanceof Transition)
                && (notifier instanceof FlowContainer)) {

            /*
             * Transition added return the target activity.
             */
            Transition transition = (Transition) newValue;
            FlowContainer flowContainer = (FlowContainer) notifier;

            if (transition.getTo() != null) {
                return flowContainer.getActivity(transition.getTo());
            }

        } else if (Notification.SET == eventType
                && xpdlEmfPkg.getTransition_To().equals(feature)
                && (notifier instanceof Transition)) {
            /*
             * Transition TO set return the new source of the connection.
             */
            Transition transition = (Transition) notifier;

            if (transition.getTo() != null
                    && transition.getFlowContainer() != null) {
                return transition.getFlowContainer()
                        .getActivity(transition.getTo());
            }

        }

        return null;
    }

    /**
     * Checks if the notification indicates a disconnect-TO-activity
     * 
     * @param notification
     * 
     * @return The activity that has just been is disconnected TO.
     */
    public Activity isRemoveConnectionTo(Notification notification) {
        Xpdl2Package xpdlEmfPkg = Xpdl2Package.eINSTANCE;
        Object feature = notification.getFeature();
        int eventType = notification.getEventType();
        Object oldValue = notification.getOldValue();
        Object notifier = notification.getNotifier();

        if (Notification.REMOVE == eventType
                && xpdlEmfPkg.getFlowContainer_Transitions().equals(feature)
                && (oldValue instanceof Transition)
                && (notifier instanceof FlowContainer)) {
            /*
             * Transition deleted, get the activity it was connected to.
             */
            Transition transition = (Transition) oldValue;
            FlowContainer flowContainer = (FlowContainer) notifier;

            if (transition.getTo() != null) {
                return flowContainer.getActivity(transition.getTo());
            }

        } else if (Notification.SET == eventType
                && xpdlEmfPkg.getTransition_To().equals(feature)
                && (notifier instanceof Transition)) {
            /*
             * Transition TO set return the old target of the connection.
             */
            Transition transition = (Transition) notifier;

            if (oldValue instanceof String
                    && transition.getFlowContainer() != null) {
                return transition.getFlowContainer()
                        .getActivity((String) oldValue);
            }

        }

        return null;
    }

    /**
     * @param notification
     * @return The activity whose content is affected by a given change or
     *         <code>null</code> if no activity was affected
     */
    protected Activity isActivityContentChange(Notification notification) {
        return (Activity) getTypedAncestor(notification, Activity.class);
    }

    /**
     * @param notification
     * @return The activity that was added by change the notification or
     *         <code>null</code> if no activity was affected
     */
    protected Activity isAddActivity(Notification notification) {
        if (Notification.ADD == notification.getEventType()
                && Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                        .equals(notification.getFeature())) {
            if (notification.getNewValue() instanceof Activity) {
                return (Activity) notification.getNewValue();
            }
        }
        return null;
    }

    /**
     * @param notification
     * @return The activity that was removed by the change notification or
     *         <code>null</code> if no activity was affected
     */
    protected Activity isRemoveActivity(Notification notification) {
        if (Notification.REMOVE == notification.getEventType()
                && Xpdl2Package.eINSTANCE.getFlowContainer_Activities()
                        .equals(notification.getFeature())) {
            if (notification.getOldValue() instanceof Activity) {
                return (Activity) notification.getOldValue();
            }
        }
        return null;
    }
}
