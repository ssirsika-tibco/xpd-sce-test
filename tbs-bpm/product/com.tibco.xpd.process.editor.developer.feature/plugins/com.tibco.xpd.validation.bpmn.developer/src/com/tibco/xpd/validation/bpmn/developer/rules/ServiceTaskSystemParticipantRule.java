/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

/**
 * Rule to check if the Send Task or Service Task have one System participant
 * assigned
 * 
 * @author bharge
 * 
 */
public class ServiceTaskSystemParticipantRule extends ProcessValidationRule {
    private static final String MORE_THAN_ONE_PARTICIPANT_FOR_SERVICE_TASK_ID =
            "bpmn.dev.moreThanOneParticipantForServiceTask"; //$NON-NLS-1$

    private static final String MORE_THAN_ONE_PARTICIPANT_FOR_SEND_TASK_ID =
            "bpmn.dev.moreThanOneParticipantForSendTask"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        List<Participant> allParticipantsList = new ArrayList<Participant>();

        EList<Participant> processParticipants = process.getParticipants();
        if (processParticipants.size() > 0) {
            allParticipantsList.addAll(processParticipants);
        }

        Package pckg = process.getPackage();
        EList<Participant> packageParticipants = pckg.getParticipants();
        if (packageParticipants.size() > 0) {
            allParticipantsList.addAll(packageParticipants);
        }

        for (Activity activity : activities) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                if (task.getTaskService() != null || task.getTaskSend() != null) {
                    EList<Performer> performerList =
                            activity.getPerformerList();

                    // if only one participant is assigned to the
                    // task(Service or Send), ensure that it is a
                    // System participant
                    if (performerList.size() == 1) {
                        String value = performerList.get(0).getValue();
                        boolean systemPartcipant =
                                isSystemPartcipant(allParticipantsList,
                                        value,
                                        activity);
                        if (!systemPartcipant) {
                            if (task.getTaskService() != null) {
                                addIssue(MORE_THAN_ONE_PARTICIPANT_FOR_SERVICE_TASK_ID,
                                        activity);
                            } else if (task.getTaskSend() != null) {
                                addIssue(MORE_THAN_ONE_PARTICIPANT_FOR_SEND_TASK_ID,
                                        activity);
                            }
                        }
                    } else if (performerList.size() > 1) {
                        // we need to check if the task(Service or Send) has
                        // more
                        // than one
                        // participants assigned and raise the issue.
                        // otherwise for a task(Service or Send) which doesn't
                        // have
                        // any
                        // participant(s) assigned would also get the error
                        // marker!!
                        if (task.getTaskService() != null) {
                            addIssue(MORE_THAN_ONE_PARTICIPANT_FOR_SERVICE_TASK_ID,
                                    activity);
                        } else if (task.getTaskSend() != null) {
                            addIssue(MORE_THAN_ONE_PARTICIPANT_FOR_SEND_TASK_ID,
                                    activity);
                        }
                    }

                }
            }
        }

    }

    /**
     * returns true if the assigned participant to the activity is system
     * 
     * @param allParticipantsList
     * @param assignedParticipantId
     * @param activity
     * @return
     */
    private boolean isSystemPartcipant(List<Participant> allParticipantsList,
            String assignedParticipantId, Activity activity) {
        Participant assignedParticipant = null;
        for (Participant participant : allParticipantsList) {
            String participantId = participant.getId();
            if (participantId.equals(assignedParticipantId)) {
                assignedParticipant = participant;
                break;
            }
        }
        if (assignedParticipant == null) {
            return false;
        }
        ParticipantTypeElem participantType =
                assignedParticipant.getParticipantType();
        if (participantType == null) {
            return false;
        }
        ParticipantType pType = participantType.getType();
        if (!ParticipantType.SYSTEM_LITERAL.equals(pType)) {
            return false;
        }
        return true;
    }
}
