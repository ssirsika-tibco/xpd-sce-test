/*
 * ENVIRONMENT:    Java Generic
 *
 * COPYRIGHT:      (C) 2009 TIBCO Software Inc
 */
package com.tibco.n2.ut.resources.rules;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.AllocationStrategy;
import com.tibco.xpd.xpdExtension.AllocationType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

public class UserActivityChainingGroupRule extends ProcessValidationRule {
    private static final String CHAINING_OFFER_STATE =
            "n2.ut.chainingOfferState"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        HashMap<String, Activity> activitySetIds =
                new HashMap<String, Activity>();

        // Find all the activities that are chained
        for (Activity activity : activities) {
            // If the activity has an associated block activity, then
            // we can assume it's an embedded sub-proc
            BlockActivity blac = activity.getBlockActivity();
            if (blac != null) {
                // Check to see if this is a chained group
                Object isChainedObj =
                        Xpdl2ModelUtil.getOtherAttribute(activity,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_IsChained());
                if (isChainedObj instanceof Boolean
                        && ((Boolean) isChainedObj).booleanValue()) {
                    // Save the activity set and the actual activity for it
                    activitySetIds.put(blac.getActivitySetId(), activity);
                }
            }
        }

        // For each chained activity check validity
        for (String activitySetId : activitySetIds.keySet()) {
            HashSet<String> performerSet = new HashSet<String>();

            for (Activity activity : process.getActivitySet(activitySetId)
                    .getActivities()) {
                Implementation impl = activity.getImplementation();
                if (impl instanceof Task) {
                    // Make sure we are checking a User Task
                    if (((Task) impl).getTaskUser() != null) {
                        // Now make sure that each activity is offer to all
                        if (!isActivityOfferResourcePattern(activity)) {
                            addIssue(CHAINING_OFFER_STATE, activity);
                        }

                        // Store all the performers in the chain (These should
                        // be the same)
                        for (Performer performer : activity.getPerformerList()) {
                            performerSet.add(performer.getValue());
                        }
                    }
                }
            }
        }
    }

    /*
     * ===================================================== METHOD :
     * isActivityOfferResourcePattern
     * =====================================================
     */
    /**
     * Find out is a given activity is of resource type offer
     * 
     * @param activity
     *            Activity to check
     * @return True if Offer To All, false otherwise
     */
    private boolean isActivityOfferResourcePattern(Activity activity) {
        Object objARP =
                Xpdl2ModelUtil.getOtherElement(activity,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ActivityResourcePatterns());

        // Make sure a resource pattern is specified
        if (objARP instanceof ActivityResourcePatterns) {
            AllocationStrategy allStrategy =
                    ((ActivityResourcePatterns) objARP).getAllocationStrategy();

            if (allStrategy != null) {
                if (AllocationType.OFFER_ALL == allStrategy.getOffer()) {
                    return true;
                }
            }
        }

        return false;
    }

}
