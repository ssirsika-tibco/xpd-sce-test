/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.resources.wc;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.resources.DependencyProxyResource;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.wc.WorkingCopyDependencyProvider;

/**
 * Dependency provider for the BOM Working Copy.
 * 
 * @author njpatel
 * 
 */
public class BOMWorkingCopyDependencyProvider extends
        WorkingCopyDependencyProvider {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.resources.IWorkingCopyDependencyProvider#getWorkingCopyClass
     * ()
     */
    @Override
    public Class<? extends WorkingCopy> getWorkingCopyClass() {
        return BOMWorkingCopy.class;
    }

    /**
     * @see com.tibco.xpd.resources.wc.WorkingCopyDependencyProvider#getProxyResource(org.eclipse.emf.common.util.URI)
     * 
     * @param uri
     * @return
     */
    @Override
    protected IResource getProxyResource(URI uri) {
        IResource res = null;
        if (uri != null && uri.isRelative()) {
            res =
                    new DependencyProxyResource(new Path(uri.toString()),
                            BOMResourcesPlugin.BOM_SPECIAL_FOLDER_KIND);
        }
        return res;
    }

}