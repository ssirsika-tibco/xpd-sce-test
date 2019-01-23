/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;

import com.tibco.xpd.script.model.internal.client.ContentAssistElement;

/**
 * 
 * @author mtorres
 *
 */
public interface JsClass extends ContentAssistElement {
    /**
     * Returns the name of the JsClass
     * 
     * @return the name of the JsClass
     */   
	String getName();
	
	/**
     * Returns the list of methods of the JsClass
     * 
     * @return the list of methods of the JsClass
     */	
	List<JsMethod> getMethodList();
    
    void addMethod(JsMethod jsMethod);
	
   /**
     * Returns the list of methods of the JsClass 
     * for a given name
     * 
     * @return the list of methods of the JsClass
     * for a given name
     */ 
	List<JsMethod> getMethodList(String methodName);

	boolean isParameterCheckingStrict();
	
    /**
     * Returns the comment
     * 
     * @return the comment
     */
	String getComment();
	
    /**
     * Returns the list of attributes 
     * for the given JsClass
     * 
     * @return the list of attributes
     */
	List<JsAttribute> getAttributeList();
    
    void addAttribute(JsAttribute jsAttribute);
	
    /**
     * Returns the list of references 
     * for the given JsClass
     * 
     * @return the list of references
     */
	List<JsReference> getReferenceList();
	
	void addReference(JsReference jsReference);

	void setIcon(Image icon);
	
	/**
     * Returns the JsDataType of the jsClass for a given expression.
     * JsExpression is a chain of expressions ie:
     * Array.toString() would be an expression "Array" with the nextExpression being 
     * another expression "toString" of type JsExpressionMethod
     * 
     * Using the previous example the right returning type would be the type of the method toString
     * encapsulated in the class JsDataType, and not the type of the class Array.
     * 
     * To return the type of the last element of the jsExpression given
     * this class should check whether the nextJsExpression of the given expression matches any of the 
     * <code>getAttributeList()</code> or <code>getReferenceList()</code> and if it matches, it can delegate
     * the return of the type to the 
     * <code>JsAttribute.getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses)</code>
     * in case of an attribute or 
     * <code>JsReference.getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses)</code>
     * in case of a reference 
     * 
     * @param jsExpression the chain of jsExpressions
     * @param supportedJsClasses the list of supported JsClasses.
     * 
     * @return JsDataType the data type of the attribute
     */
	JsDataType getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses);
	
	/**
	 * This will return the uml model of classes
	 * @return
	 */
	public Class getUmlClass();

	/**
	 * Returns an appropriate script relevant data for the type of the JSClass passed as argument.
	 * Provided to support dynamic type checking in TIBCO Form product. The default implementation returns null.
	 * @param variableName Variable name used in the script that refers to this type.
	 * @param isArray Is the type an array.
	 * @return Appropriate script relevant data for the type of the JSClass passed as argument.
	 * @since 3.x.
	 */
	public IScriptRelevantData getScriptRelevantData(String variableName, boolean isArray);
}
