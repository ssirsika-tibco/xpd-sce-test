/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.implementer.script.BaseTypeUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;

/**
 * @author mtorres
 */
public class BpmnTypeUtil extends ServicesTypeUtil {

    /**
     * @param pathType
     *            The path type.
     * @param dataType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean checkOtherAcceptableMatches(
            XSDTypeDefinition pathType, DataType dataType) {
        return checkOtherAcceptableMatches(pathType,
                BasicTypeConverterFactory.INSTANCE.getBasicType(dataType));
    }

    public static boolean checkOtherAcceptableMatches(
            XSDTypeDefinition pathType, BasicType basicType) {
        boolean match =
                BaseTypeUtil.checkOtherAcceptableMatches(pathType, basicType);
        if (!match && pathType != null) {
            if (basicType != null
                    && pathType instanceof XSDSimpleTypeDefinition) {
                XSDSimpleTypeDefinition simple =
                        (XSDSimpleTypeDefinition) pathType;

                XSDTypeDefinition baseTypeDef =
                        simple.getPrimitiveTypeDefinition();

                BasicTypeType basicTypeType = basicType.getType();
                if (BasicTypeType.BOOLEAN_LITERAL.equals(basicTypeType)
                        && xsdBooleans.contains(baseTypeDef.getName())) {
                    match = true;
                }
            }
        }
        return match;
    }
}
