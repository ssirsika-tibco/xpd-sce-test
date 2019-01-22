/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;

/**
 * @author nwilson
 */
public interface SingleMappingCompositor extends ScriptMappingCompositor {
    /**
     * @return The expression script.
     */
    String getScript();

    /**
     * @param isInput true if input parameters, false if output.
     * @return A collection of used source items.
     */
    Collection<Object> getSourceItems(boolean isInput);
    
    /**
     * @return A collection of used source item names.
     */
    Collection<String> getSourceItemNames();
    
    /**
     * @param script
     *            The new script.
     */
    void setScript(String script);

}
