/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import java.util.Set;

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptUtil;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Helper class for BomToBomSimpleMappingsHandler and
 * BomToProcessSimpleMappingsHandler. This class handles concept path (simple
 * java script) mapping issues if source or target is object type
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class SimpleMappingsBOMPrimitiveObjectHelper {

    /**
     * Basically checks the mapping compatibility between bom object sub type
     * and other types
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] handleSource(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();

        Classifier targetType = target.getType();

        String sourceObjectSubType =
                ConceptUtil.getObjectSubType(source, sourceType);
        String targetObjectSubType =
                ConceptUtil.getObjectSubType(target, targetType);

        /*
         * If subType is defined then must check that the subType is permitted
         */
        if (sourceObjectSubType.length() > 0) {

            if (targetObjectSubType.length() > 0) {
                /* for anyType target source bom class is allowed */
                if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE
                        .equals(sourceObjectSubType)) {
                    if (target.getType() instanceof org.eclipse.uml2.uml.Class) {
                        if (TransformHelper
                                .isAnonymousComplexType((org.eclipse.uml2.uml.Class) target
                                        .getType())) {
                            return BaseMappingsIssuesHelper
                                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                        }
                    }
                }
            }

            if (JSTypesCompatabilityUtil.getCompatibleBOMObjectSubTypesMap()
                    .get(sourceObjectSubType).contains(targetObjectSubType)) {
                return BaseMappingsIssuesHelper.getWarnings(source, target);
            }

            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);

        } else { /* source has no sub type and target is not a sub type */
            if (sourceType instanceof PrimitiveType) {

                if (targetType instanceof PrimitiveType) {

                    /*
                     * XPD-1977: validate here only if none of the source/target
                     * is a union type.
                     * 
                     * if we dont check for this condition then in
                     * BOMType.getType() and ProcessType.getType() it fails with
                     * IllegalArgumentException. Because it tries to get the
                     * type for union or members of the union which are not a
                     * part of the standard bom or process primitive types and
                     * returns null
                     */

                    org.eclipse.uml2.uml.DataType sourceDataType =
                            (org.eclipse.uml2.uml.DataType) sourceType;
                    boolean isSourceUnion = XSDUtil.isUnion(sourceDataType);

                    org.eclipse.uml2.uml.DataType targetDataType =
                            (org.eclipse.uml2.uml.DataType) targetType;
                    boolean isTargetUnion = XSDUtil.isUnion(targetDataType);

                    if (!isSourceUnion && !isTargetUnion) {
                        return BaseMappingsIssuesHelper
                                .getTargetPrimTypeWarnings(activity,
                                        source,
                                        target);
                    }
                } else if (targetType instanceof Class) {
                    if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY
                            .equals(sourceObjectSubType)
                            || PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE
                                    .equals(sourceObjectSubType)
                            || PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE
                                    .equals(sourceObjectSubType)
                            || PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE
                                    .equals(sourceObjectSubType)) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Basically checks the mapping compatibility between bom object sub type
     * and other types
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] handleTarget(
            Activity activity, ConceptPath source, ConceptPath target,
            String sourceTypeName) {

        Classifier targetType = target.getType();

        /*
         * target sub type is defined then source must be same sub type as
         * target, process data simple type, or bom attribute of base primitive
         * type, BOM attribute of type BOM class, BOM class
         */

        String targetObjectSubType =
                ConceptUtil.getObjectSubType(target, targetType);

        if (targetObjectSubType.length() > 0) {

            /* for anyType target source bom class is allowed */
            if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE
                    .equals(targetObjectSubType)) {
                if (source.getType() instanceof org.eclipse.uml2.uml.Class) {
                    if (TransformHelper
                            .isAnonymousComplexType((org.eclipse.uml2.uml.Class) source
                                    .getType())) {
                        return BaseMappingsIssuesHelper
                                .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                    return null;
                }
            }

            /*
             * xsdAnyAttribute can be mapped only to xsdAnyAttribute, so need
             * not check if it is compatible with any other types
             */
            if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE
                    .equals(targetObjectSubType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }

            /* for anyType target source bom class is allowed */
            if (PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY
                    .equals(targetObjectSubType)) {
                if (source.getType() instanceof org.eclipse.uml2.uml.Class) {
                    return null;
                }
                /*
                 * to check if bom attribute of base primitive type is being
                 * mapped to target any, such mapping is not allowed
                 */
                if (source.getType() instanceof PrimitiveType) {
                    return BaseMappingsIssuesHelper
                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }

                if (null == source.getType()) {
                    /*
                     * null source type implies that it is a process data.
                     * process data to anyType target is not allowed
                     */
                    return BaseMappingsIssuesHelper
                            .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }

            }

            Set<String> compatibleEqualityOperatorSet =
                    JSTypesCompatabilityUtil.getCompatibleBOMRHSSubTypesMap()
                            .get(targetObjectSubType);

            if (null != compatibleEqualityOperatorSet) {

                if (BaseMappingsIssuesHelper
                        .isSourceTypeNameInCompatibleSet(compatibleEqualityOperatorSet,
                                sourceTypeName)) {
                    return BaseMappingsIssuesHelper.getWarnings(source, target);
                }
            }
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
        }
        return null;
    }

}
