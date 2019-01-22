package com.tibco.xpd.script.model.client;

import java.util.List;

import com.tibco.xpd.script.model.internal.client.ContentAssistElement;

public interface JsMethod extends ContentAssistElement {

    /**
     * Returns the name of the JsMethod
     * 
     * @return the name of the JsMethod
     */
    String getName();

    /**
     * Returns the return data type of the JsMethod
     * 
     * @return the return data Type of the JsMethod
     */
    JsMethodParam getReturnType();

    /**
     * Returns the list of input parameters
     * 
     * @return the list of input parameters
     */
    List<JsMethodParam> getParameterType();

    /**
     * Returns the comment of the JsMethod
     * 
     * @return the comment of the JsMethod
     */
    String getComment();

    /**
     * Returns if the return type of the method is multiple
     * 
     * @return true if the return type of the method has multiplicity zero to
     *         more or one to more
     */
    boolean isMultiple();

    /**
     * Returns the JsDataType of the method for a given expression. JsExpression
     * is a chain of expressions ie: getName().toString() would be a
     * JsExpressionMethod "getName" with the nextExpression being another
     * expression "toString" of type JsExpressionMethod
     * 
     * Using the previous example the right returning type would be the type of
     * the method toString encapsulated in the class JsDataType, and not the
     * type of the getName.
     * 
     * To return the type of the last element of the jsExpression given the list
     * of supported jsClasses is provided, to try and resolve the
     * <code>getReturnType</code> of this method with a supported JsClass and
     * delegate the data type retrieval to the
     * <code>JsClass.getDataTypeForJSExpression(JsExpression jsExpression, List<JsClass> supportedJsClasses)</code>
     * that matches the <code>getDataType</code> of this method.
     * 
     * @param jsExpression
     *            the chain of jsExpressions
     * @param supportedJsClasses
     *            the list of supported JsClasses.
     * 
     * @return JsDataType the data type of the attribute
     */
    JsDataType getDataTypeForJSExpression(JsExpression jsExpression,
            List<JsClass> supportedJsClasses);
}
