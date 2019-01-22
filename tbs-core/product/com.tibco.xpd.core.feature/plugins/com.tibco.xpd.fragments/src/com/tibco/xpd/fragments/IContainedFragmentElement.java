/*
 * Copyright (c) TIBCO Software Inc. 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.fragments;

/**
 * Interface implemented by the fragment elements that can be contained in a
 * fragment category.
 * 
 * @author njpatel
 * 
 */
public interface IContainedFragmentElement {

	/**
	 * Get the parent of this element.
	 * 
	 * @return parent category.
	 */
	IFragmentCategory getParent();
}
