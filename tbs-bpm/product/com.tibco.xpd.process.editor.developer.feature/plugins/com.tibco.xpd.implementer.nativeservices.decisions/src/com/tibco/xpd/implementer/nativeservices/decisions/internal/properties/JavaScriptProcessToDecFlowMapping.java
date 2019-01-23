/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import com.tibco.xpd.processeditor.xpdl2.properties.AbstractProcToSubProcMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping compositor for JavaScript grammar mappings from process to
 * dec-flow.
 * <p>
 * Note: Main content abstracted to
 * {@link AbstractProcToSubProcMappingCompositor} @ v3.4.2
 * 
 * @author mtorres
 * @since 3.6
 */
public class JavaScriptProcessToDecFlowMapping extends
        AbstractProcToSubProcMappingCompositor {

    /**
     * @param activity
     * @param target
     */
    public JavaScriptProcessToDecFlowMapping(Activity activity, String target) {
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
    public JavaScriptProcessToDecFlowMapping(Activity activity,
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
