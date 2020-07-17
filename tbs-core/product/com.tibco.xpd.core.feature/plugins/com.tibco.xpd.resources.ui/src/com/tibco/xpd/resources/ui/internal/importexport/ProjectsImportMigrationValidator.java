/*
 * Copyright (c) TIBCO Software Inc 2004, 2020. All rights reserved.
 */

package com.tibco.xpd.resources.ui.internal.importexport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

/**
 * Sid ACE-???? Class to help with the validation of a import projects set.
 * 
 * For example check that if any project is below a certain threshold then we enforce that dependent projects must
 * already be in the workspace already (and hence migrated)
 * 
 * This functions on the set of source projects BEFORE they are actually imported.
 * 
 * TODO - if merged back to BPM Studio we should make this a contributed mechanism (rather than directly in core
 * feature)
 *
 * @author aallway
 * @since 15 Jul 2020
 */
public class ProjectsImportMigrationValidator {

    public static ProjectsImportMigrationValidator getDefault() {
        return DEFAULT;
    }

    private static final ProjectsImportMigrationValidator DEFAULT = new ProjectsImportMigrationValidator();

    /** Keep a resource set open all the time (as it is much quicker I think). */
    private ResourceSetImpl wrapperResourceSet = new ResourceSetImpl();

    /**
     * If more than one project in the given set has assets earlier than this asset version then do not allow import
     */
    private static final int INDIVIDUAL_PROJECT_IMPORT_MIGRATION_THRESHOLD = 1000;


    /**
     * @param projectsSet
     */
    private ProjectsImportMigrationValidator() {
    }

    /**
     * Validate and return error message
     * 
     * Currently checks for projects that (a) have project assets whose version is below the threshold and (b) have
     * dependencies on projects that are not already in the workspace.
     * 
     * @param projectImportRecords
     * 
     * @return IStatus with {@link IStatus#OK} on success (ok to go ahead with import) else {@link IStatus#ERROR} along
     *         with a cause in the status message.
     */
    public IStatus validateProjectImportMigration(Collection<ProjectRecord> projectImportRecords) {
        /*
         * Get the list of projects that can be safely imported because they either do not have any dependencies OR if
         * they do then they have already been imported into the workspace OR that do not need migration.
         */
        List<ProjectRecord> projectsSafeToImport = new ArrayList<ProjectRecord>();

        /*
         * And the list of projects that require dependencies that have dependencies that are not already in the
         * workspace.
         * 
         * These SHOULD NOT be imported as their own successful migration may depend on the assets in the projects they
         * reference already being migrated and ready to reference in the workspace.
         */
        List<ProjectRecord> migratingProjectsWithUnsatisfiedDependencies = new ArrayList<ProjectRecord>();
        
        Set<String> missingDependencies = new HashSet<String>();

        analyzeImportProjectSet(projectImportRecords,
                projectsSafeToImport,
                migratingProjectsWithUnsatisfiedDependencies,
                missingDependencies);

        if (migratingProjectsWithUnsatisfiedDependencies.size() > 0) {
            String message =
                    String.format(
                            Messages.ProjectsImportMigrationValidator_ImportDependenciesFirstForMigration_message,
                            getCommaSeparatedList(missingDependencies));

            return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID, message); // $NON-NLS-1$
        }

        return new Status(IStatus.OK, XpdResourcesUIActivator.ID, ""); //$NON-NLS-1$
    }

    /**
     * Get the set of projects from the given import project set that are safe to import (because they pass validation).
     * 
     * @param projectImportRecords
     * @return the set of projects from the given import project set that are safe to import.
     */
    public Collection<ProjectRecord> getProjectsThatAreSafeToImport(Collection<ProjectRecord> projectImportRecords) {
        /*
         * Get the list of projects that can be safely imported because they either do not have any dependencies OR if
         * they do then they have already been imported into the workspace OR that do not need migration.
         */
        List<ProjectRecord> projectsSafeToImport = new ArrayList<ProjectRecord>();

        /*
         * And the list of projects that require dependencies that have dependencies that are not already in the
         * workspace.
         * 
         * These SHOULD NOT be imported as their own successful migration may depend on the assets in the projects they
         * reference already being migrated and ready to reference in the workspace.
         */
        List<ProjectRecord> migratingProjectsWithUnsatisfiedDependencies = new ArrayList<ProjectRecord>();

        Set<String> missingDependencies = new HashSet<String>();

        analyzeImportProjectSet(projectImportRecords,
                projectsSafeToImport,
                migratingProjectsWithUnsatisfiedDependencies,
                missingDependencies);

        return projectsSafeToImport;
    }

    /**
     * Gather various information for the import project set.
     * 
     * @param projectImportRecords
     *            Input import project set
     * @param projectsSafeToImport
     *            return list of projects that are safe to import because they're not in workspace already, do not need
     *            to be migrated or have no dependencies
     * @param migratingProjectsWithUnsatisfiedDependencies
     *            return list of projects that require migration and have dependencies that are not already in the
     *            workspace
     * @param missingDependencies
     *            return list of all projects dependended upon by any project (migrating or not) that is not already in
     *            the workspace.
     */
    private void analyzeImportProjectSet(Collection<ProjectRecord> projectImportRecords,
            List<ProjectRecord> projectsSafeToImport, List<ProjectRecord> migratingProjectsWithUnsatisfiedDependencies,
            Set<String> missingDependencies) {
        /* Get set of projects that are safe to import. */
        for (ProjectRecord projectRecord : projectImportRecords) {
            /* Ignore projects already in the workspace. */
            if (projectIsInWorkspace(projectRecord.getProjectName())) {
                continue;
            }

            ProjectConfig projectConfig = projectRecord.getProjectConfig();

            if (projectConfig != null && projectRecord.getProjectDescription() != null) {
                IProject[] referencedProjects = projectRecord.getProjectDescription().getReferencedProjects();

                if (referencedProjects == null || referencedProjects.length == 0) {
                    /**
                     * No referenced projects at all so ok to import regardless of whether it needs to be migrated or
                     * not
                     */
                    projectsSafeToImport.add(projectRecord);

                } else {
                    /* Check if all referenced projects are already in workspace. */
                    boolean allInWorkspace = true;

                    for (int i = 0; i < referencedProjects.length; i++) {
                        IProject refProject = referencedProjects[i];

                        if (!projectIsInWorkspace(refProject.getName())) {
                            allInWorkspace = false;
                            missingDependencies.add(refProject.getName());
                        }
                    }

                    if (allInWorkspace) {
                        /**
                         * All the required dependencies are already in workspace, it is definitely ok to import even if
                         * it needs to be migrated.
                         */
                        projectsSafeToImport.add(projectRecord);

                    } else {
                        if (hasPreThresholdAssets(projectConfig)) {
                            /**
                             * If dependencies are not in workspace and project needs to be migrated then we shouldn't
                             * allow it to be imported.
                             */
                            migratingProjectsWithUnsatisfiedDependencies.add(projectRecord);

                        } else {
                            /**
                             * project doesn't need to be migrated so it's ok to go ahead and import even if
                             * dependencies aren't satisfied already in workspace (they may be in the incoming project
                             * set.
                             */
                            projectsSafeToImport.add(projectRecord);
                        }
                    }
                }
            }
        }
    }


    /**
     * @param nameList
     * @return comma separate list of names (or <none> if list empty).
     */
    private String getCommaSeparatedList(Collection<String> nameList) {
        StringBuilder sb = new StringBuilder();

        if (nameList == null || nameList.isEmpty()) {
            sb.append("<none>"); //$NON-NLS-1$

        } else {
            boolean first = true;

            for (String name : nameList) {
                if (first) {
                    sb.append(name);
                    first = false;
                } else {
                    sb.append(", "); //$NON-NLS-1$
                    sb.append(name);
                }
            }
        }

        return sb.toString();
    }

    /**
     * @param projectName
     * 
     * @return <code>true</code> if given project exists in workspace.
     */
    private boolean projectIsInWorkspace(String projectName) {
        if (ResourcesPlugin.getWorkspace() != null) {
            if (ResourcesPlugin.getWorkspace().getRoot() != null) {
                return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).exists();
            }
        }
        
        return false;
    }

    /**
     * @param projectConfig
     * @return
     */
    private boolean hasPreThresholdAssets(ProjectConfig projectConfig) {
        EList<AssetType> assetTypes = projectConfig.getAssetTypes();

        if (assetTypes != null) {
            for (AssetType assetType : assetTypes) {
                int version = assetType.getVersion();

                if (version < INDIVIDUAL_PROJECT_IMPORT_MIGRATION_THRESHOLD) {
                    return true;
                }
            }
        }

        return false;
    }


}
