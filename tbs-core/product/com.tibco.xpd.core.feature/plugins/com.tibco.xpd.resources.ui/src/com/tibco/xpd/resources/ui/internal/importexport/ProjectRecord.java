/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.resources.ui.internal.importexport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.ILeveledImportStructureProvider;
import org.eclipse.ui.internal.wizards.datatransfer.TarEntry;

import com.tibco.xpd.resources.projectconfig.DocumentRoot;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.util.ProjectConfigResourceFactoryImpl;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;

/**
 * Class declared public only for test suite.
 * 
 * Sid ACE-???? Moved to separate class so that we can add more to this such as load of .config file.
 */
public class ProjectRecord {
    private File projectSystemFile;

    private File configSystemFile;

    private Object projectArchiveFile;

    private Object configArchiveFile;

    /* For use in accessing achive files. */
    private ILeveledImportStructureProvider structureProvider = null;

    private String projectName;

    private Object parent;

    private int level;

    private IProjectDescription description;

    /** Keep a resource set open all the time (as it is much quicker I think). */
    private ResourceSetImpl importConfigResourceSet = new ResourceSetImpl();

    private ProjectConfig loadedProjectConfig = null;

    /**
     * Create a record for a project based on the info in the file.
     * 
     * @param file
     */
    public ProjectRecord(File file, File configSystemFile) {
        projectSystemFile = file;
        this.configSystemFile = configSystemFile;
        setProjectName();
    }

    /**
     * @param projectFile
     *            The Object representing the .project file
     * @param parent
     *            The parent folder of the .project file
     * @param level
     *            The number of levels deep in the provider the file is
     */
    public ProjectRecord(ILeveledImportStructureProvider structureProvider, Object projectFile, Object parent,
            int level,
            Object configArchiveFile) {
        this.structureProvider = structureProvider;
        this.projectArchiveFile = projectFile;
        this.configArchiveFile = configArchiveFile;
        this.parent = parent;
        this.level = level;
        setProjectName();
    }

    /**
     * Set the name of the project based on the projectFile.
     */
    private void setProjectName() {
        try {
            if (projectArchiveFile != null) {
                InputStream stream =
                        structureProvider.getContents(projectArchiveFile);

                // If we can get a description pull the name from there
                if (stream == null) {
                    if (projectArchiveFile instanceof ZipEntry) {
                        IPath path =
                                new Path(
                                        ((ZipEntry) projectArchiveFile)
                                                .getName());
                        projectName = path.segment(path.segmentCount() - 2);
                    } else if (projectArchiveFile instanceof TarEntry) {
                        IPath path =
                                new Path(
                                        ((TarEntry) projectArchiveFile)
                                                .getName());
                        projectName = path.segment(path.segmentCount() - 2);
                    }
                } else {
                    setProjectDescription(
                            IDEWorkbenchPlugin.getPluginWorkspace()
                                    .loadProjectDescription(stream));
                    stream.close();
                    projectName = description.getName();
                }

            }

            // If we don't have the project name try again
            if (projectName == null) {
                IPath path = new Path(projectSystemFile.getPath());
                // if the file is in the default location, use the directory
                // name as the project name
                if (isDefaultLocation(path)) {
                    projectName = path.segment(path.segmentCount() - 2);
                    setProjectDescription(
                            IDEWorkbenchPlugin.getPluginWorkspace()
                                    .newProjectDescription(projectName));
                } else {
                    setProjectDescription(IDEWorkbenchPlugin.getPluginWorkspace().loadProjectDescription(path));
                    projectName = description.getName();
                }

            }
        } catch (CoreException e) {
            // no good couldn't get the name
        } catch (IOException e) {
            // no good couldn't get the name
        }
    }

    /**
     * Returns whether the given project description file path is in the
     * default location for a project
     * 
     * @param path
     *            The path to examine
     * @return Whether the given path is the default location for a project
     */
    private boolean isDefaultLocation(IPath path) {
        // The project description file must at least be within the project,
        // which is within the workspace location
        if (path.segmentCount() < 2)
            return false;
        return path.removeLastSegments(2).toFile()
                .equals(Platform.getLocation().toFile());
    }

    /**
     * Get the name of the project
     * 
     * @return String
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Gets the label to be used when rendering this project record in the
     * UI.
     * 
     * @return String the label
     * @since 3.4
     */
    public String getProjectLabel() {
        if (description == null)
            return projectName;

        String path =
                projectSystemFile == null ? structureProvider
                        .getLabel(parent) : projectSystemFile.getParent();

        return String.format("%s (%s)", projectName, path); //$NON-NLS-1$
    }

    /**
     * @param projectRecord
     * @return The {@link ProjectConfig} model for the potential project import.
     */
    public ProjectConfig getProjectConfig() {
        if (loadedProjectConfig != null) {
            return loadedProjectConfig;
        }

        /*
         * Create a resource set and resource to place our temporary UML package in (many of our utilities currently
         * demand this in order to apply profiles and stereotypes required for setting primitive type to fixed point
         * etc).
         * 
         * We remove the resource from resource set at the end so that we don't get a build up (isn't needed for reading
         * the stereotype facets.
         * 
         * We use a UUI for all in case we get called by multiple threads in parallel (so we create a unique resource
         * (then remove it at end)
         */
        @SuppressWarnings("restriction")
        Resource importConfigResource = new ProjectConfigResourceFactoryImpl()
                .createResource(URI.createURI("__IMPORT_PROJECT_CONFIG_RESOURCE_URI__" //$NON-NLS-1$
                        + EcoreUtil.generateUUID()));

        importConfigResourceSet.getResources().add(importConfigResource);

        InputStream projectConfigContent = null;

        try {
            projectConfigContent = getProjectConfigContent();

            if (projectConfigContent != null) {
                importConfigResource.load(projectConfigContent, null);

                EList<EObject> contents = importConfigResource.getContents();

                if (contents != null && contents.size() > 0 && contents.get(0) instanceof DocumentRoot) {
                    DocumentRoot documentRoot = (DocumentRoot) contents.get(0);

                    loadedProjectConfig = documentRoot.getProjectConfig();
                }

            }

        } catch (IOException e) {
            XpdResourcesUIActivator.getDefault().getLogger().error(e,
                    "Import Migration valiation problem: Error loading .config from import project"); //$NON-NLS-1$

        } finally {
            /*
             * REMOVE that asset from the resource set (there is no tear-down lifecycle we can plugin into so we have to
             * as we don't want a build up of resources.
             */
            importConfigResourceSet.getResources().remove(importConfigResource);

            if (projectConfigContent != null) {
                try {
                    projectConfigContent.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return loadedProjectConfig;
    }

    /**
     * Get the import project's .config as an {@link InputStream}
     * 
     * @return {@link InputStream} for the .config file or null if not found.
     */
    private InputStream getProjectConfigContent() {
        if (configArchiveFile != null) {
            return structureProvider.getContents(configArchiveFile);

        } else if (configSystemFile != null) {
            try {
                return new BufferedInputStream(new FileInputStream(configSystemFile));

            } catch (FileNotFoundException e) {
                XpdResourcesUIActivator.getDefault().getLogger().error(e,
                        "Import problem: Error reading .config file from " //$NON-NLS-1$
                                + configSystemFile.getAbsolutePath());
            }
        }

        return null;
    }

    /**
     * 
     * @return
     */
    public IProjectDescription getProjectDescription() {
        return description;
    }

    /**
     * 
     * @param projectDescription
     */
    public void setProjectDescription(IProjectDescription projectDescription) {
        description = projectDescription;
    }

    /**
     * @return The object (ZipEntry / TarEntry) representing the .project file in import archive (when import is from
     *         archive else <code>null</code>)
     */
    public Object getProjectArchiveFile() {
        return projectArchiveFile;
    }

    /**
     * @return The .project {@link File} in import folder (when import is from folder else <code>null</code>)
     */
    public File getProjectSystemFile() {
        return projectSystemFile;
    }

    /**
     * @return the parent
     */
    public Object getParent() {
        return parent;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return the structureProvider
     */
    public ILeveledImportStructureProvider getStructureProvider() {
        return structureProvider;
    }
}