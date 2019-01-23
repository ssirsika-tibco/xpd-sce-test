/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.implementer.script;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.TypeDeclaration;

/**
 * @author nwilson
 */
public abstract class BaseTypeUtil {
    /** Cannot check types issue id. */
    public static final String CANNOT_CHECK_TYPES = "bpmn.dev.cannotCheckTypes"; //$NON-NLS-1$    

    /** Max length limit in mapping Decimal to Float types. * */
    public static final String DECIMAL_TO_FLOAT_MAX_LENGTH = "6"; //$NON-NLS-1$

    /** Max length limit in mapping Decimal to Double types. * */
    public static final String DECIMAL_TO_DOUBLE_MAX_LENGTH = "15"; //$NON-NLS-1$

    /** Integral part limit mapping Decimal to Byte types. * */
    public static final String DECIMAL_TO_BYTE_INTEGRALPART_LIMIT = "3"; //$NON-NLS-1$

    /** Integral part limit mapping Decimal to Short types. * */
    public static final String DECIMAL_TO_SHORT_INTEGRALPART_LIMIT = "5"; //$NON-NLS-1$

    /** Integral part limit mapping Decimal to integer types. * */
    public static final String DECIMAL_TO_INTEGER_INTEGRALPART_LIMIT = "10"; //$NON-NLS-1$

    /** Integral part limit mapping Decimal to float types. * */
    public static final String DECIMAL_TO_FLOAT_INTEGRALPART_LIMIT = "5"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to Float types. * */
    public static final String INTEGER_TO_FLOAT_MAX_LENGTH = "6"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to Double types. * */
    public static final String INTEGER_TO_DOUBLE_MAX_LENGTH = "15"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to Byte types. * */
    public static final String INTEGER_TO_BYTE_MAX_LENGTH = "3"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to Short types. * */
    public static final String INTEGER_TO_SHORT_MAX_LENGTH = "5"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to Integer types. * */
    public static final String INTEGER_TO_INTEGER_MAX_LENGTH = "10"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to Char types. * */
    public static final String INTEGER_TO_CHAR_MAX_LENGTH = "5"; //$NON-NLS-1$

    /** Max length limit in mapping Integer to long types. * */
    public static final String INTEGER_TO_LONG_MAX_LENGTH = "19"; //$NON-NLS-1$

    /** XSD String ids. */
    protected static final String[] XSD_STRING = { "string", "anyURI", "QName", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "NOTATION", "normalizedString", "token", "language", "IDREFS", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "ENTITIES", "NMTOKEN", "NMTOKENS", "Name", "NCName", "ID", "IDREF", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
            "ENTITY", "hexBinary" }; //$NON-NLS-1$ //$NON-NLS-2$

    /** XSD Integer ids. */
    protected static final String[] XSD_INTEGER = {
            "integer", "nonPositiveInteger", "negativeInteger", "long", "int", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "short", "byte", "nonNegativeInteger", "unsignedLong", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            "unsignedInt", "unsignedShort", "unsignedByte", "positiveInteger" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    /** XSD Boolean ids. */
    protected static final String[] XSD_BOOLEAN = { "boolean" }; //$NON-NLS-1$

    /** XSD Float ids. */
    protected static final String[] XSD_FLOAT = { "float", "double", "decimal", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "precisionDecimal", "string" }; //$NON-NLS-1$ //$NON-NLS-2$

    /** XSD Date Time ids. */
    protected static final String[] XSD_DATE = {
            "duration", "dateTime", "date", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            "gYearMonth", "gYear", "gMonthDay", "gDay", "gMonth", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
            "yearMonthDuration", "string" }; //$NON-NLS-1$ //$NON-NLS-2$

    /** XSD Date Time ids. */
    protected static final String[] XSD_TIME = { "dateTime", "time", //$NON-NLS-1$ //$NON-NLS-2$
            "dayTimeDuration", "string" }; //$NON-NLS-1$ //$NON-NLS-2$

    /** XPD-1491: Allow for wildcards anySimpleType and xsd:any */
    public static final String XSD_ANYSIMPLETYPE = "anySimpleType"; //$NON-NLS-1$

    public static final String XSD_ANY = "any"; //$NON-NLS-1$

    /** String loss of precision types. */
    protected static final String[] WEB_STRING_LOSS_OP = { "boolean" }; //$NON-NLS-1$

    /** Boolean loss of precision types. */
    protected static final String[] WEB_BOOLEAN_LOSS_OP = { "string" }; //$NON-NLS-1$

    /** XSD String ids. */
    protected static Collection<String> xsdStrings;

    /** XSD Integer ids. */
    protected static Collection<String> xsdIntegers;

    /** XSD Boolean ids. */
    protected static Collection<String> xsdBooleans;

    /** XSD Float ids. */
    protected static Collection<String> xsdFloats;

    /** XSD Date Time ids. */
    protected static Collection<String> xsdDateTimes;

    /** XSD Date Time ids. */
    protected static Collection<String> xsdDates;

    /** XSD Date Time ids. */
    protected static Collection<String> xsdTimes;

    /** XSD all ids. */
    protected static Collection<String> xsdAll;

    /** String loss of precision types. */
    protected static Collection<String> webStringLossOP;

    /** Boolean loss of precision types. */
    protected static Collection<String> webBooleanLossOP;

    static {
        xsdStrings = init(XSD_STRING);
        xsdIntegers = init(XSD_INTEGER);
        xsdBooleans = init(XSD_BOOLEAN);
        xsdFloats = init(XSD_FLOAT);
        xsdDates = init(XSD_DATE);
        xsdTimes = init(XSD_TIME);
        xsdDateTimes = new HashSet<String>();
        xsdDateTimes.addAll(xsdDates);
        xsdDateTimes.addAll(xsdTimes);
        xsdAll = new HashSet<String>();
        xsdAll.addAll(xsdStrings);
        xsdAll.addAll(xsdIntegers);
        xsdAll.addAll(xsdBooleans);
        xsdAll.addAll(xsdFloats);
        xsdAll.addAll(xsdDateTimes);
        webStringLossOP = init(WEB_STRING_LOSS_OP);
        webBooleanLossOP = init(WEB_BOOLEAN_LOSS_OP);
    }

    /**
     * Constructor.
     */
    public BaseTypeUtil() {
    }

    /**
     * @param array
     *            The array of strings to use.
     * @return a collection of strings.
     */
    private static Collection<String> init(String[] array) {
        Collection<String> collection = new HashSet<String>();
        for (String string : array) {
            collection.add(string);
        }
        return collection;
    }

    /**
     * @param pathType
     *            The path type.
     * @param dataType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean checkOtherAcceptableMatches(
            XSDTypeDefinition pathType, DataType dataType) {
        boolean match = false;
        if (pathType != null && dataType != null) {
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
            if (basicType != null) {
                match = checkOtherAcceptableMatches(pathType, basicType);
            }
        }
        return match;
    }

    /**
     * @param pathType
     *            The path type.
     * @param basicType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean checkOtherAcceptableMatches(
            XSDTypeDefinition pathType, BasicType basicType) {
        boolean match = false;
        if (pathType != null) {
            if (basicType != null
                    && pathType instanceof XSDSimpleTypeDefinition) {
                XSDSimpleTypeDefinition simple =
                        (XSDSimpleTypeDefinition) pathType;
                BasicTypeType basicTypeType = basicType.getType();
                if (BasicTypeType.STRING_LITERAL.equals(basicTypeType)
                        && webStringLossOP.contains(simple.getName())) {
                    match = true;
                } else if (BasicTypeType.BOOLEAN_LITERAL.equals(basicTypeType)
                        && webBooleanLossOP.contains(simple.getName())) {
                    match = true;
                }
            }

            if (basicType != null
                    && (pathType instanceof XSDComplexTypeDefinition)) {
                BasicTypeType basicTypeType = basicType.getType();
                XSDComplexTypeDefinition complex =
                        (XSDComplexTypeDefinition) pathType;
                XSDTypeDefinition typeDef = complex.getBaseType();
                if (typeDef instanceof XSDSimpleTypeDefinition) {
                    XSDTypeDefinition baseTypeDef = typeDef.getBaseType();
                    if (BasicTypeType.STRING_LITERAL.equals(basicTypeType)
                            && (xsdStrings.contains(typeDef.getName()))) {
                        match = true;
                        return match;
                    } else if (baseTypeDef != null
                            && xsdStrings.contains(baseTypeDef.getName())) {
                        match = true;
                        return match;
                    }
                    if (BasicTypeType.BOOLEAN_LITERAL.equals(basicTypeType)
                            && xsdBooleans.contains(typeDef.getName())) {
                        match = true;
                        return match;
                    } else if (baseTypeDef != null
                            && xsdBooleans.contains(baseTypeDef.getName())) {
                        match = true;
                        return match;
                    }
                    if (BasicTypeType.INTEGER_LITERAL.equals(basicTypeType)
                            && xsdIntegers.contains(typeDef.getName())) {
                        match = true;
                        return match;
                    } else if (baseTypeDef != null
                            && xsdIntegers.contains(baseTypeDef.getName())) {
                        match = true;
                        return match;
                    }
                }
            }
        }
        return match;
    }

    /**
     * @param pathType
     *            The path type.
     * @param dataType
     *            The data type.
     * @param mapping
     *            The mapping.
     * @return true if the types match.
     */
    public static boolean typesMatch(XSDTypeDefinition pathType,
            DataType dataType, DataMapping mapping) {
        boolean match = false;
        if (dataType != null) {
            BasicType basicType =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
            if (basicType != null) {
                if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
                    match = typesInMatch(pathType, basicType);

                } else {
                    match = typesOutMatch(pathType, basicType);
                }
            }
        }
        return match;
    }

    /**
     * @param pathType
     *            The path type.
     * @param basicType
     *            The data type.
     * @param mapping
     *            The mapping.
     * @return true if the types match.
     */
    public static boolean typesMatch(XSDTypeDefinition pathType,
            BasicType basicType, DataMapping mapping) {
        boolean match = false;
        if (basicType != null) {
            if (DirectionType.IN_LITERAL.equals(mapping.getDirection())) {
                match = typesInMatch(pathType, basicType);

            } else {
                match = typesOutMatch(pathType, basicType);
            }
        }
        return match;
    }

    /**
     * @param pathType
     *            The path type.
     * @param basicType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean typesInMatch(XSDTypeDefinition pathType,
            BasicType basicType) {
        boolean match = false;
        if (pathType != null) {
            XSDSimpleTypeDefinition simpleType =
                    getXSDSimpleTypeDefinition(pathType);
            if (simpleType != null) {

                /* XPD-1491: Allow mapping into xsd:any from any simple type. */
                if (XSD_ANYSIMPLETYPE.equals(simpleType.getName())
                        || XSD_ANYSIMPLETYPE.equals(simpleType
                                .getBaseTypeDefinition().getName())) {
                    if (basicType != null) {
                        return true;
                    }

                }
                Set<XSDSimpleTypeDefinition> supported =
                        getSupportedSimple(simpleType);
                for (XSDSimpleTypeDefinition next : supported) {
                    match = isInMatch(basicType.getType(), next);
                    if (match) {
                        break;
                    }
                }
            }
        }

        return match;
    }

    /**
     * Get the XSDSimpleTypeDefinition for the given XSD type if at all possible
     * - this might be the type itself or it may be the base type of a complex
     * type with simple content.
     * 
     * @param pathType
     * 
     * @return XSD Simple Type Definition of null if cannot be ascertained.
     */
    public static XSDSimpleTypeDefinition getXSDSimpleTypeDefinition(
            XSDTypeDefinition pathType) {
        if (pathType != null) {
            if (pathType instanceof XSDSimpleTypeDefinition) {
                return (XSDSimpleTypeDefinition) pathType;
            } else if (pathType.getBaseType() instanceof XSDSimpleTypeDefinition) {
                return (XSDSimpleTypeDefinition) pathType.getBaseType();
            }
        }
        return null;
    }

    private static boolean isInMatch(BasicTypeType basicType,
            XSDSimpleTypeDefinition simple) {
        boolean match = false;
        if (simple != null && basicType != null) {
            if (BasicTypeType.STRING_LITERAL.equals(basicType)) {
                match = true;
            } else if (BasicTypeType.INTEGER_LITERAL.equals(basicType)
                    && (xsdIntegers.contains(simple.getName())
                            || xsdStrings.contains(simple.getName())
                            || xsdBooleans.contains(simple.getName()) || xsdFloats
                                .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.BOOLEAN_LITERAL.equals(basicType)
                    && (xsdIntegers.contains(simple.getName())
                            || xsdStrings.contains(simple.getName()) || xsdFloats
                                .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.FLOAT_LITERAL.equals(basicType)
                    && xsdFloats.contains(simple.getName())) {
                match = true;
            } else if (BasicTypeType.DATETIME_LITERAL.equals(basicType)
                    && xsdDateTimes.contains(simple.getName())) {
                match = true;
            } else if (BasicTypeType.DATE_LITERAL.equals(basicType)
                    && xsdDates.contains(simple.getName())) {
                match = true;
            } else if (BasicTypeType.TIME_LITERAL.equals(basicType)
                    && xsdTimes.contains(simple.getName())) {
                match = true;
            } else if (BasicTypeType.PERFORMER_LITERAL.equals(basicType)) {
                match = true;
            }
        }
        return match;
    }

    private static boolean isHexBinary(String type) {
        if (type != null && type.equals("hexBinary")) {//$NON-NLS-1$
            return true;
        }
        return false;
    }

    /**
     * @param pathType
     *            The path type.
     * @param basicType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean typesOutMatch(XSDTypeDefinition pathType,
            BasicType basicType) {
        boolean match = false;
        if (pathType != null && basicType != null) {
            XSDSimpleTypeDefinition simpleType =
                    getXSDSimpleTypeDefinition(pathType);

            if (simpleType != null) {
                /*
                 * XPD-1491: Allow mapping xsd:anySimpleType only into String
                 * type.
                 */
                if (XSD_ANYSIMPLETYPE.equals(simpleType.getName())
                        || XSD_ANYSIMPLETYPE.equals(simpleType
                                .getBaseTypeDefinition().getName())) {
                    if (BasicTypeType.STRING_LITERAL
                            .equals(basicType.getType())) {
                        return true;
                    }

                }
                Set<XSDSimpleTypeDefinition> supported =
                        getSupportedSimple(simpleType);

                for (XSDSimpleTypeDefinition next : supported) {
                    match = isOutMatch(basicType.getType(), next);
                    if (match) {
                        break;
                    }
                }
            }
        }
        return match;
    }

    private static boolean isOutMatch(BasicTypeType basicType,
            XSDSimpleTypeDefinition simple) {
        boolean match = false;
        if (simple != null && basicType != null) {
            if (BasicTypeType.STRING_LITERAL.equals(basicType)) {
                match = true;
            } else if (BasicTypeType.INTEGER_LITERAL.equals(basicType)
                    && (xsdIntegers.contains(simple.getName()) || xsdStrings
                            .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.BOOLEAN_LITERAL.equals(basicType)
                    && (xsdIntegers.contains(simple.getName()) || xsdStrings
                            .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.FLOAT_LITERAL.equals(basicType)
                    && (xsdFloats.contains(simple.getName())
                            || xsdIntegers.contains(simple.getName()) || xsdStrings
                                .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.DATETIME_LITERAL.equals(basicType)
                    && (xsdDateTimes.contains(simple.getName()) || xsdStrings
                            .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.DATE_LITERAL.equals(basicType)
                    && (xsdDates.contains(simple.getName()) || xsdStrings
                            .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.TIME_LITERAL.equals(basicType)
                    && (xsdTimes.contains(simple.getName()) || xsdStrings
                            .contains(simple.getName()))) {
                match = true;
                if (BaseTypeUtil.isHexBinary(simple.getName())) {
                    match = false;
                }
            } else if (BasicTypeType.PERFORMER_LITERAL.equals(basicType)) {
                match = true;
            }
        }
        return match;
    }

    /**
     * @param simple
     * @return
     */
    public static Set<XSDSimpleTypeDefinition> getSupportedSimple(
            XSDSimpleTypeDefinition simple) {
        Set<XSDSimpleTypeDefinition> supported =
                new HashSet<XSDSimpleTypeDefinition>();
        String name = simple.getName();
        if (xsdAll.contains(name)) {
            supported.add(simple);
        } else {
            // Atomic, list or union?
            if (simple.getItemTypeDefinition() != null) {
                // List
                XSDSimpleTypeDefinition base = simple.getItemTypeDefinition();
                if (!base.equals(simple)) {
                    supported.addAll(getSupportedSimple(base));
                }
            } else if (simple.getMemberTypeDefinitions() != null
                    && simple.getMemberTypeDefinitions().size() != 0) {
                // Union
                EList<XSDSimpleTypeDefinition> memberTypes =
                        simple.getMemberTypeDefinitions();
                for (XSDSimpleTypeDefinition next : memberTypes) {
                    supported.addAll(getSupportedSimple(next));
                }
            } else {
                // Atomic
                XSDSimpleTypeDefinition base = simple.getBaseTypeDefinition();
                if (base != null && !base.equals(simple)) {
                    supported.addAll(getSupportedSimple(base));
                }
            }
        }
        return supported;
    }

    /**
     * Check that the data is of type decimal
     * 
     * @param dataType
     *            The DataType.
     * @return If is Decimal Type.
     */
    public static boolean isDecimalType(DataType dataType) {
        BasicType basic =
                BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
        if (basic != null
                && BasicTypeType.FLOAT_LITERAL.equals(basic.getType())) {
            return true;
        }
        return false;
    }

    /**
     * @param dataType
     *            The DataType.
     * @return If is String Type.
     */
    public static boolean isStringType(DataType dataType) {
        BasicType basic =
                BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
        if (basic != null
                && BasicTypeType.STRING_LITERAL.equals(basic.getType())) {
            return true;
        }
        return false;
    }

    /**
     * @param dataType
     *            The DataType.
     * @return If is String Type.
     */
    public static boolean isPerformerType(DataType dataType) {
        BasicType basic =
                BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
        if (basic != null && BasicTypeType.PERFORMER_LITERAL.equals(basic)) {
            return true;

        }
        return false;
    }

    /**
     * @param dataType
     *            The DataType.
     * @return If is Integer Type.
     */
    public static boolean isIntegerType(DataType dataType) {
        BasicType basic =
                BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
        if (basic != null && BasicTypeType.INTEGER_LITERAL.equals(basic)) {
            return true;

        }
        return false;
    }

    /**
     * @param value
     *            The value.
     * @param limit
     *            The limit.
     * @return If the value exceeds the limit.
     */
    public static boolean exceedsLimit(int value, String limit) {
        try {
            if (value > Integer.valueOf(limit).intValue()) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param value
     *            The value.
     * @param limit
     *            The limit.
     * @return If the value is less than the limit.
     */
    public static boolean isLessThanLimit(int value, String limit) {
        try {
            if (value < Integer.valueOf(limit).intValue()) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static BasicType resolveDataType(DataType dataType) {
        return BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
    }

    public static boolean checkExternalReferenceMatches(Activity activity,
            XSDTypeDefinition pathType, DataType dataType) {

        // resolve one level of type declaration to see if it is a type
        // reference
        if (dataType instanceof DeclaredType) {
            DeclaredType declType = (DeclaredType) dataType;
            if (declType.getTypeDeclarationId() != null) {
                TypeDeclaration typeDecl =
                        activity.getProcess()
                                .getPackage()
                                .getTypeDeclaration(declType.getTypeDeclarationId());
                if (typeDecl.getExternalReference() != null)
                    dataType = typeDecl.getExternalReference();
            }
        }

        if (dataType instanceof ExternalReference) {
            ExternalReference externalRef = (ExternalReference) dataType;
            String bomClassName =
                    ProcessUIUtil.resolveExternalBomClassName(externalRef
                            .getXref(), externalRef.getLocation(), externalRef
                            .getNamespace());
            if (bomClassName != null) {
                if (pathType != null && pathType.getName() != null
                        && pathType.getName().equals(bomClassName)) {
                    return true;
                }
                if ((pathType != null && pathType.getBaseType() != null)
                        && (pathType.getBaseType().getName()
                                .equals(bomClassName))) {
                    return true;
                }
            }
        }
        return false;
    }
}
