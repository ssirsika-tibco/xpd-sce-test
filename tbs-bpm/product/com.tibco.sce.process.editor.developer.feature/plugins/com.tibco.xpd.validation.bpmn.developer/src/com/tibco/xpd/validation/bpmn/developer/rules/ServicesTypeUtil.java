/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import java.util.Collection;
import java.util.HashSet;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.implementer.nativeservices.java.javaservice.mapper.ParameterTypeEnum;
import com.tibco.xpd.implementer.script.BaseTypeUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;

/**
 * @author nwilson
 */
public abstract class ServicesTypeUtil extends BaseTypeUtil {
    /** Java mapping string types. */
    protected static final ParameterTypeEnum[] JAVA_IN_STRING =
            { ParameterTypeEnum.STRING, ParameterTypeEnum.CHAR,
                    ParameterTypeEnum.PCHAR };

    /** Java mapping string types. */
    protected static final ParameterTypeEnum[] JAVA_OUT_STRING =
            { ParameterTypeEnum.STRING, ParameterTypeEnum.CHAR,
                    ParameterTypeEnum.PCHAR, ParameterTypeEnum.BOOLEAN,
                    ParameterTypeEnum.PBOOLEAN };

    /** Java mapping boolean types. */
    protected static final ParameterTypeEnum[] JAVA_IN_BOOLEAN =
            { ParameterTypeEnum.BOOLEAN, ParameterTypeEnum.PBOOLEAN,
                    ParameterTypeEnum.STRING };

    /** Java mapping boolean types. */
    protected static final ParameterTypeEnum[] JAVA_OUT_BOOLEAN =
            { ParameterTypeEnum.BOOLEAN, ParameterTypeEnum.PBOOLEAN,
                    ParameterTypeEnum.STRING };

    /** Java input mapping integer types. */
    protected static final ParameterTypeEnum[] JAVA_IN_INTEGER =
            { ParameterTypeEnum.INTEGER, ParameterTypeEnum.PINTEGER,
                    ParameterTypeEnum.BIGINTEGER, ParameterTypeEnum.SHORT,
                    ParameterTypeEnum.PSHORT, ParameterTypeEnum.LONG,
                    ParameterTypeEnum.PLONG, ParameterTypeEnum.BYTE,
                    ParameterTypeEnum.PBYTE, ParameterTypeEnum.FLOAT,
                    ParameterTypeEnum.PFLOAT, ParameterTypeEnum.BIGDECIMAL,
                    ParameterTypeEnum.DOUBLE, ParameterTypeEnum.PDOUBLE };

    /** Java output mapping integer types. */
    protected static final ParameterTypeEnum[] JAVA_OUT_INTEGER =
            { ParameterTypeEnum.DOUBLE, ParameterTypeEnum.PDOUBLE,
                    ParameterTypeEnum.FLOAT, ParameterTypeEnum.PFLOAT,
                    ParameterTypeEnum.INTEGER, ParameterTypeEnum.PINTEGER,
                    ParameterTypeEnum.BIGINTEGER, ParameterTypeEnum.SHORT,
                    ParameterTypeEnum.PSHORT, ParameterTypeEnum.LONG,
                    ParameterTypeEnum.PLONG, ParameterTypeEnum.BYTE,
                    ParameterTypeEnum.PBYTE, ParameterTypeEnum.BIGDECIMAL };

    /** Java input mapping float types. */
    protected static final ParameterTypeEnum[] JAVA_IN_FLOAT =
            { ParameterTypeEnum.FLOAT, ParameterTypeEnum.PFLOAT,
                    ParameterTypeEnum.BIGDECIMAL, ParameterTypeEnum.BIGINTEGER,
                    ParameterTypeEnum.DOUBLE, ParameterTypeEnum.PDOUBLE,
                    ParameterTypeEnum.BYTE, ParameterTypeEnum.PBYTE,
                    ParameterTypeEnum.SHORT, ParameterTypeEnum.PSHORT,
                    ParameterTypeEnum.INTEGER, ParameterTypeEnum.PINTEGER,
                    ParameterTypeEnum.LONG, ParameterTypeEnum.PLONG };

    /** Java output mapping float types. */
    protected static final ParameterTypeEnum[] JAVA_OUT_FLOAT =
            { ParameterTypeEnum.FLOAT, ParameterTypeEnum.PFLOAT,
                    ParameterTypeEnum.BIGDECIMAL, ParameterTypeEnum.DOUBLE,
                    ParameterTypeEnum.PDOUBLE, ParameterTypeEnum.INTEGER,
                    ParameterTypeEnum.PINTEGER, ParameterTypeEnum.BIGINTEGER,
                    ParameterTypeEnum.SHORT, ParameterTypeEnum.PSHORT,
                    ParameterTypeEnum.LONG, ParameterTypeEnum.PLONG,
                    ParameterTypeEnum.BYTE, ParameterTypeEnum.PBYTE };

    /** Decimal input loss of precision types. */
    protected static final ParameterTypeEnum[] JAVA_DECIMAL_IN_LOSS_OP =
            { ParameterTypeEnum.BIGINTEGER, ParameterTypeEnum.LONG,
                    ParameterTypeEnum.PLONG, ParameterTypeEnum.BYTE,
                    ParameterTypeEnum.PBYTE, ParameterTypeEnum.SHORT,
                    ParameterTypeEnum.PSHORT, ParameterTypeEnum.INTEGER,
                    ParameterTypeEnum.PINTEGER };

    /** Decimal input loss of precision types. */
    protected static final ParameterTypeEnum[] JAVA_STRING_IN_LOSS_OP =
            { ParameterTypeEnum.CHAR, ParameterTypeEnum.PCHAR };

    /** Decimal output loss of precision types. */
    protected static final ParameterTypeEnum[] JAVA_DECIMAL_OUT_LOSS_OP =
            { ParameterTypeEnum.LONG, ParameterTypeEnum.PLONG,
                    ParameterTypeEnum.DOUBLE, ParameterTypeEnum.PDOUBLE };

    /** Integer output loss of precision types. */
    protected static final ParameterTypeEnum[] JAVA_INTEGER_OUT_LOSS_OP =
            { ParameterTypeEnum.FLOAT, ParameterTypeEnum.PFLOAT,
                    ParameterTypeEnum.BIGDECIMAL };

    /** Java mapping string types. */
    protected static Collection<ParameterTypeEnum> javaInStrings;

    /** Java mapping boolean types. */
    protected static Collection<ParameterTypeEnum> javaInBooleans;

    /** Java mapping string types. */
    protected static Collection<ParameterTypeEnum> javaOutStrings;

    /** Java mapping boolean types. */
    protected static Collection<ParameterTypeEnum> javaOutBooleans;

    /** Java input mapping integer types. */
    protected static Collection<ParameterTypeEnum> javaInIntegers;

    /** Java output mapping integer types. */
    protected static Collection<ParameterTypeEnum> javaOutIntegers;

    /** Java input mapping float types. */
    protected static Collection<ParameterTypeEnum> javaInFloats;

    /** Java output mapping float types. */
    protected static Collection<ParameterTypeEnum> javaOutFloats;

    /** Decimal input loss of precision types. */
    protected static Collection<ParameterTypeEnum> javaDecimalInLossOP;

    /** String input loss of precision types. */
    protected static Collection<ParameterTypeEnum> javaStringInLossOP;

    /** Decimal output loss of precision types. */
    protected static Collection<ParameterTypeEnum> javaDecimalOutLossOP;

    /** Integer output loss of precision types. */
    protected static Collection<ParameterTypeEnum> javaIntegerOutLossOP;

    static {
        javaInStrings = init(JAVA_IN_STRING);
        javaInBooleans = init(JAVA_IN_BOOLEAN);
        javaOutStrings = init(JAVA_OUT_STRING);
        javaOutBooleans = init(JAVA_OUT_BOOLEAN);
        javaInIntegers = init(JAVA_IN_INTEGER);
        javaOutIntegers = init(JAVA_OUT_INTEGER);
        javaInFloats = init(JAVA_IN_FLOAT);
        javaOutFloats = init(JAVA_OUT_FLOAT);
        javaDecimalInLossOP = init(JAVA_DECIMAL_IN_LOSS_OP);
        javaStringInLossOP = init(JAVA_STRING_IN_LOSS_OP);
        javaDecimalOutLossOP = init(JAVA_DECIMAL_OUT_LOSS_OP);
        javaIntegerOutLossOP = init(JAVA_INTEGER_OUT_LOSS_OP);
    }

    /**
     * Constructor.
     */
    public ServicesTypeUtil() {
    }

    /**
     * @param array
     *            The array of types to use.
     * @return a collection of types.
     */
    private static Collection<ParameterTypeEnum> init(ParameterTypeEnum[] array) {
        Collection<ParameterTypeEnum> collection =
                new HashSet<ParameterTypeEnum>();
        for (ParameterTypeEnum type : array) {
            collection.add(type);
        }
        return collection;
    }

    /**
     * @param javaType
     *            The java type.
     * @param dataType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean typesInMatch(ParameterTypeEnum javaType,
            DataType dataType) {
        boolean match = false;
        if (javaType != null && dataType != null) {
            BasicType basic =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);

            if (basic != null) {
                BasicTypeType basicType = basic.getType();
                if (BasicTypeType.STRING_LITERAL.equals(basicType)
                        && javaInStrings.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.INTEGER_LITERAL.equals(basicType)
                        && javaInIntegers.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.BOOLEAN_LITERAL.equals(basicType)
                        && javaInBooleans.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.FLOAT_LITERAL.equals(basicType)
                        && javaInFloats.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.DATETIME_LITERAL.equals(basicType)
                        && ParameterTypeEnum.DATE.equals(javaType)) {
                    match = true;
                } else if (BasicTypeType.PERFORMER_LITERAL.equals(basicType)
                        && javaInStrings.contains(javaType)) {
                    match = true;
                }
            }
        }
        return match;
    }

    /**
     * @param javaType
     *            The java type.
     * @param dataType
     *            The data type.
     * @return true if the types match.
     */
    public static boolean typesOutMatch(ParameterTypeEnum javaType,
            DataType dataType) {
        boolean match = false;
        if (javaType != null && dataType != null) {
            BasicType basic =
                    BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);

            if (basic != null) {
                BasicTypeType basicType = basic.getType();
                if (BasicTypeType.STRING_LITERAL.equals(basicType)
                        && javaOutStrings.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.INTEGER_LITERAL.equals(basicType)
                        && javaOutIntegers.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.BOOLEAN_LITERAL.equals(basicType)
                        && javaOutBooleans.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.FLOAT_LITERAL.equals(basicType)
                        && javaOutFloats.contains(javaType)) {
                    match = true;
                } else if (BasicTypeType.DATETIME_LITERAL.equals(basicType)
                        && ParameterTypeEnum.DATE.equals(javaType)) {
                    match = true;
                } else if (BasicTypeType.PERFORMER_LITERAL.equals(basicType)
                        && javaOutStrings.contains(javaType)) {
                    match = true;
                }
            }
        }
        return match;
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
     * @param javaType
     *            The javaType.
     * @return The maximum length.
     */
    public static String getJavaInDecimalLengthLimit(ParameterTypeEnum javaType) {
        if (ParameterTypeEnum.FLOAT.equals(javaType)
                || ParameterTypeEnum.PFLOAT.equals(javaType)) {
            return DECIMAL_TO_FLOAT_MAX_LENGTH;
        } else if (ParameterTypeEnum.DOUBLE.equals(javaType)
                || ParameterTypeEnum.PDOUBLE.equals(javaType)) {
            return DECIMAL_TO_DOUBLE_MAX_LENGTH;
        }
        return null;
    }

    /**
     * @param javaType
     *            The javaType.
     * @return The maximum length.
     */
    public static String getJavaInDecimalIntegralPartLimit(
            ParameterTypeEnum javaType) {
        if (ParameterTypeEnum.BYTE.equals(javaType)
                || ParameterTypeEnum.PBYTE.equals(javaType)) {
            return DECIMAL_TO_BYTE_INTEGRALPART_LIMIT;
        } else if (ParameterTypeEnum.SHORT.equals(javaType)
                || ParameterTypeEnum.PSHORT.equals(javaType)) {
            return DECIMAL_TO_SHORT_INTEGRALPART_LIMIT;
        } else if (ParameterTypeEnum.INTEGER.equals(javaType)
                || ParameterTypeEnum.PINTEGER.equals(javaType)) {
            return DECIMAL_TO_INTEGER_INTEGRALPART_LIMIT;
        }
        return null;
    }

    /**
     * @param javaType
     *            The javaType.
     * @return The maximum length.
     */
    public static String getJavaOutDecimalIntegralPartLimit(
            ParameterTypeEnum javaType) {
        if (ParameterTypeEnum.BYTE.equals(javaType)
                || ParameterTypeEnum.PBYTE.equals(javaType)) {
            return DECIMAL_TO_BYTE_INTEGRALPART_LIMIT;
        } else if (ParameterTypeEnum.SHORT.equals(javaType)
                || ParameterTypeEnum.PSHORT.equals(javaType)) {
            return DECIMAL_TO_SHORT_INTEGRALPART_LIMIT;
        } else if (ParameterTypeEnum.INTEGER.equals(javaType)
                || ParameterTypeEnum.PINTEGER.equals(javaType)) {
            return DECIMAL_TO_INTEGER_INTEGRALPART_LIMIT;
        } else if (ParameterTypeEnum.FLOAT.equals(javaType)
                || ParameterTypeEnum.PFLOAT.equals(javaType)) {
            return DECIMAL_TO_FLOAT_INTEGRALPART_LIMIT;
        }
        return null;
    }

    /**
     * @param javaType
     *            The javaType.
     * @return The maximum length.
     */
    public static String getIntegerLengthLimit(ParameterTypeEnum javaType) {
        if (ParameterTypeEnum.FLOAT.equals(javaType)
                || ParameterTypeEnum.PFLOAT.equals(javaType)) {
            return INTEGER_TO_FLOAT_MAX_LENGTH;
        } else if (ParameterTypeEnum.DOUBLE.equals(javaType)
                || ParameterTypeEnum.PDOUBLE.equals(javaType)) {
            return INTEGER_TO_DOUBLE_MAX_LENGTH;
        } else if (ParameterTypeEnum.BYTE.equals(javaType)
                || ParameterTypeEnum.PBYTE.equals(javaType)) {
            return INTEGER_TO_BYTE_MAX_LENGTH;
        } else if (ParameterTypeEnum.SHORT.equals(javaType)
                || ParameterTypeEnum.PSHORT.equals(javaType)) {
            return INTEGER_TO_SHORT_MAX_LENGTH;
        } else if (ParameterTypeEnum.INTEGER.equals(javaType)
                || ParameterTypeEnum.PINTEGER.equals(javaType)) {
            return INTEGER_TO_INTEGER_MAX_LENGTH;
        } else if (ParameterTypeEnum.CHAR.equals(javaType)
                || ParameterTypeEnum.PCHAR.equals(javaType)) {
            return INTEGER_TO_CHAR_MAX_LENGTH;
        } else if (ParameterTypeEnum.LONG.equals(javaType)
                || ParameterTypeEnum.PLONG.equals(javaType)) {
            return INTEGER_TO_LONG_MAX_LENGTH;
        }
        return null;
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

    /**
     * @param javaType
     *            The javaType.
     * @return If the java type mapped to a decimal might cause a loss
     *         precision.
     */
    public static boolean causesJavaInDecimalLossOP(ParameterTypeEnum javaType) {
        if (javaDecimalInLossOP.contains(javaType)) {
            return true;
        }
        return false;
    }

    /**
     * @param javaType
     *            The javaType.
     * @return If the java type mapped to a String might cause a loss precision.
     */
    public static boolean causesJavaInStringLossOP(ParameterTypeEnum javaType) {
        if (javaStringInLossOP.contains(javaType)) {
            return true;
        }
        return false;
    }

    /**
     * @param javaType
     *            The javaType.
     * @return If the java type mapped to a decimal might cause a loss
     *         precision.
     */
    public static boolean causesJavaOutDecimalLossOP(ParameterTypeEnum javaType) {
        if (javaDecimalOutLossOP.contains(javaType)) {
            return true;
        }
        return false;
    }

    /**
     * @param javaType
     *            The javaType.
     * @return If the java type mapped to a decimal might cause a loss
     *         precision.
     */
    public static boolean causesJavaOutIntegerLossOP(ParameterTypeEnum javaType) {
        if (javaIntegerOutLossOP.contains(javaType)) {
            return true;
        }
        return false;
    }

    public static BasicType resolveDataType(DataType dataType) {
        return BasicTypeConverterFactory.INSTANCE.getBasicType(dataType);
    }
}
