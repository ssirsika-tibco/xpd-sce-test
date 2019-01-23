/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.deploy.model.extension;

/**
 * Basic SharedResource implementation.
 * <p>
 * <i>Created: 10 Feb 2009</i>
 * </p>
 * 
 * @author Jan Arciuchiewicz
 */
public class SharedResourceImpl implements SharedResource {

    private final String id;
    private final String name;
    private final String type;

    /**
     * Creates shared resource.
     */
    public SharedResourceImpl(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;

    }

    /** {@inheritDoc} */
    public String getId() {
        return id;
    }

    /** {@inheritDoc} */
    public String getName() {
        return name;
    }

    /** {@inheritDoc} */
    public String getType() {
        return type;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SharedResourceImpl other = (SharedResourceImpl) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SharedResource id: "); //$NON-NLS-1$
        stringBuilder.append(id);
        stringBuilder.append(", name: "); //$NON-NLS-1$
        stringBuilder.append(name);
        stringBuilder.append(", type: "); //$NON-NLS-1$
        stringBuilder.append(type);
        return stringBuilder.toString();
    }

}
