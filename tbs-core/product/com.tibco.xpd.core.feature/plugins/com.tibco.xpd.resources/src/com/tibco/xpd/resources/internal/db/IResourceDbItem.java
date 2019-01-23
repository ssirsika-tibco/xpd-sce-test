/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

/**
 * @author ramin
 *
 */
public interface IResourceDbItem {

	/**
	 * @return Returns the projectName.
	 */
	public String getName();
	
	/**
	 * @param name The full qualified name to set.
	 */
	public void setName(String name);

	/**
	 * @return Returns the resourceName.
	 */
	public String getURI();
	
	/**
	 * @param resourceName The resourceName to set.
	 */
	public void setURI(String resourceName);

	/**
	 * @return Returns the resourcePath.
	 */
	public String getType();

	/**
	 * @param resourcePath The resourcePath to set.
	 */
	public void setType(String type);
	
	/**
	 * @param imageURI
	 */
	public void setImageURI(String imageURI);

	/**
	 * @return
	 */
	public String getImageURI();
	
	/**
	 * 
	 */
	public void setProjectName(String projectName);
	
	/**
	 * @return
	 */
	public String getProjectName();

	/**
	 * @return
	 */
	public String getBomPath();

	/**
	 * @param bomPath
	 */
	public void setBomPath(String bomPath);

}
