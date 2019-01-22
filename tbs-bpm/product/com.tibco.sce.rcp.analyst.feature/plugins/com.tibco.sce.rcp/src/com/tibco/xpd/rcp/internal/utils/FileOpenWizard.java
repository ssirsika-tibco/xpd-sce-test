/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal.utils;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.rcp.internal.Messages;

/**
 * A wizard that provides a text control and browse button to open a file from
 * the file system.
 * 
 * @author njpatel
 * 
 */
public abstract class FileOpenWizard extends Wizard {

    public enum ResourceType {
        FILE, FOLDER;
    }

    protected static int STANDARD_LABEL_WIDTH = 85;

    private final String description;

    private final String title;

    private final boolean isNewFile;

    private String[] fileExtensionFilter;

    protected FileSelectionPage selectionPage;

    private String[] fileExtensionNames;

    /**
     * Wizard to open a file.
     * 
     * @param title
     * @param description
     * @param isNewFile
     *            <code>true</code> if this can create a new file,
     *            <code>false</code> if it should open an existing file.
     */
    public FileOpenWizard(String title, String description, boolean isNewFile) {
        this.title = title;
        this.description = description;
        this.isNewFile = isNewFile;

        setWindowTitle(title);
        setNeedsProgressMonitor(true);
    }

    /**
     * Set the file extension filters (e.g. "xml", "xpdl").
     * 
     * @param fileExtensionFilter
     */
    public void setFileFilterExtensions(String[] fileExtensionFilter) {
        this.fileExtensionFilter = fileExtensionFilter;
    }

    /**
     * Set the file extension filter names (to match filter provided in
     * {@link #setFileFilterExtensions(String[])}.
     * 
     * @param names
     */
    public void setFileExtensionFilterNames(String[] names) {
        this.fileExtensionNames = names;
    }

    /**
     * Get path of the selected file.
     * 
     * @return
     */
    protected IPath getPath() {
        return selectionPage != null ? selectionPage.getPath() : null;
    }

    /**
     * Get the initial path to select in the file browser.
     * 
     * @return
     */
    protected abstract IPath getInitialPath();

    @Override
    public void addPages() {
        selectionPage = new FileSelectionPage(getInitialPath());
        addPage(selectionPage);
    }

    /**
     * File selection page that allows the user to select a file from the file
     * system.
     */
    protected class FileSelectionPage extends WizardPage {

        private IPath path;

        private Text fileTxt;

        private String fileExt;

        public FileSelectionPage(IPath initialPath) {
            super("fileSelect"); //$NON-NLS-1$
            this.path = initialPath;
            setTitle(title);
            setDescription(description);

            if (isNewFile) {
                // Only one extension can be allowed if creating new file
                fileExt = fileExtensionFilter[0];
            }
        }

        public IPath getPath() {
            return path;
        }

        @Override
        public void createControl(Composite parent) {
            Composite root = new Composite(parent, SWT.NONE);
            root.setLayout(new GridLayout(3, false));
            Label lbl = new Label(root, SWT.NONE);
            lbl.setText(Messages.FileOpenWizard_file_label);
            GridData data = new GridData(SWT.FILL, SWT.FILL, false, false);
            data.widthHint = STANDARD_LABEL_WIDTH;
            lbl.setLayoutData(data);
            fileTxt = new Text(root, SWT.BORDER);
            fileTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            fileTxt.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {
                    setPageComplete(validatePage());
                }

            });

            Button btnBrowse = new Button(root, SWT.NONE);
            btnBrowse.setText("..."); //$NON-NLS-1$
            btnBrowse.addSelectionListener(new SelectionAdapter() {
                @Override
                public void widgetSelected(SelectionEvent e) {
                    FileDialog dlg =
                            new FileDialog(getShell(), isNewFile ? SWT.SAVE
                                    : SWT.OPEN);
                    if (isNewFile) {
                        // Only set one file filter as a new file is being
                        // created
                        dlg.setText(Messages.FileOpenWizard_selectFile_dialog_title);
                        dlg.setFilterExtensions(new String[] { "*." //$NON-NLS-1$
                                + fileExt });
                        if (fileExtensionNames != null) {
                            dlg.setFilterNames(new String[] { fileExtensionNames[0] });
                        }
                    } else {
                        dlg.setFilterExtensions(getExtensions());
                        if (fileExtensionNames != null) {
                            dlg.setFilterNames(fileExtensionNames);
                        }
                    }
                    if (path != null) {
                        // Set initial path
                        dlg.setFilterPath(path.toString());
                    }
                    String path = dlg.open();
                    if (path != null) {
                        fileTxt.setText(path);
                        setPageComplete(validatePage());
                    }
                }
            });

            Composite optionalArea = createOptionalArea(root);
            if (optionalArea != null) {
                data = new GridData(SWT.FILL, SWT.FILL, true, true);
                data.horizontalSpan = 3;
                optionalArea.setLayoutData(data);
            }

            setPageComplete(false);
            setControl(root);
        }

        /**
         * Create an optional area under the file selection control. Subclass
         * may extend, default implementation returns <code>null</code>.
         * 
         * @param parent
         * @return
         */
        protected Composite createOptionalArea(Composite parent) {
            return null;
        }

        /**
         * Validate the selection in this page.
         */
        protected boolean validatePage() {
            String err = null;

            String filePath = fileTxt.getText();
            path = null;
            if (filePath.length() == 0) {
                err = Messages.FileOpenWizard_blankFile_error_shortdesc;
            } else {
                // Validate the path
                IPath path = new Path(filePath);

                if (isNewFile) {
                    String ext = path.getFileExtension();
                    if (ext == null || !ext.equals(fileExt)) {
                        path = path.addFileExtension(fileExt);
                    }
                }

                File file = path.toFile();

                if (file.isDirectory()
                        || path.toString()
                                .endsWith(String.valueOf(IPath.SEPARATOR))) {
                    err = Messages.FileOpenWizard_provideFileName_shortdesc;
                } else if (file.exists() && isNewFile) {
                    err =
                            Messages.FileOpenWizard_fileAlreadyExists_error_shortdesc;
                } else if (!file.exists() && !isNewFile) {
                    err =
                            Messages.FileOpenWizard_fileDoesNotExist_error_shortdesc;
                } else if (!doesParentExist(file)) {
                    err =
                            Messages.FileOpenWizard_folderDoesNotExist_error_shortdesc;
                } else {
                    this.path = path;
                }
            }

            setErrorMessage(err);
            return err == null;
        }

        /**
         * Check if the parent of this file exists.
         * 
         * @param file
         * @return
         */
        private boolean doesParentExist(File file) {
            boolean exists = false;

            if (file != null) {
                File parentFile = file.getParentFile();
                exists = parentFile != null && parentFile.isDirectory();
            }
            return exists;
        }

        /**
         * Get the file extension filter to set for the file dialog. This will
         * prefix all extensions with "*.".
         * 
         * @return
         */
        private String[] getExtensions() {
            String[] ext = new String[fileExtensionFilter.length];
            for (int x = 0; x < fileExtensionFilter.length; x++) {
                ext[x] = "*." + fileExtensionFilter[x]; //$NON-NLS-1$
            }
            return ext;
        }
    }
}
