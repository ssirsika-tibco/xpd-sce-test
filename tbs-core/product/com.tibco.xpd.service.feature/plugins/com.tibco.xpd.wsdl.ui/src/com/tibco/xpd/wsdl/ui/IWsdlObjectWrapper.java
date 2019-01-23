/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.wsdl.ui;

import org.eclipse.core.runtime.IAdaptable;

/**
 * @author nwilson
 */
public interface IWsdlObjectWrapper extends IAdaptable {
    /**
     * @return The wrapped object.
     */
    Object getObject();

    /**
     * @return The parent of the wrapped object or null.
     */
    Object getParent();

    /**
     * @return The local name of WSDL object.
     */
    String getWsdlObjectLocalName();

    /**
     * @return The type of WSDL element.
     */
    String getWsdlTypeName();
}
