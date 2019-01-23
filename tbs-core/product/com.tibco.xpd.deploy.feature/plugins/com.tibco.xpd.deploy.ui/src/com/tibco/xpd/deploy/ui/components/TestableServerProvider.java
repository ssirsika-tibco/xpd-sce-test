/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.deploy.ui.components;

import com.tibco.xpd.deploy.Server;

/**
 * Provide deployment server that can be tested. It is a temporary deployment
 * server which might not be fully configured, but may be useful for testing
 * purposes.
 * 
 * @author Jan Arciuchiewicz
 */
public interface TestableServerProvider {

    /**
     * Gets a testable server.
     * 
     * @return a temporary deployment server which might not be fully
     *         configured, but may be useful for testing purposes.
     */
    Server getTestableServer();
}
