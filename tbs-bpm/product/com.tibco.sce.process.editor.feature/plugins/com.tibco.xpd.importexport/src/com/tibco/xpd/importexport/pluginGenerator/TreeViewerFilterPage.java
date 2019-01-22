/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.importexport.pluginGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.importexport.internal.Messages;
import com.tibco.xpd.importexport.pluginGenerator.IPluginData.ITreeViewerFilterProvider;
import com.tibco.xpd.importexport.pluginGenerator.NewPluginGeneratorWizard.WizardType;
import com.tibco.xpd.resources.projectconfig.specialfolders.ISpecialFolderModel;
import com.tibco.xpd.resources.projectconfig.specialfolders.extpoint.SpecialFoldersExtensionPoint;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;

/**
 * Import/Export wizard plug-in generator's wizard page to select the wizards'
 * tree viewer filters.
 * 
 * @author njpatel
 * 
 */
public class TreeViewerFilterPage extends AbstractXpdWizardPage implements
        ITreeViewerFilterProvider {

    private CheckboxTableViewer tableViewer;

    private String specialFolderFilter = ""; //$NON-NLS-1$

    private Label fileFilterLbl;

    private Text fileFilter;

    private String fileFilterStr = ""; //$NON-NLS-1$

    private final Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>();

    /**
     * Constructor
     * 
     * @param pageName
     * @param img
     */
    protected TreeViewerFilterPage(String pageName, ImageDescriptor img) {
        super(pageName);
        if (img != null) {
            setImageDescriptor(img);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.ITreeViewerFilterProvider#getTreeViewFileExtensionFilter()
     */
    public String getTreeViewFileExtensionFilter() {
        return fileFilterStr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.importexport.pluginGenerator.IPluginData.ITreeViewerFilterProvider#getTreeViewSpecialFolderFilter()
     */
    public String getTreeViewSpecialFolderFilter() {
        return specialFolderFilter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label folderLbl = new Label(container, SWT.NONE);
        folderLbl
                .setText(Messages.TreeViewerFilterPage_specialFolderFilter_label);

        tableViewer = CheckboxTableViewer.newCheckList(container, SWT.BORDER);
        tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

        tableViewer.setContentProvider(getContentProvider());
        tableViewer.setLabelProvider(getLabelProvider());
        tableViewer.setInput(SpecialFoldersExtensionPoint.getInstance());
        tableViewer.addCheckStateListener(new ICheckStateListener() {

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse.jface.viewers.CheckStateChangedEvent)
             */
            public void checkStateChanged(CheckStateChangedEvent event) {
                Object[] checkedElements = tableViewer.getCheckedElements();

                if (checkedElements != null) {
                    int count = checkedElements.length;
                    specialFolderFilter = ""; //$NON-NLS-1$
                    // Create a comma-delimeted list of selected special folder
                    // kinds
                    for (int idx = 0; idx < count; idx++) {
                        if (checkedElements[idx] instanceof ISpecialFolderModel) {
                            specialFolderFilter += ((ISpecialFolderModel) checkedElements[idx])
                                    .getKind();

                            if (idx < count - 1) {
                                specialFolderFilter += ", "; //$NON-NLS-1$
                            }
                        }
                    }

                }

                doValidatePage();
            }
        });

        fileFilterLbl = new Label(container, SWT.NONE);
        fileFilterLbl.setText(Messages.TreeViewerFilterPage_fileFilter_label);

        fileFilter = new Text(container, SWT.BORDER);
        fileFilter.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        fileFilter.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                fileFilterStr = fileFilter.getText().trim();

                // Make sure we have a comma delimeted string
                String[] exts = fileFilterStr.split("[\\W\\s]+"); //$NON-NLS-1$
                List<String> cache = new ArrayList<String>();

                for (String ext : exts) {
                    if (ext.length() > 0) {
                        cache.add(ext);
                    }
                }

                if (!cache.isEmpty()) {
                    fileFilterStr = ""; //$NON-NLS-1$

                    for (Iterator<String> iter = cache.iterator(); iter
                            .hasNext();) {
                        fileFilterStr += iter.next();

                        if (iter.hasNext()) {
                            fileFilterStr += ", "; //$NON-NLS-1$
                        }
                    }
                }
            }
        });

        setControl(container);

        setPageComplete(false);
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            // If this is the export wizard then show the file extension filter,
            // otherwise hide it. Also update the title and message
            if (getWizardType().equals(WizardType.EXPORT)) {
                setTitle(Messages.TreeViewerFilterPage_exportWizard_title);
                setMessage(Messages.TreeViewerFilterPage_exportWizard_message);
            } else {
                setTitle(Messages.TreeViewerFilterPage_importWizard_title);
                setMessage(Messages.TreeViewerFilterPage_importWizard_message);
            }
        }
        super.setVisible(visible);
    }

    @Override
    public void dispose() {

        // Dispose off all images
        for (Image img : imageCache.values()) {
            if (img != null) {
                img.dispose();
            }
        }
        imageCache.clear();

        super.dispose();
    }

    /**
     * Get the label provider for the special folder selection tree.
     * 
     * @return
     */
    private IBaseLabelProvider getLabelProvider() {
        return new LabelProvider() {
            @Override
            public String getText(Object element) {
                String text = null;
                if (element instanceof ISpecialFolderModel) {
                    text = ((ISpecialFolderModel) element).getName();
                }

                return text != null ? text : super.getText(element);
            }

            @Override
            public Image getImage(Object element) {
                if (element instanceof ISpecialFolderModel) {
                    ISpecialFolderModel sfInfo = (ISpecialFolderModel) element;
                    ImageDescriptor imgDesc = sfInfo.getIcon();
                    if (imgDesc != null) {
                        Image img = imageCache.get(imgDesc);
                        if (img == null) {
                            img = imgDesc.createImage();
                            imageCache.put(imgDesc, img);
                        }
                        return img;
                    }
                }
                return super.getImage(element);
            }
        };
    }

    /**
     * Get the content provider for the special folder selection tree
     * 
     * @return
     */
    private IStructuredContentProvider getContentProvider() {
        return new IStructuredContentProvider() {

            public Object[] getElements(Object inputElement) {
                Object[] extensions = new Object[0];
                // Get all registered special folders
                if (inputElement instanceof SpecialFoldersExtensionPoint) {
                    SpecialFoldersExtensionPoint extPoint = (SpecialFoldersExtensionPoint) inputElement;

                    extensions = extPoint.getExtensions();
                }

                return extensions;
            }

            public void dispose() {
                // Nothing to do
            }

            public void inputChanged(Viewer viewer, Object oldInput,
                    Object newInput) {
                // Nothing to do
            }
        };
    }

    /**
     * Validate the input of this page
     */
    private void doValidatePage() {
        String errMsg = null;
        setPageComplete(false);

        if (tableViewer.getCheckedElements().length == 0) {
            errMsg = Messages.TreeViewerFilterPage_selectSpecialFolderError_message;
        }

        if (errMsg != null) {
            setErrorMessage(errMsg);
        } else {
            setErrorMessage(null);
            setPageComplete(true);
        }

    }

    /**
     * Get the selected wizard type.
     * 
     * @return
     */
    private WizardType getWizardType() {
        return ((NewPluginGeneratorWizard) getWizard()).getWizardType();
    }

}