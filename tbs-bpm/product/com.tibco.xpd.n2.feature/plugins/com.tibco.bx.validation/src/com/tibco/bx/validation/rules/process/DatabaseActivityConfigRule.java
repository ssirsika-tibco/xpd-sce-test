/**
 * Copyright (c) TIBCO Software Inc 2004-2010. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * DatabaseActivityConfigRule
 * <p>
 * Check the configuration of Database related activities.
 * 
 * @author bharge
 * @since 3.3 (28 Jan 2010)
 */
public class DatabaseActivityConfigRule extends ProcessValidationRule {
    private static final String MUST_HAVE_PARTICIPANT =
            "bx.dbTaskMustHaveParticipant"; //$NON-NLS-1$

    private static final String PARTICIPANT_NOT_SYSTEM =
            "bx.dbTaskParticipantMustBeSystem"; //$NON-NLS-1$

    /*
     * Sid ACE-479 Suppress datbase participant related rules as database
     * partic's are not supported in ACE and we remove their configurations
     * during import migration and their shared resource type becomes undefined
     * (so just want the user to see the 'you must select a shared resource
     * type' problem.
     */
    private static final boolean suppressDatabaseParticipantRules = true;

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#
     * validateFlowContainer(com.tibco.xpd.xpdl2.Process,
     * org.eclipse.emf.common.util.EList, org.eclipse.emf.common.util.EList)
     */
    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        validateProcessActivities(activities);
    }

    /**
     * @param activities
     */
    private void validateProcessActivities(EList<Activity> activities) {
        for (Activity activity : activities) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                TaskService taskService = task.getTaskService();
                if (taskService != null) {
                    String type =
                            (String) Xpdl2ModelUtil
                                    .getOtherAttribute(taskService,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ImplementationType());
                    if ("Database".equals(type)) { //$NON-NLS-1$
                        checkDBActivityConfiguration(activity, taskService);
                    }
                }
            }
        }
    }

    /**
     * @param activity
     * @param service
     */
    private void checkDBActivityConfiguration(Activity activity,
            TaskService taskService) {
        /*
         * Check - database task have valid system participant.
         */
        Participant participant = null;
        if (null == activity.getPerformers()) {
            addIssue(MUST_HAVE_PARTICIPANT, activity);
        } else {
            EList<Performer> performers =
                    activity.getPerformers().getPerformers();
            for (Performer performer : performers) {
                String performerId = performer.getValue();
                participant = activity.getProcess().getParticipant(performerId);
                if (null == participant) {
                    participant =
                            activity.getProcess().getPackage()
                                    .getParticipant(performerId);
                }
                break;
            }
            if (null == participant) {
                addIssue(MUST_HAVE_PARTICIPANT, activity);
            } else { // participant is not null, check if the participant
                // type is SYSTEM
                if (!suppressDatabaseParticipantRules) {
                    ParticipantTypeElem participantType =
                            participant.getParticipantType();
                    if (participantType == null
                            || !ParticipantType.SYSTEM_LITERAL
                                    .equals(participantType.getType())) {
                        addIssue(PARTICIPANT_NOT_SYSTEM, activity);
                    }
                }
            }
        }
    }
}
