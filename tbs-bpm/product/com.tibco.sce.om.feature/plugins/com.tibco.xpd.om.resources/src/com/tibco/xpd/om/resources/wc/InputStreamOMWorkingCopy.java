/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.om.resources.wc;

import java.io.InputStream;

import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.ui.IEditorInput;

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.provider.OrganisationModelEditPlugin;
import com.tibco.xpd.resources.wc.gmf.InputStreamGMFWorkingCopy;

/**
 * OM WorkingCopy that managed a resource from an input stream (revision history
 * resources).
 * 
 * @author njpatel
 * 
 */
public class InputStreamOMWorkingCopy extends InputStreamGMFWorkingCopy {

    private final IStorage storage;

    private IEditorInput input;

    public InputStreamOMWorkingCopy(IResource resource, IStorage storage)
            throws CoreException {
        super(resource, storage.getContents());
        this.storage = storage;
        registerResourceProvider(getWorkingCopyEPackage(),
                OrganisationModelEditPlugin.getPlugin());
    }

    public InputStreamOMWorkingCopy(IResource resource, InputStream inputStream)
            throws CoreException {
        super(resource, inputStream);
        this.storage = null;
        registerResourceProvider(getWorkingCopyEPackage(),
                OrganisationModelEditPlugin.getPlugin());
    }

    @Override
    protected URI getResourceURI(IResource resource) {
        /*
         * Add the last modification time-stamp as a query to the URI to
         * distinguish between different revision history resources if opened at
         * the same time. This value will also be used to fill in the last
         * modified value in the OrgModel properties view / badge.
         */
        if (storage instanceof IFileState) {
            long modificationTime =
                    ((IFileState) storage).getModificationTime();
            return URI.createURI(resource.getName()).appendQuery(String
                    .valueOf(modificationTime));
        }
        return super.getResourceURI(resource);
    }

    @Override
    public EPackage getWorkingCopyEPackage() {
        return OMPackage.eINSTANCE;
    }

    public void setEditorInput(IEditorInput input) {
        this.input = input;

    }

    public IEditorInput getEditorInput() {
        return input;
    }

}
