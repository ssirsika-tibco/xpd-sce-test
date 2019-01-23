/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeDecimalValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeIntegerValidator;
import com.tibco.xpd.bom.test.transform.util.PrimitiveTypeTextValidator;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * <p>
 * <i>Created: 29 Feb 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM01B_DataTypes_PrimTypesFromSimpleTypesWR extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM01B_DataTypes_PrimTypesFromSimpleTypesWR.xsd";

    private ArrayList<Enumeration> allEnums = new ArrayList<Enumeration>();

    private ArrayList<Class> allClazzes = new ArrayList<Class>();

    private ArrayList<Association> allAssociations =
            new ArrayList<Association>();

    private ArrayList<PrimitiveType> allPrimTypes =
            new ArrayList<PrimitiveType>();

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder";

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;

        modelFileNames.add(MODEL_FILE);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        IFile modelFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
        OawXSDResource resource =
                new OawXSDResource(URI.createURI(modelFile.getFullPath()
                        .toPortableString()));
        resource.load(Collections.EMPTY_MAP);
        XSDSchema schema = resource.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), schema.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        IPath outputBOMPath =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputBOMPath);

        List<IStatus> errors = getErrors(result);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }

        // check resulting bom file is correct
        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        checkBOMElements(model);

        // exportTest();
    }

    private void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        // Collect all UML packaged elements
        for (PackageableElement pe : packagedElements) {
            if (pe instanceof Class) {
                allClazzes.add((Class) pe);
            } else if (pe instanceof Association) {
                allAssociations.add((Association) pe);
            } else if (pe instanceof Enumeration) {
                allEnums.add((Enumeration) pe);
            } else if (pe instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) pe);
            }
        }

        checkPrimitiveTypes(packagedElements);

    }

    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Primitive types expected",
                47,
                allPrimTypes.size());

        checkText(packagedElements);
        checkDecimals(packagedElements);
        checkIntegers(packagedElements);

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

        // xsd:decimal with restrictions 1.
        // upper and lower values are both Inclusive
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFixed_XsdDecimalWR1incl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFixed_XsdDecimalWR1incl",
                new BigDecimal("12345678901234567890"),
                true,
                new BigDecimal("-12345678901234567890"),
                true,
                20,
                5,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

        // xsd:decimal with restrictions 2.
        // upper and lower values are both Exclusive
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFixed_XsdDecimalWR2excl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFixed_XsdDecimalWR2excl",
                new BigDecimal("12345678901234567890"),
                false,
                new BigDecimal("-12345678901234567890"),
                false,
                20,
                5,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

        // xsd:decimal with restrictions 3.
        // min => Inclusive, max => Exclusive
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFixed_XsdDecimalWR3inclexcl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFixed_XsdDecimalWR3inclexcl",
                new BigDecimal("12345678901234567890"),
                false,
                new BigDecimal("-12345678901234567890"),
                true,
                20,
                5,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

        // xsd:decimal with restrictions 4.
        // min => Exclusive, max => Inclusive
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFixed_XsdDecimalWR4exclincl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFixed_XsdDecimalWR4exclincl",
                new BigDecimal("12345678901234567890"),
                true,
                new BigDecimal("-12345678901234567890"),
                false,
                20,
                5,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT);

        // xsd:float with restrictions 1.
        // Both upper and lower INCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFloat_XsdFloatWR1incl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFloat_XsdFloatWR1incl",
                new BigDecimal("1234567890.123456789"),
                true,
                new BigDecimal("-1234567890.123456789"),
                true,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

        // xsd:float with restrictions 2.
        // Both upper and lower EXCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFloat_XsdFloatWR2excl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFloat_XsdFloatWR2excl",
                new BigDecimal("1234567890.123456789"),
                false,
                new BigDecimal("-1234567890.123456789"),
                false,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

        // xsd:float with restrictions 3.
        // upper => EXCLUSIVE and lower => INCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFloat_XsdFloatWR3inclexcl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFloat_XsdFloatWR3inclexcl",
                new BigDecimal("1234567890.123456789"),
                false,
                new BigDecimal("-1234567890.123456789"),
                true,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

        // xsd:float with restrictions 4.
        // upper => INCLUSIVE and lower => EXCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFloat_XsdFloatWR4exclincl");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFloat_XsdFloatWR4exclincl",
                new BigDecimal("1234567890.123456789"),
                true,
                new BigDecimal("-1234567890.123456789"),
                false,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

        // xsd:double with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DecimalFloat_XsdDoubleWR");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFloat_XsdDoubleWR",
                new BigDecimal("1234567890.123456789"),
                true,
                new BigDecimal("-1234567890.123456789"),
                true,
                null,
                null,
                null,
                PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT);

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
     * Check all XSD data types that end up as BOM Integers of subtype unsigned
     * int
     * 
     * @param packagedElements
     * @throws Exception
     */
    private void checkIntegersSignedInt(
            EList<PackageableElement> packagedElements) throws Exception {
        PrimitiveType pt = null;
        PrimitiveTypeIntegerValidator typeSumm = null;

        // xsd:byte with restrictions 1
        // Upper and lower limits INCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdByteWR1incl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdByteWR1incl",
                new BigInteger("126"),
                new BigInteger("-127"),
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:byte with restrictions 2
        // Upper and lower limits EXCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdByteWR2excl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdByteWR2excl",
                new BigInteger("125"),
                new BigInteger("-126"),
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:byte with restrictions 3
        // Upper => EXCLUSIVE, Lower => INCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdByteWR3inclexcl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdByteWR3inclexcl",
                new BigInteger("125"),
                new BigInteger("-127"),
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:byte with restrictions 4
        // Upper => INCLUSIVE, Lower => EXCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdByteWR4exclincl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdByteWR4exclincl",
                new BigInteger("126"),
                new BigInteger("-126"),
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:unsignedByte with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdUnsignedByteWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdUnsignedByteWR",
                new BigInteger("254"),
                new BigInteger("1"),
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:unsignedShort with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdUnsignedShortWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdUnsignedShortWR",
                new BigInteger("65534"),
                new BigInteger("1"),
                null,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_SIGNEDINTEGER);

        // xsd:int with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerSignedInt_XsdIntWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdIntWR",
                new BigInteger("2147483646"),
                new BigInteger("-2147483647"),
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

        // xsd:integer with restrictions 1.
        // Upper and Lower limits both INCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdIntegerWR1incl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdIntegerWR1incl",
                new BigInteger("999999"),
                new BigInteger("-99999"),
                5,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:integer with restrictions 2.
        // Upper and Lower limits both EXCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdIntegerWR2excl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdIntegerWR2excl",
                new BigInteger("999998"),
                new BigInteger("-99998"),
                5,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:integer with restrictions 3.
        // Upper => EXCLUSIVE, Lower => INCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdIntegerWR3excl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdIntegerWR3excl",
                new BigInteger("999998"),
                new BigInteger("-99999"),
                5,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:integer with restrictions 4.
        // Upper => INCLUSIVE, Lower => EXCLUSIVE
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdIntegerWR4excl");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdIntegerWR4excl",
                new BigInteger("999999"),
                new BigInteger("-99998"),
                5,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:long with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdLongWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdLongWR",
                new BigInteger("9223372036854775806"),
                new BigInteger("-9223372036854775807"),
                20,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:negativeInteger with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdNegativeIntegerWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdNegativeIntegerWR",
                new BigInteger("-2"),
                new BigInteger("-9223372036854775807999999"),
                30,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:nonNegativeInteger with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdNonNegativeIntegerWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdNonNegativeIntegerWR",
                new BigInteger("9"),
                new BigInteger("1"),
                20,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:nonPositiveInteger with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdNonPositiveIntegerWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdNonPositiveIntegerWR",
                new BigInteger("-1"),
                new BigInteger("-2"),
                20,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:positiveIntegerWR
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdPositiveIntegerWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdPositiveIntegerWR",
                new BigInteger("999"),
                new BigInteger("2"),
                20,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:unsignedInt with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdUnsignedIntWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdUnsignedIntWR",
                new BigInteger("4294967294"),
                new BigInteger("1"),
                20,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

        // xsd:unsignedLong with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "IntegerFixed_XsdUnsignedLongWR");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdUnsignedLongWR",
                new BigInteger("18446744073709551614"),
                new BigInteger("1"),
                20,
                null,
                PrimitivesUtil.INTEGER_SUBTYPE_FIXEDLENGTH);

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
        PrimitiveTypeTextValidator typeSumm = null;
        final String PATTERN_TEST_STRING = "XYZ";
        final int TEXT_LEN_TEST_DEFAULT = 30;

        // xsd:string with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdStringWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdStringWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:id with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdIdWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdIdWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:gDay with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGDayWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGDayWR", null, null, PATTERN_TEST_STRING);

        // xsd:gMonth with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGMonthWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGMonthWR", null, null, PATTERN_TEST_STRING);

        // xsd:gMonthDay with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGMonthDayWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGMonthDayWR", null, null, PATTERN_TEST_STRING);

        // xsd:gYear with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGYearWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGYearWR", null, null, PATTERN_TEST_STRING);

        // xsd:gYearMonth with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGYearMonthWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGYearMonthWR",
                null,
                null,
                PATTERN_TEST_STRING);

        // xsd:IDREF with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdIdRefWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdIdRefWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:IDREFS with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdIdRefsWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdIdRefsWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:Language with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdLanguageWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdLanguageWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:Name with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNameWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNameWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:NCName with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNCNameWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNCNameWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:NMTOKEN with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNMTokenWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNMTokenWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:NMTOKENS with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNMTokensWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNMTokensWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:normalizedString with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNormalizedStringWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNormalizedStringWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:QName with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdQNameWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdQNameWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:ENTITY with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdEntityWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdEntityWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:ENTITIES with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdEntitiesWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdEntitiesWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

        // xsd:token with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdTokenWR");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdTokenWR",
                null,
                TEXT_LEN_TEST_DEFAULT,
                PATTERN_TEST_STRING);

    }

}
