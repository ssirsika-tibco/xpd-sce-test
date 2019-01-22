/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.resources.refactoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;

/**
 * Updates all referencing projects by removing dependency on deleted project.
 * 
 * @author rsawant
 * @since 22-Apr-2013
 */
public class ProjectDeleteParticipant extends DeleteParticipant {

    /** The project to be re-factored. */
    private IProject project;

    private IProject[] referecingProjects;

    /**
     * Removes stale dependencies
     * 
     * 
     * @author rsawant
     * @since 30-Apr-2013
     */
    private class ProjectDependencyChange extends Change {

        /** The referencing project. */
        protected final IProject referencingProject;

        /**
         * Constructs a new ProjectDependencyChange
         * 
         * @param referencingProject
         *            a referencing project
         * 
         */
        protected ProjectDependencyChange(IProject referencingProject) {

            this.referencingProject = referencingProject;

        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getName()
         * 
         * @return
         */
        @Override
        public String getName() {
            return String
                    .format(Messages.ProjectDeleteParticipant_Remove_Dependency_From_Project,
                            referencingProject.getName());
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         */
        @Override
        public void initializeValidationData(IProgressMonitor pm) {
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#isValid(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         * @throws OperationCanceledException
         */
        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException, OperationCanceledException {
            RefactoringStatus status;
            if (!referencingProject.exists()) {
                status =
                        RefactoringStatus
                                .createErrorStatus(String
                                        .format(Messages.ProjectRenameParticipant_projectNotFound_error_shortdesc,
                                                referencingProject.getName()));
            } else if (!referencingProject.isOpen()) {
                status =
                        RefactoringStatus
                                .createErrorStatus(String
                                        .format(Messages.ProjectRenameParticipant_projectClosed_error_shortdesc,
                                                referencingProject.getName()));
            } else {
                status = new RefactoringStatus();
            }
            return status;
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         */
        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {
            if (!referencingProject.isAccessible()) {
                return new NullChange();
            }

            List<IProject> newRefList = new ArrayList<IProject>();
            try {
                IProjectDescription description =
                        referencingProject.getDescription();
                IProject[] projectRefs = description.getReferencedProjects();
                for (IProject tempProject : projectRefs) {
                    if (!tempProject.equals(project)) {
                        newRefList.add(tempProject);
                    }
                }
                // Update the project description
                description.setReferencedProjects(newRefList
                        .toArray(new IProject[newRefList.size()]));
                referencingProject.setDescription(description,
                        new NullProgressMonitor());
            } catch (CoreException e) {
                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("There is a problem accessing description for '%1$s' while removing reference of project '%2$s.", //$NON-NLS-1$
                                        referencingProject.getName(),
                                        project.getName()));
            }
            return new ProjectDependencyUndoChange(referencingProject);
        }

        /**
         * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
         * 
         * @return
         */
        @Override
        public Object getModifiedElement() {
            return referencingProject;
        }

    }

    /***
     * SCF-227: adds the dependency back when undo operation is performed
     * 
     * 
     * @author bharge
     * @since 13 Aug 2013
     */

    private class ProjectDependencyUndoChange extends ProjectDependencyChange {

        /**
         * @param referencingProject
         */
        protected ProjectDependencyUndoChange(IProject referencingProject) {

            super(referencingProject);
        }

        /**
         * @see com.tibco.xpd.resources.refactoring.ProjectDeleteParticipant.ProjectDependencyChange#perform(org.eclipse.core.runtime.IProgressMonitor)
         * 
         * @param pm
         * @return
         * @throws CoreException
         */
        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {

            try {

                IProjectDescription description =
                        referencingProject.getDescription();
                IProject[] projectRefs = description.getReferencedProjects();
                List<IProject> projRefList =
                        new ArrayList<IProject>(Arrays.asList(projectRefs));
                projRefList.add(project);
                // Update the project description
                description.setReferencedProjects(projRefList
                        .toArray(new IProject[projRefList.size()]));
                referencingProject.setDescription(description,
                        new NullProgressMonitor());

            } catch (CoreException e) {

                XpdResourcesPlugin
                        .getDefault()
                        .getLogger()
                        .error(e,
                                String.format("There is a problem accessing description for '%1$s' while removing reference of project '%2$s.", //$NON-NLS-1$
                                        referencingProject.getName(),
                                        project.getName()));
            }
            return new ProjectDependencyChange(referencingProject);
        }

    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#initialize(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean initialize(Object element) {
        if (element instanceof IProject) {
            trace("initialize(%s)", element); //$NON-NLS-1$
            project = (IProject) element;
            referecingProjects = project.getReferencingProjects();
            if (referecingProjects.length > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return Messages.ProjectDeleteParticipant_RefactorDeletedProject_label;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#checkConditions(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext)
     * 
     * @param pm
     * @param context
     * @return
     * @throws OperationCanceledException
     */
    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) throws OperationCanceledException {
        return RefactoringStatus.create(Status.OK_STATUS);
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant#createChange(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     * @throws OperationCanceledException
     */
    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        CompositeChange cc =
                new CompositeChange(
                        Messages.ProjectDeleteParticipant_Dependency_Update);

        for (IProject refpr : referecingProjects) {
            cc.add(new ProjectDependencyChange(refpr));
        }
        return cc;
    }

    /**
     * Traces a method invocation.
     * 
     * @param msg
     *            Trace message.
     * @param args
     *            Message arguments.
     */
    protected void trace(String msg, Object... args) {
        if (XpdResourcesPlugin.getDefault().getLogger().isDebugEnabled()) {
            if (args.length > 0) {
                msg = String.format(msg, args);
            }

            XpdResourcesPlugin
                    .getDefault()
                    .getLogger()
                    .debug(String.format("%s: %s", getClass().getCanonicalName(), msg)); //$NON-NLS-1$
        }
    }

}
