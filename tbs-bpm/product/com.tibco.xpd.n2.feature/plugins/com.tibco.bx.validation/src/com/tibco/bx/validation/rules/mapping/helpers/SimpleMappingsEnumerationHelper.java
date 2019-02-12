/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Helper class for BomToBomSimpleMappingsHandler. This class handles concept
 * path (simple java script) mapping issues if source or target is an
 * enumeration
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class SimpleMappingsEnumerationHelper {

    /**
     * Basically checks the mapping compatibility between source enumeration and
     * other target types (target could be enumeration, union or primitive type)
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] handleSource(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();

        Classifier targetType = target.getType();
        String targetTypeName =
                BaseMappingsIssuesHelper.getTypeName(targetType, target);

        /*
         * Enumeration must target exact matching type or text
         */

        /*
         * XPD-1977: XSDUnion - if source is a enumeration mapped to a target
         * union then allow the mappings
         * 
         * eg. mums clothes : jeans sizebystring (which is an enumeration with
         * values small, medium, large) must be allowed mapping to mums
         * jeans_size target which is a union
         */

        if (targetType instanceof Enumeration) {
            if (!sourceType.equals(targetType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        } else if (targetType instanceof PrimitiveType) {
            org.eclipse.uml2.uml.DataType targetDataType =
                    (org.eclipse.uml2.uml.DataType) targetType;

            /*
             * Sid ACE-194 - we don't have XSD derived BOMs any more - removed
             * XSD base rules
             */

            /* source enum and target text is allowed */

            if (PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                    .equalsIgnoreCase(targetTypeName)) {
                return null;
            }

            if (!sourceType.equals(targetType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }

        } else if (!PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                .equals(targetTypeName)) {
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        }
        return null;
    }

    /**
     * Basically checks mappings compatibility between target enumeration/class
     * type and source class type
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] handleTarget(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();

        Classifier targetType = target.getType();

        if (sourceType instanceof Class && targetType instanceof Class) {

            if (JScriptUtils.isDynamicComplexType(sourceType)
                    && JScriptUtils.isDynamicComplexType(targetType)) {
                Class sourceClass = (Class) sourceType;
                Class targetClass = (Class) targetType;

                boolean isLHSSubType =
                        JScriptUtils.isSubType(sourceClass, targetClass);
                if (isLHSSubType) {
                    return null;
                }

                boolean isRHSSubType =
                        JScriptUtils.isSubType(targetClass, sourceClass);
                if (isRHSSubType) {
                    return BaseMappingsIssuesHelper.arr(
                            WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }
            }
        }
        if (!(sourceType.equals(targetType))) {
            /* if the types classes match then it is ok to map */
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        }
        return null;
    }
}
