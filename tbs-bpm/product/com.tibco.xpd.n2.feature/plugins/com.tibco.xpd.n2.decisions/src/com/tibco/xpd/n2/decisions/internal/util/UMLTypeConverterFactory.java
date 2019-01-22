/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.n2.decisions.internal.util;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Simple factory for getting the UML Type data type from a variety of
 * different objects BasicType
 * 
 * @author mtorres
 * @since 3.6
 */
public class UMLTypeConverterFactory {

    public static final UMLTypeConverterFactory INSTANCE =
            new UMLTypeConverterFactory();

    /**
     * Given a variety of possible data objects, return the equivalent UML type.
     * 
     * @param data
     *            Data object to get equivalent basic type for.
     * 
     * @return Type or <code>null</code> if the data object cannot be
     *         resolved to a uml type.
     */
    public Type getBasicType(Object data) {
        Object retBaseType = getBaseType(data, true);
        if (retBaseType instanceof Type) {
            return (Type) retBaseType;
        }

        return null;
    }

    /**
     * 
     * @param data
     * 
     * @return The data type of the data object.
     */
    public Object getBaseType(Object data,
            boolean convertPrimitiveTypeToBasicType) {
        Object retBaseType = null;
        if (data instanceof BasicType) {
            BasicType basicType = (BasicType) data;
            if (basicType != null && basicType.getType() != null) {
                EditingDomain editingDomain = WorkingCopyUtil.getEditingDomain(basicType);
                if (editingDomain != null) {
                    String primitiveName = null;
                    switch (basicType.getType().getValue()) {
                    case BasicTypeType.STRING:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
                        break;
                    case BasicTypeType.BOOLEAN:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME;
                        break;
                    case BasicTypeType.DATE:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME;
                        break;
                    case BasicTypeType.DATETIME:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME;
                        break;
                    case BasicTypeType.FLOAT:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME;
                        break;
                    case BasicTypeType.INTEGER:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME;
                        break;
                    case BasicTypeType.PERFORMER:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME;
                        break;
                    case BasicTypeType.TIME:
                        primitiveName = PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME;
                        break;
                    default:
                        break;
                    }
                    if(primitiveName != null){
                        retBaseType = PrimitivesUtil.getStandardPrimitiveTypeByName(editingDomain.getResourceSet(), primitiveName);
                    }
                }
            }
        }
        return retBaseType;
    }

}
