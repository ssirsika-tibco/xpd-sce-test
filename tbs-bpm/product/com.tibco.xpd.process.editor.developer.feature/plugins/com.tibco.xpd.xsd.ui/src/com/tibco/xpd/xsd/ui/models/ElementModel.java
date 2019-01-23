/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.xsd.ui.models;

/**
 * Model used for storing the name and type of the element
 * that appears in the xsd schema
 * @author glewis
 */
public class ElementModel {
    
    /**
     * Element type
     */
    private String type;
    
    /**
     * Element name
     */
    private String name;

    public ElementModel(String type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }    
}
