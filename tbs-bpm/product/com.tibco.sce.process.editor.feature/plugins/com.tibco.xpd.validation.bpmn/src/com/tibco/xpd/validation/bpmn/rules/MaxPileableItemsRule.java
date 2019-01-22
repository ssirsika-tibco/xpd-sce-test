/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.rules;

import org.eclipse.emf.common.util.EList;

import com.tibco.xpd.validation.xpdl2.rules.ProcessValidationRule;
import com.tibco.xpd.xpdExtension.ActivityResourcePatterns;
import com.tibco.xpd.xpdExtension.PilingInfo;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author bharge
 * 
 */
public class MaxPileableItemsRule extends ProcessValidationRule {
    private static final String MAX_PILABLE_ITEMS_ID =
            "bpmn.maxPileableItemsCannotBeZeroOrNegative"; //$NON-NLS-1$

    @Override
    protected void validateFlowContainer(Process process,
            EList<Activity> activities, EList<Transition> transitions) {
        for (Activity activity : activities) {
            ActivityResourcePatterns activityResourcePatterns =
                    (ActivityResourcePatterns) Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_ActivityResourcePatterns());
            if (null != activityResourcePatterns
                    && null != activityResourcePatterns.getPiling()) {
                PilingInfo pilingInfo = activityResourcePatterns.getPiling();

                if (pilingInfo != null) {
                    if (pilingInfo.isPilingAllowed()) {
                        String maxItems = pilingInfo.getMaxPiliableItems();
                        if (maxItems != null && maxItems.length() > 0) {
                            int max = 0;
                            try {
                                max = Integer.parseInt(maxItems);
                            } catch (NumberFormatException e) {
                            }

                            if (max < 1) {
                                addIssue(MAX_PILABLE_ITEMS_ID, activity);
                            }
                        }
                    }
                }
            }
        }

        return;
    }
}
