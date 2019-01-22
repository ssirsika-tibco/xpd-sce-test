/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Class;

public interface IUMLScriptRelevantData extends IScriptRelevantData {
    
    JsClass getJsClass();
    
    void setClassName(String className);
    
    void addClass(Class umlClass);

}
