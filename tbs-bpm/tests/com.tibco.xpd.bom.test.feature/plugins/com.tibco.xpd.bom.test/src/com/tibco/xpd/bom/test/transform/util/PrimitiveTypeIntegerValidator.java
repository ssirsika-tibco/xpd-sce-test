/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.util;

import java.math.BigInteger;

import junit.framework.Assert;

import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * A class to encapsulate JUnit validation of a UML2 PrimitiveType of type
 * Integer.<br>
 * </br> Once the classs is initialized with the element to be tested, the
 * isValid method can be called with expected values as its parameters.
 * 
 * @author rgreen
 * 
 */
public class PrimitiveTypeIntegerValidator extends XSDStereotypeValidator {

    private BigInteger upperLimit;

    private BigInteger lowerLimit;

    private Integer totalDigits;

    private BigInteger defaultValue;

    private PrimitiveType primType;

    private EnumerationLiteral subType;

    public PrimitiveTypeIntegerValidator(PrimitiveType pt) {
        super(pt);
        if (pt != null) {
            primType = pt;
            initialize();
        }
    }

    private void initialize() {

        String strUpperLimit =
                (String) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);

        if (strUpperLimit != null && strUpperLimit != "") {
            upperLimit = new BigInteger(strUpperLimit);
        }

        String strLowerLimit =
                (String) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);
        if (strLowerLimit != null && strLowerLimit != "") {
            lowerLimit = new BigInteger(strLowerLimit);
        }

        totalDigits =
                (Integer) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LENGTH);

        String strDefaultValue =
                (String) PrimitivesUtil
                        .getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_DEFAULT_VALUE);

        if (strDefaultValue != null && strDefaultValue != "") {
            defaultValue = new BigInteger(strDefaultValue);
        }

        subType =
                (EnumerationLiteral) PrimitivesUtil
                        .getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_SUBTYPE);

    }

    /**
     * @param String
     *            name
     * @param BigInteger
     *            upper limit
     * @param BigInteger
     *            lower Limit
     * @param Integer
     *            number length
     * @param BigInteger
     *            default value
     * @param String
     *            subtype
     * @return Boolean
     */
    public boolean isValid(String name, BigInteger upperLim,
            BigInteger lowerLim, Integer totalDig, BigInteger defVal,
            String subty) {

        Assert.assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(primType).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME));

        if (name != null) {
            Assert.assertEquals("Integer name:", name, primType.getName());
        }
        if (upperLim != null) {
            Assert.assertTrue("Integer upper limit: ", upperLimit
                    .equals(upperLim));
        }
        if (lowerLim != null) {
            Assert.assertTrue("Integer lower limit: ", lowerLimit
                    .equals(lowerLim));
        }
        if (totalDig != null) {
            Assert.assertEquals("Integer total digits:", totalDig, totalDigits);
        }
        if (defVal != null) {
            Assert.assertTrue("Integer default value:", defaultValue
                    .equals(defVal));
        }
        if (subty != null) {
            Assert.assertEquals("Integer subtype", subty, subType.getName());
        }

        return true;

    }

    public Object getUpperLimit() {
        return upperLimit;
    }

    public Object getLowerLimit() {
        return lowerLimit;
    }

    public Object getTotalDigits() {
        return totalDigits;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public PrimitiveType getPrimType() {
        return primType;
    }

}