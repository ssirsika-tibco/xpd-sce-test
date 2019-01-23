package com.tibco.example.workingcopy;

/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.WorkingCopyFactory;

/**
 * {@link WorkingCopyFactory} for com.tibco.example.model. This is contributed
 * to plugin.xml to provide working (in memory) copy of the model file.
 * WorkingCopies support standard listening, event publishing and o for model
 * files.
 * 
 * @author aallway
 * @since 6 Feb 2015
 */
public class ExampleWorkingCopyFactory implements WorkingCopyFactory {

    /**
     * @see com.tibco.xpd.resources.WorkingCopyFactory#getWorkingCopy(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     */
    @Override
    public WorkingCopy getWorkingCopy(IResource resource) {
        return new ExampleWorkingCopy(Collections.singletonList(resource));
    }

    /**
     * @see com.tibco.xpd.resources.WorkingCopyFactory#isFactoryFor(org.eclipse.core.resources.IResource)
     * 
     * @param resource
     * @return
     */
    @Override
    public boolean isFactoryFor(IResource resource) {
        if (resource instanceof IFile) {
            String ext = resource.getFileExtension();
            if ("exmp".equals(ext)) { //$NON-NLS-1$
                return true;
            }
        }
        return false;
    }

}
