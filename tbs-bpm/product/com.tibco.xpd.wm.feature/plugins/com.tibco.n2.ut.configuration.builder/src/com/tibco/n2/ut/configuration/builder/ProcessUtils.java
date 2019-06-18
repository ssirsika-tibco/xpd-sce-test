/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.n2.ut.configuration.builder;

import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;

import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;

/**
 * Class to supply utility methods for operations at the process level
 * 
 */
public class ProcessUtils {

    /**
     * Returns if the activity is based on a work type
     * 
     * @param activity
     *            The activity to check
     * @return True if Work Type, false is Work Model
     */
    static public boolean isWorkTypeActivity(Activity activity) {
        boolean isWorkType = false;

        for (ExtendedAttribute attribute : activity.getExtendedAttributes()) {
            if ((attribute.getName().compareToIgnoreCase("WorkTypeOrModel") == 0)
                    && (attribute.getValue().compareToIgnoreCase("WorkType") == 0)) {
                isWorkType = true;
            }
        }

        return isWorkType;
    }

    /**
     * Reads the version range for the process
     * 
     * @param activity
     *            Activity to get the version from
     * @return Version number range string or null is not set
     */
    static public String getProcessVersionRange(Activity activity) {
        // At the moment we use the process version as we do not have a work
        // type
        // version available at the moment
        Package xpdlPackage = activity.getProcess().getPackage();
        return getProcessVersionRange(xpdlPackage);

    }

    /**
     * getting version range from xpdl package
     * 
     * @param xpdlPackage
     * @return
     */
    static public String getProcessVersionRange(Package xpdlPackage) {
        /*
         * Sid ACE-1354 - GIVEN that there is a validation rule that has always
         * ensured that the process package version exactly matches the project
         * version THEN we can get rid of the process package version altogether
         * and use the parent Project version instead.
         */
        String versionRange = null;

        // Generate the range from the exact version
        String exactVersion = ProjectUtil
                .getProjectVersion(WorkingCopyUtil.getProjectFor(xpdlPackage));

        if ((exactVersion != null) && (exactVersion.length() > 0)) {
            VersionRange verRange = new VersionRange(new Version(exactVersion),
                    Boolean.TRUE, new Version(exactVersion), Boolean.TRUE);
            versionRange = verRange.toString();
        }
        return versionRange;
    }

}
