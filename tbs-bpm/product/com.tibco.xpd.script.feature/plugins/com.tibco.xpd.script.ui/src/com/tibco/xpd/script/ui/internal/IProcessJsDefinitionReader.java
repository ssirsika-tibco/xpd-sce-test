/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
/**
 * @author mtorres
 */
public interface IProcessJsDefinitionReader extends JsClassDefinitionReader {
    /**
     * Returns the name of the destination
     * 
     * @return
     */
    String getDestinationName();
    
    /**
     * Sets the name of the destination
     * 
     */
    void setDestinationName(String destinationName);

    /**
     * Returns the script type.
     * 
     * @return
     */
    String getScriptType();
    
    /**
     * Sets the script type.
     * 
     */
    void setScriptType(String scriptType);
}
