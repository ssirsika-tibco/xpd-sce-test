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
package com.tibco.xpd.scripteditors.internal.xpath.styles;

/**
 * Contains the symbolic name of styles used by LineStyleProvider,
 * ColorManager, and any others who may be interested
 */
public interface IStyleConstantsXPath {
	public static final String DEFAULT = "DEFAULTXPath";//$NON-NLS-1$
	public static final String KEYWORD = "KEYWORDXPath";//$NON-NLS-1$
	public static final String COMMENT = "COMMENTXPath";//$NON-NLS-1$
	public static final String LITERAL = "LITERALXPath";//$NON-NLS-1$
	public static final String UNFINISHED_COMMENT = "UNFINISHED_COMMENTXPath";//$NON-NLS-1$
}
