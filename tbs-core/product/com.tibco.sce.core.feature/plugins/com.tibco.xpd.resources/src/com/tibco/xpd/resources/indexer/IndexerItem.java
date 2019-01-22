/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.resources.indexer;

import java.util.Set;

/**
 * Indexer item that is read from and written to the indexer.
 * 
 * @author njpatel
 * 
 */
public interface IndexerItem {

    /**
     * A name, for implementer use.
     * 
     * @return name
     */
    public String getName();

    /**
     * Type of the resource, for implementer use. 
     * 
     * @return type
     */
    public String getType();

    /**
     * The unique across the workspace URI of an indexed object.
     * 
     * @return uri
     */
    public String getURI();

    /**
     * Additional attributes can be defined by the extension point
     * workingCopyIndexer.
     * 
     * @param attribute
     *            attribute name
     * @return attribute value
     */
    public String get(String attribute);

    /**
     * Get the set of attributes being managed by this indexer. This can be used
     * in {@link #get(String) get} to get the values of the attributes.
     * 
     * @see #get(String)
     * 
     * @return set of attribute keys
     */
    public Set<String> keys();

}
