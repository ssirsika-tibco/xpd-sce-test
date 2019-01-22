/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public class ParticipantTypeRule extends ProcessValidationRule {

    /** Process data type Participants must be type Performer. */
    private static final String WRONG_TYPE =
            "bpmn.activityParticipantNotPerformer"; //$NON-NLS-1$

    /**
     * @param process
     *            The process.
     * @param activities
     *            The activities.
     * @param transitions
     *            The transitions.
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     *      validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     *      org.eclipse.emf.common.util.EList,
     *      org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            validate(activity);
        }
    }

    /**
     * @param activity
     *            The activity.
     */
    public void validate(Activity activity) {
        for (Object next : activity.getPerformerList()) {
            if (next instanceof Performer) {
                Performer performer = (Performer) next;
                Object participantOrProcessData =
                        Xpdl2ModelUtil.getParticipantOrProcessData(activity
                                .getProcess(), performer);
                if (participantOrProcessData instanceof ProcessRelevantData) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) participantOrProcessData;
                    DataType type = data.getDataType();
                    if (type instanceof BasicType) {
                        BasicType basic = (BasicType) type;
                        if (!BasicTypeType.PERFORMER_LITERAL.equals(basic
                                .getType())) {
                            List<String> messages = new ArrayList<String>();
                            messages.add(data.getName());
                            messages.add(activity.getName());
                            addIssue(WRONG_TYPE, activity, messages);
                        }
                    }
                }
            }
        }
    }

}
