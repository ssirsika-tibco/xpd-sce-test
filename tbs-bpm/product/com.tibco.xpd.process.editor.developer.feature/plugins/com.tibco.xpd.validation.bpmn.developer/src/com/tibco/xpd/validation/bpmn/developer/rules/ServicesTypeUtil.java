/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.bpmn.developer.rules;

import com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConverterFactory;
import com.tibco.xpd.implementer.script.BaseTypeUtil;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataType;

/**
 * @author nwilson
 */
public abstract class ServicesTypeUtil extends BaseTypeUtil {
    /**
     * Constructor.
     */
    public ServicesTypeUtil() {
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
}
