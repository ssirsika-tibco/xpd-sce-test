/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor.change;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;

/**
 * Change to update the project reference.
 * 
 * @author njpatel
 * 
 */
public class AddProjectReferenceChange extends Change {

    private final IProject project;

    private final IProject refProject;

    /**
         * 
         */
    public AddProjectReferenceChange(IProject project, IProject refProject) {
        this.project = project;
        this.refProject = refProject;
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getName()
     * 
     * @return
     */
    @Override
    public String getName() {
        return String
                .format(Messages.AddProjectReferenceChange_addProjectReference_progress_shortdesc,
                        project.getName(),
                        refProject.getName());
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#initializeValidationData(org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param pm
     */
    @Override
    public void initializeValidationData(IProgressMonitor pm) {
        if (pm != null) {
            pm.done();
        }
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
        if (pm != null) {
            pm.done();
        }
        return new RefactoringStatus();
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

        ProjectUtil.addReferenceProject(project, refProject);

        if (pm != null) {
            pm.done();
        }

        // Shouldn't undo this
        return new NullChange();
    }

    /**
     * @see org.eclipse.ltk.core.refactoring.Change#getModifiedElement()
     * 
     * @return
     */
    @Override
    public Object getModifiedElement() {
        return project;
    }

}