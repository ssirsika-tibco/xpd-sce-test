/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import org.eclipse.emf.common.util.URI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.registry.ui.internal.Messages;

/**
 * Wizard to import a single WSDL from a user-selected URL
 */
public class WsdlUrlImportWizard extends WsdlImportBaseWizard {
    public WsdlUrlImportWizard() {
        super(new WsdlSelectionPage(), Messages.WsdlUrlImportWizard_title);
    }

    /**
     * Wizard page to handle the selection of the URL
     */
    static class WsdlSelectionPage extends WsdlSourceBaseWizardPage {
        private Text urlText;

        /** Listener for text modification events. */
        private final ModifyListener modifyListener;

        private boolean initialised;

        protected WsdlSelectionPage() {
            super(Messages.WsdlUrlImportWizard_Selection_title);
            setDescription(Messages.WsdlUrlImportWizard_Selection_longdesc);
            modifyListener = new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    updateTarget(getWsdlImportMappings());
                    setPageComplete(validatePage(false, true));
                }
            };
        }

        public URI getSourceUri() {
            return URI.createURI(urlText.getText().trim());
        }

        /**
         * @param parent
         *            The parent to add the controls to.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent) {
            Composite control = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout(2, false);
            control.setLayout(layout);

            Label urlLabel = new Label(control, SWT.NONE);
            urlLabel.setText(Messages.WsdlUrlImportWizard_Url_label);
            urlText = new Text(control, SWT.BORDER | SWT.FLAT);
            urlText
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

            urlText.addModifyListener(modifyListener);
            setControl(control);
            initialised = true;
            setPageComplete(validatePage(true, false));
        }

        /**
         * 
         * @param setFocus
         *            true means give focus to the control which contains the
         *            invalid value
         * @param showMessage
         *            true means do change the message/error-message
         * @return true if the next button should be enabled
         */
        private boolean validatePage(boolean setFocus, boolean showMessage) {
            if (!initialised) {
                return false;
            }
            setMessage(null);
            setErrorMessage(null);

            if (urlText.getText().trim().length() == 0) {
                if (showMessage) {
                    setErrorMessage(Messages.WsdlUrlImportWizard_EmptyUrl_longdesc);
                }
                if (setFocus) {
                    urlText.setFocus();
                }
                return false;

            }
            String validateErrMsg = validateURI(urlText.getText().trim());
            if (validateErrMsg != null) {
                if (showMessage) {
                    setErrorMessage(validateErrMsg);
                }
                if (setFocus) {
                    urlText.setFocus();
                }
                return false;
            }
            return true;
        }

        @Override
        WsdlImportMapping[] getWsdlImportMappings() {
            final String fileNameGuess =
                    guessImportedFileNameFromURI(urlText.getText().trim());
            if (fileNameGuess.length() == 0) {
                return new WsdlImportMapping[] {};
            }
            return new WsdlImportMapping[] { new WsdlImportMapping(urlText
                    .getText().trim(), fileNameGuess) };
        }
    }
}
