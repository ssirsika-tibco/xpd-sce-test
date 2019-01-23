/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.resources.internal.db;

import java.sql.SQLException;

/**
 * This class does the abstraction from the implementation database exception
 * (e.g. SQLException).
 * 
 * @author ramin
 *
 */
public class ResourceDbException extends Exception {

	public ResourceDbException() {
		super();
	}
	
	public ResourceDbException(SQLException ex){
		super(ex);
	}

	public ResourceDbException(Throwable ex){
		super(ex);
	}

	public ResourceDbException(String msg){
		super(msg);
	}

}
