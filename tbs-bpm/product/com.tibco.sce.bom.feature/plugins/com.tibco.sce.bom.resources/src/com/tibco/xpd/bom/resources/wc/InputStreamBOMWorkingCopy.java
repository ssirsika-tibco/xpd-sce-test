/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.resources.wc;

import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.resources.wc.gmf.InputStreamGMFWorkingCopy;

/**
 * BOM Working Copy for resources opened from revision history (based on
 * InputStream rather than IFile).
 * 
 * @author njpatel
 * 
 * @since 3.5
 */
public class InputStreamBOMWorkingCopy extends InputStreamGMFWorkingCopy {

    private final IStorage storage;

    /**
     * BOM Working Copy for Input Stream resources (revision history).
     * 
     * @param resource
     *            the resource in the file system this history belongs to
     * @param storage
     *            revision history resource
     * @throws CoreException
     */
    public InputStreamBOMWorkingCopy(IResource resource, IStorage storage)
            throws CoreException {
        super(resource, storage.getContents());
        this.storage = storage;
    }

    @Override
    public EPackage getWorkingCopyEPackage() {
        return UMLPackage.eINSTANCE;
    }

    @Override
    public String getMetaText(EObject eo) {
        String text = BOMWorkingCopy._getMetaText(eo);
        return text != null ? text : super.getMetaText(eo);
    }

    @Override
    protected URI getResourceURI(IResource resource) {
        /*
         * Add the last modification time-stamp as a query to the URI to
         * distinguish between different revision history resources if opened at
         * the same time. This value will also be used to fill in the last
         * modified value in the Model properties view / badge.
         */
        if (storage instanceof IFileState) {
            long modificationTime =
                    ((IFileState) storage).getModificationTime();
            return URI.createURI(resource.getName()).appendQuery(String
                    .valueOf(modificationTime));
        }
        return super.getResourceURI(resource);
    }

}
