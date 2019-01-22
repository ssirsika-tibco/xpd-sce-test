/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.preCommit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Join;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Split;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TransitionRef;
import com.tibco.xpd.xpdl2.TransitionRestriction;
import com.tibco.xpd.xpdl2.XorType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.resources.IProcessPreCommitContributor;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * MaintainTransitionRestrictions
 * <p>
 * XPDL Pre-Commit command provider that maintains
 * xpdl:Activity/xpdl:TransitionRestrictions according to current incoming /
 * outgoing sequence flows.
 * <p>
 * 
 * @author aallway
 */
public class MaintainTransitionRestrictions implements
        IProcessPreCommitContributor {

    private static final EAttribute TRANSITION_TO_FEATURE =
            Xpdl2Package.eINSTANCE.getTransition_To();

    private static final EAttribute TRANSITION_FROM_FEATURE =
            Xpdl2Package.eINSTANCE.getTransition_From();

    private static boolean logging = false;

    /**
     * 
     */
    public MaintainTransitionRestrictions() {
        // TODO Auto-generated constructor stub
    }

    public Command contributeCommand(ResourceSetChangeEvent event,
            Collection<ENotificationImpl> notifications)
            throws RollbackException {

        log("==> " + this.getClass().getSimpleName() + "Notifications..."); //$NON-NLS-1$ //$NON-NLS-2$

        //
        // In order to keep things simple we will create a list of
        // activities for which we wish to recalculate the
        // TransitionRestrictions for.
        //
        Set<Activity> activities = new HashSet<Activity>();

        for (ENotificationImpl notification : notifications) {
            //            log("      " + getEventType(notification) + ": Notifier(" + notification.getNotifier().getClass().getSimpleName() //$NON-NLS-1$ //$NON-NLS-2$
            //                    + ")  Feature: (" + notification.getFeature() //$NON-NLS-1$
            //                    + ")  NewValue(" + (notification.getNewValue() == null ? "" : notification.getNewValue().getClass().getSimpleName()) + ")" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            //                    + ")  OldValue(" + (notification.getOldValue() == null ? "" : notification.getOldValue().getClass().getSimpleName()) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

            int eventType = notification.getEventType();
            if (eventType == Notification.ADD) {
                Object newValue = notification.getNewValue();

                if (newValue instanceof Transition) {
                    // Obviously if we are adding a transition then we need to
                    // look
                    // at the source and target.
                    addSourceAndTargetActivity(notification.getNotifier(),
                            (Transition) newValue,
                            activities);

                } else if (newValue instanceof Activity) {
                    // Less obviously we also need to check activities that are
                    // just being added (because they may currently have
                    // transition restrictions that they now should not have).
                    activities.add((Activity) newValue);
                }

            } else if (eventType == Notification.REMOVE) {
                Object oldValue = notification.getOldValue();

                if (oldValue instanceof Transition) {
                    // Check the source and target of the transition being
                    // removed.
                    addSourceAndTargetActivity(notification.getNotifier(),
                            (Transition) oldValue,
                            activities);
                }

            } else if (eventType == Notification.SET) {
                //
                // Check for set of the to/from attribute and check both new and
                // old activity that transition is connected to.
                Object notifier = notification.getNotifier();

                if (notifier instanceof Transition) {
                    Object feature = notification.getFeature();

                    if (feature == TRANSITION_FROM_FEATURE
                            || feature == TRANSITION_TO_FEATURE) {
                        Object newValue = notification.getNewValue();
                        if (newValue instanceof String) {
                            addActivity(((Transition) notifier)
                                    .getFlowContainer(),
                                    (String) newValue,
                                    activities);
                        }

                        Object oldValue = notification.getOldValue();
                        if (oldValue instanceof String) {
                            addActivity(((Transition) notifier)
                                    .getFlowContainer(),
                                    (String) oldValue,
                                    activities);
                        }

                    }
                } else if (notifier instanceof Activity) {
                    // Change of gateway type by reset of route element.
                    if (notification.getNewValue() instanceof Route) {
                        activities.add((Activity) notifier);
                    }
                } else if (notifier instanceof Route) {
                    if (notification.getFeature() == Xpdl2Package.eINSTANCE
                            .getRoute_GatewayType()) {
                        if (((Route) notifier).eContainer() instanceof Activity) {
                            activities.add((Activity) ((Route) notifier)
                                    .eContainer());
                        }
                    }
                }
            }

        }

        if (activities.size() > 0) {
            //
            // Check and build commands to put Actitity/TransitionRestriction
            // elements right.
            //
            TransactionalEditingDomain editingDomain = event.getEditingDomain();

            CompoundCommand cmd =
                    new CompoundCommand("Update TransitionRestriction Info"); //$NON-NLS-1$

            log("    Affected Activities...."); //$NON-NLS-1$

            for (Activity act : activities) {
                log("        " + act.getName()); //$NON-NLS-1$

                updateTransitionRestrictions(editingDomain, act, cmd);
            }

            if (!cmd.isEmpty()) {
                log("<== " + this.getClass().getSimpleName()); //$NON-NLS-1$ 
                return cmd;
            }
        }

        log("<== " + this.getClass().getSimpleName() + ": No changes required"); //$NON-NLS-1$ //$NON-NLS-2$ 
        return null;
    }

    /**
     * Check and build commands to put Activity/TransitionRestriction elements
     * right.
     * 
     * @param editingDomain
     * @param act
     * @param cmd
     */
    private void updateTransitionRestrictions(
            TransactionalEditingDomain editingDomain, Activity act,
            CompoundCommand cmd) {
        //
        // Get current in and out transitions...
        //
        FlowContainer container = act.getFlowContainer();

        List<Transition> inTrans =
                Xpdl2ModelUtil.getIncomingTransitions(act.getId(), container);
        List<Transition> outTrans =
                Xpdl2ModelUtil.getOutgoingTransitions(act.getId(), container);

        //
        // Get the current join and split restrictions - note that the xpdl
        // model is hideous (at least for when all you're doing is modelling
        // BPMN). It allows for multiple split and join sets (no point for us
        // because BPMN doesn't support groups of outgoing / incoming
        // transitions with different split/join types).
        //
        // So we will HOPE other people have the same point of view and allow
        // the Join and/or Split to be in the same or different
        // TransitionRestriction elements. We will ignore anything but the first
        // join / split we come across.
        //
        // When we have to create a Join/Split we will add it to the first
        // transitionRestriction element (and create that if necessary too).
        // If the Join / Split already exists then we will continue to use that.
        TransitionRestriction firstTransitionRestriction = null;

        Join joinRestriction = null;
        Split splitRestriction = null;

        EList<TransitionRestriction> restrictions =
                act.getTransitionRestrictions();

        for (TransitionRestriction restriction : restrictions) {
            boolean unnecessaryTransRest = true;

            Join jr = restriction.getJoin();
            if (jr != null) {
                if (joinRestriction == null) {
                    joinRestriction = jr;

                    // Don't delete this restriction as it's one that's in use
                    // by a join/split we are still using.
                    unnecessaryTransRest = false;
                }
            }

            Split sr = restriction.getSplit();
            if (sr != null) {
                if (splitRestriction == null) {
                    splitRestriction = sr;
                    unnecessaryTransRest = false;
                }
            }

            if (unnecessaryTransRest) {
                // This transition restriction is superfluous to (our) handling
                // of transition restrictions, get rid of it!
                // i.e. it is not in use by the first join or split we came
                // across so we can ditch it.
                log("        - Removing unwanted TransitionRestriction"); //$NON-NLS-1$
                cmd.append(RemoveCommand.create(editingDomain, restriction));

            } else {
                // Keep track of first trans restriction which we will use to
                // add a new join / split if we need to.
                firstTransitionRestriction = restriction;
            }

        }

        // 
        // Create a TranmsitionRestriction element if we need it and there isn't
        // one already.
        //
        if ((inTrans != null && inTrans.size() > 1)
                || (outTrans != null && outTrans.size() > 1)) {
            if (firstTransitionRestriction == null) {
                // Create the TransitionRestriction in case we need it.
                firstTransitionRestriction =
                        Xpdl2Factory.eINSTANCE.createTransitionRestriction();

                log("        - Creating first transition restriction"); //$NON-NLS-1$

                cmd.append(AddCommand.create(editingDomain,
                        act,
                        Xpdl2Package.eINSTANCE
                                .getActivity_TransitionRestrictions(),
                        firstTransitionRestriction));
            }
        }

        //
        // Validate and Amend Incoming Transitions and Join element.
        //
        if (inTrans != null && inTrans.size() > 1) {
            // 
            // We have multiple incoming transitions create/update the Join
            // element.
            //
            if (joinRestriction == null) {
                // Add a JoinRestriction element if we don't have one already.
                joinRestriction = Xpdl2Factory.eINSTANCE.createJoin();

                log("        - Creating Join for first time"); //$NON-NLS-1$

                cmd.append(SetCommand.create(editingDomain,
                        firstTransitionRestriction,
                        Xpdl2Package.eINSTANCE.getTransitionRestriction_Join(),
                        joinRestriction));
            }

            // Update the join type information.
            updateJoinRestrictionType(editingDomain, act, cmd, joinRestriction);

        } else {
            //
            // If there are not multiple incoming transitions then remove the
            // join restriction if it is there.
            //
            if (joinRestriction != null) {
                log("        - Removing no-longer-necessary Join restriction"); //$NON-NLS-1$
                cmd
                        .append(RemoveCommand.create(editingDomain,
                                joinRestriction));
            }
        }

        //
        // Validate and Amend Incoming Transitions and Join element.
        //
        if (outTrans != null && outTrans.size() > 1) {
            // 
            // We have multiple outgoing transitions create/update the split
            // element.
            //
            if (splitRestriction == null) {
                // Add a JoinRestriction element if we don't have one already.
                splitRestriction = Xpdl2Factory.eINSTANCE.createSplit();

                log("        - Creating Split for first time"); //$NON-NLS-1$

                cmd.append(SetCommand
                        .create(editingDomain,
                                firstTransitionRestriction,
                                Xpdl2Package.eINSTANCE
                                        .getTransitionRestriction_Split(),
                                splitRestriction));
            }

            // Update the split type information.
            updateSplitRestrictionType(editingDomain,
                    act,
                    cmd,
                    splitRestriction,
                    outTrans);

        } else {
            //
            // If there are not multiple incoming transitions then remove the
            // split restriction if it is there.
            //
            if (splitRestriction != null) {
                log("        - Removing no-longer-necessary Split restriction"); //$NON-NLS-1$
                cmd.append(RemoveCommand
                        .create(editingDomain, splitRestriction));
            }
        }

        //
        // If there are neither multiple in or multiple out then remove
        // TransitionRestriction itself.
        //
        if ((inTrans == null || inTrans.size() < 2)
                && (outTrans == null || outTrans.size() < 2)) {
            if (firstTransitionRestriction != null) {
                log("        - Removing no-longer-necessary first TransitionRestriction"); //$NON-NLS-1$
                cmd.append(RemoveCommand.create(editingDomain,
                        firstTransitionRestriction));
            }
        }

        return;
    }

    /**
     * Update the Type and Exclusive type in the Split Restriction to be correct
     * for the given activity.
     * <p>
     * Then update the transition refs to match the outgoing transitions.
     * 
     * @param editingDomain
     * @param act
     * @param cmd
     * @param splitRestriction
     * @param outTrans
     */
    private void updateSplitRestrictionType(
            TransactionalEditingDomain editingDomain, Activity act,
            CompoundCommand cmd, Split splitRestriction,
            List<Transition> outTrans) {
        // 
        // Update split type info...
        //
        JoinSplitType newSplitType = null;
        ExclusiveType newExcType = null;

        Route gateway = act.getRoute();
        if (gateway != null) {
            //
            // For gateway's we will mimic the type defined in the route
            // element for the TransitionRestriction
            //
            // (if no gatewayType is set then we probably have a gateway
            // defined by a different product so we'll do nothing until the
            // user changes the gateway type at which time we will have a
            // gateway type set.)
            JoinSplitType gatewayType = gateway.getGatewayType();
            if (gatewayType != null) {

                newSplitType = gatewayType;

                // Update ExclusiveType
                if (JoinSplitType.EXCLUSIVE_LITERAL.equals(gatewayType)) {
                    newExcType = ExclusiveType.DATA;

                    if (gateway.isSetExclusiveType()) {
                        newExcType = gateway.getExclusiveType();

                    } else if (gateway.isSetDeprecatedXorType()) {
                        if (XorType.EVENT
                                .equals(gateway.getDeprecatedXorType())) {
                            newExcType = ExclusiveType.EVENT;
                        }
                    } else if (gateway.isSetDeprecatedInstantiate()) {
                        if (gateway.isDeprecatedInstantiate()) {
                            newExcType = ExclusiveType.EVENT;
                        }
                    }

                } else {
                    newExcType = null;
                }
            }

        } else {
            // 
            // For non-gateway activities, Splits are ALWAYS Inclusive
            //
            newSplitType = JoinSplitType.INCLUSIVE_LITERAL;
            newExcType = null;
        }

        if (newSplitType != null) {
            if (!splitRestriction.isSetType()
                    || !newSplitType.equals(splitRestriction.getType())) {
                log("        - Resetting Split Type to: " + newSplitType); //$NON-NLS-1$
                cmd.append(SetCommand.create(editingDomain,
                        splitRestriction,
                        Xpdl2Package.eINSTANCE.getSplit_Type(),
                        newSplitType));
            }

            if (newExcType == null) {
                if (splitRestriction.isSetExclusiveType()) {
                    log("        - Resetting Split Exclusive  Type to: " + newExcType); //$NON-NLS-1$
                    cmd.append(SetCommand.create(editingDomain,
                            splitRestriction,
                            Xpdl2Package.eINSTANCE.getSplit_ExclusiveType(),
                            SetCommand.UNSET_VALUE));
                }

            } else if (!splitRestriction.isSetExclusiveType()
                    || !newExcType.equals(splitRestriction.getExclusiveType())) {
                log("        - Resetting Split Exclusive  Type to: " + newExcType); //$NON-NLS-1$
                cmd.append(SetCommand.create(editingDomain,
                        splitRestriction,
                        Xpdl2Package.eINSTANCE.getSplit_ExclusiveType(),
                        newExcType));
            }
        }

        //
        // Update the Transition refs to match the list of transitions
        // NOTE: It is important to preserve the order of existing entries (as
        // this is significant for certain xplit types such as Exclusive
        // (if/elseif/elseif)
        //

        // Use a late execute compound command otherwise multiple removes etc do
        // not work properly (removeCommand notes what index to remove when it
        // is constructed).
        LateExecuteCompoundCommand updateRefsCmd =
                new LateExecuteCompoundCommand();

        // Remove entries in current refs that are no longer connected.
        EList<TransitionRef> currentRefs = splitRestriction.getTransitionRefs();

        for (TransitionRef ref : currentRefs) {
            String id = ref.getId();
            boolean foundTransitionForRef = false;

            if (id != null) {
                for (Transition trans : outTrans) {
                    if (id.equals(trans.getId())) {
                        foundTransitionForRef = true;
                        break;
                    }
                }
            }

            if (!foundTransitionForRef || id == null) {
                log("        - Removing unrequired TransitionRef: " + id);//$NON-NLS-1$
                updateRefsCmd.append(RemoveCommand.create(editingDomain, ref));
            }
        }

        // Add entries that are no longer there.
        for (Transition trans : outTrans) {
            String id = trans.getId();
            boolean foundRefForTransition = false;

            for (TransitionRef ref : currentRefs) {
                if (id.equals(ref.getId())) {
                    foundRefForTransition = true;
                    break;
                }
            }

            if (!foundRefForTransition) {
                TransitionRef ref =
                        Xpdl2Factory.eINSTANCE.createTransitionRef();
                ref.setId(id);

                log("        - Adding required TransitionRef: " + id);//$NON-NLS-1$

                updateRefsCmd.append(AddCommand.create(editingDomain,
                        splitRestriction,
                        Xpdl2Package.eINSTANCE.getSplit_TransitionRefs(),
                        ref));
            }
        }

        if (!updateRefsCmd.isEmpty()) {
            cmd.append(updateRefsCmd);
        }

        return;
    }

    /**
     * Update the Type and Exclusive type in the Join Restriction to be correct
     * for the given activity.
     * 
     * @param editingDomain
     * @param act
     * @param cmd
     * @param joinRestriction
     */
    private void updateJoinRestrictionType(
            TransactionalEditingDomain editingDomain, Activity act,
            CompoundCommand cmd, Join joinRestriction) {
        // 
        // Update join type info...
        //
        JoinSplitType newJoinType = null;
        ExclusiveType newExcType = null;

        Route gateway = act.getRoute();
        if (gateway != null) {
            //
            // For gateway's we will mimic the type defined in the route
            // element for the TransitionRestriction
            //
            // (if no gatewayType is set then we probably have a gateway
            // defined by a different product so we'll do nothing until the
            // user changes the gateway type at which time we will have a
            // gateway type set.)
            JoinSplitType gatewayType = gateway.getGatewayType();
            if (gatewayType != null) {

                newJoinType = gatewayType;

                // Update ExclusiveType
                if (JoinSplitType.EXCLUSIVE_LITERAL.equals(gatewayType)) {
                    newExcType = ExclusiveType.DATA;

                    if (gateway.isSetExclusiveType()) {
                        newExcType = gateway.getExclusiveType();

                    } else if (gateway.isSetDeprecatedXorType()) {
                        if (XorType.EVENT
                                .equals(gateway.getDeprecatedXorType())) {
                            newExcType = ExclusiveType.EVENT;
                        }
                    } else if (gateway.isSetDeprecatedInstantiate()) {
                        if (gateway.isDeprecatedInstantiate()) {
                            newExcType = ExclusiveType.EVENT;
                        }
                    }

                } else {
                    newExcType = null;
                }
            }

        } else {
            // 
            // For non-gateway activities, Joins are ALWAYS Exclusive
            //
            newJoinType = JoinSplitType.EXCLUSIVE_LITERAL;
            newExcType = ExclusiveType.DATA;
        }

        if (newJoinType != null) {
            if (!joinRestriction.isSetType()
                    || !newJoinType.equals(joinRestriction.getType())) {
                log("        - Resetting Join Type to: " + newJoinType); //$NON-NLS-1$
                cmd.append(SetCommand.create(editingDomain,
                        joinRestriction,
                        Xpdl2Package.eINSTANCE.getJoin_Type(),
                        newJoinType));
            }

            if (newExcType == null) {
                if (joinRestriction.isSetExclusiveType()) {
                    log("        - Resetting Join Exclusive Type to: " + newExcType); //$NON-NLS-1$
                    cmd.append(SetCommand.create(editingDomain,
                            joinRestriction,
                            Xpdl2Package.eINSTANCE.getJoin_ExclusiveType(),
                            SetCommand.UNSET_VALUE));
                }

            } else if (!joinRestriction.isSetExclusiveType()
                    || newExcType.equals(joinRestriction.getExclusiveType())) {
                log("        - Resetting Join Exclusive Type to: " + newExcType); //$NON-NLS-1$
                cmd.append(SetCommand.create(editingDomain,
                        joinRestriction,
                        Xpdl2Package.eINSTANCE.getJoin_ExclusiveType(),
                        newExcType));
            }
        }

        return;
    }

    /**
     * Add the source and target activities for the given transition to the
     * given list of activities.
     * 
     * @param container
     * @param transition
     * @param activities
     */
    private void addSourceAndTargetActivity(Object container,
            Transition transition, Set<Activity> activities) {

        if (container instanceof FlowContainer) {
            String from = transition.getFrom();
            if (from == null) {
                from = ""; //$NON-NLS-1$
            }

            String to = transition.getTo();
            if (to == null) {
                to = ""; //$NON-NLS-1$
            }

            for (Activity act : ((FlowContainer) container).getActivities()) {
                String id = act.getId();
                if (from.equals(id)) {
                    activities.add(act);
                } else if (to.equals(id)) {
                    activities.add(act);
                }
            }
        }

        return;
    }

    /**
     * Add the activity with the given id to the list of activities.
     * 
     * @param container
     * @param id
     * @param activities
     */
    private void addActivity(Object container, String id,
            Set<Activity> activities) {
        if (container instanceof FlowContainer) {
            Activity act = ((FlowContainer) container).getActivity(id);
            if (act != null) {
                activities.add(act);
            }
        }
        return;
    }

    private String getEventType(ENotificationImpl notification) {
        switch (notification.getEventType()) {
        case Notification.CREATE:
            return "CREATE"; //$NON-NLS-1$
        case Notification.SET:
            return "SET"; //$NON-NLS-1$
        case Notification.UNSET:
            return "UNSET"; //$NON-NLS-1$
        case Notification.ADD:
            return "ADD"; //$NON-NLS-1$
        case Notification.REMOVE:
            return "REMOVE"; //$NON-NLS-1$
        case Notification.ADD_MANY:
            return "ADD_MANY"; //$NON-NLS-1$
        case Notification.REMOVE_MANY:
            return "REMOVE_MANY"; //$NON-NLS-1$
        case Notification.MOVE:
            return "MOVE"; //$NON-NLS-1$
        case Notification.REMOVING_ADAPTER:
            return "REMOVING_ADAPTER"; //$NON-NLS-1$
        case Notification.RESOLVE:
            return "RESOLVE"; //$NON-NLS-1$
        case Notification.EVENT_TYPE_COUNT:
            return "EVENT_TYPE_COUNT"; //$NON-NLS-1$
        }
        return notification.getEventType() + "<Unknown>"; //$NON-NLS-1$

    }

    private void log(String msg) {
        if (logging) {
            System.out.println(msg);
        }
    }

}
