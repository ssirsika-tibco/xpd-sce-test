/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.n2.globalsignal.resource.newproject;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.editor.util.GsdEditorUtil;
import com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetVersionProvider;
import com.tibco.xpd.resources.projectconfig.projectassets.SpecialFolderAssetConfiguration;

/**
 * Configurator for Global Signal Definition Special Folder Asset.
 * 
 * @author sajain
 * @since 27-Jan-2015
 */
public class GsdAssetConfigurator extends
        AbstractSpecialFolderAssetConfigurator implements
        IProjectAssetVersionProvider {

    /**
     * Returns GlobalSignalDefinition Special Folder Kind .
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getSpecialFolderKind()
     * 
     * @return
     */
    @Override
    protected String getSpecialFolderKind() {
        return GsdResourcePlugin.GSD_SPECIAL_FOLDER_KIND;
    }

    /**
     * Returns the name for GlobalSignalDefinition special folder.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getSpecialFolderName()
     * 
     * @return
     */
    @Override
    protected String getSpecialFolderName() {

        Object config = getConfiguration();

        if (config instanceof SpecialFolderAssetConfiguration) {

            String name =
                    ((SpecialFolderAssetConfiguration) config)
                            .getSpecialFolderName();

            if (name != null && name.length() > 0) {
                return name;
            }
        }
        return getDefaultFolderName();
    }

    /**
     * Returns the Default name for Global Signal Definition special folder.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.AbstractSpecialFolderAssetConfigurator#getDefaultFolderName()
     * 
     * @return
     */
    @Override
    public String getDefaultFolderName() {
        return com.tibco.xpd.n2.globalsignal.resource.internal.Messages.GlobalSignalDefinitionAssetWizardPage_Default_GSD_Folder_Name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.
     * AbstractSpecialFolderAssetConfigurator
     * #configure(org.eclipse.core.resources.IProject, java.lang.Object)
     */
    @Override
    public void configure(IProject project, Object configuration)
            throws CoreException {

        super.configure(project, configuration);

        /*
         * Handle Global Signal Definition Asset Configuration.
         */
        if (configuration instanceof GsdAssetConfiguration) {

            GsdAssetConfiguration gsdConfig =
                    (GsdAssetConfiguration) configuration;

            if (gsdConfig.isCreateFile()) {

                /*
                 * Get Special Folder Name
                 */
                String folderName = gsdConfig.getSpecialFolderName();

                /*
                 * Create the folder
                 */
                if (folderName != null) {
                    IFolder folder = project.getFolder(folderName);

                    if (folder.exists()) {

                        /*
                         * Create the sample GSD model in this folder
                         */
                        IPath path = folder.getFullPath();
                        path = path.append(gsdConfig.getGSDFileName());

                        /*
                         * Create Global Signal Definition file with initial
                         * model.
                         */
                        Resource newGSDFile =
                                GsdEditorUtil
                                        .createGSDFile(path,
                                                new NullProgressMonitor());

                        /*
                         * Open new file in Editor.
                         */
                        GsdEditorUtil
                                .openGlobalSignalDefinitionEditor(newGSDFile);
                    }
                }

            }

        }

    }

    /*
     * (non-Javadoc) Returns the Format version for this Asset.
     * 
     * @see com.tibco.xpd.resources.projectconfig.projectassets.
     * IProjectAssetVersionProvider
     * #getVersion(org.eclipse.core.resources.IProject)
     */
    @Override
    public int getVersion(IProject project) {

        return Integer
                .parseInt(GsdResourcePlugin.FORMAT_VERSION);
    }

}
