/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.ui.components;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.deploy.ui.DeployUIActivator;
import com.tibco.xpd.deploy.ui.DeployUIConstants;
import com.tibco.xpd.deploy.ui.internal.Messages;

/**
 * File control with optional system and workspace pickers.
 * <p>
 * <i>Created: 12 Mar 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class FileControl extends Composite {

    public static final int EMPTY_PATH_ERROR_CODE = 1;

    public static final int INVALID_PATH_ERROR_CODE = 2;

    public static final int NOT_EXIST_ERROR_CODE = 4;

    public static final int PARENT_NOT_EXIST_ERROR_CODE = 8;

    /**
     * Key for browse type option. Possible values are: "SYSTEM", "WORKSPACE",
     * "BOTH". "BOTH" is default value if not specified.
     */
    public static final String FILE_BROWSE_TYPE = "FileBrowseType"; //$NON-NLS-1$

    /** Only system picker button will be shown. */
    public static final String FILE_BROWSE_TYPE_SYSTEM = "SYSTEM"; //$NON-NLS-1$

    /** Only workspace picker button will be shown. */
    public static final String FILE_BROWSE_TYPE_WORKSPACE = "WORKSPACE"; //$NON-NLS-1$

    /** System and workspace picker buttons will be shown. */
    public static final String FILE_BROWSE_TYPE_BOTH = "BOTH"; //$NON-NLS-1$

    /**
     * Key for object to browse option. Possible values are: "FILE", "FOLDER".
     * "FILE" is default value if not specified.
     */
    public static final String FILE_BROWSE_OBJECT = "FileBrowseObject"; //$NON-NLS-1$

    /** Control will be used for file picking. */
    public static final String FILE_BROWSE_OBJECT_FILE = "FILE"; //$NON-NLS-1$

    /** Control will be used for folder picking. */
    public static final String FILE_BROWSE_OBJECT_FOLDER = "FOLDER"; //$NON-NLS-1$

    /**
     * Key for default value option. Default value of the control. This should
     * be a valid path. Default is "".
     */
    public static final String FILE_DEFAULT_VALUE = "FileDefaultValue"; //$NON-NLS-1$

    /**
     * Key for file extension option. Space separated list of file extensions
     * used to filter files in picker. For example: "xml xsl xsd".
     */
    public static final String FILE_EXTENSIONS = "FileExtensions"; //$NON-NLS-1$

    /**
     * Key for workspace relative path option. Determines if path picked by
     * workspace picker will be relative (to workspace root): "true" value, or
     * absolute "false" value. Default is "true".
     */
    public static final String FILE_WORKSPACE_RELATIVE_PATH =
            "FileWorkspaceRelativePath"; //$NON-NLS-1$

    /**
     * Key for showing hidden files option. If set to "true" then workspace
     * resources starting with '.' will be shown in the workspace picker.
     * Default is "false" - '.' starting resources are hidden.
     */
    public static final String FILE_SHOW_HIDDEN = "FileShowHidden"; //$NON-NLS-1$

    /**
     * Option for setting text on the system picker button. If not provided then
     * icon will be used (default).
     */
    public static final String FILE_BROWSE_SYSTEM_TEXT = "FileBrowseSystemText"; //$NON-NLS-1$

    /**
     * Option for setting tooltip on the system picker button. If not provided
     * then externalized form of "Browse System..." will be used.
     */
    public static final String FILE_BROWSE_SYSTEM_TOOLTIP =
            "FileBrowseSystemTooltip"; //$NON-NLS-1$

    /**
     * Option for setting text on the workspace picker button. If not provided
     * then icon will be used (default).
     */
    public static final String FILE_BROWSE_WORKSPACE_TEXT =
            "FileBrowseWorkspaceText"; //$NON-NLS-1$

    /**
     * Option for setting tooltip on the workspace picker button. If not
     * provided then externalized form of "Browse Workspace..." will be used.
     */
    public static final String FILE_BROWSE_WORKSPACE_TOOLTIP =
            "FileBrowseWorkspaceTooltip"; //$NON-NLS-1$

    /**
     * Option key for setting the severity of the problem when resource doesn't
     * exist. Possible values are: "ERROR", "WARNING", "IGNORE". Default value
     * is: "ERROR".
     */
    public static final String FILE_VALIDATE_EXISTANCE_SEVERITY =
            "FileValidateExistanceSeverity"; //$NON-NLS-1$

    /**
     * Option key for setting the severity of the problem when resource parent
     * doesn't exist. Possible values are: "ERROR", "WARNING", "IGNORE". Default
     * value is: "IGNORE".
     */
    public static final String FILE_VALIDATE_PARENT_EXISTANCE_SEVERITY =
            "FileValidateParentExistanceSeverity"; //$NON-NLS-1$

    /**
     * Error severity. The problem with this severity will be manifested as an
     * error.
     */
    public static final String FILE_VALIDATE_SEVERITY_ERROR = "ERROR"; //$NON-NLS-1$

    /**
     * Warning severity. The problem with this severity will be manifested as a
     * warning.
     */
    public static final String FILE_VALIDATE_SEVERITY_WARNING = "WARNING"; //$NON-NLS-1$

    /**
     * Ignore severity. The problem with this severity will be ignored.
     */
    public static final String FILE_VALIDATE_SEVERITY_IGNORE = "IGNORE"; //$NON-NLS-1$

    /**
     * The style of the composite. The parameter should be integer value.
     * Default is SWT.NONE so the value would be "0".
     */

    /** */
    public static final String FILE_COMPOSITE_STYLE = "FileCompositeStryle"; //$NON-NLS-1$

    /**
     * The style of the buttons. The parameter should be integer value. Default
     * is SWT.PUSH so the value would be "8".
     */
    public static final String FILE_BUTTONS_STYLE = "FileButtonsStryle"; //$NON-NLS-1$

    /**
     * The style of the text. The parameter should be integer value. Default is
     * SWT.SINGLE | SWT.BORDER so the value will be "2052" (2 | 2048).
     */
    public static final String FILE_TEXT_STYLE = "FileTextStryle"; //$NON-NLS-1$

    protected static final String FILE_BROWSE_DEFAULT_TEXT = ""; //$NON-NLS-1$

    protected static final String FILE_BROWSE_SYSTEM_TOOLTIP_DEFAULT =
            Messages.FileControl_systemPickerButton_tooltip;

    protected static final String FILE_BROWSE_WORKSPACE_TOOLTIP_DEFAULT =
            Messages.FileControl_workspacePickerButton_tooltip;

    protected static final ViewerFilter HIDDEN_FILTER =
            new PatternFilter("^\\..*$"); //$NON-NLS-1$

    protected static final int BROWSE_SYSTEM = 1;

    protected static final int BROWSE_WORKSPACE = 2;

    protected Button pickSystemButton;

    protected Button pickWorkspaceButton;

    protected final Text textField;

    protected String optionDefaultValue;

    protected int optionBrowseType;

    protected String optionBrowseObject;

    protected String[] optionExtensions;

    protected boolean optionWorkspaceRelativePath;

    protected boolean optionShowHidden;

    protected String optionBrowseSystemText;

    protected String optionBrowseSystemTooltip;

    protected String optionBrowseWorkspaceText;

    protected String optionBrowseWorkspaceTooltip;

    /**
     * Severity of validation status if file/directory not exists. One of
     * IStatus.ERROR, IStatus.WARNING, IStatus.CANCEL(ignore).
     */
    protected int optionValidateExistanceSeverity;

    /**
     * Severity of validation status if file/directory not exists. One of
     * IStatus.ERROR, IStatus.WARNING, IStatus.CANCEL(ignore).
     */
    protected int optionValidateParentExistanceSeverity;

    /** Style used for button. */
    protected int optionButtonsStyle;

    /** Style used for text. */
    protected int optionTextStyle;

    /**
     * Creates new file control.
     * 
     * @param parent
     *            parent composite.
     * @param options
     *            contains style and rendering options and other hints.
     */
    public FileControl(Composite parent, final Map<String, String> options) {
        super(parent, getCompositeStyleOption(options));

        initOptions(options);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns =
                optionBrowseType == (BROWSE_SYSTEM | BROWSE_SYSTEM) ? 4 : 3;
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.makeColumnsEqualWidth = false;
        this.setLayout(gridLayout);

        textField = new Text(this, optionTextStyle);
        textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textField.setText(optionDefaultValue);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true,
                false).applyTo(textField);

        if ((optionBrowseType & BROWSE_SYSTEM) > 0) {
            pickSystemButton = new Button(this, optionButtonsStyle);
            if (FILE_BROWSE_DEFAULT_TEXT.equals(optionBrowseSystemText)) {
                pickSystemButton.setImage(DeployUIActivator.getDefault()
                        .getImageRegistry()
                        .get(DeployUIConstants.IMG_SYSTEM_PICK));
            }
            pickSystemButton.setText(optionBrowseSystemText);
            pickSystemButton.setToolTipText(optionBrowseSystemTooltip);
            GridDataFactory.swtDefaults().applyTo(pickSystemButton);
            pickSystemButton.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                    // do nothing.
                }

                public void widgetSelected(SelectionEvent e) {
                    onPickSystemButtonSelected(e);
                }
            });
        }

        if ((optionBrowseType & BROWSE_WORKSPACE) > 0) {
            // TODO Apply style from options.
            pickWorkspaceButton = new Button(this, optionButtonsStyle);
            if (FILE_BROWSE_DEFAULT_TEXT.equals(optionBrowseWorkspaceText)) {
                pickWorkspaceButton.setImage(DeployUIActivator.getDefault()
                        .getImageRegistry()
                        .get(DeployUIConstants.IMG_WORKSPACE_PICK));
            }
            pickWorkspaceButton.setText(optionBrowseWorkspaceText);
            pickWorkspaceButton.setToolTipText(optionBrowseWorkspaceTooltip);
            GridDataFactory.swtDefaults().applyTo(pickWorkspaceButton);
            pickWorkspaceButton.addSelectionListener(new SelectionListener() {
                public void widgetDefaultSelected(SelectionEvent e) {
                    // do nothing.
                }

                public void widgetSelected(SelectionEvent e) {
                    onPickWorkspaceButtonSelected(e);
                }
            });
        }

    }

    protected void onPickSystemButtonSelected(SelectionEvent e) {
        if (FILE_BROWSE_OBJECT_FILE.equals(optionBrowseObject)) {
            String chosenFile =
                    browseSystemFile(textField.getText(), optionExtensions);
            if (chosenFile != null) {
                textField.setText(chosenFile);
            }
        } else if (FILE_BROWSE_OBJECT_FOLDER.equals(optionBrowseObject)) {
            String chosenDir = browseSystemDirectory(textField.getText());
            if (chosenDir != null) {
                textField.setText(chosenDir);
            }
        }
    }

    protected void onPickWorkspaceButtonSelected(SelectionEvent e) {
        if (FILE_BROWSE_OBJECT_FILE.equals(optionBrowseObject)) {
            String chosenFile =
                    browseWorkspaceFile(textField.getText(),
                            optionExtensions,
                            optionWorkspaceRelativePath);
            if (chosenFile != null) {
                textField.setText(chosenFile);
            }
        } else if (FILE_BROWSE_OBJECT_FOLDER.equals(optionBrowseObject)) {
            String chosenDir =
                    browseWorkspaceDirectory(textField.getText(), true);
            if (chosenDir != null) {
                textField.setText(chosenDir);
            }
        }
    }

    /**
     * Gets the path from the control text. If the path is valid it is always
     * absolute or relative to the workspace root. If relative workspace path
     * are allowed then to get the java.io.File representation of the path one
     * could use:
     * 
     * <pre>
     * String pathString = getText();
     * if (!new Path(pathString).isAbsolute()) {
     *     pathString =
     *             workpaceRoot.getLocation().append(pathStringString)
     *                     .toPortableString();
     * }
     * File f = new File(pathString);
     * 
     * </pre>
     * 
     * @return the current value of the control.
     */
    public String getText() {
        if (textField != null && !textField.isDisposed()) {
            return textField.getText();
        }
        return optionDefaultValue;
    }

    /**
     * Sets the current value of the control text.
     * 
     * @param string
     *            the string should represent absolute or workspace relative
     *            path.
     */
    public void setText(String string) {
        if (textField != null && !textField.isDisposed()) {
            textField.setText(string);
        }
    }

    /**
     * Validates control and returns status representing validation result.
     * 
     * @return the status of validation result.
     */
    public IStatus validate() {
        String pluginId = DeployUIActivator.PLUGIN_ID;
        String pathString = getText();
        if (pathString.trim().length() == 0) {
            return new Status(IStatus.ERROR, pluginId, EMPTY_PATH_ERROR_CODE,
                    Messages.FileControl_emptyPathError_message, null);
        }
        if (!new Path("").isValidPath(pathString)) {//$NON-NLS-1$
            return new Status(IStatus.ERROR, pluginId, INVALID_PATH_ERROR_CODE,
                    Messages.FileControl_invalidPathError_message, null);
        }
        Path path = new Path(pathString);
        if (optionValidateExistanceSeverity != IStatus.CANCEL) {
            if (FILE_BROWSE_OBJECT_FILE.equals(optionBrowseObject)
                    && !fileExists(path)) {
                return new Status(optionValidateExistanceSeverity, pluginId,
                        NOT_EXIST_ERROR_CODE,
                        Messages.FileControl_fileNotExist_message, null);
            } else if (FILE_BROWSE_OBJECT_FOLDER.equals(optionBrowseObject)
                    && !folderExists(path)) {
                return new Status(optionValidateExistanceSeverity, pluginId,
                        NOT_EXIST_ERROR_CODE,
                        Messages.FileControl_folderNotExist_message, null);
            }
        }
        IPath parentPath = new Path(pathString).removeLastSegments(1);
        if (optionValidateExistanceSeverity != IStatus.CANCEL) {
            if (!folderExists(parentPath)) {
                return new Status(optionValidateExistanceSeverity, pluginId,
                        PARENT_NOT_EXIST_ERROR_CODE,
                        Messages.FileControl_parentFolderNotExist_message, null);
            }
        }
        return Status.OK_STATUS;
    }

    protected boolean fileExists(final IPath path) {
        if (!path.isAbsolute()) {
            IResource resource = getWorkspaceRoot().findMember(path);
            return resource instanceof IFile && resource.exists();
        } else {
            File file = new File(path.toPortableString());
            return file.exists() && file.isFile();
        }
    }

    protected boolean folderExists(final IPath path) {
        if (!path.isAbsolute()) {
            IResource resource = getWorkspaceRoot().findMember(path);
            return resource instanceof IFolder && resource.exists();
        } else {
            File file = new File(path.toPortableString());
            return file.exists() && file.isDirectory();
        }
    }

    /**
     * Helper to open the file chooser dialog.
     * 
     * @param startingDirectory
     *            the directory to open the dialog on.
     * @return string representing the file the user selected or
     *         <code>null</code> if they do not.
     */
    protected String browseSystemFile(String startingDirectory,
            String[] fileExtension) {
        File startingDirFile = findStartingFile(startingDirectory);
        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
        if (startingDirFile != null) {
            if (startingDirFile.isDirectory()) {
                dialog.setFilterPath(startingDirFile.getPath());
            } else if (startingDirFile.isFile()) {
                dialog.setFileName(startingDirFile.getPath());
            }
        }
        if (fileExtension != null) {
            dialog.setFilterExtensions(fileExtension);
        }
        String file = dialog.open();
        if (file != null) {
            file = file.trim();
            if (file.length() > 0) {
                return new File(file).getAbsolutePath();
            }
        }

        return null;
    }

    /**
     * Helper that opens the directory chooser dialog.
     * 
     * @param startingDirectory
     *            The directory the dialog will open in.
     * @return String representing chosen directory or <code>null</code>.
     * 
     */
    protected String browseSystemDirectory(String startingDirectory) {
        File startingDirFile = findStartingFile(startingDirectory);
        DirectoryDialog fileDialog = new DirectoryDialog(getShell(), SWT.OPEN);
        if (startingDirFile != null) {
            fileDialog.setFilterPath(startingDirFile.getPath());
        }
        String dir = fileDialog.open();
        if (dir != null) {
            dir = dir.trim();
            if (dir.length() > 0) {
                return new File(dir).getAbsolutePath();
            }
        }

        return null;
    }

    /**
     * Helper to open the workspace file chooser dialog.
     * 
     * @param startingDirectory
     *            the directory to open the dialog on.
     * @return string representing the file the user selected or
     *         <code>null</code> if they do not.
     */
    protected String browseWorkspaceFile(String startingDirectory,
            String[] fileExtension, boolean useRelative) {

        IResource startingResource = findStartingResource(startingDirectory);
        Object[] initialSelection = new Object[0];
        if (startingResource != null) {
            initialSelection = new Object[] { startingResource };
        }
        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
        filters.add(new ExtensionsFilter(fileExtension));
        if (!optionShowHidden) {
            filters.add(HIDDEN_FILTER);
        }

        IFile[] pickedFiles =
                WorkspaceResourceDialog.openFileSelection(getShell(),
                        Messages.FileControl_systemFilePicker_title,
                        Messages.FileControl_systemFilePicker_message,
                        false,
                        initialSelection,
                        filters);

        if (pickedFiles.length > 0) {
            if (useRelative) {
                return pickedFiles[0].getFullPath().makeRelative()
                        .toPortableString();
            } else {
                return pickedFiles[0].getLocation().toOSString();
            }
        }
        return null;
    }

    /**
     * Helper that opens the workspace directory chooser dialog.
     * 
     * @param startingDirectory
     *            The directory the dialog will open in.
     * @return File File or <code>null</code>.
     * 
     */
    protected String browseWorkspaceDirectory(String startingDirectory,
            boolean useRelative) {
        IResource startingResource = findStartingResource(startingDirectory);
        Object[] initialSelection = new Object[0];
        if (startingResource != null) {
            if (startingResource instanceof IFile) {
                startingResource = startingResource.getParent();
            }
            initialSelection = new Object[] { startingResource };
        }
        List<ViewerFilter> filters =
                optionShowHidden ? null : Arrays.asList(HIDDEN_FILTER);

        IContainer[] pickedFolders =
                WorkspaceResourceDialog.openFolderSelection(getShell(),
                        Messages.FileControl_workspaceFolderPicker_title,
                        Messages.FileControl_workspaceFolderPicker_message,
                        false,
                        initialSelection,
                        filters);

        if (pickedFolders.length > 0 && pickedFolders[0] != null) {
            if (useRelative) {
                return pickedFolders[0].getFullPath().makeRelative()
                        .toPortableString();
            } else {
                return pickedFolders[0].getLocation().toOSString();
            }
        }
        return null;
    }

    private static class ExtensionsFilter extends ViewerFilter {
        private final Set<String> extensions = new HashSet<String>();

        public ExtensionsFilter(String[] extensions) {
            for (String ext : extensions) {
                this.extensions.add(ext);
            }
        }

        /** {@inheritDoc} */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof IFile) {
                IFile file = (IFile) element;
                if (extensions.contains(file.getFileExtension())
                        || extensions.isEmpty()) {
                    return true;
                }
                return false;
            }
            return true;
        }

    }

    private static class PatternFilter extends ViewerFilter {
        private final Pattern pattern;

        public PatternFilter(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        /** {@inheritDoc} */
        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof IResource) {
                IResource res = (IResource) element;
                if (pattern.matcher(res.getName()).matches()) {
                    return false;
                }
            }
            return true;
        }

    }

    protected IWorkspaceRoot getWorkspaceRoot() {
        return ResourcesPlugin.getWorkspace().getRoot();
    }

    /**
     * @param startDirPath
     */
    private File findStartingFile(final String startingDirectoryFile) {
        String startDir = (startingDirectoryFile == null) ? "" //$NON-NLS-1$
                : startingDirectoryFile;
        IPath startDirPath = new Path(startDir);
        if (!startDirPath.isAbsolute()) {
            startDirPath =
                    getWorkspaceRoot().getLocation().append(startDirPath);
        }
        File file = new File(startDirPath.toPortableString());
        File parent = null;
        String parentPathString = file.getParent();
        if (parentPathString != null) {
            parent = new File(parentPathString);
        }
        if (file.exists()) {
            return file;
        } else if (parent != null && parent.exists()) {
            return parent;
        }
        return null;
    }

    /**
     * Finds starting resource.
     * 
     * @param startingDirectory
     *            the path to resource. It my be absolute.
     * @return
     */
    private IResource findStartingResource(final String startingDirectory) {
        String startDir = (startingDirectory == null) ? "" //$NON-NLS-1$
                : startingDirectory;
        IPath startDirPath = new Path(startDir);
        IPath workspaceLocPath = getWorkspaceRoot().getLocation();
        if (startDirPath.isAbsolute()
                && workspaceLocPath.isPrefixOf(startDirPath)) {
            startDirPath =
                    startDirPath.removeFirstSegments(workspaceLocPath
                            .matchingFirstSegments(startDirPath))
                            .makeRelative();
        }
        return getWorkspaceRoot().findMember(startDirPath.toPortableString());
    }

    /**
     * Reading the known options and initializing option variables.
     * 
     * @param options
     *            the options map possibly containing customization options.
     */
    private void initOptions(Map<String, String> options) {
        // browse type - default is BOTH
        {
            String userBrowseType = options.get(FILE_BROWSE_TYPE);
            if (FILE_BROWSE_TYPE_BOTH.equals(userBrowseType)) {
                optionBrowseType = BROWSE_SYSTEM | BROWSE_WORKSPACE;
            } else if (FILE_BROWSE_TYPE_SYSTEM.equals(userBrowseType)) {
                optionBrowseType = BROWSE_SYSTEM;
            } else if (FILE_BROWSE_TYPE_WORKSPACE.equals(userBrowseType)) {
                optionBrowseType = BROWSE_WORKSPACE;
            } else {
                optionBrowseType = BROWSE_SYSTEM | BROWSE_WORKSPACE; // default
                // is BOTH
            }
        }
        // browse object - default is FILE
        {
            String userBrowseObject = options.get(FILE_BROWSE_OBJECT);
            if (userBrowseObject != null) {
                userBrowseObject = userBrowseObject.trim();
            }
            if (FILE_BROWSE_OBJECT_FILE.equals(userBrowseObject)) {
                optionBrowseObject = FILE_BROWSE_OBJECT_FILE;
            } else if (FILE_BROWSE_OBJECT_FOLDER.equals(userBrowseObject)) {
                optionBrowseObject = FILE_BROWSE_OBJECT_FOLDER;
            } else {
                optionBrowseObject = FILE_BROWSE_OBJECT_FILE; // default is FILE
            }
        }
        // extensions - default is String[0]
        {
            String userExtensions = options.get(FILE_EXTENSIONS);
            if (userExtensions != null) {
                optionExtensions = userExtensions.split("\\s"); //$NON-NLS-1$
            } else {
                optionExtensions = new String[] {};
            }

        }
        // defaultValue
        {
            String userDefaultValue = options.get(FILE_DEFAULT_VALUE);
            if (userDefaultValue != null) {
                optionDefaultValue = userDefaultValue.trim();
            } else {
                optionDefaultValue = ""; //$NON-NLS-1$ //default
            }

        }
        // workspaceRelativePath - default is true
        {
            String userWorkspaceRelativePath =
                    options.get(FILE_WORKSPACE_RELATIVE_PATH);
            if (userWorkspaceRelativePath != null) {
                optionWorkspaceRelativePath =
                        Boolean.parseBoolean(userWorkspaceRelativePath.trim());
            } else {
                optionWorkspaceRelativePath = true;
            }

        }
        // showHidded - default is false
        {
            String userShowHidden = options.get(FILE_SHOW_HIDDEN);
            if (userShowHidden != null) {
                optionShowHidden = Boolean.parseBoolean(userShowHidden.trim());
            } else {
                optionShowHidden = false;
            }

        }

        // browseSystemText - default is null (icon is used instead)
        {
            String userBrowseSystemText = options.get(FILE_BROWSE_SYSTEM_TEXT);
            if (userBrowseSystemText != null) {
                optionBrowseSystemText = userBrowseSystemText.trim();
            } else {
                optionBrowseSystemText = FILE_BROWSE_DEFAULT_TEXT;
            }
        }

        // browseWorkspaceText - default is null (icon is used instead)
        {
            String userBrowseWorkspaceText =
                    options.get(FILE_BROWSE_WORKSPACE_TEXT);
            if (userBrowseWorkspaceText != null) {
                optionBrowseWorkspaceText = userBrowseWorkspaceText.trim();
            } else {
                optionBrowseWorkspaceText = FILE_BROWSE_DEFAULT_TEXT;
            }
        }

        // browseSystemTooltip
        {
            String userBrowseSystemTooltip =
                    options.get(FILE_BROWSE_SYSTEM_TOOLTIP);
            if (userBrowseSystemTooltip != null) {
                optionBrowseSystemTooltip = userBrowseSystemTooltip.trim();
            } else {
                optionBrowseSystemTooltip = FILE_BROWSE_SYSTEM_TOOLTIP_DEFAULT; // default
                // (
                // externalized
                // )
            }
        }

        // browseWorkspaceTooltip
        {
            String userBrowseWorkspaceTooltip =
                    options.get(FILE_BROWSE_WORKSPACE_TOOLTIP);
            if (userBrowseWorkspaceTooltip != null) {
                optionBrowseWorkspaceTooltip =
                        userBrowseWorkspaceTooltip.trim();
            } else {
                optionBrowseWorkspaceTooltip =
                        FILE_BROWSE_WORKSPACE_TOOLTIP_DEFAULT; // default
                // (
                // externalized
                // )
            }
        }

        // validateParentExistanceSeverity
        {
            optionValidateParentExistanceSeverity = IStatus.CANCEL; // default
            // ignore
            String userParentExistanceSeverity =
                    options.get(FILE_VALIDATE_PARENT_EXISTANCE_SEVERITY);
            if (userParentExistanceSeverity != null) {
                userParentExistanceSeverity =
                        userParentExistanceSeverity.trim();
                if (FILE_VALIDATE_SEVERITY_ERROR
                        .equals(userParentExistanceSeverity)) {
                    optionValidateParentExistanceSeverity = IStatus.ERROR;
                } else if (FILE_VALIDATE_SEVERITY_WARNING
                        .equals(userParentExistanceSeverity)) {
                    optionValidateParentExistanceSeverity = IStatus.WARNING;
                } else if (FILE_VALIDATE_SEVERITY_IGNORE
                        .equals(userParentExistanceSeverity)) {
                    optionValidateParentExistanceSeverity = IStatus.CANCEL;
                }
            }
        }
        // validateExistanceSeverity
        {
            optionValidateExistanceSeverity = IStatus.ERROR; // default ERROR
            String userExistanceSeverity =
                    options.get(FILE_VALIDATE_EXISTANCE_SEVERITY);
            if (userExistanceSeverity != null) {
                userExistanceSeverity = userExistanceSeverity.trim();
                if (FILE_VALIDATE_SEVERITY_ERROR.equals(userExistanceSeverity)) {
                    optionValidateExistanceSeverity = IStatus.ERROR;
                } else if (FILE_VALIDATE_SEVERITY_WARNING
                        .equals(userExistanceSeverity)) {
                    optionValidateExistanceSeverity = IStatus.WARNING;
                } else if (FILE_VALIDATE_SEVERITY_IGNORE
                        .equals(userExistanceSeverity)) {
                    optionValidateExistanceSeverity = IStatus.CANCEL;
                }
            }
        }
        // buttonsStyle - default is SWT.PUSH
        {
            optionButtonsStyle = SWT.PUSH;
            String buttonsStyle = options.get(FILE_BUTTONS_STYLE);
            if (buttonsStyle != null) {
                try {
                    int buttonsStyleInt = Integer.parseInt(buttonsStyle.trim());
                    optionButtonsStyle = buttonsStyleInt;
                } catch (NumberFormatException e) {
                    // ignore, default will be used
                }
            }
        }
        // textStyle - default is SWT.SINGLE | SWT.BORDER
        {
            optionTextStyle = SWT.SINGLE | SWT.BORDER;
            String textStyle = options.get(FILE_TEXT_STYLE);
            if (textStyle != null) {
                try {
                    int textStyleInt = Integer.parseInt(textStyle.trim());
                    optionTextStyle = textStyleInt;
                } catch (NumberFormatException e) {
                    // ignore, default will be used
                }
            }
        }
    }

    private static int getCompositeStyleOption(Map<String, String> options) {
        String compositeStyle = options.get(FILE_COMPOSITE_STYLE);
        if (compositeStyle != null) {
            try {
                int compositeStyleInt = Integer.parseInt(compositeStyle.trim());
                return compositeStyleInt;
            } catch (NumberFormatException e) {
                // ignore, default will be used
            }
        }
        return SWT.NONE; // default
    }

    /**
     * Adds listener to the text control.
     * 
     * @param eventType
     *            the listening event type.
     * @param listener
     *            the listener to remove.
     */
    public void addTextListener(int eventType, Listener listener) {
        textField.addListener(eventType, listener);
    }

    /**
     * Removes listener from the text control.
     * 
     * @param eventType
     *            the listening event type.
     * @param listener
     *            the listener to remove.
     */
    public void removeTextListener(int eventType, Listener listener) {
        textField.removeListener(eventType, listener);
    }

    /** {@inheritDoc} */
    @Override
    public void setData(String key, Object value) {
        super.setData(key, value);
        textField.setData(key, value);
    }

    /** {@inheritDoc} */
    @Override
    public void setData(Object data) {
        super.setData(data);
        textField.setData(data);
    }
}
