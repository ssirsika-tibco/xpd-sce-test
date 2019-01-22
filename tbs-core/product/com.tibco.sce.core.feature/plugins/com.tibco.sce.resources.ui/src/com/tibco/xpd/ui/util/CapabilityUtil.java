/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.util;

import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.internal.actions.ActivitiesUtil;

/**
 * @author nwilson
 */
public final class CapabilityUtil {
    public static final String DEVELOPER_ACTIVITY_ID =
            "com.tibco.xpd.capabilities.developer"; //$NON-NLS-1$

    public static final String SOLUTION_DESIGN_CAPABILITYID =
            "com.tibco.xpd.solutiondesign"; //$NON-NLS-1$

    private CapabilityUtil() {
    }

    @SuppressWarnings("unchecked")
    public static boolean isDeveloperActivityEnabled() {
        if (!XpdResourcesPlugin.isInHeadlessMode()) {
            IWorkbenchActivitySupport activitySupport =
                    PlatformUI.getWorkbench().getActivitySupport();
            IActivityManager activityManager =
                    activitySupport.getActivityManager();
            Set<String> enabledActivityIds =
                    (Set<String>) activityManager.getEnabledActivityIds();
            return enabledActivityIds.contains(DEVELOPER_ACTIVITY_ID);
        }
        return true;
    }

    public static void enableSolutionDesignCapability(boolean enable) {
        ActivitiesUtil
                .setCategoryEnabled(CapabilityUtil.SOLUTION_DESIGN_CAPABILITYID,
                        enable);
    }
}
