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
package com.tibco.xpd.rql.script.preferences.ui;

import com.tibco.xpd.rql.script.RQLScriptPlugin;

/**
 * Help context ids for the XPath Source Editor.
 * <p>
 * This interface contains constants only; it is not intended to be implemented.
 * </p>
 * 
 */
public interface IRQLHelpContextIds {
    public static final String PREFIX = RQLScriptPlugin.PLUGIN_ID + "."; //$NON-NLS-1$

    // RQL Source page editor
    public static final String RQL_SOURCEVIEW_HELPID =
            "RQLSourceView_source_HelpId"; //$NON-NLS-1$

    // RQL Files Preference page
    public static final String RQL_PREFWEBX_FILES_HELPID = PREFIX + "webx0043"; //$NON-NLS-1$

    // RQL Source Preference page
    public static final String RQL_PREFWEBX_SOURCE_HELPID = PREFIX + "webx0044"; //$NON-NLS-1$

    // RQL Styles Preference page
    public static final String RQL_PREFWEBX_STYLES_HELPID = PREFIX + "webx0045"; //$NON-NLS-1$

    // Content Assist action
    public static final String RQL_CONTMNU_CONTENTASSIST_HELPID =
            PREFIX + "rql0000"; //$NON-NLS-1$

    // JavaScript Styles Preference page
    public static final String RQL_PREFWEBX_STYLES_HELPID2 =
            PREFIX + "webx0042"; //$NON-NLS-1$

}
