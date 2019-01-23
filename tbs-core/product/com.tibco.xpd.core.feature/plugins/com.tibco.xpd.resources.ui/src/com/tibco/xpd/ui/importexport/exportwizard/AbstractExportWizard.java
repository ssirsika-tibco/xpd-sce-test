/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.xml.sax.SAXException;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExportWizardPage.IExportDataProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider3;
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
 * This is an abstract export wizard class that provides a one page wizard to
 * export selected resources to a chosen destination. The export is done using
 * XSLT transformation with the provided xslt file(s).
 * <p>
 * The following abstract methods have to be implemented:
 * <ul>
 * <li><code>{@link #getExportFileExt()}</code> - Get the file extension that
 * will be used for the exported files.</li>
 * <li><code>{@link ITransformationStylesheetsProvider#getXslts()}</code> - To
 * provide the xslts for the export.
 * <li><code>{@link #getFilters()}</code> - To set the viewer filters to apply
 * to the source selection tree viewer.</li>
 * </ul>
 * </p>
 * <p>
 * The following methods can be called to customize the wizard (see methods for
 * more details):
 * <ul>
 * <li><code>{@link #setWindowTitle(String)}</code> - Set the wizard's title.</li>
 * <li><code>{@link #setWindowMessage(String)}</code> - Set the wizard's
 * message.</li>
 * <li><code>{@link #registerSubTask(ExportSubTask)}</code> - Register
 * <code>{@link ExportSubTask}</code> objects that will be called after export
 * of every resource to carry out any additional tasks.</li>
 * <li> <code>{@link #setWorkspaceExportFolder(String)}</code> - Set the
 * workspace export folder. This is the folder in which the exported files will
 * be placed when the project is selected as the target. The folder name
 * provided will be concatenated with '/Exports'.</li>
 * <li><code>{@link #setPreference(String, String)}</code> - To store values in
 * the preference store</li>
 * </ul>
 * </p>
 * <p>
 * The following methods can be overridden to customize the wizard (see methods
 * for more details):
 * <ul>
 * <li><code>{@link #getSorter()}</code> - To set the sorter for the source
 * selection tree viewer.</li>
 * <li><code>{@link #getInput()}</code> - To set the input for the source tree
 * viewer.</li>
 * <li><code>{@link #getOutputFileName(String)}</code> - This method is called
 * during export to determine the name of the exported file.</li>
 * <li><code>{@link #performOperation(IProgressMonitor)}</code> - Perform the
 * export. The default implementation uses XSLT transformation to export the
 * selected resources.</li>
 * <li><code>{@link #getSystemId()}</code> - If the xslt contains relative URL
 * references (e.g. DTD reference) then a system id has to be provided.</li>
 * </ul>
 * </p>
 * <p>
 * NOTE: If XSLT is not being used to generate the exported file then use
 * <code>FileExportWizard</code> instead.
 * </p>
 * 
 * @see org.eclipse.jface.wizard.Wizard
 * @see FileExportWizard
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractExportWizard extends Wizard implements
        IExportWizard, ITransformationStylesheetsProvider,
        ITransformationStylesheetsProvider3 {

    /** Indicates whether the export is to the project or an external path. */
    public enum ExportDestination {
        // Export to project
        PROJECT,
        // Export to external path
        PATH
    };

    // Wizard page for selecting xpdl files and export destination
    private ExportWizardPage pageIO = null;

    private IStructuredSelection initialSelection = null;

    // Overwrite selection
    private OverwriteStatus overwriteDestFile;

    // Subtasks list
    private List<ExportSubTask> subTasks = null;

    // If project output is selected then the following subfolder
    // will be used under the Project folder for output.
    private String workspaceExportFolder = getInitialRootExportFolder();

    private String outputFileExt;

    private String windowMessage =
            Messages.AbstractExportWizard_default_message;

    public String getWindowMessage() {
        return windowMessage;
    }

    private URL[] xslts;

    private String currentOutputFolder = ""; //$NON-NLS-1$

    private IFile currentInputFile = null;

    /**
     * Constructor.
     */
    public AbstractExportWizard() {
        super();
        setNeedsProgressMonitor(true);
        subTasks = new ArrayList<ExportSubTask>();
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
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.initialSelection = selection;

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
        if (getPageIO() != null) {
            return getPageIO().getDestinationSelection();
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
        if (getPageIO() != null) {
            return getPageIO().getDestinationPath();
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
        if (getPageIO() != null) {
            return getPageIO().getSelectedFiles();
        } else {
            throw new NullPointerException("Export wizard IO page not loaded."); //$NON-NLS-1$
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public final boolean performFinish() {
        boolean ret = true;

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
                                    // print the exception trace else we won't
                                    // know from where it was thrown.
                                    e.printStackTrace();
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
                        ErrorDialog
                                .openError(getShell(),
                                        Messages.AbstractExportWizard_exportErrDialog_title,
                                        Messages.AbstractExportWizard_exportErrDialog_message,
                                        cause instanceof CoreException ? ((CoreException) cause)
                                                .getStatus() : new Status(
                                                IStatus.ERROR,
                                                XpdResourcesUIActivator.ID,
                                                IStatus.OK, cause
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
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        boolean bRet = super.canFinish();

        if (xslts == null) {
            xslts = getXslts();
        }

        // Check that we have a transformer,
        // if not then return false
        if (bRet && (xslts == null || xslts.length == 0)) {
            bRet = false;
        }

        return bRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        // Add the selection/destination page
        pageIO =
                new ExportWizardPage(initialSelection, getInput(), getSorter(),
                        getFilters(), new IExportDataProvider() {
                            @Override
                            public String getPreference(String key) {
                                return AbstractExportWizard.this
                                        .getPreference(key);
                            }

                            @Override
                            public String getWorkspaceExportFolder() {
                                return AbstractExportWizard.this
                                        .getWorkspaceExportFolder();
                            }

                            @Override
                            public void setPreference(String key, String value) {
                                AbstractExportWizard.this.setPreference(key,
                                        value);
                            }
                        });
        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(windowMessage);
        pageIO.setEmptySelectionAnError(isEmptySelectionAnError());
        addPage(pageIO);
    }

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
     * alphabetical order.
     * 
     * @return A <code>ViewerSorter</code> to apply to the tree viewer.
     */
    protected ViewerSorter getSorter() {
        // Default implementation is to sort alphabetically. Subclass can
        // override this to provide a sorter.
        return new ViewerSorter();
    }

    /**
     * Get the tree viewer input. This is the workspace root by default.
     * Subclasses can override this to provide their own input.
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
    public void registerSubTask(ExportSubTask sub) {
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
     * @param szInputFileName
     * @return String
     */
    protected String getOutputFileName(String szInputFileName) {
        String szFileName = szInputFileName;

        // Get the output file extension
        if (outputFileExt == null) {
            outputFileExt = getExportFileExt();
        }

        if (outputFileExt != null && outputFileExt.length() > 0) {

            // Find the last stop and replace the text after that with the given
            // file extension
            szFileName = szFileName.substring(0, szFileName.lastIndexOf('.'));
            szFileName += "." + outputFileExt; //$NON-NLS-1$
        }

        return szFileName;
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

        final Display display = getShell().getDisplay();
        IStructuredSelection selectedFiles = getSelectedFiles();

        // calculate the number of tasks to set for the progress
        // monitor
        int monitorTasks = selectedFiles.size();

        // If we have subtasks then multiply the task with this
        // number
        monitorTasks += (selectedFiles.size() * subTasks.size());

        monitor.beginTask(Messages.AbstractExportWizard_exportingTask_title,
                monitorTasks);

        // Set the overwrite flag to a default value of overwrite
        overwriteDestFile = OverwriteStatus.FILE;

        InputStream inStream = null;
        OutputFile outputFile = null;
        try {
            ImportExportTransformer transformer = getImportExportTransformer();

            for (Iterator<?> iter = selectedFiles.iterator(); iter.hasNext();) {
                Object obj = iter.next();

                if (monitor.isCanceled()) {
                    // User selected to cancel
                    break;
                }

                if (obj instanceof IFile) {
                    IFile xpdlFile = (IFile) obj;

                    /*
                     * If the overwrite file message was shown and NO selected
                     * then the value needs to be reset back to default,
                     * otherwise no further files will be processed
                     */
                    if (overwriteDestFile == OverwriteStatus.NO)
                        overwriteDestFile = OverwriteStatus.FILE;

                    // Update progress monitor
                    monitor.subTask(xpdlFile.getName());

                    // Get the output file
                    outputFile = getOutputFile(xpdlFile);

                    if (outputFile != null) {
                        // If file exists and the user hasn't selected to
                        // overwrite all files then show dialog
                        if (outputFile.exists()
                                && overwriteDestFile != OverwriteStatus.ALL_FILES) {
                            final OutputFile outFile = outputFile;
                            // Ask user if the existing file can be
                            // overwritten
                            display.syncExec(new Runnable() {
                                @Override
                                public void run() {
                                    overwriteDestFile =
                                            overwriteFileMessageDialog(outFile);
                                }
                            });
                        }

                        // Check whether we can process the file
                        if (overwriteDestFile.canOverwrite()) {

                            // Process any subtasks
                            if (subTasks != null) {
                                for (ExportSubTask task : subTasks) {
                                    // Each subtask is ONE tick of main task.
                                    task.perform(new SubProgressMonitor(
                                            monitor, 1), xpdlFile, outputFile
                                            .getFile());
                                }
                            }

                            // Sub-tasks may have reset the subtask name.
                            monitor.subTask(xpdlFile.getName());

                            // Transform
                            transformer
                                    .performTransformation(new SubProgressMonitor(
                                            monitor, 1),
                                            xpdlFile.getContents(),
                                            outputFile.getOutputStream());

                            // Close the output file
                            outputFile.close();

                            // Close the streams
                            try {
                                if (inStream != null) {
                                    inStream.close();
                                    inStream = null;
                                }
                            } catch (IOException e) {
                                // Do nothing, closing streams.
                            }

                            monitor.worked(1);
                        }

                        // If the overwrite file dialog was shown and the
                        // user cancelled then throw interrupted exception
                        // to stop the export process
                        if (overwriteDestFile == OverwriteStatus.CANCEL) {
                            throw new OperationCanceledException();
                        }
                    }
                }
            }
            transformer = null;

        } catch (Exception e) {
            if (isUserCancelledException(e)) {
                throw new OperationCanceledException();
            }
            // If this is a CoreException or an InterruptedException then just
            // throw them, otherwise wrap exception in CoreException.
            else if (e instanceof CoreException) {
                throw (CoreException) e;
            } else if (e instanceof InterruptedException) {
                throw (InterruptedException) e;
            } else {
                if (e.getCause() instanceof CoreException) {
                    throw (CoreException) e.getCause();
                } else {
                    throw new CoreException(new Status(IStatus.ERROR,
                            XpdResourcesUIActivator.ID, IStatus.OK,
                            e.getLocalizedMessage(), e));
                }
            }

        } finally {
            // Close all streams
            try {
                if (inStream != null)
                    inStream.close();
            } catch (IOException e) {
                // Do nothing, closing stream
            }

            /*
             * If the export destination is the workspace then re-sync the
             * workspace with the file system if sub-tasks were executed. This
             * is needed as the sub tasks may have added more resources to the
             * export destination.
             */
            if (subTasks != null && outputFile != null) {
                IContainer container = outputFile.getParentIContainer();

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
     * The subclass can override this if they wish to do something like caching
     * the ImportExportTransformer (effectively keeping and precompiling it).
     * 
     * @return The ImportExportTransformer
     * @throws SAXException
     * @throws IOException
     * @throws TransformerConfigurationException
     */
    protected ImportExportTransformer getImportExportTransformer()
            throws TransformerConfigurationException, IOException, SAXException {
        return new ImportExportTransformer(this);
    }

    /**
     * SAX parser / transformer has started wrapping up
     * InterruptedException/OperationCanceledException so we need to unravel
     * these in order to find whether the given exception is a user presed
     * cancel operation.
     * 
     * @param e
     * @return true if the given exception is a user cancelled operation
     *         exception of some kind (or a wrapped one).
     */
    private boolean isUserCancelledException(Exception e) {
        Set<Exception> alreadyProcessed = new HashSet<Exception>();

        while (e != null) {
            // Make sure we cannot go round in circles.
            if (alreadyProcessed.contains(e)) {
                break;
            }

            alreadyProcessed.add(e);

            if (e instanceof OperationCanceledException
                    || e instanceof InterruptedException) {
                return true;
            }

            if (e instanceof SAXException) {
                e = ((SAXException) e).getException();
            } else if (e instanceof TransformerException
                    && ((TransformerException) e).getException() instanceof Exception) {
                e = (Exception) ((TransformerException) e).getException();
            } else {
                e = null;
            }
        }

        return false;
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
    private OutputFile getOutputFile(IFile inputFile) throws CoreException {
        OutputFile outputFile = null;
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

            createFolders(folder);

            currentOutputFolder = folder.getLocation().toOSString();

            outputFile = new OutputFile(folder.getFile(outputFileName));

        } else /* export to external path */{
            IPath destPath = getDestinationPath();

            if (destPath != null) {
                currentOutputFolder = destPath.toString();
                IPath path = destPath.append(outputFileName);
                outputFile = new OutputFile(path.toFile());
            }
        }

        currentInputFile = inputFile;

        return outputFile;
    }

    /**
     * Get the last output folder set by getOutputFile() as op sys string
     * 
     * @return
     */
    public String getOSOutputFolder() {
        return currentOutputFolder;
    }

    /**
     * Get the current input file.
     * 
     * @return
     */
    public IFile getCurrentInputFile() {
        return currentInputFile;
    }

    /**
     * Create project export folders in the workspace
     * 
     * @param folder
     * @throws CoreException
     */
    protected void createFolders(IFolder folder) throws CoreException {
        // Refresh the folder to align it with the file system
        if (!folder.isSynchronized(IResource.DEPTH_ONE)) {
            folder.refreshLocal(IResource.DEPTH_ONE, null);
        }

        if (!folder.exists()) {
            IContainer parent = folder.getParent();

            if (parent instanceof IFolder) {
                IFolder parentFolder = (IFolder) parent;
                // Create parent folder
                createFolders(parentFolder);
            }
            // Create current folder
            folder.create(true, true, null);
        }
    }

    /**
     * Show the overwrite file message dialog
     * 
     * @param outputFile
     * @return <code>{@link OverwriteStatus}</code> value of the selection made
     *         in the dialog
     */
    protected OverwriteStatus overwriteFileMessageDialog(OutputFile outputFile) {

        String msg =
                String.format(Messages.AbstractExportWizard_destFileExistsDlg_message,
                        outputFile.getName(),
                        outputFile.getPath().toString());

        OverwriteFileMessageDialog dialog =
                new OverwriteFileMessageDialog(getShell(),
                        Messages.AbstractExportWizard_destFileExistsDlg_title,
                        msg);

        return dialog.getOverwriteStatus(dialog.open());
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getSystemId()
     */
    @Override
    public String getSystemId() {
        // Subclasses should override this if they intend to provide a system id
        return null;
    }

    /**
     * Represents the output file - this could either be a <code>File</code> or
     * an <code>IFile</code>. If the export destination is set to the export
     * folder in the project then this will be an <code>IFile</code>. This class
     * provides common methods to manipulate either type of object.
     */
    private class OutputFile {
        private final Object oFile;

        private File file = null;

        private OutputStream outStream = null;

        public OutputFile(Object oFile) {
            this.oFile = oFile;
        }

        /**
         * Get the name of the file
         * 
         * @return Name of file
         */
        public String getName() {
            String name = null;

            if (getFile() != null) {
                name = getFile().getName();
            }

            return name;
        }

        /**
         * Get the path to this file
         * 
         * @return
         */
        public IPath getPath() {
            IPath path = null;

            if (oFile instanceof IFile) {
                path = ((IFile) oFile).getFullPath();
            } else if (oFile instanceof File) {
                path = new Path(((File) oFile).getAbsolutePath());
            }

            // Remove the file name from the path
            if (path != null && path.segmentCount() > 0) {
                path = path.removeLastSegments(1);
            }

            return path;
        }

        /**
         * Check if the output file exists
         * 
         * @return
         * @throws CoreException
         */
        public boolean exists() throws CoreException {
            boolean doesExist = false;

            if (oFile != null) {
                if (oFile instanceof IFile) {
                    IFile ifile = (IFile) oFile;
                    // Make sure the file is in synch with the file system
                    if (!ifile.isSynchronized(IResource.DEPTH_ZERO)) {
                        ifile.refreshLocal(IResource.DEPTH_ZERO, null);
                    }

                    doesExist = ifile.exists();

                } else if (oFile instanceof File) {
                    doesExist = ((File) oFile).exists();
                }
            }

            return doesExist;
        }

        /**
         * Returns the output stream of the file
         * <p>
         * <b>NOTE:</b><br/>
         * If this output file is an <code>IFile</code> then the
         * <code>ByteArrayOutputStream</code> will be returned. This is because
         * the <code>IFile</code> will need to be created (using
         * IFile.create(...)). After the output stream has been populated the
         * method <code>close()</code> of this class should be called. This will
         * take care of creating IFile (if the output file is an IFile) and will
         * close the output stream.
         * </p>
         * 
         * @return The following will be returned depending on the type of the
         *         output file:
         *         <ul>
         *         <li><code>File</code> - <code>FileOutputStream</code> will be
         *         returned</li>
         *         <li><code>IFile</code> - <code>ByteArrayOutputStream</code>
         *         will be returned</li>
         *         </ul>
         * @throws FileNotFoundException
         */
        public OutputStream getOutputStream() throws FileNotFoundException {

            if (outStream != null) {
                // Close the output stream.
                try {
                    outStream.close();
                    outStream = null;
                } catch (IOException e) {
                    // Do nothing as we are closing output stream
                }
            }

            if (oFile != null) {
                if (oFile instanceof File) {
                    outStream = new FileOutputStream((File) oFile);
                } else if (oFile instanceof IFile) {
                    outStream = new ByteArrayOutputStream();
                }
            }

            return outStream;
        }

        /**
         * Will return the <code>IContainer</code> if this output file is an
         * <code>IFile</code>
         * 
         * @return <code>IContainer</code> if the output file is an
         *         <code>IFile</code>, otherwise it will return <b>null</b>.
         */
        public IContainer getParentIContainer() {
            IContainer container = null;

            if (oFile instanceof IFile) {
                container = ((IFile) oFile).getParent();
            }

            return container;
        }

        /**
         * Close the output stream that was created. Also, if this OutputFile
         * represents an IFile then it get's created.
         * 
         * @throws CoreException
         */
        public void close() throws CoreException {
            if (oFile != null) {
                // If this is an IFile then create it
                if (oFile instanceof IFile) {
                    IFile iFile = (IFile) oFile;
                    if (outStream != null) {
                        if (outStream instanceof ByteArrayOutputStream) {
                            ByteArrayOutputStream byteOutStream =
                                    (ByteArrayOutputStream) outStream;
                            ByteArrayInputStream byteInStream =
                                    new ByteArrayInputStream(
                                            byteOutStream.toByteArray());

                            // If the ifile exists then update it's content,
                            // otherwise create it
                            if (iFile.exists()) {
                                iFile.setContents(byteInStream,
                                        false,
                                        true,
                                        null);
                            } else {
                                iFile.create(byteInStream, false, null);
                            }
                        }
                    }
                }

                // Close the output stream
                try {
                    outStream.close();
                } catch (IOException e) {
                    // Do nothing, closing stream
                }
                outStream = null;
            }
        }

        /**
         * Get the File
         * 
         * @return The output file in the file system
         */
        protected File getFile() {

            // Convert object to file
            if (file == null && oFile != null) {
                if (oFile instanceof IFile) {
                    file = ((IFile) oFile).getLocation().toFile();
                } else if (oFile instanceof File) {
                    file = (File) oFile;
                }
            }

            return file;
        }
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider3#getCharsetEncoding()
     * 
     * @return
     */
    @Override
    public String getCharsetEncoding() {
        return "UTF-8"; //$NON-NLS-1$
    }

    protected IStructuredSelection getInitialSelection() {
        return initialSelection;
    }

    protected String getInitialRootExportFolder() {
        return "/" + Messages.AbstractExportWizard_exportFolder_label; //$NON-NLS-1$
    }

    protected boolean isEmptySelectionAnError() {
        return false;
    }

    protected ExportWizardPage getPageIO() {
        return pageIO;
    }

    protected void setPageIO(ExportWizardPage pageIO) {
        this.pageIO = pageIO;
    }

}
