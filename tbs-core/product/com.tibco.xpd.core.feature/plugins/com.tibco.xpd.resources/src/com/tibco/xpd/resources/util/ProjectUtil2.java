/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.resources.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Provides methods to get the dependency hierarchy for a given project. This
 * methods (in additions to methods provided in {@link ProjectUtil}) will throws
 * exceptions when a cyclic dependency is detected.
 * 
 * @author njpatel
 * @since 3.5.1.3
 */
public final class ProjectUtil2 {

    /**
     * Get the full hierarchy of referencing projects.
     * 
     * @param project
     *            eclipse project.
     * 
     * @return Set set of referencing projects, empty set if none.
     * @throws CyclicDependencyException
     *             thrown when a cyclic dependency is detected.
     */
    public static Set<IProject> getReferencingProjectsHierarchy(
            IProject project, Set<IProject> referencingProjectsHierarchy)
            throws CyclicDependencyException {
        // Using linkedHashSet to get a predictable list - items ordered in
        // order of insertion
        Set<IProject> referencedProjectsHierarchy =
                new LinkedHashSet<IProject>();

        /*
         * Start with the original project in already processed list. If we come
         * across it again following the hierarchy then we will throw an error.
         */
        Set<IProject> alreadyOnHierarchyPathList = new HashSet<IProject>();
        alreadyOnHierarchyPathList.add(project);

        internalGetReferencingProjectsHierarchy(project,
                referencedProjectsHierarchy,
                alreadyOnHierarchyPathList);

        return referencedProjectsHierarchy;
    }

    /**
     * @param projects
     *            collection of eclipse projects to be considered
     * @return collection of projects directly referencing the
     *         <code>projects<code> specified
     * @throws CyclicDependencyException
     */
    public static Set<IProject> getReferencingProjectsHierarchies(
            Set<IProject> projects) throws CyclicDependencyException {

        Set<IProject> associatedProjects = new LinkedHashSet<IProject>();
        for (IProject proj : projects) {
            associatedProjects
                    .addAll(getReferencingProjectsHierarchy(proj, null));
        }

        return associatedProjects;
    }

    /**
     * Get the full hierarchy of referenced projects.
     * 
     * @param project
     *            eclipse project.
     * @param referencedProjectsHierarchy
     *            current set of included projects
     * @param wantXpdNatureOnly
     *            <code>true</code> if only projects with XPD nature set should
     *            be included.
     * 
     * @return Set set of referenced projects, empty set if none.
     * @throws CyclicDependencyException
     *             thrown when a cyclic dependency is detected.
     * @since 3.5.1.3
     */
    public static Set<IProject> getReferencedProjectsHierarchy(IProject project,
            boolean wantXpdNatureOnly) throws CyclicDependencyException {

        // Using linkedHashSet to get a predictable list - items ordered in
        // order of insertion
        Set<IProject> referencedProjectsHierarchy =
                new LinkedHashSet<IProject>();

        /*
         * Start with the original project in already processed list. If we come
         * across it again following the hierarchy then we will throw an error.
         */
        Set<IProject> alreadyOnHierarchyPathList = new HashSet<IProject>();
        alreadyOnHierarchyPathList.add(project);

        internalGetReferencedProjectsHierarchy(project,
                referencedProjectsHierarchy,
                wantXpdNatureOnly,
                alreadyOnHierarchyPathList);

        return referencedProjectsHierarchy;
    }

    /**
     * @param projects
     *            collection of eclipse projects to be considered
     * @param wantXpdNatureOnly
     *            Whether only interested in xpd natured projects.
     * @return collection of projects directly and/or indirectly referenced by
     *         the <code>projects<code> specified
     * @throws CyclicDependencyException
     */
    public static Set<IProject> getReferencedProjectsHierarchies(
            Set<IProject> projects, boolean wantXpdNatureOnly)
            throws CyclicDependencyException {

        Set<IProject> associatedProjects = new LinkedHashSet<IProject>();
        for (IProject proj : projects) {
            associatedProjects.addAll(ProjectUtil2
                    .getReferencedProjectsHierarchy(proj, wantXpdNatureOnly));
        }

        return associatedProjects;

    }

    /**
     * @param project
     *            project to get refs for.
     * @param referencedProjectsHierarchy
     *            Set of referenced projects to add to.
     * @param wantXpdNatureOnly
     *            Whether only interested in xpd natured projects.
     * @param alreadyOnHierarchyPathList
     *            The set of projects we have found above between this project
     *            and the original project requested in
     *            {@link #getReferencingProjectsHierarchy(IProject, Set)}
     * @throws CyclicDependencyException
     */
    private static void internalGetReferencedProjectsHierarchy(IProject project,
            Set<IProject> referencedProjectsHierarchy,
            boolean wantXpdNatureOnly, Set<IProject> alreadyOnHierarchyPathList)
            throws CyclicDependencyException {
        // Get the referencing Projects
        IProject[] referencedProjects = null;
        try {
            referencedProjects = project.getReferencedProjects();
        } catch (CoreException e) {
            // Do nothing, this probably means that
            // This project does not exist or
            // This project is not open
        }
        if (referencedProjects != null) {
            for (int i = 0; i < referencedProjects.length; i++) {
                IProject referencedProject = referencedProjects[i];
                try {
                    if (referencedProject != null
                            && (!wantXpdNatureOnly || referencedProject
                                    .hasNature(XpdConsts.PROJECT_NATURE_ID))) {
                        // Add the project to the list of projects hierarchy if
                        // it is not in the list

                        /*
                         * Make sure that we haven't come across this project on
                         * the stack of project processed along this hierarchy
                         * path so far.
                         * 
                         * If we have then its a cycle!!
                         */
                        if (!alreadyOnHierarchyPathList
                                .contains(referencedProject)) {

                            /*
                             * Only bother adding and recursing for a project if
                             * we haven't done so already (this ISN'T a cycle,
                             * it's 2 separate projects in the hierarchy tree
                             * both referencing the same child project BUT not
                             * on the same tree path.
                             */
                            if (!referencedProjectsHierarchy
                                    .contains(referencedProject)) {
                                referencedProjectsHierarchy
                                        .add(referencedProject);

                                /*
                                 * Add this project to the already processed on
                                 * this path thru the hierarchy list.
                                 * 
                                 * Then if we come across it again whilst
                                 * following THIS path then an exception will be
                                 * thrown.
                                 */
                                alreadyOnHierarchyPathList
                                        .add(referencedProject);

                                internalGetReferencedProjectsHierarchy(
                                        referencedProject,
                                        referencedProjectsHierarchy,
                                        wantXpdNatureOnly,
                                        alreadyOnHierarchyPathList);

                                /*
                                 * Now remove the project from the already
                                 * processed list (for this hierarchy path).
                                 * Because if we hit the SAME project following
                                 * the references from another of the sibling
                                 * references then THAT is OK.
                                 */
                                alreadyOnHierarchyPathList
                                        .remove(referencedProject);
                            }

                        } else {
                            throw new CyclicDependencyException();
                        }
                    }
                } catch (CoreException e) {

                }
            }
        }
        return;
    }

    /**
     * @param project
     *            project to get refs for.
     * @param referencingProjectsHierarchy
     *            Set of referenced projects to add to.
     * @param wantXpdNatureOnly
     *            Whether only interested in xpd natured projects.
     * @param alreadyOnHierarchyPathList
     *            The set of projects we have found above between this project
     *            and the original project requested in
     *            {@link #getReferencingProjectsHierarchy(IProject, Set)}
     * @throws CyclicDependencyException
     */
    private static void internalGetReferencingProjectsHierarchy(
            IProject project, Set<IProject> referencingProjectsHierarchy,
            Set<IProject> alreadyOnHierarchyPathList)
            throws CyclicDependencyException {
        // Get the referencing Projects
        IProject[] referencingProjects = null;
        referencingProjects = project.getReferencingProjects();

        if (referencingProjects != null) {
            for (int i = 0; i < referencingProjects.length; i++) {
                IProject referencingProject = referencingProjects[i];
                if (referencingProject != null) {
                    // Add the project to the list of projects hierarchy if
                    // it is not in the list

                    /*
                     * Make sure that we haven't come across this project on the
                     * stack of project processed along this hierarchy path so
                     * far.
                     * 
                     * If we have then its a cycle!!
                     */
                    if (!alreadyOnHierarchyPathList
                            .contains(referencingProject)) {

                        /*
                         * Only bother adding and recursing for a project if we
                         * haven't done so already (this ISN'T a cycle, it's 2
                         * separate projects in the hierarchy tree both
                         * referencing the same child project BUT not on the
                         * same tree path.
                         */
                        if (!referencingProjectsHierarchy
                                .contains(referencingProject)) {
                            referencingProjectsHierarchy
                                    .add(referencingProject);

                            /*
                             * Add this project to the already processed on this
                             * path thru the hierarchy list.
                             * 
                             * Then if we come across it again whilst following
                             * THIS path then an exception will be thrown.
                             */
                            alreadyOnHierarchyPathList.add(referencingProject);

                            internalGetReferencingProjectsHierarchy(
                                    referencingProject,
                                    referencingProjectsHierarchy,
                                    alreadyOnHierarchyPathList);

                            /*
                             * Now remove the project from the already processed
                             * list (for this hierarchy path). Because if we hit
                             * the SAME project following the references from
                             * another of the sibling references then THAT is
                             * OK.
                             */
                            alreadyOnHierarchyPathList
                                    .remove(referencingProject);
                        }

                    } else {
                        throw new CyclicDependencyException();
                    }
                }

            }
        }
        return;
    }

    /**
     * Determines all projects that reference, directly or indirectly, those
     * projects specified. This overloaded version of
     * <code>getReferencingProjectsHierarchies()</code> should be used when the
     * project considered have not been loaded into a workspace and therefore
     * <code>Project</code> instances are not available to determine the lines
     * of ancestor projects
     * 
     * @param targetProjectDescriptions
     *            eclipse projects from which project hierarchies are sought
     * @param projectsSuperset
     *            all visible projects defined by their
     *            <code>IProjectDescription</code>s (used for lookup purposes)
     * @return project hierarchies as derived from the specified target projects
     * @throws CyclicDependencyException
     */
    public static Set<IProjectDescription> getReferencingProjectsHierarchies(
            Set<IProjectDescription> targetProjectDescriptions,
            Set<IProjectDescription> projectsSuperset)
            throws CyclicDependencyException {

        Set<IProjectDescription> associatedProjects =
                new HashSet<IProjectDescription>();

        if ((targetProjectDescriptions != null)
                && !targetProjectDescriptions.isEmpty()) {

            Set<IProjectDescription> associatedProjectsOnCurrentLineage =
                    new HashSet<IProjectDescription>();

            Map<String, IProjectDescription> projectsSupersetMap =
                    new HashMap<String, IProjectDescription>();
            for (IProjectDescription projDesc : projectsSuperset)
                projectsSupersetMap.put(projDesc.getName(), projDesc);

            for (IProjectDescription projDesc : targetProjectDescriptions) {

                if (projDesc != null) {
                    associatedProjectsOnCurrentLineage.add(projDesc);
                    internalGetReferencingProjectsHierarchy(associatedProjects,
                            associatedProjectsOnCurrentLineage,
                            projDesc,
                            projectsSupersetMap);
                    associatedProjectsOnCurrentLineage.remove(projDesc);
                }

            }
        }

        return associatedProjects;
    }

    /**
     * Determines all projects that are referencing, directly or indirectly, the
     * specified project. This overloaded version of
     * <code>internalGetReferencingProjectsHierarchy()</code> should be used
     * when the project being considered has not been loaded into a workspace
     * and therefore a <code>Project</code> instance is not available to
     * determine lines of ancestor projects
     * 
     * @param associatedProjects
     *            projects that have been found to be ancestors of the specified
     *            project
     * @param associatedProjectsOnCurrentLineage
     *            all projects on a current branch of investigation
     * @param projDesc
     *            an eclipse project's <code>ProjectDescription</code>
     * @param projectsSuperset
     *            all visible projects defined by their
     *            <code>IProjectDescription</code>s (used for lookup purposes)
     * @throws CyclicDependencyException
     */
    private static void internalGetReferencingProjectsHierarchy(
            Set<IProjectDescription> associatedProjects,
            Set<IProjectDescription> associatedProjectsOnCurrentLineage,
            IProjectDescription projDesc,
            Map<String, IProjectDescription> projectsSuperset)
            throws CyclicDependencyException {

        if (projDesc != null) {

            for (String candidateProjName : projectsSuperset.keySet()) {
                IProjectDescription candidateProjDesc =
                        projectsSuperset.get(candidateProjName);
                for (IProject referencedProj : candidateProjDesc
                        .getReferencedProjects()) {
                    IProjectDescription referencedProjDesc =
                            projectsSuperset.get(referencedProj.getName());
                    if ((referencedProjDesc != null)
                            && referencedProjDesc.equals(projDesc)
                            && !associatedProjects
                                    .contains(candidateProjDesc)) {

                        if (!associatedProjectsOnCurrentLineage
                                .contains(candidateProjDesc)) {
                            associatedProjects.add(candidateProjDesc);
                            associatedProjectsOnCurrentLineage
                                    .add(candidateProjDesc);
                            internalGetReferencingProjectsHierarchy(
                                    associatedProjects,
                                    associatedProjectsOnCurrentLineage,
                                    candidateProjDesc,
                                    projectsSuperset);
                            associatedProjectsOnCurrentLineage
                                    .remove(candidateProjDesc);
                        } else {
                            throw new CyclicDependencyException();
                        }

                    }
                }
            }

        }

    }

    /**
     * Determines all projects referenced, directly or indirectly, by those
     * projects specified. This overloaded version of
     * <code>getReferencedProjectsHierarchies()</code> should be used when the
     * projects considered have not been loaded into a workspace and therefore
     * <code>Project</code> instances are not available to determine the lines
     * of ancestor projects
     * 
     * @param targetProjectDescriptions
     *            eclipse projects from which project hierarchies are sought
     * @param hangingReferencedProjects
     *            collection of referenced projects that are out of scope (i.e.
     *            absent from <code>projectSuperset</code>)
     * @param projectsSuperset
     *            all visible projects defined by their
     *            <code>IProjectDescription</code>s (used for lookup purposes)
     * @return project hierarchies as derived from the specified target projects
     * @throws CyclicDependencyException
     */
    public static Set<IProjectDescription> getReferencedProjectsHierarchies(
            Set<IProjectDescription> targetProjectDescriptions,
            Set<String> hangingReferencedProjects,
            Set<IProjectDescription> projectsSuperset)
            throws CyclicDependencyException {

        Set<IProjectDescription> associatedProjects =
                new HashSet<IProjectDescription>();

        if ((targetProjectDescriptions != null)
                && !targetProjectDescriptions.isEmpty()) {

            Set<IProjectDescription> associatedProjectsOnCurrentLineage =
                    new HashSet<IProjectDescription>();

            Map<String, IProjectDescription> nonAssociatedProjectsMap =
                    new HashMap<String, IProjectDescription>();
            for (IProjectDescription projDesc : projectsSuperset)
                nonAssociatedProjectsMap.put(projDesc.getName(), projDesc);

            for (IProjectDescription projDesc : targetProjectDescriptions) {

                if (projDesc != null) {
                    associatedProjectsOnCurrentLineage.add(projDesc);
                    internalGetReferencedProjectsHierarchy(associatedProjects,
                            hangingReferencedProjects,
                            associatedProjectsOnCurrentLineage,
                            projDesc,
                            nonAssociatedProjectsMap);
                    associatedProjectsOnCurrentLineage.remove(projDesc);
                }

            }
        }

        return associatedProjects;
    }

    /**
     * Determines all projects that are referencing, directly or indirectly, the
     * specified project. This overloaded version of
     * <code>internalGetReferencingProjectsHierarchy()</code> should be used
     * when the project being considered has not been loaded into a workspace
     * and therefore a <code>Project</code> instance is not available to
     * determine lines of ancestor projects
     * 
     * @param associatedProjects
     *            projects that have been found so far to be ancestors of the
     *            specified project
     * @param associatedProjectsOnCurrentLineage
     *            all projects on a current branch
     * @param projDesc
     *            an eclipse project's <code>ProjectDescription</code>
     * @param projectsSuperset
     *            all visible projects defined by their
     *            <code>IProjectDescription</code>s (used for lookup purposes)
     * @throws CyclicDependencyException
     */

    /**
     * Determines all projects that are referenced, directly or indirectly, by
     * the specified project. This overloaded version of
     * <code>internalGetReferencedProjectsHierarchy()</code> should be used when
     * the project being considered has not been loaded into a workspace and
     * therefore a <code>Project</code> instance is not available to determine
     * lines of ancestor projects
     * 
     * @param associatedProjects
     *            projects that have been found so far to be descendants of the
     *            specified project
     * @param hangingReferencedProjects
     *            collection of referenced projects that are out of scope (i.e.
     *            absent from <code>projectSuperset</code>)
     * @param associatedProjectsOnCurrentLineage
     *            all projects on a current branch of investigation
     * @param projDesc
     *            an eclipse project's <code>ProjectDescription</code>
     * @param projectsSuperset
     *            all in-scope projects defined by their
     *            <code>IProjectDescription</code>s (used for lookup purposes)
     * @throws CyclicDependencyException
     */
    private static void internalGetReferencedProjectsHierarchy(
            Set<IProjectDescription> associatedProjects,
            Set<String> hangingReferencedProjects,
            Set<IProjectDescription> associatedProjectsOnCurrentLineage,
            IProjectDescription projDesc,
            Map<String, IProjectDescription> projectsSuperset)
            throws CyclicDependencyException {

        if (projDesc != null) {

            for (IProject projReferenced : projDesc.getReferencedProjects()) {
                IProjectDescription projReferencedDesc =
                        projectsSuperset.get(projReferenced.getName());

                // if possible record name of referenced project that is not in
                // the superset
                if (projReferencedDesc != null) {
                    if (!associatedProjectsOnCurrentLineage
                            .contains(projReferencedDesc)) {

                        if (associatedProjects.add(projReferencedDesc)) {

                            associatedProjectsOnCurrentLineage
                                    .add(projReferencedDesc);
                            ProjectUtil2.internalGetReferencedProjectsHierarchy(
                                    associatedProjects,
                                    hangingReferencedProjects,
                                    associatedProjectsOnCurrentLineage,
                                    projReferencedDesc,
                                    projectsSuperset);
                            associatedProjectsOnCurrentLineage
                                    .remove(projReferencedDesc);
                        }

                    } else {
                        throw new CyclicDependencyException();
                    }
                } else if ((hangingReferencedProjects != null) && !ProjectUtil
                        .isGeneratedProject(projReferenced.getName())) {
                    hangingReferencedProjects.add(projReferenced.getName());
                }
            }
        }

    }

    /*
     * Sid ACE-122: Moved 'hasErrorLevelProblemMarkers from CompositeUtil to
     * this more generic location.
     */

    /**
     * For a given Studio Project tells whether there are error level problem
     * markers on it or on referenced projects.
     * 
     * @param studioProject
     * @return
     * @throws CoreException
     */
    public static boolean hasErrorLevelProblemMarkers(IProject studioProject)
            throws CoreException {
        return hasErrorLevelProblemMarkers(
                Collections.singleton(studioProject));
    }

    /**
     * For a given collection of Studio Projects tells whether there are error
     * level problem markers on it or their referenced projects.
     * 
     * @param studioProjects
     *            collection of projects.
     * @return true if one of the studioProjects or one of their referenced
     *         project has error marker.
     * @throws CoreException
     */
    public static boolean hasErrorLevelProblemMarkers(
            Collection<IProject> studioProjects) throws CoreException {
        Set<IProject> projectsHierarchy = new HashSet<IProject>();
        for (IProject studioProject : studioProjects) {
            Set<IProject> referencedProjectsHierarchy = new HashSet<IProject>();
            ProjectUtil.getReferencedProjectsHierarchy(studioProject,
                    referencedProjectsHierarchy);
            projectsHierarchy.addAll(referencedProjectsHierarchy);
            projectsHierarchy.add(studioProject);
        }
        boolean errorLevelProblemMarker = false;
        for (IProject eachProject : projectsHierarchy) {
            if (!eachProject.isAccessible()) {
                continue;
            }
            IMarker[] markers = eachProject
                    .findMarkers(null, true, IResource.DEPTH_INFINITE);
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
                int severity = marker.getAttribute(IMarker.SEVERITY,
                        IMarker.SEVERITY_WARNING);
                if (severity == IMarker.SEVERITY_ERROR) {
                    errorLevelProblemMarker = true;
                    // no need to check further
                    return errorLevelProblemMarker;
                }
            }
        }
        return errorLevelProblemMarker;
    }

    private static final String simpleDateFormat = "yyyyMMddHHmmssSSS"; //$NON-NLS-1$

    private static final SimpleDateFormat format =
            new SimpleDateFormat(simpleDateFormat);

    /**
     * Sid ACE-122 - Moved from DAANamingUtils
     * 
     * @return Auto-generated (current time) version qualifier.
     * 
     */
    public static String getAutogeneratedQualifier() {
        String formattedDate = format.format(new Date());
        return formattedDate;
    }

}
