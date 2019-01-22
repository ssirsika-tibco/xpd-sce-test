/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.DefaultJsClass;

/**
 * This interface provides a wrapper for the Class
 * in UML model, and it is used to resolve multiple 
 * multiplicity
 * 
 * @author mtorres
 * 
 */
public class DefaultJsClassMultiple extends DefaultJsClass implements JsClassMultiple{

    public DefaultJsClassMultiple(Class umlClass) {
        super(umlClass);
    }
    
    public DefaultJsClassMultiple(Class umlClass, Class multipleClass) {
        super(umlClass, multipleClass);
    }

}
