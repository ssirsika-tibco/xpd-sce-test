/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.n2.cds.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * @author rsomayaj
 * 
 */
public class JSTypesCompatabilityUtil {

    protected static Map<String, Set<String>> BOM_TO_BOM_PRIMITIVETYPES_MAP;

    protected static Map<String, Set<String>> BOM_OBJECT_SUB_TYPES_MAP;

    protected static Map<String, Set<String>> BOM_OBJECT_RHS_SUB_TYPES_MAP;

    protected static Map<BasicTypeType, Set<String>> PROCESS_TO_BOM_TYPES_MAP;

    protected static Map<String, Set<String>> CDS_TO_CDS_PRIMITIVETYPES_MAP;

    protected static Map<String, Set<BasicTypeType>> BOM_TO_PROCESS_TYPES_MAP;

    protected static Map<String, String> PROCESS_TO_BOM_CONVERSION;

    /**
     * @return the Map that provides relationship between compatible
     *         Process(XPDL) to BOM types.
     */
    public static Map<String, Set<BasicTypeType>> getCompatibleBOMToProcessTypesMap() {
        if (BOM_TO_PROCESS_TYPES_MAP != null) {
            return BOM_TO_PROCESS_TYPES_MAP;
        }

        BOM_TO_PROCESS_TYPES_MAP = new HashMap<String, Set<BasicTypeType>>();

        HashSet<BasicTypeType> booleanAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.BOOLEAN_LITERAL,
                        BasicTypeType.STRING_LITERAL));

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                booleanAllowed);

        HashSet<BasicTypeType> integerAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.INTEGER_LITERAL,
                        BasicTypeType.BOOLEAN_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.STRING_LITERAL));

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                integerAllowed);

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                integerAllowed);

        HashSet<BasicTypeType> textAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.BOOLEAN_LITERAL,
                        BasicTypeType.INTEGER_LITERAL,
                        BasicTypeType.FLOAT_LITERAL,
                        BasicTypeType.STRING_LITERAL,
                        BasicTypeType.PERFORMER_LITERAL));

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                textAllowed);

        HashSet<BasicTypeType> dateAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.STRING_LITERAL,
                        BasicTypeType.DATE_LITERAL,
                        BasicTypeType.DATETIME_LITERAL));

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                dateAllowed);

        HashSet<BasicTypeType> timeAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.TIME_LITERAL,
                        BasicTypeType.STRING_LITERAL));

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                timeAllowed);

        HashSet<BasicTypeType> dateTimeAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.DATE_LITERAL,
                        BasicTypeType.DATETIME_LITERAL,
                        BasicTypeType.STRING_LITERAL,
                        BasicTypeType.TIME_LITERAL));

        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                dateTimeAllowed);

        BOM_TO_PROCESS_TYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                dateTimeAllowed);

        HashSet<BasicTypeType> durationAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.STRING_LITERAL));
        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                durationAllowed);

        BOM_TO_PROCESS_TYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME,
                Collections.EMPTY_SET);

        HashSet<BasicTypeType> uriAndIdAllowed = new HashSet<BasicTypeType>(
                Arrays.asList(BasicTypeType.STRING_LITERAL));
        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                uriAndIdAllowed);
        BOM_TO_PROCESS_TYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                uriAndIdAllowed);

        return BOM_TO_PROCESS_TYPES_MAP;

    }

    /**
     * @return the Map that provides relationship between compatible
     *         Process(XPDL) to BOM types.
     */
    public static Map<BasicTypeType, Set<String>> getCompatibleProcessToBOMTypesMap() {
        if (PROCESS_TO_BOM_TYPES_MAP != null) {
            return PROCESS_TO_BOM_TYPES_MAP;
        }

        PROCESS_TO_BOM_TYPES_MAP = new HashMap<BasicTypeType, Set<String>>();

        HashSet<String> booleanAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));

        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.BOOLEAN_LITERAL,
                booleanAllowed);

        HashSet<String> integerAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.INTEGER_LITERAL,
                integerAllowed);

        HashSet<String> decimalAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.FLOAT_LITERAL,
                decimalAllowed);

        HashSet<String> textAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.STRING_LITERAL, textAllowed);

        HashSet<String> dateAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.DATE_LITERAL, dateAllowed);

        HashSet<String> timeAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.TIME_LITERAL, timeAllowed);

        HashSet<String> dateTimeAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.DATETIME_LITERAL,
                dateTimeAllowed);

        HashSet<String> performerAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));
        PROCESS_TO_BOM_TYPES_MAP.put(BasicTypeType.PERFORMER_LITERAL,
                performerAllowed);

        return PROCESS_TO_BOM_TYPES_MAP;
    }

    public static Map<String, Set<String>> getCompatibleBOMObjectSubTypesMap() {
        if (null != BOM_OBJECT_SUB_TYPES_MAP) {
            return BOM_OBJECT_SUB_TYPES_MAP;
        }
        BOM_OBJECT_SUB_TYPES_MAP = new HashMap<String, Set<String>>();

        // values allowed to map to xsdAnyType
        HashSet<String> anyTypeAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE));
        BOM_OBJECT_SUB_TYPES_MAP.put(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE,
                anyTypeAllowed);

        // values allowed to map to xsdAnySimpleType
        HashSet<String> anySimpleType = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE));
        BOM_OBJECT_SUB_TYPES_MAP.put(
                PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE,
                anySimpleType);

        // values allowed to map to xsdAny
        HashSet<String> anyAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY));
        BOM_OBJECT_SUB_TYPES_MAP.put(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY,
                anyAllowed);

        // values allowed to map to xsdAnyAttribute
        HashSet<String> anyAttribute = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE));
        BOM_OBJECT_SUB_TYPES_MAP.put(
                PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE,
                anyAttribute);

        return BOM_OBJECT_SUB_TYPES_MAP;
    }

    public static Map<String, Set<String>> getCompatibleBOMRHSSubTypesMap() {
        if (null != BOM_OBJECT_RHS_SUB_TYPES_MAP) {
            return BOM_OBJECT_RHS_SUB_TYPES_MAP;
        }

        // values allowed to map anySimpleType from RHS
        BOM_OBJECT_RHS_SUB_TYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> anyTypeAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                        PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE));

        BOM_OBJECT_RHS_SUB_TYPES_MAP
                .put(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYTYPE, anyTypeAllowed);

        HashSet<String> anySimpleType = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                        PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE));

        BOM_OBJECT_RHS_SUB_TYPES_MAP.put(
                PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYSIMPLETYPE,
                anySimpleType);

        HashSet<String> anyAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                        PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY));
        BOM_OBJECT_RHS_SUB_TYPES_MAP.put(PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANY,
                anyAllowed);

        HashSet<String> anyAttributeAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                        PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE));
        BOM_OBJECT_RHS_SUB_TYPES_MAP.put(
                PrimitivesUtil.OBJECT_SUBTYPE_XSD_ANYATTRIBUTE,
                anyAttributeAllowed);

        return BOM_OBJECT_RHS_SUB_TYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between compatible BOM to BOM
     *         types.
     */
    public static Map<String, Set<String>> getCompatibleBOMtoBOMTypesMap() {
        if (BOM_TO_BOM_PRIMITIVETYPES_MAP != null) {
            return BOM_TO_BOM_PRIMITIVETYPES_MAP;
        }
        BOM_TO_BOM_PRIMITIVETYPES_MAP = new HashMap<String, Set<String>>();

        // Values allowed to map to Boolean fields
        HashSet<String> booleanAllowed = new HashSet<String>();
        BOM_TO_BOM_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME, booleanAllowed);

        // Values allowed to map to Integer fields
        HashSet<String> integerAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME, integerAllowed);

        // Values allowed to map to Decimal fields
        HashSet<String> decimalAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME, decimalAllowed);

        // Values allowed to map to Text fields
        HashSet<String> textAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME, textAllowed);

        // Values allowed to map to Date fields
        HashSet<String> dateAllowed = new HashSet<String>(Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME, dateAllowed);

        // Values allowed to map to Time fields
        HashSet<String> timeAllowed = new HashSet<String>(Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME, timeAllowed);

        // Values allowed to map to DateTime fields
        HashSet<String> dateTimeAllowed = new HashSet<String>(Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                dateTimeAllowed);
        // Values allowed to map to DateTimeTZ fields
        HashSet<String> dateTimeTZAllowed = new HashSet<String>(Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME));
        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                dateTimeTZAllowed);

        // Values allowed to URI or Id
        HashSet<String> uriOrId = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));

        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                uriOrId);

        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                uriOrId);

        // Values for Attachment EMPTY SET
        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME,
                Collections.EMPTY_SET);

        //
        HashSet<String> durationAllowed = new HashSet<String>();
        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME,
                durationAllowed);

        //
        HashSet<String> bomObjectAllowed =
                new HashSet<String>(Arrays.asList(JsConsts.BOM_OBJECT));
        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(JsConsts.BOM_OBJECT,
                bomObjectAllowed);

        BOM_TO_BOM_PRIMITIVETYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME,
                Collections
                        .singleton(PrimitivesUtil.BOM_PRIMITIVE_OBJECT_NAME));

        return BOM_TO_BOM_PRIMITIVETYPES_MAP;
    }

    /**
     * @return the Map that provides relationship between compatible CDS to CDS
     *         types.
     */
    public static Map<String, Set<String>> getCompatibleCDStoCDSTypesMap() {
        if (CDS_TO_CDS_PRIMITIVETYPES_MAP != null) {
            return CDS_TO_CDS_PRIMITIVETYPES_MAP;
        }
        CDS_TO_CDS_PRIMITIVETYPES_MAP = new HashMap<String, Set<String>>();

        HashSet<String> textAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_ID_NAME,
                        JsConsts.DQL_STRING));
        CDS_TO_CDS_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME, textAllowed);

        CDS_TO_CDS_PRIMITIVETYPES_MAP.put(JsConsts.PERFORMER, textAllowed);

        HashSet<String> booleanAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME));
        CDS_TO_CDS_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME, booleanAllowed);

        HashSet<String> integerAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME));

        CDS_TO_CDS_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME, integerAllowed);

        HashSet<String> decimalAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME));
        CDS_TO_CDS_PRIMITIVETYPES_MAP
                .put(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME, decimalAllowed);

        HashSet<String> dateTimeTZAllowed = new HashSet<String>(
                Arrays.asList(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME,
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME));

        CDS_TO_CDS_PRIMITIVETYPES_MAP.put(
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME,
                dateTimeTZAllowed);

        return CDS_TO_CDS_PRIMITIVETYPES_MAP;
    }

    public static Map<String, String> getN2ToBomTypeConversion() {
        if (PROCESS_TO_BOM_CONVERSION != null) {
            return PROCESS_TO_BOM_CONVERSION;
        }
        PROCESS_TO_BOM_CONVERSION = new HashMap<String, String>();

        PROCESS_TO_BOM_CONVERSION.put(JsConsts.FLOAT,
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.STRING,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.PERFORMER,
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.NUMBER,
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.BOM_DATE,
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.DATETIMELC,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.DATETIMETZLC,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME);
        PROCESS_TO_BOM_CONVERSION.put(JsConsts.DATETIMETZ,
                PrimitivesUtil.BOM_PRIMITIVE_DATETIMETZ_NAME);

        return PROCESS_TO_BOM_CONVERSION;
    }

}
