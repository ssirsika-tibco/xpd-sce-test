/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.registry.ui.selector.RegistryServiceSelector;

/**
 * A wizard to import multiple WSDLs from UDDI
 */
public class WsdlRegistryImportWizard extends WsdlImportBaseWizard {
    public WsdlRegistryImportWizard() {
        super(new WsdlSelectionPage(), Messages.WsdlRegistryImportWizard_title);
    }

    /**
     * A wizard page allowing the user to select registry sources
     */
    private static class WsdlSelectionPage extends WsdlSourceBaseWizardPage {
        private RegistryServiceSelector registryServiceSelector;
        private Text registryUrlText;
        private final ISelectionChangedListener selectionChangedListener;

        protected WsdlSelectionPage() {
            super(Messages.WsdlRegistryImportWizard_Selection_title);
            setDescription(Messages.WsdlRegistryImportWizard_Selection_longdesc);
            selectionChangedListener = new ISelectionChangedListener() {
                public void selectionChanged(SelectionChangedEvent event) {
                    registryUrlText.setText(""); //$NON-NLS-1$
                    final Cursor waitCursor = new Cursor(Display.getDefault(),
                            SWT.CURSOR_WAIT);
                    final Cursor originalCursor = getShell().getCursor();
                    try {
                        getShell().setCursor(waitCursor);
                        String[] urls = registryServiceSelector.getWsdlUrls();
                        if (urls.length > 0) {
                            if (urls.length == 1) {
                                if (urls[0] != null) {
                                    registryUrlText.setText(urls[0]);
                                } else {
                                    registryUrlText.setText(""); //$NON-NLS-1$
                                }
                            } else {
                                registryUrlText
                                        .setText(Messages.WsdlRegistryImportWizard_Multiple_URLs_short_mesages);
                            }
                        } else {
                            if (registryServiceSelector.isEmptySelection()) {
                                registryUrlText.setText(""); //$NON-NLS-1$
                            }
                        }
                    } finally {
                        getShell().setCursor(originalCursor);
                        waitCursor.dispose();
                    }
                    ((WsdlImportBaseWizard) getWizard())
                            .updateTarget(getWsdlImportMappings());
                    updatePageComplete();
                }
            };
        }

        /**
         * @param parent
         *            The parent to add the controls to.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
         *      org.eclipse.swt.widgets.Composite)
         */
        public void createControl(Composite parent) {
            Composite control = new Composite(parent, SWT.NONE);
            GridLayout layout = new GridLayout();
            control.setLayout(layout);

            registryServiceSelector = new RegistryServiceSelector(control,
                    SWT.NONE, true);
            registryServiceSelector.setLayoutData(new GridData(SWT.FILL,
                    SWT.FILL, true, true));
            Label urlLabel = new Label(control, SWT.NONE);
            urlLabel.setText(Messages.WsdlRegistryImportWizard_WsdlUrl_label);
            registryUrlText = new Text(control, SWT.BORDER | SWT.FLAT
                    | SWT.READ_ONLY);
            assignGridData(registryUrlText);

            registryServiceSelector
                    .addSelectionChangedListener(selectionChangedListener);

            setControl(control);
            updatePageComplete();
        }

        /**
         * Sets the page complete status based on user selection.
         */
        private void updatePageComplete() {
            setErrorMessage(null);
            setMessage(Messages.WsdlRegistryImportWizard_title);
            if (registryServiceSelector.selectionContainsAnyValidUrls()) {
                setPageComplete(true);
                if (registryServiceSelector.selectionContainsAnyInvalidUrls()) {
                    setMessage(
                            Messages.WsdlRegistryImportWizard_Selection_contains_some_invalid_URLS_warning_message,
                            WARNING);
                }
            } else {
                if (!registryServiceSelector.isEmptySelection())
                    setErrorMessage(Messages.WsdlRegistryImportWizard_NotValidUrl_message);
                setPageComplete(false);
            }
        }

        /**
         * Sets a default GridData layout data for the control.
         * 
         * @param control
         *            The control to set the layout data for.
         */
        private void assignGridData(Control control) {
            control
                    .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        }

        @Override
        WsdlImportMapping[] getWsdlImportMappings() {
            return WsdlImportBaseWizard
                    .getWsdlImportMappings(registryServiceSelector);
        }
    }
}
