/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.validation.internal;

import java.util.Collection;

import com.tibco.xpd.validation.destinations.Destination;

/**
 * Workspace resource validation destination.
 * 
 * @author njpatel
 * 
 */
public class WorkspaceResourceValidationDestination extends Destination {

    private final String assetId;

    /**
     * Workspace resource validation destination.
     * 
     * @param id
     *            id of destination
     * @param name
     *            name of destination (used as prefix in problems view)
     * @param version
     *            version of destination (used as part of prefix in problems
     *            view if specified)
     * @param assetId
     *            project asset id
     * @param validationProviders
     *            validation provider ids.
     */
    public WorkspaceResourceValidationDestination(String id, String name,
            String version, String assetId,
            Collection<String> validationProviders) {
        super(id, name, version, false, validationProviders);
        this.assetId = assetId;
    }

    /**
     * Get asset id.
     * 
     * @return asset id or <code>null</code> if one is not specified.
     */
    public String getAssetId() {
        return assetId;
    }

}
