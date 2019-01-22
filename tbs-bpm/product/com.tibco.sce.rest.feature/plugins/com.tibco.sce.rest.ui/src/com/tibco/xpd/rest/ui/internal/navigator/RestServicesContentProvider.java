/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.ui.internal.navigator;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rest.ui.RestServicesUtil;
import com.tibco.xpd.ui.projectexplorer.providers.AbstractWorkingCopySaveablesContentProvider;

/**
 * Navigator content provider to contribute the REST Services specifal folder
 * and resources.
 * 
 * @author nwilson
 * @since 12 Jan 2015
 */
public class RestServicesContentProvider extends
        AbstractWorkingCopySaveablesContentProvider {

    // Special folders handled by this content provider
    private static final String[] KINDS =
            new String[] { RestServicesUtil.REST_SPECIAL_FOLDER_KIND };

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetPipelinedChildren(java.lang.Object,
     *      java.util.Set)
     * 
     * @param aParent
     * @param theCurrentChildren
     */
    @Override
    protected void doGetPipelinedChildren(Object parent,
            @SuppressWarnings("rawtypes") Set theCurrentChildren) {
        if (parent instanceof IProject) {
            XpdProjectResourceFactory resourceFactory =
                    XpdResourcesPlugin.getDefault()
                            .getXpdProjectResourceFactory((IProject) parent);

            if (resourceFactory != null) {
                WorkingCopy[] workingCopies =
                        resourceFactory.getWorkingCopies();
                for (WorkingCopy wc : workingCopies) {
                    // Only interested in JsonSchemaWorkingCopy working copies
                    if (wc.getClass().getSimpleName()
                            .equals("JsonSchemaWorkingCopy")) { //$NON-NLS-1$
                        registerWorkingCopy(wc);
                    }
                }
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
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doGetParent(java.lang.Object)
     * 
     * @param element
     * @return
     */
    @Override
    protected Object doGetParent(Object element) {
        return null;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#doHasChildren(java.lang.Object)
     * 
     * @param element
     *            The element to check.
     * @return true if it has children.
     */
    @Override
    protected boolean doHasChildren(Object element) {
        boolean hasChildren = false;
        if (element instanceof IFile) {
            hasChildren = false;
        } else {
            hasChildren = getChildren(element).length > 0;
        }
        return hasChildren;
    }

    /**
     * @see com.tibco.xpd.ui.projectexplorer.providers.AbstractSpecialFoldersContentProvider#getSpecialFolderKindInclusion()
     * 
     * @return The supported special folder kinds.
     */
    @Override
    public String[] getSpecialFolderKindInclusion() {
        return KINDS;
    }

}
