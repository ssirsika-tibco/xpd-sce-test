/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.resources.refactoring;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.internal.Messages;
import com.tibco.xpd.resources.util.XpdConsts;

/**
 * Rename participant that update all project references when a referenced
 * project is renamed.
 * 
 * @since 3.5.10
 */
public class ProjectRenameParticipant extends RenameParticipant {
    /** The project to be renamed. */
    private IProject project;

    private List<IProject> referencingProjects;

    /**
     * Maintains project referential integrity by cascading a project name
     * change to a referencing project.
     */
    private final class ProjectReferenceChange extends Change {
        /** The referencing project. */
        private final IProject referencingProject;

        /** The old name of the referenced project. */
        private final String oldName;

        /** The new name of the referenced project. */
        private final String newName;

        /**
         * Constructs a new ProjectReferenceChange.
         * 
         * @param referencingProject
         *            The referencing project to which the rename is to be
         *            cascaded.
         * @param oldName
         *            The old name of the referenced project.
         * @param newName
         *            The new name of the referenced project.
         */
        private ProjectReferenceChange(IProject referencingProject,
                String oldName, String newName) {
            this.referencingProject = referencingProject;
            this.oldName = oldName;
            this.newName = newName;
        }

        @Override
        public Object getModifiedElement() {
            return referencingProject;
        }

        @Override
        public String getName() {
            return String.format(Messages.ProjectRenameParticipant_refactor_shortDesc,
                    referencingProject.getName());
        }

        @Override
        public void initializeValidationData(IProgressMonitor pm) {
        }

        @Override
        public RefactoringStatus isValid(IProgressMonitor pm)
                throws CoreException {
            RefactoringStatus status;
            if (!referencingProject.exists()) {
                status =
                        RefactoringStatus.createErrorStatus(String
                                .format(Messages.ProjectRenameParticipant_projectNotFound_error_shortdesc,
                                        referencingProject.getName()));
            } else if (!referencingProject.isOpen()) {
                status =
                        RefactoringStatus.createErrorStatus(String
                                .format(Messages.ProjectRenameParticipant_projectClosed_error_shortdesc,
                                        referencingProject.getName()));
            } else {
                status = new RefactoringStatus();
            }
            return status;
        }

        @Override
        public Change perform(IProgressMonitor pm) throws CoreException {
            IProject[] referencedProjects =
                    referencingProject.getReferencedProjects();
            for (int i = 0; i < referencedProjects.length; i++) {
                IProject referencedProject = referencedProjects[i];
                if (referencedProject.getName().equals(oldName)) {
                    referencedProject =
                            ResourcesPlugin.getWorkspace().getRoot()
                                    .getProject(newName);
                    referencedProjects[i] = referencedProject;
                    break;
                }
            }
            IProjectDescription description =
                    referencingProject.getDescription();
            description.setReferencedProjects(referencedProjects);
            referencingProject.setDescription(description, null);
            return new ProjectReferenceChange(referencingProject, newName,
                    oldName);
        }
    }

    @Override
    public RefactoringStatus checkConditions(IProgressMonitor pm,
            CheckConditionsContext context) {
        return null;
    }

    @Override
    public Change createChange(IProgressMonitor pm) throws CoreException {
        String oldName = project.getName();
        String newName = getArguments().getNewName();
        CompositeChange cc = new CompositeChange(Messages.ProjectRenameParticipant_updateReferences_shortdesc);
        for (IProject referencingProject : this.referencingProjects) {
            cc.add(new ProjectReferenceChange(referencingProject, oldName,
                    newName));
        }
        return cc;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected boolean initialize(Object element) {
        this.referencingProjects = new ArrayList<IProject>();

        if (element instanceof IProject) {
            trace("initialize(%s)", element); //$NON-NLS-1$
            project = (IProject) element;

            IProject[] refProjects = project.getReferencingProjects();

            if (refProjects.length > 0) {
                /*
                 * Check if any of the referencing projects is an XPD nature
                 * project
                 */
                for (IProject ref : refProjects) {
                    try {
                        if (ref.hasNature(XpdConsts.PROJECT_NATURE_ID)) {
                            this.referencingProjects.add(ref);
                        }
                    } catch (CoreException e) {
                        XpdResourcesPlugin
                                .getDefault()
                                .getLogger()
                                .error(e,
                                        String.format("There is a problem checking for nature on project '%1$s' during the rename of project '%2$s.", //$NON-NLS-1$
                                                ref.getName(),
                                                project.getName()));
                    }
                }

                if (!referencingProjects.isEmpty()) {
                    // There are projects that need their references to be
                    // updated
                    return true;
                }
            }
        }
        return false;
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
