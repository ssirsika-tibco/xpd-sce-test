/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tibco.xpd.script.sourceviewer.internal.contentassist;



public class TextRange {

	public int offStart;
	public int right;

	public TextRange(int newOffStart, int newRight) {
		this.offStart = newOffStart;
		this.right = newRight;
	}

}
