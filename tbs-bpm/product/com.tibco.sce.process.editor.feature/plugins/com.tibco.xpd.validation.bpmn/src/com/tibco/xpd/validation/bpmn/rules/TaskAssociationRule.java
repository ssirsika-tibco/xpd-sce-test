package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.util.TaskObjectUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;

public class TaskAssociationRule extends ProcessValidationRule {
    private static final String ID = "bpmn.participantToTaskAssociation"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process, EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task){
                Task task = (Task)implementation;
                if (null != task.getTaskUser() || null != task.getTaskManual() ){
                    EObject[] eObjects = TaskObjectUtil.getActivityPerformers(activity);
                    for (EObject object : eObjects) {
                        if (object instanceof Participant){
                            Participant participant = (Participant)object;
                            if (null != participant.getId()){
                                if (null != participant.getParticipantType() && null != participant.getParticipantType().getType()){
                                    if (participant.getParticipantType().getType().equals(ParticipantType.SYSTEM_LITERAL)){
                                        addIssue(ID, activity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}