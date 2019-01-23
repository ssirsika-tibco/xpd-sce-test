/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wm.tasklibrary.editor.resources;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.OpenAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;
import com.tibco.xpd.wm.tasklibrary.editor.TaskLibraryEditorPlugin;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Task library project explorer content provider.
 * 
 * @author aallway
 * @since 3.2
 */
public class TaskLibraryProjectExplorerContentProvider extends
        AbstractWorkingCopySaveablesContentProvider implements
        ITreeViewerListener, IOpenListener {

    private static final String[] specialFolderFilter =
            { TaskLibraryEditorPlugin.TASK_LIBRARY_SPECIAL_FOLDER_KIND };

    @Override
    protected Object[] doGetChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof IFile) {
            // 
            // The first (and only) Process element represents the Task Library.
            // It is usual to have an element that represents the main object in
            // the file (so that, for instance, task library name can be
            // different from file name if necessary).
            //
            Process taskLibrary =
                    TaskLibraryFactory.INSTANCE
                            .getTaskLibrary((IFile) parentElement);
            if (taskLibrary != null) {
                children = new Object[] { taskLibrary };
            }

        } else if (parentElement instanceof Process) {
            // Next level is process
            children =
                    TaskLibraryAssetGroups
                            .getTaskLibraryGroups((Process) parentElement);

        } else if (parentElement instanceof INavigatorGroup) {
            // This is a navigator group so return it's children
            children =
                    ((INavigatorGroup) parentElement).getChildren().toArray();
        }

        // Return the children
        return children != null ? children : new Object[0];
    }

    @Override
    protected Object doGetParent(Object element) {
        Object parent = null;

        if (element instanceof INavigatorGroup) {
            // This is a navigator group, so return it's parent
            parent = ((INavigatorGroup) element).getParent();

        } else if (element instanceof EObject) {
            EObject eo = (EObject) element;

            // Get working copy of the EObject
            WorkingCopy wc = getWorkingCopy(eo);

            if (wc != null) {
                // If this is a Process (he task library itself) then the parent
                // will be the IFile
                if (eo instanceof Process) {
                    // Get the IResource of the working copy
                    parent = wc.getEclipseResources().get(0);

                } else {
                    /*
                     * Get the logical group of the eobject
                     */
                    parent = TaskLibraryAssetGroups.getParentGroup(eo);
                }
            }
        }

        return parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doGetPipelinedChildren(Object aParent, Set theCurrentChildren) {
        if (aParent instanceof IFile) {
            Object[] children = doGetChildren(aParent);

            if (children != null && children.length > 0) {
                theCurrentChildren.addAll(Arrays.asList(children));
            }
        }
    }

    @Override
    protected boolean doHasChildren(Object element) {
        boolean gotChildren = false;

        if (element instanceof INavigatorGroup) {
            // Get children from the group
            gotChildren = ((INavigatorGroup) element).hasChildren();

        } else if (element instanceof IFile) {
            // If there is a valid working copy for the file then it has
            // children
            WorkingCopy wc = getWorkingCopy((IResource) element);

            gotChildren = (wc != null && !wc.isInvalidFile());

        } else if (element instanceof Package || element instanceof Process
                || element instanceof ProcessInterface) {
            // If this is a package or process or process interface then it has
            // children
            gotChildren = true;
        }

        return gotChildren;
    }

    @Override
    public String[] getSpecialFolderKindInclusion() {
        return specialFolderFilter;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        TreeViewer oldViewer = getViewer();

        if (oldViewer != null) {
            // Unregister the tree listener
            oldViewer.removeTreeListener(this);
            oldViewer.removeOpenListener(this);
        }
        // This should register the new viewer
        super.inputChanged(viewer, oldInput, newInput);

        // Check if there is a new viewer, if it has then register the tree
        // listener
        TreeViewer newViewer = getViewer();

        if (newViewer != null) {
            newViewer.addTreeListener(this);
            newViewer.addOpenListener(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeViewerListener#treeCollapsed(org.eclipse
     * .jface.viewers.TreeExpansionEvent)
     */
    public void treeCollapsed(TreeExpansionEvent event) {
        // Do nothing
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
         * If a packages XPDL file is expanded then we want to automatically
         * expand the package under it as well.
         */
        if (event.getElement() instanceof IFile) {
            WorkingCopy wc =
                    getWorkingCopyWithoutRegistering((IResource) event
                            .getElement());

            if (wc != null && wc.getRootElement() instanceof Package) {
                if (event.getSource() instanceof TreeViewer) {
                    final TreeViewer treeViewer =
                            (TreeViewer) event.getSource();
                    final Object element = event.getElement();
                    // Viewer may be busy so we need to queue this for the UI
                    // thread to run.
                    treeViewer.getControl().getDisplay()
                            .asyncExec(new Runnable() {

                                public void run() {
                                    treeViewer.expandToLevel(element, 2);
                                }

                            });

                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IOpenListener#open(org.eclipse.jface.viewers
     * .OpenEvent)
     */
    public void open(OpenEvent event) {
        /*
         * If a Process was double-clicked on then open the process editor. If
         * the process tree has collapsed then re-expand it.
         */
        IStructuredSelection selection =
                (IStructuredSelection) event.getSelection();

        if (selection.size() == 1
                && (selection.getFirstElement() instanceof Process || selection
                        .getFirstElement() instanceof Activity)) {
            NamedElement processOrActivity =
                    (NamedElement) selection.getFirstElement();

            if (event.getSource() instanceof TreeViewer) {
                TreeViewer viewer = (TreeViewer) event.getSource();

                // If the process tree has been collapsed then expand it
                if (!viewer.getExpandedState(processOrActivity)) {
                    viewer.setExpandedState(processOrActivity, true);
                }
                // Run the open action - to open the process
                OpenAction.run((IStructuredSelection) event.getSelection());
            }
        }
    }
}
