/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import org.eclipse.uml2.uml.Class;


/**
 * This interface provides an extension of the properties
 * available for a JsMethod, JsAttribute, etc...
 * 
 * @author mtorres
 * 
 */
public interface IJsElementExt {
    
    String getBaseDataType();
    
    boolean isStatic();
    
    boolean isReadOnly();
}
