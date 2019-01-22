/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.process.globalsignal.refactor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.xpd.n2.process.globalsignal.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.internal.refactor.change.AddProjectReferenceChange;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.CyclicDependencyException;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.ProjectUtil2;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Move participant that computes and adds project references after GSD file is
 * moved across projects.
 * 
 * @author kthombar
 * @since Jul 1, 2015
 */
public class AddProjectReferencesOnGsdMoveParticipant extends
        AbstractGsdMoveParticipant {

    /**
     * the target project of move
     */
    private IProject targetProject;

    /**
     * the affected process projects that depend on the GSD project whose GSD
     * file is moved
     */
    private Set<IProject> candidateProcessProjectsToAddReferences =
            new HashSet<IProject>();

    /**
     * the projects that the GSD project require to reference because the GSD
     * file(being moved) has dependency on it
     */
    private Set<IProject> projectsToReference = new HashSet<IProject>();

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {

        return Messages.AddProjectReferencesOnGsdMoveParticipant_AddProjectReferenceParticipant_name;
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#initialize(org.eclipse.core.resources.IResource,
     *      org.eclipse.core.resources.IFolder)
     * 
     * @param sourceMovedResource
     * @param targetGsdSpecialFolder
     * @return
     */
    @Override
    protected boolean initialize(IResource sourceMovedResource,
            IFolder targetGsdSpecialFolder) {

        targetProject = targetGsdSpecialFolder.getProject();

        if (targetProject != null) {
            return targetProject.equals(sourceMovedResource.getProject()) ? false
                    : true;
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext,
     *      org.eclipse.core.resources.IResource,
     *      org.eclipse.core.resources.IFolder, java.util.Map)
     * 
     * @param pm
     * @param context
     * @param sourceMovedResource
     * @param targetGsdSpecialFolder
     * @param gsdFileToReferencingActivityMap
     * @return
     * @throws OperationCanceledException
     */
    @Override
    protected RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context, IResource sourceMovedResource,
            IFolder targetGsdSpecialFolder,
            Map<IFile, List<Activity>> gsdFileToReferencingActivityMap)
            throws OperationCanceledException {

        if (targetProject == null) {
            return RefactoringStatus
                    .createFatalErrorStatus(String
                            .format(Messages.AddProjectReferencesOnGsdMoveParticipant_CouldNotAccessProjectError_msg,
                                    targetGsdSpecialFolder.getName()));
        }

        Collection<List<Activity>> allActivitiesInMap =
                gsdFileToReferencingActivityMap.values();

        /*
         * Get all the prcess project that need to refer the target project
         * after GSD file is moved into it.
         */
        for (List<Activity> activitesReferencingGsds : allActivitiesInMap) {

            for (Activity activity : activitesReferencingGsds) {

                IProject projectFor = WorkingCopyUtil.getProjectFor(activity);

                if (projectFor != null) {

                    candidateProcessProjectsToAddReferences.add(projectFor);

                } else {
                    return RefactoringStatus
                            .createFatalErrorStatus(String
                                    .format(Messages.AddProjectReferencesOnGsdMoveParticipant_CouldNotaccessProjectForActivityError_msg,
                                            Xpdl2ModelUtil
                                                    .getDisplayNameOrName(activity)));
                }
            }
        }

        /*
         * Check if referencing new GSD target project from source Process
         * PRoject does not cause cyclic dependency.
         */
        for (IProject projectToAddReferenceIn : candidateProcessProjectsToAddReferences) {

            if (!projectToAddReferenceIn.equals(targetProject)) {

                if (willCauseCyclicReference(projectToAddReferenceIn,
                        targetProject)) {

                    return RefactoringStatus
                            .createFatalErrorStatus(String
                                    .format(Messages.AddProjectReferencesOnGsdMoveParticipant_CyclicProjectReferenceError_msg,
                                            projectToAddReferenceIn.getName(),
                                            targetProject.getName()));
                }
            }
        }

        /*
         * Get all the projects that the target project needs to references
         * after GSD is moved into it.
         */
        Set<IFile> affectedGsdFiles = gsdFileToReferencingActivityMap.keySet();

        for (IFile gsdFile : affectedGsdFiles) {
            Set<IResource> deepDependencies =
                    WorkingCopyUtil.getDeepDependencies(gsdFile);

            for (IResource iResource : deepDependencies) {
                IProject projectToReference = iResource.getProject();

                if (!targetProject.equals(projectToReference)) {

                    projectsToReference.add(projectToReference);
                }
            }
        }

        /*
         * check for cyclic references.
         */
        for (IProject projToReference : projectsToReference) {

            if (!projToReference.equals(targetProject)) {

                if (willCauseCyclicReference(targetProject, projToReference)) {

                    return RefactoringStatus
                            .createFatalErrorStatus(String
                                    .format(Messages.AddProjectReferencesOnGsdMoveParticipant_CyclicProjectReferenceError_msg,
                                            targetProject.getName(),
                                            projToReference.getName()));
                }
            }
        }

        return new RefactoringStatus();
    }

    /**
     * Check whether by setting the given referenced project we will end up with
     * a cyclic reference.
     * 
     * @param project
     * @param referencedProject
     * @return
     */
    private boolean willCauseCyclicReference(IProject project,
            IProject referencedProject) {

        try {
            Set<IProject> refProjects =
                    ProjectUtil2
                            .getReferencedProjectsHierarchy(referencedProject,
                                    true);
            for (IProject ref : refProjects) {
                if (ref.equals(project)) {
                    return true;
                }
            }
        } catch (CyclicDependencyException e) {
            // Do nothing - there should already be validation markers showing
            // this problem
        }
        return false;
    }

    /**
     * @see com.tibco.xpd.n2.process.globalsignal.refactor.AbstractGsdMoveParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.resources.IResource,
     *      org.eclipse.core.resources.IFolder, java.util.Map)
     * 
     * @param pm
     * @param sourceMovedResource
     * @param targetGsdSpecialFolder
     * @param gsdFileToReferencingActivityMap
     * @return
     */
    @Override
    protected Change createChange(IProgressMonitor pm,
            IResource sourceMovedResource, IFolder targetGsdSpecialFolder,
            Map<IFile, List<Activity>> gsdFileToReferencingActivityMap) {

        List<AddProjectReferenceChange> projectReferenceChanges =
                new ArrayList<AddProjectReferenceChange>();

        try {
            /*
             * add all changes that need to reference the target project
             */
            for (IProject project : candidateProcessProjectsToAddReferences) {
                if (!ProjectUtil.isProjectReferenced(project, targetProject)) {
                    projectReferenceChanges.add(new AddProjectReferenceChange(
                            project, targetProject));
                }
            }
            /*
             * add all changes where-in target project needs to reference other
             * projects.
             */
            for (IProject projToReference : projectsToReference) {
                if (!ProjectUtil.isProjectReferenced(targetProject,
                        projToReference)) {
                    projectReferenceChanges.add(new AddProjectReferenceChange(
                            targetProject, projToReference));
                }
            }
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        /*
         * return the change.
         */
        if (!projectReferenceChanges.isEmpty()) {
            return new CompositeChange(
                    String.format(Messages.AddProjectReferencesOnGsdMoveParticipant_AddProjectReferenceChange_name,
                            sourceMovedResource.getName()),
                    projectReferenceChanges
                            .toArray(new Change[projectReferenceChanges.size()]));
        }

        return null;
    }
}
