/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.rcp.internal;

import com.tibco.xpd.rcp.internal.resources.IRCPContainer;

/**
 * A listener that can be registered to get notification of resources being
 * opened/created.
 * 
 * @author njpatel
 * 
 */
public interface IOpenResourceListener {

    /**
     * The given resource has been opened/created.
     * 
     * @param resource
     */
    void opened(IRCPContainer resource);
}
