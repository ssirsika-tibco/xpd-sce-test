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
public class XPathProcessToWsdlMappingFactory extends
        ScriptMappingCompositorFactory {

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target parameter.
     * @return The compositor.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory#
     *      getCompositor(com.tibco.xpd.xpdl2.Activity, java.lang.String)
     */
    @Override
    public ScriptMappingCompositor getCompositor(Activity activity,
            String target) {
        return new XPathProcessToWsdlMapping(activity, target);
    }

    /**
     * @param activity
     *            The activity.
     * @param target
     *            The target parameter.
     * @param script
     *            The current script.
     * @return The compositor.
     * @see com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory#
     *      getCompositor(com.tibco.xpd.xpdl2.Activity, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public ScriptMappingCompositor getCompositor(Activity activity,
            String target, String script) {
        return new XPathProcessToWsdlMapping(activity, target, script);
    }

}
