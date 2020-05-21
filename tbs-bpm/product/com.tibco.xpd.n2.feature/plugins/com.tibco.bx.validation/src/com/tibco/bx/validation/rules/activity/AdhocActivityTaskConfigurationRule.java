/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.bx.validation.rules.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataContextReferenceResolver;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessInterfaceUtil;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.util.OMUtil;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.validation.xpdl2.resolutions.SetReferencedProjectResolution;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AdHocExecutionTypeType;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.RequiredAccessPrivileges;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Loop;
import com.tibco.xpd.xpdl2.LoopType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.resolvers.DataReferenceContext;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Process Manager rules for Adhoc Tasks. i.e. Tasks having Ahdoc Configuration
 * (i.e. User Tasks and Re-usable sub process)
 * 
 * @author kthombar
 * @since 13-Aug-2014
 */
public class AdhocActivityTaskConfigurationRule extends ProcessValidationRule {

    /**
     * XPD-7189: Moved this validation to pageflow destination from
     * ProcessManager
     * 
     * Ad-hoc user/sub-process task is only supported for business processes.
     */

    /**
     * Task boundary events are not supported for ad-hoc tasks.
     */
    private static String ISSUE_ADHOC_ACTIVITY_CANNOT_HAVE_BOUNDARY_EVENT =
            "bx.adhocActivitiesCannotHaveBoundaryEvents"; //$NON-NLS-1$

    /**
     * Automatic ad-hoc task must have an enablement condition.
     */
    private static String ISSUE_ADHOC_ACTIVITY_MUST_HAVE_ENABLEMENT_CONDITION =
            "bx.adhocActivitiesShouldHaveEnablementCondition"; //$NON-NLS-1$

    /**
     * Ad-hoc user/sub-process task cannot have incoming/outgoing flow.
     */
    private static String ISSUE_ADHOC_ACTIVITY_SHOULD_NOT_HAVE_INCOMING_OUTGOING_TRANSITIONS =
            "bx.adhocActivitiesShouldNoHaveIncomingOutgoingTransitions";//$NON-NLS-1$

    /**
     * It is recommended that you detail Initializer Activities on Ad-Hoc tasks
     * that refer to formal parameters in ad-hoc pre-condition (they may be null
     * when pre-condition is initially evaluated).
     */
    private static String ISSUE_ADHOC_PRECONDITION_HAVING_FP_WITHOUT_EXPLICIT_INITIALIZER =
            "bx.adHocPreConditionHasFPWithNoExplicitInitializer"; //$NON-NLS-1$

    /**
     * Ad-hoc tasks are only permitted in the top level process (not in embedded
     * / event sub-processes).
     */
    private static String ISSUE_ADHOC_MUST_BE_TOP_LEVEL =
            "bx.adHocMustBeTopLevel"; //$NON-NLS-1$

    /**
     * Multiple instance Ad-hoc tasks are not supported.
     */
    private static String ISSUE_MULTIINSTANCE_ADHOC_NOT_SUPPORTED =
            "bx.multiInstanceAdHocTasksNotSupported"; //$NON-NLS-1$

    /**
     * Invalid organisation privilege reference selected.
     */
    private final String REFERENCED_PRIVILEGE_NOT_FOUND_IN_ORG_MODEL =
            "bx.InvalidPrivilegeReference"; //$NON-NLS-1$

    /**
     * Referenced privilege '%1$s' resides in an unreferenced project.
     */
    private final String ISSUE_PRIVILEGE_IN_NON_REFERENCED_PROJ =
            "bx.PrivilegeInNonReferencedProject"; //$NON-NLS-1$

    /** Sid ACE-3680 Disallow Manual Ad-Hoc in ACE for now. */
    private final String ISSUE_MANUAL_ADHOC_NOT_SUPPORTED = "ace.manual.adhoc.not.supported.yet"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ActivityValidationRule#validate(com.tibco.xpd.xpdl2.Activity)
     * 
     * @param container
     */
    @Override
    public void validate(Process process) {

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            /*
             * go ahead only if developer capability is enabled.
             */

            ProcessDataContextReferenceResolver resolver =
                    new ProcessDataContextReferenceResolver();

            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);

            for (Activity activity : allActivitiesInProc) {

                Object adhocConfig =
                        Xpdl2ModelUtil
                                .getOtherElement(activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_AdHocTaskConfiguration());

                if (adhocConfig instanceof AdHocTaskConfigurationType) {

                    AdHocTaskConfigurationType adhocConfigType =
                            (AdHocTaskConfigurationType) adhocConfig;

                    boolean invalidAdhocConfiguration =
                            isInvalidAdhocConfiguration(activity,
                                    adhocConfigType);
                    /*
                     * XPD-7189: It wasn't raising unsupported issue on all
                     * activities with the problem if it found one activity with
                     * the problem.
                     */
                    if (!invalidAdhocConfiguration) {

                        /*
                         * XPD-6948: Saket: Add
                         * "Ad-Hoc task not fully configured" issues if we don't
                         * have any "invalid usage of Ad-Hoc task" issues.
                         */
                        checkIfAdhocTaskHasEnablement(activity, adhocConfigType);

                        checkIfAdhocTaskHasBoundryEvents(activity,
                                adhocConfigType);

                        checkForExplicitInitializerAndPreconditionInAdhoc(activity,
                                adhocConfigType,
                                resolver);

                        checkAndValidateReferencedPrivileges(activity,
                                adhocConfigType);
                    }
                }
            }
        }
    }

    /**
     * This method raises validation if any of the conditions below are true
     * <p>
     * 1. The referenced privilege is no longer available
     * <p>
     * 2. The OM project for the referenced privilege is no longer
     * available(this might be the situation if the project is deleted and the
     * indexer is not updated as yet)
     * <p>
     * 3. If the privilege resides in a non referenced project.
     * 
     * @param activity
     * @param adhocConfigType
     */
    private void checkAndValidateReferencedPrivileges(Activity activity,
            AdHocTaskConfigurationType adhocConfigType) {

        RequiredAccessPrivileges requiredAccessPrivileges =
                adhocConfigType.getRequiredAccessPrivileges();

        if (null != requiredAccessPrivileges) {

            IProject sourceProcessProject =
                    WorkingCopyUtil.getProjectFor(activity);

            EList<ExternalReference> privilegeReference =
                    requiredAccessPrivileges.getPrivilegeReference();

            for (ExternalReference externalReference : privilegeReference) {

                String xref = externalReference.getXref();
                EObject eObject = OMUtil.getEObjectByID(xref);

                if (null == eObject) {
                    /*
                     * referenced privilege doesn't exist
                     */
                    addIssue(REFERENCED_PRIVILEGE_NOT_FOUND_IN_ORG_MODEL,
                            activity);
                } else {

                    IProject omProject = WorkingCopyUtil.getProjectFor(eObject);

                    if (omProject == null) {
                        /*
                         * om project doesn't exist or is deleted from the
                         * workspace
                         */

                        addIssue(REFERENCED_PRIVILEGE_NOT_FOUND_IN_ORG_MODEL,
                                activity);
                    } else {
                        /*
                         * check if the OM project is referenced.
                         */
                        if (!isInSameProjectSet(sourceProcessProject, omProject)) {
                            /*
                             * XPD-6970: If the OM project is not referenced
                             * then raise validation with quick fix to add
                             * project reference.
                             */

                            Map<String, String> additional =
                                    new HashMap<String, String>();
                            additional
                                    .put(SetReferencedProjectResolution.PROJECTNAME_ADDITIONAL_INFO,
                                            omProject.getName());

                            addIssue(ISSUE_PRIVILEGE_IN_NON_REFERENCED_PROJ,
                                    activity,
                                    Collections
                                            .singletonList(((NamedElement) eObject)
                                                    .getName()),
                                    additional);
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param referencerProject
     * @param referenceeProject
     * @return true if the two projects are in the same cross-referencing set of
     *         projects or false if not.
     */
    private boolean isInSameProjectSet(IProject referencerProject,
            IProject referenceeProject) {

        if (referencerProject != null && referenceeProject != null) {
            try {
                return ProjectUtil.isProjectReferenced(referencerProject,
                        referenceeProject);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Checks if the usage of the Ad-Hoc activity is valid.
     * 
     * <p>
     * The Ad-Hoc activity usage is invalid if it does not follow the following.
     * </p>
     * <ul>
     * <li>Ad-Hoc activity should be used in the top level process i.e the Main
     * process</li>
     * <li>Ad-Hoc activity should be used only in Business Process</li>
     * <li>Ad-Hoc activity should not have incoming flow</li>
     * <li>Ad-Hoc activity should not be multi instance</li>
     * </ul>
     * 
     * Adds appropriate error markers and returns true/false accordingly.
     * 
     * @param adhocConfigType
     * @param activity
     * @return true if usage of Ad-Hoc activity is valid as per above, false
     *         otherwise.
     */
    private boolean isInvalidAdhocConfiguration(Activity activity,
            AdHocTaskConfigurationType adhocConfigType) {

        boolean isInvalid = false;
        /*
         * XPD-6869: Saket: We don't support an Ad-Hoc activity inside an
         * activity set, it must be top level.
         */
        if (!isAdHocActivityTopLevel(activity)) {

            isInvalid = true;
            addIssue(ISSUE_ADHOC_MUST_BE_TOP_LEVEL, activity);
        }

        /* Invalid if the Adhoc Activity has incoming Flow */
        if (!activity.getIncomingTransitions().isEmpty()
                || !activity.getOutgoingTransitions().isEmpty()) {

            isInvalid = true;
            addIssue(ISSUE_ADHOC_ACTIVITY_SHOULD_NOT_HAVE_INCOMING_OUTGOING_TRANSITIONS,
                    activity);
        }

        /*
         * XPD-6862: Saket: We don't support multi-instance Ad-Hoc activities,
         * hence is invalid.
         */
        /*
         * Get activity loop.
         */
        Loop loop = activity.getLoop();

        if (loop != null) {

            /*
             * Check if it is multi instance loop.
             */
            if (LoopType.MULTI_INSTANCE_LITERAL.equals(loop.getLoopType())) {

                /*
                 * Add issue if it is.
                 */
                isInvalid = true;
                addIssue(ISSUE_MULTIINSTANCE_ADHOC_NOT_SUPPORTED, activity);
            }
        }

        /* Sid ACE-3680 Disallow Manual Ad-Hoc in ACE for now. */
        if (AdHocExecutionTypeType.MANUAL.equals(adhocConfigType.getAdHocExecutionType())) {
            isInvalid = true;
            addIssue(ISSUE_MANUAL_ADHOC_NOT_SUPPORTED, activity);
        }

        return isInvalid;
    }

    /**
     * Return <code>true</code> if the container of the specified activity is a
     * process, <code>false</code> otherwise.
     * 
     * @param act
     *            AdHoc activity.
     * @return Return <code>true</code> if the container of the specified
     *         activity is a process, <code>false</code> otherwise.
     */
    private boolean isAdHocActivityTopLevel(Activity act) {

        /*
         * Return true if it's placed inside a process.
         */
        if (act.eContainer() instanceof Process) {
            return true;
        }

        /*
         * Return false otherwise.
         */
        return false;
    }

    /**
     * Check if the ad-hoc DOES NOT have any initializer explicitly set and has
     * preconditions containing one or more formal parameters. If yes, then
     * raise a warning marker.
     * 
     * @param activity
     *            Activity to look into.
     * @param adhocConfigType
     *            Activity's AdHoc configuration type.
     * @param resolver
     *            Process data context reference resolver instance created for
     *            activity's containing process.
     */
    private void checkForExplicitInitializerAndPreconditionInAdhoc(
            Activity activity, AdHocTaskConfigurationType adhocConfigType,
            ProcessDataContextReferenceResolver resolver) {

        boolean hasInitializerActivity = false;

        /*
         * First of all check whether enablement is NOT null and then only
         * proceed.
         */
        EnablementType enablement = adhocConfigType.getEnablement();

        if (enablement != null) {

            /*
             * Now we need to check if this activity DOES NOT have any
             * initialiser activity and then only we'll process further.
             */
            if (enablement.getInitializerActivities() != null) {

                EList<ActivityRef> activityRef =
                        enablement.getInitializerActivities().getActivityRef();

                if (activityRef != null && !activityRef.isEmpty()) {

                    hasInitializerActivity = true;
                }
            }

            /*
             * Proceed only if we've found that there were no initialiser
             * activities.
             */
            if (!hasInitializerActivity) {

                /*
                 * Get all formal parameters in the process.
                 */
                List<FormalParameter> allFormalParams =
                        ProcessInterfaceUtil.getAllFormalParameters(activity
                                .getProcess());

                /*
                 * Check if any formal parameter is being referenced in the
                 * script expression text.
                 */
                boolean scriptExpressionHasFormalParam = false;

                if (enablement.getPreconditionExpression() != null) {
                    /*
                     * Script expression text.
                     */
                    String expressionText =
                            enablement.getPreconditionExpression().getText();

                    /*
                     * Check for formal parameter name occurence in the script
                     * text.
                     */
                    for (FormalParameter eachFormalParam : allFormalParams) {

                        if (expressionText.contains(eachFormalParam.getName())) {

                            /*
                             * If we've found one, then break out.
                             */
                            scriptExpressionHasFormalParam = true;
                            break;
                        }
                    }

                    /*
                     * If any formal parameter was referenced from the script
                     * text, then check for data references through resolver.
                     */
                    if (scriptExpressionHasFormalParam) {

                        Set<ProcessRelevantData> dataRefs =
                                resolver.getDataReferences(activity,
                                        DataReferenceContext.CONTEXT_ADHOC_PRECONDITION);

                        if (dataRefs != null && !dataRefs.isEmpty()) {

                            /*
                             * Finally, if there's data reference, then add an
                             * issue.
                             */

                            List<String> msgs = new ArrayList<String>();

                            msgs.add(Xpdl2ModelUtil.getDisplayName(activity));

                            addIssue(ISSUE_ADHOC_PRECONDITION_HAVING_FP_WITHOUT_EXPLICIT_INITIALIZER,
                                    activity,
                                    msgs);

                        }
                    }
                }
            }
        }
    }

    /**
     * Check if the Adhoc activity has any boundary event attached, if yes then
     * raise an error.
     * 
     * @param activity
     * @param adhocConfigType
     */
    private void checkIfAdhocTaskHasBoundryEvents(Activity activity,
            AdHocTaskConfigurationType adhocConfigType) {

        Collection<Activity> attachedEvents =
                Xpdl2ModelUtil.getAttachedEvents(activity);

        if (attachedEvents != null && !attachedEvents.isEmpty()) {
            addIssue(ISSUE_ADHOC_ACTIVITY_CANNOT_HAVE_BOUNDARY_EVENT, activity);
        }
    }

    /**
     * Check if the Adhoc activity has atleast one of the enablement conditions
     * set [i.e. Initilalizer Activity or Precondition expression], if not then
     * raise an error marker.
     * 
     * @param activity
     * @param adhocConfigType
     */
    private void checkIfAdhocTaskHasEnablement(Activity activity,
            AdHocTaskConfigurationType adhocConfigType) {

        AdHocExecutionTypeType adHocExecutionType =
                adhocConfigType.getAdHocExecutionType();

        if (AdHocExecutionTypeType.AUTOMATIC.equals(adHocExecutionType)) {
            /*
             * Add this rule for only for Automatic adhoc activities.
             */
            boolean doesNotHaveEnablementCondition = true;

            EnablementType enablement = adhocConfigType.getEnablement();

            if (enablement != null) {

                if (enablement.getInitializerActivities() != null) {

                    if (!enablement.getInitializerActivities().getActivityRef()
                            .isEmpty()) {
                        doesNotHaveEnablementCondition = false;
                    }
                }

                if (doesNotHaveEnablementCondition
                        && enablement.getPreconditionExpression() != null) {
                    doesNotHaveEnablementCondition = false;
                }
            }

            if (doesNotHaveEnablementCondition) {
                /*
                 * If the Automatic adhoc activity does not have Initializer
                 * activity set as well as the Precondition script then raise
                 * error marker.
                 */
                addIssue(ISSUE_ADHOC_ACTIVITY_MUST_HAVE_ENABLEMENT_CONDITION,
                        activity);
            }
        }
    }

}
