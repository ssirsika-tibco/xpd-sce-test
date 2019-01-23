/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.common.util.SimulationXpdlUtils;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

/**
 * @author nwilson
 */
public class ParticipantToManualActivityRule extends ProcessValidationRule {

    /** Participant not assigned issue ID. */
    public static final String MANUAL_ACTIVITY_WITH_NO_PARTICIPANT =
            "sim.participantNotAssignedToManualActivity"; //$NON-NLS-1$

    /** Participant not assigned issue ID. */
    public static final String MULTIPLE_PARTICIPANTS =
            "sim.MultipleParticipantsOnManualActivity"; //$NON-NLS-1$

    /** No activity sim data issue ID. */
    public static final String ACTIVITY_WITH_NO_SIM_DATA =
            "sim.noSimDataForActivity"; //$NON-NLS-1$

    /** XPD-219 - Performer field participants are not supported. */
    public static final String PERFORMER_FIELD_PARTICIPANT_NOT_SUPPORTED =
            "sim.performerFieldParticipantNotSupported"; //$NON-NLS-1$

    /**
     * @param process
     *            The process.
     * @param activity
     *            The activity to check.
     */
    private void checkSimDataForActivity(Process process, Activity activity) {
        ActivitySimulationDataType simData =
                SimulationXpdlUtils.getActivitySimulationData(activity);
        if (simData == null) {
            addIssue(ACTIVITY_WITH_NO_SIM_DATA, activity);
        }
    }

    /**
     * @param activity
     *            The activity.
     * @return true if it is a Task activity.
     */
    private boolean isTaskActivity(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof Task) {
            return true;
        }
        return false;
    }

    private boolean isIndependentSubFlow(Activity activity) {
        Implementation implementation = activity.getImplementation();
        if (implementation instanceof SubFlow) {
            return true;
        }
        return false;
    }

    /* XPD-219 - checkValidPerformer() method no longer required. */

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        if (process != null) {

            for (Object next : activities) {

                Activity activity = (Activity) next;
                if (isTaskActivity(activity) || isIndependentSubFlow(activity)) {

                    /*
                     * XPD-219 BEGIN
                     */
                    EObject[] performers =
                            TaskObjectUtil.getActivityPerformers(activity);
                    if (performers == null || performers.length == 0) {
                        addIssue(MANUAL_ACTIVITY_WITH_NO_PARTICIPANT, activity);

                    } else if (performers.length > 1) {
                        addIssue(MULTIPLE_PARTICIPANTS, activity);

                    } else if (performers[0] instanceof ProcessRelevantData) {
                        addIssue(PERFORMER_FIELD_PARTICIPANT_NOT_SUPPORTED,
                                activity);
                    }
                    /*
                     * XPD-219 END
                     */

                    checkSimDataForActivity(process, activity);
                }
            }
        }
    }

}
