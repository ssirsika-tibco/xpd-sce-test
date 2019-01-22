/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.n2.globalsignal.resource.ui.navigator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.progress.UIJob;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.AbstractAssetGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.globalSignalDefinition.GlobalSignal;
import com.tibco.xpd.globalSignalDefinition.GlobalSignalDefinitions;
import com.tibco.xpd.globalSignalDefinition.PayloadDataField;
import com.tibco.xpd.globalSignalDefinition.workingcopy.GsdWorkingCopy;
import com.tibco.xpd.n2.globalsignal.resource.GsdResourcePlugin;
import com.tibco.xpd.n2.globalsignal.resource.internal.Messages;
import com.tibco.xpd.n2.globalsignal.resource.ui.GSDAssetGroups;
import com.tibco.xpd.n2.globalsignal.resource.ui.GlobalSignalAssetGroups;
import com.tibco.xpd.n2.globalsignal.resource.ui.GlobalSignalsGroup;
import com.tibco.xpd.n2.globalsignal.resource.ui.PayloadDataGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * The Navigator Content provider for the GlobalSignalDefinition Asset.
 * 
 * @author sajain
 * @since Jan 28, 2015
 */
public class GsdContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    /**
     * Set of working copies.
     */
    private final Set<GsdWorkingCopy> workingCopies;

    /**
     * Working copy listener.
     */
    private final GsdWorkingCopyListener wcListener;

    /**
     * Special folders handled by this content provider
     */
    private static final String[] KINDS =
            new String[] { GsdResourcePlugin.GSD_SPECIAL_FOLDER_KIND };

    /**
     * The Navigator Content provider for the GlobalSignalDefinition Asset.
     */
    public GsdContentProvider() {
        updateInclusionList();
        workingCopies = new HashSet<GsdWorkingCopy>();
        wcListener = new GsdWorkingCopyListener();
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     * 
     * @param aParent
     * @param theCurrentChildren
     */
    @Override
    protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {

        /*
         * Register all GSD working copies for the given project - this will
         * allow the saveables for this working copies to be set
         */

        if (aParent instanceof IFile) {

            Object[] children = doGetChildren(aParent);

            if (children != null && children.length > 0) {

                /*
                 * Add children to the set
                 */
                theCurrentChildren.addAll(Arrays.asList(children));
            }
        }

    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     * 
     * @param parentElement
     * @return
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {

        Object[] children = null;

        if (parentElement instanceof INavigatorGroup) {

            /*
             * This is a navigator group so return it's children
             */
            children =
                    ((INavigatorGroup) parentElement).getChildren().toArray();

        } else if (parentElement instanceof IFile) {

            IFile file = (IFile) parentElement;

            GsdWorkingCopy wc = getWorkingCopy(file);

            if (wc != null) {

                EObject root = wc.getRootElement();

                if (root instanceof GlobalSignalDefinitions) {

                    children =
                            GSDAssetGroups
                                    .getGSDGroups((GlobalSignalDefinitions) root);
                }
            }

        } else if (parentElement instanceof GlobalSignal) {

            GlobalSignal file = (GlobalSignal) parentElement;

            children = GlobalSignalAssetGroups.getGlobalSignalGroups(file);

        }

        return children != null ? children : new Object[0];
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Object doGetParent(Object element) {

        Object parent = null;

        if (element instanceof INavigatorGroup) {

            /*
             * This is a navigator group, so return it's parent
             */
            parent = ((INavigatorGroup) element).getParent();

        } else if (element instanceof EObject) {

            EObject eo = (EObject) element;

            /*
             * Get working copy of the EObject
             */
            WorkingCopy wc = getWorkingCopy(eo);

            if (wc != null) {

                if (eo instanceof GlobalSignal) {

                    /*
                     * Get the logical group of the eobject
                     */
                    EObject eContainer = eo.eContainer();

                    if (eContainer instanceof GlobalSignalDefinitions) {

                        parent = new GlobalSignalsGroup(eContainer);

                    }
                } else if (eo instanceof PayloadDataField) {

                    /*
                     * Get the logical group of the eobject
                     */
                    EObject eContainer = eo.eContainer();
                    AbstractAssetGroup[] assetGroups = null;

                    if (eContainer instanceof GlobalSignal) {

                        parent = new PayloadDataGroup(eContainer);
                    }
                }
            }
        }

        return parent;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected boolean doHasChildren(Object element) {

        boolean hasChildren = false;

        if (element instanceof INavigatorGroup) {

            /*
             * Get children from the group.
             */
            hasChildren = ((INavigatorGroup) element).hasChildren();

        } else if (element instanceof IFile) {

            /*
             * GSD file will have children if everything is okay with the
             * working copy.
             */
            GsdWorkingCopy wc =
                    getWorkingCopy((IFile) element);

            if (wc != null && !wc.isInvalidFile()) {

                hasChildren = true;
            }

        } else if (element instanceof GlobalSignal) {

            /*
             * Global signal will always have children since it will always have
             * Payload data root node.
             */
            hasChildren = true;

        } else if (element instanceof PayloadDataField) {

            /*
             * Payload data can't have children.
             */
            hasChildren = false;
        }
        return hasChildren;
    }

    /**
     * @param inputElement
     *            The input element.
     * @return An array of root elements.
     * @see com.tibco.xpd.ui.projectexplorer.providers.
     *      AbstractSpecialFoldersContentProvider#getElements(java.lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {

        return getChildren(inputElement);
    }

    @Override
    protected boolean doRefresh(WorkingCopy wc, PropertyChangeEvent evt) {

        return true;
    }

    @Override
    public String[] getSpecialFolderKindInclusion() {

        return KINDS;
    }

    /**
     * Get the {@link GsdWorkingCopy} of the given file. If
     * this working copy is accessed for the first time then a change listener
     * will be registered with the working copy so that the viewer can be
     * refreshed if the gsd file changes.
     * 
     * @param file
     * @return working copy or <code>null</code> if one is not found or is not a
     *         <code>GlobalSignalDefinitionWorkingCopy</code>.
     */
    private GsdWorkingCopy getWorkingCopy(IFile file) {

        return (GsdWorkingCopy) super.getWorkingCopy(file);

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
    private void refresh(final List<IResource> resources) {

        if (resources != null && !resources.isEmpty()) {

            new UIJob(Messages.GsdContentProvider_refreshJob_title) {

                @Override
                public IStatus runInUIThread(IProgressMonitor monitor) {

                    TreeViewer viewer = getViewer();

                    if (viewer != null && !viewer.getControl().isDisposed()) {

                        SubProgressMonitor mon =
                                new SubProgressMonitor(monitor,
                                        resources.size());

                        for (IResource res : resources) {

                            mon.subTask(String
                                    .format(Messages.GsdContentProvider_refreshJob_sub_title,
                                            res.getName()));

                            viewer.refresh(res);
                            mon.worked(1);
                        }
                    }

                    return Status.OK_STATUS;
                }
            }.schedule();
        }
    }

    @Override
    public void dispose() {

        /*
         * Unregister all working copy listeners.
         */
        for (GsdWorkingCopy wc : workingCopies) {

            wc.removeListener(wcListener);
        }

        workingCopies.clear();
        super.dispose();
    }

    /**
     * Working copy listener that will refresh the project explorer if the gsd
     * working copy is changed or reloaded.
     * 
     * @author sajain
     * 
     */
    private class GsdWorkingCopyListener implements PropertyChangeListener {

        /*
         * (non-Javadoc)
         * 
         * @seejava.beans.PropertyChangeListener#propertyChange(java.beans.
         * PropertyChangeEvent)
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if (evt.getPropertyName().equals(WorkingCopy.CHANGED)
                    || evt.getPropertyName().equals(WorkingCopy.PROP_RELOADED)) {

                /*
                 * Refresh the content
                 */
                WorkingCopy wc = (WorkingCopy) evt.getSource();

                if (wc != null && wc.getEclipseResources() != null
                        && !wc.getEclipseResources().isEmpty()) {

                    refresh(wc.getEclipseResources());
                }

            } else if (evt.getPropertyName().equals(WorkingCopy.PROP_REMOVED)) {

                /*
                 * Remove the working copy from local cache
                 */
                WorkingCopy wc = (WorkingCopy) evt.getSource();

                if (wc != null) {

                    workingCopies.remove(wc);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeViewerListener#treeExpanded(org.eclipse
     * .jface.viewers.TreeExpansionEvent)
     */
    public void treeExpanded(TreeExpansionEvent event) {

        /*
         * If a GSD file is expanded then we want to automatically expand the
         * Global Signals under it as well.
         */
        if (event.getElement() instanceof IFile) {

            WorkingCopy wc =
                    getWorkingCopyWithoutRegistering((IResource) event
                            .getElement());

            if (wc != null
                    && wc.getRootElement() instanceof GlobalSignalDefinitions) {

                if (event.getSource() instanceof TreeViewer) {

                    final TreeViewer treeViewer =
                            (TreeViewer) event.getSource();

                    final Object element = event.getElement();

                    /*
                     * Viewer may be busy so we need to queue this for the UI
                     * thread to run.
                     */
                    treeViewer.getControl().getDisplay()
                            .asyncExec(new Runnable() {

                                @Override
                                public void run() {
                                    treeViewer.expandToLevel(element, 2);
                                }

                            });

                }
            }
        }
    }
}
