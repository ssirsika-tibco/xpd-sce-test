/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.decisions.internal.properties;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author mtorres
 */
public class JavaScriptProcessToDecFlowMappingFactory extends
        ScriptMappingCompositorFactory {

    /**
     * @param activity
     * @param target
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory#getCompositor(com.tibco.xpd.xpdl2.Activity, java.lang.String)
     */
    @Override
    public ScriptMappingCompositor getCompositor(Activity activity,
            String target) {
        return new JavaScriptProcessToDecFlowMapping(activity, target);
    }

    /**
     * @param activity
     * @param target
     * @param script
     * @return
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory#getCompositor(com.tibco.xpd.xpdl2.Activity, java.lang.String, java.lang.String)
     */
    @Override
    public ScriptMappingCompositor getCompositor(Activity activity,
            String target, String script) {
        return new JavaScriptProcessToDecFlowMapping(activity, target, script);
    }

}
