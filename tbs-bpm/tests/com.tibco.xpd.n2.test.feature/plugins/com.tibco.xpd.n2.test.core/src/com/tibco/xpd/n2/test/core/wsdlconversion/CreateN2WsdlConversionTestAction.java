/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.n2.test.core.wsdlconversion;

import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.IWizard;

import com.tibco.xpd.n2.test.core.CreateN2BaseTestAction;

/**
 * 
 * 
 * @author aallway
 * @since 19 Apr 2011
 */
public class CreateN2WsdlConversionTestAction extends CreateN2BaseTestAction {

    /**
     * @see com.tibco.xpd.n2.test.core.CreateN2BaseTestAction#getTestCreationWizard(java.util.Collection)
     * 
     * @param srcResources
     * @return
     */
    @Override
    protected IWizard getTestCreationWizard(Collection<IResource> srcResources) {
        return new N2CreateWsdlConversionTestWizard(srcResources);
    }

    /**
     * @see com.tibco.xpd.core.createtestwizards.CreateBaseTestAction#isEnabled(java.util.Collection)
     * 
     * @param srcResources
     * @return
     */
    @Override
    protected boolean isEnabled(Collection<IResource> srcResources) {
        /* Enable only if there is a single WSDL or XSD selected. */

        if (super.isEnabled(srcResources)) {
            if (srcResources.size() == 1) {
                IResource src = srcResources.iterator().next();
                if ("wsdl".equalsIgnoreCase(src.getFileExtension()) //$NON-NLS-1$
                        || "xsd".equalsIgnoreCase(src.getFileExtension())) { //$NON-NLS-1$
                    return true;
                }
            }
        }
        return false;
    }
}
