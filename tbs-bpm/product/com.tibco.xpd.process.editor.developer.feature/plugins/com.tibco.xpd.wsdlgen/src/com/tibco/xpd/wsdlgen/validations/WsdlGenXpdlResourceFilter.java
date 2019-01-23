/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.wsdlgen.validations;

import org.eclipse.core.resources.IFile;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IFileFilter;
import com.tibco.xpd.xpdl2.resources.Xpdl2WorkingCopyImpl;

/**
 * Returns true for any file that has xpdl working copy.
 * 
 * @author aallway
 * @since 29 Jul 2011
 */
public class WsdlGenXpdlResourceFilter implements IFileFilter {

    /**
     * @see com.tibco.xpd.validation.provider.IFileFilter#accept(org.eclipse.core.resources.IFile)
     * 
     * @param file
     * @return
     */
    @Override
    public boolean accept(IFile file) {
        if (file != null) {
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopy(file);
            if (wc instanceof Xpdl2WorkingCopyImpl) {
                return true;
            }
        }
        return false;
    }

}
