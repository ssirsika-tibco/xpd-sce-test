/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.ui.complexdatatype;

/**
 * Small data class to describe a reference to an external complex data type.
 * <p>
 * It is entirely up to a particular {@link ComplexDataTypeRefResolver} to
 * decide whether to use one or both the path and the ref parts of the
 * reference.
 * <p>
 * It is only necessary for it to be consistent in conversion of object to
 * reference details and visa versa.
 * 
 * @author aallway
 * 
 */
public class ComplexDataTypeReference {
    /** [Optional] The path to the object's container. */
    private String location = ""; //$NON-NLS-1$

    /** [Optional] The ref to the object within it's container. */
    private String xRef = ""; //$NON-NLS-1$

    /** [Optional] The object's namespace. */
    private String nameSpace = ""; //$NON-NLS-1$

    /**
     * Create a complex data type reference.
     * 
     * @param location
     *            [Optional] Path to object's container.
     * @param xRef
     *            [Optional] Reference to object.
     * @param nameSpace
     *            [Optional] object namespace.
     */
    public ComplexDataTypeReference(String location, String xRef,
            String nameSpace) {
        if (location != null) {
            this.location = location;
        }

        if (xRef != null) {
            this.xRef = xRef;
        }

        if (nameSpace != null) {
            this.nameSpace = nameSpace;
        }
    }

    /**
     * @return the path to the object's container
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the ref to the object within it's container
     */
    public String getXRef() {
        return xRef;
    }

    /**
     * @param location
     *            the path to the object's container to set
     */
    public void setLocation(String location) {
        if (location != null) {
            this.location = location;
        } else {
            this.location = ""; //$NON-NLS-1$
        }
    }

    /**
     * @param xRef
     *            the ref to the object within it's container to set
     */
    public void setXRef(String xRef) {
        if (xRef != null) {
            this.xRef = xRef;
        } else {
            this.xRef = ""; //$NON-NLS-1$
        }
    }

    /**
     * @return the nameSpace
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * @param nameSpace
     *            the nameSpace to set
     */
    public void setNameSpace(String nameSpace) {
        if (nameSpace != null) {
            this.nameSpace = nameSpace;
        } else {
            this.nameSpace = ""; //$NON-NLS-1$
        }
    }

}
