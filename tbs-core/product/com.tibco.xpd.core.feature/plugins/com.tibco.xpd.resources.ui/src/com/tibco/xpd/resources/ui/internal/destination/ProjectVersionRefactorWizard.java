/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.destination;

import org.eclipse.core.resources.IProject;
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.tibco.xpd.resources.projectconfig.ProjectDetails;

/**
 * Project details refactor wizard.
 * 
 * @author njpatel
 * 
 */
public class ProjectVersionRefactorWizard extends RefactoringWizard {

    /**
     * Project details refactor wizard.
     * 
     * @param project
     *            project being refactored
     * @param details
     *            new project details.
     */
    public ProjectVersionRefactorWizard(String title, IProject project,
            ProjectDetails details) {
        this(project, details,
                RefactoringWizard.CHECK_INITIAL_CONDITIONS_ON_OPEN
                        | RefactoringWizard.DIALOG_BASED_USER_INTERFACE | RefactoringWizard.PREVIEW_EXPAND_FIRST_NODE);
        setForcePreviewReview(true);
        setDefaultPageTitle(title);
        setHelpAvailable(false);
    }

    /**
     * Project details refactor wizard.
     * 
     * @param project
     *            project being refactored
     * @param details
     *            new project details.
     * @param flags
     *            see
     *            {@link RefactoringWizard#RefactoringWizard(org.eclipse.ltk.core.refactoring.Refactoring, int)
     *            RefactoringWizard}.
     */
    public ProjectVersionRefactorWizard(IProject project,
            ProjectDetails details, int flags) {
        super(new ProcessorBasedRefactoring(
                new ProjectVersionRefactoringProcessor(project, details)),
                flags);
    }

    @Override
    protected void addUserInputPages() {
        // No pages to add
    }

}
