/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.moveprocess.participants;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Change that adds all the projects to be added as the referenced projects to
 * the target project. Does not Support undo/redo.
 * 
 * 
 * @author kthombar
 * @since 14-Sep-2014
 */
public class AddReferencedProjectChange extends Change {

    private Set<IProject> allProjectsToReference;

    private IProject targetProject;

    public AddReferencedProjectChange(Set<IProject> allProjectsToReference,
            IProject targetProject) {
        this.allProjectsToReference = allProjectsToReference;
        this.targetProject = targetProject;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return String
                .format(Messages.AddReferencedProjectChange_AddProjectReferencesChange_desc,
                        targetProject.getName(),
                        projectNamesCommaSeparated());
    }

    /**
     * Returns a string of all the project names which should be referenced.
     * 
     * @return
     */
    private String projectNamesCommaSeparated() {
        String projectNames = ""; //$NON-NLS-1$
        Object[] array = allProjectsToReference.toArray();

        for (int i = 0; i < array.length; i++) {
            IProject proj = (IProject) array[i];
            if (i != array.length - 1) {
                projectNames = projectNames + " '" + proj.getName() + "' ,"; //$NON-NLS-1$//$NON-NLS-2$
            } else {
                projectNames = projectNames + " '" + proj.getName() + "'"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return projectNames;
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
    public RefactoringStatus isValid(IProgressMonitor pm) throws CoreException,
            OperationCanceledException {
        return new RefactoringStatus();
    }

    /**
     * Adds the projects in the list "allReferencedProjects" as referenced
     * projects, and undos the same.
     * 
     * @see org.eclipse.ltk.core.refactoring.Change#perform(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     * @return
     * @throws CoreException
     */
    @Override
    public Change perform(IProgressMonitor pm) throws CoreException {

        pm.beginTask(getName(), 1);

        for (IProject eachProjectToReference : allProjectsToReference) {
            /* add necessary project references */
            ProjectUtil.addReferenceProject(targetProject,
                    eachProjectToReference);
        }

        pm.worked(1);

        /*
         * Return null as we dont want to support undo redo
         */
        return null;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {
        return null;
    }
}
