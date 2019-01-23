/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author nwilson
 */
public class JavaScriptProcessToWsdlMappingFactory extends
        ScriptMappingCompositorFactory {

    /**
     * 
     */
    public JavaScriptProcessToWsdlMappingFactory() {
    }

    /**
     * @param activity
     *            The activity containing the mapping.
     * @param target
     *            The target field name.
     * @return The ScriptMappingCompositor.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory#
     *      getCompositor(com.tibco.xpd.xpdl2.Activity, java.lang.String)
     */
    @Override
    public ScriptMappingCompositor getCompositor(Activity activity,
            String target) {
        return new JavaScriptProcessToWsdlMapping(activity, target);
    }

    /**
     * @param activity
     *            The activity containing the mapping.
     * @param target
     *            The target field name.
     * @param script
     *            The existing script.
     * @return The ScriptMappingCompositor.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory#
     *      getCompositor(com.tibco.xpd.xpdl2.Activity, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public ScriptMappingCompositor getCompositor(Activity activity,
            String target, String script) {
        return new JavaScriptProcessToWsdlMapping(activity, target, script);
    }

}
