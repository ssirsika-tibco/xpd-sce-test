/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.util;

import org.eclipse.core.resources.IProject;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.UserInfoUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Process;

/**
 * process participant related uti (moved from removed AddPortTypeCommand class.
 *
 * @author aallway
 * @since 13 Feb 2019
 */
public class ParticipantUtil {

    /**
     * @param process
     * 
     * @return legacy format for the participant name.
     */
    public static String getDefaultParticipantNameForProcessApi(
            String processName, String packageName) {
        return packageName + "_" + processName; //$NON-NLS-1$
    }

    /**
     * @param process
     * 
     * @return current format for the participant name.
     */
    public static String getDefaultParticipantNameForProcessApi(
            String processName) {
        return processName;
    }

    /**
     * @param process
     * 
     * @return The default shared resource URI for auto-generated process api
     *         activity participant.
     */
    public static String getDefaultParticipantLabelForProcessApi(
            String processLabel, String packageLabel) {
        return processLabel + " " + //$NON-NLS-1$
                Messages.AddPortTypeCommand_APIActivityEndPointSuffix_label;
    }

    /**
     * @param process
     * 
     * @return The default shared resource URI for auto-generated process api
     *         activity participant.
     */
    public static String getDefaultSharedResourceURIForProcessApi(
            Process process, String processName, String packageName) {
        IProject project = WorkingCopyUtil.getProjectFor(process);
        String userPrefURI =
                UserInfoUtil.getProjectPreferences(project).getEndpointURI();
        if (null != userPrefURI && userPrefURI.trim().length() > 0) {
            /**
             * default endpoint uri is '/'. if the user has modified endpoint
             * uri in the preference page then we would consider it for shared
             * resource uri; else we already prefix the shared res uri with '/'
             */
            if (!userPrefURI.equalsIgnoreCase(
                    UserInfoUtil.getDefaultValue().getEndpointURI())) {
                return userPrefURI + "/" + packageName + "/" + processName; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return "/" + packageName + "/" + processName; //$NON-NLS-1$ //$NON-NLS-2$
    }

}
