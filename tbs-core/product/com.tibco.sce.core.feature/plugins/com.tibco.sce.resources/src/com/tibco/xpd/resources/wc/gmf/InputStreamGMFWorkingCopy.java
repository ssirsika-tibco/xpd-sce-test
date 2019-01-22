/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.resources.wc.gmf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.resources.wc.InvalidFileException;

/**
 * Extension to the {@link AbstractGMFWorkingCopy} that creates a working copy
 * from an {@link InputStream}. This will typically be used for resources being
 * opened from revision history.
 * 
 * @author njpatel
 * 
 * @since 3.5
 */
public abstract class InputStreamGMFWorkingCopy extends AbstractGMFWorkingCopy {

    private InputStream inputStream;

    /**
     * Working copy for a InputStream resource.
     * 
     * @param resource
     * @param inputStream
     */
    public InputStreamGMFWorkingCopy(IResource resource, InputStream inputStream) {
        this(Collections.singletonList(resource));
        this.inputStream = inputStream;
    }

    /**
     * @param resources
     */
    protected InputStreamGMFWorkingCopy(List<IResource> resources) {
        super(resources);
    }

    @Override
    public boolean isExist() {
        /*
         * Resource may not 'exist' if it is a remote repository revision. We
         * have to pretend it exists in order to get doLoadModel() to go forward
         * and call loadResource()
         */
        return inputStream != null;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    /**
     * Get the URI of the resource to create for this working copy. Default
     * implementation will just create the URI based on the resource name.
     * 
     * @param resource
     * @return
     */
    protected URI getResourceURI(IResource resource) {
        return URI.createURI(resource.getName());
    }

    @Override
    protected Resource loadResource(IResource resource)
            throws InvalidFileException {

        URI uri = getResourceURI(resource);
        Assert.isNotNull(uri, "Resource URI cannot be null."); //$NON-NLS-1$
        Resource res = getEditingDomain().createResource(uri.toString());
        try {
            res.load(inputStream, Collections.EMPTY_MAP);
        } catch (IOException e) {
            throw new InvalidFileException(
                    String
                            .format("Failed to load resource(%s) from alternative InputStream.", //$NON-NLS-1$
                                    resource.getFullPath().toString()), e);
        }
        return res;
    }

    @Override
    protected IResourceChangeListener createResourceChangeListener() {
        /*
         * Do not respond to the resources changes, this would be on a save of
         * the actual workspace xpdl file rather than the temporary stream copy
         * that we have so we don't want to attempt to reload etc!
         */
        return null;
    }

    @Override
    protected void registerEditingDomainListeners(EditingDomain ed) {
        /*
         * Do not respond to the ed domain changes, this would be on a save of
         * the actual workspace xpdl file rather than the temporary stream copy
         * that we have so we don't want to attempt to reload etc!
         */
    }

    @Override
    protected IOperationHistoryListener createOperationHistoryListener() {
        /*
         * Do not respond to the op history changes, this would be on a save of
         * the actual workspace xpdl file rather than the temporary stream copy
         * that we have so we don't want to attempt to reload etc!
         */
        return null;
    }

    @Override
    protected void doSave() throws IOException {
        throw new RuntimeException(
                "Cannot save resource that was opened with alternate InputStream"); //$NON-NLS-1$
    }

}
