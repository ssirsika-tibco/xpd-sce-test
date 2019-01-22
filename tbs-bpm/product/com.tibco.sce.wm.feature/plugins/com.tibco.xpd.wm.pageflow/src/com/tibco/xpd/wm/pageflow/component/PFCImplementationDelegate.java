/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.pfe.PfeFactory;

/**
 * @author kupadhya
 * 
 */
public class PFCImplementationDelegate extends ImplementationDelegate {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate
     * #createDefaultImplementation()
     */
    @Override
    public Implementation createDefaultImplementation() {
        return PfeFactory.eINSTANCE.createPFESpecification();
    }

}
