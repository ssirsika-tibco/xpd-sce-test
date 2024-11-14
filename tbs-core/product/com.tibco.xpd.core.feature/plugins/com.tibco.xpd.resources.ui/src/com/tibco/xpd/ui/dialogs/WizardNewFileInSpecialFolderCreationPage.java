/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.dialogs;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.GovernanceStateService;

/**
 * New file in special folder creation page. This is similar to the
 * {@link WizardNewFileCreationPage} but shows the common navigator view instead
 * of the default view (general functionality is similar in most cases).
 * <p>
 * Note that this class does not extend {@link WizardNewFileCreationPage} but
 * extends {@link WizardPage} directly.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class WizardNewFileInSpecialFolderCreationPage extends AbstractXpdWizardPage
        implements Listener {

    /**
     * Tries to obtain project's special folder selection for passed selection.
     * It will use first object in selection to obtain context project and try
     * to find specialFolder of specified specialFolderKind and
     * preferedFolderName if it's possible. If there is no special folder of the
     * specialFolderKind and preferedFolderName (or prefferedFolderName wasn't
     * specified) then one of the found specialFolderKind will be returned. If
     * project doesn't contain special folders of specialFolderKind then the
     * input selection will be returned.
     * 
     * @param selection
     *            the selection passed.
     * @param specialFolderKind
     *            the kind of special folder we would like to be selected.
     * @param preferedFolderName
     *            preferred name of the special folder or <code>null</code> if
     *            there is no such preference.
     * @return the selection containing preferably special folder of
     *         specialFolderKind and preferedFolderName or any special folder of
     *         specialFolderKind or input selection.
     */
    protected static IStructuredSelection getSpecialFolderSelection(
            IStructuredSelection selection, String specialFolderKind,
            String preferedFolderName) {
        Object el = selection.getFirstElement();
        IProject project = null;
        if (el instanceof IResource && ((IResource) el).getProject() != null) {
            project = ((IResource) el).getProject();
        } else if (el instanceof IAdaptable) {
            Object adapter = ((IAdaptable) el).getAdapter(IResource.class);
            if (adapter instanceof IResource) {
                project = ((IResource) adapter).getProject();
            }
        }

        // Try to load adapter contributed by extensions.
        if (project == null && el != null) {
            Object adapter =
                    Platform.getAdapterManager().loadAdapter(el,
                            IResource.class.getName());
            if (adapter instanceof IResource) {
                project = ((IResource) adapter).getProject();
            }
        }

        if (project != null) {
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                EList<SpecialFolder> foldersOfKind =
                        config.getSpecialFolders()
                                .getFoldersOfKind(specialFolderKind);
                if (preferedFolderName != null) {
                    for (SpecialFolder sf : foldersOfKind) {
                        if (sf.getFolder() != null
                                && preferedFolderName.equals(sf.getFolder()
                                        .getName())) {
                            return new StructuredSelection(sf);
                        }
                    }
                }
                if (foldersOfKind.size() > 0) {
                    return new StructuredSelection(foldersOfKind.iterator()
                            .next());
                }
            }
        }
        return selection;

    }

    private final IStructuredSelection selection;

    private Text fileNameText;

    /**
     * Default file name
     */
    private String defaultFileName;

    /**
     * Default file extension
     */
    private String defaultFileExtension;

    private SpecialFolderContainerGroup containerGroup;

    private IFile newFile;

    private ISelectionValidator validator;

    private String newFileLabel;

    private int overwriteSeverity = IStatus.ERROR;

    private final List<ViewerFilter> filters;

    private boolean validateFileExtension;
    
	private Composite fileGroup;

    /**
     * Constructor. New file in special folder creation page.
     * 
     * @param pageName
     *            name of the page
     * @param selection
     *            current workspace selection.
     */
    public WizardNewFileInSpecialFolderCreationPage(String pageName,
            IStructuredSelection selection) {
        super(pageName);
        this.selection = selection;
        setPageComplete(false);
        filters = new ArrayList<ViewerFilter>();
    }

    /**
     * Add a filter to filter the contents of the tree viewer.
     * 
     * @param filter
     * @since 3.3
     */
    public void addFilter(ViewerFilter filter) {
        if (filter != null && !filters.contains(filter)) {
            filters.add(filter);
        }
    }

    /**
     * Set the file name. This will add the default
     * {@link #setFileExtension(String) extension} (if specified) to the file
     * name if one does not exist.
     * 
     * @param fileName
     *            name of new file.
     */
    public void setFileName(String fileName) {
        if (fileName == null) {
            fileName = ""; //$NON-NLS-1$
        }

        // Set the file extension if one is not set
        if (fileName.length() > 0 && defaultFileExtension != null
                && !fileName.endsWith("." + defaultFileExtension)) { //$NON-NLS-1$
            fileName = fileName.concat("." + defaultFileExtension); //$NON-NLS-1$
        }

        // Update the file text control if present, otherwise store the value
        if (fileNameText != null && !fileNameText.isDisposed()) {
            fileNameText.setText(fileName);
            fileNameText.setSelection(0, fileName.length());
        } else {
            this.defaultFileName = fileName;
        }
    }

    /**
     * Set the new file extension. The new file name will be appended with this
     * extension if the extension is not specified in the file name.
     * 
     * @param fileExtension
     *            name of new file extension. This should be without the leading
     *            '.'.
     * @see #setValidateFileExtension(boolean)
     */
    public void setFileExtension(String fileExtension) {

        if (fileExtension != null) {
            fileExtension = fileExtension.trim();

            // Remove any leading extension separator
            while (fileExtension.startsWith(".")) { //$NON-NLS-1$
                fileExtension = fileExtension.substring(1);
            }

            if (fileExtension.length() == 0) {
                fileExtension = null;
            }
        }

        this.defaultFileExtension = fileExtension;
    }

    /**
     * Set the selection validator. Once a folder/special folder is selected
     * this validator will be given an opportunity to validate the selection.
     * <p>
     * A convenience method,
     * {@link #setSpecialFolderSelectionValidator(String, IStatus)
     * setSpecialFolderSelectionValidator}, has been provided to set up a
     * special folder validator.
     * </p>
     * 
     * @param validator
     *            selection validator.
     */
    public void setSelectionValidator(ISelectionValidator validator) {
        this.validator = validator;
    }

    /**
     * Convenience method to set a special folder validator. This will validate
     * that the selected container is a special folder of the given kind (or
     * contained in). If not, an error of the given severity will be raised.
     * 
     * @see #setSelectionValidator(com.tibco.xpd.ui.dialogs.WizardNewFileInSpecialFolderCreationPage.ISelectionValidator)
     * 
     * @param specialFolderKind
     *            kind of special folder
     * @param status
     *            <code>IStatus</code> value. This can be either
     *            {@link IStatus#WARNING} or {@link IStatus#ERROR}. The message
     *            in the <code>IStatus</code> will be displayed when this
     *            validation fails.
     */
    public void setSpecialFolderSelectionValidator(
            final String specialFolderKind, final IStatus status) {

        Assert.isNotNull(specialFolderKind, "special folder kind"); //$NON-NLS-1$
        Assert.isLegal(status.getSeverity() == IStatus.ERROR
                || status.getSeverity() == IStatus.WARNING,
                "errorLevel value is invalid"); //$NON-NLS-1$

        // Add a special folder validation
        setSelectionValidator(new SpecialFolderValidator(specialFolderKind,
                status));
    }

    /**
     * Get the currently selected file name.
     * 
     * @return new file name.
     */
    public String getFileName() {
        return fileNameText != null && !fileNameText.isDisposed() ? fileNameText
                .getText().trim()
                : (defaultFileName != null ? defaultFileName : ""); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
     * Event)
     */
    @Override
    public void handleEvent(Event event) {
        setPageComplete(validatePage());
    }

    /**
     * Get the currently selected container path.
     * 
     * @return container path. This can be <code>null</code>.
     */
    public IPath getContainerFullPath() {
        return containerGroup != null ? containerGroup.getContainerPath()
                : null;
    }

    /**
     * Returns a stream containing the initial contents to be given to new file
     * resource instances. <b>Subclasses</b> may wish to override. This default
     * implementation provides no initial contents.
     * 
     * @return initial contents to be given to new file resource instances
     */
    protected InputStream getInitialContents() {
        return null;
    }

    /**
     * Creates a new file resource in the selected container and with the
     * selected name. Creates any missing resource containers along the path;
     * does nothing if the container resources already exist.
     * <p>
     * In normal usage, this method is invoked after the user has pressed Finish
     * on the wizard; the enablement of the Finish button implies that all
     * controls on on this page currently contain valid values.
     * </p>
     * <p>
     * Note that this page caches the new file once it has been successfully
     * created; subsequent invocations of this method will answer the same file
     * resource without attempting to create it again.
     * </p>
     * <p>
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * </p>
     * 
     * @return the created file resource, or <code>null</code> if the file was
     *         not created
     */
	public IFile createNewFile()
	{
		return this.createNewFile(false, getFileName(), getInitialContents());
	}

	/**
	 * Creates a new file resource in the selected container and with the selected file name and the input stream.
	 * 
	 * This page will cache the new file once it has been successfully created if the clearCache flag is not set and
	 * subsequent invocations of this method will answer the same file resource without attempting to create it again.
	 * If clearCache is true, always create a new file
	 * 
	 * @param clearCache
	 * @param fileName
	 * @param contentStream
	 * @return
	 */
	public IFile createNewFile(boolean clearCache, String fileName, InputStream contentStream)
	{
		if (!clearCache && newFile != null)
		{
            return newFile;
        }

        // create the new file and cache it if successful

        final IPath containerPath = containerGroup.getContainerPath();
		IPath newFilePath = containerPath.append(fileName);
        final IFile newFileHandle =
                ResourcesPlugin.getWorkspace().getRoot().getFile(newFilePath);
		final InputStream initialContents = contentStream;

        IRunnableWithProgress op = new IRunnableWithProgress() {
            @Override
            public void run(IProgressMonitor monitor) {
                CreateFileOperation op =
                        new CreateFileOperation(
                                newFileHandle,
                                null,
                                initialContents,
                                Messages.WizardNewFileInSpecialFolderCreationPage_newFileOperation_label);
                try {
                    PlatformUI.getWorkbench().getOperationSupport()
                            .getOperationHistory().execute(op,
                                    monitor,
                                    WorkspaceUndoUtil
                                            .getUIInfoAdapter(getShell()));
                } catch (final ExecutionException e) {
                    getContainer().getShell().getDisplay()
                            .syncExec(new Runnable() {
                                @Override
                                public void run() {
                                    if (e.getCause() instanceof CoreException) {
                                        ErrorDialog
                                                .openError(getContainer()
                                                        .getShell(),
                                                        Messages.WizardNewFileInSpecialFolderCreationPage_creationProblemErr_title,
                                                        null, // no
                                                        // special
                                                        // message
                                                        ((CoreException) e
                                                                .getCause())
                                                                .getStatus());
                                    } else {
                                        XpdResourcesUIActivator.getDefault()
                                                .getLogger().error(e);
                                        MessageDialog
                                                .openError(getContainer()
                                                        .getShell(),
                                                        Messages.WizardNewFileInSpecialFolderCreationPage_creationProblemErr_title,
                                                        String
                                                                .format(Messages.WizardNewFileInSpecialFolderCreationPage_internalErr_message,
                                                                        e
                                                                                .getCause()
                                                                                .getLocalizedMessage()));
                                    }
                                }
                            });
                }
            }
        };
        try {
            getContainer().run(true, true, op);
        } catch (InterruptedException e) {
            return null;
        } catch (InvocationTargetException e) {
            // Execution Exceptions are handled above but we may still get
            // unexpected runtime errors.
            XpdResourcesUIActivator.getDefault().getLogger().error(e);
            MessageDialog
                    .openError(getContainer().getShell(),
                            Messages.WizardNewFileInSpecialFolderCreationPage_creationProblemErr_title,
                            String
                                    .format(Messages.WizardNewFileInSpecialFolderCreationPage_internalErr_message,
                                            e.getTargetException().getMessage()));

            return null;
        }

        newFile = newFileHandle;

        return newFile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    @Override
    public void createControl(Composite parent) {
        initializeDialogUnits(parent);
        // top level group
        Composite topLevel = new Composite(parent, SWT.NONE);
        topLevel.setLayout(new GridLayout());
        topLevel.setFont(parent.getFont());

        // Create the container selection group
        containerGroup = new SpecialFolderContainerGroup(topLevel, this, true);
        containerGroup.setInitialSelection(selection);
        for (ViewerFilter filter : filters) {
            containerGroup.addFilter(filter);
        }

        fileGroup = new Composite(topLevel, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        fileGroup.setLayout(layout);
        fileGroup
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Create the file input
        Label lbl = new Label(fileGroup, SWT.NONE);
        lbl.setText(getNewFileLabel());

        fileNameText = new Text(fileGroup, SWT.BORDER);
        fileNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        fileNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                setFileName(fileNameText.getText());
            }
        });
        // Set given file name as default
        setFileName(defaultFileName);
        fileNameText.addListener(SWT.Modify, this);

        Composite advancedGroup = createAdvancedControls(topLevel);

        if (advancedGroup != null) {
            advancedGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                    false));
        }

        setPageComplete(validatePage());
        // Show description on opening
        setErrorMessage(null);
        setMessage(null);
        setControl(topLevel);

        // Set focus on the file name text control
        fileNameText.setFocus();
    }

    /**
     * 
     * Subclasses can override this method to append their own controls. Returns
     * null by default.
     * 
     * @since 3.2
     * 
     * @param topLevel
     * @return Composite
     */
    protected Composite createAdvancedControls(Composite topLevel) {
        return null;
    }

	/**
	 * Hides the file controls container - this includes the file name label and input box
	 */
	protected void hideFileGroup()
	{
		GridData fileGrpLayout = new GridData();
		fileGrpLayout.heightHint = 1;
		if (fileGroup != null)
		{
			fileGroup.setVisible(false);
			fileGroup.setLayoutData(fileGrpLayout);
		}
	}
    /**
     * Set the new file label if the default needs to be overriden.
     * 
     * @param label
     *            label to display for the new file entry text control.
     */
    public void setNewFileLabel(String label) {
        this.newFileLabel = label;
    }

    /**
     * Get the new file label to be set for the new file text control.
     * 
     * @return label. If none specified then default will be returned.
     */
    protected String getNewFileLabel() {
        if (newFileLabel == null) {
            newFileLabel =
                    Messages.WizardNewFileInSpecialFolderCreationPage_fileName_label;
        }

        return newFileLabel;
    }

    /**
     * Validate the page.
     * 
     * @return <code>true</code> if page is valid, <code>false</code> otherwise.
     */
    protected boolean validatePage() {
        IStatus status = Status.OK_STATUS;
        final String newFileName = getFileName();
        final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IPath containerPath = containerGroup.getContainerPath();

        // Validate the file name
		// Nikita ACE-8246 Validate the file name only if the group that it is contained in is actually visible
        if (fileGroup.isVisible() && "".equals(newFileName)) { //$NON-NLS-1$
            status =
                    createErrorStatus(Messages.WizardNewFileInSpecialFolderCreationPage_noFileSpecified_message);
        } else if (fileGroup.isVisible() && validateFileExtension && defaultFileExtension != null
                && !newFileName.endsWith("." + defaultFileExtension)) { //$NON-NLS-1$
            status =
                    createErrorStatus(String
                            .format(Messages.WizardNewFileInSpecialFolderCreationPage_incorrectExtension_message,
                                    defaultFileExtension));
        } else {
            // Validate the container path
            if (containerPath == null) {
                status =
                        createErrorStatus(Messages.WizardNewFileInSpecialFolderCreationPage_noFolderSpecified_message);
            } else if (fileGroup.isVisible() && !containerPath.isValidSegment(newFileName)) {
                status =
                        createErrorStatus(String
                                .format(Messages.WizardNewFileInSpecialFolderCreationPage_invalidCharacterInFileName,
                                        newFileName));
            } else {
                String projectName = containerPath.segment(0);

                if (projectName != null) {
                    /* Sid ACE-2859 - Check against locked projects. */
                    IProject project = root.getProject(projectName);

                    if (!project.exists()) {
                        status =
                            createErrorStatus(Messages.WizardNewFileInSpecialFolderCreationPage_folderDoedNotExist_message);

                    } else {
                        try {
                            if (new GovernanceStateService().isLockedForProduction(project)) {
                                status = createErrorStatus(Messages.BasicNewXpdResourceWizard_ProjectLockedError);
                            }
                        } catch (CoreException e) {
                        }
                    }
                }

                if (status.getSeverity() == IStatus.OK) {
                    // Check that the path does not contain any existing files
                    IPath path = containerPath;
                    while (path.segmentCount() > 1) {
                        if (root.getFile(path).exists()) {
                            status =
                                    createErrorStatus(String
                                            .format(Messages.WizardNewFileInSpecialFolderCreationPage_fileAlreadyExistsInLocation_message,
                                                    path.toString()));
                            break;
                        }
                        path = path.removeLastSegments(1);
                    }
                }
            }
        }

		// Nikita ACE-8246 Validate the combined only if the container that contains the file name in is actually
		// visible
		if (status.getSeverity() == IStatus.OK && fileGroup.isVisible())
		{
            // Combine the path and the file name and validate path
            IPath path = containerPath.append(newFileName);

            // Validate complete path
            status =
                    ResourcesPlugin.getWorkspace()
                            .validatePath(path.toString(), IResource.FILE);

            if (status.getSeverity() == IStatus.OK) {
                // Make sure the file doesn't already exist
                if (root.getFile(path).exists()) {
                    switch (overwriteSeverity) {
                    case IStatus.CANCEL:
                    case IStatus.ERROR:
                        status =
                                createErrorStatus(String
                                        .format(Messages.WizardNewFileInSpecialFolderCreationPage_fileAlreadyExists_message,
                                                newFileName));
                        break;
                    case IStatus.WARNING:
                    case IStatus.INFO:
                        status =
                                new Status(
                                        IStatus.WARNING,
                                        XpdResourcesUIActivator.ID,
                                        String
                                                .format(Messages.WizardNewFileInSpecialFolderCreationPage_fileWillBeOverwritten_message,
                                                        newFileName));
                        break;
                    case IStatus.OK:
                        // ignore
                        break;
                    default:
                        Assert.isLegal(false,
                                "Illegal overwriteSeverity value."); //$NON-NLS-1$
                    }
                }
            }
        }

        // Finally, if a selection validator has been defined then run that
        if (status.getSeverity() == IStatus.OK && validator != null) {
            IStatus oldStatus = status;
            status = validator.isValid(containerGroup.getSelection());

            if (status == null) {
                // Revert to previous status
                status = oldStatus;
            }
        }

        // Show appropriate message
        switch (status.getSeverity()) {
        case IStatus.ERROR:
            setErrorMessage(status.getMessage());
            break;

        case IStatus.WARNING:
            setMessage(status.getMessage(), WARNING);
            break;

        default:
            setErrorMessage(null);
            setMessage(null);
            break;
        }

        return (status.getSeverity() != IStatus.ERROR);
    }

    /**
     * Convenience method to create an error status.
     * 
     * @param errMsg
     *            error message to add to status
     * @return error status.
     */
    private IStatus createErrorStatus(String errMsg) {
        return new Status(IStatus.ERROR, XpdResourcesUIActivator.ID, errMsg);
    }

    /**
     * Set the status describing what should happen with validation if the
     * specified file already exists. The default severity is
     * <code>IStatus.ERROR</code>.
     * 
     * @param severity
     *            the overwriteSeverity to set. The severity must be one of: <li>
     *            <code>IStatus.OK</code> - the existing file is overwritten
     *            without warning.</li> <li><code>IStatus.INFO</code> - there is
     *            a warning that the file will be overwritten.</li> <li>
     *            <code>IStatus.WARNING</code> - there is a warning that the
     *            file will be overwritten.</li> <li>
     *            <code>IStatus.ERROR</code> - if the file already exists than
     *            it will be manifested with error and .</li> <li>
     *            <code>IStatus.CANCEL</code> - the same as IStatus.ERROR.</li>
     * @throws IllegalArgumentException
     *             if status is invalid.
     * @see IStatus
     */
    public void setOverwriteSeverity(int severity) {
        if (severity == IStatus.OK | severity == IStatus.INFO
                | severity == IStatus.ERROR | severity == IStatus.CANCEL
                | severity == IStatus.WARNING) {
            this.overwriteSeverity = severity;
        } else {
            throw new IllegalArgumentException(String
                    .format("Illegal severity: %1$d", //$NON-NLS-1$
                            severity));
        }
    }

    /**
     * Returns overwrite severity (One of IStatus constants). The default
     * severity is <code>IStatus.ERROR</code>.
     * 
     * @return the overwriteSeverity.
     */
    public int getOverwriteSeverity() {
        return overwriteSeverity;
    }

    @Override
    public void dispose() {
        filters.clear();
        super.dispose();
    }

    /**
     * Sets if the validation of the filename extension is performed (default is
     * <code>false</code>). The filename extension's validation will only apply
     * if file extension is set first. (To set file extension use:
     * {@link #setFileExtension(String)} method.)
     * 
     * @param validateFileExtension
     *            <code>true</code> if the extension should be validated.
     */
    public void setValidateFileExtension(boolean validateFileExtension) {
        this.validateFileExtension = validateFileExtension;
    }

    /**
     * This interface should be implemented to validate the current selection in
     * the tree viewer of the {@link WizardNewFileInSpecialFolderCreationPage}.
     * 
     * @author njpatel
     * 
     */
    public interface ISelectionValidator {
        /**
         * Validate whether the container selection is a valid destination for
         * the new file.
         * 
         * @param selection
         *            current selected object. This will be an
         *            <code>IFolder</code> or <code>SpecialFolder</code>. Note
         *            that the <code>IFolder</code> may not exist yet.
         * @return <code>IStatus</code> with {@link IStatus#ERROR},
         *         {@link IStatus#WARNING} or {@link IStatus#OK} severity
         *         values.
         */
        IStatus isValid(Object selection);
    }

    /**
     * Implementation of the <code>ISelectionValidator</code> that validates
     * that the selected container is of the given kind of special folder. If
     * not a status will be raised with the given error level.
     * 
     * @author njpatel
     * 
     */
    private class SpecialFolderValidator implements ISelectionValidator {

        private final String kind;

        private final IStatus status;

        /**
         * Constructor
         * 
         * @param kind
         *            kind of special folder
         * @param status
         *            <code>IStatus</code>. Value can be {@value IStatus#ERROR}
         *            or {@value IStatus#WARNING}.
         */
        public SpecialFolderValidator(String kind, IStatus status) {
            this.kind = kind;
            this.status = status;
        }

        @Override
        public IStatus isValid(Object selection) {
            SpecialFolder sf = null;

            // Check if the selection is a special folder or a folder inside
            // a special folder
            if (selection instanceof SpecialFolder) {
                sf = (SpecialFolder) selection;
            } else {
                IFolder folder = null;

                if (selection instanceof IFolder) {
                    folder = (IFolder) selection;
                } else if (selection instanceof IAdaptable) {
                    folder =
                            ((IAdaptable) selection)
                                    .getAdapter(IFolder.class);
                }

                if (folder != null) {
                    ProjectConfig config =
                            XpdResourcesPlugin.getDefault()
                                    .getProjectConfig(folder.getProject());

                    if (config != null) {
                        sf =
                                config.getSpecialFolders()
                                        .getFolderContainer(folder);
                    }
                }
            }

            if (sf != null && sf.getKind().equals(kind)) {
                return Status.OK_STATUS;
            }

            return status;
        }
    }

}