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



import java.util.Vector;

public interface ContentElement {


	boolean hasChildren(Object element);

	ContentElement getParent();

	Vector getChildren();

	String getName();

	int getNameOffset();

	int getOffset();

	int getLength();

	int getType();

	void setParent(ContentElement parent);

	void setName(String name);

	void setNameOffset(int nameOffset);

	void setOffset(int offset);

	void setLength(int length);

	void setType(int type);

	void addChild(ContentElement child);
}
