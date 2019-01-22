/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.refactor;

import org.eclipse.emf.ecore.EObject;
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

import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdUserInputWizardPage;
import com.tibco.xpd.ui.util.NameUtil;

/**
 * A wizard for the rename GSD elements refactoring.
 * 
 * @author sajain
 * @since May 12, 2015
 */
public class RenameGSDElementWizard extends RefactoringWizard {

    /**
     * New name for the GSD element.
     */
    private String newElementName = null;

    /**
     * GSD element to be refactored.
     */
    private EObject element;

    /**
     * Creates a {@link RenameGSDElementWizard}.
     * 
     * @param element
     *            the element to rename. The element must exist.
     */
    public RenameGSDElementWizard(EObject element) {

        super(new RenameRefactoring(new RenameGSDElementProcessor(element)),
                DIALOG_BASED_USER_INTERFACE);

        this.element = element;

        setDefaultPageTitle(Messages.RenameGSDElementWizard_RenameElementPageTitle);

        setWindowTitle(Messages.RenameGSDElementWizard_RenameElementWindowTitle);
    }

    /**
     * Creates a {@link RenameGSDElementWizard}.
     * 
     * @param element
     *            the element to rename. The element must exist.
     */
    public RenameGSDElementWizard(EObject element, String newElementName) {

        super(new RenameRefactoring(new RenameGSDElementProcessor(element)),
                DIALOG_BASED_USER_INTERFACE);

        this.element = element;

        setDefaultPageTitle(Messages.RenameGSDElementWizard_RenameElementPageTitle);

        setWindowTitle(Messages.RenameGSDElementWizard_RenameElementWindowTitle);

        this.newElementName = newElementName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ltk.ui.refactoring.RefactoringWizard#addUserInputPages()
     */
    @Override
    protected void addUserInputPages() {

        RenameGSDElementProcessor processor =
                (RenameGSDElementProcessor) getRefactoring()
                        .getAdapter(RenameGSDElementProcessor.class);

        addPage(new RenameGSDElementRefactoringConfigurationPage(processor,
                newElementName, element));
    }

    /**
     * Wizard page to rename/refactor GSD element.
     * 
     * @author sajain
     * @since May 12, 2015
     */
    private static class RenameGSDElementRefactoringConfigurationPage extends
            AbstractXpdUserInputWizardPage {

        /**
         * Rename GSD element processor instance.
         */
        private final RenameGSDElementProcessor refactoringProcessor;

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
         * Boolean field to indicate whether the payload name and payload label
         * are still in synch.
         */
        boolean isNameLinkedToLabel;

        /**
         * New name of element being refactored.
         */
        private String newElementName = null;

        /**
         * Element to be refactored.
         */
        private EObject element;

        /**
         * Wizard page to rename/refactor GSD element.
         * 
         * @param processor
         * @param newElementName
         * @param element
         */
        public RenameGSDElementRefactoringConfigurationPage(
                RenameGSDElementProcessor processor, String newElementName,
                EObject element) {

            super("RenameElementRefactoringInputPage"); //$NON-NLS-1$

            this.element = element;

            refactoringProcessor = processor;

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

            if (element instanceof PayloadDataField) {

                Label labelOldLabel = new Label(composite, SWT.NONE);
                labelOldLabel.setText(Messages.RenameGSDElementWizard_OldLabel);
                labelOldLabel.setLayoutData(new GridData());

                oldLabelText = new Text(composite, SWT.BORDER | SWT.READ_ONLY);

                oldLabelText.setText(refactoringProcessor
                        .getOldElementDisplayName());

                oldLabelText.setFont(composite.getFont());

                oldLabelText.setLayoutData(new GridData(GridData.FILL,
                        GridData.BEGINNING, true, false));

                Label labelNewLabel = new Label(composite, SWT.NONE);
                labelNewLabel.setText(Messages.RenameGSDElementWizard_NewLabel);
                labelNewLabel.setLayoutData(new GridData());

                newLabelText = new Text(composite, SWT.BORDER);

                newLabelText.setText(refactoringProcessor
                        .getNewElementDisplayName());

                newLabelText.setFont(composite.getFont());

                newLabelText.setLayoutData(new GridData(GridData.FILL,
                        GridData.BEGINNING, true, false));

            }

            Label oldNameLabel = new Label(composite, SWT.NONE);
            oldNameLabel.setText(Messages.RenameGSDElementWizard_OldName);
            oldNameLabel.setLayoutData(new GridData());

            oldNametext = new Text(composite, SWT.BORDER | SWT.READ_ONLY);

            oldNametext.setText(refactoringProcessor.getNewElementName());
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

                newNameText.setText(refactoringProcessor.getNewElementName());

            }

            newNameText.setFont(composite.getFont());

            newNameText.setLayoutData(new GridData(GridData.FILL,
                    GridData.BEGINNING, true, false));

            if (element instanceof PayloadDataField) {

                isNameLinkedToLabel =
                        oldNametext.getText()
                                .equals(NameUtil.getInternalName(oldLabelText
                                        .getText(), true));
            }

            if (element instanceof PayloadDataField) {

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

            } else {

                newNameText.addModifyListener(new ModifyListener() {

                    @Override
                    public void modifyText(ModifyEvent e) {

                        validatePage();
                    }
                });

            }

            if (element instanceof PayloadDataField) {

                newLabelText.selectAll();

            } else {

                newNameText.selectAll();

            }

            validatePage();
            setControl(composite);
        }

        @Override
        public void setVisible(boolean visible) {

            if (visible) {

                if (element instanceof PayloadDataField) {
                    newLabelText.setFocus();
                } else {
                    newNameText.setFocus();
                }
            }

            super.setVisible(visible);
        }

        protected final void validatePage() {

            String text = newNameText.getText();

            RefactoringStatus status = new RefactoringStatus();

            if (element instanceof PayloadDataField) {

                String label = newLabelText.getText();

                status =
                        refactoringProcessor
                                .validateNewElementName(text, label);

            } else {

                status = refactoringProcessor.validateNewElementName(text);

            }

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

            refactoringProcessor.setNewElementName(newNameText.getText());

            if (element instanceof PayloadDataField) {

                refactoringProcessor.setNewDisplayElementName(newLabelText
                        .getText());

            }
        }
    }
}