/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtensionPoint;

/**
 * Implementation of the IAssetConfigurator to configure a special folder for a
 * project. Asset contributors can use this class as their configurator if they
 * intend on contributing a special folder. This class can also be extended to
 * add more specific contributions.
 * <p>
 * The following methods of this abstract class have to be implemented by the
 * subclass:
 * <ul>
 * <li>{@link #getSpecialFolderKind()}</li>
 * <li>{@link #getSpecialFolderName()}</li>
 * </ul>
 * </p>
 * <p>
 * Since 3.2 a new method has been introduced to get the
 * {@link #getDefaultFolderName() default} special folder name. If using the new
 * API to add a special folder without contributing any wizard pages then this
 * method should be implemented to return a valid folder name. See
 * <code>SpecialFolderAssetConfigurator</code> for a default implementation that
 * can be used directly.
 * </p>
 * 
 * @see SpecialFolderAssetConfigurator
 * 
 * @author njpatel
 */
public abstract class AbstractSpecialFolderAssetConfigurator implements
        IAssetConfigurator {

    private Object configuration;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.ui.newproject.assets.IAssetConfigurator#configure(org.eclipse
     * .core.resources.IProject, java.lang.Object)
     */
    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {

        /*
         * Create the selected folder and set it as a packages folder
         */

        this.configuration = configuration;
        if (project != null) {

            String folderName = getSpecialFolderName();

            // Update the configuration with the special folder name
            if (configuration instanceof SpecialFolderAssetConfiguration) {
                ((SpecialFolderAssetConfiguration) configuration)
                        .setSpecialFolderName(folderName);
            }

            if (folderName != null) {
                String kind = getSpecialFolderKind();

                if (kind != null) {

                    // Get the project config
                    ProjectConfig projectConfig =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(project);

                    if (projectConfig != null) {
                        // Create folder
                        IFolder folder = project.getFolder(folderName);

                        if (!folder.exists()) {
                            createFolder(folder);
                        }

                        // Create a process package special folder
                        SpecialFolder existingSpecialFolder =
                                projectConfig.getSpecialFolders()
                                        .getFolder(folder);
                        if (existingSpecialFolder == null) {
                            projectConfig.getSpecialFolders().addFolder(folder,
                                    kind);
                        }

                    } else {
                        throw new NullPointerException(
                                String.format("Cannot access project config for %s", project //$NON-NLS-1$
                                                .getName()));
                    }
                }
            } else {
                throw new NullPointerException("Special Folder name is null."); //$NON-NLS-1$
            }
        } else {
            throw new NullPointerException("project is null"); //$NON-NLS-1$
        }

    }

    /**
     * Get the configuration passed to this asset configurator
     * 
     * @return Configuration object of this asset type.
     */
    protected Object getConfiguration() {
        return configuration;
    }

    /**
     * Get the {@link SpecialFolder} kind information.
     * 
     * @return kind information
     * @since 3.2
     */
    public final ISpecialFolderModel getFolderKind() {
        String kind = getSpecialFolderKind();

        if (kind != null) {
            return SpecialFoldersExtensionPoint.getInstance()
                    .getExtensionByKind(kind);
        }

        return null;
    }

    /**
     * Get the default special folder name. The default implementation returns
     * an empty string, subclasses should extend to provide the default name.
     * 
     * @return folder name, should not be <code>null</code>
     * @since 3.2
     */
    public String getDefaultFolderName() {
        return ""; //$NON-NLS-1$
    }

    /**
     * Get the special folder kind to create.
     * 
     * @return special folder kind that this asset type is contributing.
     */
    protected abstract String getSpecialFolderKind();

    /**
     * Get the name of the special folder to create.
     * 
     * @return special folder name of the special folder this asset type is
     *         contributing.
     */
    protected abstract String getSpecialFolderName();

    /**
     * Create the given folder. All non-existing parent folders will also be
     * created.
     * 
     * @param folder
     * @throws CoreException
     */
    private void createFolder(IFolder folder) throws CoreException {
        if (folder != null) {
            IContainer parent = folder.getParent();

            if (!parent.exists()) {
                if (parent instanceof IFolder) {
                    // Create the parent
                    createFolder((IFolder) parent);
                }
            }
            folder.create(false, true, null);
        }
    }

}
