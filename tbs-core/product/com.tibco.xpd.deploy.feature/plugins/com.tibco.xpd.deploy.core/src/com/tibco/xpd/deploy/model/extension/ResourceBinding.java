/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.net.URL;

/**
 * 
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public interface ResourceBinding {

    String getLocalId();

    String getLocalName();

    String getLocalType();

    URL getLocalModuleURL();

    String getSharedResourceId();

    void setSharedResourceId(String sharedResouceId);
}
