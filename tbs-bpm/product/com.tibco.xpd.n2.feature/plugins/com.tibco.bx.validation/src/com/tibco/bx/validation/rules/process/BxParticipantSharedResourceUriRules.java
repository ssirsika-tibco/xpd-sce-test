/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.bx.validation.rules.process;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.validation.xpdl2.rules.PackageValidationRule;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.ParticipantType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2ParticipantReferenceResolver;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BxParticipantSharedResourceUriRules
 * 
 * 
 * @author aallway
 * @since 3.3 (20 Nov 2009)
 */
public class BxParticipantSharedResourceUriRules extends PackageValidationRule {

    /*
     * SID XPD-734 - all Web-Service related checking moved to
     * SystemParticipantSharedResourceRule
     */

    private static final String ISSUE_EMAILACT_PARTIC_MUSTBE_SHAREDRES_EMAILSVC =
            "bx.emailActParticSharedResMustBeEmail"; //$NON-NLS-1$

    private static final String ISSUE_EMAILACT_PARTIC_MUSTHAVE_SHAREDRES_NAME =
            "bx.emailParticipantMustHaveSharedResourceName"; //$NON-NLS-1$

    private static final String ISSUE_DBACT_PARTIC_MUSTBE_SHAREDRES_DBSVC =
            "bx.dbActParticSharedResMustBeDatabase"; //$NON-NLS-1$

    private static final String ISSUE_DBACT_PARTIC_MUSTHAVE_SHAREDRES_NAME =
            "bx.dbActParticipantMustHaveSharedResourceName"; //$NON-NLS-1$

    private static final String ISSUE_DBACT_PARTIC_MUSTHAVE_PROFILE_NAME =
            "bx.dbActParticSharedResMustHaveJdbcProfName"; //$NON-NLS-1$

    /*
     * Sid ACE-479 Suppress datbase participant related rules as database
     * partic's are not supported in ACE and we remove their configurations
     * during import migration and their shared resource type becomes undefined
     * (so just want the user to see the 'you must select a shared resource
     * type' problem.
     */
    private static final boolean suppressDatabaseParticipantRules = true;

    @Override
    public void validate(Package pckg) {
        for (Participant participant : pckg.getParticipants()) {
            validateParticipant(participant);
        }

        for (Process process : pckg.getProcesses()) {
            for (Participant participant : process.getParticipants()) {
                validateParticipant(participant);
            }
        }

        return;
    }

    /**
     * @param participant
     */
    private void validateParticipant(Participant participant) {
        if (participant.getParticipantType() != null
                && ParticipantType.SYSTEM_LITERAL.equals(participant
                        .getParticipantType().getType())) {

            Set<EObject> referencingObjects =
                    Xpdl2ParticipantReferenceResolver
                            .getReferencingObjects(participant);

            if (!referencingObjects.isEmpty()) {
                ParticipantSharedResource participantSharedRes =
                        (ParticipantSharedResource) Xpdl2ModelUtil
                                .getOtherElement(participant,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ParticipantSharedResource());
                /*
                 * Rules for all database related activity participant.
                 */
                if (!suppressDatabaseParticipantRules) {
                    boolean isUsedInDBActivity =
                            containsDBActivity(referencingObjects);
                    if (isUsedInDBActivity) {
                        if (null == participantSharedRes
                                || null == participantSharedRes.getJdbc()) {
                            addIssue(ISSUE_DBACT_PARTIC_MUSTBE_SHAREDRES_DBSVC,
                                    participant);
                        } else if (participantSharedRes.getJdbc()
                                .getInstanceName() == null
                                || participantSharedRes.getJdbc()
                                        .getInstanceName().trim()
                                        .length() == 0) {
                            addIssue(ISSUE_DBACT_PARTIC_MUSTHAVE_SHAREDRES_NAME,
                                    participant);
                        } else if (null == participantSharedRes.getJdbc()
                                .getJdbcProfileName()
                                || participantSharedRes.getJdbc()
                                        .getJdbcProfileName().trim()
                                        .length() == 0) {
                            addIssue(ISSUE_DBACT_PARTIC_MUSTHAVE_PROFILE_NAME,
                                    participant);
                        }
                    }
                }

                /*
                 * Rules for all email related activity participant.
                 */
                boolean isUsedInEmailActivity =
                        containsEmailActivity(referencingObjects);
                if (isUsedInEmailActivity) {
                    if (null == participantSharedRes
                            || null == participantSharedRes.getEmail()) {
                        addIssue(ISSUE_EMAILACT_PARTIC_MUSTBE_SHAREDRES_EMAILSVC,
                                participant);
                    } else if (participantSharedRes.getEmail()
                            .getInstanceName() == null
                            || participantSharedRes.getEmail()
                                    .getInstanceName().length() == 0) {
                        addIssue(ISSUE_EMAILACT_PARTIC_MUSTHAVE_SHAREDRES_NAME,
                                participant);
                    }
                }
            }
        }

        return;
    }

    /**
     * @param referencingObjects
     * @return
     */
    private boolean containsEmailActivity(Set<EObject> referencingObjects) {
        for (EObject object : referencingObjects) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;
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
                        if ("E-Mail".equals(type)) { //$NON-NLS-1$
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param referencingObjects
     * @return
     */
    private boolean containsDBActivity(Set<EObject> referencingObjects) {
        for (EObject object : referencingObjects) {
            if (object instanceof Activity) {
                Activity activity = (Activity) object;

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
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
