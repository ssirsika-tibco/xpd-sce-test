/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.transform.de.component;

import com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.CommonFactory;

/**
 * @author mtorres
 * 
 */
public class DEImplementationDelegate extends ImplementationDelegate {

    @Override
    public Implementation createDefaultImplementation() {
        return CommonFactory.eINSTANCE.createDEImplementation();
    }

}
