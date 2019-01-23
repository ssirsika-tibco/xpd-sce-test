/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.n2.brm.component;

import com.tibco.amf.sca.componenttype.implementation.ImplementationDelegate;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.n2.model.common.CommonFactory;

/**
 * Implementation delegate for Event Collection component.
 * 
 * @author jarciuch
 * @since 10 Jan 2014
 */
public class EcImplementationDelegate extends ImplementationDelegate {
    @Override
    public Implementation createDefaultImplementation() {
        return CommonFactory.eINSTANCE.createECImplementation();
    }
}
