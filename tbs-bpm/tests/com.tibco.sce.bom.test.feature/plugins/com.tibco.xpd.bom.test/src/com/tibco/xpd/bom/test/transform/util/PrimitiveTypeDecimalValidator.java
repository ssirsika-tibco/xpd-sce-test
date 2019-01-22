/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.util;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * A class to encapsulate JUnit validation of a UML2 PrimitiveType of type
 * Decimal.<br>
 * </br> Once the classs is initialized with the element to be tested, the
 * isValid method can be called with expected values as its parameters.
 * 
 * @author rgreen
 * 
 */
public class PrimitiveTypeDecimalValidator extends XSDStereotypeValidator {

    private BigDecimal upperLimit;

    private BigDecimal lowerLimit;

    private Boolean lowerInclusive;

    private Boolean upperInclusive;

    private Integer totalDigits;

    private Integer fractionDigits;

    private BigDecimal defaultValue;

    private PrimitiveType primType;

    private EnumerationLiteral subType;

    public PrimitiveTypeDecimalValidator(PrimitiveType pt) {
        super(pt);
        if (pt != null) {
            primType = pt;
            initialize();
        }
    }

    private void initialize() {

        upperInclusive =
                (Boolean) PrimitivesUtil
                        .getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER_INCLUSIVE);
        lowerInclusive =
                (Boolean) PrimitivesUtil
                        .getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER_INCLUSIVE);
        String strUpperLimit =
                (String) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_UPPER);

        if (strUpperLimit != null && strUpperLimit != "") {
            upperLimit = new BigDecimal(strUpperLimit);
        }

        String strLowerLimit =
                (String) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LOWER);
        if (strLowerLimit != null && strLowerLimit != "") {
            lowerLimit = new BigDecimal(strLowerLimit);
        }

        totalDigits =
                (Integer) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_LENGTH);

        fractionDigits =
                (Integer) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_PLACES);

        String strDefaultValue =
                (String) PrimitivesUtil
                        .getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_DEFAULT_VALUE);

        if (strDefaultValue != null && strDefaultValue != "") {
            defaultValue = new BigDecimal(strDefaultValue);
        }

        subType =
                (EnumerationLiteral) PrimitivesUtil
                        .getFacetPropertyValue(primType,
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

    }

    /**
     * @param name
     * @param upperLim
     * @param upperInc
     * @param lowerLim
     * @param lowerInc
     * @param totalDig
     * @param decPlaces
     * @param defVal
     * @param subty
     * @return
     */
    public boolean isValid(String name, BigDecimal upperLim, Boolean upperInc,
            BigDecimal lowerLim, Boolean lowerInc, Integer totalDig,
            Integer decPlaces, BigDecimal defVal, String subty) {

        Assert.assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(primType).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME));

        if (name != null) {
            Assert.assertEquals("Decimal name:", name, primType.getName());
        }
        if (upperLim != null) {
            Assert.assertTrue("Decimal upper limit: ", upperLimit
                    .equals(upperLim));
        }
        if (upperInc != null) {
            Assert.assertTrue("Decimal upperInclusive", upperInclusive
                    .equals(upperInc));
        }
        if (lowerLim != null) {
            Assert.assertTrue("Decimal lower limit: ", lowerLimit
                    .equals(lowerLim));
        }
        if (lowerInc != null) {
            Assert.assertTrue("Decimal lowerInclusive", lowerInclusive
                    .equals(lowerInc));
        }
        if (totalDig != null && totalDig != -1) {
            Assert.assertEquals("Decimal total digits:", totalDig, totalDigits);
        }
        if (decPlaces != null && totalDig != -1) {
            Assert.assertEquals("Decimal fraction digits:",
                    decPlaces,
                    fractionDigits);
        }
        if (defVal != null) {
            Assert.assertTrue("Decimal default value:", defaultValue
                    .equals(defVal));
        }
        if (subty != null) {
            Assert.assertEquals("Decimal subtype", subty, subType.getName());
        }

        return true;

    }

    public Object getUpperLimit() {
        return upperLimit;
    }

    public Object getLowerLimit() {
        return lowerLimit;
    }

    public Object getLowerInclusive() {
        return lowerInclusive;
    }

    public Object getUpperInclusive() {
        return upperInclusive;
    }

    public Object getTotalDigits() {
        return totalDigits;
    }

    public Object getFractionDigits() {
        return fractionDigits;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public PrimitiveType getPrimType() {
        return primType;
    }

}