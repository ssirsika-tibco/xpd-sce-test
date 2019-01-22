/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.deploy.webdav.DeploymentConstants;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Wizard page for selecting workspace files to be deployed.
 * @author TIBCO Software, Inc.
 */
public class SelectModulesPage extends AbstractXpdWizardPage {

    /** text box for the file path. */
    private Text fileText;
    /** flag to detect if page is initialized or not. */
    private boolean initialized;
    /** File Browser Button. */
    private Button fileBrowseButton;

    /**
     * Constructs a new SelectModulesPage.
     */
    public SelectModulesPage() {
        super(Messages.SelectModulesPage_super_label);
        setTitle(Messages.SelectModulesPage_page_title);
        setDescription(Messages.SelectModulesPage_page_description);
    }

    /**
     * Implements org.eclipse.jface.dialogs.IDialigPage.createControl().
     * @param parent - composite parent for control.
     */
    public final void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        gridLayout.verticalSpacing = 10;
        composite.setLayout(gridLayout);

        Group modulesGroup = new Group(composite, SWT.NULL);
        modulesGroup.setText(Messages.SelectModulesPage_file_selection_label);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(modulesGroup);
        GridLayout paramGroupLayout = new GridLayout();
        paramGroupLayout.numColumns = 3;
        modulesGroup.setLayout(paramGroupLayout);

        Label fileLabel = new Label(modulesGroup, SWT.NONE);
        fileLabel.setText(Messages.SelectModulesPage_file_label);
        GridDataFactory.swtDefaults().applyTo(fileLabel);

        fileText = new Text(modulesGroup, SWT.SINGLE | SWT.BORDER);
        GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(fileText);
        fileText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                if (initialized) {
                    setPageComplete(validatePage(true));
                }
            }
        });

        fileBrowseButton = new Button(modulesGroup, SWT.PUSH);
        fileBrowseButton.setText(Messages.SelectModulesPage_browse_button_label);
        GridDataFactory.swtDefaults().applyTo(fileBrowseButton);
        fileBrowseButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                CustomResourceSelectionDialog dialog = new CustomResourceSelectionDialog(getShell(), ResourcesPlugin.getWorkspace()
                    .getRoot(), Messages.SelectModulesPage_resource_selection_label);
                dialog.setInitialSelections(new Object[] {});
                // dialog.setInitialSelections(getFormFolders());
                if (dialog.open() == Window.OK) {
                    Object[] result = dialog.getResult();

                    /*
                     * We need here either single source file or comma delimited list of files or folder in case all files are
                     * selected from the folder.
                     */
                    String fileListStr = getStringForSelection(result);
                    fileText.setText(fileListStr);

                }
            }
        });

        initializePageDefaults();
        setPageComplete(validatePage(false));
        setControl(composite);
    }

    /**
     *
     * Return the folder path for the Parent folder if all members within the folder are selected.
     * @param file - selected file
     * @param noOfSelectedFiles - no of files in the folder
     * @return folder path for the parent folder.
     * @since 1.0
     */
    private String getParentFolderPath(IFile file, int noOfSelectedFiles) {

        IFolder folder = (IFolder)file.getParent();
        IResource[] children;
        try {
            children = folder.members();
        } catch (CoreException e) {
            e.printStackTrace();
            return null;
        }
        if (children.length == noOfSelectedFiles) {
            return folder.getFullPath().toPortableString();
        } else {
            return null;
        }

    }

    /**
     * Gets the List of IFile resources for the selected files in the wizard.
     * @return List of selected files.
     */
    public List<IFile> getSelectedFiles() {

        List<IFile> list = new ArrayList<IFile>();
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        String selectedFilePath = fileText.getText().trim();

        String[] filePaths = selectedFilePath.split(DeploymentConstants.FILE_PATH_DELIMITER);
        for (String element : filePaths) {
            IResource member = root.findMember(element);
            if (member instanceof IFile) {
                list.add((IFile)member);
            } else if (member instanceof IFolder) {
                IFolder folder = (IFolder)member;
                try {
                    IResource[] children = folder.members();
                    for (IResource element2 : children) {
                        if (element2 instanceof IFile) {
                            list.add((IFile)element2);
                        }
                    }
                } catch (CoreException e) {
                    e.printStackTrace();
                }
            }
        }
        if (filePaths.length > 0) {
            return list;
        } else {
            return Collections.emptyList();
        }

    }

    /**
     * Construct the delimited string concatenating list of files.
     * @param files List of files.
     * @return delimited string
     */
    private String getStringForSelection(Object[] files) {

        StringBuffer buff = new StringBuffer();

        // Check if all files in a folder are selected or not.
        // Need to return the folder path in that case.

        String parentFolderPath;
        parentFolderPath = getParentFolderPath((IFile)files[0], files.length);
        if (parentFolderPath != null) {
            return parentFolderPath;
        }
        // Get the delimited list of filepaths
        for (Object element : files) {
            if (element instanceof IFile) {
                IFile file = (IFile)element;
                if (buff.length() == 0) {
                    buff.append(file.getFullPath().toPortableString());
                } else {
                    buff.append(DeploymentConstants.FILE_PATH_DELIMITER);
                    buff.append(file.getFullPath().toPortableString());
                }
                String p1 = file.getFullPath().toPortableString();
            }
        }

        return buff.toString();

    }
    /**
     * Helper method for setting the page defaults.
     * @since 1.0
     */
    private void initializePageDefaults() {
        initialized = true;
    }

    /**
     *
     * Validates the selection within the wizard.
     * @param showMessages - true if you want to display the error message.
     * @return true if the selected resources are valid.
     * @since 1.0
     */
    private boolean validatePage(boolean showMessages) {
        setMessage(null);
        setErrorMessage(null);
        // errors
        if (fileText.getText().trim().length() == 0) {
            if (showMessages) {
                setErrorMessage(Messages.SelectModulesPage_selection_empty_label);
            }
            return false;
        }
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        String selectedFilePath = fileText.getText().trim();
        if (selectedFilePath.indexOf(DeploymentConstants.FILE_PATH_DELIMITER) != -1) {

            String[] filePaths = selectedFilePath.split(DeploymentConstants.FILE_PATH_DELIMITER);
            for (String element : filePaths) {
                IResource member = root.findMember(element);
                if (member == null || !member.exists() || !(member instanceof IFile)) {
                    if (showMessages) {
                        setErrorMessage(Messages.SelectModulesPage_file_does_not_exist_label + " " + element); //$NON-NLS-1$
                    }
                    return false;
                }
            }
        } else {
            IResource member = root.findMember(selectedFilePath);
            if (member == null || !member.exists() || !(member instanceof IFile || member instanceof IFolder)) {
                if (showMessages) {
                    setErrorMessage(Messages.SelectModulesPage_file_does_not_exist_label + " " + selectedFilePath); //$NON-NLS-1$
                }
                return false;
            }
        }
        // warnings

        // no warnings and errors
        setMessage(null);
        setErrorMessage(null);
        return true;
    }
}
