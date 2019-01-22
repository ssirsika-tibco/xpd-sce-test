/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.util;

import junit.framework.Assert;

import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * A class to encapsulate JUnit validation of a UML2 PrimitiveType of type Text.<br>
 * </br> Once the classs is initialized with the element to be tested, the
 * isValid method can be called with expected values as its parameters.
 * 
 * @author rgreen
 * 
 */
public class PrimitiveTypeTextValidator extends XSDStereotypeValidator {

    private Integer textLength;

    private String defaultValue;

    private String pattern;

    private PrimitiveType primType;

    public PrimitiveTypeTextValidator(PrimitiveType pt) {
        super(pt);
        if (pt != null) {
            primType = pt;
            initialize();
        }
    }

    private void initialize() {

        defaultValue =
                (String) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE);

        textLength =
                (Integer) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

        pattern =
                (String) PrimitivesUtil.getFacetPropertyValue(primType,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);

    }

    /**
     * @param name
     * @param defVal
     * @param len
     * @param patt
     * @return
     */
    public boolean isValid(String name, String defVal, Integer len, String patt) {

        Assert.assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(primType).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));

        if (name != null) {
            Assert.assertEquals("Text name:", name, primType.getName());
        }
        if (len != null) {
            Assert.assertEquals("Text max length:", len, textLength);
        }
        if (defVal != null) {
            Assert.assertTrue("Text default value:", defaultValue
                    .equals(defVal));
        }
        if (patt != null) {
            Assert.assertTrue("Text pattern value:", pattern.equals(patt));
        }
        return true;

    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public PrimitiveType getPrimType() {
        return primType;
    }

    public Integer getTextLength() {
        return textLength;
    }

    public String getPattern() {
        return pattern;
    }

}