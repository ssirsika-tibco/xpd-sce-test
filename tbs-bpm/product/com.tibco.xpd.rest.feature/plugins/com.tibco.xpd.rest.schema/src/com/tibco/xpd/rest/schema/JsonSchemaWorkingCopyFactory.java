package com.tibco.xpd.rest.schema;

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * Factory for JSON Schema working copies.
 * 
 * @author nwilson
 * @since 30 Jan 2015
 */
public class JsonSchemaWorkingCopyFactory implements WorkingCopyFactory {

    /**
     * @see com.tibco.xpd.resources.WorkingCopyFactory
     *      #getWorkingCopy(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     *            The JSON Schema file resource.
     * @return A new working copy for the file.
     */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new JsonSchemaWorkingCopy(Collections.singletonList(resource));
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopyFactory
     *      #isFactoryFor(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     *            The resource to check.
     * @return true if the file has the "jsd" extension.
     */
    @Override
    public boolean isFactoryFor(IResource resource) {
        boolean isFactory = false;
        if (resource instanceof IFile) {
            String ext = ((IFile) resource).getFileExtension();

            isFactory = (ext != null && ext.equalsIgnoreCase("jsd")); //$NON-NLS-1$
        }
        return isFactory;
    }

}
