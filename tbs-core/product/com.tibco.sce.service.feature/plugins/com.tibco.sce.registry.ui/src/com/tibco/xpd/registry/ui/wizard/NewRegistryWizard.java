/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.registry.ui.wizard;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tibco.xpd.registry.IRegistryManager;
import com.tibco.xpd.registry.Registry;
import com.tibco.xpd.registry.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * @author nwilson
 */
public class NewRegistryWizard extends Wizard implements INewWizard {

    /** The required details page. */
    private RegistryRequiredPage requiredPage;

    /** The new registry object. */
    private Registry registry;

    /** A collection of currently used names. */
    private Collection<String> currentNames;

    /**
     * @param workbench
     *            The workbench for this wizard.
     * @param selection
     *            The current selection.
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(final IWorkbench workbench,
            final IStructuredSelection selection) {
        currentNames = new HashSet<String>();
        IRegistryManager registryManager = com.tibco.xpd.registry.Activator
                .getRegistryManager();
        Collection<Registry> registries = registryManager.getRegistries();
        for (Registry registry : registries) {
            currentNames.add(registry.getName());
        }

        registry = new Registry();

        String requiredPageName = Messages.NewRegistryWizard_Wizard_title;
        setWindowTitle(requiredPageName);
        requiredPage = new RegistryRequiredPage(requiredPageName);

        addPage(requiredPage);
    }

    /**
     * @return true if the wizard completed successfully, otherwise false.
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        registry.setName(requiredPage.getRegistryName());
        registry.setQueryManagerUrl(requiredPage.getQueryManagerUrl());
        registry.setLifeCycleManagerUrl(requiredPage.getLifeCycleManagerUrl());
        IRegistryManager registryManager = com.tibco.xpd.registry.Activator
                .getRegistryManager();
        registryManager.addRegistry(registry);
        return true;
    }

    /**
     * @author nwilson
     */
    class RegistryRequiredPage extends AbstractXpdWizardPage {
        /** The name of the registry. */
        private Text registryName;

        /** The query Manager URL. */
        private Text queryManagerUrl;

        /** The life cycle manager URL. */
        private Text lifeCycleManagerUrl;

        /** Page state updater. */
        private ModifyListener updater;

        private boolean initialised;

        /**
         * @param pageName
         *            The name of the page.
         */
        protected RegistryRequiredPage(final String pageName) {
            super(pageName);
            setTitle(pageName);
            setDescription(Messages.NewRegistryWizard_Wizard_longdesc);
        }

        /**
         * @param parent
         *            The parent control.
         * @see org.eclipse.jface.dialogs.IDialogPage#createControl(
         *      org.eclipse.swt.widgets.Composite)
         */
        public void createControl(final Composite parent) {
            Composite composite = new Composite(parent, SWT.NONE);
            composite.setLayout(new GridLayout(2, false));

            Label nameLabel = new Label(composite, SWT.NONE);
            nameLabel.setText(Messages.NewRegistryWizard_Name_label);
            registryName = new Text(composite, SWT.BORDER);
            registryName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            Label queryManagerUrlLabel = new Label(composite, SWT.NONE);
            queryManagerUrlLabel
                    .setText(Messages.NewRegistryWizard_InquiryUrl_label);
            queryManagerUrl = new Text(composite, SWT.BORDER);
            queryManagerUrl
                    .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

            Label lifeCycleManagerUrlLabel = new Label(composite, SWT.NONE);
            lifeCycleManagerUrlLabel
                    .setText(Messages.NewRegistryWizard_PublishUrl_label);
            lifeCycleManagerUrl = new Text(composite, SWT.BORDER);
            lifeCycleManagerUrl.setLayoutData(new GridData(
                    GridData.FILL_HORIZONTAL));

            updater = new ModifyListener() {
                public void modifyText(ModifyEvent e) {
                    setPageComplete(validatePage(false, true));
                }
            };
            registryName.addModifyListener(updater);
            queryManagerUrl.addModifyListener(updater);
            lifeCycleManagerUrl.addModifyListener(updater);

            setControl(composite);
            setPageComplete(validatePage(true, false));
            initialised = true;
        }

        /**
         * @return The name of the new registry.
         */
        public String getRegistryName() {
            return registryName.getText();
        }

        /**
         * @return The registry inquiry URL.
         */
        public String getQueryManagerUrl() {
            return queryManagerUrl.getText();
        }

        /**
         * @return The registry publish URL.
         */
        public String getLifeCycleManagerUrl() {
            return lifeCycleManagerUrl.getText();
        }

        private boolean validatePage(boolean setFocus, boolean showMessage) {
            if (!initialised) {
                return false;
            }
            setMessage(null);
            setErrorMessage(null);

            // Name
            String name = registryName.getText();
            if (name.length() == 0) {
                if (showMessage) {
                    setErrorMessage(Messages.NewRegistryWizard_EmptyName_longdesc);
                }
                if (setFocus) {
                    registryName.setFocus();
                }
                return false;
            } else if (currentNames.contains(name)) {
                if (showMessage) {
                    setErrorMessage(Messages.NewRegistryWizard_RegistryAlreadyExists_longdesc);
                }
                if (setFocus) {
                    registryName.setFocus();
                }
                return false;
            }

            // Inquiry URL
            if (queryManagerUrl.getText().length() == 0) {
                if (showMessage) {
                    setErrorMessage(Messages.NewRegistryWizard_EmptyInuiryUrl_longdesc);
                }
                if (setFocus) {
                    queryManagerUrl.setFocus();
                }
                return false;
            }
            // Publish URL
            if (lifeCycleManagerUrl.getText().length() == 0) {
                if (showMessage) {
                    setMessage(
                            Messages.NewRegistryWizard_EmptyPublishUrl_longdesc,
                            WARNING);
                }
                if (setFocus) {
                    lifeCycleManagerUrl.setFocus();
                }
                return true;
            }
            return true;
        }

    }
}
