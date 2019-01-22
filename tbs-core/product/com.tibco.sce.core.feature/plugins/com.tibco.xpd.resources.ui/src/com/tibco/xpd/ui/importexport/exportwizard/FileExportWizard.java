/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ISelectionValidator;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.ui.importexport.exportwizard.AbstractExportWizard.ExportDestination;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.IExportDataProvider;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog;
import com.tibco.xpd.ui.importexport.utils.OverwriteFileMessageDialog.OverwriteStatus;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectExclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.TypedViewerFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * An abstract export wizard that can be used to export a selection of files
 * from the workspace.
 * <p>
 * NOTE: <code>AbstractExportWizard</code> should be used if XSLT is being used
 * to generate the exported file.
 * </p>
 * 
 * @see AbstractExportWizard
 * 
 * @author njpatel
 * 
 * @since 3.3
 */
public abstract class FileExportWizard extends Wizard implements IExportWizard,
        IExportDataProvider {

    // Wizard page for selecting xpdl files and export destination
    private ExportWizardPage pageIO = null;

    private IStructuredSelection initialSelection = null;

    // Overwrite selection
    private OverwriteStatus overwriteDestFile;

    // Subtasks list
    protected List<ExportSubTask> subTasks = null;

    // If project output is selected then the following subfolder
    // will be used under the Project folder for output.
    private String workspaceExportFolder =
            "/" + Messages.AbstractExportWizard_exportFolder_label; //$NON-NLS-1$

    private String outputFileExt;

    private String windowMessage =
            Messages.AbstractExportWizard_default_message;

    private ISelectionValidator validator;

    /**
     * Constructor.
     */
    public FileExportWizard() {
        super();
        setNeedsProgressMonitor(true);
        subTasks = new ArrayList<ExportSubTask>();
    }

    /**
     * Get the initial selection for this wizard.
     * 
     * @return
     */
    public IStructuredSelection getInitialSelection() {
        return initialSelection;
    }

    /**
     * Get the wizard page that deals with the selection of the files to export.
     * 
     * @return
     */
    protected ExportWizardPage getExportPage() {
        return pageIO;
    }

    /**
     * Get the file extension to apply to the exported files.
     * 
     * @return String
     */
    public abstract String getExportFileExt();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.initialSelection = selection;

    }

    /**
     * Set the selection validator to validate the selected files for export.
     * 
     * @param validator
     */
    public void setSelectionValidator(ISelectionValidator validator) {
        this.validator = validator;
    }

    /**
     * Set the default message to appear in the wizard page.
     * 
     * @param message
     */
    public void setWindowMessage(String message) {
        windowMessage = message;
    }

    /**
     * Get the export destination selection.
     * 
     * @return <code>{@link ExportDestination}</code>.
     * 
     * @throws NullPointerException
     *             If the Export Wizard IO page hasn't been loaded
     */
    protected final ExportDestination getExportDestinationSelection() {
        if (pageIO != null) {
            return pageIO.getDestinationSelection();
        } else {
            throw new NullPointerException("Export wizard IO page not loaded."); //$NON-NLS-1$
        }
    }

    /**
     * Get the export destination path selected.
     * 
     * @return IPath of the destination. If no destination path provided then
     *         <code>null</code> will be returned.
     * 
     * @throws NullPointerException
     *             If the Export Wizard IO page hasn't been loaded
     */
    protected final IPath getDestinationPath() {
        if (pageIO != null) {
            return pageIO.getDestinationPath();
        } else {
            throw new NullPointerException("Export wizard IO page not loaded."); //$NON-NLS-1$
        }
    }

    /**
     * Get the list of files selected for export.
     * 
     * @return IStructuredSelection of files to export, if no files selected
     *         then empty selection will be returned.
     * 
     * @throws NullPointerException
     *             If the Export Wizard IO page hasn't been loaded
     */
    protected final IStructuredSelection getSelectedFiles() {
        if (pageIO != null) {
            return pageIO.getSelectedFiles();
        } else {
            throw new NullPointerException("Export wizard IO page not loaded."); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    public final boolean performFinish() {
        boolean ret = true;

        if (!canFinish()) {
            getContainer().updateButtons();
            return false;
        }

        if (PlatformUI.getWorkbench().saveAllEditors(true)) {
            // Perform operation in a workspace modify operation as the
            // workspace may be affected
            WorkspaceModifyOperation operation =
                    new WorkspaceModifyOperation() {
                        @Override
                        protected void execute(IProgressMonitor monitor)
                                throws CoreException,
                                InvocationTargetException, InterruptedException {
                            IStructuredSelection selectedFiles =
                                    getSelectedFiles();

                            if (selectedFiles != null
                                    && !selectedFiles.isEmpty()) {
                                try {
                                    // Perform the export
                                    performOperation(monitor);
                                } catch (OperationCanceledException e) {
                                    throw new InvocationTargetException(e);
                                } catch (InterruptedException e) {
                                    throw e;
                                } catch (CoreException e) {
                                    throw e;
                                } catch (Exception e) {
                                    // Throw core exception
                                    throw new CoreException(new Status(
                                            IStatus.ERROR,
                                            XpdResourcesUIActivator.ID,
                                            IStatus.OK,
                                            e.getLocalizedMessage(), e));
                                }
                            }
                        }
                    };

            try {
                getContainer().run(true, true, operation);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();

                if (cause != null) {
                    if (cause instanceof OperationCanceledException) {
                        ret = false;
                    } else {
                        XpdResourcesUIActivator.getDefault().getLogger()
                                .error(e.getCause(), e.getLocalizedMessage());
                        ErrorDialog
                                .openError(getShell(),
                                        Messages.AbstractExportWizard_exportErrDialog_title,
                                        Messages.AbstractExportWizard_exportErrDialog_message,
                                        cause instanceof CoreException ? ((CoreException) cause)
                                                .getStatus()
                                                : new Status(
                                                        IStatus.ERROR,
                                                        XpdResourcesUIActivator.ID,
                                                        IStatus.OK,
                                                        cause
                                                                .getLocalizedMessage(),
                                                        cause));
                    }
                }
            } catch (InterruptedException e) {
                // Process interrupted so don't close the wizard
                ret = false;
            }
        } else {
            return false;
        }

        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    public void addPages() {
        // Add the selection/destination page
        pageIO =
                new ExportWizardPage(initialSelection, getInput(), getSorter(),
                        getFilters(), this);
        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(windowMessage);
        pageIO.setSelectionValidator(validator);
        addPage(pageIO);
    }

    /**
     * Export the given input file to produce the output file. The output file
     * could be destined for the workspace or the file system.
     * 
     * @see WorkspaceOutputFile
     * @see FileSystemOutputFile
     * 
     * @param inputFile
     *            file being processed
     * @param outputFile
     *            output selection
     * @param monitor
     *            progress monitor
     * @throws CoreException
     */
    protected abstract void processFile(IFile inputFile,
            IOutputFile<?> outputFile, IProgressMonitor monitor)
            throws CoreException;

    /**
     * Get the <code>ViewerFilter</code> objects to apply to the tree viewer.
     * 
     * @see EObjectExclusionFilter
     * @see EObjectFilter
     * @see EObjectInclusionFilter
     * @see FileExtensionInclusionFilter
     * @see NoFileContentFilter
     * @see SpecialFoldersOnlyFilter
     * @see TypedViewerFilter
     * @see XpdNatureProjectsOnly
     * 
     * @return Array of ViewerFilter objects to apply to the tree viewer,
     *         otherwise <code>null</code> if no filter is required.
     */
    protected abstract ViewerFilter[] getFilters();

    /**
     * Get the <code>ViewerSorter</code> objects to apply to the tree viewer.
     * The default implementation sets the default sorter - sort content in
     * alphabetical order. Subclasses may override to set a different sorter.
     * 
     * @return A <code>ViewerSorter</code> to apply to the tree viewer.
     */
    private ViewerSorter getSorter() {
        // Default implementation is to sort alphabetically. Subclass can
        // override this to provide a sorter.
        return new ViewerSorter();
    }

    /**
     * Get the tree viewer input. This is the workspace root by default.
     * Subclasses may override to provide their own input.
     * 
     * @return Input for the tree viewer.
     */
    protected Object getInput() {
        // Default implementation is to set the workspace root, subclasses can
        // override this to provide their own input.
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    /**
     * Register any subtasks required by the wizard. This tasks will run
     * post-export of every selected file.
     * 
     * @param sub
     *            - SubTask
     */
    public final void registerSubTask(ExportSubTask sub) {
        subTasks.add(sub);
    }

    /**
     * Set the preference key, value pair
     * 
     * @param szKey
     * @param szValue
     */
    public void setPreference(String szKey, String szValue) {
        // Make sure we have all data
        if (szKey != null && szKey != "" && szValue != null) { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesUIActivator.getDefault().getPreferenceStore();
            pStore.setValue(getClass().getName() + szKey, szValue);
        }
    }

    /**
     * Get the value of the give preference key
     * 
     * @param szKey
     * @return String
     */
    public String getPreference(String szKey) {
        String szRet = null;

        // Make sure we have the key, otherwise return null
        if (szKey != null && szKey != "") { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesUIActivator.getDefault().getPreferenceStore();
            szRet = pStore.getString(getClass().getName() + szKey);
        }

        return szRet;
    }

    @Override
    public void dispose() {
        // If any subtasks defined then call their dispose
        if (subTasks != null && !subTasks.isEmpty()) {
            for (ExportSubTask task : subTasks) {
                task.dispose();
            }
            subTasks = null;
        }
        super.dispose();
    }

    /**
     * Add export specific workspace output folder. This will create a folder
     * under the project "<i>EXPORT/&lt;folderName&gt;</i>" for output<br>
     * 
     * @param folderName
     */
    protected final void setWorkspaceExportFolder(String folderName) {
        if (folderName.charAt(0) != '\\' && folderName.charAt(0) != '/')
            workspaceExportFolder += IPath.SEPARATOR;

        workspaceExportFolder += folderName;
    }

    /**
     * Gets the project export folder setting
     * 
     * @return String
     */
    public final String getWorkspaceExportFolder() {
        // Get IPath for the export folder so that we can
        // return the folder location in the right format
        IPath pt = new Path(workspaceExportFolder);
        return pt.toString();
    }

    /**
     * This method is called when the output file is being saved. It will strip
     * the file extension of the given input file and replace it with the given
     * output file extension (using <code>{@link #getExportFileExt()}</code>).
     * <p>
     * Subclass should override this function if the output file name needs to
     * be tailored.
     * </p>
     * 
     * @param name
     * @return String
     */
    protected String getOutputFileName(String name) {
        String fileName = name;

        // Get the output file extension
        if (outputFileExt == null) {
            outputFileExt = getExportFileExt();
        }

        if (outputFileExt != null && outputFileExt.length() > 0) {

            // Find the last stop and replace the text after that with the given
            // file extension
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            fileName += "." + outputFileExt; //$NON-NLS-1$
        }

        return fileName;
    }

    /**
     * Transform the input files to create the export files. This will loop
     * through all selected files and apply the transformation to each.
     * 
     * @param monitor
     * @throws CoreException
     * @throws InterruptedException
     */
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        IStructuredSelection selectedFiles = getSelectedFiles();

        // calculate the number of tasks to set for the progress
        // monitor
        int monitorTasks = selectedFiles.size() * 2 + 1;

        // If we have subtasks then multiply the task with this
        // number
        monitorTasks += (selectedFiles.size() * subTasks.size());

        monitor.beginTask(Messages.AbstractExportWizard_exportingTask_title,
                monitorTasks);

        // Set the overwrite flag to a default value of overwrite
        overwriteDestFile = OverwriteStatus.FILE;

        IOutputFile<?> outputFile = null;
        try {
            for (Iterator<?> iter = selectedFiles.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (monitor.isCanceled()) {
                    // User selected to cancel
                    throw new OperationCanceledException();
                }
                monitor.worked(1);
                if (obj instanceof IFile) {
                    exportFile((IFile) obj, monitor);
                }
            }
        } finally {
            /*
             * If the export destination is the workspace then re-sync the
             * workspace with the file system if sub-tasks were executed. This
             * is needed as the sub tasks may have added more resources to the
             * export destination.
             */
            if (subTasks != null && outputFile instanceof WorkspaceOutputFile) {
                IContainer container =
                        ((WorkspaceOutputFile) outputFile).getFile()
                                .getParent();
                if (container != null
                        && !container.isSynchronized(IResource.DEPTH_INFINITE)) {
                    // Synch the container with the file system
                    container.refreshLocal(IResource.DEPTH_INFINITE, monitor);
                }
            }

            if (monitor != null)
                monitor.done();
        }
    }

    /**
     * Export the given file. This will process any registered
     * {@link ExportSubTask subtasks} before calling
     * {@link #processFile(IFile, IOutputFile, IProgressMonitor) processFile} to
     * carry out the actual export.
     * 
     * @param inputFile
     * @param monitor
     * @throws CoreException
     */
    protected void exportFile(IFile inputFile, IProgressMonitor monitor)
            throws CoreException {
        IOutputFile<?> outputFile = null;
        /*
         * If the overwrite file message was shown and NO selected then the
         * value needs to be reset back to default, otherwise no further files
         * will be processed
         */
        if (overwriteDestFile == OverwriteStatus.NO)
            overwriteDestFile = OverwriteStatus.FILE;

        // Update progress monitor
        monitor.subTask(inputFile.getName());

        // Get the output file
        outputFile = getOutputFile(inputFile);

        if (outputFile != null) {
            // If file exists and the user hasn't selected to
            // overwrite all files then show dialog
            if (outputFile.exists()
                    && overwriteDestFile != OverwriteStatus.ALL_FILES) {
                final IOutputFile<?> outFile = outputFile;
                // Ask user if the existing file can be
                // overwritten
                getShell().getDisplay().syncExec(new Runnable() {
                    public void run() {
                        overwriteDestFile = overwriteFileMessageDialog(outFile);
                    }
                });
            }

            // Check whether we can process the file
            if (overwriteDestFile.canOverwrite()) {
                // Process any subtasks
                if (subTasks != null) {
                    for (ExportSubTask task : subTasks) {
                        // Each subtask is ONE tick of main task.
                        File file =
                                outputFile instanceof WorkspaceOutputFile ? ((WorkspaceOutputFile) outputFile)
                                        .getFile().getLocation().toFile()
                                        : (File) outputFile.getFile();

                        task.perform(new SubProgressMonitor(monitor, 1),
                                inputFile,
                                file);
                    }
                }

                // Sub-tasks may have reset the subtask name.
                monitor.subTask(inputFile.getName());

                // Export the file
                processFile(inputFile, outputFile, monitor);
            }
            monitor.worked(1);

            // If the overwrite file dialog was shown and the
            // user cancelled then stop the export process
            if (overwriteDestFile == OverwriteStatus.CANCEL) {
                throw new OperationCanceledException();
            }
        }
    }

    /**
     * Returns the output file object based on the selection made in the wizard
     * with regards to the destination of the export.
     * 
     * @param inputFile
     * @return The <code>{@link OutputFile}</code> object which wraps the
     *         resource being created.
     * @throws CoreException
     */
    private IOutputFile<?> getOutputFile(IFile inputFile) throws CoreException {
        IOutputFile<?> outputFile = null;
        String outputFileName = getOutputFileName(inputFile.getName());

        // If the output file name is null or blank then set it to the same name
        // as the input file
        if (outputFileName == null || outputFileName.length() == 0) {
            outputFileName = inputFile.getName();
        }

        /*
         * If export destination is the project then create the export folder in
         * the workspace if required and return an IFile
         */
        if (getExportDestinationSelection() == ExportDestination.PROJECT) {
            IFolder folder =
                    inputFile.getProject().getFolder(workspaceExportFolder);
            ProjectUtil.createFolder(folder, false, null);
            outputFile =
                    new WorkspaceOutputFile(folder.getFile(outputFileName));

        } else /* export to external path */{
            IPath destPath = getDestinationPath();

            if (destPath != null) {
                IPath path = destPath.append(outputFileName);
                outputFile = new FileSystemOutputFile(path.toFile());
            }
        }
        return outputFile;
    }

    /**
     * Show the overwrite file message dialog
     * 
     * @param szOutputFileName
     * @return <code>{@link OverwriteStatus}</code> value of the selection made
     *         in the dialog
     */
    private OverwriteStatus overwriteFileMessageDialog(IOutputFile<?> outputFile) {
        String msg =
                String
                        .format(Messages.AbstractExportWizard_destFileExistsDlg_message,
                                outputFile.getName(),
                                outputFile.getPath().toString());

        OverwriteFileMessageDialog dialog =
                new OverwriteFileMessageDialog(getShell(),
                        Messages.AbstractExportWizard_destFileExistsDlg_title,
                        msg);

        return dialog.getOverwriteStatus(dialog.open());
    }

    /**
     * Represents the output file - this could either be a <code>File</code>(
     * {@link FileSystemOutputFile}) or an <code>IFile</code>(
     * {@link WorkspaceOutputFile}). If the export destination is set to the
     * export folder in the project then this will be an <code>IFile</code>.
     */
    protected interface IOutputFile<T> {
        String getName();

        IPath getPath();

        boolean exists() throws CoreException;

        T getFile();
    }

    /**
     * This represents the workspace output file ({@link IFile}).
     * 
     * @author njpatel
     * 
     */
    protected class WorkspaceOutputFile implements IOutputFile<IFile> {

        private final IFile file;

        private WorkspaceOutputFile(IFile file) {
            Assert.isNotNull(file);
            this.file = file;
        }

        public boolean exists() throws CoreException {
            return file.exists();
        }

        public IFile getFile() {
            return file;
        }

        public String getName() {
            return file.getName();
        }

        public IPath getPath() {
            return file.getFullPath();
        }
    }

    /**
     * This represents the File system ({@link File}) output file.
     * 
     * @author njpatel
     * 
     */
    protected class FileSystemOutputFile implements IOutputFile<File> {

        private final File file;

        private FileSystemOutputFile(File file) {
            Assert.isNotNull(file);
            this.file = file;
        }

        public boolean exists() throws CoreException {
            return file.exists();
        }

        public File getFile() {
            return file;
        }

        public String getName() {
            return file.getName();
        }

        public IPath getPath() {
            return new Path(file.getAbsolutePath());
        }
    }
}
