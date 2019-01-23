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
package com.tibco.xpd.rql.script.styles;

/**
 * Contains the symbolic name of styles used by LineStyleProvider,
 * ColorManager, and any others who may be interested
 */
public interface IStyleConstantsRQL {
	public static final String DEFAULT = "DEFAULTRQL";//$NON-NLS-1$
    public static final String KEYWORDENTITIES = "KEYWORDENTITIESRQL";//$NON-NLS-1$
    public static final String KEYWORDATTRIBUTES = "KEYWORDATTRIBUTESRQL";//$NON-NLS-1$
    public static final String KEYWORDOPERATORS = "KEYWORDOPERATORSRQL";//$NON-NLS-1$
}
