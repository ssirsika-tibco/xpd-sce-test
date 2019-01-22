/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.rules.datamapper;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.helpers.BaseScriptToConceptPathMappingsHelper;
import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.bom.modeler.custom.enumlitext.util.EnumLitValueUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.validation.bpmn.rules.baserules.JavaScriptTypeCompatibilityResult;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Utility for checking type compatibility between Enumerations and primitive
 * types. Note that this support non-text enumerations so should not be used for
 * all situations. This is initially intended for use with Data Mapper only.
 * 
 * @author nwilson
 * @since 19 Jun 2015
 */
public class EnumerationTypeCompatibilityUtil {
    /**
     * Check compatibility of the to process data concept paths.
     * 
     * @param srcData
     * @param targetData
     * @param directionType
     * 
     * @return JavaScriptTypeCompatibilityResult
     */
    public static JavaScriptTypeCompatibilityResult checkTypeCompatibility(
            ConceptPath srcData, ConceptPath targetData) {

        Object sourceType = getType(srcData);
        Object targetType = getType(targetData);
        return checkCompatibility(sourceType, targetType);
    }

    /**
     * Check compatibility of the source and target data type.
     * 
     * @param srcDataType
     * @param targetDataType
     * @param directionType
     * 
     * @return JavaScriptTypeCompatibilityResult
     */
    public static JavaScriptTypeCompatibilityResult checkTypeCompatibility(
            IScriptRelevantData srcDataType, ConceptPath targetData) {

        Classifier sourceType =
                BaseScriptToConceptPathMappingsHelper
                        .getSourceScriptTypeClassifier(srcDataType);
        Object targetType = getType(targetData);
        return checkCompatibility(sourceType, targetType);
    }

    /**
     * Could return Classifier or BasicType.
     * 
     * @param targetData
     * @return
     */
    private static Object getType(ConceptPath targetData) {
        return BasicTypeConverterFactory.INSTANCE.getBaseType(targetData
                .getItem(), true);
    }

    /**
     * @param sourceType
     * @param targetType
     * @return
     */
    private static JavaScriptTypeCompatibilityResult checkCompatibility(
            Object sourceType, Object targetType) {
        JavaScriptTypeCompatibilityResult result =
                JavaScriptTypeCompatibilityResult.UNHANDLED_SCENARIO;
        if (sourceType instanceof Enumeration
                || targetType instanceof Enumeration) {
            BasicTypeType baseSourceType = getBaseType(sourceType);
            BasicTypeType baseTargetType = getBaseType(targetType);
            if (baseSourceType != null && baseSourceType.equals(baseTargetType)) {
                result =
                        JavaScriptTypeCompatibilityResult.HANDLED_SCENARIO_CHECK_SUCCEEDED;
            }
        }
        return result;
    }

    /**
     * Converts Enumeration, primitive and BOM primitive types to the equivalent
     * base BOM Primitive Type.
     * 
     * @param type
     *            The type to convert.
     * @return the base BOM primitive type.
     */
    private static BasicTypeType getBaseType(Object type) {
        BasicTypeType base = null;
        BasicType basic = null;
        if (type instanceof Enumeration) {
            Enumeration e = (Enumeration) type;
            PrimitiveType pt = EnumLitValueUtil.getBaseType(e);
            basic = BasicTypeConverterFactory.INSTANCE.getBasicType(pt);
        } else if (type instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) type;
            basic = BasicTypeConverterFactory.INSTANCE.getBasicType(pt);
        } else if (type instanceof ProcessRelevantData) {
            ProcessRelevantData prd = (ProcessRelevantData) type;
            DataType dt = prd.getDataType();
            basic = BasicTypeConverterFactory.INSTANCE.getBasicType(dt);
        } else if (type instanceof BasicType) {
            basic = (BasicType) type;
        }
        if (basic != null) {
            base = basic.getType();
        }
        return base;
    }
}
