/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.wm.pageflow.component;

import com.tibco.amf.sca.model.componenttype.ComponentType;
import com.tibco.amf.sca.model.extensionpoints.Implementation;
import com.tibco.bx.composite.core.extensions.IComponentTypeContributor;

/**
 * @author Jan Arciuchiewicz
 */
public class BXPageflowComponentTypeContributor implements
        IComponentTypeContributor {

    /**
     * {@inheritDoc}
     */
    public void contributeToComponentType(ComponentType serviceType,
            Implementation implementation) {
        System.out
                .println("Called 'contributeToComponentType' for: " + serviceType); //$NON-NLS-1$
    }

}
