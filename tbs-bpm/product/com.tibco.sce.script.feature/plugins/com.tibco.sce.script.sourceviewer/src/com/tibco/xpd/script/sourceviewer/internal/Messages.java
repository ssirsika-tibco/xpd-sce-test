/**********************************************************************
 * Copyright (c) 2005 2006 IBM Corporation and others. All rights reserved.   This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package com.tibco.xpd.script.sourceviewer.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

/**
 * Strings used by Javascript Common UI
 * 
 * @plannedfor 1.0
 */
public class Messages extends NLS {
    
	private static final String BUNDLE_NAME = "com.tibco.xpd.script.sourceviewer.internal.messages";//$NON-NLS-1$

	public static String Default_Code_UI_;

	public static String DefaultContentAssistProcessor_DatePart;

	public static String DefaultContentAssistProcessor_LocalFunction;

	public static String DefaultContentAssistProcessor_ProcessData;

	public static String DefaultContentAssistProcessor_TimePart;

	public static String JScriptSourceViewer_ContentAssistAction;

	public static String JScriptSourceViewer_CopyAction;

	public static String JScriptSourceViewer_CutAction;

	public static String JScriptSourceViewer_DeleteAction;

	public static String JScriptSourceViewer_PasteAction;

	public static String JScriptSourceViewer_RedoAction;

	public static String JScriptSourceViewer_SelectAllAction;

	public static String JScriptSourceViewer_UndoAction;
	public static String Keywords_UI_;
	public static String Keywords_UI_Entities;
    public static String Keywords_UI_Attributes;
    public static String Keywords_UI_OperatorCombinator;
	public static String Literal_Strings_UI_;
	public static String Comments_UI_;
	public static String Unfinished_Strings_and_Comments_UI_;
	public static String internal_error_1;
	public static String internal_error_2;
	public static String Content_assist_UI_;
	public static String Automatically_make_suggest_UI_;
	public static String Prompt_when_these_characte_UI_;
	public static String Formatting_UI_;
	public static String Indent_using_tabs;
	public static String Indent_using_spaces;
	public static String Indentation_size;  


	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages() {
		// cannot create new instance
	}
    private static ResourceBundle fResourceBundle;
    public static ResourceBundle getResourceBundle() {
        try {
            if (fResourceBundle == null)
                fResourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        } catch (MissingResourceException x) {
            fResourceBundle = null;
        }
        return fResourceBundle;
    }
}