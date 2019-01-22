/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.dbimport.wizards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.tibco.xpd.bom.dbimport.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.part.UMLDiagramEditorUtil;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.dialogs.FileSelectionBrowserControl;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.FileExtensionInclusionFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.NoFileContentFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Wizard page to select a BOM file or specify new BOM file name to be created
 * in a selected BOM special folder.
 * 
 * 
 * @author agondal
 * @since 15 Jan 2014
 */
public class BOMFileSelectionWizardPage extends AbstractXpdWizardPage implements
        Listener {

    private final IStructuredSelection selection;

    private FileSelectionBrowserControl fileSelectionControl;

    private String fileName;

    private String fileExtension;

    private Text fileNameText;

    private boolean createNewBomFile = true;

    private IPath containerPath;

    protected BOMFileSelectionWizardPage(String pageName,
            IStructuredSelection selection) {
        super(pageName);
        this.selection = selection;
        setTitle(Messages.BOMFileSelectionPage_title);
        setDescription(Messages.BOMFileSelectionPage_shortdesc);
        setFileExtension(BOMResourcesPlugin.BOM_FILE_EXTENSION);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
     * .Composite)
     */
    public void createControl(Composite parent) {

        /* Show default message when page first shown */
        setErrorMessage(null);
        setMessage(null);

        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        ViewerFilter[] viewerFilters = createViewerFilters();

        /* Create the container selection group */
        fileSelectionControl = new FileSelectionBrowserControl();
        Composite ctrl = fileSelectionControl.createControl(container);
        ctrl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        fileSelectionControl.setInitialSelection(selection);
        for (ViewerFilter filter : viewerFilters) {
            fileSelectionControl.addFilter(filter);
        }

        fileSelectionControl.setListener(this);

        Composite fileGroup = new Composite(container, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        fileGroup.setLayout(layout);
        fileGroup
                .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // Create the file input
        Label lbl = new Label(fileGroup, SWT.NONE);
        lbl.setText(Messages.BOMFileSelectionPage_FileNameLabel);

        fileNameText = new Text(fileGroup, SWT.BORDER);
        fileNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        fileNameText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

                setFileName(fileNameText.getText());
                /*
                 * check if the selection is a special folder and if the BOM
                 * file with the given name already exists
                 */
                if (selection.getFirstElement() instanceof SpecialFolder) {

                    SpecialFolder specialFolder =
                            (SpecialFolder) selection.getFirstElement();
                    IFolder folder = specialFolder.getFolder();
                    IFile bomFile = folder.getFile(fileNameText.getText());
                    if (!bomFile.exists()) {
                        setCreateNewBomFile(true);
                    } else {
                        if (BOMResourcesPlugin.BOM_FILE_EXTENSION
                                .equals(bomFile.getFileExtension())) {

                            setFileName(bomFile.getName());
                            setCreateNewBomFile(false);
                        }
                    }
                }
            }
        });
        /* Set initial file name */
        setFileName(getInitialFileName());
        fileNameText.addListener(SWT.Modify, this);

        setControl(container);
        setPageComplete(true);

        /* Set focus on the file name text control */
        fileNameText.setFocus();
    }

    /**
     * @return
     */
    private String getInitialFileName() {

        return UMLDiagramEditorUtil.getUniqueFileName(getContainerFullPath(),
                getFileName(),
                getFileExtension());
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set the file name. This will add the default file extension (if
     * specified) to the file name if one does not exist.
     * 
     * @param fileName
     *            name of new file.
     */
    public void setFileName(String fileName) {

        if (fileName == null) {

            fileName = ""; //$NON-NLS-1$
        }

        if (fileNameText != null && !fileNameText.isDisposed()) {

            fileNameText.setText(fileName);
            fileNameText.setSelection(0, fileName.length());
            this.fileName = fileName;
        }
    }

    /**
     * @return the fileExtension
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * @param fileExtension
     *            the fileExtension to set
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * @return path of the destination BOM special folder
     */
    public IPath getContainerFullPath() {

        if (containerPath == null) {
            Object sel = selection.getFirstElement();
            if (sel instanceof SpecialFolder) {
                SpecialFolder sf = (SpecialFolder) sel;
                setContainerPath(sf.getFolder().getFullPath());
            }
        }
        return containerPath;
    }

    /**
     * @param containerPath
     *            the containerPath to set
     */
    public void setContainerPath(IPath containerPath) {
        this.containerPath = containerPath;
    }

    /**
     * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
     * 
     * @param event
     */
    public void handleEvent(Event event) {

        if (event.widget instanceof Tree) {

            TreeItem[] selection = ((Tree) event.widget).getSelection();
            if (selection != null && selection.length > 0) {

                Object item = selection[0].getData();
                setErrorMessage(null);
                setPageComplete(true);
                if (item instanceof IFile) {

                    IFile bomFile = (IFile) item;
                    if (BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(bomFile
                            .getFileExtension())) {
                        setFileName(bomFile.getName());
                        setCreateNewBomFile(false);
                        setContainerPath(bomFile.getParent().getFullPath());
                    }
                } else if (item instanceof SpecialFolder) {
                    SpecialFolder sf = (SpecialFolder) item;
                    setContainerPath(sf.getFolder().getFullPath());
                } else {
                    setErrorMessage(Messages.BOMFileSelectionPage_SelectValidFileErrorMessage);
                    setPageComplete(false);
                }
            }
        }

        if (event.widget instanceof Text) {
            Text txt = (Text) event.widget;
            String fileNameText = txt.getText();
            if (fileNameText.contains(" ")) { //$NON-NLS-1$         
                setErrorMessage(Messages.ProfileSelectImportWizard_InvalidFileNameErr_shortdesc);
                setPageComplete(false);
            } else if (!fileNameText
                    .endsWith("." + BOMResourcesPlugin.BOM_FILE_EXTENSION)) { //$NON-NLS-1$            
                setErrorMessage(Messages.ProfileSelectImportWizard_InvalidFileExtensionErr_shortdesc);
                setPageComplete(false);
            } else {
                setErrorMessage(null);
                setPageComplete(true);
            }
        }
    }

    /**
     * create the filters on the tree viewer
     * 
     * @return ViewerFilter[]
     */
    private ViewerFilter[] createViewerFilters() {

        List<ViewerFilter> filters = new ArrayList<ViewerFilter>();

        filters.add(new XpdNatureProjectsOnly(false));
        filters.add(new NoFileContentFilter());
        filters.add(new SpecialFoldersOnlyFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND)));
        filters.add(new FileExtensionInclusionFilter(Collections
                .singleton(BOMResourcesPlugin.BOM_FILE_EXTENSION)));

        return filters.toArray(new ViewerFilter[filters.size()]);
    }

    /**
     * @return the createNewBomFile
     */
    public boolean isCreateNewBomFile() {
        return createNewBomFile;
    }

    /**
     * @param createNewBomFile
     *            the createNewBomFile to set
     */
    public void setCreateNewBomFile(boolean createNewBomFile) {
        this.createNewBomFile = createNewBomFile;
    }
}
