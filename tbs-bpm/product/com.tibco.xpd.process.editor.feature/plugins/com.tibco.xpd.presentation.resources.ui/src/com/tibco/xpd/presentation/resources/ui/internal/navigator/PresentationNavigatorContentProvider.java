/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.presentation.resources.ui.internal.navigator;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

import com.tibco.xpd.presentation.resources.ui.internal.util.PresentationManager;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * Provides content for Presentation content for Project Explorer view.
 * <p>
 * <i>Created: 26 Jun 2008</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class PresentationNavigatorContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    // private static final Object[] EMPTY_ARRAY = new Object[] {};
    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { PresentationManager.PRESENTATION_SPECIAL_FOLDER_KIND };

    // Adapter factory content provider
    private final AdapterFactoryContentProvider factoryContentProvider;

    /**
     * The constructor.
     */
    public PresentationNavigatorContentProvider() {
        factoryContentProvider =
                new TransactionalAdapterFactoryContentProvider(
                        XpdResourcesPlugin.getDefault().getEditingDomain(),
                        XpdResourcesPlugin.getDefault().getAdapterFactory());
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
        if (parent instanceof IFile) {
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
            // change to true if there will be elements to
            // display underneath file.
            hasChildren = false;
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

}
