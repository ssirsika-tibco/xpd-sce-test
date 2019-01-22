/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.ui.documentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.Preferences;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.preferences.PreferenceStoreKeys;

/**
 * This class is a utility class for the document generation
 * 
 * @author mtorres
 */
public class DocGenUtil {
    public static final String DOCUMENTATION_TEMP_FOLDER_NAME =
            ".documentationOutput"; //$NON-NLS-1$

    public static final String DOCUMENTATION_LOGO_FILE_NAME = "Logo.png"; //$NON-NLS-1$

    public static final String TIBCO_LOGO_SRC = "/icons/TibcoLogoWhite.png"; //$NON-NLS-1$

    /**
     * 
     * Copies the logo image to be used in the exported documentation. If the
     * custom logo preference is set then it uses the custom logo image
     * provided, otherwise uses the TIBCO logo.
     * 
     * @param imagesFolder
     * 
     */
    public static void createLogoFile(IProject project, File imagesFolder)
            throws CoreException {
        try {

            if (!imagesFolder.exists()) {
                if (!imagesFolder.mkdirs()) {
                    throw new CoreException(new Status(IStatus.ERROR,
                            XpdResourcesUIActivator.ID, IStatus.OK,
                            Messages.IndexResourceCopier_FailedToCreateFolder,
                            null));
                }
            }

            boolean useCustomLogo = false;
            String prefLogoFilePath = null;
            if (project != null) {
                IEclipsePreferences preferences =
                        getProjectSpecificDocSettings(project);

                if (preferences != null) {
                    Preferences node =
                            preferences
                                    .node(PreferenceStoreKeys.PROJECT_SPECIFIC_DOC_NODE);
                    if (node != null) {
                        useCustomLogo =
                                node.getBoolean(PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE,
                                        false);

                        if (useCustomLogo) {
                            prefLogoFilePath =
                                    node.get(PreferenceStoreKeys.LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE,
                                            ""); //$NON-NLS-1$                                 
                        }
                    }
                } else {
                    IPreferenceStore store =
                            XpdResourcesUIActivator.getDefault()
                                    .getPreferenceStore();
                    if (store != null) {
                        useCustomLogo =
                                store.getBoolean(PreferenceStoreKeys.USE_CUSTOM_LOGO_WHEN_EXPORTING_DOCUMENTATION_VALUE);
                        prefLogoFilePath =
                                store.getString(PreferenceStoreKeys.LOGO_FILE_PATH_USED_FOR_EXPORTING_DOCUMENTATION_VALUE);
                    }
                }
            }

            IFile logoFileSrc = null;
            if (useCustomLogo) {
                boolean logoFound = false;
                if (prefLogoFilePath != null && !prefLogoFilePath.isEmpty()) {

                    IResource logo =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .findMember(prefLogoFilePath);
                    if (logo instanceof IFile && logo.exists()) {
                        logoFileSrc = (IFile) logo;
                        logoFound = true;
                    }
                }
                /*
                 * If use custom logo pref is set but file doesn't exist then
                 * log the error.
                 */
                if (!logoFound) {
                    XpdResourcesUIActivator
                            .getDefault()
                            .getLogger()
                            .log(new Status(
                                    IStatus.ERROR,
                                    XpdResourcesUIActivator.ID,
                                    Messages.DocGenUtil_LogoImageNotFoundMessage));

                }
            }

            File logoFileDest =
                    new File(imagesFolder, DOCUMENTATION_LOGO_FILE_NAME);
            InputStream inStream = null;
            OutputStream outStream = null;
            try {
                if (logoFileSrc != null) {
                    inStream = logoFileSrc.getContents();
                } else {
                    inStream =
                            DocGenUtil.class
                                    .getResourceAsStream(TIBCO_LOGO_SRC);
                }

                outStream = new FileOutputStream(logoFileDest);

                for (int i = inStream.read(); i >= 0; i = inStream.read()) {
                    outStream.write(i);
                }
            } catch (FileNotFoundException e) {
                XpdResourcesUIActivator.getDefault().getLogger().error(e);
            } finally {
                if (inStream != null) {
                    inStream.close();
                }

                if (outStream != null) {
                    outStream.close();
                }
            }
        } catch (IOException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
        }
    }

    /**
     * Get the project preferences of the given project.
     * 
     * @param project
     * @return
     */
    private static IEclipsePreferences getProjectPreferences(IProject project) {
        ProjectScope scope = new ProjectScope(project);
        IEclipsePreferences preferences =
                scope.getNode(XpdResourcesUIActivator.ID);
        return preferences;
    }

    /**
     * 
     * @param project
     * @return
     */
    private static IEclipsePreferences getProjectSpecificDocSettings(
            IProject project) {

        if (project != null) {
            IEclipsePreferences preferences = getProjectPreferences(project);
            if (preferences != null) {
                Preferences node =
                        preferences
                                .node(PreferenceStoreKeys.PROJECT_SPECIFIC_DOC_NODE);
                if (node != null) {
                    boolean hasProjectDocSettings =
                            node.getBoolean(PreferenceStoreKeys.HAS_PROJECT_SPECIFIC_DOC_SETTINGS,
                                    false);
                    if (hasProjectDocSettings) {
                        return preferences;
                    }
                }
            }
        }
        return null;
    }
}
