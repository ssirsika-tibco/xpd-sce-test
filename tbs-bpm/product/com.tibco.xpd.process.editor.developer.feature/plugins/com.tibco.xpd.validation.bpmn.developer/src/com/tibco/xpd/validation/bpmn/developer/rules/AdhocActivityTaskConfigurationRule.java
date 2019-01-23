/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.AdHocTaskConfigurationType;
import com.tibco.xpd.xpdExtension.EnablementType;
import com.tibco.xpd.xpdExtension.InitializerActivitiesType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * BPMN rules for Automatic/Manual Ad-Hoc Activities(i.e. UserTask and Re-Usable
 * Sub process.).
 * 
 * @author kthombar
 * @since 13-Aug-2014
 */
public class AdhocActivityTaskConfigurationRule extends ProcessValidationRule {

    /**
     * Rule : Selected ad-hoc initializer activity no longer exists.
     * <p>
     * Resolution : Remove invalid initializer activities.
     */
    private static String ISSUE_ADHOC_ACTIVITY_DELETED_INITIALIZER_ACTIVITES =
            "bpmn.dev.adhocInitializerActivityNotAvailable"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule#validate(com.tibco.xpd.xpdl2.Process)
     * 
     * @param process
     */
    @Override
    public void validate(Process process) {

        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            /*
             * go ahead only if developer capability is enabled.
             */
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

                    validateInitializerActivities(process,
                            adhocConfigType,
                            activity);
                }
            }
        }
    }

    /**
     * Checks if the Initializer activities available with the Ad-Hoc activity
     * are present in the model. [i.e. they are not deleted]. If they are not
     * present in the model then raise an error marker with quick fix to remove
     * them.
     * 
     * @param process
     *            the parent process
     * @param adhocConfigType
     *            the {@link AdHocTaskConfigurationType} to validate
     * @param activity
     *            the activity having Adhoc config set.
     */
    private void validateInitializerActivities(Process process,
            AdHocTaskConfigurationType adhocConfigType, Activity activity) {

        EnablementType enablement = adhocConfigType.getEnablement();

        if (enablement != null) {
            InitializerActivitiesType initializerActivities =
                    enablement.getInitializerActivities();

            if (initializerActivities != null) {
                EList<ActivityRef> activityRef =
                        initializerActivities.getActivityRef();

                for (ActivityRef eachActivityRef : activityRef) {

                    String idRef = eachActivityRef.getIdRef();

                    if (idRef != null && !idRef.isEmpty()) {

                        Activity activityById =
                                Xpdl2ModelUtil.getActivityById(process, idRef);

                        if (activityById == null) {
                            /*
                             * If we do not find the activity that means that it
                             * is deleted from the model. Hence we need to
                             * delete its entry from the initializer activities.
                             * Hence add an Error.
                             */
                            addIssue(ISSUE_ADHOC_ACTIVITY_DELETED_INITIALIZER_ACTIVITES,
                                    activity);
                            break;
                        }
                    }
                }
            }
        }
    }
}
