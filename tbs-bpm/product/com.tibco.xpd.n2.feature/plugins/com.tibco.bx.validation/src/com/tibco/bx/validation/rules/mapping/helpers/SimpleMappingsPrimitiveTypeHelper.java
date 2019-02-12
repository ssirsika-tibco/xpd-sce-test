/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Helper class for BomToBomSimpleMappingsHandler and
 * ProcessToBomSimpleMappingsHandler. This class handles concept path mapping
 * issues if source or target are primitive types
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class SimpleMappingsPrimitiveTypeHelper {

    /**
     * 
     * Basically checks mappings compatibility between source primitive type and
     * target primitive type/enumeration/class
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] handleSource(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();
        String sourceTypeName =
                BaseMappingsIssuesHelper.getTypeName(sourceType, source);

        Classifier targetType = target.getType();

        /*
         * Compare with target if target is a Primitive Type and when it is not.
         */
        if (targetType instanceof PrimitiveType) {
            return handlePrimitiveTypeTarget(activity, source, target);

        } else if (targetType instanceof Enumeration) {
            /* If target is enumeration then source must be string. */
            if (!PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME
                    .equals(sourceTypeName)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
            return BaseMappingsIssuesHelper.getWarnings(source, target);

        } else if (targetType instanceof org.eclipse.uml2.uml.Class) {
            /*
             * If source is Primitive and target is Class then types dont match
             */
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        }
        return null;
    }

    /**
     * compare source primitive type with target if target is a primitive type
     * or a union
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] handlePrimitiveTypeTarget(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();
        String sourceTypeName =
                BaseMappingsIssuesHelper.getTypeName(sourceType, source);

        if (null == sourceType && null == sourceTypeName) {
            sourceTypeName =
                    BaseMappingsIssuesHelper.getTypeStr(activity, source);
        }

        /*
         * Sid ACE-194 - we don't have XSD derived BOMs any more - removed XSD
         * base rules
         */

        /*
         * XPD-1977: none of them is a union, then do the regular validation
         */

        return BaseMappingsIssuesHelper
                .getTargetPrimTypeWarnings(activity, source, target);

    }

    /**
     * 
     * checks for mappings between target primitive types and source type class
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

        /*
         * XPD-1491: If target type is Object primitive type and the source is
         * any BOM class
         */
        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME
                .equals(targetType.getName())
                && sourceType instanceof org.eclipse.uml2.uml.Class) {
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        }

        /*
         * If source is not Primitive and target is primitive - then it is an
         * invalid mapping.
         */
        return BaseMappingsIssuesHelper
                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);

    }

}
