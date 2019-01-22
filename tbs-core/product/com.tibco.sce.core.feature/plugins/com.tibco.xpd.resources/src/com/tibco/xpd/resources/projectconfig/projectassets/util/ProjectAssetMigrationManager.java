/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.resources.projectconfig.projectassets.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.projectconfig.AssetType;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.ProjectConfigPackage;
import com.tibco.xpd.resources.projectconfig.projectassets.IProjectAssetMigration;
import com.tibco.xpd.resources.util.DependencySorter;
import com.tibco.xpd.resources.util.DependencySorter.Arc;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Provides the project asset migration functionality.
 * 
 * @author njpatel
 */
public final class ProjectAssetMigrationManager {

    private static final ProjectAssetMigrationManager INSTANCE =
            new ProjectAssetMigrationManager();

    private final ProjectAssetManager assetMananger;

    private List<AssetMigration> migrations;

    private ProjectAssetMigrationManager() {
        assetMananger =
                (ProjectAssetManager) ProjectAssetManager
                        .getProjectAssetMenager();
    }

    /**
     * Get the singleton instance of this manager class.
     * 
     * @return
     */
    public static final ProjectAssetMigrationManager getInstance() {
        return INSTANCE;
    }

    /**
     * Check if the given project needs it's assets to be migrated.
     * 
     * @param project
     * @return <code>true</code> if project needs migrating, <code>false</code>
     *         otherwise.
     */
    public boolean doesProjectNeedMigrating(IProject project) {
        return ProjectCompatibilityWithCode.OLDER
                .equals(getProjectCompatibilityWithCode(project));
    }

    /**
     * Get the compatibility status between the project's assets and the
     * installed version of the code that deals with those -
     * 
     * Note that if some assets are new and some are old (which shouldn't really
     * happen) then this will return OLDER because migration can still be
     * performed on the older assets (then on revalidation we would return
     * NEWER).
     * 
     * @param project
     * @return {@link ProjectCompatibilityWithCode}
     */
    public ProjectCompatibilityWithCode getProjectCompatibilityWithCode(
            IProject project) {
        ProjectCompatibilityWithCode projectCompatibilityWithCode =
                ProjectCompatibilityWithCode.COMPATIBLE;

        if (project != null && project.isAccessible()) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);

            if (config != null) {
                for (AssetType type : config.getAssetTypes()) {
                    ProjectCompatibilityWithCode assetCompat =
                            getProjectAssetCompatibilityWithCode(type, project);

                    if (ProjectCompatibilityWithCode.OLDER.equals(assetCompat)) {
                        /*
                         * OLDER - needs migrating - takes precedence because
                         * older assets can be migrated.
                         */
                        projectCompatibilityWithCode = assetCompat;
                        break;

                    } else if (ProjectCompatibilityWithCode.NEWER
                            .equals(assetCompat)) {
                        projectCompatibilityWithCode = assetCompat;
                    }
                }
            }
        }

        return projectCompatibilityWithCode;
    }

    /**
     * Migrate the given project. Note that this will run in a workspace modify
     * operation.
     * <p>
     * This is the same as calling
     * {@link #migrate(IProject, boolean, IProgressMonitor) migrate (project,
     * true, monitor)}.
     * </p>
     * 
     * @param project
     * @param monitor
     * @throws CoreException
     */
    public void migrate(final IProject project, IProgressMonitor monitor)
            throws CoreException {
        migrate(project, true, monitor);
    }

    /**
     * Migrate the given project. Note that this will run in a workspace modify
     * operation.
     * 
     * @param project
     *            project to migrate
     * @param doCleanBuildAfterMigrate
     *            <code>true</code> if a clean build should be called on the
     *            project once it has been migrated
     * @param monitor
     * @throws CoreException
     * @since 3.5.3
     */
    public void migrate(final IProject project,
            boolean doCleanBuildAfterMigrate, IProgressMonitor monitor)
            throws CoreException {
        if (project != null && project.isAccessible()) {
            final ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                final List<AssetMigration> migrationList =
                        getSortedMigrationList(project, config);

                if (migrationList != null && !migrationList.isEmpty()) {
                    if (monitor == null) {
                        monitor = new NullProgressMonitor();
                    }

                    monitor.setTaskName(String
                            .format(Messages.ProjectAssetMigrationManager_migrating_progress_label,
                                    project.getName()));
                    ResourcesPlugin.getWorkspace()
                            .run(new IWorkspaceRunnable() {

                                @Override
                                public void run(IProgressMonitor monitor)
                                        throws CoreException {
                                    IStatus result =
                                            doMigrate(project,
                                                    config,
                                                    migrationList,
                                                    monitor);
                                    if (result.getSeverity() == IStatus.WARNING) {
                                        // Log the warning and continue
                                        XpdResourcesPlugin.getDefault()
                                                .getLogger().log(result);
                                    } else if (result.getSeverity() == IStatus.ERROR) {
                                        throw new CoreException(result);
                                    }
                                }

                            },
                                    monitor);

                    if (doCleanBuildAfterMigrate) {
                        // Call full build on the project
                        project.build(IncrementalProjectBuilder.CLEAN_BUILD,
                                monitor);
                    }
                }
            }
        }
    }

    /**
     * Run the given asset migrations on the project.
     * 
     * @param project
     * @param config
     * @param migrations
     * @param monitor
     * @return
     */
    private IStatus doMigrate(IProject project, ProjectConfig config,
            List<AssetMigration> migrations, IProgressMonitor monitor) {
        // Set project migration flag.
        XpdResourcesPlugin.getDefault().setProjectMigrationInProgress(project);
        try {
            monitor.beginTask(String
                    .format(Messages.ProjectAssetMigrationManager_migrating_progress_label,
                            project.getName()),
                    migrations.size() + 1);
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(config);
            EditingDomain ed = wc.getEditingDomain();
            List<IStatus> result = new ArrayList<IStatus>();
            CompoundCommand ccmd =
                    new CompoundCommand(
                            Messages.ProjectAssetMigrationManager_updateProjectConfig_command_label);
            for (AssetMigration migration : migrations) {
                AssetType configuredAsset =
                        getConfiguredAsset(config, migration.getAssetId());
                if (configuredAsset != null) {
                    int currentVersion = migration.getCurrentVersion(project);

                    if (configuredAsset.getVersion() < currentVersion) {
                        IStatus status =
                                migration.getMigrator().migrate(project,
                                        migration.assetElement,
                                        configuredAsset.getVersion(),
                                        currentVersion);
                        result.add(status);
                        if (status.getSeverity() == IStatus.ERROR) {
                            // Don't continue with migration
                            break;
                        } else {
                            ccmd.append(SetCommand.create(ed,
                                    configuredAsset,
                                    ProjectConfigPackage.eINSTANCE
                                            .getAssetType_Version(),
                                    currentVersion));
                        }
                    }
                }
                monitor.worked(1);
            }

            if (!ccmd.isEmpty()) {
                ed.getCommandStack().execute(ccmd);
                try {
                    wc.save();
                } catch (IOException e) {
                    return new Status(
                            IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN,
                            String.format("Failed to save the config of project '%s' after migration.", //$NON-NLS-1$
                                    project.getName()), e);
                }
            }

            monitor.done();

            return result.isEmpty() ? Status.OK_STATUS
                    : new MultiStatus(
                            XpdResourcesPlugin.ID_PLUGIN,
                            0,
                            result.toArray(new IStatus[result.size()]),
                            String.format(Messages.ProjectAssetMigrationManager_migrationResult_status_title,
                                    project.getName()), null);
        } finally {
            // Clear project migration flag.
            XpdResourcesPlugin.getDefault()
                    .setProjectMigrationInProgress(null);
        }
    }

    /**
     * Get the configured asset type of the given id from the config.
     * 
     * @param config
     * @param assetId
     * @return asset type if found, <code>null</code> otherwise.
     */
    private AssetType getConfiguredAsset(ProjectConfig config, String assetId) {
        for (AssetType type : config.getAssetTypes()) {
            if (assetId.equals(type.getId())) {
                return type;
            }
        }
        return null;
    }

    /**
     * Get the list of migrations to run on the project in dependency order.
     * 
     * @param project
     * 
     * @param config
     * 
     * @return
     * 
     * @throws CoreException
     */
    private List<AssetMigration> getSortedMigrationList(IProject project,
            ProjectConfig config) throws CoreException {

        List<AssetMigration> allMigrations = new ArrayList<AssetMigration>();
        List<Arc<AssetMigration>> arcs = new ArrayList<Arc<AssetMigration>>();

        /*
         * Collect ids of all assets configured in the project. This will be
         * used to identify dependency (if any) assets that need to be migrated
         * for this project - don't want to add dependency to assets that are
         * not configured for this project.
         */
        List<String> configuredAssets = new ArrayList<String>();
        for (AssetType type : config.getAssetTypes()) {
            configuredAssets.add(type.getId());
        }

        for (AssetType type : config.getAssetTypes()) {
            AssetMigration migration = getMigration(type.getId());
            if (migration != null) {
                allMigrations.add(migration);
                for (String assetId : migration.getMigrateAfter()) {
                    if (configuredAssets.contains(assetId)) {
                        AssetMigration dependsOn = getMigration(assetId);
                        if (dependsOn != null) {
                            arcs.add(new Arc<AssetMigration>(migration,
                                    dependsOn));
                        }
                    }
                }
            }
        }

        try {
            return new DependencySorter<AssetMigration>(arcs, allMigrations)
                    .getOrderedList();
        } catch (IllegalArgumentException e) {
            // Catch cyclic problems
            throw new CoreException(
                    new Status(
                            IStatus.ERROR,
                            XpdResourcesPlugin.ID_PLUGIN,
                            Messages.ProjectAssetMigrationManager_cyclicDependency_error_shortdesc,
                            e));
        }
    }

    /**
     * Check if the given configured asset is current.
     * 
     * @param type
     * @param project
     * 
     * @return {@link ProjectCompatibilityWithCode} indicating compatible, older
     *         or newer.
     */
    private ProjectCompatibilityWithCode getProjectAssetCompatibilityWithCode(
            AssetType type, IProject project) {
        AssetMigration migration = getMigration(type.getId());
        if (migration != null) {
            if (type.getVersion() > migration.getCurrentVersion(project)) {
                return ProjectCompatibilityWithCode.NEWER;

            } else if (type.getVersion() < migration.getCurrentVersion(project)) {
                return ProjectCompatibilityWithCode.OLDER;
            }
        }

        /*
         * No version or migration handler means don't care so treat as
         * compatible
         */
        return ProjectCompatibilityWithCode.COMPATIBLE;
    }

    /**
     * Get the migration element of the asset with the given id.
     * 
     * @param assetId
     * @return migration element or <code>null</code> if no migration is defined
     *         for this asset type.
     */
    private AssetMigration getMigration(String assetId) {
        for (AssetMigration migration : getMigrations()) {
            if (assetId.equals(migration.getAssetId())) {
                return migration;
            }
        }
        return null;
    }

    /**
     * Get all registered migrations elements from the assets.
     * 
     * @return
     */
    public List<AssetMigration> getMigrations() {
        if (migrations == null) {
            migrations = new ArrayList<AssetMigration>();

            for (ProjectAssetElement elem : assetMananger.getAssets()) {
                IConfigurationElement[] children =
                        elem.getConfigurationElement()
                                .getChildren("assetMigration"); //$NON-NLS-1$
                if (children.length > 0) {
                    AssetMigration migration;
                    try {
                        migration = new AssetMigration(elem, children[0]);
                        migrations.add(migration);
                    } catch (CoreException e) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("Problem with loading the migration of the asset extension '%s'.", //$NON-NLS-1$
                                                elem.getId()));
                    }
                }
            }
        }
        return migrations;
    }

    /**
     * Represents the asset migration element of the asset extension.
     */
    private static class AssetMigration {

        private final IProjectAssetMigration migrator;

        private final List<String> migrateAfter;

        private ProjectAssetElement assetElement;

        public AssetMigration(ProjectAssetElement assetElement,
                IConfigurationElement element) throws CoreException {
            this.assetElement = assetElement;
            this.migrator =
                    (IProjectAssetMigration) element
                            .createExecutableExtension("class"); //$NON-NLS-1$

            migrateAfter = new ArrayList<String>();

            for (IConfigurationElement child : element
                    .getChildren("migrateAfter")) { //$NON-NLS-1$
                String id = child.getAttribute("assetId"); //$NON-NLS-1$
                if (id != null) {
                    migrateAfter.add(id);
                }
            }
        }

        /**
         * Get the asset id.
         * 
         * @return
         */
        public String getAssetId() {
            return assetElement.getId();
        }

        /**
         * Get the migration class.
         * 
         * @return
         */
        public IProjectAssetMigration getMigrator() {
            return migrator;
        }

        /**
         * Get current version of this asset.
         * 
         * @param project
         * @return
         */
        public int getCurrentVersion(IProject project) {
            return assetElement.getVersion(project);
        }

        /**
         * Get any dependencies on other asset migrations.
         * 
         * @return list of asset ids, or empty list if no dependency set.
         */
        public List<String> getMigrateAfter() {
            return migrateAfter;
        }

    }

    /**
     * Enumeration for finer grained handling of project asset version differing
     * from asset-code-version
     * 
     * 
     * @author aallway
     * @since 2 Feb 2012
     */
    public enum ProjectCompatibilityWithCode {
        /**
         * All assets in project are compatible with corresponding asset-code
         * studio
         */
        COMPATIBLE,

        /**
         * The project has asset(s) that were created by an older version of the
         * corresponding asset-code and requires migration
         */
        OLDER,

        /**
         * The project has asset(s) that were created by a newer version on the
         * corresponding asset code.
         */
        NEWER
    }
}
