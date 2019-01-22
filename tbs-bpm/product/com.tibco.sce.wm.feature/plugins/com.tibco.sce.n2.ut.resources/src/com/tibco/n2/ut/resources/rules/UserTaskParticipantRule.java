/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.resources.rules;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.n2.ut.configuration.builder.UserActivityParticipantUtils;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.ModelElement;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.ConditionalParticipant;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Class to validate User Task Participants are those supported
 * 
 */
public class UserTaskParticipantRule extends ProcessValidationRule {

    private static final String NO_PERFORMER = "n2.ut.userTaskWithoutPerformer"; //$NON-NLS-1$

    private static final String ALLOCATE_MORETHANONE =
            "n2.ut.allocatToMoreThanOne"; //$NON-NLS-1$

    private static final String UNSUPPORTED_PARTICIPANT_TYPE =
            "n2.ut.unsupportedParticipantType"; //$NON-NLS-1$

    private static final String CONDITIONAL_PARTICIPANT =
            "n2.ut.conditionalParticipant"; //$NON-NLS-1$

    private static final String ORG_QUERY_LIMIT = "n2.ut.orgQueryLimit"; //$NON-NLS-1$

    private static final String REFERENCED_DYNAMIC_ORGANIZATION_LIMIT =
            "n2.ut.userTaskDynamicParticipantLimit"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        /*
         * Check to if this is a page flow Activity or in a Service Process, if
         * so we do not validate if a participant is set or not
         */
        if (Xpdl2ModelUtil.isPageflow(process)
                || Xpdl2ModelUtil.isServiceProcess(process)) {
            return;
        }

        // When Work Models are supported we will need to only
        // check that there is a participant if there is no Work Model
        for (Activity activity : activities) {
            Implementation impl = activity.getImplementation();
            if (impl instanceof Task) {
                TaskUser user = ((Task) impl).getTaskUser();
                if (user != null) {
                    // Make sure that there is at least one participant
                    EList<Performer> performers = activity.getPerformerList();
                    if (performers.size() == 0) {
                        addIssue(NO_PERFORMER, activity);
                        // Skip to the next activity
                        continue;
                    }

                    Object objARP =
                            Xpdl2ModelUtil
                                    .getOtherElement(activity,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ActivityResourcePatterns());

                    // Find out if it is Offer or Allocate
                    AllocationType allocationType = null;

                    // Make sure a resource pattern is specified
                    if (objARP instanceof ActivityResourcePatterns) {
                        AllocationStrategy allStrategy =
                                ((ActivityResourcePatterns) objARP)
                                        .getAllocationStrategy();
                        if (allStrategy != null) {
                            allocationType = allStrategy.getOffer();
                        }
                    }

                    // If allocate then we can have only a single participant
                    if ((allocationType == AllocationType.ALLOCATE_ONE)
                            && (performers.size() > 1)) {
                        addIssue(ALLOCATE_MORETHANONE, activity);
                        // Skip to the next activity
                        continue;
                    }
                    Set<Organization> referredDynamicOrganization =
                            new HashSet<Organization>();
                    for (Performer performer : performers) {
                        Participant participant =
                                activity.getProcess()
                                        .getParticipant(performer.getValue());

                        if (participant != null) {
                            ExternalReference externalReference =
                                    participant.getExternalReference();

                            if (externalReference != null) {
                                // Get the Model Element from the Organisational
                                // Model
                                ModelElement omModelElement =
                                        UserActivityParticipantUtils
                                                .getOMModelElement(externalReference);

                                if (omModelElement != null) {
                                    String orgQuery =
                                            UserActivityParticipantUtils
                                                    .getOrgQuery(omModelElement);

                                    if ((orgQuery != null)
                                            && (orgQuery.length() > 0)) {
                                        // If we have an Query string then make
                                        // sure it is the only
                                        // performer for this activity
                                        if (performers.size() > 1) {
                                            addIssue(ORG_QUERY_LIMIT, activity);
                                        }
                                    } else {
                                        // Check for all the supported types
                                        if (!(omModelElement instanceof Group)
                                                && !(omModelElement instanceof OrgUnit)
                                                && !(omModelElement instanceof Position)) {
                                            // Not one of the supported types
                                            // for an external Organisational
                                            // model
                                            addIssue(UNSUPPORTED_PARTICIPANT_TYPE,
                                                    activity);
                                            break;
                                        }
                                    }

                                    if (omModelElement instanceof Position) {
                                        Position position =
                                                (Position) omModelElement;
                                        EObject eContainer =
                                                position.eContainer();
                                        OrgUnit orgUnit = (OrgUnit) eContainer;
                                        Organization organization =
                                                orgUnit.getOrganization();
                                        if (organization != null
                                                && organization.isDynamic()) {
                                            referredDynamicOrganization
                                                    .add(organization);
                                        }
                                    } else if (omModelElement instanceof OrgUnit) {
                                        OrgUnit orgUnit =
                                                (OrgUnit) omModelElement;

                                        Organization organization =
                                                orgUnit.getOrganization();
                                        if (organization != null
                                                && organization.isDynamic()) {
                                            referredDynamicOrganization
                                                    .add(organization);
                                        }
                                    }
                                }

                            } else {
                                // Not using an organisational model so must be
                                // defined as basic
                                int partType =
                                        participant.getParticipantType()
                                                .getType().getValue();
                                if ((partType != ParticipantType.RESOURCE_SET)) {
                                    // Not one of the supported types for an
                                    // external Organisational model
                                    addIssue(UNSUPPORTED_PARTICIPANT_TYPE,
                                            activity);
                                    break;
                                }
                                // Make sure that there is not more than one RQL
                                // query participants
                                if (performers.size() > 1) {
                                    addIssue(ORG_QUERY_LIMIT, activity);
                                }
                            }
                        } else {
                            // Check to see if this is a performer field
                            ProcessRelevantData participantField =
                                    UserActivityParticipantUtils
                                            .getDynamicParticipant(activity,
                                                    performer.getValue());
                            if (participantField != null) {
                                // Make sure that there is not more than one RQL
                                // query participants
                                // This includes more than one performer type or
                                // an array of performer types
                                if (performers.size() > 1) {
                                    addIssue(ORG_QUERY_LIMIT, activity);
                                }

                                /*
                                 * ABPM-732: Array performer fields now
                                 * supported.
                                 */

                                if (checkConditionalParticipant(participantField) != true) {
                                    addIssue(CONDITIONAL_PARTICIPANT, activity);
                                }
                            }
                        }
                    }
                    if (referredDynamicOrganization.size() > 1) {
                        /*
                         * If a user task is assigned dynamic participants which
                         * refer different dynamic organization then raise issue
                         */
                        addIssue(REFERENCED_DYNAMIC_ORGANIZATION_LIMIT,
                                activity);
                    }
                }
            }
        }
    }

    /**
     * Checks to ensure that a performer field does not have any conditional
     * script set
     * 
     * @param dataField
     *            The Performer field
     * @return True is OK (has no script set), false otherwise
     */
    private boolean checkConditionalParticipant(ProcessRelevantData dataField) {
        // Check to see if this is a participant query
        Object objARP =
                Xpdl2ModelUtil.getOtherElement(dataField,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ConditionalParticipant());

        if (objARP != null) {
            if (objARP instanceof ConditionalParticipant) {
                Expression perfScript =
                        ((ConditionalParticipant) objARP).getPerformerScript();
                if ((perfScript != null) && (perfScript.getText().length() > 0)) {
                    return false;
                }
            }
        }

        return true;
    }

}
