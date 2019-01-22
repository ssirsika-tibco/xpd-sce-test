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
 * SID : This handles composite mappings in SubProcess invocation tasks AND
 * Catch Error Events (both of which can map a process's data to their own
 * process data.
 * 
 * @author aallway
 * @since 3.4.2 (23 Aug 2010)
 */
public class JavaScriptSubProcessToProcessMapping extends
        AbstractSubProcToProcMappingCompositor {

    /**
     * @param activity
     * @param target
     */
    public JavaScriptSubProcessToProcessMapping(Activity activity, String target) {
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
    public JavaScriptSubProcessToProcessMapping(Activity activity,
            String target, String script) {
        super(activity, target, script);
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.tibco.xpd.processeditor.xpdl2.properties.
     * AbstractSubProcToProcMappingCompositor#getScriptGrammar()
     */
    @Override
    protected String getScriptGrammar() {
        return ScriptGrammarFactory.JAVASCRIPT;
    }
}
