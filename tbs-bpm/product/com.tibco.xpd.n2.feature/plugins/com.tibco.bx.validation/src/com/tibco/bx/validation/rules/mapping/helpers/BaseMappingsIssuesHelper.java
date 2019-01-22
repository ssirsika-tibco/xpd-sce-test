/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.bx.validation.rules.mapping.helpers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.bx.validation.rules.mapping.Entity;
import com.tibco.bx.validation.rules.mapping.WebServiceJavaScriptMappingIssue;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessDataUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.n2.cds.script.N2JScriptDataTypeMapper;
import com.tibco.xpd.n2.cds.utils.JSTypesCompatabilityUtil;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Helper class for data to concept path and script to concept path mappings
 * 
 * @author bharge
 * @since 9 Nov 2011
 */
public class BaseMappingsIssuesHelper {

    /**
     * 
     * @param warnings
     * @return array of {@link WebServiceJavaScriptMappingIssue}
     */
    public static WebServiceJavaScriptMappingIssue[] arr(
            WebServiceJavaScriptMappingIssue... warnings) {
        return warnings;
    }

    /**
     * @param source
     * @param target
     * @return warnings for mappings
     */
    public static WebServiceJavaScriptMappingIssue[] getWarnings(
            ConceptPath source, ConceptPath target) {
        Entity se = new Entity(source);
        Entity te = new Entity(target);
        WebServiceJavaScriptMappingIssue[] warnings = se.validate(te);
        return warnings;
    }

    /**
     * Converts specific types that are the same to generic types ie: String and
     * Performer to String ...
     * 
     * @param specificType
     * @return
     */
    public static String convertSpecificToGenericType(String specificType) {

        String genericType = specificType;
        Map<String, String> specificToGenericTypeConversionMap =
                N2JScriptDataTypeMapper.getInstance()
                        .getSpecificToGenericTypeConversionMap();

        if (specificToGenericTypeConversionMap != null) {

            String genericCandidate =
                    specificToGenericTypeConversionMap.get(specificType);
            if (null == genericCandidate) {

                Iterator<String> iterator =
                        specificToGenericTypeConversionMap.keySet().iterator();
                while (iterator.hasNext()) {

                    String next = iterator.next();
                    if (next.equalsIgnoreCase(specificType)) {
                        genericCandidate =
                                specificToGenericTypeConversionMap.get(next);
                        break;
                    }
                }
            }
            if (genericCandidate != null) {
                genericType = genericCandidate;
            }
        }
        return genericType;
    }

    /**
     * get the type name for the given classifier and concept path
     * 
     * @param type
     * @param target
     * @return the base primitive type name or type name for UML primitive type <br>
     *         Returns the sub types of {@link BigDecimal} or {@link BigInteger}
     *         for Integer or Decimal types
     */
    public static String getTypeName(Classifier type, ConceptPath target) {

        String typeName = null;
        Classifier basePrimitiveType = null;
        String intOrDeciSubType = null;

        if (type instanceof PrimitiveType) {
            /* Extract the base primitive type */
            basePrimitiveType =
                    PrimitivesUtil.getBasePrimitiveType((PrimitiveType) type);
        }

        if (basePrimitiveType != null) {
            typeName = basePrimitiveType.getName();
        } else {
            if (null != type) {
                typeName = type.getName();
            }
        }

        if (target.getItem() instanceof Property) {
            if (JsConsts.INTEGER.equals(typeName)) {

                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) type,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE,
                                        (Property) target.getItem());

                if (facetPropertyValue instanceof EnumerationLiteral
                        && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                                .equals((((EnumerationLiteral) facetPropertyValue)
                                        .getName()))) {
                    intOrDeciSubType = JsConsts.BIGINTEGER;
                } else {
                    intOrDeciSubType = JsConsts.INTEGER;
                }
            } else if (JsConsts.DECIMAL.equals(typeName)) {

                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) type,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE,
                                        (Property) target.getItem());

                if (facetPropertyValue instanceof EnumerationLiteral
                        && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                .equals((((EnumerationLiteral) facetPropertyValue)
                                        .getName()))) {
                    intOrDeciSubType = JsConsts.BIGDECIMAL;
                } else {
                    intOrDeciSubType = JsConsts.DECIMAL;
                }
            }
        } else {
            if (JsConsts.INTEGER.equals(typeName)) {

                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) type,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);

                if (facetPropertyValue instanceof EnumerationLiteral
                        && PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH
                                .equals((((EnumerationLiteral) facetPropertyValue)
                                        .getName()))) {
                    intOrDeciSubType = JsConsts.BIGINTEGER;
                } else {
                    intOrDeciSubType = JsConsts.INTEGER;
                }
            } else if (JsConsts.DECIMAL.equals(typeName)) {

                Object facetPropertyValue =
                        PrimitivesUtil
                                .getFacetPropertyValue((PrimitiveType) type,
                                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

                if (facetPropertyValue instanceof EnumerationLiteral
                        && PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT
                                .equals((((EnumerationLiteral) facetPropertyValue)
                                        .getName()))) {
                    intOrDeciSubType = JsConsts.BIGDECIMAL;
                } else {
                    intOrDeciSubType = JsConsts.DECIMAL;
                }
            }

        }
        if (null != intOrDeciSubType && !intOrDeciSubType.equals(typeName)) {
            typeName = intOrDeciSubType;
        }

        return typeName;
    }

    /***
     * 
     * get the type name for the given activity (resolves the process relevant
     * data for this activity) and concept path
     * 
     * @param activity
     * @param target
     * @return the type name for process relevant data basic types
     */
    public static String getTypeStr(Activity activity, ConceptPath target) {

        String typeStr = null;

        ProcessRelevantData data = null;

        /*
         * Sid XPD-8114 - We do extensively use dummy fields and parameters to
         * represent other objects in mapper content trees (like
         * Process->Priority in process data mapper) - these won't be resolvable
         * as paths, but the ConceptPath has thre Field/Parameter in anyway so
         * we can just use that to extract type.
         */
        if (target.getItem() instanceof ProcessRelevantData) {
            data = (ProcessRelevantData) target.getItem();
        } else {
            String path = target.getPath();
            data = ProcessDataUtil.resolveData(activity, path);
        }

        if (null != data) {
            DataType dataType = data.getDataType();

            if (dataType instanceof BasicType) {
                BasicType basicType = (BasicType) dataType;

                typeStr = basicType.getType().getName();
            }
        }

        return typeStr;
    }

    /**
     * get the basic type for the given activity and concept path (resolves the
     * process relevant data for the given concept path and activity)
     * 
     * @param activity
     * @param target
     * @return the {@link BasicType} for a process relevant data
     */
    public static BasicType getBasicType(Activity activity, ConceptPath target) {

        ProcessRelevantData data = null;

        /*
         * Sid XPD-8114 - We do extensively use dummy fields and parameters to
         * represent other objects in mapper content trees (like
         * Process->Priority in process data mapper) - these won't be resolvable
         * as paths, but the ConceptPath has thre Field/Parameter in anyway so
         * we can just use that to extract type.
         */
        if (target.getItem() instanceof ProcessRelevantData) {
            data = (ProcessRelevantData) target.getItem();
        } else {
            String path = target.getPath();
            data = ProcessDataUtil.resolveData(activity, path);
        }

        if (null != data) {
            DataType dataType = data.getDataType();

            if (dataType instanceof BasicType) {
                return (BasicType) dataType;
            }
        }
        return null;
    }

    /**
     * 
     * checks if the given type name is available in the given set
     * 
     * @param compatibleEqualityOperatorSet
     * @param sourceTypeName
     * @return <code>true</code> if the type name is present in the compatible
     *         set (checks for both specific and generic type name) <br>
     *         <code>false</code> if not
     */
    public static boolean isSourceTypeNameInCompatibleSet(
            Set<String> compatibleEqualityOperatorSet, String sourceTypeName) {
        boolean found = false;
        if (compatibleEqualityOperatorSet.contains(sourceTypeName)
                || compatibleEqualityOperatorSet
                        .contains(convertSpecificToGenericType(sourceTypeName))) {
            found = true;
        }
        if (!found) {
            Iterator<String> compatibleIter =
                    compatibleEqualityOperatorSet.iterator();
            while (compatibleIter.hasNext()) {
                String next = compatibleIter.next();
                if (next.equalsIgnoreCase(sourceTypeName)) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    /**
     * 
     * @param sourceTypeStrName
     * @param targetTypeStrName
     * @return <code>true</code> if both type names are equal (checks for both
     *         generic and specific types) <br>
     *         <code>false</code> if not
     */
    public static boolean isSourceNameTargetNameEquals(
            String sourceTypeStrName, String targetTypeStrName) {
        if (sourceTypeStrName == null && targetTypeStrName == null) {
            return true;
        }
        if (sourceTypeStrName.equalsIgnoreCase(targetTypeStrName)) {
            return true;
        }
        if (sourceTypeStrName != null) {
            String genereicType =
                    convertSpecificToGenericType(sourceTypeStrName);
            if (genereicType != null
                    && genereicType.equalsIgnoreCase(targetTypeStrName)) {
                return true;
            }
        }
        if (targetTypeStrName != null) {
            String genericType =
                    convertSpecificToGenericType(targetTypeStrName);
            if (genericType != null
                    && genericType.equalsIgnoreCase(sourceTypeStrName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * Basically checks if the source is compatible with the target when both
     * are primitive types but none of them is a union
     * 
     * @param activity
     * @param source
     * @param target
     * @return array of {@link WebServiceJavaScriptMappingIssue} having errors
     *         if the source is not present in the target's compatible
     *         assignments map <br>
     *         array of {@link WebServiceJavaScriptMappingIssue} having warnings
     *         (if any) if the source is present in the target's assignments map
     */
    @SuppressWarnings("restriction")
    public static WebServiceJavaScriptMappingIssue[] getTargetPrimTypeWarnings(
            Activity activity, ConceptPath source, ConceptPath target) {

        Classifier sourceType = source.getType();
        String sourceTypeName =
                BaseMappingsIssuesHelper.getTypeName(sourceType, source);

        if (null == sourceType && null == sourceTypeName) {
            sourceTypeName =
                    BaseMappingsIssuesHelper.getTypeStr(activity, source);
        }

        Classifier targetType = target.getType();
        String targetTypeName =
                BaseMappingsIssuesHelper.getTypeName(targetType, target);

        if (targetType instanceof PrimitiveType) {

            Map<String, Set<String>> compatibleAssignmentTypesMap =
                    N2JScriptDataTypeMapper.getInstance()
                            .getCompatibleAssignmentTypesMap();

            compatibleAssignmentTypesMap =
                    N2JScriptDataTypeMapper
                            .getInstance()
                            .convertSpecificMapToGeneric(compatibleAssignmentTypesMap);

            if (compatibleAssignmentTypesMap != null) {
                Set<String> compatibleEqualityOperatorSet =
                        compatibleAssignmentTypesMap.get(targetTypeName);

                if (null != compatibleEqualityOperatorSet) {

                    if (isSourceTypeNameInCompatibleSet(compatibleEqualityOperatorSet,
                            sourceTypeName)) {
                        return null;
                    }
                }

                String genericType = sourceTypeName;

                Map<String, String> specificToGenericTypeConversionMap =
                        N2JScriptDataTypeMapper.getInstance()
                                .getSpecificToGenericTypeConversionMap();

                if (null != specificToGenericTypeConversionMap) {

                    Iterator<String> iterator =
                            specificToGenericTypeConversionMap.keySet()
                                    .iterator();
                    while (iterator.hasNext()) {
                        String next = iterator.next();
                        if (next.equalsIgnoreCase(sourceTypeName)) {
                            String genericCandidate =
                                    specificToGenericTypeConversionMap
                                            .get(next);
                            if (genericCandidate != null) {
                                genericType = genericCandidate;
                            }
                            break;
                        }
                    }
                }

                if (null != genericType && genericType.equals(targetTypeName)) {
                    return null;
                }
                if (compatibleEqualityOperatorSet != null
                        && !compatibleEqualityOperatorSet.contains(genericType)) {
                    return arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                }
                if (null != sourceTypeName && null != targetTypeName) {
                    if (!sourceTypeName.equals(targetTypeName)) {
                        return arr(WebServiceJavaScriptMappingIssue.TYPES_DONT_MATCH);
                    }
                }
            }

            Set<String> compatibleTypes =
                    JSTypesCompatabilityUtil.getCompatibleBOMtoBOMTypesMap()
                            .get(targetTypeName);
            /*
             * Compares if both the types are of a base type primitive type.
             */
            if (sourceTypeName != null && targetTypeName != null) {

                if (null != compatibleTypes
                        && compatibleTypes.contains(sourceTypeName)) {
                    return BaseMappingsIssuesHelper.getWarnings(source, target);
                } else if (sourceTypeName.equals(targetTypeName)) {
                    return BaseMappingsIssuesHelper.getWarnings(source, target);
                }

            }
        }
        return null;
    }

    /**
     * Adds warnings for mappings from BOM to process
     * 
     * @param source
     * @param target
     * @return WebServiceJavaScriptMappingIssue[]
     */
    public static WebServiceJavaScriptMappingIssue[] getBOMToProcessNecessaryWarnings(
            ConceptPath source, ConceptPath target) {
        // source - BOM
        // target - process

        return BaseMappingsIssuesHelper.getWarnings(source, target);

    }

    /**
     * Adds warnings for mappings from process to BOM
     * 
     * @param source
     * @param target
     * @return WebServiceJavaScriptMappingIssue[]
     */
    public static WebServiceJavaScriptMappingIssue[] getProcessToBOMNecessaryWarnings(
            ConceptPath source, ConceptPath target) {
        // source - process
        // target - BOM
        return BaseMappingsIssuesHelper.getWarnings(source, target);
    }

    /**
     * Comparing Process basic types to BOM Primitive types
     * 
     * @param dataType
     * @param type
     * @return <code>true</code> if the types match
     */
    public static Boolean doesProcessToBOMTypesMatch(BasicTypeType basicType,
            Classifier type) {
        if (type instanceof Enumeration) {
            /* Treat all enumerations as Text for compatibility purposes. */
            if (BasicTypeType.STRING_LITERAL.equals(basicType)) {
                return Boolean.TRUE;
            }
        } else {
            if (type instanceof PrimitiveType) {
                // Extract the base primitive types for primitive types
                type =
                        PrimitivesUtil
                                .getBasePrimitiveType((PrimitiveType) type);
            }
            String bomPrimitiveTypeName = type.getName();
            Set<String> compatibleProcessToBOMTypesMap =
                    JSTypesCompatabilityUtil
                            .getCompatibleProcessToBOMTypesMap().get(basicType);

            if (compatibleProcessToBOMTypesMap != null
                    && compatibleProcessToBOMTypesMap
                            .contains(bomPrimitiveTypeName)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Comparing Process basic types to BOM Primitive types
     * 
     * @param dataType
     * @param type
     * @return <code>true</code> if the types match
     */
    public static Boolean doesBOMToProcessTypesMatch(Classifier type,
            BasicTypeType basicType) {
        if (type instanceof Enumeration) {
            if (BasicTypeType.STRING_LITERAL.equals(basicType)) {
                return Boolean.TRUE;
            }
        } else {
            if (type instanceof PrimitiveType) {
                // Extract the base primitive types for primitive types
                type =
                        PrimitivesUtil
                                .getBasePrimitiveType((PrimitiveType) type);
            }
            String bomPrimitiveTypeName = type.getName();

            Set<BasicTypeType> compatibleBOMToProcessTypesMap =
                    JSTypesCompatabilityUtil
                            .getCompatibleBOMToProcessTypesMap()
                            .get(bomPrimitiveTypeName);
            if (compatibleBOMToProcessTypesMap != null
                    && compatibleBOMToProcessTypesMap.contains(basicType)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
