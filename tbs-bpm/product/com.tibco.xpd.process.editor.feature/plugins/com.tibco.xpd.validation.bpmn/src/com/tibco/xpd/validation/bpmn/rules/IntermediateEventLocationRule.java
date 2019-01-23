/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TriggerType;

/**
 * @author nwilson
 */
public class IntermediateEventLocationRule extends FlowContainerValidationRule {

    /** Invalid event location issue ID. */
    private static final String INVALID_LOCATION = "bpmn.invalidEventLocation"; //$NON-NLS-1$

    /** Invalid Cancel event location issue ID. */
    private static final String INVALID_CANCEL_LOCATION =
            "bpmn.invalidCancelEventLocation"; //$NON-NLS-1$

    /**
     * @param flowContainer
     *            The process to check.
     * @see com.tibco.xpd.validation.xpdl2.rules.FlowContainerValidationRule#validate(FlowContainer)
     */
    @Override
    public void validate(FlowContainer flowContainer) {
        for (Object next : flowContainer.getActivities()) {
            Activity activity = (Activity) next;
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediate = (IntermediateEvent) event;
                TriggerType type = intermediate.getTrigger();
                String targetId = intermediate.getTarget();
                if (targetId == null) {
                    if (TriggerType.CANCEL_LITERAL.equals(type)&& !activity.getIncomingTransitions().isEmpty()) {
                      /*
                       * XPD-6576: This validation will only be raised for cancel events which have incoming flows because we now support cancel event handlers.
                       */
                        addIssue(INVALID_CANCEL_LOCATION, activity);
                    }
                } else {
                    if (TriggerType.CANCEL_LITERAL.equals(type)) {
                        boolean invalidCancel = true;
                        Activity target = flowContainer.getActivity(targetId);
                        if (target != null) {
                            Implementation implementation =
                                    target.getImplementation();
                            if (implementation instanceof SubFlow) {
                                if (target.isIsATransaction()) {
                                    invalidCancel = false;
                                }
                            }
                            BlockActivity block = target.getBlockActivity();
                            if (block != null) {
                                if (target.isIsATransaction()) {
                                    invalidCancel = false;
                                }
                            }
                        }
                        if (invalidCancel) {
                            addIssue(INVALID_CANCEL_LOCATION, activity);
                        }
                    } else {
                        boolean invalid = true;
                        Activity target = flowContainer.getActivity(targetId);
                        if (target != null) {
                            Implementation implementation =
                                    target.getImplementation();
                            if (implementation instanceof Task) {
                                invalid = false;
                            } else if (implementation instanceof SubFlow) {
                                invalid = false;
                            } else if (implementation instanceof No) {
                                invalid = false;
                            } else if (implementation instanceof Reference) {
                                invalid = false;
                            }

                            BlockActivity block = target.getBlockActivity();
                            if (block != null) {
                                invalid = false;
                            }
                        }
                        if (invalid) {
                            addIssue(INVALID_LOCATION, activity);
                        }
                    }
                }
            }
        }
    }

}
