/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.validation.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtensionPoint;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.IResourceFilter;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.SpecialFolderFileBindingUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Special folder resource validator. This checks for duplicate resources within
 * special folders that do not allow it.
 * <p>
 * For a given project to validate this will find the top-most project in the
 * hierarchy chain that contains the project being validate and will start
 * validating from that point down.
 * </p>
 * 
 * @author njpatel
 * @since 3.5.3
 */
public class SpecialFolderResourceValidator {

    private static final String ERR_MSG =
            Messages.SpecialFolderResourceValidator_duplicateResource_error_longdesc;

    private static final String DUPLICATE_RESOURCE_MARKER_ID =
            "com.tibco.xpd.validation.duplicateResource"; //$NON-NLS-1$

    private static final String ATT_KIND = "kind"; //$NON-NLS-1$

    private final List<ISpecialFolderModel> specialFolderModels;

    private final Map<String, List<Pattern>> fileMatchPatternsMap;

    /**
     * Special folder resource validator.
     */
    public SpecialFolderResourceValidator() {
        specialFolderModels =
                getSpecialFolderKindsThatDoNotAllowDuplicateResources();
        fileMatchPatternsMap = new HashMap<String, List<Pattern>>();
    }

    /**
     * To be called during incrememtal build. This will look at the change delta
     * to identify which resources have been added/removed and will validate the
     * affected special folders in the project (and reference hierarchy) being
     * built.
     * 
     * @param builder
     *            to builder to get hold of the change deltas.
     * @throws CoreException
     */
    public void validateAffectedSpecialFolders(IncrementalProjectBuilder builder)
            throws CoreException {

        /*
         * From the change delta in the project being built (and any referenced
         * projects) work out the special folders that have been affected so we
         * can limit the special folder resource validation to these kinds of
         * special folders only.
         */
        Set<String> sfKinds = new HashSet<String>();
        IProject project = builder.getProject();
        IResourceDelta delta = builder.getDelta(project);
        if (delta != null && delta.getAffectedChildren().length > 0) {
            DeltaVisitor visitor = new DeltaVisitor(project);
            delta.accept(visitor);
            sfKinds.addAll(visitor.getAffectedSpecialFolderKinds());
        }

        // Look at all deltas of referenced projects
        Set<IProject> refProjects;
        try {
            refProjects =
                    ProjectUtil2.getReferencedProjectsHierarchy(project, true);

            for (IProject refProject : refProjects) {
                delta = builder.getDelta(refProject);
                if (delta != null && delta.getAffectedChildren().length > 0) {
                    DeltaVisitor visitor = new DeltaVisitor(refProject);
                    delta.accept(visitor);
                    sfKinds.addAll(visitor.getAffectedSpecialFolderKinds());
                }
            }
        } catch (CyclicDependencyException e) {
            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .error(String.format(Messages.SpecialFolderResourceValidator_projectCyclicDependency_error_shortdesc,
                            project.getName()));
        }

        if (!sfKinds.isEmpty()) {
            List<ISpecialFolderModel> sfKindsToValidate =
                    new ArrayList<ISpecialFolderModel>();
            for (ISpecialFolderModel model : specialFolderModels) {
                if (sfKinds.contains(model.getKind())) {
                    sfKindsToValidate.add(model);
                }
            }

            if (!sfKindsToValidate.isEmpty()) {
                validate(project, sfKindsToValidate);
            }
        }

    }

    /**
     * Validate all special folders of the given project (and reference
     * hierarchy).
     * 
     * @param project
     * @throws CoreException
     */
    public void validateAllSpecialFolders(IProject project)
            throws CoreException {
        // Validate all special folder kinds
        validate(project, specialFolderModels);
    }

    /**
     * Remove all duplicate resource markers from the given project.
     * 
     * @param project
     * @throws CoreException
     */
    public void clean(IProject project) throws CoreException {
        IMarker[] markers = getDuplicateResourceMarkers(project);
        for (IMarker marker : markers) {
            if (marker.exists()) {
                marker.delete();
            }
        }
    }

    /**
     * Remove all duplicate resource markers from the project that relate to the
     * given special folder kind.
     * 
     * @param project
     * @param specialFolderKind
     * @throws CoreException
     */
    private void clearMarkers(IProject project,
            ISpecialFolderModel specialFolderKind) throws CoreException {
        IMarker[] markers = getDuplicateResourceMarkers(project);
        for (IMarker marker : markers) {

            if (marker.exists()
                    && specialFolderKind.getKind()
                            .equals(marker.getAttribute(ATT_KIND))) {
                marker.delete();
            }
        }
    }

    /**
     * Create a problem marker (if one doesn't already exist) on the given
     * project (will create a problem marker for each duplicate path).
     * 
     * @param project
     *            project to add marker to
     * @param sfKind
     *            special folder that has duplicate resource
     * @param duplicatePaths
     *            duplicate files found in the references
     * @throws CoreException
     */
    private void createIssue(IProject project, ISpecialFolderModel sfKind,
            Set<String> duplicatePaths) throws CoreException {

        IMarker[] existingMarkers = getDuplicateResourceMarkers(project);
        List<IMarker> markersToRemove = new ArrayList<IMarker>();

        // Check if the marker already exist for the duplicate resource
        for (IMarker marker : existingMarkers) {
            if (marker.exists()) {
                if (sfKind.getKind().equals(marker.getAttribute(ATT_KIND))
                        && duplicatePaths.contains(marker
                                .getAttribute(IMarker.LOCATION))) {
                    /*
                     * Leave this marker as is as it still applies (so don't
                     * report another marker for this path)
                     */
                    duplicatePaths
                            .remove(marker.getAttribute(IMarker.LOCATION));
                } else {
                    markersToRemove.add(marker);
                }
            }
        }

        /* Remove any makers that don't apply anymore */
        for (IMarker marker : markersToRemove) {
            marker.delete();
        }

        // Add marker for each new duplicate file reported
        for (String path : duplicatePaths) {
            IMarker marker = project.createMarker(DUPLICATE_RESOURCE_MARKER_ID);
            marker.setAttribute(ATT_KIND, sfKind.getKind());
            marker.setAttribute(IMarker.MESSAGE,
                    String.format(ERR_MSG, path, sfKind.getName()));
            marker.setAttribute(IMarker.LOCATION, path);
            marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
        }
    }

    /**
     * Get all duplicate resource markers from the given project.
     * 
     * @param project
     * @return array of markers, empty array if none found.
     * @throws CoreException
     */
    private IMarker[] getDuplicateResourceMarkers(IProject project)
            throws CoreException {
        return project.findMarkers(DUPLICATE_RESOURCE_MARKER_ID,
                false,
                IResource.DEPTH_ZERO);
    }

    /**
     * Validate the project for duplicate resources in special folders of the
     * given kind. If this project is referenced by other XPD projects then this
     * will find all the projects at the top-most in the hierarchy tree that
     * contains this project and will validate from that point down.
     * 
     * @param project
     * @param sfKindsToValidate
     *            special folder kinds to validate.
     */
    private void validate(IProject project,
            List<ISpecialFolderModel> sfKindsToValidate) {
        for (IProject topLevelProject : getTopLevelProjects(project,
                new HashSet<IProject>())) {
            validateTopLevelProject(topLevelProject, sfKindsToValidate);
        }
    }

    /**
     * Validate the given top-level project (project at the top of the hierarchy
     * tree).
     * 
     * @param project
     */
    private void validateTopLevelProject(IProject project,
            List<ISpecialFolderModel> sfKinds) {
        for (ISpecialFolderModel sfKind : sfKinds) {
            validate(project, sfKind, new HashMap<IProject, List<IFile>>());
        }
    }

    /**
     * Validate the given project for duplicate resources in the special folder
     * kind and return all resources in the special folder. If a duplicate
     * resource is found this method will add the problem marker to the project.
     * <p>
     * Note that this is a recursive method.
     * </p>
     * 
     * @param project
     *            project to validate
     * @param specialFolderKind
     *            special folder kind to validate
     * @param processedProjects
     *            cache to maintain all projects already processed in the
     *            hierarchy tree (to avoid repeated/cyclic calculations)
     * @return list containing special folder relative paths to all resources
     *         that this project can access under the given special folder.
     */
    private List<IFile> validate(IProject project,
            ISpecialFolderModel specialFolderKind,
            Map<IProject, List<IFile>> processedProjects) {

        List<IFile> allFiles = processedProjects.get(project);

        if (allFiles != null) {
            /*
             * If we have already processed this project then just return its
             * last result
             */
            return allFiles;
        }

        /* Add empty list for now and then add full list once calculated */
        processedProjects.put(project, new ArrayList<IFile>(0));
        allFiles = new ArrayList<IFile>();

        try {
            allFiles.addAll(getFilesInSpecialFolder(project,
                    specialFolderKind.getKind()));
        } catch (CoreException e1) {
            // do nothing
        }

        try {
            /*
             * Check all referenced XPD projects
             */
            for (IProject refProject : project.getReferencedProjects()) {
                if (refProject.isAccessible()
                        && refProject.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    List<IFile> files =
                            validate(refProject,
                                    specialFolderKind,
                                    processedProjects);

                    for (IFile file : files) {
                        if (!allFiles.contains(file)) {
                            allFiles.add(file);
                        }
                    }
                }
            }
        } catch (CoreException e) {
            // TODO: Error
        }

        if (!allFiles.isEmpty()) {
            Set<IPath> relativePaths = new HashSet<IPath>();
            Set<String> duplicatePaths = new HashSet<String>();
            for (Iterator<IFile> iter = allFiles.iterator(); iter.hasNext();) {
                /*
                 * SCF-151: we were making a call to
                 * getSpecialFolderRelativePath(IFile) which looks at special
                 * folder's file binding for a given file. This was failing to
                 * find the duplicate resources between Generated Services and
                 * Service Descriptors folder. (If there exists a wsdl file with
                 * same name under Generated Services and Service Descriptors it
                 * was failing to mark them as duplicate).
                 * 
                 * For wsdl files we do not have any file binding to special
                 * folder because for SOA projects wsdl files can be anywhere
                 * not under Service Descriptors special folder.
                 */
                IPath relativePath =
                        SpecialFolderUtil.getSpecialFolderRelativePath(iter
                                .next(), specialFolderKind.getKind());
                if (relativePath != null) {
                    if (!relativePaths.contains(relativePath)) {
                        relativePaths.add(relativePath);
                    } else {
                        duplicatePaths.add(relativePath.toString());
                        // Remove duplicate file from list
                        iter.remove();
                    }
                }

            }

            try {
                if (!duplicatePaths.isEmpty()) {
                    // Report duplicate resources
                    createIssue(project, specialFolderKind, duplicatePaths);
                } else {
                    clearMarkers(project, specialFolderKind);
                }
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        processedProjects.put(project, allFiles);

        return allFiles;
    }

    /**
     * Get the files in the special folder of kind given. If the special folder
     * file binding extension(s) is configured then only files that match the
     * binding will be reported.
     * 
     * @param project
     * @param specialFolderKind
     * @return list of files, empty list if none found.
     * @throws CoreException
     */
    private List<IFile> getFilesInSpecialFolder(IProject project,
            String specialFolderKind) throws CoreException {

        List<IFile> resources = new ArrayList<IFile>();

        Collection<IResource> allResources =
                SpecialFolderUtil
                        .getAllDeepResourcesInSpecialFolderKind(project,
                                specialFolderKind,
                                new IResourceFilter() {

                                    public boolean includeResource(
                                            IResource resource) {

                                        /*
                                         * SCF-159: we are not interested in
                                         * resources from svn
                                         */
                                        if (resource.getName().startsWith(".")) { //$NON-NLS-1$
                                            return false;
                                        }
                                        return true;
                                    }
                                });

        if (!allResources.isEmpty()) {
            List<Pattern> patterns = getFileMatchPatterns(specialFolderKind);
            for (IResource res : allResources) {

                if (res instanceof IFile) {
                    IResource resToAdd = null;

                    if (!patterns.isEmpty()) {
                        for (Pattern pattern : patterns) {
                            if (pattern.matcher(res.getName()).matches()) {
                                resToAdd = res;
                                break;
                            }
                        }
                    } else {
                        resToAdd = res;
                    }

                    if (resToAdd != null) {
                        resources.add((IFile) resToAdd);
                    }
                }
            }
        }
        return resources;
    }

    /**
     * Get all top-level projects in the reference hierarchy that contains the
     * given project.
     * 
     * @param project
     * @param processedProjects
     *            temporary cache to stop cyclic processing
     * @return list containing the project if this project is not referenced by
     *         any other, or all top-level projects in the reference hierarchy
     *         that contains this project.
     */
    private List<IProject> getTopLevelProjects(IProject project,
            Set<IProject> processedProjects) {
        List<IProject> topLevelProjects = new ArrayList<IProject>();
        List<IProject> refProjects = getReferencingXPDProjects(project);

        if (refProjects.isEmpty()) {
            // This is a top-level XPD project
            topLevelProjects.add(project);
        } else {
            for (IProject referencingProject : refProjects) {
                if (!processedProjects.contains(referencingProject)) {
                    processedProjects.add(referencingProject);

                    // Get project that reference this referencing project
                    refProjects = getReferencingXPDProjects(referencingProject);
                    if (refProjects.isEmpty()) {
                        // This is the top-level XPD project
                        topLevelProjects.add(referencingProject);
                    } else {
                        // This is not top-level XPD project so continue up the
                        // hierarchy
                        topLevelProjects
                                .addAll(getTopLevelProjects(referencingProject,
                                        processedProjects));
                    }
                }
            }
        }

        return topLevelProjects;
    }

    /**
     * Get a list of registered special folder kinds that do not allow duplicate
     * resources.
     * 
     * @return
     */
    private List<ISpecialFolderModel> getSpecialFolderKindsThatDoNotAllowDuplicateResources() {
        List<ISpecialFolderModel> models = new ArrayList<ISpecialFolderModel>();

        for (ISpecialFolderModel model : SpecialFoldersExtensionPoint
                .getInstance().getExtensions()) {
            if (!model.isDuplicateResourcesAllowed()) {
                models.add(model);
            }
        }

        return models;
    }

    /**
     * If the file binding extension(s) has been defined for the given special
     * folder kind then get all file match patterns to compare with files
     * present in the special folders of this kind.
     * 
     * @param specialFolderKind
     * @return list of patterns, empty list if none defined.
     */
    private List<Pattern> getFileMatchPatterns(String specialFolderKind) {
        List<Pattern> patterns = fileMatchPatternsMap.get(specialFolderKind);

        if (patterns == null) {
            String[] filePatterns =
                    SpecialFolderFileBindingUtil.getInstance()
                            .getFilePatterns(specialFolderKind);
            patterns = new ArrayList<Pattern>(filePatterns.length);
            fileMatchPatternsMap.put(specialFolderKind, patterns);

            for (String pattern : filePatterns) {
                try {
                    patterns.add(Pattern.compile(pattern));
                } catch (PatternSyntaxException e) {
                    // Ignore and continue
                }
            }
        }

        return patterns;
    }

    /**
     * Get the list of XPD-nature projects that reference the given project.
     * 
     * @param project
     * @return list of projects, empty list if project has no (accessible)
     *         referencing projects or none of the referencing projects have XPD
     *         nature.
     */
    private List<IProject> getReferencingXPDProjects(IProject project) {
        List<IProject> referencingProjects = new ArrayList<IProject>();

        for (IProject refProject : project.getReferencingProjects()) {
            try {
                if (refProject.isAccessible()
                        && refProject.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                    referencingProjects.add(refProject);
                }
            } catch (CoreException e) {
                // Do nothing and continue with next project
            }
        }

        return referencingProjects;
    }

    /**
     * Resource delta visitor that will record the special folder kinds, if any,
     * of resources that have been added or deleted in the build.
     */
    private class DeltaVisitor implements IResourceDeltaVisitor {

        private final Set<String> specialFolderKinds = new HashSet<String>();

        private ProjectConfig config;

        public DeltaVisitor(IProject project) {
            config = XpdResourcesPlugin.getDefault().getProjectConfig(project);
        }

        /**
         * Get the special folder kinds of all special folders that have had
         * resources added to or removed from.
         * 
         * @return
         */
        public Set<String> getAffectedSpecialFolderKinds() {
            return specialFolderKinds;
        }

        /**
         * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
         * 
         * @param delta
         * @return
         * @throws CoreException
         */
        public boolean visit(IResourceDelta delta) throws CoreException {
            if (config == null) {
                return false;
            }

            // Only interested in resources that have been added or removed
            // (which will include moved and renamed resources)
            if (delta.getKind() == IResourceDelta.ADDED
                    || delta.getKind() == IResourceDelta.REMOVED) {
                IResource resource = delta.getResource();

                if (resource instanceof IFile) {
                    SpecialFolder sf =
                            config.getSpecialFolders()
                                    .getFolderContainer(resource);
                    if (sf != null) {
                        List<Pattern> patterns =
                                getFileMatchPatterns(sf.getKind());
                        if (!patterns.isEmpty()) {
                            for (Pattern pattern : patterns) {
                                if (pattern.matcher(resource.getName())
                                        .matches()) {
                                    specialFolderKinds.add(sf.getKind());
                                    break;
                                }
                            }
                        } else {
                            specialFolderKinds.add(sf.getKind());
                        }
                    }
                }
            }
            return true;
        }

    }
}
