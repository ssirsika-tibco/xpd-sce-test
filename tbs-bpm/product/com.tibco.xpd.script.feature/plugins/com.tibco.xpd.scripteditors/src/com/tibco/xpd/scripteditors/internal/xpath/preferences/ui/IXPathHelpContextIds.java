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
package com.tibco.xpd.scripteditors.internal.xpath.preferences.ui;

import com.tibco.xpd.scripteditors.ScriptEditorsPlugin;

/**
 * Help context ids for the XPath Source Editor.
 * <p>
 * This interface contains constants only; it is not intended to be implemented.
 * </p>
 * 
 */
public interface IXPathHelpContextIds {
    public static final String PREFIX = ScriptEditorsPlugin.PLUGIN_ID + "."; //$NON-NLS-1$

    // XPath Source page editor
    public static final String XPATH_SOURCEVIEW_HELPID =
            "XPathSourceView_source_HelpId"; //$NON-NLS-1$

    // XPath Files Preference page
    public static final String XPATH_PREFWEBX_FILES_HELPID =
            PREFIX + "webx0043"; //$NON-NLS-1$

    // XPath Source Preference page
    public static final String XPATH_PREFWEBX_SOURCE_HELPID =
            PREFIX + "webx0044"; //$NON-NLS-1$

    // XPath Styles Preference page
    public static final String XPATH_PREFWEBX_STYLES_HELPID =
            PREFIX + "webx0045"; //$NON-NLS-1$

    // Content Assist action
    public static final String XPATH_CONTMNU_CONTENTASSIST_HELPID =
            PREFIX + "xpath0000"; //$NON-NLS-1$
}
