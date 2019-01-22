/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.importexport.ImportExportPlugin;
import com.tibco.xpd.importexport.export.custom.CustomExportWizard;
import com.tibco.xpd.importexport.imports.custom.CustomImportWizard;
import com.tibco.xpd.importexport.internal.Messages;

/**
 * Import/Export plug-in generator new wizard. This wizard will allow the user
 * to create a new plug-in containing either an import or export wizard. The
 * wizards will be based on either the <code>{@link CustomExportWizard}</code>
 * or <code>{@link CustomImportWizard}</code> which will use XSL to
 * import/export resources.
 * <p>
 * Plug-in templates will be used to generate the new plug-in jar file that will
 * be stored in a given location (user choice). This wizard will collect all the
 * information from the user to create this new plug-in.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class NewPluginGeneratorWizard extends Wizard implements INewWizard {

    /**
     * Wizard Type enum used to indicate what type of wizard - import or export
     * - is being created.
     */
    public enum WizardType {
        IMPORT(Messages.NewPluginGeneratorWizard_wizardTypeImport_title), EXPORT(
                Messages.NewPluginGeneratorWizard_wizardTypeExport_title);

        private String name;

        WizardType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Generated plug-in destination - install or export to external folder.
     */
    public enum PluginDestination {
        /*
         * XPD-7040: We have now removed the 'Install Plug-in' option from the
         * destination.
         */
        // Create the plugin jar in the folder given
        CREATEINFOLDER
    };

    protected static final String WIZARD_BANNER_IMG =
            "icons/wizban/newpprj_wiz.gif"; //$NON-NLS-1$

    /*
     * Templates
     */
    private static final String IMPORT_TEMPLATEFILE =
            "/templates/importWizardTemplate.jar"; //$NON-NLS-1$

    private static final String EXPORT_TEMPLATEFILE =
            "/templates/exportWizardTemplate.jar"; //$NON-NLS-1$

    /*
     * Pages
     */
    private PluginInfoPage pluginInfoPage;

    private WizardInfoPage wizardInfoPage;

    private TreeViewerFilterPage filterPage;

    private DestInfoPage destPage;

    private String destinationPath = null;

    private boolean restartRequired = false;

    private PluginDestination destination;

    /**
     * Wizard to create new import wizard plug-in.
     */
    public NewPluginGeneratorWizard() {
        super();
        setWindowTitle(Messages.NewPluginGeneratorWizard_window_title);
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {

        ImageDescriptor img =
                ImportExportPlugin.getImageDescriptor(WIZARD_BANNER_IMG);

        // Plug-in information
        pluginInfoPage = new PluginInfoPage("pluginInfo", //$NON-NLS-1$
                Messages.NewPluginGeneratorWizard_pluginPage_title, img);
        addPage(pluginInfoPage);
        // Wizard information
        wizardInfoPage = new WizardInfoPage("wizardInfo", img); //$NON-NLS-1$
        addPage(wizardInfoPage);
        // Tree viewer filter selection of special folders and file extension
        filterPage = new TreeViewerFilterPage("filterInfo", img); //$NON-NLS-1$
        addPage(filterPage);
        // Plug-in destination choice - install or export to external folder
        destPage =
                new DestInfoPage(
                        "destInfo", Messages.NewPluginGeneratorWizard_destinationPage_title, img); //$NON-NLS-1$
        addPage(destPage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        // Nothing to do
    }

    /**
     * Get the currently selected wizard type
     * 
     * @return
     */
    public WizardType getWizardType() {
        if (pluginInfoPage == null) {
            throw new NullPointerException("pluginInfo page not loaded."); //$NON-NLS-1$
        }
        return pluginInfoPage.getWizardType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        boolean bRet = false;

        // Get destination selection
        destination = destPage.getDestinationSelection();

        // If folder destination selected get the folder path
        if (destination == PluginDestination.CREATEINFOLDER)
            destinationPath = destPage.getDestinationPath();

        try {
            getContainer().run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor)
                        throws InvocationTargetException, InterruptedException {
                    // Generate the import wizard plug-in
                    performOperation(monitor);
                }
            });

            // Completed so set return value to true
            bRet = true;

        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
            bRet = false;
        } catch (InterruptedException e1) {
            bRet = false;
        }

        // If restart is required restart the workbench
        if (bRet && restartRequired) {
            // If restart was not successful then warn user
            if (!PlatformUI.getWorkbench().restart()) {
                MessageDialog
                        .openInformation(getShell(),
                                Messages.NewPluginGeneratorWizard_restartFailedDialog_title,
                                Messages.NewPluginGeneratorWizard_restartFailedDialog_message);
            }
        }

        return bRet;
    }

    /**
     * Generate the new import wizard plug-in. Called by
     * <code>{@link #performFinish()}</code>
     * 
     * @param monitor
     * 
     * @throws InterruptedException
     */
    private void performOperation(IProgressMonitor monitor)
            throws InterruptedException {

        try {
            String templateFile = null;
            WizardType type = getWizardType();

            // Get the template URL
            if (type == null) {
                throw new NullPointerException("Wizard type selection is null."); //$NON-NLS-1$
            }

            if (type.equals(WizardType.IMPORT)) {
                templateFile = IMPORT_TEMPLATEFILE;
            } else if (type.equals(WizardType.EXPORT)) {
                templateFile = EXPORT_TEMPLATEFILE;
            } else {
                throw new IllegalArgumentException("Wizard type is invalid."); //$NON-NLS-1$
            }

            // Generate the plug-in jar
            PluginGenerator pluginGenerator =
                    new PluginGenerator(getShell(), getClass()
                            .getResource(templateFile), new IPluginData() {

                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * com.tibco.xpd.importexport.pluginGenerator.IPluginData
                         * #getPluginDataProvider()
                         */
                        @Override
                        public IPluginDataProvider getPluginDataProvider() {
                            return pluginInfoPage;
                        }

                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * com.tibco.xpd.importexport.pluginGenerator.IPluginData
                         * #getTreeViewerFilterProvider()
                         */
                        @Override
                        public ITreeViewerFilterProvider getTreeViewerFilterProvider() {
                            return filterPage;
                        }

                        /*
                         * (non-Javadoc)
                         * 
                         * @see
                         * com.tibco.xpd.importexport.pluginGenerator.IPluginData
                         * #getWizardDataProvider()
                         */
                        @Override
                        public IWizardDataProvider getWizardDataProvider() {
                            return wizardInfoPage;
                        }
                    });

            // Number of work units for the monitor. 1 for the actual creation
            // of the plugin
            int work = 1;

            monitor.beginTask(Messages.NewPluginGeneratorWizard_createPluginTask_title,
                    work);

            // Make sure progress hasn't been cancelled by user
            if (!monitor.isCanceled()) {
                String szLocation = null;
                File fLocation = null;

                monitor.subTask("..."); //$NON-NLS-1$

                szLocation = destinationPath;

                fLocation = new File(szLocation);

                if (fLocation == null
                        || (fLocation != null && !fLocation.isDirectory())) {
                    String szErrMsg =
                            String.format(Messages.NewPluginGeneratorWizard_folderNotFound_message,
                                    szLocation);

                    displayError(Messages.NewPluginGeneratorWizard_locationNotFoundDialog_title,
                            szErrMsg);
                    throw new InterruptedException();

                } else {
                    // Create the plug-in
                    try {
                        pluginGenerator.create(new File(szLocation));
                        monitor.worked(1);

                    } catch (Exception e) {
                        if (e instanceof InterruptedException) {
                            throw (InterruptedException) e;
                        } else {
                            displayError(getWindowTitle(), e);
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                // Progress cancelled
                throw new InterruptedException();
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Displays an error message dialog (invoked in the UI thread).
     * 
     * @param szTitle
     *            Title of the dialog
     * @param szMessage
     *            Message to display in the dialog
     */
    private void displayError(final String szTitle, final String szMessage) {

        getShell().getDisplay().syncExec(new Runnable() {
            @Override
            public void run() {
                MessageDialog.openError(getShell(), szTitle, szMessage);
            }
        });
    }

    /**
     * Display an error message dialog (invoked in the UI thread) with details
     * of the exception
     * 
     * @param title
     *            Title of the error dialog
     * @param e
     *            Exception to report
     */
    private void displayError(final String title, final Exception e) {
        if (title != null && e != null) {
            getShell().getDisplay().syncExec(new Runnable() {

                @Override
                public void run() {
                    ErrorDialog.openError(getShell(), title, e
                            .getLocalizedMessage(), new Status(IStatus.ERROR,
                            ImportExportPlugin.PLUGIN_ID, IStatus.OK, null, e));
                }
            });
        }
    }

}
