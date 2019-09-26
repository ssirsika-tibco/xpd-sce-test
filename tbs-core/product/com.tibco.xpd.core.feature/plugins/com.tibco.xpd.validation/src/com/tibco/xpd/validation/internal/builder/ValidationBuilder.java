/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.internal.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.internal.Workbench;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.indexer.IndexerService;
import com.tibco.xpd.resources.internal.indexer.IndexerServiceImpl;
import com.tibco.xpd.resources.internal.indexer.ResourceDependencyIndexer;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager;
import com.tibco.xpd.resources.projectconfig.projectassets.util.ProjectAssetMigrationManager.ProjectCompatibilityWithCode;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdConsts;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.internal.EMFValidator;
import com.tibco.xpd.validation.internal.Messages;
import com.tibco.xpd.validation.internal.SpecialFolderResourceValidator;
import com.tibco.xpd.validation.internal.ValidationManager;
import com.tibco.xpd.validation.internal.WorkspaceResourceValidationManager;
import com.tibco.xpd.validation.internal.engine.ImplicitDependencyIndexer;
import com.tibco.xpd.validation.internal.validation.ProjectLifecycleValidator;
import com.tibco.xpd.validation.internal.validation.ProjectValidator;
import com.tibco.xpd.validation.provider.IFileFilter;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.IssueInfo;
import com.tibco.xpd.validation.rules.ValidationUtil;

/**
 * @author nwilson, rassisi
 */
public class ValidationBuilder extends IncrementalProjectBuilder {

    private static final Logger LOG = ValidationActivator.getDefault()
            .getLogger();

    /**
     * A list of already validated projects.
     */
    static ArrayList<IProject> validatedProjects = null;

    ProjectValidator cycledProjectValidator = new ProjectValidator();

    /** The collection of file filters. */
    private IFileFilter filter;

    /** Validator that will validate each project's lifecycle id. */
    private final ProjectLifecycleValidator lifecycleValidator;

    /**
     * Validates duplicate resources in Special Folders.
     */
    private final SpecialFolderResourceValidator specialFolderResourceValidator;

    /**
     * Constructor.
     */
    public ValidationBuilder() {
        lifecycleValidator = new ProjectLifecycleValidator();
        specialFolderResourceValidator = new SpecialFolderResourceValidator();
    }

    /**
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#startupOnInitialize()
     */
    @Override
    protected void startupOnInitialize() {
        super.startupOnInitialize();
        filter = ValidationManager.getInstance().getFileFilter();
    }

    /**
     * @param kind
     *            The build type.
     * @param args
     *            build arguments.
     * @param monitor
     *            The progress monitor.
     * @return null.
     * @throws CoreException
     *             If there was a problem during the build.
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
     *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
            throws CoreException {

        // XPD-4044: Builder should not run when the workbench is closing.
        if (!shouldRun()) {
            return null;
        }
        TRACE(String.format("Build started on '%s'.", getProject())); //$NON-NLS-1$

        ProjectCompatibilityWithCode projectCompatibilityWithCode =
                ProjectAssetMigrationManager.getInstance()
                        .getProjectCompatibilityWithCode(getProject());

        /*
         * Sid ACE-444 If this is a Studio project without the SCE destination
         * then it hasn't been created / imported correctly.
         */
        if (ProjectCompatibilityWithCode.NOT_SCE
                .equals(projectCompatibilityWithCode)) {
            System.err
                    .println("** ValidationBuilder: '" + getProject().getName() + "' not a CE project - adding Marker"); //$NON-NLS-1$ //$NON-NLS-2$

            /*
             * Remove all problem markers and then add single migration marker
             */
            getProject().deleteMarkers(
                    "org.eclipse.core.resources.problemmarker", //$NON-NLS-1$
                    true,
                    IResource.DEPTH_INFINITE);

            clearProjectFromNewerStudioMarker(getProject());
            clearMigrationMarker(getProject());

            createNotCeDestinationMarker(getProject());

        }
        /*
         * Only offer migration problem marker with migration quick fix if
         * project has assets of an older version compared with the installed
         * code that handles that asset (not if ti's the other way around)
         */
        else if (ProjectCompatibilityWithCode.OLDER
                .equals(projectCompatibilityWithCode)) {
            System.err.println("** ValidationBuilder: '" + getProject().getName() + "' Older project - adding Marker"); //$NON-NLS-1$ //$NON-NLS-2$

            /*
             * Remove all problem markers and then add single migration marker
             */
            getProject()
                    .deleteMarkers("org.eclipse.core.resources.problemmarker", //$NON-NLS-1$
                            true,
                            IResource.DEPTH_INFINITE);

            clearProjectFromNewerStudioMarker(getProject());
            clearNotCeDestinationMarker(getProject());

            createMigrationMarker(getProject());


        } else if (ProjectCompatibilityWithCode.NEWER
                .equals(projectCompatibilityWithCode)) {
            System.err.println("** ValidationBuilder: '" + getProject().getName() + "' Newer project - adding Marker"); //$NON-NLS-1$ //$NON-NLS-2$

            /*
             * Remove all problem markers and then add single migration marker
             */
            getProject()
                    .deleteMarkers("org.eclipse.core.resources.problemmarker", //$NON-NLS-1$
                            true,
                            IResource.DEPTH_INFINITE);

            clearMigrationMarker(getProject());
            clearNotCeDestinationMarker(getProject());

            /*
             * Otherwise if the project has assets that are a newer version than
             * the installed code that handles them then add a different
             * markers.
             */
            createProjectFromNewerStudioMarker(getProject());

        } else {
            System.err.println("** ValidationBuilder: '" + getProject().getName() //$NON-NLS-1$
                    + "'Compatible project - clearing any migration markers"); //$NON-NLS-1$

            clearProjectFromNewerStudioMarker(getProject());
            clearMigrationMarker(getProject());


            if (kind == FULL_BUILD) {
                TRACE("Full build requested..."); //$NON-NLS-1$
                long start = System.currentTimeMillis();
                fullBuild(monitor);

                lifecycleValidator.validate(getProject(), monitor);
                TRACE(String.format("Full build complete, took %d ms.", System //$NON-NLS-1$
                        .currentTimeMillis() - start));
            } else {
                IResourceDelta delta = getDelta(getProject());

                if (delta != null && !doFullBuild(delta)) {
                    TRACE("Incremental build requested..."); //$NON-NLS-1$
                    long start = System.currentTimeMillis();
                    incrementalBuild(delta, monitor);
                    TRACE(String
                            .format("Incremental build complete, took %d ms.", System //$NON-NLS-1$
                                            .currentTimeMillis()
                                            - start));
                } else {
                    TRACE("Full build requested due to change in project properties or configuration..."); //$NON-NLS-1$
                    long start = System.currentTimeMillis();
                    fullBuild(monitor);
                    lifecycleValidator.validate(getProject(), monitor);
                    TRACE(String
                            .format("Full build complete, took %d ms.", System //$NON-NLS-1$
                                    .currentTimeMillis() - start));
                }
            }
        }

        // Get all referenced projects
        Set<IProject> referencedProjects =
                ProjectUtil.getReferencedProjectsHierarchy(getProject(), null);

        TRACE(String.format("Build completed on '%s'.", getProject())); //$NON-NLS-1$
        // Return all (including implicit) referenced projects
        return referencedProjects != null ? referencedProjects
                .toArray(new IProject[referencedProjects.size()]) : null;
    }

    /**
     * Clear the migration marker from the project.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5
     */
    private void clearMigrationMarker(IProject project) throws CoreException {
        project.deleteMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO);
    }

    /**
     * Create the migration marker on the given project.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5
     */
    private void createMigrationMarker(IProject project) throws CoreException {
        IMarker[] markers =
                project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                        false,
                        IResource.DEPTH_ZERO);

        if (markers.length == 0) {
            IssueInfo info =
                    ValidationManager.getInstance().getValidationEngine()
                            .getIssueInfo("migrateProject.issue"); //$NON-NLS-1$
            IMarker marker =
                    project.createMarker(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE);
            marker.setAttribute(IMarker.LOCATION, project.getFullPath()
                    .toString());
            marker.setAttribute(IMarker.SEVERITY, info.getSeverity());
            marker.setAttribute(IMarker.MESSAGE,
                    String.format(info.getMessage(), project.getName()));
            marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
            marker.setAttribute(IIssue.ID, info.getId());
        }
    }

    /**
     * Clear the project from newer verison of studio marker from the project.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5.3
     */
    private void clearProjectFromNewerStudioMarker(IProject project)
            throws CoreException {
        project.deleteMarkers(XpdConsts.PROJECT_FROM_NEWER_STUDIO_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO);
    }

    /**
     * Create the Project is from newer version of Studio marker.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5.3
     */
    private void createProjectFromNewerStudioMarker(IProject project)
            throws CoreException {
        IMarker[] markers =
                project.findMarkers(XpdConsts.PROJECT_MIGRATION_MARKER_TYPE,
                        false,
                        IResource.DEPTH_ZERO);

        if (markers.length == 0) {
            IssueInfo info =
                    ValidationManager.getInstance().getValidationEngine()
                            .getIssueInfo("projectFromNewerVersion.issue"); //$NON-NLS-1$
            IMarker marker =
                    project.createMarker(XpdConsts.PROJECT_FROM_NEWER_STUDIO_MARKER_TYPE);
            marker.setAttribute(IMarker.LOCATION, project.getFullPath()
                    .toString());
            marker.setAttribute(IMarker.SEVERITY, info.getSeverity());
            marker.setAttribute(IMarker.MESSAGE,
                    String.format(info.getMessage(), project.getName()));
            marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
            marker.setAttribute(IIssue.ID, info.getId());
        }
    }

    /**
     * Clear the Not ACE destination marker from the project.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5
     */
    private void clearNotCeDestinationMarker(IProject project)
            throws CoreException {
        project.deleteMarkers(XpdConsts.PROJECT_NOT_ACE_DESTINATION_MARKER_TYPE,
                false,
                IResource.DEPTH_ZERO);
    }

    /**
     * Create the migration marker on the given project.
     * 
     * @param project
     * @throws CoreException
     * @since 3.5
     */
    private void createNotCeDestinationMarker(IProject project)
            throws CoreException {
        IMarker[] markers =
                project.findMarkers(
                        XpdConsts.PROJECT_NOT_ACE_DESTINATION_MARKER_TYPE,
                        false,
                        IResource.DEPTH_ZERO);

        if (markers.length == 0) {
            IssueInfo info = ValidationManager.getInstance()
                    .getValidationEngine()
                    .getIssueInfo("notAceDestinationProject.issue"); //$NON-NLS-1$
            IMarker marker = project
                    .createMarker(
                            XpdConsts.PROJECT_NOT_ACE_DESTINATION_MARKER_TYPE);
            marker.setAttribute(IMarker.LOCATION,
                    project.getFullPath().toString());
            marker.setAttribute(IMarker.SEVERITY, info.getSeverity());
            marker.setAttribute(IMarker.MESSAGE,
                    String.format(info.getMessage(), project.getName()));
            marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
            marker.setAttribute(IIssue.ID, info.getId());
        }
    }

    private void index(Collection<IFile> changedFiles, IProgressMonitor monitor) {
        TRACE("Starting indexing..."); //$NON-NLS-1$
        IndexerServiceImpl service =
                (IndexerServiceImpl) XpdResourcesPlugin.getDefault()
                        .getIndexerService();

        for (IFile file : changedFiles) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
            if (wc != null) {
                monitor.subTask(String
                        .format(Messages.ValidationBuilder_indexing_monitor_shortdesc,
                                wc.getName()));
                TRACE(String.format("Indexing '%s'...", wc //$NON-NLS-1$
                        .getEclipseResources().get(0).getFullPath().toString()));
                service.index(wc);
                TRACE("Indexing complete."); //$NON-NLS-1$
            }
            monitor.worked(1);
        }
        TRACE("Completed indexing."); //$NON-NLS-1$
    }

    /**
     * Check if the config file has been updated. This can happen when special
     * folders have changed/created/removed. It will also check if the config
     * file has been deleted.
     * 
     * @param delta
     *            changed resource delta.
     * @return <code>true</code> if the config file is an affected child of the
     *         resource delta, <code>false</code> otherwise.
     */
    private boolean isConfigUpdated(IResourceDelta delta) {
        boolean updated = false;

        if (delta != null) {
            IResourceDelta[] children = delta.getAffectedChildren();

            for (IResourceDelta child : children) {
                if (child.getResource().getProjectRelativePath().toString()
                        .equals(XpdResourcesPlugin.PROJECTCONFIGFILE)) {
                    // Only return true if contents of the file have changed or
                    // the file has been removed
                    updated =
                            child.getKind() == IResourceDelta.REMOVED
                                    || (child.getKind() == IResourceDelta.CHANGED && (child
                                            .getFlags() & IResourceDelta.CONTENT) != 0);
                    break;
                }
            }
        }

        return updated;
    }

    /**
     * Check if a full build is required. This will check the following:
     * <ol>
     * <li>Check if the .config file has been updated</li>
     * <li>Check if the project description has changed</li>
     * <li>If the project has referenced projects then check if any of them had
     * their description or config file changed.</li>
     * </ol>
     * If any of the above has occurred then a full build will be required.
     * 
     * @param delta
     *            current resource delta
     * @return <code>true</code> if full build is required, <code>false</code>
     *         otherwise.
     */
    private boolean doFullBuild(IResourceDelta delta) {
        boolean fullBuild = false;

        validatedProjects = new ArrayList<IProject>();

        if (delta != null) {
            fullBuild = isConfigUpdated(delta);

            if (!fullBuild) {
                /*
                 * If the project description has changed of this project or any
                 * referenced project then run full build
                 */
                // Check if the description of current project has changed
                fullBuild =
                        (delta.getFlags() & IResourceDelta.DESCRIPTION) != 0;

                // Check if description of referenced projects has changed
                if (!fullBuild) {
                    Set<IProject> referencedProjects =
                            ProjectUtil
                                    .getReferencedProjectsHierarchy(getProject(),
                                            null);

                    if (referencedProjects != null) {
                        for (IProject proj : referencedProjects) {
                            try {
                                if (proj != getProject()
                                        && proj.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                                    reValidateProject(proj);
                                    IResourceDelta refDelta = getDelta(proj);
                                    // Check if the project description has
                                    // changed or the .config file has changed
                                    if (fullBuild =
                                            refDelta != null
                                                    && ((refDelta.getFlags() & IResourceDelta.DESCRIPTION) != 0)
                                                    || isConfigUpdated(refDelta)) {
                                        // Reference project's description or
                                        // config has changed
                                        break;
                                    }
                                }
                            } catch (CoreException e) {
                                // Do nothing - carry on checking the next
                                // project
                            }
                        }
                    }
                }
            }
        }

        return fullBuild;
    }

    /**
     * @param monitor
     *            The progress monitor.
     * @throws CoreException
     *             If there was a problem during the build.
     */
    private void fullBuild(IProgressMonitor monitor) throws CoreException {

        IProject project = getProject();
        specialFolderResourceValidator.validateAllSpecialFolders(project);

        ValidationResourceVisitor validationResourceVisitor =
                new ValidationResourceVisitor(monitor);
        project.accept(validationResourceVisitor);
        checkCancel(monitor, true);

        /* Index all resources */
        IndexResourceVisitor indexResVisitor = new IndexResourceVisitor();
        project.accept(indexResVisitor);
        Set<IFile> filesForIndexing = indexResVisitor.getFilesForIndexing();

        index(filesForIndexing, monitor);
        checkCancel(monitor, true);

        // validate all files and dependencies.
        validateFiles(validationResourceVisitor.getFilesToValidate(), monitor);
        checkCancel(monitor, true);

        cycledProjectValidator.validate(project);
        // Get all referenced projects
        Set<IProject> referencedProjects =
                ProjectUtil.getReferencedProjectsHierarchy(project, null);

        for (IProject refProject : referencedProjects) {

            if (project != refProject) {

                cycledProjectValidator.validate(refProject);
            }
            checkCancel(monitor, true);
        }
        monitor.done();
    }

    /**
     * Find all other resources that should be validated as a result of
     * validating the given (toValidate) resources
     * 
     * @param newForValidation
     *            resources being validated
     * @return set of files to validate
     */
    private Set<IFile> addAffectedResources(Set<IFile> newForValidation)
            throws CoreException {

        Set<IFile> forValidation = new HashSet<IFile>();

        if (newForValidation != null) {
            while (!newForValidation.isEmpty()) {
                /*
                 * Converting newForValidation to an array to get a snapshot of
                 * this list - otherwise, if we iterate through the list we will
                 * fall into concurrent modification exceptions.
                 */
                for (IFile file : newForValidation
                        .toArray(new IFile[newForValidation.size()])) {
                    if (!forValidation.contains(file)) {
                        forValidation.add(file);

                        Set<IResource> affected = new HashSet<IResource>();

                        affected.addAll(getInverseDependencies(file));
                        affected.addAll(getImplicitDependencies(file));

                        for (IResource nres : affected) {
                            if (nres instanceof IFile
                                    && !forValidation.contains(nres)
                                    && !newForValidation.contains(nres)) {
                                newForValidation.add((IFile) nres);
                            }
                        }
                    }
                    newForValidation.remove(file);
                }
            }
        }

        return forValidation;
    }

    /**
     * Get the resources that depend on the given file.
     * 
     * @param file
     * @return collection of resources, empty collection if none found.
     */
    private Collection<IResource> getInverseDependencies(IFile file) {
        return ResourceDependencyIndexer.getInverseDependencies(file, null);
    }

    /**
     * Get all implicit dependencies of the given file.
     * 
     * @param file
     * @return dependencies or empty set if none.
     */
    private Set<IFile> getImplicitDependencies(IFile file) {
        Set<IFile> files = new HashSet<IFile>();

        Collection<IResource> handles =
                new ImplicitDependencyIndexer(file).getDependencyHandles();
        if (handles != null) {
            for (IResource res : handles) {
                if (res instanceof IFile && res.exists()) {
                    files.add((IFile) res);
                }
            }
        }

        return files;
    }

    /**
     * @param delta
     *            The resource delta.
     * @param monitor
     *            The progress monitor.
     * @throws CoreException
     *             If there was a problem during the build.
     */
    private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor)
            throws CoreException {

        IProject project = getProject();
        reValidateProject(project);
        Set<IFile> filesToValidate = new HashSet<IFile>();

        // If the delta contains children then validate
        if (delta.getAffectedChildren().length > 0) {

            specialFolderResourceValidator.validateAffectedSpecialFolders(this);

            ValidationDeltaVisitor validationVisitor =
                    new ValidationDeltaVisitor(monitor);
            delta.accept(validationVisitor);

            filesToValidate.addAll(validationVisitor.getFilesToValidate());

            /*
             * If files have been deleted then re-validate all resources that
             * depended on these files.
             */
            Set<IFile> deletedFiles = validationVisitor.getDeletedFiles();
            if (!deletedFiles.isEmpty()) {
                for (IFile deletedFile : deletedFiles) {
                    Collection<IResource> inverseDependencies =
                            getInverseDependencies(deletedFile);
                    for (IResource inverseDependency : inverseDependencies) {
                        if (inverseDependency instanceof IFile) {
                            filesToValidate.add((IFile) inverseDependency);
                        }
                    }
                }
            }

        } else if (delta.getKind() == IResourceDelta.NO_CHANGE
                && delta.getAffectedChildren().length == 0) {
            /**
             * Delta for referencing project. There was a change in referenced
             * project. Run workspace validation for the project.
             */
            WorkspaceResourceValidationManager resourceValidationManager =
                    new WorkspaceResourceValidationManager(project, true);
            resourceValidationManager.validate(project);
        }

        checkCancel(monitor, true);
        /*
         * Check the delta for any
         */
        Set<IProject> referenced =
                ProjectUtil.getReferencedProjectsHierarchy(project, null);

        if (referenced != null) {
            boolean runFullBuild = false;
            IProject refProject = null;
            boolean isProjectOpened = false;
            for (IProject ref : referenced) {

                if (ref.isAccessible()) {
                    if (ref.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                        IResourceDelta refDelta = getDelta(ref);

                        if (refDelta != null) {
                            if ((refDelta.getFlags() & IResourceDelta.OPEN) != 0) {
                                isProjectOpened = true;
                            }

                            filesToValidate
                                    .addAll(getFilesToValidateAsRefProjectChanged(refDelta));
                        }
                    }
                } else {
                    // If project was closed then run full build
                    IResourceDelta refDelta = getDelta(ref);
                    if (refDelta != null
                            && (refDelta.getFlags() & IResourceDelta.OPEN) != 0) {
                        refProject = ref;
                        runFullBuild = true;
                        break;
                    }
                }
            }
            checkCancel(monitor, true);

            /*
             * If a referenced project has been opened then validate all special
             * folder resources (to check for duplicate resource where not
             * permitted).
             */
            if (isProjectOpened) {
                specialFolderResourceValidator
                        .validateAllSpecialFolders(project);
            }

            if (runFullBuild) {
                TRACE(String
                        .format("Full build requested because of change to referenced project '%s'", //$NON-NLS-1$
                                refProject));
                long start = System.currentTimeMillis();
                fullBuild(monitor);
                TRACE(String.format("Full build completed, took %d ms.", //$NON-NLS-1$
                        System.currentTimeMillis() - start));
            } else if (!filesToValidate.isEmpty()) {
                checkCancel(monitor, true);
                /* Index all changed resources */
                IndexResourceDeltaVisitor indexResDeltaVisitor =
                        new IndexResourceDeltaVisitor();
                delta.accept(indexResDeltaVisitor);
                Set<IFile> filesForIndexing =
                        indexResDeltaVisitor.getFilesForIndexing();
                index(filesForIndexing, monitor);

                // validate affected files
                validateFiles(filesToValidate, monitor);
            }
        }

        monitor.done();
    }

    /**
     * Get all the files in the local project that may have been affected as a
     * result of the change to a resource in the referenced project.
     * 
     * @param delta
     * @return
     * @throws CoreException
     */
    private Set<IFile> getFilesToValidateAsRefProjectChanged(
            IResourceDelta delta) throws CoreException {
        final Set<IFile> files = new HashSet<IFile>();
        final IProject thisProject = getProject();
        final Set<IResource> affectedFiles = new HashSet<IResource>();

        delta.accept(new IResourceDeltaVisitor() {

            public boolean visit(IResourceDelta delta) throws CoreException {
                IResource resource = delta.getResource();

                /*
                 * Ignore derived/hidden resources
                 */
                if (shouldIgnoreResourceDelta(resource)) {
                    return false;
                }

                if (resource instanceof IFile) {
                    if (isDeltaOfInterest(delta)) {
                        affectedFiles.add(resource);
                        for (IResource refResource : WorkingCopyUtil
                                .getAffectedResources(resource)) {
                            if (thisProject.equals(refResource.getProject())) {
                                files.add((IFile) refResource);
                            }
                        }
                    }
                    return false;
                }
                return true;
            }
        });

        // SCF-126: Validate possible proxy dependents
        Set<IResource> dependents =
                ValidationUtil.getPossibleProxyDependents(affectedFiles);
        if (!dependents.isEmpty()) {
            for (IResource res : dependents) {
                if (res instanceof IFile) {
                    files.add((IFile) res);
                }
            }
        }

        return files;
    }

    /**
     * Check if the given delta is of interest to this builder. This will check
     * whether the resource has been added, removed or the contents have
     * changed.
     * 
     * @param delta
     * @return
     */
    private boolean isDeltaOfInterest(IResourceDelta delta) {
        if (delta.getKind() == IResourceDelta.ADDED
                || delta.getKind() == IResourceDelta.REMOVED
                || (delta.getKind() == IResourceDelta.CHANGED && (delta
                        .getFlags() & IResourceDelta.CONTENT) != 0)) {

            IResource resource = delta.getResource();
            if (!(resource instanceof IFile) || filter.accept((IFile) resource)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Validate all files in the set and all files that depends on them. It take
     * care of the implicit dependencies that could be created during
     * validation.
     * 
     * @param filesToValidate
     * @throws CoreException
     */
    private void validateFiles(Set<IFile> filesToValidate,
            IProgressMonitor monitor) throws CoreException {

        // extend the set with affected objects
        monitor.subTask(Messages.ValidationBuilder_calculatingDependencies_monitor_shortdesc);
        Set<IFile> forValidation = addAffectedResources(filesToValidate);

        Set<IFile> validated = new HashSet<IFile>();

        while (!forValidation.isEmpty()) {
            // validate all files that can be validated
            for (IFile file : forValidation) {
                if (filter.accept(file)) {
                    monitor.subTask(String
                            .format(Messages.ValidationBuilder_validating_progress_message,
                                    file.getFullPath().toString()));
                    validate(file, true);
                }
                monitor.worked(1);
                checkCancel(monitor, true);
            }
            validated.addAll(forValidation);

            /*
             * Work out if any more implicit dependencies have been added - if
             * so then validated them too
             */
            monitor.subTask(Messages.ValidationBuilder_calculatingDependencies_monitor_shortdesc);
            forValidation = addAffectedResources(forValidation);

            // remove all already validated files
            forValidation.removeAll(validated);
        }
        monitor.done();
    }

    /**
     * Will clean invalid file markers on all invalid files.
     * 
     * @param monitor
     *            The progress monitor.
     * @throws CoreException
     *             If there was a problem during the build.
     * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        // clean invalid file markers
        final IProject project = getProject();
        TRACE(String.format("Clean requested on '%s'", project)); //$NON-NLS-1$
        long start = System.currentTimeMillis();
        reValidateProject(project);

        final IndexerService indexerService =
                XpdResourcesPlugin.getDefault().getIndexerService();

        monitor.subTask(String.format(Messages.ValidationBuilder_2,
                project.getName()));

        final List<WorkingCopy> invalidWorkingCopies =
                new ArrayList<WorkingCopy>();

        specialFolderResourceValidator.clean(project);

        // Clear lifecycle markers
        lifecycleValidator.removeMarkers(project);
        project.accept(new IResourceVisitor() {
            public boolean visit(IResource resource) throws CoreException {

                // Clear workspace resource validation markers
                clearWorkspaceResourceMarkers(resource);

                // Clear the EMF validation markers
                EMFValidator.getInstance().clean(resource, null);

                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(resource);
                if (wc != null) {
                    if (wc.isInvalidFile()
                            && !invalidWorkingCopies.contains(wc)) {
                        invalidWorkingCopies.add(wc);
                    }

                    List<IResource> eclipseResources = wc.getEclipseResources();

                    if (eclipseResources != null) {
                        for (IResource res : eclipseResources) {
                            if (res instanceof IFile) {
                                clearMarkers((IFile) res);
                            }
                        }
                    }

                    // Clear the marker severity cache
                    wc.clearSeverityCache();
                }
                return true;
            }
        });

        /*
         * Clear the implicit dependency indexer of all resources from this
         * project
         */
        ImplicitDependencyIndexer.clearIndexer(project);

        monitor.beginTask(String.format(Messages.ValidationBuilder_3,
                project.getName()),
                invalidWorkingCopies.size());
        for (WorkingCopy wc : invalidWorkingCopies) {
            monitor.worked(1);
            wc.reLoad();
        }

        indexerService.clean(project, monitor);

        TRACE(String.format("Clean completed in %d ms.", System //$NON-NLS-1$
                .currentTimeMillis() - start));
    }

    private void clearWorkspaceResourceMarkers(final IResource resource) {
        if (resource != null) {
            try {
                resource.deleteMarkers(ValidationActivator.WORKSPACE_RESOURCE_MARKER_ID,
                        true,
                        IResource.DEPTH_ZERO);
            } catch (CoreException e) {
                ValidationActivator.getDefault().getLogger().error(e);
            }
        }
    }

    /**
     * @param file
     *            The file to remove validation markers from.
     */
    private void clearMarkers(final IFile file) {
        IWorkspace workspace = file.getWorkspace();
        try {
            workspace.run(new IWorkspaceRunnable() {

                public void run(IProgressMonitor monitor) throws CoreException {
                    List<IMarker> markers = new ArrayList<IMarker>();

                    // Clean all validation markers
                    markers.addAll(Arrays.asList(file
                            .findMarkers(XpdConsts.VALIDATION_MARKER_TYPE,
                                    true,
                                    IResource.DEPTH_ZERO)));

                    // Clean all cycled project markers
                    markers.addAll(Arrays.asList(file
                            .findMarkers(ValidationActivator.CYCLED_PROJECTS_MARKER_ID,
                                    true,
                                    IResource.DEPTH_ZERO)));

                    for (IMarker marker : markers) {
                        marker.delete();
                    }
                }
            },
                    null);
        } catch (CoreException e) {
            ValidationActivator.getDefault().getLogger().error(e);
        }
    }

    /**
     * Resource visitor to index files in a project on full build
     * 
     * 
     * @author bharge
     * @since 11 Nov 2014
     */
    private class IndexResourceVisitor implements IResourceVisitor {

        private final Set<IFile> filesForIndexing = new HashSet<IFile>();

        /**
         * @return the filesForIndexing
         */
        public Set<IFile> getFilesForIndexing() {

            return filesForIndexing;
        }

        /**
         * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
         * 
         * @param resource
         * @return
         * @throws CoreException
         */
        public boolean visit(IResource resource) throws CoreException {

            if (resource instanceof IFile) {

                filesForIndexing.add((IFile) resource);
            }

            return true;
        }

    }

    /**
     * Resource visitor to index files in a project on incremental build
     * 
     * 
     * @author bharge
     * @since 11 Nov 2014
     */
    private class IndexResourceDeltaVisitor implements IResourceDeltaVisitor {

        private final Set<IFile> filesForIndexing = new HashSet<IFile>();

        /**
         * @return the filesForIndexing
         */
        public Set<IFile> getFilesForIndexing() {

            return filesForIndexing;
        }

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        public boolean visit(IResourceDelta delta) throws CoreException {

            int kind = delta.getKind();
            IResource resource = delta.getResource();

            if (resource instanceof IFile) {

                if (kind == IResourceDelta.ADDED
                        || (kind == IResourceDelta.CHANGED && (delta.getFlags() & IResourceDelta.CONTENT) != 0)) {

                    filesForIndexing.add((IFile) resource);
                }
            } else if (resource instanceof IContainer) {

                IResourceDelta[] children = delta.getAffectedChildren();
                for (IResourceDelta child : children) {

                    child.accept(this);
                }
            }

            return true;
        }

    }

    interface FileCollector {
        Set<IFile> getFilesToValidate();
    }

    /**
     * @author nwilson
     */
    class ValidationResourceVisitor implements IResourceVisitor, FileCollector {

        private final Set<IFile> forValidation = new HashSet<IFile>();

        /** The progress monitor. */
        private final IProgressMonitor monitor;

        private final WorkspaceResourceValidationManager workspaceResourceValidator;

        /**
         * @param monitor
         *            The progress monitor.
         */
        public ValidationResourceVisitor(IProgressMonitor monitor) {
            this.monitor = monitor;

            workspaceResourceValidator =
                    new WorkspaceResourceValidationManager(getProject());
        }

        /**
         * @return
         */
        public Set<IFile> getFilesToValidate() {
            return forValidation;
        }

        /**
         * @param resource
         *            The resource to visit.
         * @return false.
         * @throws CoreException
         *             If there was a problem visiting the resource.
         * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
         */
        public boolean visit(IResource resource) throws CoreException {

            /*
             * If this is a derived / hidden resource then ignore it
             */
            if (shouldIgnoreResourceDelta(resource)) {
                return false;
            }
            checkCancel(monitor, true);

            // Validate workspace resources
            clearWorkspaceResourceMarkers(resource);
            workspaceResourceValidator.validate(resource);

            if (resource instanceof IFile) {
                IFile file = (IFile) resource;
                clearMarkers(file);
                // if (filter.accept(file)) {
                // validate(resource, true);
                // }
                forValidation.add(file);

                monitor.done();
            }
            return true;
        }

    }

    /**
     * @author nwilson
     */
    class ValidationDeltaVisitor implements IResourceDeltaVisitor,
            FileCollector {

        private final Set<IFile> forValidation = new HashSet<IFile>();

        private final Set<IFile> deletedFiles = new HashSet<IFile>();

        /**
         * Get the files that have been added or changed in the delta.
         * 
         * @return
         */
        public Set<IFile> getFilesToValidate() {
            return forValidation;
        }

        /**
         * Get the files that have been deleted in the delta.
         * 
         * @return
         */
        public Set<IFile> getDeletedFiles() {
            return deletedFiles;
        }

        /** The progress monitor. */
        private final IProgressMonitor monitor;

        private final WorkspaceResourceValidationManager workspaceResourceValidator;

        /**
         * @param monitor
         *            The progress monitor.
         */
        public ValidationDeltaVisitor(IProgressMonitor monitor) {
            this.monitor = monitor;

            workspaceResourceValidator =
                    new WorkspaceResourceValidationManager(getProject());
        }

        /**
         * @param delta
         *            The resource delta to visit.
         * @return false.
         * @throws CoreException
         *             If there was a problem visiting the resource.
         * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
         */
        public boolean visit(IResourceDelta delta) throws CoreException {
            int kind = delta.getKind();
            IResource resource = delta.getResource();

            /*
             * Ignore any derived/hidden resources
             */
            if (shouldIgnoreResourceDelta(resource)) {
                return false;
            }
            checkCancel(monitor, true);

            if (resource instanceof IFile) {
                IFile file = (IFile) resource;

                // add file for validation
                if (kind == IResourceDelta.ADDED
                        || (kind == IResourceDelta.CHANGED && (delta.getFlags() & IResourceDelta.CONTENT) != 0)) {
                    forValidation.add(file);
                }

                if (!filter.accept(file)) {
                    if (kind == IResourceDelta.ADDED) {
                        clearMarkers(file);
                    }
                }
                if (kind == IResourceDelta.ADDED
                        || kind == IResourceDelta.REMOVED) {

                    // Store all deleted files
                    if (kind == IResourceDelta.REMOVED) {
                        deletedFiles.add(file);
                    }
                }

            } else if (resource instanceof IContainer) {
                IResourceDelta[] children = delta.getAffectedChildren();
                for (IResourceDelta child : children) {
                    child.accept(this);
                }
            }

            // Validate the resource
            if (kind == IResourceDelta.ADDED) {
                clearWorkspaceResourceMarkers(resource);
            }
            workspaceResourceValidator.validate(resource);

            monitor.worked(1);
            return false;
        }
    }

    /**
     * @param resource
     *            The resource to validate.
     */
    public void validate(IResource resource) {
        validate(resource, false);
    }

    /**
     * @param resource
     *            The resource to validate.
     * @param clean
     *            true if all of the markers should be cleaned first.
     */
    public void validate(IResource resource, boolean clean) {
        // XPD-4044: Builder should not run when the workbench is closing.
        if (shouldRun()) {
            IProject project = resource.getProject();
            XpdProjectResourceFactory factory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory(project);
            WorkingCopy wc = factory.getWorkingCopy(resource);
            if (wc != null) {

                wc.getAttributes().put(ValidationActivator.LINKED_RESOURCE,
                        null);

                Collection<EObject> affected = new ArrayList<EObject>();
                affected.add(wc.getRootElement());
                Validator validator =
                        new Validator(ValidationManager.getInstance()
                                .getDestinations());
                try {
                    TRACE(String.format("Validating '%s'...", resource)); //$NON-NLS-1$
                    long start = System.currentTimeMillis();
                    validator.validate(resource, true, clean);

                    TRACE(String
                            .format("Validation completed in %d ms.", System //$NON-NLS-1$
                                    .currentTimeMillis() - start));
                } catch (Throwable e) {
                    ValidationActivator.getDefault().getLogger().error(e);
                }
            }
        }
    }

    /**
     * Forces the validation of the project.
     * 
     * @param project
     */
    void reValidateProject(IProject project) {
        getValidatedProjects().remove(project);
        validateProject(project);
    }

    /**
     * Validates the project. Checks if the project has cyclic dependencies.
     * 
     * @param project
     */
    void validateProject(IProject project) {
        // if (!getValidatedProjects().contains(project))
        {
            cycledProjectValidator.validate(project);
            getValidatedProjects().add(project);
        }
    }

    /**
     * Check if the given resource should be ignored for validation. This is
     * used during visiting resource deltas.
     * <p>
     * Resources that start with "." will be ignored.
     * </p>
     * 
     * @param resource
     * @return <code>true</code> if this resource should not be included for
     *         validation.
     */
    private boolean shouldIgnoreResourceDelta(IResource resource) {
        if (resource != null && resource.getName().startsWith(".")) { //$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * @return
     */
    public static ArrayList<IProject> getValidatedProjects() {
        if (validatedProjects == null) {
            validatedProjects = new ArrayList<IProject>();
        }
        return validatedProjects;
    }

    private static final void TRACE(String message) {
        LOG.trace(Logger.CATEGORY_BUILD,
                String.format("[%d]ValidationBuilder: ", Thread.currentThread() //$NON-NLS-1$
                        .getId()) + message);
    }

    /**
     * Check if this builder should run, not allowed to run when the workbench
     * is closing. Default implementation returns <code>true</code> if this is
     * not the RCP application. Subclasses may override
     * 
     * @return
     * @since 3.5.2
     */
    protected boolean shouldRun() {
        // XPD-4044: Builder should not run when the workbench is in closing
        // process.

        // SCF-154: Prevent NPE in headless mode.
        if (!XpdResourcesPlugin.isInHeadlessMode()
                && Workbench.getInstance() != null) {
            return (!Workbench.getInstance().isClosing());
        }
        return true;
    }

    /**
     * Cancel current processing.
     * 
     * @param monitor
     *            Progress monitor
     * @param forgetBuildSate
     *            <code>true</code> if build state should be forgotten
     */
    protected void checkCancel(IProgressMonitor monitor, boolean forgetBuildSate) {
        IncrementalProjectBuilder builder = forgetBuildSate ? this : null;
        ProjectUtil.checkBuildCancel(monitor, builder);
    }
}
