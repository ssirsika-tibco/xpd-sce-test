/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
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
public class XSDBOM01A_DataTypes_PrimTypesFromSimpleTypes extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM01A_DataTypes_PrimTypesFromSimpleTypes.xsd";

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
                43,
                allPrimTypes.size());

        checkBooleans(packagedElements);
        checkDateTime(packagedElements);
        checkText(packagedElements);
        checkDecimals(packagedElements);
        checkIntegers(packagedElements);
        checkTheRestText(packagedElements);

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
                                "URI_XsdAnyURI");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_URI_NAME));

        // xsd:duration
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Duration_XsdDuration");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME));

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
                                "Date_XsdDate");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME));

        // xsd:dateTime
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "DateTime_XsdDateTime");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME));

        // xsd:timee
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Time_XsdTime");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME));

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
                                "Boolean_XsdBoolean");

        assertTrue("Base Primitive Type:",
                PrimitivesUtil.getBasePrimitiveType(pt).getName()
                        .equals(PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME));
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
                                "DecimalFixed_XsdDecimal");

        typeSumm = new PrimitiveTypeDecimalValidator(pt);
        typeSumm.isValid("DecimalFixed_XsdDecimal",
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
                                "DecimalFloat_XsdFloat");

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
        typeSumm.isValid("DecimalFloat_XsdFloat",
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
                                "DecimalFloat_XsdDouble");

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
        typeSumm.isValid("DecimalFloat_XsdDouble",
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
                                "IntegerSignedInt_XsdByte");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdByte",
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
                                "IntegerSignedInt_XsdUnsignedByte");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdUnsignedByte",
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
                                "IntegerSignedInt_XsdShort");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdShort",
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
                                "IntegerSignedInt_XsdUnsignedShort");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdUnsignedShort",
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
                                "IntegerSignedInt_XsdInt");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerSignedInt_XsdInt",
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
                                "IntegerFixed_XsdInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdInteger",
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
                                "IntegerFixed_XsdLong");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdLong",
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
                                "IntegerFixed_XsdNegativeInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdNegativeInteger",
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
                                "IntegerFixed_XsdNonNegativeInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdNonNegativeInteger",
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
                                "IntegerFixed_XsdNonPositiveInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdNonPositiveInteger",
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
                                "IntegerFixed_XsdPositiveInteger");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdPositiveInteger",
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
                                "IntegerFixed_XsdUnsignedInt");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdUnsignedInt",
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
                                "IntegerFixed_XsdUnsignedLong");

        typeSumm = new PrimitiveTypeIntegerValidator(pt);

        typeSumm.isValid("IntegerFixed_XsdUnsignedLong",
                null,
                null,
                null,
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

        // xsd:string
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdString");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdString", null, null, null);

        // xsd:id
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdId");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdId", null, null, null);

        // base64Binary
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdBase64Binary");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdBase64Binary", null, null, null);

        // xsd:hexBinary
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdHexBinary");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdHexBinary", null, null, null);

        // xsd:gDay
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGDay");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGDay", null, null, null);

        // xsd:gMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGMonth");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGMonth", null, null, null);

        // xsd:gYear
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGYear");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGYear", null, null, null);

        // xsd:gYearMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdGYearMonth");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdGYearMonth", null, null, null);

        // xsd: IDREF
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdIdRef");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdIdRef", null, null, null);

        // xsd:IDREFS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdIdRefs");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdIdRefs", null, null, null);

        // xsd:Name
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdName");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdName", null, null, null);

        // xsd:NCName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNCName");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNCName", null, null, null);

        // xsd:NMTOKEN
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNMToken");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNMToken", null, null, null);

        // xsd:NMTOKENS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNMTokens");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNMTokens", null, null, null);

        // xsd:normaizedString
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdNormalizedString");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdNormalizedString", null, null, null);

        // xsd:QName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdQName");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdQName", null, null, null);

        // xsd:ENTITY
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdEntity");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdEntity", null, null, null);

        // xsd:ENTITIES
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdEntities");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdEntities", null, null, null);

        // xsd:token
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "Text_XsdToken");

        typeSumm = new PrimitiveTypeTextValidator(pt);
        typeSumm.isValid("Text_XsdToken", null, null, null);

    }

}
