/*
 * Copyright (c) TIBCO Software Inc. 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.analyst.resources.xpdl2.projectexplorer;

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

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesConsts;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.actions.OpenAction;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.INavigatorGroup;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.groups.PackageAssetGroups;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;

/**
 * Project Explorer content provider for the <b>BPM Content</b>.
 * 
 * @author njpatel
 */
public class BpmContentProvider extends
        AbstractWorkingCopySaveablesContentProvider implements
        ITreeViewerListener, IOpenListener {

    // Only show package folders
    private static final String[] specialFolderFilter =
            { Xpdl2ResourcesConsts.PROCESSES_SPECIAL_FOLDER_KIND };

    @Override
    protected Object[] doGetChildren(Object parentElement) {
        Object[] children = null;

        if (parentElement instanceof INavigatorGroup) {
            // This is a navigator group so return it's children
            children =
                    ((INavigatorGroup) parentElement).getChildren().toArray();

        } else if (parentElement instanceof Package) {
            // Get the groups defined for the package
            children =
                    PackageAssetGroups
                            .getPackageGroups((Package) parentElement);

        } else if (parentElement instanceof Process) {
            // Get the groups defined for the Process
            children =
                    PackageAssetGroups
                            .getProcessGroups((Process) parentElement);

        } else if (parentElement instanceof ProcessInterface) {
            // Get the groups defined for the Process
            children =
                    PackageAssetGroups
                            .getProcessInterfaceGroups((ProcessInterface) parentElement);

        } else if (parentElement instanceof InterfaceMethod) {
            // Get the groups defined for the Process
            children =
                    PackageAssetGroups
                            .getInterfaceMethodGroups((InterfaceMethod) parentElement);

        } else if (parentElement instanceof ProcessInterfaces) {
            children =
                    ((ProcessInterfaces) parentElement).getProcessInterface()
                            .toArray();
        } else if (parentElement instanceof IFile) {
            // Get the working copy
            WorkingCopy wc = getWorkingCopy((IResource) parentElement);

            EObject root = wc.getRootElement();
            if (wc != null && !wc.isInvalidFile() && root != null) {

                children = new Object[] { root };
            }
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
                // If this is a Package then the parent will be the IFile
                if (eo instanceof Package) {
                    // Get the IResource of the working copy
                    parent = wc.getEclipseResources().get(0);

                } else {
                    /*
                     * Get the logical group of the eobject
                     */
                    parent = PackageAssetGroups.getParentGroup(eo);
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
                || element instanceof ProcessInterface
                || element instanceof InterfaceMethod) {
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
                        .getFirstElement() instanceof ProcessInterface)
                || selection.getFirstElement() instanceof Package) {
            NamedElement processOrInterface =
                    (NamedElement) selection.getFirstElement();

            if (event.getSource() instanceof TreeViewer) {
                TreeViewer viewer = (TreeViewer) event.getSource();

                // If the process tree has been collapsed then expand it
                if (!viewer.getExpandedState(processOrInterface)) {
                    viewer.setExpandedState(processOrInterface, true);
                }
                // Run the open action - to open the process
                OpenAction.run((IStructuredSelection) event.getSelection());
            }
        }
    }
}
