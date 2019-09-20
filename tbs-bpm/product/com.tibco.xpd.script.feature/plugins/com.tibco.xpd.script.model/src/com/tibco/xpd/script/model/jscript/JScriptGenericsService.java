/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.script.model.jscript;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.uml2.uml.ClassifierTemplateParameter;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.TemplateParameter;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsMethodParam;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.client.JsUmlAttribute;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;

/**
 * Class for handling classes defined with generic typing and parameters.
 * <p>
 * 
 * Generic type parameters can be defined on a UML class using the following structure:
 * 
 * <ul>
 * <li>Redefinable Template Signature
 * <ul>
 * <li>Classifier Template Parameter
 * <ul>
 * <li>Class</li>
 * </ul>
 * </li>
 * </ul>
 * </li>
 * </ul>
 * 
 * The name of the Class should be set to the generic type name (usually 'T' for a single type). The Class can then be
 * used as the type for function parameters and return values within the parent Class.
 *
 * @author nwilson
 * @since 27 Aug 2019
 */
public class JScriptGenericsService {

    /**
     * Checks if a JsMethodParam is of generic type.
     * 
     * @param param
     *            The param to check.
     * @return true if the type is generic.
     */
    public boolean isGeneric(JsMethodParam param) {
        boolean isGeneric = false;
        Parameter umlParam = param.getUMLParameter();
        if (umlParam != null) {
            Type type = umlParam.getType();
            if (type != null) {
                TemplateParameter template = type.getOwningTemplateParameter();
                isGeneric = template instanceof ClassifierTemplateParameter;
            }
        }
        return isGeneric;
    }

    /**
     * Creates a map for a function of generic type names to actual types.
     * 
     * @param genericContext
     *            The context to extract the actual type from.
     * @param jsMethodParam
     *            The method parameter.
     * @return A map of generic type names to types.
     */
    public Map<String, Type> createTypeMap(IScriptRelevantData genericContext, JsMethodParam jsMethodParam) {
        Map<String, Type> typeMap = new HashMap<>();
        Parameter umlParam = jsMethodParam.getUMLParameter();
        if (umlParam != null) {
            Type type = umlParam.getType();
            if (type != null) {
                TemplateParameter template = type.getOwningTemplateParameter();
                if (template instanceof ClassifierTemplateParameter) {
                    String key = type.getName();
                    Type value = getType(genericContext);
                    if (value != null) {
                        typeMap.put(key, value);
                    }
                }
            }
        }
        return typeMap;
    }

    /**
     * Extracts an actual type from a generic context.
     * 
     * @param genericContext
     *            The generic context.
     * @return The actual type used.
     */
    private Type getType(IScriptRelevantData genericContext) {
        Type type = null;
        if (genericContext instanceof ITypeResolution) {
            ITypeResolution typeResolution = (ITypeResolution) genericContext;
            Object ext = typeResolution.getExtendedInfo();
            if (ext instanceof JsUmlAttribute) {
                JsUmlAttribute att = (JsUmlAttribute) ext;
                type = att.getUmlType();
            } else if (ext instanceof JsReference) {
                JsReference ref = (JsReference) ext;
                JsClass referencedJsClass = ref.getReferencedJsClass();
                if (referencedJsClass != null) {
                    type = referencedJsClass.getUmlClass();
                }
            }
        }
        return type;
    }
}
