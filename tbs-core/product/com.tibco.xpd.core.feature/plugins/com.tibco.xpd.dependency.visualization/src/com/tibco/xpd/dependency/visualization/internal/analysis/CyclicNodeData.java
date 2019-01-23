/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.dependency.visualization.internal.analysis;

/**
 * Placeholder to store cyclic node data
 * 
 * @author Ali
 * @since 13 Nov 2014
 */
public class CyclicNodeData {

	String srcNodeName;

	String destNodeName;

	private boolean hasCycle = false;

	/**
	 * @param src Source Node
	 * @param dest Destination Node
	 * @param hasCycle {@link Boolean} defines has cycle or not between src and dest nodes.
	 */
	CyclicNodeData(String src, String dest, boolean hasCycle) {
		this.srcNodeName = src;
		this.destNodeName = dest;
		setHasCycle(hasCycle);
	}

	/**
	 * @return the hasCycle
	 */
	public boolean getHasCycle() {
		return hasCycle;
	}

	/**
	 * @param hasCycle
	 *            the hasCycle to set
	 */
	public void setHasCycle(boolean hasCycle) {
		this.hasCycle = hasCycle;
	}
}
