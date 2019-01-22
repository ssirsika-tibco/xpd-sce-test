/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */
package com.tibco.xpd.rsd.ui.navigator;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.util.IOpenEventListener;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Widget;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rsd.ui.components.RsdContentProvider;
import com.tibco.xpd.rsd.ui.editor.RsdEditorOpener;
import com.tibco.xpd.rsd.wc.RsdWorkingCopy;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * Provides content for REST Service Descriptor for Project Explorer view.
 * 
 * @author jarciuch
 * @since 27 Feb 2015
 */
public class RsdNavigatorContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { RestConstants.REST_SPECIAL_FOLDER_KIND };

    // Adapter factory content provider
    private final RsdContentProvider rsdContentProvider;

    private final IOpenEventListener openListener;

    private OpenStrategy handler;

    /**
     * The constructor.
     */
    public RsdNavigatorContentProvider() {

        TransactionalAdapterFactoryContentProvider delegate =
                new TransactionalAdapterFactoryContentProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory());
        rsdContentProvider = new RsdContentProvider(delegate);

        openListener = new IOpenEventListener() {
            /**
             * {@inheritDoc}
             */
            @Override
            public void handleOpen(SelectionEvent e) {
                Object data = e.item;

                if (data instanceof Widget) {
                    data = ((Widget) data).getData();
                }

                /*
                 * Open the selected item in the editor only if it's form 'rsd'
                 * package.
                 */
                if (RsdNavigatorLabelProvider.isFromRsdPackage(data)) {
                    EObject eo = (EObject) data;
                    RsdEditorOpener.getDefault().openEditor(eo, /* select= */
                    true);
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] doGetChildren(Object parentElement) {
        return rsdContentProvider.getChildren(parentElement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object doGetParent(Object element) {
        Object parent = null;

        parent = rsdContentProvider.getParent(element);

        if (parent instanceof Resource) {
            parent = WorkspaceSynchronizer.getFile((Resource) parent);
        }

        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void doGetPipelinedChildren(Object parent, Set theCurrentChildren) {

        // Register all RSD working copies for the given project - this will
        // allow the saveables for this working copies to be set
        if (parent instanceof IProject) {
            XpdProjectResourceFactory resourceFactory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory((IProject) parent);

            if (resourceFactory != null) {
                WorkingCopy[] workingCopies =
                        resourceFactory.getWorkingCopies();

                for (WorkingCopy wc : workingCopies) {
                    if (wc instanceof RsdWorkingCopy) {
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

    /**
     * {@inheritDoc}
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        return KINDS;
    }

    /**
     * {@inheritDoc}
     */
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
