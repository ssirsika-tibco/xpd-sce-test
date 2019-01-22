/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping compositor for JavaScript grammar mappings from process to
 * sub-process.
 * <p>
 * Note: Main content abstracted to
 * {@link AbstractProcToSubProcMappingCompositor} @ v3.4.2
 * 
 * @author aallway
 * @since 3.4.2 (23 Aug 2010)
 */
public class JavaScriptProcessToSubProcessMapping extends
        AbstractProcToSubProcMappingCompositor {

    /**
     * @param activity
     * @param target
     */
    public JavaScriptProcessToSubProcessMapping(Activity activity, String target) {
        super(activity, target);
    }

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target.
     * @param script
     *            The current script.
     */
    public JavaScriptProcessToSubProcessMapping(Activity activity,
            String target, String script) {
        super(activity, target, script);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractProcToSubProcMappingCompositor#getScriptGrammar()
     */
    @Override
    protected String getScriptGrammar() {
        return ScriptGrammarFactory.JAVASCRIPT;
    }
}
