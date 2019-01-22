/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Adapter factory to adapt an <code>URIEditorInput</code> to a
 * <code>WorkingCopy</code>.
 * 
 * @author njpatel
 * 
 */
public class URIEditorInputAdapterFactory implements IAdapterFactory {

    // Shared editing domain
    private final TransactionalEditingDomain ed = XpdResourcesPlugin
            .getDefault().getEditingDomain();

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
     * java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        Object adapter = null;

        if (adaptableObject instanceof URIEditorInput) {
            URIEditorInput input = (URIEditorInput) adaptableObject;
            URI uri = input.getURI();
            if (uri != null) {
                EObject object = ed.getResourceSet().getEObject(uri, true);

                if (object != null) {
                    adapter = WorkingCopyUtil.getWorkingCopyFor(object);
                    /*
                     * If failed to get adapter - probably because this is being
                     * called when no WorkingCopyProviderAdapter has been
                     * installed (used by WorkingCopyUtil.getWorkingCopyFor
                     * above) - then try loading working copy of the IResource.
                     */
                    if (adapter == null) {
                        Resource res = object.eResource();

                        if (res != null) {
                            IFile file = WorkspaceSynchronizer.getFile(res);

                            if (file != null) {
                                adapter = XpdResourcesPlugin.getDefault()
                                        .getWorkingCopy(file);
                            }
                        }
                    }
                }

            }
        }

        return adapter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    @SuppressWarnings("unchecked")
    public Class[] getAdapterList() {
        return new Class[] { WorkingCopy.class };
    }

}
