/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.projectexplorer.providers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.navigator.SaveablesProvider;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.internal.Messages;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.wc.WorkingCopySaveable;

/**
 * Abstract pipeline tree content provider for the Project Explorer to be used
 * as the base for the content provider set in the extension point
 * <code>org.eclipse.ui.navigator.navigatorContent</code>. This class extends
 * <code>{@link AbstractSpecialFoldersContentProvider}</code> to provide
 * saveables support through the working copies.
 * <p>
 * This class adapts to <code>{@link SaveablesProvider}</code> for saveables
 * support in the Project Explorer.
 * </p>
 * <p>
 * Working copies for <code>EObjects</code> or <code>IResources</code> should be
 * accessed through the methods in this class. This will allow for the working
 * copies to be registered and listeners attached for changes, and also the
 * saveables engine updated accordingly. The methods are:
 * <ul>
 * <li><code>{@link #getWorkingCopy(EObject)}</code> - Get working copy for the
 * <code>EObject</code></li>
 * <li><code>{@link #getWorkingCopy(IResource)}</code> - Get working copy for
 * the <code>IResource</code></li>
 * </ul>
 * </p>
 * <p>
 * <strong>See <code>{@link AbstractSpecialFoldersContentProvider}</code> for
 * information about using this abstract class.</strong>
 * </p>
 * 
 * @see AbstractSpecialFoldersContentProvider
 * 
 * @author njpatel
 * 
 */
public abstract class AbstractWorkingCopySaveablesContentProvider extends
        AbstractSpecialFoldersContentProvider implements IAdaptable {

    /**
     * SaveablesProvider for this class. (Lazy loaded.)
     */
    private WorkingCopySaveablesProvider saveablesProvider = null;

    /**
     * Working Copy property change listener. (Lazy loaded.)
     */
    private WorkingCopyEventListener workingCopyListener = null;

    /**
     * Working copy set. All working copies accessed through methods
     * <code>{@link #getWorkingCopy(IResource)}</code> or
     * <code>{@link #getWorkingCopy(EObject)}</code> will be registered and a
     * listener will be registered to listen to changes.
     */
    private Set<WorkingCopy> workingCopySet = new HashSet<WorkingCopy>();

    /**
     * Get the <code>WorkingCopy</code> of the <code>IResource</code>. This also
     * registers the working copy listener.
     * <p>
     * If the working copy is required without registering the listener then use
     * <code>{@link #getWorkingCopyWithoutRegistering(IResource)}</code>.
     * </p>
     * 
     * @see #getWorkingCopyWithoutRegistering(IResource)
     * 
     * @param res
     *            Resource
     * @return If the resource has a working copy then it will be return,
     *         otherwise <code>null</code> will be returned.
     */
    public WorkingCopy getWorkingCopy(IResource res) {
        WorkingCopy wc = getWorkingCopyWithoutRegistering(res);

        if (wc != null) {
            // Register working copy
            registerWorkingCopy(wc);
        }

        return wc;
    }

    /**
     * Get the <code>WorkingCopy</code> of the <code>IResource</code>.
     * <p>
     * If the working copy listener needs to be registered then use
     * <code>{@link #getWorkingCopy(IResource)}</code>.
     * </p>
     * 
     * @see #getWorkingCopy(IResource)
     * 
     * @param res
     *            Resource
     * @return If the resource has a working copy then it will be return,
     *         otherwise <code>null</code> will be returned.
     */
    public WorkingCopy getWorkingCopyWithoutRegistering(IResource res) {
        WorkingCopy wc = null;

        if (res != null) {
            wc = XpdResourcesPlugin.getDefault().getWorkingCopy(res);
        }

        return wc;
    }

    /**
     * Get the <code>WorkingCopy</code> of the given <code>EObject</code>. This
     * also registers the working copy listener.
     * <p>
     * If the working copy is required without registering the listener then use
     * <code>{@link #getWorkingCopyWithoutRegistering(EObject)}</code>.
     * </p>
     * 
     * @see #getWorkingCopyWithoutRegistering(EObject)
     * 
     * @param eo
     *            <code>EObject</code> to get working copy of.
     * @return If the <code>EObject</code> has a working copy then it will be
     *         returned, otherwise <code>null</code> will be returned.
     */
    public WorkingCopy getWorkingCopy(EObject eo) {
        WorkingCopy wc = getWorkingCopyWithoutRegistering(eo);

        if (wc != null) {
            // Register working copy
            registerWorkingCopy(wc);
        }

        return wc;
    }

    /**
     * Get the <code>WorkingCopy</code> of the given <code>EObject</code>.
     * <p>
     * If the working copy listener needs to be registered then use
     * <code>{@link #getWorkingCopy(IResource)}</code>.
     * </p>
     * 
     * @param eo
     *            <code>EObject</code> to get the working copy of.
     * @return If the <code>EObject</code> has a working copy then it will be
     *         returned, otherwise <code>null</code> will be returned.
     */
    public WorkingCopy getWorkingCopyWithoutRegistering(EObject eo) {
        WorkingCopy wc = null;

        if (eo != null) {
            wc = WorkingCopyUtil.getWorkingCopyFor(eo);
        }

        return wc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter) {

        if (adapter == SaveablesProvider.class) {

            if (saveablesProvider == null) {
                saveablesProvider = new WorkingCopySaveablesProvider();
            }

            return saveablesProvider;
        }

        return Platform.getAdapterManager().getAdapter(this, adapter);
    }

    /**
     * Dispose of working copies and unregister listeners. Subclasses may
     * override this but they must call this super method.
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
        if (!workingCopySet.isEmpty()) {
            // remove the listeners
            if (workingCopyListener != null) {
                for (WorkingCopy wc : workingCopySet) {
                    wc.removeListener(workingCopyListener);
                }
                workingCopyListener = null;
            }
            workingCopySet.clear();
        }

        // Dispose of the saveables provider
        if (saveablesProvider != null) {
            saveablesProvider.dispose();
            saveablesProvider = null;
        }

        super.dispose();
    }

    @Override
    protected void doVisit(IResourceDelta delta) throws CoreException {
        if (delta.getKind() == IResourceDelta.REMOVED) {
            IResource res = delta.getResource();

            if (res instanceof IFile) {
                // If this is an XPDL resource then we need to unregister
                // it's working copy
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault().getWorkingCopy(res);

                unregisterWorkingCopy(wc);
            }
        }
    }

    /**
     * Register the working copy. This involves registering a listener and also
     * firing a saveables opened event.
     * 
     * @param wc
     */
    protected void registerWorkingCopy(WorkingCopy wc) {
        if (wc != null && !workingCopySet.contains(wc)) {

            // If working copy event listener not initialised then do so
            if (workingCopyListener == null) {
                workingCopyListener = new WorkingCopyEventListener();
            }

            // Register listener
            wc.addListener(workingCopyListener);

            workingCopySet.add(wc);

            // If there is a saveables service available then fire a saveable
            // opened event
            if (saveablesProvider != null) {
                saveablesProvider.saveableOpened(wc.getSaveable());
            }

        }
    }

    /**
     * Unregister the working copy. This involves remove its listener and also
     * firing a saveables closed event.
     * 
     * @param wc
     */
    private void unregisterWorkingCopy(WorkingCopy wc) {
        if (wc != null && workingCopySet.contains(wc)) {
            wc.removeListener(workingCopyListener);
            workingCopySet.remove(wc);

            // If saveable service provided then close related saveable
            if (saveablesProvider != null) {
                saveablesProvider.saveableClosed(wc.getSaveable());
            }
        }
    }

    /**
     * SaveablesProvider adapter for the
     * <code>AbstractWorkingCopySaveablesContentProvider</code>.
     * 
     * @author njpatel
     */
    private class WorkingCopySaveablesProvider extends SaveablesProvider {

        /**
         * Indicate whether the saveable service is available
         */
        private boolean saveablesServiceAvailable = false;

        @Override
        protected void doInit() {
            // Indicate that the saveables service is available
            saveablesServiceAvailable = true;
            super.doInit();
        }

        @Override
        public Object[] getElements(Saveable saveable) {
            // If saveable is a working copy saveable then return the IFile
            // associated with the working copy
            if (saveable instanceof WorkingCopySaveable)
                return new Object[] { ((WorkingCopySaveable) saveable)
                        .getWorkingCopy().getEclipseResources().get(0) };

            return null;
        }

        @Override
        public Saveable getSaveable(Object element) {
            Saveable saveable = null;

            // If this is a valid package file then return the saveable of it's
            // working copy (make sure the file is in the packages folder
            if (element instanceof IFile) {
                // IFile file = (IFile) element;

                WorkingCopy wc =
                        getWorkingCopyWithoutRegistering((IFile) element);

                if (wc != null && workingCopySet.contains(wc)) {

                    // /*
                    // * Add this working copy to our local list in case it
                    // hasn't
                    // * already been done. This will be the case if the process
                    // * editor has been opened (persistence) on startup.
                    // */
                    // registerWorkingCopy(wc);

                    saveable = wc.getSaveable();
                }
            }

            return saveable;
        }

        @Override
        public Saveable[] getSaveables() {
            List<Saveable> saveables = new ArrayList<Saveable>();

            // Get saveables of all working copies in the set
            for (WorkingCopy wc : workingCopySet) {
                saveables.add(wc.getSaveable());
            }

            return saveables.toArray(new Saveable[saveables.size()]);
        }

        /**
         * Call when a saveable is added to the project explorer - this will
         * fire a saveables opened event.
         * 
         * @param saveable
         */
        public void saveableOpened(Saveable saveable) {
            if (saveablesServiceAvailable && saveable != null) {
                fireSaveablesOpened(new Saveable[] { saveable });
            }
        }

        /**
         * Call when a saveable's dirty flag changes - this will fire a
         * saveables dirty changed event.
         * 
         * @param saveable
         */
        public void saveableDirty(Saveable saveable) {
            if (saveablesServiceAvailable && saveable != null) {
                fireSaveablesDirtyChanged(new Saveable[] { saveable });
            }
        }

        /**
         * Call when a saveable is closed or removed in the project explorer -
         * this will fire a saveables closed event.
         * 
         * @param saveable
         */
        public void saveableClosed(Saveable saveable) {
            if (saveablesServiceAvailable && saveable != null) {
                fireSaveablesClosed(new Saveable[] { saveable });
            }
        }
    }

    /**
     * Check if the content should be refreshed based on the property change
     * event received.
     * <p>
     * Subclasses may override. Default implementation returns <code>true</code>
     * .
     * </p>
     * 
     * @param wc
     *            <code>WorkingCopy</code> triggering the event
     * @param evt
     *            the property change event
     * @return <code>true</code> if the content should be refreshed,
     *         <code>false</code> otherwise.
     * 
     * @since 3.0
     */
    protected boolean doRefresh(WorkingCopy wc, PropertyChangeEvent evt) {
        return true;
    }

    /**
     * Working copy property change event listener. This will listen to changes
     * in working copy of Xpdl2 packages and update the project tree in
     * response.
     * 
     * @author njpatel
     * 
     */
    private class WorkingCopyEventListener implements PropertyChangeListener {
        // SCF-257: Need Label Provider , to forcibly refresh the text of the
        // TreeItem representing this WorkingCopy, to update the Dirty Marker on
        // Save.The normal refresh is deferred until post Build, by the
        // DecorationManager on Pending Decoration Job.
        private ProjectExplorerLabelProvider labelProvider =
                new ProjectExplorerLabelProvider();

        /*
         * (non-Javadoc)
         * 
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
         * PropertyChangeEvent)
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            WorkingCopy wc = (WorkingCopy) evt.getSource();

            // Check if tree should be refreshed
            if (doRefresh(wc, evt)) {
                // Refresh the tree
                refresh(wc.getEclipseResources(),
                        propertyName.equals(WorkingCopy.PROP_REMOVED));

                if (propertyName.equals(WorkingCopy.PROP_REMOVED)) {
                    // Unregister the working copy
                    unregisterWorkingCopy(wc);
                } else if (propertyName.equals(WorkingCopy.PROP_DIRTY)) {
                    // Update the saveable
                    if (saveablesProvider != null) {
                        saveablesProvider.saveableDirty(wc.getSaveable());
                    }

                    // Reselect the item to refresh the Save action
                    /*
                     * Sid ACE-1684 We can be called on a non Display thread, in
                     * which case viewer.setSelection() will cause an except, so
                     * make sure it's executed on Display thread.
                     */
                    Display.getDefault().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            TreeViewer viewer = getViewer();
                            if (viewer != null) {
                                ISelection selection = viewer.getSelection();
                                if (selection != null)
                                    viewer.setSelection(selection);
                            }
                        }
                    });
                }
            }
        }

        /**
         * Refresh the given objects in the tree. If collapse is set to
         * <code>true</code> then collapse the tree of these objects.
         * 
         * @param objs
         *            objects to refresh
         * @param collapse
         *            if <code>true</code> collapse the trees of these objects.
         */
        private void refresh(final List<IResource> resources,
                final boolean collapse) {
            if (resources != null && !resources.isEmpty()) {

                new UIJob(
                        Messages.AbstractWorkingCopySaveablesContentProvider_refresh_label) {
                    @Override
                    public IStatus runInUIThread(IProgressMonitor monitor) {
                        TreeViewer viewer = getViewer();

                        if (viewer != null && !viewer.getControl().isDisposed()) {

                            SubProgressMonitor mon =
                                    new SubProgressMonitor(monitor,
                                            resources.size());

                            for (IResource res : resources) {

                                mon.subTask(String
                                        .format(Messages.AbstractWorkingCopySaveablesContentProvider_refreshingResource_shortdesc,
                                                res.getName()));

                                if (collapse) {
                                    viewer.collapseToLevel(res,
                                            AbstractTreeViewer.ALL_LEVELS);
                                }

                                /*
                                 * SCF-257: Force immediate refresh of resource
                                 * to force update of dirty flag.
                                 * ProjectExplorer's viewer DecorationManager
                                 * prevents update of label because the
                                 * decoration job is scheduled lower priority
                                 * than the build job. So we have to force
                                 * immediate update on dirty flag otherwise the
                                 * "*" doesn't get cleared until AFTER build.
                                 * 
                                 * To be on the safe side we only update if we
                                 * find the tree item. AND we still call
                                 * viewer.refresh() to schedule a later refresh.
                                 */

                                /* FInd tree item for resource. */
                                TreeItem foundItem =
                                        findTreeItem(viewer.getTree(), res);

                                if (foundItem != null
                                        && !foundItem.isDisposed()) {
                                    String newText = labelProvider.getText(res);
                                    foundItem.setText(newText);
                                }

                                /*
                                 * * To be on the safe side we only update if we
                                 * find the tree item. AND we still call
                                 * viewer.refresh() to schedule a later refresh.
                                 */

                                viewer.refresh(res);

                                mon.worked(1);
                            }
                        }

                        return Status.OK_STATUS;
                    }

                    /**
                     * Searches the Tree for the TreeItem representing the given
                     * IResource 'findItem'.Returns null when not found.
                     * 
                     * @param tree
                     *            , parent tree to search in.
                     * @param findItem
                     *            , IResource to search for.
                     * @return TreeItem, representing the given IResource, null
                     *         if not found.
                     */
                    private TreeItem findTreeItem(Tree tree, IResource findItem) {

                        if (tree != null) {
                            TreeItem treeItem =
                                    recusiveFind(tree.getItems(), findItem);
                            if (treeItem != null) {
                                return treeItem;
                            }

                        }
                        return null;

                    }

                    /**
                     * Find method to search TreeItem representing the
                     * IResource, this is a recursive method to search the
                     * TreeItems hierarchy for the given IResource.
                     * 
                     * @param items
                     * @param findItem
                     * @return TreeItem representing the IResource.
                     */
                    private TreeItem recusiveFind(TreeItem[] items,
                            IResource findItem) {

                        if (items != null) {

                            for (int i = 0; i < items.length; i++) {

                                TreeItem treeItem = items[i];

                                if (treeItem != null && !treeItem.isDisposed()
                                        && treeItem.getData() != null) {

                                    if (findItem.equals(treeItem.getData())) {
                                        return treeItem;

                                    } else if (treeItem.getData() instanceof IAdaptable) {

                                        Object object =
                                                ((IAdaptable) treeItem
                                                        .getData())
                                                        .getAdapter(IResource.class);

                                        if (object != null
                                                && findItem.equals(object)) {
                                            return treeItem;
                                        }
                                    }
                                }

                            }
                        }

                        /* If not found at level then recurs. */
                        for (int i = 0; i < items.length; i++) {

                            TreeItem treeItem = items[i];

                            if (treeItem != null && !treeItem.isDisposed()) {

                                TreeItem foundItem =
                                        recusiveFind(treeItem.getItems(),
                                                findItem);

                                if (foundItem != null) {
                                    return foundItem;
                                }
                            }
                        }

                        return null;
                    }

                }.schedule();
            }

        }

    }

}
