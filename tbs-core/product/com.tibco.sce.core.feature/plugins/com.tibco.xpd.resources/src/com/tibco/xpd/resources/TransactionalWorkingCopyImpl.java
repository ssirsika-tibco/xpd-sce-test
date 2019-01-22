/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.resources;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.resources.wc.AbstractTransactionalWorkingCopy;

/**
 * Default implementation of the {@link AbstractTransactionalWorkingCopy}. This
 * class may be used as is for a {@link WorkingCopy} or extended for more
 * specific implementation.
 * <p>
 * If this class is used as is it will load the {@link Resource} being managed
 * into the global resource set and the first element in the resource's contents
 * will be selected as the root element.
 * </p>
 * 
 * @author njpatel
 * @since 3.2
 */
public class TransactionalWorkingCopyImpl extends
        AbstractTransactionalWorkingCopy {

    private final EPackage ePackage;

    public TransactionalWorkingCopyImpl(List<IResource> resources,
            EPackage ePackage) {
        super(resources);
        this.ePackage = ePackage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.WorkingCopy#getWorkingCopyEPackage()
     */
    public EPackage getWorkingCopyEPackage() {
        return ePackage;
    }

}
