/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.gen.ui.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ISelectionValidator;

import com.tibco.xpd.bom.gen.api.BOMGenerator2;
import com.tibco.xpd.bom.gen.api.GeneratorData;
import com.tibco.xpd.bom.gen.api.GeneratorData.BuildType;
import com.tibco.xpd.bom.gen.extensions.BOMGenerator2Extension;
import com.tibco.xpd.bom.gen.ui.Activator;
import com.tibco.xpd.bom.gen.ui.internal.Messages;
import com.tibco.xpd.bom.gen.util.DependencyAnalyzer;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.IExportDataProvider;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;
import com.tibco.xpd.validation.ValidationActivator;
import com.tibco.xpd.validation.destinations.Destination;
import com.tibco.xpd.validation.explicit.Validator;
import com.tibco.xpd.validation.provider.IIssue;
import com.tibco.xpd.validation.provider.ValidationException;

/**
 * Wizard that allows the user to generate, using registered strategies, from
 * BOM models.
 * 
 * @author njpatel
 * 
 * @since 3.3
 * 
 */
public class BOMGeneratorWizard extends Wizard implements IExportWizard {

    private static final String DIALOG_SETTINGS_ID = "bomGenWizard.settings"; //$NON-NLS-1$

    private static final String DESTINATION_KEY = "export_destination"; //$NON-NLS-1$

    private ExportWizardPage exportPage;

    private StrategySelectionPage genPage;

    private DependencyAnalyzer dependencyAnalyzer;

    private IStructuredSelection selection;

    private Validator validator;

    /**
     * BOM generation wizard.
     */
    public BOMGeneratorWizard() {
        setWindowTitle(Messages.BOMGeneratorWizard_title);
        setHelpAvailable(false);
        setNeedsProgressMonitor(true);

        Destination destination =
                ValidationActivator
                        .getDefault()
                        .getDestination(BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC);
        Assert.isNotNull(destination,
                "Failed to get the BOM generic destination"); //$NON-NLS-1$
        validator = new Validator(destination);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        IDialogSettings section =
                Activator.getDefault().getDialogSettings()
                        .getSection(DIALOG_SETTINGS_ID);
        if (section == null) {
            section =
                    Activator.getDefault().getDialogSettings()
                            .addNewSection(DIALOG_SETTINGS_ID);
        }
        setDialogSettings(section);
    }

    @Override
    public void addPages() {
        exportPage =
                new ExportWizardPage(selection, ResourcesPlugin.getWorkspace()
                        .getRoot(), new ViewerSorter(), getFilters(),
                        getExportProvider(),
                        ExportWizardPage.FILESYSTEM_DESTINATION_OPTION) {
                    @Override
                    protected void updatePageCompletion() {
                        if (!isAutoBuildEnabled()) {
                            // Auto build is switched off so cannot generate
                            setErrorMessage(Messages.BOMGeneratorWizard_buildAutomaticSwitchedOff_error_message);
                            setPageComplete(false);
                        } else {
                            super.updatePageCompletion();
                        }
                    }
                };
        exportPage.setTitle(getWindowTitle());
        exportPage
                .setDescription(Messages.BOMGeneratorWizard_page_shortDescription);

        // If an export destination is persisted then set it in the page
        String path = getPersistedDestinationPath();
        if (path != null && path.length() > 0) {
            exportPage.setDestinationPath(new Path(path));
        }

        exportPage.setSelectionValidator(new ISelectionValidator() {
            public String isValid(Object selection) {
                if (selection instanceof IStructuredSelection) {
                    try {
                    	IProject project = null;
                        for (Iterator<?> iter =
                                ((IStructuredSelection) selection).iterator(); iter
                                .hasNext();) {
                            Object next = iter.next();
                            if (next instanceof IFile) {
                                IFile file = (IFile) next;
                                if (project == null)
                                	project = file.getProject();                                
                                if (!file.getProject().equals(project)){
                                	return String
                                    	.format(Messages.BOMGeneratorWizard_different_projects_message,
                                            file.getName());
                                }
                                
                                Collection<IIssue> issues =
                                        validator.validate(file);
                                if (hasError(issues)) {
                                    return String
                                            .format(Messages.BOMGeneratorWizard_validationErrors_error_message,
                                                    file.getName());
                                }
                            }
                        }
                    } catch (ValidationException e) {
                        return e.getLocalizedMessage();
                    }
                }
                return null;
            }
        });
        addPage(exportPage);
        genPage = new StrategySelectionPage("generators"); //$NON-NLS-1$
        genPage.setTitle(getWindowTitle());
        genPage
                .setDescription(Messages.BOMGeneratorWizard_selectStrategy_page_shortdesc);
        addPage(genPage);
    }

    /**
     * Run the dependency analyzer on the current file selection.
     * 
     * @see #getDependencyAnalyzer()
     * 
     * @return {@link DependencyAnalyzer} if files are selected,
     *         <code>null</code> otherwise.
     */
    public DependencyAnalyzer runDependencyAnalyzer() {
        dependencyAnalyzer = null;
        Collection<IFile> selectedFiles = getSelection();
        if (!selectedFiles.isEmpty()) {
            dependencyAnalyzer = new DependencyAnalyzer(selectedFiles);
        }
        return dependencyAnalyzer;
    }

    /**
     * Get the last calculated dependency analyzer.
     * 
     * @see #runDependencyAnalyzer()
     * 
     * @return {@link DependencyAnalyzer} or <code>null</code> if no dependency
     *         analysis has been run yet.
     */
    public DependencyAnalyzer getDependencyAnalyzer() {
        return dependencyAnalyzer;
    }

    @Override
    public boolean performFinish() {
        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {
                performOperation(monitor);
            }
        };

        try {
            getContainer().run(true, true, op);
            return true;
        } catch (InvocationTargetException e) {
            IStatus status = null;

            if (e.getCause() instanceof CoreException) {
                status = ((CoreException) e.getCause()).getStatus();
            } else {
                status =
                        new Status(IStatus.ERROR, Activator.PLUGIN_ID, e
                                .getLocalizedMessage(), e.getCause());
            }
            Activator.getDefault().getLogger().log(status);

            ErrorDialog
                    .openError(getShell(),
                            Messages.BOMGeneratorWizard_errorDialog_title,
                            Messages.BOMGeneratorWizard_errorReportedDuringGeneration_error_message,
                            status);

        } catch (InterruptedException e) {
            // Do nothing
        }

        return false;
    }

    /**
     * Get the current selection of files in this wizard.
     * 
     * @return selected files or empty list of none.
     */
    public Collection<IFile> getSelection() {
        List<IFile> bomFiles = new ArrayList<IFile>();
        if (exportPage != null) {
            IStructuredSelection selection = exportPage.getSelectedFiles();
            if (selection != null) {
                for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
                    Object next = iter.next();
                    if (next instanceof IFile) {
                        bomFiles.add((IFile) next);
                    }
                }
            }
        }
        
        
        return bomFiles;
    }

    /**
     * Perform the generation.
     * 
     * @param monitor
     * @throws CoreException
     */
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException {
        Collection<IFile> selectedFiles = getSelection();
        BOMGenerator2Extension generator = genPage.getSelectedStrategy();

        if (generator != null && !selectedFiles.isEmpty()) {
            Collection<IFile> initialSelection = getSelection();
            DependencyAnalyzer analyzer = getDependencyAnalyzer();
            List<IFile> result = analyzer.getResult();
            IPath path = exportPage.getDestinationPath();
            persistDestinationPath(path.toString());
            File destination = new File(path.toString());
            generator.getGenerator().setDependencyProvider(analyzer);

            executeStrategy(generator, new GeneratorData(BuildType.FULL,
                    initialSelection), result, analyzer, destination, monitor);
        }
    }

    /**
     * Execute the given strategy.
     * 
     * @param strategy
     *            strategy to execute
     * @param data
     *            context data to pass to strategy
     * @param filesToProcess
     *            all BOM resources to process
     * @param analyzer
     *            dependency analyzer to pass to strategy
     * @param destination
     *            the destination of the generated artifacts.
     * @param monitor
     *            progress monitor.
     * @throws CoreException
     */
    private void executeStrategy(BOMGenerator2Extension strategy,
            GeneratorData data, Collection<IFile> filesToProcess,
            DependencyAnalyzer analyzer, File destination,
            IProgressMonitor monitor) throws CoreException {
        BOMGenerator2 generator = strategy.getGenerator();
        generator.setDependencyProvider(analyzer);
        
        if (filesToProcess.size() > 0){
        	IProject project = filesToProcess.iterator().next().getProject();
        	generator.prepareForBuildCycle(project, BuildType.FULL);
        }        

        try {
            // monitor.beginTask("Executing Strategy...", 3);
            // Initialize
            generator.initialize(filesToProcess, data, monitor/*
                                                               * new
                                                               * SubProgressMonitor
                                                               * ( monitor, 1)
                                                               */);
            monitor.worked(1);
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            // Generate
            generator.generate(filesToProcess, data, monitor/*
                                                             * new
                                                             * SubProgressMonitor
                                                             * ( monitor, 1)
                                                             */);
            monitor.worked(1);
            if (monitor.isCanceled()) {
                throw new OperationCanceledException();
            }
            // Package
            generator.archive(filesToProcess, destination, data, monitor
            /* new SubProgressMonitor(monitor, 1) */);
        } finally {
            monitor.done();
        }
    }

    /**
     * Get the persisted export destination path, if any.
     * 
     * @return
     */
    private String getPersistedDestinationPath() {
        return getDialogSettings().get(DESTINATION_KEY);
    }

    /**
     * Persist the selected export destination path.
     * 
     * @param path
     */
    private void persistDestinationPath(String path) {
        getDialogSettings().put(DESTINATION_KEY, path);
    }

    /**
     * Check if there are any error issues in the collection.
     * 
     * @param issues
     * @return
     */
    private boolean hasError(Collection<IIssue> issues) {
        if (issues != null) {
            for (IIssue issue : issues) {
                if (issue.getSeverity() == IMarker.SEVERITY_ERROR) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the viewer filters to apply to the file selection tree viewer.
     * 
     * @return
     */
    private ViewerFilter[] getFilters() {
        ViewerFilter[] filters = new ViewerFilter[4];
        /*
         * Filters to show bom files in BOM special folder in XPD nature
         * projects
         */
        filters[0] = new XpdNatureProjectsOnly(false);

        filters[1] =
                new SpecialFoldersOnlyFilter(Collections
                        .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND));
        filters[2] =
                new FileExtensionInclusionFilter(Collections
                        .singleton(BOMResourcesPlugin.BOM_FILE_EXTENSION));
        filters[3] = new NoFileContentFilter();
        return filters;
    }

    /**
     * Check if auto build is enabled in the workspace.
     * 
     * @return
     */
    private boolean isAutoBuildEnabled() {
        IWorkspaceDescription description =
                ResourcesPlugin.getWorkspace().getDescription();
        return description.isAutoBuilding();
    }

    /**
     * Export data provider for the export wizard page.
     * 
     * @return
     */
    private IExportDataProvider getExportProvider() {
        return new IExportDataProvider() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage
             * .IExportDataProvider#getPreference(java.lang.String)
             */
            public String getPreference(String key) {
                String ret = null;

                // Make sure we have the key, otherwise return null
                if (key != null) {
                    IPreferenceStore store =
                            Activator.getDefault().getPreferenceStore();
                    ret = store.getString(getClass().getName() + "." + key); //$NON-NLS-1$
                }

                return ret;
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage
             * .IExportDataProvider#getWorkspaceExportFolder()
             */
            public String getWorkspaceExportFolder() {
                return "BOM Generator"; //$NON-NLS-1$
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage
             * .IExportDataProvider#setPreference(java.lang.String,
             * java.lang.String)
             */
            public void setPreference(String key, String value) {
                if (key != null && value != null) {
                    IPreferenceStore store =
                            Activator.getDefault().getPreferenceStore();
                    store.setValue(key, value);
                }
            }
        };
    }
}
