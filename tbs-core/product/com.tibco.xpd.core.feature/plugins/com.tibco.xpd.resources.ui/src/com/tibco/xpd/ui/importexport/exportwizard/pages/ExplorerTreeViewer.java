/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.ui.importexport.exportwizard.pages;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

/**
 * Checkbox tree viewer with the project explorer view. The content of the tree
 * will be determined by the input to the constructors
 * <code>{@link #ExplorerTreeViewer(Composite, Object, ViewerSorter, ViewerFilter[])}</code>
 * .
 * 
 * @author njpatel
 */
public class ExplorerTreeViewer extends CommonViewer implements
        ICheckStateListener, ICheckable {

    /**
     * Project Explorer Viewer ID
     */
    private static final String PROJECT_EXPLORER_VIEWER_ID =
            "org.eclipse.ui.navigator.ProjectExplorer"; //$NON-NLS-1$

    private ITreeContentProvider contentProvider = null;

    private final Object input;

    private INavigatorContentService service = null;

    private final ViewerSorter sorter;

    private final ViewerFilter[] filters;

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
    public ExplorerTreeViewer(Composite container, Object input,
            ViewerSorter sorter, ViewerFilter[] filters) {
        super(PROJECT_EXPLORER_VIEWER_ID, container, SWT.CHECK | SWT.BORDER);

        this.sorter = sorter;
        this.filters = filters;
        this.input = input;

        // set the label and content providers
        setProvidersFiltersAndSorter();

        addCheckStateListener(this);
    }

    /**
     * Sets the selected files as checked in the tree view<br>
     * 
     * @param selection
     *            IStructuredSelection object containing selected elements in
     *            the workspace
     */
    public void setInitialSelection(IStructuredSelection selection) {
        setSelection(selection, true);

        // Check and reveal each item in the initial selection
        for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
            Object item = iter.next();

            if (setChecked(item, true)) {
                updateCheckPath(item, true);
                // Reveal all children of the selected item
                revealAllChildren(item);
            }
        }
    }

    /**
     * Returns the list of selected files
     * 
     * @see #getSelectedElements()
     * 
     * @return IStructuredSelection List of files selected, empty list if no
     *         files selected
     */
    public IStructuredSelection getSelectedFiles() {
        Object[] checkedItems = getCheckedElements();
        List<IFile> lRet = new ArrayList<IFile>();

        for (Object item : checkedItems) {
            if (item instanceof IFile)
                lRet.add((IFile) item);
        }

        // If we have no items then return null
        return lRet.size() > 0 ? new StructuredSelection(lRet)
                : StructuredSelection.EMPTY;
    }

    /**
     * Get the elements selected in this tree viewer. This will only return the
     * selected "leaf" elements.
     * 
     * @see #getSelectedFiles()
     * 
     * @return selected items or empty selection if nothing selected.
     * @since 3.3
     */
    public IStructuredSelection getSelectedElements() {
        if (getTree() != null) {
            TreeItem[] items = getTree().getItems();
            if (items != null) {
                List<TreeItem> selectedItems = new ArrayList<TreeItem>();
                for (TreeItem item : items) {
                    selectedItems.addAll(getSelectedItems(item));
                }

                if (!selectedItems.isEmpty()) {
                    List<Object> selectedObjs = new ArrayList<Object>();
                    for (TreeItem item : selectedItems) {
                        Object data = item.getData();
                        if (data != null) {
                            selectedObjs.add(data);
                        }
                    }

                    if (!selectedObjs.isEmpty()) {
                        return new StructuredSelection(selectedObjs);
                    }
                }
            }
        }

        return StructuredSelection.EMPTY;
    }

    /**
     * Find the selected "leaf" elements of the tree item.
     * 
     * @param item
     * @return
     * @since 3.3
     */
    private List<TreeItem> getSelectedItems(TreeItem item) {
        List<TreeItem> items = new ArrayList<TreeItem>();

        if (item != null && item.getChecked()) {
            TreeItem[] children = item.getItems();
            if (children == null || children.length == 0) {
                // This is a leaf
                if (item.getChecked()) {
                    // This is a selected item
                    items.add(item);
                }
            } else {
                // Process children
                for (TreeItem child : children) {
                    items.addAll(getSelectedItems(child));
                }
            }
        }

        return items;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ICheckStateListener#checkStateChanged(org.eclipse
     * .jface.viewers.CheckStateChangedEvent)
     */
    @Override
    public void checkStateChanged(CheckStateChangedEvent event) {

        Object elem = event.getElement();
        boolean checked = event.getChecked();

        // If this item is gray checked then set it to complete checked rather
        // than unchecked
        if (getGrayed(elem)) {
            // Ungray the item
            setGrayed(elem, false);
            checked = true;
        }

        setChecked(elem, checked);
        // Update the parent and children of this object
        updateCheckPath(elem, checked);
    }

    /**
     * Check/uncheck all elements in the node
     * 
     * @param checked
     */
    public void setAllChecked(final boolean checked) {

        updateCheckPath(input, checked);
    }

    /**
     * Dispose of resources
     */
    @Override
    public void dispose() {

        // if (checkStateListener != null) {
        // removeCheckStateListener(checkStateListener);
        // }

        if (service != null) {
            service.dispose();
            service = null;
        }
    }

    /**
     * Set up the content and label provider and apply sort and filters for the
     * purpose of the export wizard
     */
    private void setProvidersFiltersAndSorter() {
        // Get the navigator content service
        NavigatorContentServiceFactory fact =
                NavigatorContentServiceFactory.INSTANCE;
        service = fact.createContentService(PROJECT_EXPLORER_VIEWER_ID, this);

        if (service != null) {
            // Get the content provider
            contentProvider = service.createCommonContentProvider();

            // Set the content and label provider
            setContentProvider(contentProvider);
            setLabelProvider(service.createCommonLabelProvider());

            // Apply the viewer sorter
            setSorter(sorter);

            // Apply the filters
            if (filters != null) {
                for (ViewerFilter filter : filters) {
                    addFilter(filter);
                }
            }

            // Set the workspace root as the input for the tree viewer
            setInput(input);
        }
    }

    /**
     * Update the checkbox for the children and parent of the given element
     * 
     * @param element
     * @param checked
     */
    protected void updateCheckPath(Object element, final boolean checked) {
        // Update the element's children and their children
        updateChildren(element, checked);
        // Update the parent of the element
        updateParent(element);
    }

    /**
     * Update the check state of the children of the given element
     * 
     * @param element
     * @param check
     */
    private void updateChildren(Object element, final boolean check) {

        if (element != null) {
            Object[] children = getFilteredChildren(element);

            if (children != null && children.length > 0) {
                for (Object child : children) {
                    // Ungray the child first in case it is gray
                    setGrayed(child, false);
                    setChecked(child, check);

                    // Update any of this object's children too
                    updateChildren(child, check);
                }
            } else {
                children = getRawChildren(element);
                if (children != null && children.length > 0) {
                    for (Object child : children) {
                        setChecked(child, check);
                    }
                }
            }
        }
    }

    /**
     * Update the check status of the element's parent
     * 
     * @param element
     */
    private void updateParent(Object element) {

        // Get the parent of this element
        Object parent = getParentElement(element);

        if (parent != null && parent != input) {
            final int unchecked = 0x01;
            final int checked = 0x02;
            final int graychecked = 0x04;
            int status = 0x00;
            /*
             * Get all the children of this parent and if they are all checked
             * then set the parent to checked, otherwise set the parent to grey
             * checked if only some are checked
             */
            Object[] children = getFilteredChildren(parent);

            if (children != null) {
                for (Object child : children) {
                    // Check the status of the child
                    if (getGrayed(child)) {
                        // Child is grayed
                        status |= graychecked;
                    } else if (getChecked(child)) {
                        // Child is checked
                        status |= checked;
                    } else {
                        // Child is unchecked
                        status |= unchecked;
                    }

                    /*
                     * If a grey checked child is found then set parent to gray
                     * and stop searching. If some children so far are checked
                     * and unchecked then set parent to gray and stop searching.
                     * Otherwise, carry on with the rest of the children
                     */
                    if ((status & graychecked) == graychecked
                            || (status & (checked | unchecked)) == (checked | unchecked)) {
                        status = graychecked;
                        break;
                    }
                }
            }

            // Update the status of the parent
            if (status > 0) {
                if (status == graychecked) {
                    // Parent gray checked
                    setGrayChecked(parent, true);
                } else {
                    // Parent checked or unchecked, disable grayed
                    setGrayed(parent, false);
                    setChecked(parent, status == checked);
                }
            }
            // Update the grandparent
            updateParent(parent);
        }
    }

    /**
     * Reveal all children of the given element
     * 
     * @param element
     */
    private void revealAllChildren(Object element) {
        if (element != null) {
            Object[] children = getFilteredChildren(element);

            if (children == null || (children != null && children.length == 0)) {
                // End of the branch so reveal the element
                reveal(element);
            } else {
                // Recurse into the children
                for (Object child : children) {
                    revealAllChildren(child);
                }
            }
        }
    }

    /*
     * XPD-4398: ClassCastException in WorkingSetsContentProvider.init(). Reason
     * -Bundle org.eclipse.ui.navigator_3.5 [porting Studio to Eclipse 3.7
     * Indigo] has changes , where in WorkingSetsContentProvider.init() method
     * casts the viewer to CommonViewer, which fails for ExplorerTreeViewer .
     * Changes in ExplorerTreeViewer to extend CommonViewer and implement
     * ICheckable interface and other methods[taken from CheckboxTreeViewer] to
     * support a CheckboxTreeViewer.
     */

    /**
     * List of check state listeners (element type:
     * <code>ICheckStateListener</code>).
     */
    private ListenerList checkStateListeners = new ListenerList();

    /**
     * Provides the desired state of the check boxes.
     */
    private ICheckStateProvider checkStateProvider;

    /**
     * Last item clicked on, or <code>null</code> if none.
     */
    private TreeItem lastClickedItem = null;

    /*
     * (non-Javadoc) Method declared on ICheckable.
     */
    @Override
    public void addCheckStateListener(ICheckStateListener listener) {
        checkStateListeners.add(listener);
    }

    /**
     * Sets the {@link ICheckStateProvider} for this {@link CheckboxTreeViewer}.
     * The check state provider will supply the logic for deciding whether the
     * check box associated with each item should be checked, grayed or
     * unchecked.
     * 
     * @param checkStateProvider
     *            The provider.
     * @since 3.5
     */
    public void setCheckStateProvider(ICheckStateProvider checkStateProvider) {
        this.checkStateProvider = checkStateProvider;
        refresh();
    }

    /*
     * Extends this method to update check box states.
     */
    @Override
    protected void doUpdateItem(Item item, Object element) {
        super.doUpdateItem(item, element);
        if (!item.isDisposed() && checkStateProvider != null) {
            setChecked(element, checkStateProvider.isChecked(element));
            setGrayed(element, checkStateProvider.isGrayed(element));
        }
    }

    /**
     * Notifies any check state listeners that the check state of an element has
     * changed. Only listeners registered at the time this method is called are
     * notified.
     * 
     * @param event
     *            a check state changed event
     * 
     * @see ICheckStateListener#checkStateChanged
     */
    protected void fireCheckStateChanged(final CheckStateChangedEvent event) {
        Object[] array = checkStateListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ICheckStateListener l = (ICheckStateListener) array[i];
            SafeRunnable.run(new SafeRunnable() {
                @Override
                public void run() {
                    l.checkStateChanged(event);
                }
            });
        }

    }

    /*
     * (non-Javadoc) Method declared on ICheckable.
     */
    @Override
    public boolean getChecked(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TreeItem) {
            return ((TreeItem) widget).getChecked();
        }
        return false;
    }

    /**
     * Returns a list of checked elements in this viewer's tree, including
     * currently hidden ones that are marked as checked but are under a
     * collapsed ancestor.
     * <p>
     * This method is typically used when preserving the interesting state of a
     * viewer; <code>setCheckedElements</code> is used during the restore.
     * </p>
     * 
     * @return the array of checked elements
     * 
     * @see #setCheckedElements
     */
    public Object[] getCheckedElements() {
        ArrayList v = new ArrayList();
        Control tree = getControl();
        internalCollectChecked(v, tree);
        return v.toArray();
    }

    /**
     * Returns the grayed state of the given element.
     * 
     * @param element
     *            the element
     * @return <code>true</code> if the element is grayed, and
     *         <code>false</code> if not grayed
     */
    public boolean getGrayed(Object element) {
        Widget widget = findItem(element);
        if (widget instanceof TreeItem) {
            return ((TreeItem) widget).getGrayed();
        }
        return false;
    }

    /**
     * Returns a list of grayed elements in this viewer's tree, including
     * currently hidden ones that are marked as grayed but are under a collapsed
     * ancestor.
     * <p>
     * This method is typically used when preserving the interesting state of a
     * viewer; <code>setGrayedElements</code> is used during the restore.
     * </p>
     * 
     * @return the array of grayed elements
     * 
     * @see #setGrayedElements
     */
    public Object[] getGrayedElements() {
        List result = new ArrayList();
        internalCollectGrayed(result, getControl());
        return result.toArray();
    }

    /*
     * (non-Javadoc) Method declared on StructuredViewer.
     */
    @Override
    protected void handleDoubleSelect(SelectionEvent event) {

        if (lastClickedItem != null) {
            TreeItem item = lastClickedItem;
            Object data = item.getData();
            if (data != null) {
                boolean state = item.getChecked();
                setChecked(data, !state);
                fireCheckStateChanged(new CheckStateChangedEvent(this, data,
                        !state));
            }
            lastClickedItem = null;
        } else {
            super.handleDoubleSelect(event);
        }
    }

    /*
     * (non-Javadoc) Method declared on StructuredViewer.
     */
    @Override
    protected void handleSelect(SelectionEvent event) {

        lastClickedItem = null;
        if (event.detail == SWT.CHECK) {
            TreeItem item = (TreeItem) event.item;
            lastClickedItem = item;
            super.handleSelect(event);

            Object data = item.getData();
            if (data != null) {
                fireCheckStateChanged(new CheckStateChangedEvent(this, data,
                        item.getChecked()));
            }
        } else {
            super.handleSelect(event);
        }
    }

    /**
     * Gathers the checked states of the given widget and its descendents,
     * following a pre-order traversal of the tree.
     * 
     * @param result
     *            a writable list of elements (element type: <code>Object</code>
     *            )
     * @param widget
     *            the widget
     */
    private void internalCollectChecked(List result, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem && ((TreeItem) item).getChecked()) {
                Object data = item.getData();
                if (data != null) {
                    result.add(data);
                }
            }
            internalCollectChecked(result, item);
        }
    }

    /**
     * Gathers the grayed states of the given widget and its descendents,
     * following a pre-order traversal of the tree.
     * 
     * @param result
     *            a writable list of elements (element type: <code>Object</code>
     *            )
     * @param widget
     *            the widget
     */
    private void internalCollectGrayed(List result, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];
            if (item instanceof TreeItem && ((TreeItem) item).getGrayed()) {
                Object data = item.getData();
                if (data != null) {
                    result.add(data);
                }
            }
            internalCollectGrayed(result, item);
        }
    }

    /**
     * Sets the checked state of all items to correspond to the given set of
     * checked elements.
     * 
     * @param checkedElements
     *            the set (element type: <code>Object</code>) of elements which
     *            are checked
     * @param widget
     *            the widget
     */
    private void internalSetChecked(Hashtable checkedElements, Widget widget) {
        Item[] items = getChildren(widget);
        for (int i = 0; i < items.length; i++) {
            TreeItem item = (TreeItem) items[i];
            Object data = item.getData();
            if (data != null) {
                boolean checked = checkedElements.containsKey(data);
                if (checked != item.getChecked()) {
                    item.setChecked(checked);
                }
            }
            internalSetChecked(checkedElements, item);
        }
    }

    /*
     * (non-Javadoc) Method declared on ICheckable.
     */
    @Override
    public void removeCheckStateListener(ICheckStateListener listener) {
        checkStateListeners.remove(listener);
    }

    /*
     * (non-Javadoc) Method declared on ICheckable.
     */
    @Override
    public boolean setChecked(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            ((TreeItem) widget).setChecked(state);
            return true;
        }
        return false;
    }

    /**
     * Sets the checked state for the children of the given item.
     * 
     * @param item
     *            the item
     * @param state
     *            <code>true</code> if the item should be checked, and
     *            <code>false</code> if it should be unchecked
     */
    private void setCheckedChildren(Item item, boolean state) {
        createChildren(item);
        Item[] items = getChildren(item);
        if (items != null) {
            for (int i = 0; i < items.length; i++) {
                Item it = items[i];
                if (it.getData() != null && (it instanceof TreeItem)) {
                    TreeItem treeItem = (TreeItem) it;
                    treeItem.setChecked(state);
                    setCheckedChildren(treeItem, state);
                }
            }
        }
    }

    /**
     * Sets which elements are checked in this viewer's tree. The given list
     * contains the elements that are to be checked; all other elements are to
     * be unchecked. Does not fire events to check state listeners.
     * <p>
     * This method is typically used when restoring the interesting state of a
     * viewer captured by an earlier call to <code>getCheckedElements</code>.
     * </p>
     * 
     * @param elements
     *            the array of checked elements
     * @see #getCheckedElements
     */
    public void setCheckedElements(Object[] elements) {
        assertElementsNotNull(elements);
        Hashtable checkedElements = new Hashtable(elements.length * 2 + 1);
        for (int i = 0; i < elements.length; ++i) {
            Object element = elements[i];
            // Ensure item exists for element
            internalExpand(element, false);
            checkedElements.put(element, element);
        }
        Control tree = getControl();
        tree.setRedraw(false);
        internalSetChecked(checkedElements, tree);
        tree.setRedraw(true);
    }

    /**
     * Sets the grayed state for the given element in this viewer.
     * 
     * @param element
     *            the element
     * @param state
     *            <code>true</code> if the item should be grayed, and
     *            <code>false</code> if it should be ungrayed
     * @return <code>true</code> if the gray state could be set, and
     *         <code>false</code> otherwise
     */
    public boolean setGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            ((TreeItem) widget).setGrayed(state);
            return true;
        }
        return false;
    }

    /**
     * Check and gray the selection rather than calling both setGrayed and
     * setChecked as an optimization. Does not fire events to check state
     * listeners.
     * 
     * @param element
     *            the item being checked
     * @param state
     *            a boolean indicating selection or deselection
     * @return boolean indicating success or failure.
     */
    public boolean setGrayChecked(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setChecked(state);
            item.setGrayed(state);
            return true;
        }
        return false;
    }

    /**
     * Sets the grayed state for the given element and its parents in this
     * viewer.
     * 
     * @param element
     *            the element
     * @param state
     *            <code>true</code> if the item should be grayed, and
     *            <code>false</code> if it should be ungrayed
     * @return <code>true</code> if the element is visible and the gray state
     *         could be set, and <code>false</code> otherwise
     * @see #setGrayed
     */
    public boolean setParentsGrayed(Object element, boolean state) {
        Assert.isNotNull(element);
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setGrayed(state);
            item = item.getParentItem();
            while (item != null) {
                item.setGrayed(state);
                item = item.getParentItem();
            }
            return true;
        }
        return false;
    }

    /**
     * Sets the checked state for the given element and its visible children in
     * this viewer. Assumes that the element has been expanded before. To
     * enforce that the item is expanded, call <code>expandToLevel</code> for
     * the element. Does not fire events to check state listeners.
     * 
     * @param element
     *            the element
     * @param state
     *            <code>true</code> if the item should be checked, and
     *            <code>false</code> if it should be unchecked
     * @return <code>true</code> if the checked state could be set, and
     *         <code>false</code> otherwise
     */
    public boolean setSubtreeChecked(Object element, boolean state) {
        Widget widget = internalExpand(element, false);
        if (widget instanceof TreeItem) {
            TreeItem item = (TreeItem) widget;
            item.setChecked(state);
            setCheckedChildren(item, state);
            return true;
        }
        return false;
    }

}
