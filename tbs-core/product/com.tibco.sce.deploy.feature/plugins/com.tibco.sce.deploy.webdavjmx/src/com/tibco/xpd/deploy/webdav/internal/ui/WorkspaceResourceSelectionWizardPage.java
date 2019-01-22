/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.deploy.webdav.internal.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.tibco.xpd.deploy.webdav.Utils;
import com.tibco.xpd.deploy.webdav.WebDAVPlugin;
import com.tibco.xpd.deploy.webdav.ui.internal.Messages;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.ui.dialogs.AbstractXpdWizardPage;
import com.tibco.xpd.ui.importexport.exportwizard.pages.ExplorerTreeViewer;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Export's main wizard page. This page will allow the user to select the xpdl
 * files to export from the projects and select the export destination. The
 * destination can either be the exports workspace folder in the project, or an
 * external path in the file system.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class WorkspaceResourceSelectionWizardPage extends AbstractXpdWizardPage {

    // Tree view control
    private ExplorerTreeViewer resourceViewer;

    // This will be set to the initial selection and subsequently to the current
    // selection in the tree
    private final IStructuredSelection selection;

    private boolean initialized = false;

    /**
     * Constructor.
     * 
     * @param selection
     *            The initial selection to be made in the tree viewer.
     * @param input
     *            The input of the tree viewer.
     * @param sorter
     *            The <code>ViewerSorter</code> to apply to the tree viewer.
     * 
     * @param filters
     *            An array of <code>ViewerFilter</code>s to apply to filter the
     *            content of the tree viewer.
     */
    public WorkspaceResourceSelectionWizardPage(IStructuredSelection selection) {
        super("ResourceSelection"); //$NON-NLS-1$
        setTitle(Messages.WorkspaceResourceSelectionWizardPage_WebDAVDeployment_title);
        setDescription(String
                .format(Messages.WorkspaceResourceSelectionWizardPage_WebDAVDeployment_shortdesc));
        this.selection = selection;
    }

    @Override
    public void dispose() {
        if (resourceViewer != null) {
            resourceViewer.dispose();
            resourceViewer = null;
        }
        super.dispose();
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
        Composite container = new Composite(parent, SWT.NONE);

        GridLayout gridLayout = new GridLayout();
        container.setLayout(gridLayout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));

        // Create the package viewer control
        createTreeControl(container);

        setControl(container);

        // Update page completion
        updatePageCompletion();
        initialized = true;
    }

    /**
     * Check page completion
     */
    protected void updatePageCompletion() {
        // Check that we have proper selection in the tree
        List<IResource> selectedDAVResources = getSelectedDAVResources();
        if (selectedDAVResources.isEmpty()) {
            // No selection
            if (initialized) {
                setErrorMessage(String
                        .format(Messages.WorkspaceResourceSelectionWizardPage_WebDAVDeployment_ResourceNotSelectedError_message,
                                Messages.WorkspaceResourceSelectionWizardPage_WebDAVSpecialFolderName));
            }
            setPageComplete(false);
            return;
        }

        setErrorMessage(null);
        setPageComplete(true);
    }

    /**
     * Create the file viewer control
     * 
     * @param container
     */
    private void createTreeControl(Composite container) {
        GridData gridData;

        // Expand/Collapse all toolbar
        final ToolBar tBar = new ToolBar(container, SWT.HORIZONTAL | SWT.FLAT);
        tBar.setLayout(new RowLayout(SWT.HORIZONTAL));
        gridData = new GridData(SWT.END, 0, false, false);
        tBar.setLayoutData(gridData);

        final ToolItem tExpandAll = new ToolItem(tBar, SWT.PUSH);
        tExpandAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry()
                .get(XpdResourcesUIConstants.EXPORT_EXPAND_ALL));
        tExpandAll
                .setToolTipText(Messages.WorkspaceResourceSelectionWizardPage_ExpandAll_button);
        tExpandAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.expandAll();
            }
        });

        final ToolItem tCollapseAll = new ToolItem(tBar, SWT.PUSH);
        tCollapseAll.setImage(XpdResourcesUIActivator.getDefault()
                .getImageRegistry()
                .get(XpdResourcesUIConstants.EXPORT_COLLAPSE_ALL));
        tCollapseAll
                .setToolTipText(Messages.WorkspaceResourceSelectionWizardPage_CollapseAll_button);
        tCollapseAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.collapseAll();
            }
        });

        IStructuredSelection initialSelection = StructuredSelection.EMPTY;
        Object input = ResourcesPlugin.getWorkspace().getRoot();
        ViewerSorter viewerSorter = new ViewerSorter();
        ViewerFilter[] viewerFilters = createViewerFilters();

        // Package tree viewer
        resourceViewer =
                new ExplorerTreeViewer(container, input, viewerSorter,
                        viewerFilters);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 2;
        gridData.widthHint = 100;
        gridData.heightHint = 150;
        resourceViewer.getControl().setLayoutData(gridData);

        resourceViewer.setInitialSelection(selection);
        resourceViewer
                .addPostSelectionChangedListener(new ISelectionChangedListener() {
                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        // Update page completion
                        updatePageCompletion();
                    }
                });

        //
        // Do not expand all (not expanding will just cause the parent nodes
        // of the selected elements to be expanded - which is what we want
        // really anyway).
        // It is a bad idea to expand all because this will always cause a get
        // children on things (like xpdl files etc) and that will always cause
        // the file to be loaded into memory. So expandAll causes every files
        // that matches the filter to be loaded into memory.
        //
        // fileViewer.expandAll();
        //

        // Container for the Select / Deselect all buttons
        final Composite cmpTreeSelect = new Composite(container, SWT.NULL);
        RowLayout selectLayout = new RowLayout();
        selectLayout.type = SWT.HORIZONTAL;
        cmpTreeSelect.setLayout(selectLayout);

        // Select All button
        final Button btnSelectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnSelectAll
                .setText(Messages.WorkspaceResourceSelectionWizardPage_SelectAll_button);
        btnSelectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.setAllChecked(true);
                updatePageCompletion();
            }
        });

        // Deselect All button
        final Button btnUnselectAll = new Button(cmpTreeSelect, SWT.PUSH);
        btnUnselectAll
                .setText(Messages.WorkspaceResourceSelectionWizardPage_DeselectAll_button);
        btnUnselectAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.setAllChecked(false);
                updatePageCompletion();
            }
        });
    }

    protected ViewerFilter[] createViewerFilters() {
        return new ViewerFilter[] {
                new SpecialFoldersOnlyFilter(
                        Collections
                                .singleton(WebDAVPlugin.WEBDAV_DEPLOYABLE_KIND)),
                new XpdNatureProjectsOnly(false) };
    }

    /**
     * @return
     */
    /* package */List<IResource> getSelectedDAVResources() {
        Object[] checkedItems = resourceViewer.getCheckedElements();
        List<IResource> davResources = new ArrayList<IResource>();
        for (Object item : checkedItems) {
            if (item instanceof IResource
                    && Utils.isInDAVSpecialFolder((IResource) item)) {
                davResources.add((IResource) item);
            }
        }
        return davResources;
    }

}
