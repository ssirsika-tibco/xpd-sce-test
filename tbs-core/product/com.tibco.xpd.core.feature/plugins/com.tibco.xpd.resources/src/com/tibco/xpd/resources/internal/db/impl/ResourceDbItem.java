/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db.impl;

import com.tibco.xpd.resources.internal.db.IResourceDbItem;
import com.tibco.xpd.resources.internal.db.ResourceItemType;

/**
 * This class stores resource information.
 * 
 * @author ramin
 * 
 */
public class ResourceDbItem implements IResourceDbItem {

    /** resource name. */
    private String name;

    /** URI of the resource (with fragment). */
    private String uri;

    /**
     * Actual supported types are: package, class, primitive_type
     */
    private String type;

    /**
     * 
     */
    private String imageURI;

    /**
     * The project name is needed for queries
     */
    private String projectName;

    /**
     * the project relative path to the bom resource
     */
    private String bomPath;

    /**
     * to avoid that it will be called
     */
    ResourceDbItem() {
    }

    /**
     * Create ResourceEntity by <code>IResource</code>.
     * 
     * @param resource
     *            to get information from
     */
    @SuppressWarnings("nls")
    public ResourceDbItem(boolean x, String projectName, String bomPath,
            String name, String uri) {
        this.projectName = projectName;
        this.name = name;
        this.uri = uri;
        this.type = ResourceItemType.FILE.toString();
        this.imageURI = ""; //$NON-NLS-1$
        this.bomPath = bomPath;
    }

    @SuppressWarnings("nls")
    public ResourceDbItem(boolean x, String projectName, String bomPath,
            String name, String uri, String type) {
        this.projectName = projectName;
        this.name = name;
        this.uri = uri;
        this.type = type;
        this.imageURI = ""; //$NON-NLS-1$
        this.bomPath = bomPath;
    }

    @SuppressWarnings("nls")
    public ResourceDbItem(boolean x, String projectName, String bomPath,
            String name, String uri, String type, String imageURI) {
        this.projectName = projectName;
        this.name = name;
        this.uri = uri;
        this.type = type;
        this.imageURI = imageURI;
        this.bomPath = bomPath;
    }

    /**
     * @return Returns the full qualified name.
     */
    @SuppressWarnings("nls")
    public String getName() {
        if (name == null) {
            name = ""; //$NON-NLS-1$
        }
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the resourcePath.
     */
    public String getURI() {
        return uri;
    }

    /**
     * @param resourcePath
     *            The resourcePath to set.
     */
    public void setURI(String uri) {
        this.uri = uri;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDbItem#getType()
     */
    public String getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDbItem#setType(com.tibco.xpd.resources.db.ResourceType)
     */
    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings("nls")
    @Override
    public String toString() {
        String typ = (type == null) ? "null" : type.toString(); //$NON-NLS-1$
        return "ResourceDbItem (name = " + name + " uri = " + uri + "type = " + typ + "image_uri = " + imageURI; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDbItem#setImageURI(java.lang.String)
     */
    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDbItem#getImageURI()
     */
    public String getImageURI() {
        return imageURI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDbItem#getProjectName()
     */
    public String getProjectName() {
        return projectName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.resources.db.IResourceDbItem#setProjectName(java.lang.String)
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBomPath() {
        return bomPath;
    }

    public void setBomPath(String bomPath) {
        this.bomPath = bomPath;
    }

}
