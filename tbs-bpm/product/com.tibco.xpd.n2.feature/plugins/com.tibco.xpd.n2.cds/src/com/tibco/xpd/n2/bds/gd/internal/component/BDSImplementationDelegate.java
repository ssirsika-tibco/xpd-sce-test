/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.n2.bds.gd.internal.component;

import com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.CommonFactory;

/**
 * Creates DBS implementation.
 * 
 * @author Jan Arciuchiewicz
 */
public class BDSImplementationDelegate extends ImplementationDelegate {

    @Override
    public Implementation createDefaultImplementation() {
        return CommonFactory.eINSTANCE.createBDSImplementation();
    }

}
