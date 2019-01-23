/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringChangeDescriptor;
import org.eclipse.ltk.core.refactoring.participants.DeleteRefactoring;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange;
import org.eclipse.ltk.internal.core.refactoring.RefactoringCoreMessages;
import org.eclipse.ltk.internal.core.refactoring.resource.DeleteResourcesProcessor;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The refactoring delete project wizard that disallows the undo of project
 * deletes. (Replicating what
 * <code>org.eclipse.ltk.ui.refactoring.resource.DeleteResourceWizard</code>
 * does, but customised for RCP requirements).
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
public class RCPDeleteProjectWizard extends RefactoringWizard {

    private final IProject[] projects;

    /**
     * @param refactoring
     * @param flags
     */
    public RCPDeleteProjectWizard(IProject[] projects) {
        super(new DeleteRefactoring(getProcessor(projects)),
                DIALOG_BASED_USER_INTERFACE);
        this.projects = projects;

        setWindowTitle("Delete Project");
        setDefaultPageTitle("Delete Project");
    }

    private static DeleteResourcesProcessor getProcessor(
            final IProject[] projects) {
        return new DeleteResourcesProcessor(projects, true) {
            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor
             * #createChange(org.eclipse.core.runtime.IProgressMonitor)
             */
            @Override
            public Change createChange(IProgressMonitor pm)
                    throws CoreException, OperationCanceledException {
                pm.beginTask(RefactoringCoreMessages.DeleteResourcesProcessor_create_task,
                        projects.length);
                try {
                    RefactoringChangeDescriptor descriptor =
                            new RefactoringChangeDescriptor(createDescriptor());
                    CompositeChange change =
                            new CompositeChange(
                                    RefactoringCoreMessages.DeleteResourcesProcessor_change_name);
                    change.markAsSynthetic();
                    for (int i = 0; i < projects.length; i++) {
                        pm.worked(1);
                        DeleteResourceChange dc =
                                new DeleteResourceChange(
                                        projects[i].getFullPath(), true,
                                        isDeleteContents()) {
                                    /**
                                     * @see org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange#perform(org.eclipse.core.runtime.IProgressMonitor)
                                     * 
                                     * @param pm
                                     * @return
                                     * @throws CoreException
                                     */
                                    @Override
                                    public Change perform(IProgressMonitor pm)
                                            throws CoreException {
                                        super.perform(pm);

                                        /*
                                         * Don't allow the undo of project
                                         * deletes.
                                         */
                                        return new NullChange();
                                    }
                                };
                        dc.setDescriptor(descriptor);
                        change.add(dc);
                    }
                    return change;
                } finally {
                    pm.done();
                }
            }
        };
    }

    /**
     * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
     * 
     */
    @Override
    protected void addUserInputPages() {
        DeleteProjectWizardPage page = new DeleteProjectWizardPage();
        addPage(page);
    }

    /**
     * Wizard page for the delete project wizard.
     * 
     */
    private class DeleteProjectWizardPage extends UserInputWizardPage {

        /**
         * @param name
         */
        public DeleteProjectWizardPage() {
            super("delete-project"); //$NON-NLS-1$
        }

        /**
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         * 
         * @param parent
         */
        @Override
        public void createControl(Composite parent) {
            initializeDialogUnits(parent);

            Point defaultSpacing = LayoutConstants.getSpacing();

            Composite root = new Composite(parent, SWT.NONE);
            GridLayout gridLayout = new GridLayout(2, false);
            gridLayout.horizontalSpacing = defaultSpacing.x * 2;
            gridLayout.verticalSpacing = defaultSpacing.y;
            root.setLayout(gridLayout);

            Image image = parent.getDisplay().getSystemImage(SWT.ICON_QUESTION);
            Label imageLabel = new Label(root, SWT.NULL);
            imageLabel.setBackground(image.getBackground());
            imageLabel.setImage(image);
            imageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING,
                    false, false));

            Label lbl = new Label(root, SWT.WRAP);
            if (projects.length == 1) {
                lbl.setText(String
                        .format("Are you sure you want to delete project '%s'?",
                                projects[0].getName()));
            } else {
                lbl.setText(String
                        .format("Are you sure you want to delete these %d projects?",
                                projects.length));
            }
            GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
            gridData.widthHint = 125;
            lbl.setLayoutData(gridData);

            Label noteLbl = new Label(root, SWT.WRAP);
            noteLbl.setText("NOTE: Project deletion cannot be undone.");
            GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
            data.horizontalSpan = 2;
            data.verticalIndent = 15;
            noteLbl.setLayoutData(data);

            setControl(root);
        }
    }

}
