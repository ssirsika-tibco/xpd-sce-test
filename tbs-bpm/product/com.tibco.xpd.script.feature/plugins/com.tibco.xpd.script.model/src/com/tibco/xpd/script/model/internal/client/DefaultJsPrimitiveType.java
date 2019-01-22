/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.model.internal.client;

import org.eclipse.uml2.uml.PrimitiveType;
/**
 * This class provides a wrapper for the Primitive Type classifier
 * in UML model
 * 
 * @author mtorres
 * 
 */
public class DefaultJsPrimitiveType extends AbstractDefaultJsDataType implements JsPrimitiveType{

    public DefaultJsPrimitiveType(PrimitiveType umlPrimitiveType) {
        super(umlPrimitiveType);
    }    
}
