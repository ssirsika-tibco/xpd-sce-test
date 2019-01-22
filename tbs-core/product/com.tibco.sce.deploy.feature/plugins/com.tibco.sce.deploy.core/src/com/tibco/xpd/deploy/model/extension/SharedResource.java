/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

/**
 * Represent shared resource on the server.
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public interface SharedResource {

    /**
     * Returns the identifier of shared resource.
     * 
     * @return the identifier of shared resource.
     */
    String getId();

    /**
     * Returns the name of shared resource.
     * 
     * @return the name of shared resource.
     */
    String getName();

    /**
     * Returns the type of shared resource.
     * 
     * @return the type of shared resource.
     */
    String getType();
}
