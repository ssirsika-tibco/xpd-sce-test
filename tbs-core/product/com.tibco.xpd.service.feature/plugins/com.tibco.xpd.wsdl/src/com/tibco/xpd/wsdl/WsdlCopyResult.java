/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.wsdl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * class to hold a status and map of wsdl/xsd file and its original location.
 * This is desired by SDS team as they want to get rid of their copy of
 * WsdlCopier and use this one as api. see SCF-188
 * 
 * 
 * @author bharge
 * @since 17 Jun 2013
 */
public class WsdlCopyResult {

    private IStatus status = Status.OK_STATUS;

    private Map<IFile, String> copiedResources = new HashMap<IFile, String>();;

    /**
     * @return the status
     */
    public IStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(IStatus status) {
        this.status = status;
    }

    /**
     * @return the copiedResources
     */
    public Map<IFile, String> getCopiedResources() {
        return copiedResources;
    }
}