/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

import java.net.URL;

/**
 * Basic resource binding implementation.
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class ResourceBindingImpl implements ResourceBinding {

    protected String localId;

    protected String localName;

    protected String localType;

    protected URL localURL;

    protected String sharedResourceId;

    public ResourceBindingImpl() {
    }

    /**
     *  
     */
    public ResourceBindingImpl(String localId, String localName,
            String localType, URL localURL) {
        this.localId = localId;
        this.localName = localName;
        this.localType = localType;
        this.localURL = localURL;
        this.sharedResourceId = null;
    }

    /**
     *  
     */
    public ResourceBindingImpl(String id, String name, String type,
            URL localURL, String sharedResourceId) {
        this(id, name, type, localURL);
        this.sharedResourceId = sharedResourceId;
    }

    /**
     * Creates the new binding based on existing one.
     * 
     * @param binding
     * @param sharedResourceId
     */
    public ResourceBindingImpl(ResourceBinding binding, String sharedResourceId) {
        this(binding.getLocalId(), binding.getLocalName(), binding
                .getLocalType(), binding.getLocalModuleURL(), sharedResourceId);
    }

    /** {@inheritDoc} */
    public URL getLocalModuleURL() {
        return localURL;
    }

    /** {@inheritDoc} */
    public String getLocalName() {
        return localName;
    }

    /** {@inheritDoc} */
    public String getLocalType() {
        return localType;
    }

    /** {@inheritDoc} */
    public String getLocalId() {
        return localId;
    }

    /** {@inheritDoc} */
    public String getSharedResourceId() {
        return sharedResourceId;
    }

    /**
     * Sets shared resource id.
     * 
     * @param sharedResourceId
     *            the sharedResourceId to set
     */
    public void setSharedResourceId(String sharedResourceId) {
        this.sharedResourceId = sharedResourceId;
    }

    /**
     * @return the localURL
     */
    protected URL getLocalURL() {
        return localURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((localId == null) ? 0 : localId.hashCode());
        result =
                prime * result
                        + ((localType == null) ? 0 : localType.hashCode());
        result =
                prime * result + ((localURL == null) ? 0 : localURL.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceBindingImpl other = (ResourceBindingImpl) obj;
        if (localId == null) {
            if (other.localId != null)
                return false;
        } else if (!localId.equals(other.localId))
            return false;
        if (localType == null) {
            if (other.localType != null)
                return false;
        } else if (!localType.equals(other.localType))
            return false;
        if (localURL == null) {
            if (other.localURL != null)
                return false;
        } else if (!localURL.equals(other.localURL))
            return false;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Binding id: "); //$NON-NLS-1$
        sb.append(localId);
        sb.append(", name: "); //$NON-NLS-1$
        sb.append(localName);
        sb.append(", type: "); //$NON-NLS-1$
        sb.append(localType);
        sb.append(", moduleURL: "); //$NON-NLS-1$
        sb.append(localURL);
        sb.append(", sharedResourceId: "); //$NON-NLS-1$
        sb.append(sharedResourceId);
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    public SharedResource getSharedResource() {
        return null;
    }

}
