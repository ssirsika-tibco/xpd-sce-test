/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.IFileEditorInput;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @author wzurek
 * 
 */
public class EditorInputAdapterFactory implements IAdapterFactory {

    /**
     * adapts to WorkingCopy.
     * 
     * @param adaptableObject
     *            IFileEditorInput
     * @param adapterType
     *            WorkingCopy.class
     * @return working copy from the factory
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof IFileEditorInput) {
            IFile file = ((IFileEditorInput) adaptableObject).getFile();
            return XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        }
        return null;
    }

    /**
     * List of possible adapters.
     * 
     * @return list of classes to what we can adapt
     */
    public Class[] getAdapterList() {
        return new Class[] { WorkingCopy.class };
    }

}
