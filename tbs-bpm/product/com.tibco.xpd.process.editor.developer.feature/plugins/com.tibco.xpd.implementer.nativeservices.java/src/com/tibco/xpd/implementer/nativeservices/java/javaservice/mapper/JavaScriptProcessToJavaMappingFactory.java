/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
 * @author nwilson
 */
public class JavaScriptProcessToJavaMappingFactory extends
        ScriptMappingCompositorFactory {

    /**
     * 
     */
    public JavaScriptProcessToJavaMappingFactory() {
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
        return new JavaScriptProcessToJavaMapping(activity, target);
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
        return new JavaScriptProcessToJavaMapping(activity, target, script);
    }

}
