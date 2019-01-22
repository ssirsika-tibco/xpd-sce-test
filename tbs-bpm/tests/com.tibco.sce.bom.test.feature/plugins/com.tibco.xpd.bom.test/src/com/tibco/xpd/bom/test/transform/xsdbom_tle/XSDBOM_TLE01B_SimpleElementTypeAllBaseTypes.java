/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
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
 * 
 * Tests the simplest form of top level element of type xsd base type.
 * <p>
 * &lt;xsd:element name="testStringData" type="xsd:string"&gt;
 * </p>
 * The schema used in this test contains a top level element for each supported
 * xsd base types <br>
 * </br>
 * <p>
 * <i> Tests 3.1 and 3.2 as described in 'BDS Support for Handling - Studio BOM
 * Import'. </i>
 * </p>
 * 
 * @author rgreen
 */
public class XSDBOM_TLE01B_SimpleElementTypeAllBaseTypes extends
        AbstractTLETest {

    public XSDBOM_TLE01B_SimpleElementTypeAllBaseTypes() {
        super("XSDBOM_TLE01B_SimpleElementTypeAllBaseTypes.xsd");
    }

    @Override
    protected void checkBOMElements(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        checkPrimitiveTypes(packagedElements);

    }

    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Primitive types expected",
                41,
                packagedElements.size());

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
                                "tleStringType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleStringType", null, null, null);

        // xsd:gDay
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleGDayType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleGDayType", null, null, null);

        // xsd:gMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleGMonthType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleGMonthType", null, null, null);

        // xsd:gYear
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleGYearType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleGYearType", null, null, null);

        // xsd:gYearMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleGYearMonthType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleGYearMonthType", null, null, null);

        // xsd:ID
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleIdType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleIdType", null, null, null);

        // xsd: IDREF
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleIdRefType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleIdRefType", null, null, null);

        // xsd:IDREFS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleIdRefsType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleIdRefsType", null, null, null);

        // xsd:Name
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleNameType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleNameType", null, null, null);

        // xsd:NCName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleNCNameType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleNCNameType", null, null, null);

        // xsd:NMTOKEN
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleNMTokenType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleNMTokenType", null, null, null);

        // xsd:NMTOKENS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleNMTokensType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleNMTokensType", null, null, null);

        // xsd:normaizedString
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleNormalizedStringType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleNormalizedStringType", null, null, null);

        // xsd:QName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleQNameType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleQNameType", null, null, null);

        // xsd:ENTITY
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleENTITYType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleENTITYType", null, null, null);

        // xsd:ENTITIES
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleENTITIESType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleENTITIESType", null, null, null);

        // xsd:token
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleTokenType");

        textValidator = new PrimitiveTypeTextValidator(pt);
        textValidator.isValid("tleTokenType", null, null, null);

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
                                "tleByteType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleByteType",
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
                                "tleUnsignedByteType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleUnsignedByteType",
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
                                "tleShortType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleShortType",
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
                                "tleUnsignedShortType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleUnsignedShortType",
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
                                "tleIntType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleIntType",
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
                                "tleIntegerType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleIntegerType",
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
                                "tleLongType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleLongType",
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
                                "tleNegativeIntegerType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleNegativeIntegerType",
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
                                "tleNonNegativeIntegerType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleNonNegativeIntegerType",
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
                                "tlePositiveIntegerType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tlePositiveIntegerType",
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
                                "tleNonPositiveIntegerType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleNonPositiveIntegerType",
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
                                "tleUnsignedIntType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleUnsignedIntType",
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
                                "tleUnsignedLongType");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("tleUnsignedLongType",
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
                                "tleDecimalType");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("tleDecimalType",
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
                                "tleFloatType");

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
        typeSumm.isValid("tleFloatType",
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
                                "tleDoubleType");

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
        typeSumm.isValid("tleDoubleType",
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
                                "tleBooleanType");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
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
                                "tleDateType");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME));

        // xsd:time
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleTimeType");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME));

        // xsd:dateTime
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleDateTimeType");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME));

    }

    private void checkTheRestText(EList<PackageableElement> packagedElements)
            throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeTextValidator typeSumm = null;

        // xsd:anyURI
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleAnyURIType");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME));

        // xsd:duration
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tleDurationType");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME));

    }
}
