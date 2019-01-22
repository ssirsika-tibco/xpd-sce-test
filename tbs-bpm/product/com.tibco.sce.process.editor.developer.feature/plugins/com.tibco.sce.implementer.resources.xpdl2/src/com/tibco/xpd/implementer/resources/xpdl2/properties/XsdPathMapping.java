/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

/**
 * @author nwilson
 */
public class XsdPathMapping {
    /** The source object. */
    private String source;
    /** The target object. */
    private String target;
    
    /**
     * @param source The source object.
     * @param target The target object.
     */
    public XsdPathMapping(final String source, final String target) {
        this.source = source;
        this.target = target;
    }
    /**
     * @return The source object.
     */
    public String getSource() {
        return source;
    }
    /**
     * @return The target object.
     */
    public String getTarget() {
        return target;
    }
}
