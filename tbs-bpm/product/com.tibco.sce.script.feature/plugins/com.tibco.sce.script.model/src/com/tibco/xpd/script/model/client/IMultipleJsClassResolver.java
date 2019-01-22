package com.tibco.xpd.script.model.client;

import org.eclipse.uml2.uml.Class;

public interface IMultipleJsClassResolver extends Cloneable{
    
    String getMultipleJsClassName();
    
    JsClass getMultipleJsClass();
    
    Class getMultipleClass();
    
    void setMultipleClass(Class multipleClass);
    
    Object clone() throws CloneNotSupportedException;

}
