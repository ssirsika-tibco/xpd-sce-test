/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.process.om.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.tibco.xpd.process.om.internal.Messages;
import com.tibco.xpd.process.om.wizards.pages.XPDLSelectionWizardPageIO;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider3;

/**
 * This is an abstract wizard class that provides a one page wizard to refactor
 * or import selected resources to a chosen destination.
 * 
 * @see org.eclipse.jface.wizard.Wizard
 * 
 * @author glewis
 * 
 */
public abstract class AbstractXPDLSelectionWizard extends Wizard implements
        IExportWizard, ITransformationStylesheetsProvider3 {

    // Wizard page for selecting xpdl files and refactor destination
    protected XPDLSelectionWizardPageIO pageIO = null;

    protected IStructuredSelection initialSelection = null;

    private String outputFileExt;

    protected String windowMessage = Messages.AbstractRefactorXPDLToOMWizard_default_message;

    private final String currentOutputFolder = ""; //$NON-NLS-1$

    private final IFile currentInputFile = null;

    protected boolean bIsDestinationRequired;

    private final String operationCompleteTitle;

    private final String operationCompleteMessage;

    protected boolean isRunnable = true;

    /**
     * Constructor.
     */
    public AbstractXPDLSelectionWizard(boolean bIsDestinationRequired,
            String operationCompleteTitle, String operationCompleteMessage) {
        super();
        this.bIsDestinationRequired = bIsDestinationRequired;
        this.operationCompleteTitle = operationCompleteTitle;
        this.operationCompleteMessage = operationCompleteMessage;
        setNeedsProgressMonitor(true);
    }

    /**
     * Get the file extension to apply to the refactored file.
     * 
     * @return String
     */
    public abstract String getRefactorFileExt();

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
     * Set the default message to appear in the wizard page.
     * 
     * @param message
     */
    public void setWindowMessage(String message) {
        windowMessage = message;
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
    protected final IFolder getDestinationFolder() {
        if (pageIO != null) {
            return pageIO.getDestinationFolder();
        } else {
            throw new NullPointerException(
                    Messages.AbstractRefactorXPDLToOMWizard_refactorIOPageNotLoaded_message);
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
            throw new NullPointerException(
                    Messages.AbstractRefactorXPDLToOMWizard_refactorIOPageNotLoaded_message);
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
        MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION
                | SWT.OK | SWT.CANCEL);
        messageBox
                .setText(Messages.AbstractXPDLSelectionWizard_projectRefDialog_title);
        messageBox
                .setMessage(Messages.AbstractXPDLSelectionWizard_projectRefDialog_message);
        if (messageBox.open() == SWT.OK) {
            // Perform operation in a workspace modify operation as the
            // workspace may be affected
            WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
                @Override
                protected void execute(IProgressMonitor monitor)
                        throws CoreException, InvocationTargetException,
                        InterruptedException {
                    IStructuredSelection selectedFiles = getSelectedFiles();

                    if (selectedFiles != null && !selectedFiles.isEmpty()) {
                        try {
                            // Perform the export
                            performOperation(monitor);
                        } catch (InterruptedException e) {
                            throw new CoreException(new Status(IStatus.ERROR,
                                    XpdResourcesUIActivator.ID, IStatus.OK, e
                                            .getLocalizedMessage(), e));
                        } catch (Exception e) {
                            // Throw core exception
                            throw new CoreException(new Status(IStatus.ERROR,
                                    XpdResourcesUIActivator.ID, IStatus.OK, e
                                            .getLocalizedMessage(), e));
                        }
                    }
                }
            };

            try {
                getContainer().run(true, true, operation);

                if (isRunnable) {
                    MessageDialog.openInformation(getShell(),
                            operationCompleteTitle, operationCompleteMessage);
                }
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();

                if (cause != null) {
                    ErrorDialog
                            .openError(
                                    getShell(),
                                    Messages.AbstractRefactorXPDLToOMWizard_refactorErrDialog_title,
                                    Messages.AbstractRefactorXPDLToOMWizard_refactorErrDialog_message,
                                    new Status(IStatus.ERROR,
                                            XpdResourcesUIActivator.ID,
                                            IStatus.OK, cause
                                                    .getLocalizedMessage(),
                                            cause));
                }
            } catch (InterruptedException e) {
                // Process interrupted so don't close the wizard
                ret = false;
            }
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
        pageIO = new XPDLSelectionWizardPageIO(initialSelection, getInput(),
                getSorter(), getFilters(), bIsDestinationRequired);
        pageIO.setTitle(getWindowTitle());
        pageIO.setDescription(windowMessage);
        addPage(pageIO);
    }

    /**
     * Get the <code>ViewerFilter</code> objects to apply to the tree viewer.
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
    private ViewerSorter getSorter() {
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
    private Object getInput() {
        // Default implementation is to set the workspace root, subclasses can
        // override this to provide their own input.
        return ResourcesPlugin.getWorkspace().getRoot();
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
            IPreferenceStore pStore = XpdResourcesUIActivator.getDefault()
                    .getPreferenceStore();
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
            IPreferenceStore pStore = XpdResourcesUIActivator.getDefault()
                    .getPreferenceStore();
            szRet = pStore.getString(getClass().getName() + szKey);
        }

        return szRet;
    }

    /**
     * This method is called when the output file is being saved. It will strip
     * the file extension of the given input file and replace it with the given
     * output file extension (using <code>{@link #getRefactorFileExt()}</code>).
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
            outputFileExt = getRefactorFileExt();
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
     * Set the system identifier for the input source being transformed. This
     * will be used in resolving relative URL references in the input source.
     * 
     * @see com.tibco.xpd.importexport.ImportExportTransformer.ITransformationStylesheetsProvider#getSystemId()
     */
    public String getSystemId() {
        // Subclasses should override this if they intend to provide a system id
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.ITransformationStylesheetsProvider3#getCharsetEncoding()
     * 
     * @return
     */
    public String getCharsetEncoding() {
        return "UTF-8"; //$NON-NLS-1$
    }

    public String getDefaultOMName() {
        return pageIO.getDefaultOMName();
    }

}
