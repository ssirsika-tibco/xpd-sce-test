/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.openspacegwtgadget.integration.core;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.xpd.openspacegwtgadget.integration.OpenspaceGadgetPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;

/**
 * Project nature for openspace gadget development.
 * 
 * @author aallway
 * @since 4 Dec 2012
 */
public class OpenspaceGadgetNature implements IProjectNature {

    public static final String OPENSPACE_GADGET_DEV_NATURE =
            OpenspaceGadgetPlugin.PLUGIN_ID + "." //$NON-NLS-1$
                    + "OpenspaceGWTGadgetNature"; //$NON-NLS-1$

    public static final String GWT_GADGET_NATURE =
            "com.gwtplugins.gwt.eclipse.core.gwtNature"; //$NON-NLS-1$

    /**
     * Openspace gadget development project DAA staging folder.
     */
    public static final String OPENSPACE_DAA_STAGING_FOLDER =
            ".openspaceGadgetApplication"; //$NON-NLS-1$

    private static final String COMPOSITE_MODULES_OUTPUT_KIND =
            "compositeModulesOutput"; //$NON-NLS-1$

    private static final String CLEAN_STAGING_FOLDER_BUILDER_ID =
            "com.tibco.xpd.openspace.integration.cleanStagingFolderBuilder"; //$NON-NLS-1$

    private IProject project;

    /**
     * @see org.eclipse.core.resources.IProjectNature#configure()
     * 
     * @throws CoreException
     */
    @Override
    public void configure() throws CoreException {
        if (project != null) {
            /* By default pre-create the openspace gadget DAA staging folder. */
            getOpenspaceDAAStagingFolder(project, true);

            ProjectUtil.addBuilderToProject(project,
                    CLEAN_STAGING_FOLDER_BUILDER_ID);
        }
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#deconfigure()
     * 
     * @throws CoreException
     */
    @Override
    public void deconfigure() throws CoreException {
        ProjectUtil.removeBuilderFromProject(project,
                CLEAN_STAGING_FOLDER_BUILDER_ID);

    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#getProject()
     * 
     * @return
     */
    @Override
    public IProject getProject() {
        return project;
    }

    /**
     * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
     * 
     * @param project
     */
    @Override
    public void setProject(IProject project) {
        this.project = project;
    }

    /**
     * @param project
     * @return <code>true</code> if project has Openspace gadget development
     *         nature.
     */
    public static boolean isOpenspaceGadgetDevProject(IProject project) {
        try {
            if (project != null) {
                if (project.hasNature(
                        OpenspaceGadgetNature.OPENSPACE_GADGET_DEV_NATURE)) {
                    return true;
                }
            }
        } catch (CoreException e) {
        }

        return false;
    }

    /**
     * @param project
     * @return <code>true</code> if project has GWT gadget development nature.
     */
    public static boolean isGWTGadgetProject(IProject project) {
        try {
            if (project != null) {
                if (project
                        .hasNature(OpenspaceGadgetNature.GWT_GADGET_NATURE)) {
                    return true;
                }
            }
        } catch (CoreException e) {
        }

        return false;
    }

    /**
     * @param project
     * @param create
     *            <code>true</code> if folder should be created if it does not
     *            exist.
     * 
     * @return The DAA staging folder for the openspace gadget DAA.
     */
    public static IFolder getOpenspaceDAAStagingFolder(IProject project,
            boolean create) {
        /*
         * We may not need special folder but currently there are enough
         * warnings from AMF DAA generator to warrant using one.
         */
        SpecialFolder sf;
        if (create) {
            sf = SpecialFolderUtil.getCreateSpecialFolderOfKind(project,
                    COMPOSITE_MODULES_OUTPUT_KIND,
                    OPENSPACE_DAA_STAGING_FOLDER);

            if (sf != null && sf.getFolder() != null) {
                try {
                    /*
                     * Make sure we set derived so that won't be included in
                     * version control check-in.
                     */
                    sf.getFolder().setDerived(true, new NullProgressMonitor());

                    return sf.getFolder();

                } catch (CoreException e) {
                    OpenspaceGadgetPlugin.getDefault().getLogger().error(e,
                            "Could not create DAA staging folder: " //$NON-NLS-1$
                                    + sf.getFolder().getFullPath().toString());
                }
            }
            return null;

        } else {
            return project.getFolder(OPENSPACE_DAA_STAGING_FOLDER);
        }

    }

}
