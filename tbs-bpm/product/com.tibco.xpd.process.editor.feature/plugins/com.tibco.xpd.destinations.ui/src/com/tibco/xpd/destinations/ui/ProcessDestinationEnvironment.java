/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.destinations.ui;

/**
 * Simple data class representing a Process Destination Environment
 * 
 * @author aallway
 *
 */
public class ProcessDestinationEnvironment {

    private String id = ""; //$NON-NLS-1$
    
    private String name = ""; //$NON-NLS-1$
    
    private boolean userSelectable = false;
    
    private String validationDestinationId = ""; //$NON-NLS-1$

    private String version = ""; //$NON-NLS-1$
    
    private boolean includeXPathErrors = true;
    
    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userSelectable
     */
    public boolean isUserSelectable() {
        return userSelectable;
    }

    /**
     * @param userSelectable the userSelectable to set
     */
    public void setUserSelectable(boolean userSelectable) {
        this.userSelectable = userSelectable;
    }

    /**
     * @return the validationDestinationId
     */
    public String getValidationDestinationId() {
        return validationDestinationId;
    }

    /**
     * @param validationDestinationId the validationDestinationId to set
     */
    public void setValidationDestinationId(String validationDestinationId) {
        this.validationDestinationId = validationDestinationId;
    }

    /**
     * @return the includeXPathErrors
     */
    public boolean isIncludeXPathErrors() {
        return includeXPathErrors;
    }

    /**
     * @param includeXPathErrors the includeXPathErrors to set
     */
    public void setIncludeXPathErrors(boolean includeXPathErrors) {
        this.includeXPathErrors = includeXPathErrors;
    }
    
    
    
}
