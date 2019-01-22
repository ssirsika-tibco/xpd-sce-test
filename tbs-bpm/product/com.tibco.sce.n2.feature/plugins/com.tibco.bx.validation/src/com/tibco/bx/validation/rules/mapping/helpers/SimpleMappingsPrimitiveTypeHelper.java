/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import java.util.List;
import java.util.Set;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

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
            if (!PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME.equals(sourceTypeName)) {
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

        Classifier targetType = target.getType();
        /*
         * XPD-1977: when both source and target are primitive types then check
         * if they are union type and have matching member types for the union
         */
        org.eclipse.uml2.uml.DataType sourceDataType =
                (org.eclipse.uml2.uml.DataType) sourceType;
        boolean isSourceUnion = false;
        if (null != sourceDataType) {
            isSourceUnion = XSDUtil.isUnion(sourceDataType);
        }

        org.eclipse.uml2.uml.DataType targetDataType =
                (org.eclipse.uml2.uml.DataType) targetType;
        boolean isTargetUnion = false;
        if (null != targetDataType) {
            isTargetUnion = XSDUtil.isUnion(targetDataType);
        }

        if (isSourceUnion && isTargetUnion) {
            /*
             * XPD-1977: check if they are the same union types.
             */
            if (!sourceDataType.equals(targetDataType)) {
                return BaseMappingsIssuesHelper
                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
            }
        } else if (isSourceUnion && !isTargetUnion) {
            /* do not allow the mapping if source is union and target is not */
            return BaseMappingsIssuesHelper
                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);

        } else if (!isSourceUnion && isTargetUnion) {
            /*
             * XPD-1977: if target is union then source must be member type of
             * the target union type members
             */
            List<org.eclipse.uml2.uml.DataType> targetUnionMemberTypes =
                    XSDUtil.getUnionMemberTypes(targetDataType);

            if (null != sourceDataType) {
                /*
                 * XPD-2174: allow mapping when source is a bom attribute of
                 * simple type that matches one of the member types of union.
                 */
                for (org.eclipse.uml2.uml.DataType dataType : targetUnionMemberTypes) {

                    String typeName =
                            BaseMappingsIssuesHelper.getTypeName(dataType,
                                    target);

                    Set<String> compatibleTypes =
                            JSTypesCompatabilityUtil
                                    .getCompatibleBOMtoBOMTypesMap()
                                    .get(sourceTypeName);
                    /*
                     * Compares if both the types are of a base type primitive
                     * type.
                     */
                    if (sourceTypeName != null && typeName != null) {
                        /*
                         * if one of the member types of the union is matching
                         * exit out of the loop
                         */
                        if (sourceTypeName.equals(typeName)) {
                            break;
                        } else if (!compatibleTypes.contains(typeName)) {
                            return BaseMappingsIssuesHelper
                                    .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                        }
                    }
                }
            } else {
                /*
                 * XPD-2174: allow mapping when source is a process relevant
                 * data of simple type that matches one of union member types.
                 */
                String path = source.getPath();
                ProcessRelevantData data =
                        ProcessDataUtil.resolveData(activity, path);

                if (data != null) {
                    DataType srcDataType = data.getDataType();

                    if (srcDataType instanceof BasicType) {
                        BasicType basicType = (BasicType) srcDataType;

                        for (org.eclipse.uml2.uml.DataType dataType : targetUnionMemberTypes) {
                            Boolean doesProcessToBOMTypesMatch =
                                    BaseMappingsIssuesHelper
                                            .doesProcessToBOMTypesMatch(basicType
                                                    .getType(),
                                                    dataType);
                            /*
                             * if one of the member types of the union is
                             * matching exit out of the loop
                             */
                            if (doesProcessToBOMTypesMatch) {
                                break;
                            } else {
                                return BaseMappingsIssuesHelper
                                        .arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                            }
                        }
                    }
                }
            }
        } else { /*
                  * XPD-1977: none of them is a union, then do the regular
                  * validation
                  */

            return BaseMappingsIssuesHelper.getTargetPrimTypeWarnings(activity,
                    source,
                    target);
        }

        return null;
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
        if (PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME.equals(targetType
                .getName()) && sourceType instanceof org.eclipse.uml2.uml.Class) {
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
