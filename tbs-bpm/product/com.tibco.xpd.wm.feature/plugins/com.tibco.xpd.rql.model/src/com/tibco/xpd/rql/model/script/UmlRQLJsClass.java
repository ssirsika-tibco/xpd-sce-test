/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.model.script;

import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.client.DefaultJsClass;
/**
 * 
 * 
 * @author Miguel Torres
 * 
 */
public class UmlRQLJsClass extends DefaultJsClass {
    
    public UmlRQLJsClass(Class umlClass) {
        super(umlClass);
    }

    public UmlRQLJsClass(Class umlClass, Class multipleClass) {
        super(umlClass, multipleClass);
    }
    
    @Override
    public String getContentAssistString() {
        return super.getContentAssistString() + "()";//$NON-NLS-1$
    }
    
}
