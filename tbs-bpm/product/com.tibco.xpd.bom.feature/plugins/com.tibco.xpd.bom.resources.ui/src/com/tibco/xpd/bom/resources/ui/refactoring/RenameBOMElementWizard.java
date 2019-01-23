/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.resources.ui.refactoring;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.ltk.internal.ui.refactoring.RefactoringUIMessages;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.NamedElement;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdUserInputWizardPage;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * A wizard for the rename bom elements refactoring.
 * 
 * @author mtorres
 * 
 */
public class RenameBOMElementWizard extends RefactoringWizard {

    private String newElementName = null;

    /**
     * Creates a {@link RenameBOMElementWizard}.
     * 
     * @param element
     *            the element to rename. The element must exist.
     */
    public RenameBOMElementWizard(NamedElement element) {
        super(new RenameRefactoring(new RenameBOMElementProcessor(element)),
                DIALOG_BASED_USER_INTERFACE);
        setDefaultPageTitle(Messages.RenameBOMElementWizard_RenameElementPageTitle);
        setWindowTitle(Messages.RenameBOMElementWizard_RenameElementWindowTitle);
    }

    /**
     * Creates a {@link RenameBOMElementWizard}.
     * 
     * @param element
     *            the element to rename. The element must exist.
     */
    public RenameBOMElementWizard(NamedElement element, String newElementName) {
        super(new RenameRefactoring(new RenameBOMElementProcessor(element)),
                DIALOG_BASED_USER_INTERFACE);
        setDefaultPageTitle(Messages.RenameBOMElementWizard_RenameElementPageTitle);
        setWindowTitle(Messages.RenameBOMElementWizard_RenameElementWindowTitle);
        this.newElementName = newElementName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
     */
    @Override
    protected void addUserInputPages() {
        RenameBOMElementProcessor processor =
                (RenameBOMElementProcessor) getRefactoring()
                        .getAdapter(RenameBOMElementProcessor.class);
        addPage(new RenameBOMElementRefactoringConfigurationPage(processor,
                newElementName));
    }

    private static class RenameBOMElementRefactoringConfigurationPage extends
            AbstractXpdUserInputWizardPage {

        /**
         * Rename BOM element processor instance.
         */
        private final RenameBOMElementProcessor fRefactoringProcessor;

        /**
         * Old name field.
         */
        private Text oldNametext;

        /**
         * New name field.
         */
        private Text newNameText;

        /**
         * Old label field.
         */
        private Text oldLabelText;

        /**
         * New label field.
         */
        private Text newLabelText;

        /**
         * Boolean field to indicate whether the name and label are still
         * linked.
         */
        boolean isNameLinkedToLabel;

        private String newElementName = null;

        public RenameBOMElementRefactoringConfigurationPage(
                RenameBOMElementProcessor processor, String newElementName) {
            super("RenameElementRefactoringInputPage"); //$NON-NLS-1$
            fRefactoringProcessor = processor;
            this.newElementName = newElementName;
            this.isNameLinkedToLabel = false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt
         * .widgets.Composite)
         */
        @Override
        public void createControl(Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));
            composite
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
            composite.setFont(parent.getFont());

            Label labelOldLabel = new Label(composite, SWT.NONE);
            labelOldLabel.setText(Messages.RenameBOMElementWizard_OldLabel);
            labelOldLabel.setLayoutData(new GridData());

            oldLabelText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
            oldLabelText.setText(fRefactoringProcessor
                    .getNewElementDisplayName());
            oldLabelText.setFont(composite.getFont());
            oldLabelText.setLayoutData(new GridData(GridData.FILL,
                    GridData.BEGINNING, true, false));

            Label labelNewLabel = new Label(composite, SWT.NONE);
            labelNewLabel.setText(Messages.RenameBOMElementWizard_NewLabel);
            labelNewLabel.setLayoutData(new GridData());

            newLabelText = new Text(composite, SWT.BORDER);

            newLabelText.setText(fRefactoringProcessor
                    .getNewElementDisplayName());

            newLabelText.setFont(composite.getFont());

            newLabelText.setLayoutData(new GridData(GridData.FILL,
                    GridData.BEGINNING, true, false));

            Label oldNameLabel = new Label(composite, SWT.NONE);
            oldNameLabel.setText(Messages.RenameBOMElementWizard_OldName);
            oldNameLabel.setLayoutData(new GridData());

            oldNametext = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
            oldNametext.setText(fRefactoringProcessor.getNewElementName());
            oldNametext.setFont(composite.getFont());
            oldNametext.setLayoutData(new GridData(GridData.FILL,
                    GridData.BEGINNING, true, false));

            Label labelName = new Label(composite, SWT.NONE);
            labelName
                    .setText(RefactoringUIMessages.RenameResourceWizard_name_field_label);
            labelName.setLayoutData(new GridData());

            newNameText = new Text(composite, SWT.BORDER);
            if (newElementName != null) {
                newNameText.setText(newElementName);
            } else {
                newNameText.setText(fRefactoringProcessor.getNewElementName());
            }
            newNameText.setFont(composite.getFont());
            newNameText.setLayoutData(new GridData(GridData.FILL,
                    GridData.BEGINNING, true, false));

            isNameLinkedToLabel =
                    oldNametext.getText()
                            .equals(NameUtil.getInternalName(oldLabelText
                                    .getText(), true));

            newLabelText.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {

                    if (isNameLinkedToLabel) {

                        newNameText.setText(newLabelText.getText());

                    }

                    validatePage();

                }
            });

            newNameText.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {

                    validatePage();
                }
            });

            newLabelText.selectAll();
            validatePage();
            setControl(composite);
        }

        @Override
        public void setVisible(boolean visible) {
            if (visible) {
                newLabelText.setFocus();
            }
            super.setVisible(visible);
        }

        protected final void validatePage() {
            String text = newNameText.getText();
            String label = newLabelText.getText();

            RefactoringStatus status =
                    fRefactoringProcessor.validateNewElementName(text, label);
            setPageComplete(status);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.ltk.ui.refactoring.UserInputWizardPage#performFinish()
         */
        @Override
        protected boolean performFinish() {
            initializeRefactoring();
            storeSettings();
            return super.performFinish();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ltk.ui.refactoring.UserInputWizardPage#getNextPage()
         */
        @Override
        public IWizardPage getNextPage() {
            initializeRefactoring();
            storeSettings();
            return super.getNextPage();
        }

        private void storeSettings() {
        }

        private void initializeRefactoring() {
            fRefactoringProcessor.setNewElementName(newNameText.getText());

            fRefactoringProcessor.setNewElementDisplayName(newLabelText
                    .getText());
        }
    }
}