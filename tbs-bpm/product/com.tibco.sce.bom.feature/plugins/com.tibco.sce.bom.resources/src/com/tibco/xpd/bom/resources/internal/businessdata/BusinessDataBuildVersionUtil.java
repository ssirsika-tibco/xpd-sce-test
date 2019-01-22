/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.resources.internal.businessdata;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.framework.Version;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.internal.Messages;
import com.tibco.xpd.bom.resources.utils.BOMUtils;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectDetails;

/**
 * 
 * 
 * @author njpatel
 * @since 19 Nov 2013
 */
public final class BusinessDataBuildVersionUtil {

    private static final String LAST_CHANGE = "lastChange"; //$NON-NLS-1$

    private static final String CHECKSUM = "checksum"; //$NON-NLS-1$

    /**
     * Get the current build version of the given Business Data project. This
     * will get the project lifecycle version and replace the qualifier with the
     * last change time stamp from the preference. If "qualifier" is set by the
     * user then then lifecycle version will be returned as is.
     * <p>
     * <strong>Please note that if no timestamp is set in the project preference
     * then this method call will set the value and return the adjusted version
     * accordingly.
     * </p>
     * 
     * @param project
     * @return version or <code>null</code> if no project doesn't have a
     *         version.
     * 
     * @throws IllegalArgumentException
     *             if the project is not a Business Data project.
     */
    public static String getBusinessDataBuildVersion(IProject project) {
        if (project != null && project.isAccessible()) {

            /*
             * If this is not a Business Data project then throw exception
             */
            if (!BOMUtils.isBusinessDataProject(project)) {
                throw new IllegalArgumentException(
                        String.format(Messages.BusinessDataBuildVersionUtil_expectedBusinessDataProject_error_longdesc,
                                project.getName()));
            }

            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                ProjectDetails details = config.getProjectDetails();
                if (details != null) {
                    String version = details.getVersion();

                    if (version != null && !version.isEmpty()) {
                        String timeStamp = getBuildVersionTimeStamp(project);

                        if (timeStamp != null) {
                            Version ver = Version.parseVersion(version);
                            if (ver.getQualifier().equals("qualifier")) { //$NON-NLS-1$
                                Version newVer =
                                        new Version(ver.getMajor(),
                                                ver.getMinor(), ver.getMicro(),
                                                timeStamp);

                                version = newVer.toString();
                            }
                        }
                    }

                    return version;
                }
            }
        }
        return null;
    }

    /**
     * Get the last stored checksum from the given Business Data project.
     * 
     * @param project
     * @return checksum value, <code>null</code> if one hasn't been set or
     *         project is not accessible.
     */
    /* package */static String getCheckSum(IProject project) {
        if (project != null && project.isAccessible()) {
            try {
                return getPreferenceValue(project, CHECKSUM);
            } catch (BackingStoreException e) {
                BOMResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Failed to read the stored checksum value of Business Data project '%s'.", //$NON-NLS-1$
                                        project.getName()));
            }
        }
        return null;
    }

    /**
     * Set the checksum for the given project.
     * 
     * @param project
     * @param checksum
     */
    /* package */static void setCheckSum(IProject project, String checksum) {
        if (project != null && project.isAccessible()) {
            try {
                updatePreference(project, CHECKSUM, checksum);
            } catch (BackingStoreException e) {
                BOMResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Failed to read the stored checksum value of Business Data project '%s'.", //$NON-NLS-1$
                                        project.getName()));
            }
        }
    }

    /**
     * Get the current timestamp stored in the project preference.
     * <p>
     * <strong>Please note that if no timestamp is set in the project preference
     * then this method call will set the value and return it.
     * </p>
     * 
     * @param project
     * @return
     * 
     * @throws IllegalArgumentException
     *             if the project is not a Business Data project.
     */
    public static String getBuildVersionTimeStamp(IProject project) {
        String timeStamp = null;
        if (project != null && project.isAccessible()) {
            /*
             * If this is not a Business Data project then throw exception
             */
            if (!BOMUtils.isBusinessDataProject(project)) {
                throw new IllegalArgumentException(
                        String.format(Messages.BusinessDataBuildVersionUtil_expectedBusinessDataProject_error_longdesc,
                                project.getName()));
            }

            try {
                timeStamp = getPreferenceValue(project, LAST_CHANGE);
            } catch (BackingStoreException e) {
                BOMResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("Failed to read the last change timestamp of Business Data project '%s'.", //$NON-NLS-1$
                                        project.getName()));
            }

            if (timeStamp == null) {
                // No timestamp set so set it now
                timeStamp = updateBuildTimeStamp(project);
            }
        }
        return timeStamp;
    }

    /**
     * Update the time stamp in the project settings with the current time.
     * 
     * @param project
     *            the project in which to update the business data timestamp.
     * @return the timestamp string if set successfully, <code>null</code>
     *         otherwise.
     */
    public static String updateBuildTimeStamp(IProject project) {
        String formattedDate =
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()); //$NON-NLS-1$

        try {
            updatePreference(project, LAST_CHANGE, formattedDate);
            return formattedDate;
        } catch (BackingStoreException e) {
            BOMResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(e,
                            String.format("Failed to update the last change time stamp of Business Data project '%s'.", //$NON-NLS-1$
                                    project.getName()));
        }

        return null;
    }

    /**
     * Update the project preference with the info given.
     * 
     * @param project
     * @param key
     * @param value
     * @throws BackingStoreException
     */
    private static void updatePreference(IProject project, String key,
            String value) throws BackingStoreException {
        ProjectScope scope = new ProjectScope(project);
        if (scope != null) {
            IEclipsePreferences preference =
                    scope.getNode(BOMResourcesPlugin.PLUGIN_ID);
            if (preference != null) {
                preference.put(key, value);
                preference.flush();
            }
        }
    }

    /**
     * Read the value of the preference from the given project.
     * 
     * @param project
     * @param key
     * @return set value for the given key, <code>null</code> if not set.
     * @throws BackingStoreException
     */
    private static String getPreferenceValue(IProject project, String key)
            throws BackingStoreException {
        ProjectScope scope = new ProjectScope(project);
        if (scope != null) {
            IEclipsePreferences preference =
                    scope.getNode(BOMResourcesPlugin.PLUGIN_ID);

            if (preference != null) {
                return preference.get(key, null);

            }
        }
        return null;
    }
}
