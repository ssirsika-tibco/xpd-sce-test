/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.script.model.internal.client.ContentAssistElement;

/**
 * 
 * <p>
 * <i>Created: 18 Oct 2007</i>
 * </p>
 * 
 * @author Miguel Torres
 * 
 */
public interface JsReference extends ContentAssistElement {
    
    String getName();

    String getDataType();
    
    String getComment();
    
    Image getIcon();
    
    JsClass getReferencedJsClass();
    
    boolean isMultiple();
    
    /**
     * Returns the JsDataType of the reference for a given expression.
     * JsExpression is a chain of expressions ie:
     * referenceName.toString() would be a jsExpression "referenceName" with the nextExpression being 
     * another expression "toString" of type JsExpressionMethod
     * 
     * Using the previous example the right returning type would be the type of the method toString
     * encapsulated in the class JsDataType, and not the type of the referenceName.
     * 
     * To return the type of the last element of the jsExpression given
     * the reference can delegate the retrieval of the correct data type to the referenced class.
     * 
     * 
     * @param jsExpression the chain of jsExpressions
     * @param supportedJsClasses the list of supported JsClasses.
     * 
     * @return JsDataType the data type of the attribute
     */
    JsDataType getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses);
}
