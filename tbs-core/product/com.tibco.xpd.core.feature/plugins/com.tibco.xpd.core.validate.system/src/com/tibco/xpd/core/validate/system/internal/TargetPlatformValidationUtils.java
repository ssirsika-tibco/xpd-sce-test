/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.core.validate.system.internal;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.pde.core.plugin.TargetPlatform;

import com.tibco.xpd.ui.resources.StatusInfo;

/**
 * This Utility class is created for Target Platform Validation utilities. Which
 * will provide all Target Platform validation support required by DAA Export
 * and System Check Validation.
 * 
 * @author aprasad
 * @since 13 Jun 2013
 */
// XPD-4935: The check for correct target platform set in Studio, should in DAA
// export and System Validation test.
@SuppressWarnings("restriction")
public class TargetPlatformValidationUtils {

    private static final String TARGET_PLATFORM_PATH_KEY =
            new String("platform_path"); //$NON-NLS-1$

    private static final String PDE_CORE_PLUGIN_ID =
            new String("org.eclipse.pde.core"); //$NON-NLS-1$

    /**
     * Required Target Platform Path
     */
    public static final Path TARGET_PLATFORM_PATH = new Path(
            SystemValidationPreferences.getDefaultTargetPlatformPath());

    /**
     * This method returns the default target platform for the running host
     * studio .
     * 
     * @return
     */
    public static String getDefaultTargetPlatform() {
        Location location = Platform.getInstallLocation();
        String defaultTargetPlatformLocation =
                TARGET_PLATFORM_PATH.toOSString();
        if (location != null) {
            URL url = location.getURL();
            IPath path = new Path(url.getFile()).removeTrailingSeparator();
            /*
             * Remove last 3 segments from path to get base for expected path
             * Example from C:\Program Files (x86)\TIBCO\Studio
             * 3.6.0\V11\studio\3.6\eclipse to get C:\Program Files
             * (x86)\TIBCO\Studio 3.6.0\V11
             */
            path = path.removeLastSegments(3);

            // 3. compute expected target location , with respect to running
            // host
            defaultTargetPlatformLocation =
                    path.append(TARGET_PLATFORM_PATH).toOSString();
        }
        return defaultTargetPlatformLocation;
    }

    /**
     * This method returns the main target platform location used in the
     * workspace.
     * 
     * @return the main target platform location.
     */
    private static String getWorkspaceTargetPlatform() {

        return TargetPlatform.getLocation();
    }

    /**
     * Checks the workspace uses the default Target Platform or the target
     * platform is valid.The Default Target Platform is the internal target
     * platform of the running host Studio.Returns false if the target platform
     * is not set or is invalid. A target platform is invalid if its location is
     * not accessible.
     * 
     * @param messages
     * @return
     */
    public static StatusInfo validateTargetPlatform() {
        StatusInfo status = new StatusInfo();
        // 1. get Target Platform Location set for the Workspace.
        String workspaceTargetPlatformLocation = getWorkspaceTargetPlatform();
        // This should never happen, target platform should always be set.
        if (workspaceTargetPlatformLocation == null
                || workspaceTargetPlatformLocation.isEmpty()) {
            // ERROR , Target Platform not set
            String errorMsg = String.format(
                    Messages.TargetPlatformValidationUtils_TargetPlatform_NotSet_Msg
                            + workspaceTargetPlatformLocation,
                    TARGET_PLATFORM_PATH);
            status.setError(errorMsg);
        } else {
            // 2. get default Target Platform for the running host i.e Studio
            String defaultTargetPlatformLocation = getDefaultTargetPlatform();
            if (!workspaceTargetPlatformLocation
                    .equalsIgnoreCase(defaultTargetPlatformLocation)) {
                // if workspace target platform is not same as the default
                // check if the workspace Target platform location is
                // accessible/valid from running
                // host location.
                File file = new File(workspaceTargetPlatformLocation);
                // Raise error if the Target Platform directory does not exist.
                if (file == null || !file.exists() || !file.isDirectory()) {
                    // raise error if does not exist
                    String errorMsg = String.format(
                            Messages.TargetPlatformValidationUtils_TargetPlatform_InvalidError_Msg,
                            workspaceTargetPlatformLocation);
                    status.setError(errorMsg);
                } else {
                    // Workspace Target Platform dir exists and is accessible,
                    String warnMsg =
                            Messages.TargetPlatformValidationUtils_TargetPlatform_Default_Not_Used_Msg;
                    status.setWarning(warnMsg);
                }
            } else {
                status.setOK();
            }
        }
        return status;
    }

}
