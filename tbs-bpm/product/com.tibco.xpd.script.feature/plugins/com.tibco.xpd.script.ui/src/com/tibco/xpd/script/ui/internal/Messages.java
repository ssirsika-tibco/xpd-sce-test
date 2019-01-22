/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.script.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * @author rsomayaj
 *
 */
public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.script.ui.internal.messages"; //$NON-NLS-1$

    public static String BaseScriptSection_EditorUnavailable_shortdesc;

    public static String BaseScriptSection_ScriptDefinedAs_Label;
    
    public static String ScriptGrammarContributionsUtil_Model_Contribution_UnResolved;
    
    public static String BaseScriptSection_InvalidGrammar_shortdesc;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
