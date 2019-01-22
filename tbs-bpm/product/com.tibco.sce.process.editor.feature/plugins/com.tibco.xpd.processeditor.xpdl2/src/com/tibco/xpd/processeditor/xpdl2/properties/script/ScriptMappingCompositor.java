/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.script;

import com.tibco.xpd.xpdl2.Expression;

/**
 * This is the base interface for providing script support for data mapping. It
 * provides a means to add individual field mappings to a mapping expression
 * and allows generation of an executable script representing the full
 * mapping.
 * @author nwilson
 */
public interface ScriptMappingCompositor {
    
    /**
     * @param source The mapping source item.
     * @param target The mapping target item.
     */
    void addMapping(Object source, Object target);
    
    /**
     * @param source The mapping source item.
     * @param target The mapping target item.
     */
    void removeMapping(Object source, Object target);
    
    /**
     * Remove all mappings from the given parameter.
     * 
     * @param source
     *            The parameter to remove.
     */
    void removeAllMappingsFrom(Object source);

    /**
     * @return true if the script contains any mappings.
     */
    boolean containsMappings();

        /**
     * @return The expression for this script.
     */
    Expression getExpression();
    
    /**
     * @return The target field name.
     */
    String getTarget();
    
    /**
     * @param target
     *            the target to set
     */
    void setTarget(String target);

}
