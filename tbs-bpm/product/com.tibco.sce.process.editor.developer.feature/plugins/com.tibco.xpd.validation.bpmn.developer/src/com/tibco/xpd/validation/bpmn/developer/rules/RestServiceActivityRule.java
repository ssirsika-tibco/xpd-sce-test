/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.implementer.resources.xpdl2.properties.RestServiceTaskAdapter;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.RestServiceOperation;
import com.tibco.xpd.xpdExtension.RestServiceResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.ParticipantTypeElem;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rules for REST Service invocation activities.
 * 
 * @author nwilson
 * @since 12 Mar 2015
 */
public class RestServiceActivityRule extends ProcessActivitiesValidationRule {

    private static final String MISSING_REST_OPERATION =
            "bpmn.dev.missingRestOperation"; //$NON-NLS-1$

    private static final String MISSING_PROJECT_REFERENCE =
            "bpmn.dev.missingProjectReference"; //$NON-NLS-1$

    private static final String INVALID_REST_OPERATION =
            "bpmn.dev.invalidRestOperation"; //$NON-NLS-1$

    private static final String INVALID_SHARED_RESOURCE =
            "bpmn.dev.invalidRestSharedResource"; //$NON-NLS-1$

    private static final String MISSING_SHARED_RESOURCE =
            "bpmn.dev.missingRestSharedResource"; //$NON-NLS-1$

    public static final String REFERENCED_PARTICIPANT_ID =
            "referencedParticipantId"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessActivitiesValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param activity
     *            The activity to validate.
     */
    @Override
    protected void validate(Activity activity) {
        RestServiceTaskAdapter rsta = new RestServiceTaskAdapter();
        if (rsta.isRestServiceImplementation(activity)) {
            // Check for a valid REST operation.
            OtherElementsContainer container = rsta.getRSOContainer(activity);
            if (container != null) {
                RestServiceOperation rso = rsta.getRSO(container);
                if (rso == null) {
                    // No REST Service Operation defined.
                    List<String> messages = new ArrayList<>();
                    messages.add(activity.getName());
                    addIssue(MISSING_REST_OPERATION, activity, messages);
                } else {
                    Method method = null;
                    IndexerItem ii = rsta.getMethodIndexerItem(rso);
                    if (ii != null) {
                        method = rsta.getRSOMethod(ii);
                    }
                    if (method == null) {
                        // Invalid method reference.
                        List<String> messages = new ArrayList<>();
                        messages.add(activity.getName());
                        addIssue(INVALID_REST_OPERATION, activity, messages);
                    } else {// XPD-7645 Only get WC if method is not null

                        // Check project reference.
                        IProject activityProject =
                                WorkingCopyUtil.getProjectFor(activity);
                        IProject methodProject =
                                WorkingCopyUtil.getProjectFor(method);
                        if (!activityProject.equals(methodProject)) {
                            Set<IProject> projects = new HashSet<>();
                            ProjectUtil
                                    .getReferencedProjectsHierarchy(activityProject,
                                            projects);
                            if (!projects.contains(methodProject)) {
                                List<String> messages = new ArrayList<>();
                                messages.add(methodProject.getName());
                                messages.add(ii.getName());
                                addIssue(MISSING_PROJECT_REFERENCE,
                                        activity,
                                        messages);
                            }
                        }

                        checkValidRestServicePArticipant(activity);
                    }
                }
            }
        }
    }

    /**
     * Checks if the correct participant for rest service activity is available,
     * else raises problem marker.
     * 
     * @param activity
     */
    private void checkValidRestServicePArticipant(Activity activity) {
        // Check for correct participant type.
        Process process = activity.getProcess();
        boolean participantFound = false;
        for (Performer performer : activity.getPerformerList()) {
            Participant participant =
                    process.getParticipant(performer.getValue());
            if (participant != null) {
                ParticipantTypeElem type = participant.getParticipantType();
                if (ParticipantType.SYSTEM_LITERAL.equals(type.getType())) {
                    Object other =
                            Xpdl2ModelUtil
                                    .getOtherElement(participant,
                                            XpdExtensionPackage.eINSTANCE
                                                    .getDocumentRoot_ParticipantSharedResource());
                    ParticipantSharedResource psr = null;
                    RestServiceResource rsr = null;
                    if (other instanceof ParticipantSharedResource) {
                        psr = (ParticipantSharedResource) other;
                        rsr = psr.getRestService();
                    }
                    if (rsr == null) {
                        // Shared resource type must be REST Service.
                        List<String> messages = new ArrayList<>();
                        messages.add(activity.getName());

                        addIssue(INVALID_SHARED_RESOURCE,
                                activity,
                                messages,
                                Collections
                                        .singletonMap(REFERENCED_PARTICIPANT_ID,
                                                participant.getId()));
                    }
                    participantFound = true;
                }
            }
        }
        if (!participantFound) {
            // Shared resource must be set.
            List<String> messages = new ArrayList<>();
            messages.add(activity.getName());
            addIssue(MISSING_SHARED_RESOURCE, activity, messages);
        }
    }
}
