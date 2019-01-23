/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.DrillDownComposite;

import com.tibco.xpd.resources.projectconfig.SpecialFolder;

/**
 * An UI control that contains a common navigator view that allows selection of
 * a resource from the workspace. This has been created to replace the default
 * tree in a wizard/dialog that displays the default view of the workspace and
 * not the common navigator showing the special folders. This groups will be
 * typically used in a wizard page to select a resource.
 * <p>
 * Use {@link #setListener(Listener) setListener} to register a listener with
 * this control to get notifications of selections in the tree and call
 * {@link #createControl(Composite) createControl} to create the actual control.
 * </p>
 * 
 * @author njpatel
 * 
 * @since 3.3
 */
public class FileSelectionBrowserControl {

    private CommonViewer commonViewer;

    private ViewerComparator comparator;

    private int widthHint = 320;

    private int heightHint = 300;

    private Listener clientListener;

    private Object input;

    private final List<ViewerFilter> filters;

    /**
     * Listener that will delegate selection in the tree to the client listener.
     */
    private final ISelectionChangedListener changeListener =
            new ISelectionChangedListener() {

                /*
                 * (non-Javadoc)
                 * 
                 * @seeorg.eclipse.jface.viewers.ISelectionChangedListener#
                 * selectionChanged
                 * (org.eclipse.jface.viewers.SelectionChangedEvent)
                 */
                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    // Fire an event so the parent can update its controls
                    if (clientListener != null) {
                        Event changeEvent = new Event();
                        changeEvent.type = SWT.Selection;
                        changeEvent.widget = commonViewer.getControl();
                        clientListener.handleEvent(changeEvent);
                    }
                }
            };

    /**
     * Resource selection 'project explorer' style control. Use
     * {@link #setInput(Object) setInput} to set the input of the tree (the
     * workspace root will be set as input by default).
     */
    public FileSelectionBrowserControl() {
        this(SWT.DEFAULT, SWT.DEFAULT);
    }

    /**
     * Resource selection 'project explorer' style control. Use
     * {@link #setInput(Object) setInput} to set the input of the tree (the
     * workspace root will be set as input by default).
     * 
     * @param widthHint
     *            width hint for the tree view
     * @param heightHint
     *            height hint for the tree view
     */
    public FileSelectionBrowserControl(int widthHint, int heightHint) {
        if (widthHint != SWT.DEFAULT)
            this.widthHint = widthHint;
        if (heightHint != SWT.DEFAULT)
            this.heightHint = heightHint;
        // Set the workspace root as input by default
        this.input = ResourcesPlugin.getWorkspace().getRoot();
        this.filters = new ArrayList<ViewerFilter>();
    }

    /**
     * Set the listener to get notifications of selection change.
     * 
     * @param listener
     */
    public void setListener(Listener listener) {
        this.clientListener = listener;
    }

    /**
     * Set the input for the common navigator tree.
     * 
     * @param input
     */
    public void setInput(Object input) {
        Assert.isNotNull(input, "input"); //$NON-NLS-1$
        this.input = input;
    }

    /**
     * Get the current selection.
     * 
     * @return current selection.
     */
    public Object getSelection() {
        Object selection = commonViewer.getSelection();

        if (selection instanceof IStructuredSelection) {
            selection = ((IStructuredSelection) selection).getFirstElement();
        }

        return selection;
    }

    /**
     * Set the initial selection for the tree. The first item in the selection
     * will be selected.
     * 
     * @param selection
     *            initial selection.
     */
    public void setInitialSelection(IStructuredSelection selection) {
        // Don't report any events
        commonViewer.removeSelectionChangedListener(changeListener);
        try {
            if (selection != null && !selection.isEmpty()) {
                // Select item in the tree
                commonViewer.setSelection(new StructuredSelection(selection
                        .getFirstElement()),
                        true);
            }
        } finally {
            commonViewer.addSelectionChangedListener(changeListener);
        }
    }

    /**
     * Set the initial selection for the tree and reveal that item by expanding
     * it's ancestor tree
     * <p>
     * In some circumstances the
     * {@link #setInitialSelection(IStructuredSelection)} method will not work
     * even when passed a valid selection object. This is because the
     * CommonViewer relies on content provider to reliably calculate parent
     * object for a given element. In some cases this does not work (we found
     * that to be the case for Java packages for instance; and can also be
     * interfered with by plugin contributions that return objects as 'possible
     * children' to navigator content service and then fail to process the get
     * parent properly.
     * <p>
     * Therefore rather than relying on content providers 'get-parent' for
     * object to work correctly, we will recursively sift through the tree
     * looking for a tree item that represents the tree node for the given
     * selection and create a tree path from it's actual ancestor node tree and
     * use that full TreePath to set the selection.
     * 
     * @param selection
     *            data object to select.
     * 
     * @since SCF 3.6.1
     */
    public void forceSetInitialSelection(Object selection) {
        // Don't report any events
        commonViewer.removeSelectionChangedListener(changeListener);
        try {
            if (selection != null) {
                if (commonViewer.getControl() instanceof Tree) {
                    Tree tree = (Tree) commonViewer.getControl();

                    /*
                     * Must expandAll() first otherwise the tree's treeItem's
                     * will not be populated.
                     */
                    commonViewer.expandAll();

                    /* Recursively look for the selection in tree. */
                    List<Object> pathToSelection = new ArrayList<Object>();
                    TreeItem foundTreeItemForSelection =
                            recursiveFindTreeItem(tree.getItems(),
                                    selection,
                                    pathToSelection);

                    /* Collapse them all again. */
                    commonViewer.collapseAll();

                    if (foundTreeItemForSelection != null) {
                        commonViewer.setSelection(new TreeSelection(
                                new TreePath(pathToSelection.toArray())), true);
                    }
                }

            } else {
                /* Null selection */
                commonViewer.setSelection(new StructuredSelection());
            }

        } finally {
            commonViewer.addSelectionChangedListener(changeListener);
        }
    }

    /**
     * Recursively look for the selection in tree.
     * 
     * @param items
     * @param findElement
     * @param pathToSelection
     * 
     * @return tree item that represents tree node for findElement if found in
     *         which case ALSO pathToSelection list is populated with the data
     *         for each node to the found element.
     */
    private TreeItem recursiveFindTreeItem(TreeItem[] items,
            Object findElement, List<Object> pathToSelection) {
        if (items != null) {

            /* See if the selection exists on this level of the tree. */
            for (TreeItem item : items) {
                Object data = item.getData();

                if (findElement.equals(data)) {
                    pathToSelection.add(data);
                    return item;
                }
            }

            /* If not found at this level. Recurs into sub-folders. */
            for (TreeItem item : items) {
                TreeItem subItem =
                        recursiveFindTreeItem(item.getItems(),
                                findElement,
                                pathToSelection);
                if (subItem != null) {
                    pathToSelection.add(0, item.getData());
                    return subItem;
                }
            }
        }

        return null;
    }

    /**
     * Create the control tree view.
     * 
     * @param parent
     *            parent <code>Composite</code>.
     */
    public Composite createControl(Composite parent) {
        Composite root = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        root.setLayout(layout);

        DrillDownComposite drillDown = new DrillDownComposite(root, SWT.BORDER);
        GridData gData = new GridData(SWT.FILL, SWT.FILL, true, true);
        gData.heightHint = heightHint;
        gData.widthHint = widthHint;
        drillDown.setLayoutData(gData);

        commonViewer =
                new CommonViewer(ProjectExplorer.VIEW_ID, drillDown, SWT.NONE);
        commonViewer
                .setContentProvider(new CustomContentProvider(commonViewer));

        // Apply the navigator active filters
        ViewerFilter[] navigatorFilters =
                commonViewer.getNavigatorContentService().getFilterService()
                        .getVisibleFilters(true);
        for (ViewerFilter filter : navigatorFilters) {
            commonViewer.addFilter(filter);
        }

        // If any filters were added before the common viewer was created then
        // add them here and clear the list
        for (ViewerFilter filter : filters) {
            commonViewer.addFilter(filter);
        }
        filters.clear();

        drillDown.setChildTree(commonViewer);

        ViewerComparator comparator = getComparator();
        if (comparator != null) {
            commonViewer.setComparator(comparator);
        }

        commonViewer.setInput(input);

        commonViewer.addSelectionChangedListener(changeListener);

        return root;
    }

    /**
     * Add filters to filter the tree viewer.
     * 
     * @param filter
     */
    public void addFilter(ViewerFilter filter) {
        if (filter != null) {
            if (commonViewer != null && !commonViewer.getControl().isDisposed()) {
                commonViewer.addFilter(filter);
            } else {
                filters.add(filter);
            }
        }
    }

    /**
     * Set the viewer comparator.
     * 
     * @param comparator
     */
    public void setComparator(ViewerComparator comparator) {
        this.comparator = comparator;
    }

    /**
     * Get the currently set comparator. If no comparator is set then the
     * default comparator will be returned.
     * 
     * @return comparator.
     */
    public ViewerComparator getComparator() {
        // If no sorter defined then use default one
        if (comparator == null) {
            comparator = new SpecialFolderComparator();
        }
        return comparator;
    }

    /**
     * Default special folder comparator. This will set the order of display of
     * folder and special folders.
     * 
     * @author njpatel
     * 
     */
    private class SpecialFolderComparator extends ViewerComparator {
        private static final int PROJECT = 0;

        private static final int SPECIAL_FOLDER = 1;

        private static final int FOLDER = 2;

        @Override
        public int category(Object element) {
            int category = super.category(element);

            if (element instanceof IProject) {
                category = PROJECT;
            } else if (element instanceof SpecialFolder) {
                category = SPECIAL_FOLDER;
            } else if (element instanceof IFolder) {
                category = FOLDER;
            }

            return category;
        }
    }

    /**
     * Custom content provider that extends the navigator service content
     * provider. This is required to report the correct value when hasChildren
     * is called as the navigator service does not consider any filters applied
     * to the common viewer. This results in users being able to "drill-down"
     * into a container that does not show any children in the view.
     * 
     * @author njpatel
     * 
     */
    private class CustomContentProvider implements ITreeContentProvider {

        private ITreeContentProvider contentProvider;

        private final CommonViewer viewer;

        public CustomContentProvider(CommonViewer viewer) {
            this.viewer = viewer;
            contentProvider =
                    viewer.getNavigatorContentService()
                            .createCommonContentProvider();
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            return contentProvider.getChildren(parentElement);
        }

        @Override
        public Object getParent(Object element) {
            return contentProvider.getParent(element);
        }

        @Override
        public boolean hasChildren(Object element) {
            Object[] children = getChildren(element);

            if (children != null && children.length > 0) {
                // Apply filters to check if this parent actually has children
                // in the view
                for (ViewerFilter filter : viewer.getFilters()) {
                    children = filter.filter(viewer, element, children);
                    if (children == null || children.length == 0) {
                        break;
                    }
                }

                return children != null && children.length > 0;
            }

            return false;
        }

        @Override
        public Object[] getElements(Object inputElement) {
            return contentProvider.getElements(inputElement);
        }

        @Override
        public void dispose() {
            contentProvider.dispose();
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            contentProvider.inputChanged(viewer, oldInput, newInput);
        }

    }
}
