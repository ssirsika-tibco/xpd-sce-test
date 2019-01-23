/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositor;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptMappingCompositorFactory;
import com.tibco.xpd.xpdl2.Activity;

/**
  * SID : This handles composite mappings in SubProcess invocation tasks AND
 * Catch Error Events (both of which can map a process's data to their own
 * process data.
 * * @author nwilson
 */
public class JavaScriptSubProcessToProcessMappingFactory extends
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
        return new JavaScriptSubProcessToProcessMapping(activity, target);
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
        return new JavaScriptSubProcessToProcessMapping(activity, target, script);
    }

}
