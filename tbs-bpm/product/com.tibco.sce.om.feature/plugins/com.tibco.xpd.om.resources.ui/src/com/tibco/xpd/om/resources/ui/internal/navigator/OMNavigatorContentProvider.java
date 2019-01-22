/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.internal.navigator;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.util.IOpenEventListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.tibco.xpd.om.resources.OMResourcesActivator;
import com.tibco.xpd.om.resources.ui.OMResourcesUIActivator;
import com.tibco.xpd.om.resources.ui.internal.Messages;
import com.tibco.xpd.om.resources.wc.OMWorkingCopy;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * Provides content for Organisation Model for Project Explorer view.
 * <p>
 * <i>Created: 21 Nov 2007</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class OMNavigatorContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    // private static final Object[] EMPTY_ARRAY = new Object[] {};
    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { OMResourcesActivator.OM_SPECIAL_FOLDER_KIND };

    // Adapter factory content provider
    private final AdapterFactoryContentProvider factoryContentProvider;

    private final IOpenEventListener openListener;

    private OpenStrategy handler;

    /**
     * The constructor.
     */
    public OMNavigatorContentProvider() {
        factoryContentProvider =
                new TransactionalAdapterFactoryContentProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory());

        openListener = new IOpenEventListener() {

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.eclipse.jface.util.IOpenEventListener#handleOpen(org.eclipse
             * .swt.events.SelectionEvent)
             */
            @Override
            public void handleOpen(SelectionEvent e) {
                Object data = e.item;

                /*
                 * Open the selected item in the editor
                 */

                if (data instanceof Widget) {
                    data = ((Widget) data).getData();
                }

                if (data instanceof EObject) {
                    try {
                        OMResourcesUIActivator.openEditor(PlatformUI
                                .getWorkbench().getActiveWorkbenchWindow()
                                .getActivePage(), (EObject) data);
                    } catch (PartInitException partInitException) {
                        Shell shell =
                                getViewer() != null ? getViewer().getTree()
                                        .getShell() : XpdResourcesPlugin
                                        .getStandardDisplay().getActiveShell();

                        IStatus status =
                                new Status(
                                        IStatus.ERROR,
                                        OMResourcesUIActivator.PLUGIN_ID,
                                        partInitException.getLocalizedMessage(),
                                        partInitException);
                        ErrorDialog
                                .openError(shell,
                                        Messages.OMNavigatorContentProvider_openEditorDlg_error_title,
                                        Messages.OMNavigatorContentProvider_openEditorDlg_error_message,
                                        status);
                    }
                }
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider#doGetChildren(java.lang.Object)
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        return factoryContentProvider.getChildren(parentElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     */
    @Override
    protected Object doGetParent(Object element) {
        Object parent = null;

        parent = factoryContentProvider.getParent(element);

        if (parent instanceof Resource) {
            parent = WorkspaceSynchronizer.getFile((Resource) parent);
        }

        return parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider
     * #doGetPipelinedChildren(java.lang.Object, java.util.Set)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doGetPipelinedChildren(Object parent, Set theCurrentChildren) {

        // Register all OM working copies for the given project - this will
        // allow the saveables for this working copies to be set
        if (parent instanceof IProject) {
            XpdProjectResourceFactory resourceFactory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory((IProject) parent);

            if (resourceFactory != null) {
                WorkingCopy[] workingCopies =
                        resourceFactory.getWorkingCopies();

                for (WorkingCopy wc : workingCopies) {
                    // Only interested in OM working copies
                    if (wc instanceof OMWorkingCopy) {
                        registerWorkingCopy(wc);
                    }
                }
            }
        } else if (parent instanceof IFile) {
            WorkingCopy wc = getWorkingCopy((IResource) parent);
            EObject element = wc.getRootElement();
            if (element != null) {
                theCurrentChildren.add(element);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     */
    @Override
    protected boolean doHasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof IFile) {
            hasChildren = true;
        } else {
            hasChildren = getChildren(element).length > 0;
        }
        return hasChildren;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.ui.projectexplorer.providers.
     * AbstractSpecialFoldersContentProvider#getSpecialFolderKindInclusion()
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        return KINDS;
    }

    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // Viewer has changed so register open listener
        if (getViewer() != viewer) {
            if (handler != null) {
                handler.removeOpenListener(openListener);
                handler = null;
            }

            if (viewer != null) {
                handler = new OpenStrategy(viewer.getControl());
                handler.addOpenListener(openListener);
            }
        }
        super.inputChanged(viewer, oldInput, newInput);
    }

}
