/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil;
import com.tibco.xpd.processeditor.xpdl2.util.EventObjectUtil.ReturnCatchThrowTypes;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check whether a catch signal event with a signal name
 * provided has a matching throw event within its scope. That is, the throw
 * event can be at the same process as the catch event, or could be in a
 * parent/child embedded subprocess.
 * 
 * @author njpatel
 */
public class SignalThrowCatchRule extends ProcessValidationRule {

    private static final String ISSUE_NOMATCHINGTHROW =
            "bpmn.dev.errorNoThrowSignalForGivenCatchSignal"; //$NON-NLS-1$

    private static final String ISSUE_NOTHROW =
            "bpmn.dev.errorNoThrowSignalEvent"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // Nothing to be done here
    }

    @Override
    public void validate(Process process) {

        Collection<Activity> catchEvents =
                EventObjectUtil.getProcessSignalEvents(process,
                        ReturnCatchThrowTypes.CATCH_ONLY);

        /*
         * For each catch event find all the throw events visible to it.
         */
        for (Activity catchEvent : catchEvents) {

            if (EventObjectUtil.isLocalSignalEvent(catchEvent)) {
                /*
                 * XPD-7075: Support these validations only for local signals.
                 */
                Collection<Activity> throwEventsForCatch =
                        EventObjectUtil.getThrowSignalsInScope(catchEvent);

                String signalName = EventObjectUtil.getSignalName(catchEvent);

                if (throwEventsForCatch.isEmpty()) {
                    if (signalName.length() > 0) {
                        /*
                         * No throw signal for explicitly caught signal name in
                         * scope
                         */
                        addIssue(ISSUE_NOMATCHINGTHROW,
                                catchEvent,
                                Collections.singletonList(signalName));

                    } else {
                        /*
                         * Catch is a "catch all" and there are no throw signals
                         * in scope at all
                         */
                        if (throwEventsForCatch.isEmpty()) {
                            addIssue(ISSUE_NOTHROW,
                                    catchEvent,
                                    Collections.singletonList(Xpdl2ModelUtil
                                            .getDisplayNameOrName(catchEvent)));

                        }
                    }
                }
            }
        }
    }
}
