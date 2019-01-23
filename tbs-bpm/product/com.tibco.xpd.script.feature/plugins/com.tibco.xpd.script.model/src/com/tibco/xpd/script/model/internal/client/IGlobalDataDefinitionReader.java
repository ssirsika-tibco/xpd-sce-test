/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.script.model.internal.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsMethod;

/**
 * Global data Definition Reader that reads from UML model and provides the list of
 * global properties and methods from a given model (for eg: UML Model, BOM)
 * 
 * 
 * @author mtorres
 * 
 */
public interface IGlobalDataDefinitionReader {

    /**
     * This will return all the global methods read from the model
     * 
     * @return
     */
    List<JsMethod> getSupportedGlobalMethods();

    /**
     * This will return all the global properties read from the model
     * 
     * @return
     */
    List<JsAttribute> getSupportedGlobalProperties();

    Image getIcon();

}
