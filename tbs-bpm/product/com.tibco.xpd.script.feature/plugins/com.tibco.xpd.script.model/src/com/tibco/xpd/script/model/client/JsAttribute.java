/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.internal.client.ContentAssistElement;


public interface JsAttribute extends ContentAssistElement{

    /**
     * Returns the name of the JsAttribute
     * 
     * @return the name of the JsAttribute
     */
	String getName();
	
	/**
     * Returns the data type of the JsAttribute
     * 
     * @return the dataType of the JsAttribute
     */
	String getDataType();
	
    /**
     * Returns the UML type for the JsAttribute
     * 
     * @return the UML type for the JsAttribute
     */
    Type getUmlType();

	/**
     * Returns the comment of the JsAttribute
     * 
     * @return the comment of the JsAttribute
     */
	String getComment();

	/**
     * Returns if the attribute is multiple
     * 
     * @return true if the attribute has multiplicity
     * zero to more or one to more
     */
	boolean isMultiple();
	
	/**
     * Returns the JsDataType of the attribute for a given expression.
     * JsExpression is a chain of expressions ie:
     * name.getName() would be an expression "name" with the nextExpression being 
     * another expression "getName" of type JsExpressionMethod
     * 
     * Using the previous example the right returning type would be the type of the method getName
     * encapsulated in the class JsDataType, and not the type of the attribute name.
     * 
     * To return the type of the last element of the jsExpression given
     * the list of supported jsClasses is provided, to try and resolve the <code>getDataType</code> of this 
     * attribute with a supported JsClass and delegate the data type retrieval to the 
     * <code>JsClass.getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses)</code>
     * that matches the <code>getDataType</code> of this attribute.
     * 
     * @param jsExpression the chain of jsExpressions
     * @param supportedJsClasses the list of supported JsClasses.
     * 
     * @return JsDataType the data type of the attribute
     */
	JsDataType getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses);

	/**
     * Returns an appropriate script relevant data for the property type.
     * Provided to support dynamic type checking in TIBCO Form product. The default implementation returns null.
     * @return Appropriate script relevant data for the property type.
     * @since 3.x.
     */
	public IScriptRelevantData getScriptRelevantData();
}
