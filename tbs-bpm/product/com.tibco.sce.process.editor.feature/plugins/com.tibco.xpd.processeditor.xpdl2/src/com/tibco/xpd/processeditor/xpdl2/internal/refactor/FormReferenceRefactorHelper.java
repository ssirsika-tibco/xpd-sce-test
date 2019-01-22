/**
 * Copyright (c) TIBCO Software Inc 2004-2012. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.internal.refactor;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Refactor helper for Form references. This subclasses the default
 * {@link XpdlReferenceRefactorHelper} as Forms references go from Forms to Xpdl
 * (not the other way round).
 * 
 */
public class FormReferenceRefactorHelper extends XpdlReferenceRefactorHelper {

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.internal.refactor.XpdlReferenceRefactorHelper#getAffectedResources(org.eclipse.core.resources.IFile)
     * 
     * @param file
     * @return
     */
    @Override
    protected Collection<IResource> getAffectedResources(IFile file) {
        /* For form references the references are from Form file to XPDL */
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);

        if (wc != null) {
            return wc.getDependency();
        }
        return new ArrayList<IResource>(0);
    }

}