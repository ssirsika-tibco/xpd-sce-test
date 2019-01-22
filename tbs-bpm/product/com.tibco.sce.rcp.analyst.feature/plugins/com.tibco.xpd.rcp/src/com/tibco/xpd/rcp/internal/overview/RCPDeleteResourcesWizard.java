/*
 * Copyright (c) TIBCO Software Inc 2004, 2012. All rights reserved.
 */

package com.tibco.xpd.rcp.internal.overview;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.internal.core.refactoring.Resources;
import org.eclipse.ltk.internal.core.refactoring.resource.DeleteResourcesProcessor;
import org.eclipse.ltk.internal.ui.refactoring.BasicElementLabels;
import org.eclipse.ltk.internal.ui.refactoring.Messages;
import org.eclipse.ltk.internal.ui.refactoring.RefactoringUIMessages;
import org.eclipse.ltk.ui.refactoring.UserInputWizardPage;
import org.eclipse.ltk.ui.refactoring.resource.DeleteResourcesWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.tibco.xpd.rcp.internal.resources.ExtFolderResource;
import com.tibco.xpd.rcp.internal.resources.IRCPResource;
import com.tibco.xpd.rcp.internal.resources.MAAResource;
import com.tibco.xpd.rcp.internal.resources.RCPResourceManager;

/**
 * Extension of the LTK {@link DeleteResourcesWizard} to allow us to control the
 * enablement of the "Delete content...." check-box when deleting projects. When
 * deleting projects from an MAA file the user cannot be given the choice to
 * delete the contents as it will always have to be deleted.
 * 
 * @author njpatel
 */
@SuppressWarnings("restriction")
/* public */class RCPDeleteResourcesWizard extends DeleteResourcesWizard {

    /**
     * @param resources
     */
    public RCPDeleteResourcesWizard(IResource[] resources) {
        super(resources);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
     */
    @Override
    protected void addUserInputPages() {
        DeleteResourcesProcessor processor =
                (DeleteResourcesProcessor) getRefactoring()
                        .getAdapter(DeleteResourcesProcessor.class);
        addPage(new DeleteResourcesRefactoringConfigurationPage(processor,
                RCPResourceManager.getResource()));
    }

    /**
     * Main wizard page for the delete resource wizard.
     */
    private static class DeleteResourcesRefactoringConfigurationPage extends
            UserInputWizardPage {

        private DeleteResourcesProcessor fRefactoringProcessor;

        private Button fDeleteContentsButton;

        private final IRCPResource resource;

        public DeleteResourcesRefactoringConfigurationPage(
                DeleteResourcesProcessor processor, IRCPResource resource) {
            super("DeleteResourcesRefactoringConfigurationPage"); //$NON-NLS-1$
            fRefactoringProcessor = processor;
            this.resource = resource;
        }

        @Override
        public void createControl(Composite parent) {
            initializeDialogUnits(parent);

            Point defaultSpacing = LayoutConstants.getSpacing();

            Composite composite = new Composite(parent, SWT.NONE);
            GridLayout gridLayout = new GridLayout(2, false);
            gridLayout.horizontalSpacing = defaultSpacing.x * 2;
            gridLayout.verticalSpacing = defaultSpacing.y;

            composite.setLayout(gridLayout);
            composite
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            composite.setFont(parent.getFont());

            Image image = parent.getDisplay().getSystemImage(SWT.ICON_QUESTION);
            Label imageLabel = new Label(composite, SWT.NULL);
            imageLabel.setBackground(image.getBackground());
            imageLabel.setImage(image);
            imageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING,
                    false, false));

            IResource[] resources =
                    fRefactoringProcessor.getResourcesToDelete();
            Label label = new Label(composite, SWT.WRAP);
            label.setFont(composite.getFont());

            if (containsLinkedResource(resources)) {
                if (resources.length == 1) {
                    label.setText(Messages
                            .format(RefactoringUIMessages.DeleteResourcesWizard_label_single_linked,
                                    BasicElementLabels
                                            .getResourceName(resources[0])));
                } else {
                    label.setText(Messages
                            .format(RefactoringUIMessages.DeleteResourcesWizard_label_multi_linked,
                                    new Integer(resources.length)));
                }
            } else {
                if (resources.length == 1) {
                    label.setText(Messages
                            .format(RefactoringUIMessages.DeleteResourcesWizard_label_single,
                                    BasicElementLabels
                                            .getResourceName(resources[0])));
                } else {
                    label.setText(Messages
                            .format(RefactoringUIMessages.DeleteResourcesWizard_label_multi,
                                    new Integer(resources.length)));
                }
            }
            GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
            gridData.widthHint =
                    convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
            label.setLayoutData(gridData);

            Composite supportArea = new Composite(composite, SWT.NONE);
            supportArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                    true, 2, 1));
            gridLayout = new GridLayout(1, false);
            gridLayout.horizontalSpacing = defaultSpacing.x * 2;
            gridLayout.verticalSpacing = defaultSpacing.y;

            supportArea.setLayout(gridLayout);

            /*
             * In the RCP only show this option if editing projects from a given
             * folder location
             */
            if (Resources.containsOnlyProjects(resources)
                    && resource instanceof ExtFolderResource) {
                fDeleteContentsButton = new Button(supportArea, SWT.CHECK);
                fDeleteContentsButton.setFont(composite.getFont());
                fDeleteContentsButton.setLayoutData(new GridData(SWT.FILL,
                        SWT.CENTER, true, false, 1, 1));
                fDeleteContentsButton
                        .setText(RefactoringUIMessages.DeleteResourcesWizard_project_deleteContents);
                fDeleteContentsButton.setFocus();
            }
            setControl(composite);
        }

        private boolean containsLinkedResource(IResource[] resources) {
            for (int i = 0; i < resources.length; i++) {
                IResource resource = resources[i];
                if (resource != null && resource.isLinked()) { // paranoia code,
                                                               // can not be
                                                               // null
                    return true;
                }
            }
            return false;
        }

        @Override
        protected boolean performFinish() {
            initializeRefactoring();
            storeSettings();
            return super.performFinish();
        }

        @Override
        public IWizardPage getNextPage() {
            initializeRefactoring();
            storeSettings();
            return super.getNextPage();
        }

        private void initializeRefactoring() {
            // Don't delete content if MAA resource (so the user has option to
            // undo), otherwise check the delete
            // content checkbox
            if (resource instanceof MAAResource) {
                fRefactoringProcessor.setDeleteContents(false);
            } else {
                fRefactoringProcessor
                        .setDeleteContents(fDeleteContentsButton == null ? false
                                : fDeleteContentsButton.getSelection());
            }
        }

        private void storeSettings() {
        }
    }

}
