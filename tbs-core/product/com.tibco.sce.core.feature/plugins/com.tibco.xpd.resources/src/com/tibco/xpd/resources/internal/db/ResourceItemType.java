/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

/**
 * The semantics used by the resource db plugin. It is an abstraction over
 * possible different API's.
 * 
 * @author ramin
 * 
 */
public enum ResourceItemType {

	PACKAGE("PACKAGE"), CLASS("CLASS"), FILE("FILE"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		PRIMITIVE_TYPE("PRIMITIVE_TYPE"),  //$NON-NLS-1$
			
			UNKNOWN("UNKNOWN"); //$NON-NLS-1$

	String name;

	ResourceItemType(String name) {
		this.name = name;
	}

	static public ResourceItemType create(String name) {
		if (name.equalsIgnoreCase("package")) { //$NON-NLS-1$
			return PACKAGE;
		} else if (name.equalsIgnoreCase("class")) { //$NON-NLS-1$
			return CLASS;
		} else if (name.equalsIgnoreCase("primitive_type")) { //$NON-NLS-1$
			return PRIMITIVE_TYPE;
		} else if (name.equalsIgnoreCase("file")) { //$NON-NLS-1$
			return FILE;
		}
		return UNKNOWN;
	}
}
