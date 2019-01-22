/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.presentation.resources.ui.internal.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.xpd.presentation.resources.ui.PresentationResourcesUIActivator;
import com.tibco.xpd.presentation.resources.ui.internal.Messages;
import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.projectconfig.ProjectConfig;
import com.tibco.xpd.resources.projectconfig.SpecialFolder;
import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.XpdResourcesUIConstants;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.SpecialFoldersOnlyFilter;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.XpdNatureProjectsOnly;

/**
 * Picks a resource from presentation special folder.
 * 
 * @author Jan Arciuchiewicz
 */
public class PresentationResourcePicker extends SelectionStatusDialog {

    /** Id for clear button. */
    private static final int CLEAR_ID = 50;

    /** Default width of the tree in unit of characters. */
    private static int DEFAULT_WIDTH = 90;

    /** Default height of the tree in unit of characters. */
    private static int DEFAULT_HEIGHT = 18;

    // Tree view control
    private ProjectExplorerTreeViewer resourceViewer;

    private final String initialPath;

    private final IProject project;

    /**
     * Creates picker.
     * 
     * @param parent
     *            parent shell.
     * @param initialPath
     *            presentation special folder relative path to a resource to
     *            preselect (for example "folder/file.txt").
     * @param project
     *            context project or null to display all XPD projects.
     */
    public PresentationResourcePicker(Shell parent, String initialPath,
            IProject project) {
        super(parent);
        this.initialPath = initialPath;
        this.project = project;
        setTitle(Messages.PresentationResourcePicker_PresentationResourcePicker_title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite root = (Composite) super.createDialogArea(parent);
        createTreeControl(root);
        return root;
    }

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent,
                IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL,
                true);
        createButton(parent,
                CLEAR_ID,
                Messages.PresentationResourcePicker_Clear_button,
                false);
        createButton(parent,
                IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL,
                false);
    }

    /**
     * (non-Javadoc) Method declared on Dialog.
     */
    @Override
    protected void buttonPressed(int buttonId) {
        super.buttonPressed(buttonId);
        if (buttonId == CLEAR_ID) {
            clearPressed();
        }
    }

    /**
     * Clear button pressed.
     */
    protected void clearPressed() {
        setResult(Collections.emptyList());
        setReturnCode(OK);
        close();
    }

    /**
     * Create the file viewer control
     * 
     * @param container
     */
    private void createTreeControl(Composite container) {
        GridData gridData;

        GridLayout containerLayout = (GridLayout) container.getLayout();
        containerLayout.numColumns = 2;
        containerLayout.makeColumnsEqualWidth = false;
        setMessage(Messages.PresentationResourcePicker_PresentationResourcePicker_message);
        Label messageArea = createMessageArea(container);
        GridDataFactory.fillDefaults().applyTo(messageArea);

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
                .setToolTipText(Messages.PresentationResourcePicker_ExpandAll_tooltip);
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
                .setToolTipText(Messages.PresentationResourcePicker_CollapseAll_tooltip);
        tCollapseAll.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                resourceViewer.collapseAll();
            }
        });

        Object input =
                (project != null) ? project : ResourcesPlugin.getWorkspace()
                        .getRoot();
        ViewerSorter viewerSorter = new ViewerSorter();
        ViewerFilter[] viewerFilters = createViewerFilters();

        // Package tree viewer
        resourceViewer =
                new ProjectExplorerTreeViewer(container, input, viewerSorter,
                        viewerFilters);
        gridData = new GridData(GridData.FILL_BOTH);
        gridData.horizontalSpan = 2;
        gridData.widthHint = convertWidthInCharsToPixels(DEFAULT_WIDTH);
        gridData.heightHint = convertHeightInCharsToPixels(DEFAULT_HEIGHT);
        resourceViewer.getControl().setLayoutData(gridData);

        // resourceViewer.setInitialSelection(selection);
        resourceViewer
                .addPostSelectionChangedListener(new ISelectionChangedListener() {
                    public void selectionChanged(SelectionChangedEvent event) {
                        // Update page completion
                        updatePageCompletion();
                    }
                });
        resourceViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                if (isValidSelection().isOK()) {
                    okPressed();
                }
            }
        });
        setInitalSelection();
    }

    /**
     * Sets inital selection.
     */
    protected void setInitalSelection() {
        if (project != null) {
            IResource initialResource = null;
            ProjectConfig config =
                    XpdResourcesPlugin.getDefault().getProjectConfig(project);
            if (config != null) {
                for (SpecialFolder sf : config
                        .getSpecialFolders()
                        .getFoldersOfKind(PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND)) {
                    if (sf != null && sf.getFolder() != null) {
                        IResource member =
                                sf.getFolder().findMember(initialPath);
                        if (member != null) {
                            initialResource = member;

                        }
                    }
                }
            }
            if (initialResource != null) {
                StructuredSelection selection =
                        new StructuredSelection(initialResource);
                resourceViewer.setSelection(selection, true);
            }
        }
    }

    protected ViewerFilter[] createViewerFilters() {
        return new ViewerFilter[] {
                new SpecialFoldersOnlyFilter(
                        Collections
                                .singleton(PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND)),
                new XpdNatureProjectsOnly(false) };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void computeResult() {
        IStructuredSelection selection =
                ((IStructuredSelection) resourceViewer.getSelection());
        Object firstElement = selection.getFirstElement();
        if (firstElement instanceof IResource) {
            IResource res = ((IResource) firstElement);
            String sfRelativePath =
                    SpecialFolderUtil
                            .getSpecialFolderRelativePath(res,
                                    PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND)
                            .toPortableString();
            setResult(Arrays.asList(sfRelativePath));
        }
    }

    protected IStatus isValidSelection() {
        IStructuredSelection selection =
                ((IStructuredSelection) resourceViewer.getSelection());
        if (selection.size() == 1
                && selection.getFirstElement() instanceof IResource) {
            IResource r = (IResource) selection.getFirstElement();
            List<SpecialFolder> presentationSFs =
                    SpecialFolderUtil
                            .getAllSpecialFoldersOfKind(r.getProject(),
                                    PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND);
            for (SpecialFolder sf : presentationSFs) {
                IFolder f = sf.getFolder();
                if (f != null && f.getFullPath().isPrefixOf(r.getFullPath())) {
                    return Status.OK_STATUS;
                }
            }
        }
        return new Status(IStatus.ERROR,
                PresentationResourcesUIActivator.PLUGIN_ID,
                Messages.PresentationResourcePicker_IncorrectSelection_message);
    }

    /**
     * Check page completion.
     */
    protected void updatePageCompletion() {
        updateStatus(isValidSelection());
    }

    private static class ProjectExplorerTreeViewer extends TreeViewer {

        /**
         * Project Explorer Viewer ID
         */
        private static final String PROJECT_EXPLORER_VIEWER_ID =
                "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

        private INavigatorContentService service = null;

        /**
         * Constructor.
         * 
         * @param container
         *            Parent <code>Composite</code> of this tree viewer.
         * @param input
         *            The input of the tree viewer.
         * @param sorter
         *            The <code>ViewerSorter</code> to apply to the tree viewer.
         * 
         * @param filters
         *            Array of <code>ViewerFilter</code> objects to apply to the
         *            tree viewer to filter the content.
         * 
         */
        public ProjectExplorerTreeViewer(Composite container, Object input,
                ViewerSorter sorter, ViewerFilter[] filters) {
            super(container);
            // Get the navigator content service
            NavigatorContentServiceFactory fact =
                    NavigatorContentServiceFactory.INSTANCE;
            service =
                    fact.createContentService(PROJECT_EXPLORER_VIEWER_ID, this);

            if (service != null) {
                setContentProvider(service.createCommonContentProvider());
                setLabelProvider(service.createCommonLabelProvider());
                // Apply the viewer sorter
                setSorter(sorter);
                // Apply the filters
                if (filters != null) {
                    for (ViewerFilter filter : filters) {
                        addFilter(filter);
                    }
                }
                setInput(input);
            }
        }

        /**
         * Dispose of resources
         */
        public void dispose() {
            if (service != null) {
                service.dispose();
                service = null;
            }
        }
    }
}