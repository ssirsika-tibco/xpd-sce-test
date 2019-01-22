/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.xsdbom_tle;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;

import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeDecimalValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeIntegerValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

/**
 * Tests top-level attributes.
 * <p>
 * Tests 3.7 as described in 'BDS Support for Handling - Studio BOM Import'.
 * </p>
 * 
 * @author njpatel
 * 
 */
public class XSDBOM_TLE06_TopLevelAttributes extends AbstractTLETest {

    public XSDBOM_TLE06_TopLevelAttributes() {
        super("XSDBOM_TLE06_TopLevelAttributes.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        /*
         * Expecting 41 primitive types
         */
        assertEquals("Number of Primitive types", 41, packagedElements.size());

        checkText(packagedElements);
        checkIntegers(packagedElements);
        checkDecimals(packagedElements);
        checkBooleans(packagedElements);
        checkDateTime(packagedElements);
        checkTheRestText(packagedElements);

    }

    /**
     * Check all XSD data types that end up as BOM Text
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkText(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeTextValidator textValidator = null;

        // xsd:string
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrString");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrString", null, null, null);

        // xsd:gDay
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrGDay");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrGDay", null, null, null);

        // xsd:gMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrGMonth");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrGMonth", null, null, null);

        // xsd:gYear
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrGYear");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrGYear", null, null, null);

        // xsd:gYearMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrGYearMonth");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrGYearMonth", null, null, null);

        // xsd:ID
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrId");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrId", null, null, null);

        // xsd: IDREF
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrIdRef");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrIdRef", null, null, null);

        // xsd:IDREFS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrIdRefs");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrIdRefs", null, null, null);

        // xsd:Name
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrName");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrName", null, null, null);

        // xsd:NCName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNCName");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrNCName", null, null, null);

        // xsd:NMTOKEN
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNMToken");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrNMToken", null, null, null);

        // xsd:NMTOKENS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNMTokens");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrNMTokens", null, null, null);

        // xsd:normaizedString
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNormalizedString");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrNormalizedString", null, null, null);

        // xsd:QName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrQName");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrQName", null, null, null);

        // xsd:ENTITY
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrENTITY");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrENTITY", null, null, null);

        // xsd:ENTITIES
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrENTITIES");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrENTITIES", null, null, null);

        // xsd:token
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrToken");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("attrToken", null, null, null);

    }

    /**
     * 
     * Check all XSD data types that end up as BOM Integers
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkIntegers(EList<PackageableElement> packagedElements)
            throws Exception {
        checkIntegersFixedLen(packagedElements);
        checkIntegersSignedInt(packagedElements);
    }

    /**
     * Check all XSD data types that end up as BOM Integers of subtype signed
     * int
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkIntegersSignedInt(
            EList<PackageableElement> packagedElements) throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeIntegerValidator typeSumm = null;

        // xsd:byte
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrByte");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrByte",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:unsignedByte
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrUnsignedByte");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrUnsignedByte",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:short
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrShort");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrShort",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:unsignedShort
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrUnsignedShort");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrUnsignedShort",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:int
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrInt");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrInt",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

    }

    /**
     * Check all XSD data types that end up as BOM Integers of subtype Fixed
     * Length
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkIntegersFixedLen(
            EList<PackageableElement> packagedElements) throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeIntegerValidator typeSumm = null;

        // xsd:integer
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrInteger",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:long
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrLong");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrLong",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:negativeInteger
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNegativeInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrNegativeInteger",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:nonNegativeInteger
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNonNegativeInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrNonNegativeInteger",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:positiveInteger
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrPositiveInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrPositiveInteger",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:nonPositiveInteger
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrNonPositiveInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrNonPositiveInteger",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:unsignedInt
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrUnsignedInt");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrUnsignedInt",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd;unsignedLong
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrUnsignedLong");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("attrUnsignedLong",
                null,
                null,
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

    }

    /**
     * 
     * Check all XSD data types that end up as BOM type Decimal
     * 
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkDecimals(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeDecimalValidator typeSumm = null;

        // xsd:decimal
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrDecimal");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("attrDecimal",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

        // xsd:float
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrFloat");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        /*
         * TODO: test default restrictions
         */
        // typeSumm.isValid("DecimalFloatXsdFloat",
        // new BigDecimal("340.28235E+36"),
        // true,
        // new BigDecimal("-1.4E-45"),
        // true,
        // null,
        // null,
        // null,
        // PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);
        typeSumm.isValid("attrFloat",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

        /*
         * Need to insert tests for checking INFINITY and NaN. We need a desgin
         * decision on how to represent infinity and Nan in the BOM UI. Until
         * then we will ignore testing the imported types with INF and Nan set.
         */

        // xsd:double
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrDouble");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        /*
         * TODO: test default restrictions
         */
        // typeSumm.isValid("DecimalFloatXsdDouble",
        // new BigDecimal("179.76931348623157E+306"),
        // true,
        // new BigDecimal("4.9E-324"),
        // true,
        // null,
        // null,
        // null,
        // PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);
        typeSumm.isValid("attrDouble",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

    }

    /**
     * 
     * Check all XSD data types that end up as BOM Boolean
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkBooleans(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;

        // xsd:boolean
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrBoolean");

        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pt).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME));
    }

    /**
     * 
     * Check all XSD data types that end up as BOM type Date, DateTime and Time
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkDateTime(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;

        // xsd:date
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrDate");

        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pt).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME));

        // xsd:time
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrTime");

        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pt).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME));

        // xsd:dateTime
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrDateTime");

        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pt).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME));

    }

    private void checkTheRestText(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;

        // xsd:anyURI
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrAnyURI");

        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pt).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME));

        // xsd:duration
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "attrDuration");

        assertTrue("Base Primitive Type:", PrimitivesUtil
                .getBasePrimitiveType(pt).getName()
                .equals(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME));

    }

}
