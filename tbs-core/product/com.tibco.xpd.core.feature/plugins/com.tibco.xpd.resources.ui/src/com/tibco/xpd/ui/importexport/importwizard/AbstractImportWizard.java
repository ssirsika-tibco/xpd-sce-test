/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.importwizard;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.navigator.CommonNavigator;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.importexport.importwizard.pages.FilteredFSElement;
import com.tibco.xpd.ui.importexport.importwizard.pages.ImportWizardPageIO;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationCharsetEncodingProvider;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider;

/**
 * This is an abstract import wizard that provides a one page wizard to import a
 * file (import transforms the selected resource using the given xsl file - this
 * can also be multiple xsl files). The wizard page allows the user to select
 * the resources to import and destination folder in which to create the
 * imported resource.
 * <p>
 * The following abstract methods have to be implemented:
 * <ul>
 * <li><code>{@link #getImportFileExtension()}</code> - Get the file extension
 * that will be used for the imported files.</li>
 * <li><code>{@link ITransformationStylesheetsProvider#getXslts()}</code> - To
 * provide the xslts for the import.
 * <li>All methods defined in interface <code>IImportWizardPageProvider</code>
 * will also have to implemented.</li>
 * </ul>
 * </p>
 * <p>
 * The following methods can be called to customize the wizard (see methods for
 * more details):
 * <ul>
 * <li><code>{@link #setWindowTitle(String)}</code> - Set the wizard's title.</li>
 * <li><code>{@link #setWindowMessage(String)}</code> - Set the wizard's
 * message.</li>
 * <li><code>{@link #registerSubTask(ImportSubTask)}</code> - Register
 * <code>{@link ImportSubTask}</code> objects that will be called after import
 * of each individual resource to carry out any additional tasks.</li>
 * <li><code>{@link #registerPostImportTask(ImportSubTask)}</code> - Register
 * <code>{@link PostImportTask}</code> objects that will be called for eazch
 * output file after ALL files have been created (and had sub-tasks executed).</li>
 * <li><code>{@link #setPreference(String, String)}</code> - To store values in
 * the preference store</li>
 * <li><code>{@link #setWizardBannerImage(ImageDescriptor)}</code> - Set the
 * wizard banner image.</li>
 * </ul>
 * </p>
 * <p>
 * The following methods can be overridden to customize the wizard (see methods
 * for more details):
 * <ul>
 * <li><code>{@link #getOutputFileName(File)}</code> - This method is called
 * during import to determine the name of the imported file.</li>
 * <li><code>{@link #performOperation(IProgressMonitor)}</code> - Perform the
 * import. The default implementation uses XSLT transformation to import the
 * selected resources.</li>
 * <li><code>{@link #getSystemId()}</code> - If the xslt contains relative URL
 * references (e.g. DTD reference) then a system id has to be provided.</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.jface.wizard.Wizard
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractImportWizard extends Wizard implements
        IImportWizard, ITransformationStylesheetsProvider,
        ITransformationCharsetEncodingProvider, IImportWizardPageProvider {

    protected IStructuredSelection selection = null;

    protected ImportWizardPageIO pageIO = null;

    // Subtasks list
    private List<ImportSubTask> subTasks = new ArrayList<ImportSubTask>();

    // Post import tasks.
    private List<PostImportTask> postImportTasks =
            new ArrayList<PostImportTask>();

    private final List<IFile> outputFiles;

    /**
     * Overwrite resource choices
     */
    protected enum OverwriteStatus {
        NO(1), // Don't overwrite the file
        FILE(2), // Overwrite the file
        ALL_FILES(3), // Overwrite all files in future
        CANCEL(4); // Cancel selected in overwrite dialog

        // Default value will be FILE.
        private int value = 2;

        private OverwriteStatus(int value) {
            this.value = value;
        }

        public void setDefault() {
            value = FILE.value;
        }

        /**
         * Can ovewrite if the value is set to FILE or ALL_FILES
         * 
         * @return
         */
        public boolean canOverwrite() {
            return (this == FILE || this == ALL_FILES);
        }

        /**
         * Check if cancel was selected in the overwrite dialog
         * 
         * @return
         */
        public boolean isCancelled() {
            return this == CANCEL;
        }
    }

    private String windowMessage =
            Messages.AbstractImportWizard_default_message;

    // Image for the wizard banner
    protected ImageDescriptor imgWizardBanner = null;

    private CommonNavigator navigator;

    // List of items imported. This list will be used to select
    // the items imported in the navigator tree
    private List<Object> importedItems = new ArrayList<Object>();

    protected String currentTransformingInputCharEncoding = null;

    /**
     * Abstract import wizard that provides functionality to import a file.
     */
    public AbstractImportWizard() {
        super();
        setNeedsProgressMonitor(true);
        outputFiles = new ArrayList<IFile>();
    }

    /**
     * Get the initial selection passed to this wizard.
     * 
     * @return
     */
    protected IStructuredSelection getInitialSelection() {
        return selection;
    }

    /**
     * Get the list of files that were created by this import process.
     * 
     * @return list of files created, empty collection if none.
     * @since 3.5.10
     */
    public Collection<IFile> getOutputFiles() {
        return outputFiles;
    }

    /**
     * Get the list of resources selected for import.
     * 
     * @throws NullPointerException
     *             If the wizard page hasn't been loaded.
     * @return
     * @deprecated (since 3.0) Use {@link #getSelectedResources()} to get the
     *             list of resources selected for import.
     */
    @Deprecated
    @SuppressWarnings("unchecked")
    protected List<IResource> getSelectedFiles() {
        if (pageIO != null) {
            return pageIO.getSelectedFiles();
        }
        throw new NullPointerException("Wizard page not loaded."); //$NON-NLS-1$
    }

    /**
     * Get the selected resources for import.
     * 
     * @return list of <code>FilteredFSElement</code> objects
     * 
     * @since 3.0
     */
    @SuppressWarnings("unchecked")
    protected List<FilteredFSElement> getSelectedResources() {
        if (pageIO != null) {
            return pageIO.getSelectedFiles();
        }
        throw new NullPointerException("Wizard page not loaded."); //$NON-NLS-1$
    }

    /**
     * Get the destination folder of the import.
     * 
     * @throws NullPointerException
     *             If the wizard page hasn't been loaded.
     * @return <code>IFolder</code>
     * 
     * @deprecated (since 3.0) Use {@link #getDestinationContainer()} to get the
     *             destination selection.
     */
    @Deprecated
    protected IFolder getDestinationFolder() {
        if (pageIO != null) {
            return pageIO.getDestinationFolder();
        }
        throw new NullPointerException("Wizard page not loaded."); //$NON-NLS-1$
    }

    /**
     * Get the destination container of the import.
     * 
     * @throws NullPointerException
     *             If the wizard page hasn't been loaded.
     * @return <code>IContainer</code>
     * 
     * @since 3.0
     */
    protected IContainer getDestinationContainer() {
        if (pageIO != null) {
            return pageIO.getDestinationContainer();
        }
        throw new NullPointerException("Wizard page not loaded."); //$NON-NLS-1$
    }

    /**
     * Get the overwrite existing resources status selected in the wizard page.
     * 
     * @throws NullPointerException
     *             If the wizard page hasn't been loaded.
     * @return <code>OverwriteStatus.ALL_FILES</code> If all existing files
     *         should be overwritten, else it returns
     *         <code>OverwriteStatus.FILE</code>
     */
    protected OverwriteStatus getOverwriteStatus() {
        if (pageIO != null) {
            return pageIO.getOverwriteExistingResources() ? OverwriteStatus.ALL_FILES
                    : OverwriteStatus.FILE;
        }
        throw new NullPointerException("Wizard page not loaded."); //$NON-NLS-1$
    }

    /*
     * 
     * Don't override. Please use overridden #performOpeation(ProgressMonitor)
     * instead.
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    @Override
    public final boolean performFinish() {
        IStatus beforePerformFinish = beforePerformFinish();

        if (beforePerformFinish != null && !beforePerformFinish.isOK()) {
            return false;
        }

        boolean ret = true;
        final IContainer destination = getDestinationContainer();
        final List<FilteredFSElement> selectedResources =
                getSelectedResources();
        if (destination != null && destination.isAccessible()
                && selectedResources != null && !selectedResources.isEmpty()) {
            // Run operation to import
            WorkspaceModifyOperation operation =
                    new WorkspaceModifyOperation() {

                        @Override
                        protected void execute(IProgressMonitor monitor)
                                throws CoreException,
                                InvocationTargetException, InterruptedException {

                            // Make sure that the destination folder is in sync
                            // with
                            // the file system, if it doesn't then sync it
                            if (!destination
                                    .isSynchronized(IResource.DEPTH_ONE)) {
                                destination.refreshLocal(IResource.DEPTH_ONE,
                                        monitor);
                            }

                            // Perform the import
                            performOperation(monitor);
                        }

                    };

            // Run the operation

            try {
                getContainer().run(true, true, operation);
            } catch (InvocationTargetException e) {
                if (e.getCause() instanceof CoreException) {
                    CoreException exception = (CoreException) e.getCause();

                    ErrorDialog
                            .openError(getShell(),
                                    Messages.AbstractImportWizard_failedToImportDlg_title,
                                    Messages.AbstractImportWizard_failedToImportDlg_message,
                                    exception.getStatus());
                } else {
                    XpdResourcesUIActivator.getDefault().getLogger()
                            .error(e.getCause());
                }
            } catch (InterruptedException e) {
                // Operation cancelled so don't close the wizard
                ret = false;
            }

            // If items were selected then select them in the navigator
            if (ret && navigator != null && !importedItems.isEmpty()) {
                navigator.getCommonViewer()
                        .setSelection(new StructuredSelection(importedItems),
                                true);
            }
        } else {
            throw new NullPointerException(
                    Messages.AbstractImportWizard_importDestNotFound_error_message);
        }

        return ret;
    }

    /**
     * Method called at the beginning of performFinish(). It will give a chance
     * to apply UI changes to the data model stored in the wizard in the wizard
     * if needed.
     */
    protected IStatus beforePerformFinish() {
        // Default implementation do nothing. Override if needed.
        return null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    @Override
    public void addPages() {
        // Add the source/destination selection page
        pageIO = new ImportWizardPageIO(selection, this);

        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(getWindowMessage());

        // If wizard banner image specified then set it
        if (imgWizardBanner != null)
            pageIO.setImageDescriptor(imgWizardBanner);
        addPage(pageIO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
        IWorkbenchPage activePage =
                workbench.getActiveWorkbenchWindow().getActivePage();

        // Get common navigator if it was the source of this wizard's input
        if (activePage.getActivePart() instanceof CommonNavigator) {
            navigator = (CommonNavigator) activePage.getActivePart();
        }
    }

    /**
     * Register any subtasks required by the wizard. This tasks will run
     * post-import of each selected file (prior to import of next)
     * <p>
     * This is as opposed to {@link #registerPostImportTask(PostImportTask)}
     * which executes the tasks after all files have been imported.
     * 
     * @param sub
     *            - SubTask
     */
    public void registerSubTask(ImportSubTask sub) {
        subTasks.add(sub);
    }

    /**
     * Register a task that is to be performed on each output file <b>after
     * all</b> imports have completed (i.e. main transform and sub-tasks for all
     * files is finished).
     * <p>
     * This is as opposed to {@link #registerSubTask(ImportSubTask)} which
     * executes sub-tasks after each individual output file is created.
     * 
     * @param postImportTask
     * 
     * @since 01-Nov-12
     */
    public void registerPostImportTask(PostImportTask postImportTask) {
        postImportTasks.add(postImportTask);
    }

    /**
     * Store a preference key, value pair in the plugin
     * 
     * @param szKey
     * @param szValue
     */
    public void setPreference(String szKey, String szValue) {

        // Make sure all data is present
        if (szKey != null && szKey != "" && szValue != null) { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesUIActivator.getDefault().getPreferenceStore();
            pStore.setValue(getClass().getName() + szKey, szValue);
        }
    }

    /**
     * Get the value of the given preference key
     * 
     * @param szKey
     * @return String
     */
    public String getPreference(String szKey) {
        String szRet = null;

        // Make sure a key is provided
        if (szKey != null && szKey != "") { //$NON-NLS-1$
            IPreferenceStore pStore =
                    XpdResourcesUIActivator.getDefault().getPreferenceStore();
            szRet = pStore.getString(getClass().getName() + szKey);
        }

        return szRet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        boolean finish = super.canFinish();

        // If can finish then check that there is a transformer
        // If not, don't allow finish
        if (finish && (getXslts() == null || getXslts().length == 0))
            finish = false;

        return finish;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.importexport.utils.ImportExportTransformer.
     * ITransformationStylesheetsProvider#getSystemId()
     */
    @Override
    public String getSystemId() {
        // Default implementation - subclasses may override.
        return null;
    }

    /**
     * Set the message that will appear under the main title at the top of the
     * wizard pages
     * 
     * @param szMessage
     */
    protected void setWindowMessage(String szMessage) {
        windowMessage = szMessage;
    }

    /**
     * Get the window message.
     * 
     * @see #setWindowMessage(String)
     * 
     * @return The wizard message if set, <b>null</b> otherwise.
     */
    protected String getWindowMessage() {
        return windowMessage;
    }

    /**
     * Set the wizard banner icon. This will appear for each page in the wizard
     * 
     * @param img
     */
    protected void setWizardBannerImage(ImageDescriptor img) {
        imgWizardBanner = img;
    }

    /**
     * Get the destination file name. This will replace the file extension of
     * the given source file with '.xpdl'.<br>
     * This method can be overriden by the subclass to rename the imported
     * files.
     * 
     * @param srcFile
     * @return String
     */
    protected String getOutputFileName(File srcFile) {

        String szFileName = srcFile.getName();
        String szRetVal = null;

        // Find the file extension and replace with ".xpdl"
        szRetVal = szFileName.substring(0, szFileName.lastIndexOf('.') + 1);
        szRetVal += getImportFileExtension();

        return szRetVal;
    }

    /**
     * Get the import file extension. Do not include the extension separator.
     * 
     * @return Extension to apply to the imported file.
     */
    protected abstract String getImportFileExtension();

    /**
     * Perform the import. This will apply transformation to each selected file
     * to create the import file.
     * 
     * @param monitor
     * @throws CoreException
     * @throws InterruptedException
     */
    protected void performOperation(IProgressMonitor monitor)
            throws CoreException, InterruptedException {

        // If cancel was clicked in the dialog then quit here
        if (monitor.isCanceled()) {
            monitor.done();
            throw new InterruptedException();
        }

        FilteredFSElement fe;
        File inputFile;
        final List<FilteredFSElement> filesToImport = getSelectedResources();
        final IContainer destination = getDestinationContainer();
        OverwriteStatus overwriteStatus = getOverwriteStatus();
        FileInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream byteInputStream = null;

        try {
            /*
             * Sid SNA-1 - Fixed number of tasks to be done calculation which
             * was a bit bizarre before.
             */
            monitor.beginTask(Messages.AbstractImportWizard_importingTask_title,
                    filesToImport.size()
                            + (filesToImport.size() * subTasks.size())
                            + (filesToImport.size() * postImportTasks.size()));

            ImportExportTransformer transformer =
                    new ImportExportTransformer(this);

            // Process each selected file
            for (Iterator<FilteredFSElement> iter = filesToImport.iterator(); iter
                    .hasNext();) {
                fe = iter.next();

                boolean done = false;

                // If overwrite dialog was shown and NO selected then we need to
                // reset it to the default value, otherwise no further files
                // will be processed
                if (overwriteStatus == OverwriteStatus.NO) {
                    overwriteStatus = OverwriteStatus.FILE;
                }

                if (fe.getFileSystemObject() instanceof File) {
                    inputFile = (File) fe.getFileSystemObject();

                    // Update progress monitor
                    monitor.subTask(inputFile.getName());

                    // Get the destination file to create
                    final IFile outputFile =
                            destination.getFile(new Path(
                                    getOutputFileName(inputFile)));

                    // If the output file exists and not asked to overwrite
                    // all files prompt the user
                    if (outputFile.exists()
                            && overwriteStatus != OverwriteStatus.ALL_FILES) {
                        final OverwriteStatus status[] = new OverwriteStatus[1];

                        getShell().getDisplay().syncExec(new Runnable() {
                            @Override
                            public void run() {
                                status[0] =
                                        canOverwriteFile(outputFile.getName(),
                                                destination.getName());
                            }
                        });

                        overwriteStatus = status[0];

                        if (overwriteStatus == OverwriteStatus.CANCEL) {
                            break;
                        }
                    }

                    // If we can progress with transforming the file then do
                    // so
                    if (overwriteStatus.canOverwrite()) {
                        // Prepare the streams

                        //
                        // Save the input character encoding for the current
                        // input file.
                        // transformer.performTransformation() will call
                        // getInput/OutputCharsetEncoding() methods dring its
                        // processing.
                        currentTransformingInputCharEncoding =
                                getXmlEncoding(inputFile);

                        // System.out
                        // .println(
                        // "BEFORE...\n========================================="
                        // );
                        // outputNameAttribute(inputFile);

                        inputStream = new FileInputStream(inputFile);

                        outputStream = new ByteArrayOutputStream();

                        // Transform the file
                        transformer.performTransformation(inputStream,
                                outputStream);

                        ByteArrayInputStream resultInStream =
                                new ByteArrayInputStream(
                                        outputStream.toByteArray());

                        if (!outputFile.exists()) {
                            outputFile.create(resultInStream,
                                    IResource.FORCE,
                                    new NullProgressMonitor());
                        } else {
                            outputFile.setContents(resultInStream,
                                    IResource.FORCE,
                                    new NullProgressMonitor());

                            /*
                             * SID SIA-46: Sometimes (timing dependent)
                             * setContents doesn't cause a reload to happen (or
                             * at least not one then propagates out a working
                             * copy reloaded notification (probably because
                             * we're in a workspce mod op). So Force a reload on
                             * the working copy to ensure editors are closed
                             * etc.
                             */
                            WorkingCopy wc =
                                    WorkingCopyUtil.getWorkingCopy(outputFile);
                            if (wc != null) {
                                wc.reLoad();
                            }
                        }

                        // Close all streams
                        inputStream.close();
                        outputStream.close();

                        // System.out
                        // .println(
                        // "\nAFTER...\n========================================="
                        // );
                        // outputNameAttribute(outputFile.getLocation().toFile())
                        // ;
                        // System.out.println("");

                        /*
                         * Sid SNA-1 - move progress along for
                         * "finished import part" before processing sub-tasks
                         */
                        done = true;
                        monitor.worked(1);

                        // Process any subtasks
                        processSubTasks(new SubProgressMonitor(monitor,
                                subTasks.size()), inputFile, outputFile);

                        // Add file to the list of items imported
                        importedItems.add(outputFile);
                        outputFile.refreshLocal(IResource.DEPTH_ZERO, monitor);

                        outputFiles.add(outputFile);
                    }
                }

                if (!done) {
                    monitor.worked(1 + subTasks.size());
                }

                // If cancel was clicked in the dialog then stop process
                if (monitor.isCanceled()) {
                    break;
                }

            } // end for (Next import resource).

            /*
             * Process post-import tasks - these are performed per output file
             * after ALL files have been output and normal sub-tasks have been
             * performed.
             */
            processPostImportTasks(monitor);

            transformer = null;
        } catch (CoreException e) {
            throw e;
        } catch (Exception e) {
            if (e.getCause() instanceof CoreException) {
                throw (CoreException) e.getCause();
            } else {
                // Throw core exception
                throw generateCoreException(e);
            }
        } finally {
            // Close all streams
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                if (byteInputStream != null) {
                    byteInputStream.close();
                }
            } catch (IOException ioe) {
                ; // Do nothing as closing streams
            }

            monitor.done();
        }
    }

    /**
     * Process post-import tasks - these are performed per output file after ALL
     * files have been output and normal sub-tasks have been performed.
     * 
     * @param monitor
     * @throws CoreException
     */
    protected void processPostImportTasks(IProgressMonitor monitor)
            throws CoreException {
        /*
         * Prior to process the post import tasks we ensure that ALL files have
         * been indexed. If we don't do this then sub tasks that rely on indexes
         * / working copies of other files imported at the same time will not
         * work correctly if the order of processing does not follow the order
         * of dependency.
         * 
         * As fixing up dependencies etc is probably one of the main jobs that
         * will be done in post import processing then it makes sense to do this
         * here.
         */
        for (IFile outputFile : outputFiles) {
            /* Ensure working copy loaded (and hence re-index etc). */
            WorkingCopyUtil.getWorkingCopy(outputFile);
        }

        for (IFile outputFile : outputFiles) {
            processPostImportTasks(outputFile, new SubProgressMonitor(monitor,
                    postImportTasks.size()));
        }
    }

    /**
     * Process the post import tasks.
     * 
     * @param outputFile
     * @param monitor
     * @throws CoreException
     */
    private void processPostImportTasks(IFile outputFile,
            IProgressMonitor monitor) throws CoreException {
        try {
            if (postImportTasks.size() > 0) {
                monitor.beginTask("", postImportTasks.size()); //$NON-NLS-1$

                for (PostImportTask task : postImportTasks) {
                    task.perform(new SubProgressMonitor(monitor, 1), outputFile);
                }

                /*
                 * Save the working copy.
                 */
                WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(outputFile);
                if (wc != null) {
                    try {
                        wc.save();
                    } catch (IOException e) {
                        XpdResourcesPlugin.getDefault().getLogger().error(e);
                    }
                }

            }
        } finally {
            monitor.done();
        }

    }

    /**
     * Outputs the first Name attribute in the give file a sa list of bytes
     * (might be useful for character set checking.
     * 
     * @param tstStream
     * @throws IOException
     */
    private void outputNameAttribute(File inputFile) throws IOException {

        byte[] data = new byte[10000];

        FileInputStream tstStream = new FileInputStream(inputFile);

        int lenIn = tstStream.read(data);
        boolean inName = false;
        for (int i = 0; i < lenIn - 7; i++) {
            if (inName) {
                if (data[i] == '\"') {
                    break;
                }

                System.out.println("  :  " + (int) data[i] + "  ("
                        + (char) data[i] + ")");
            }
            if (data[i] == 'N' && data[i + 1] == 'a' && data[i + 2] == 'm'
                    && data[i + 3] == 'e' && data[i + 4] == '='
                    && data[i + 5] == '\"') {
                inName = true;
                i += 5;
            }
        }

        tstStream.close();
    }

    /**
     * Run any registered sub tasks. This will be executed after every selected
     * resource is imported.
     * 
     * @param monitor
     * @param inFile
     * @param outFile
     * @throws CoreException
     */
    private void processSubTasks(SubProgressMonitor monitor, File inFile,
            IFile outFile) throws CoreException {
        try {
            if (subTasks.size() > 0) {
                monitor.beginTask("", subTasks.size());

                for (ImportSubTask task : subTasks) {
                    task.perform(new SubProgressMonitor(monitor, 1),
                            inFile,
                            outFile);
                }
            }
        } finally {
            monitor.done();
        }
    }

    /**
     * Display the overwrite message dialog
     * 
     * @param szOutputFileName
     * @param szProjectName
     * @return
     */
    protected OverwriteStatus canOverwriteFile(String szOutputFileName,
            String szProjectName) {
        OverwriteStatus status = OverwriteStatus.NO;

        // Create buttons list for the message dialog
        String szButtonsList[] =
                new String[] { IDialogConstants.YES_LABEL,
                        IDialogConstants.YES_TO_ALL_LABEL,
                        IDialogConstants.NO_LABEL,
                        IDialogConstants.CANCEL_LABEL };
        String szMsg =
                String.format(Messages.AbstractImportWizard_OverwriteFile_message,
                        szOutputFileName,
                        szProjectName);

        // Create message dialog with Yes, Yes To All, No and Cancel buttons
        MessageDialog ms =
                new MessageDialog(getShell(),
                        Messages.AbstractImportWizard_fileExistsDlg_title,
                        null, szMsg, MessageDialog.QUESTION, szButtonsList, 0);

        int nRet = ms.open();

        // Check return value and set our flag accordingly
        if (szButtonsList[nRet].equals(IDialogConstants.YES_LABEL))
            status = OverwriteStatus.FILE;
        else if (szButtonsList[nRet].equals(IDialogConstants.YES_TO_ALL_LABEL))
            status = OverwriteStatus.ALL_FILES;
        else if (szButtonsList[nRet].equals(IDialogConstants.CANCEL_LABEL))
            status = OverwriteStatus.CANCEL;
        else
            // No clicked
            status = OverwriteStatus.NO;

        return status;
    }

    /**
     * Create a <code>CoreException</code> to wrap the given
     * <code>Throwable</code> object
     * 
     * @param throwable
     * @return
     */
    protected CoreException generateCoreException(Throwable throwable) {
        return new CoreException(new Status(IStatus.ERROR,
                XpdResourcesUIActivator.ID, IStatus.OK,
                throwable.getLocalizedMessage(), throwable));
    }

    @Override
    public String getCharsetEncodingForStream(
            EncodingForStream encodingForStream) {
        if (EncodingForStream.INPUT.equals(encodingForStream)) {
            // Base the input character set encoding on the value in the
            // <?output
            // header of the current input file.
            if (currentTransformingInputCharEncoding != null
                    && currentTransformingInputCharEncoding.length() > 0) {
                return currentTransformingInputCharEncoding;
            }

            return null;
        }

        // For output and xslts assume UTF-8 for now (subclass can always
        // override if it wants to.
        return "UTF-8";
    }

    /**
     * Extract and return the encoding specified at the top of the given xmp
     * file.
     * <p>
     * Could not get any xml parser we normally use to return encoding
     * consistently especially when source doc is early iProcess's "iso-8859-1"
     * 
     * @param inputFile
     * @return encoding.
     */
    public static String getXmlEncoding(File inputFile) {
        //
        // Apologies for the Raw reading but I can't find any standard xml
        // parser that will return iProcess pre v11.1 "iso-8859-1"
        //
        // FIRST We'll make a guess that the file will be in a single / mixed
        // encoing (such as iso or UTF-8).
        //
        // If we fail to find that we'll try again in UTF-16 format, if that
        // fails we'll revert to UTF-16).
        String encoding = null;

        FileInputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader lineReader = null;

        try {
            inputStream = new FileInputStream(inputFile);
            inputStreamReader = new InputStreamReader(inputStream);
            lineReader = new BufferedReader(inputStreamReader);

            String line = lineReader.readLine();

            if (line != null) {
                String encodingMarkerString = "encoding=\"";
                int idx = line.indexOf(encodingMarkerString);
                if (idx >= 0) {
                    String rest =
                            line.substring(idx + encodingMarkerString.length());
                    idx = rest.indexOf("\"");

                    if (idx >= 0) {
                        encoding = rest.substring(0, idx);
                    }
                }
            }

        } catch (Exception e) {
        } finally {

            if (lineReader != null) {
                try {
                    lineReader.close();
                } catch (IOException e) {
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

        if (encoding == null) {
            //
            // Couldn't find encoding; this may be because the file is in UTF-16
            // format.
            try {
                inputStream = new FileInputStream(inputFile);
                inputStreamReader =
                        new InputStreamReader(inputStream, "UTF-16"); //$NON-NLS-1$
                lineReader = new BufferedReader(inputStreamReader);

                String line = lineReader.readLine();

                if (line != null) {
                    String encodingMarkerString = "encoding=\""; //$NON-NLS-1$
                    int idx = line.indexOf(encodingMarkerString);
                    if (idx >= 0) {
                        String rest =
                                line.substring(idx
                                        + encodingMarkerString.length());
                        idx = rest.indexOf("\""); //$NON-NLS-1$

                        if (idx >= 0) {
                            encoding = rest.substring(0, idx);
                        }
                    }
                }

            } catch (Exception e) {
            } finally {

                if (lineReader != null) {
                    try {
                        lineReader.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        if (encoding == null) {
            // If all else fails then resort to utf-8.
            encoding = "UTF-8"; //$NON-NLS-1$
        }

        return encoding;
    }

}
