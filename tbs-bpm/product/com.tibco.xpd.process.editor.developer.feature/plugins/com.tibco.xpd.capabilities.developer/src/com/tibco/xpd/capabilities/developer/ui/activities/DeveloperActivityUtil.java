/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.capabilities.developer.ui.activities;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.xpd.capabilities.developer.DeveloperCapabilitiesConstants;

/**
 * Utility class for managing developer activity.
 * <p>
 * <i>Created: 24 Apr 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class DeveloperActivityUtil {

    @SuppressWarnings("unchecked")
    public static void setDeveloperActivity(boolean enabled) {
        synchronized (DeveloperActivityUtil.class) {
            IWorkbenchActivitySupport activitySupport = PlatformUI
                    .getWorkbench().getActivitySupport();
            IActivityManager activityManager = activitySupport
                    .getActivityManager();
            Set enabledActivityIds = new HashSet(activityManager
                    .getEnabledActivityIds());
            String developerActivityId = DeveloperCapabilitiesConstants.DEVELOPER_ACTIVITY_ID;
            if (enabled) {
                if (enabledActivityIds.add(developerActivityId)) {
                    activitySupport.setEnabledActivityIds(enabledActivityIds);
                }
            } else {
                if (enabledActivityIds.remove(developerActivityId)) {
                    activitySupport.setEnabledActivityIds(enabledActivityIds);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean isDeveloperActivityEnabled() {
        synchronized (DeveloperActivityUtil.class) {
            IWorkbenchActivitySupport activitySupport = PlatformUI
                    .getWorkbench().getActivitySupport();
            IActivityManager activityManager = activitySupport
                    .getActivityManager();
            Set<String> enabledActivityIds = (Set<String>) activityManager
                    .getEnabledActivityIds();
            return enabledActivityIds
                    .contains(DeveloperCapabilitiesConstants.DEVELOPER_ACTIVITY_ID);
        }
    }

}
