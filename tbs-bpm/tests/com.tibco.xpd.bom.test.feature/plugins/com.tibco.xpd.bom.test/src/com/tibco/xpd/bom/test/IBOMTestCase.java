/**
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.bom.test;

/**
 * All BOMTestCases must implement this interface to assure that 
 * the COMTestCase works properly.
 * 
 * @author ramin
 *
 */
public interface IBOMTestCase {

	/**
	 * this method will be used internally by BOMTestCase
	 */
	public void setConceptName();
	
	/**
	 * this method will be used internally by BOMTestCase
	 */
	public void setProjectName();

}
