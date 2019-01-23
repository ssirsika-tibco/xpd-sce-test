/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.resources.rules;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.ProcessResourcePatterns;
import com.tibco.xpd.xpdExtension.RetainFamiliarActivities;
import com.tibco.xpd.xpdExtension.SeparationOfDutiesActivities;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Validation rule to check that all of the Resource Patterns are correctly set
 * 
 */
public class UserActivityResourcePatternsRule extends ProcessValidationRule {

    private static final String MULTIPLE_RESOURCE_PATTERN =
            "n2.ut.multipleResourcePattern"; //$NON-NLS-1$

    private static final String SEP_DUTIES_ALLOCATION_TYPE =
            "n2.ut.sepDutiesAllocationType"; //$NON-NLS-1$

    private static final String RETAIN_FAMILIAR_ALLOCATION_TYPE =
            "n2.ut.retainFamiliarAllocationType"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        // This validation rule is to check that all of the Resource Patterns
        // are correctly set

        Object prpObj =
                Xpdl2ModelUtil.getOtherElement(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ProcessResourcePatterns());

        if (prpObj instanceof ProcessResourcePatterns) {
            ProcessResourcePatterns prp = (ProcessResourcePatterns) prpObj;

            // Check that Retain Familiar is only using the offer to all option
            for (RetainFamiliarActivities retain : prp
                    .getRetainFamiliarActivities()) {
                for (ActivityRef activityRef : retain.getActivityRef()) {
                    Activity activity = activityRef.getActivity();
                    if (activity!= null) {
                    if (!isActivityOfferAllocationStrategy(activity)) {
                        addIssue(RETAIN_FAMILIAR_ALLOCATION_TYPE, activity);
                    }
                    }
                }
            }

            Set<String> sepActivities = new HashSet<String>();

            // Make sure that two sets of separation of duties are not applied
            // to
            // the same activity
            for (SeparationOfDutiesActivities seperation : prp
                    .getSeparationOfDutiesActivities()) {
                for (ActivityRef activityRef : seperation.getActivityRef()) {
                    // Check to see if this activity is already in the set so
                    // has
                    // already been used in a separation of duties
                    if (sepActivities.contains(activityRef.getIdRef())) {
                        addIssue(MULTIPLE_RESOURCE_PATTERN,
                                activityRef.getActivity());
                    } else {
                        // Add to the set of activities already in a separation
                        // of duties
                        sepActivities.add(activityRef.getIdRef());
                    }

                    // check to see if this activity has Allocation Strategy
                    // Offer to All. if not raise an issue
                    Activity activity = activityRef.getActivity();
                    if (activity != null) {
                        if (!isActivityOfferAllocationStrategy(activity)) {
                            addIssue(SEP_DUTIES_ALLOCATION_TYPE, activity);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param activity
     */
    private boolean isActivityOfferAllocationStrategy(Activity activity) {
        Object actResPat =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());
        if (actResPat instanceof ActivityResourcePatterns) {
            AllocationStrategy allocationStrategy =
                    ((ActivityResourcePatterns) actResPat)
                            .getAllocationStrategy();
            if (null != allocationStrategy) {
                if (AllocationType.OFFER_ALL == allocationStrategy.getOffer()) {
                    return true;
                }
            }
        }
        return false;
    }

}
