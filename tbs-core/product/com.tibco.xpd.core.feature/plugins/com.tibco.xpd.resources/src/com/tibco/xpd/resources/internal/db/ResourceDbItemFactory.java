/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

import com.tibco.xpd.resources.internal.db.impl.ResourceDbItem;

/**
 * 
 * @author ramin
 *
 */
public class ResourceDbItemFactory {

	/**
	 * @param name
	 * @param uri
	 * @return
	 */
	public static IResourceDbItem createDefaultItem(boolean x, String projectName, String bomPath, String name, String uri, String imageURI){
		return new ResourceDbItem(true, projectName, bomPath, name, uri, ResourceItemType.FILE.toString(), imageURI);
	}

	/**
	 * @param name
	 * @param uri
	 * @param type
	 * @return
	 */
	public static IResourceDbItem createDefaultItem(boolean x, String projectName, String bomPath, String name, String uri, String type, String imageURI){
		return new ResourceDbItem(true, projectName, bomPath, name, uri, type, imageURI);
	}
	
	/**
	 * @param className
	 * @param name
	 * @param uri
	 * @param type
	 * @return
	 * @throws ResourceDbException
	 */
	public static IResourceDbItem createItem(String projectName, String className, String name, String uri, ResourceItemType type) throws ResourceDbException {
		throw new ResourceDbException(ResourceDbFactory.DB_NOT_IMPLEMENTED);
	}

}
