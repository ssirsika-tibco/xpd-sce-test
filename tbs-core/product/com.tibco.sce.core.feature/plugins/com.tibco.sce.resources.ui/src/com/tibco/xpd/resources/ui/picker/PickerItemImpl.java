/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.resources.ui.picker;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.resources.indexer.IndexerItem;

/**
 * Basic implementation of PickerItem. It also allows you to create
 * {@link PickerItem}s based on {@link IndexerItem}.
 * 
 * @since 3.3.1
 * @author Jan Arciuchiewicz
 */
public class PickerItemImpl implements PickerItem {

    private PickerContentExtension provider;

    private String qualifiedName;

    public PickerItemImpl(IndexerItem indexerItem,
            PickerContentExtension provider) {
        attr = new HashMap<String, String>();
        for (String key : indexerItem.keys()) {
            attr.put(key, indexerItem.get(key));
        }
        this.name = indexerItem.getName();
        this.type = indexerItem.getType();
        this.uri = indexerItem.getURI();
        this.provider = provider;
    }

    public PickerItemImpl(String name, String type, String uri,
            Map<String, String> additionalAttributes,
            PickerContentExtension provider) {
        this.name = name;
        this.type = type;
        this.uri = uri;
        this.attr = additionalAttributes;
        this.provider = provider;
    }

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

    /**
     * Additional attributes. This map will contain all indexer item's
     * additional attributes if PickerItemImpl was created from an
     * {@link IndexerItem}.
     */
    protected Map<String, String> attr;

    /**
     * {@inheritDoc}
     */
    public String get(String attribute) {
        return attr == null ? null : attr.get(attribute);
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    public String getURI() {
        return uri;
    }

    /**
     * Sets the picker item's name.
     * 
     * @param name
     *            new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the picker item's type.
     * 
     * @param type
     *            new type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets the picker item's URI.
     * 
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    public Set<String> keys() {
        if (attr != null) {
            return attr.keySet();
        }
        return Collections.emptySet();
    }

    /**
     * Set the additional parameter value. It remove the parameter when the
     * value is null.
     * 
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        if (attr == null) {
            attr = new HashMap<String, String>();
        }
        if (value != null) {
            attr.put(key, value);
        } else {
            attr.remove(key);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PickerContentExtension getPickerExtension() {
        return provider;
    }

    /**
     * @param provider
     */
    public void setProvider(PickerContentExtension provider) {
        this.provider = provider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getQualifiedName() {
        if (qualifiedName != null) {
            return qualifiedName;
        }
        return getName();
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result + ((provider == null) ? 0 : provider.hashCode());
        result =
                prime * result + ((getURI() == null) ? 0 : getURI().hashCode());
        return result;
    }

    /**
     * Picker items are consider to be equal if they have the {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PickerItem other = (PickerItem) obj;
        if (provider == null) {
            if (other.getPickerExtension() != null)
                return false;
        } else if (!provider.equals(other.getPickerExtension()))
            return false;
        if (getURI() == null) {
            if (other.getURI() != null)
                return false;
        } else if (!getURI().equals(other.getURI()))
            return false;
        return true;
    }
}
