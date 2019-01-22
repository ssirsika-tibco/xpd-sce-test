/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.resources.internal.indexer;

import java.util.Map;
import java.util.Set;

import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * The Indexer item implementation of the InderItem interface by storing the
 * interface's attributes as fields and the additional attributes in a Map.
 * 
 * @author rassisi
 * 
 * @deprecated use com.tibco.xpd.resources.indexer.IndexerItemImpl
 */
@Deprecated
public class IndexerItemImpl implements IndexerItem {

    /**
     * The name of the resource item
     */
    private String name;

    /**
     * The type is an abstraction from the current EMF/uml2 implementation. The
     * actually supported types are in R
     */
    private String type;

    /**
     * The uri is used as a unique key in the resource database.
     */
    private String uri;

    private Map<String, String> additionalAttributes;

    public IndexerItemImpl(String name, String type, String uri,
            Map<String, String> additionalAttributes) {
        this();
        this.name = name;
        this.type = type;
        this.uri = uri;
        this.additionalAttributes = additionalAttributes;
    }

    /**
     * 
     */
    public IndexerItemImpl() {
    }

    public String get(String attribute) {
        return additionalAttributes.get(attribute);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getURI() {
        return uri;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    public Set<String> keys() {
        return additionalAttributes.keySet();
    }
}
