package com.tibco.xpd.script.transform.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME =
            "com.tibco.xpd.script.transform.internal.messages"; //$NON-NLS-1$

    public static String ActivityScriptTransformContentProvider_NewScriptInitialName;

    public static String ScriptMappingTransformSection_MapProcessToScriptTitle;

    public static String TransformScriptEditorSection_SetScriptTaskGrammar;

    public static String TransformEditorSection_GoToTransformMappings;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
