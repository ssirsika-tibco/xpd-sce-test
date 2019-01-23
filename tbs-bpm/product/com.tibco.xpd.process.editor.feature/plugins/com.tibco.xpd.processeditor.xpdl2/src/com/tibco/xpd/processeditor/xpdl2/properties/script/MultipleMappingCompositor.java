/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import java.util.Collection;

/**
 * @author nwilson
 */
public interface MultipleMappingCompositor extends ScriptMappingCompositor {
    /**
     * @param path
     *            The path mapped to.
     * @return The script for that mapping.
     */
    String getScript(Object path);

    /**
     * @return A collection of target items.
     */
    Collection<Object> getTargetItems();
    
    /**
     * @param target The target item to get source items for.
     * @return A collection of source items.
     */
    Collection<Object> getSourceItems(Object target);
    
    /**
     * @param target
     *            The target to map to.
     * @param script
     *            The script for the mapping.
     */
    void setScript(Object target, String script);
 
        /**
     * @param target
     *            The target to remove.
     */
    void removeScript(Object target);

}
