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
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM11_CheckDifferentTypesImportedTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM11_CheckDifferentTypesImportedTransformationTest.xsd";

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
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        goldFileNames.add(MODEL_FILE);

        super.setUp();
    }

    @Override
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
        // check that all attributes have the type stereotype value filled
        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);

        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);

        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        checkBOMElements(model);

        exportTest();

    }

    /**
     * @param model
     * @throws Exception
     */
    private void checkBOMElements(Model model) throws Exception {
        boolean isXSDNotationProfileApplied =
                TransformHelper.isXSDNotationProfileApplied(model);

        assertTrue("Check XSD profile is applied to Model",
                isXSDNotationProfileApplied);

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Number of packaged elements in model", 1, packagedElements.size()); //$NON-NLS-1$
        assertTrue("Packaged Element is a Class",
                packagedElements.get(0) instanceof Class);

        Class newComplexTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "tmpItem"); //$NON-NLS-1$

        EList<Property> ownedAttributes =
                newComplexTypeCls.getOwnedAttributes();

        assertEquals("Check number of attributes:", ownedAttributes.size(), 43);

        // check attributes
        // TransformUtil.assertAttributeSize(newComplexTypeCls, 43);

        // Go through all the attributes and check they have the XSD sterotype
        // applied
        for (Property prop : ownedAttributes) {

            EList<Stereotype> appliedStereotypes = prop.getAppliedStereotypes();
            boolean found = false;
            Stereotype stereo = null;

            for (Stereotype stereotype : appliedStereotypes) {
                if (stereotype.getName()
                        .equals(XsdStereotypeUtils.XSD_BASED_PROPERTY)) {
                    found = true;
                    stereo = stereotype;
                    break;
                }
            }

            assertTrue("Check XsdBasedProperty stereotype is applied", found);

            Object value =
                    prop.getValue(stereo, XsdStereotypeUtils.XSD_PROPERTY_TYPE);

            assertNotNull("Check xsdType is not null", value);

            assertTrue("Check xsdType is a String", value instanceof String);

            String xsdType = (String) value;

            assertTrue("Check xsdType is not empty",
                    xsdType.trim().length() > 0);

        }

        /*
         * TransformUtil.assertAttributeInClass(newComplexTypeCls
         * .getAllAttributes(), "anySimpleTypeX",
         * PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
         */
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "anyURIX",
                PrimitivesUtil.BOM_PRIMITIVE_URI_NAME);
        // TransformUtil.assertAttributeInClass(ownedAttributes,
        // "base64BinaryX",
        // PrimitivesUtil.BOM_PRIMITIVE_ATTACHMENT_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "booleanX",
                PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "byteX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "byteX",
                "integerSubType",
                "signedInteger");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "byteX",
                "-128");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "byteX",
                "127");
        TransformUtil.assertIntegerLengthNotSet(ownedAttributes, "byteX");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "dateX",
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "dateTimeX",
                PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "decimalX",
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "decimalX",
                "decimalSubType",
                "fixedPoint");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "doubleX",
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "doubleX",
                "decimalSubType",
                "floatingPoint");
        TransformUtil.assertPropertyDecimalLower(ownedAttributes,
                "doubleX",
                "-1.7976931348623157E308");
        TransformUtil.assertPropertyDecimalUpper(ownedAttributes,
                "doubleX",
                "1.7976931348623157E308");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "durationX",
                PrimitivesUtil.BOM_PRIMITIVE_DURATION_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "entitiesX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "entitiesX",
                "[\\i-[:]][\\c-[:]]*( [\\i-[:]][\\c-[:]]*)*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "entityX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "entityX",
                "[\\i-[:]][\\c-[:]]*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "floatX",
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "floatX",
                "decimalSubType",
                "floatingPoint");
        TransformUtil.assertPropertyDecimalLower(ownedAttributes,
                "floatX",
                "-3.4028235E38");
        TransformUtil.assertPropertyDecimalUpper(ownedAttributes,
                "floatX",
                "3.4028235E38");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "gdayX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "gdayX",
                        "\\-\\-\\-(0[1-9]|[12][0-9]|3[01])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "gmonthX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "gmonthX",
                        "\\-\\-(0[1-9]|[1][0-2])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "gmonthDayX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "gmonthDayX",
                        "\\-\\-(0[1-9]|[1][0-2])\\-(0[1-9]|[12][0-9]|3[01])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "gyearX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "gyearX",
                        "(\\-\\d{4,}|\\d{4,})(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "gyearMonthX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "gyearMonthX",
                        "(\\-\\d{4,}|\\d{4,})(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))\\-(0[1-9]|[1][0-2])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "hexBinaryX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "hexBinaryX",
                "([0-9a-fA-F][0-9a-fA-F])*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "base64BinaryX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "base64BinaryX",
                        "(([A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/] ?)*(([A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[A-Za-z0-9+/])|([A-Za-z0-9+/] ?[A-Za-z0-9+/] ?[AEIMQUYcgkosw048] ?=)|([A-Za-z0-9+/] ?[AQgw] ?= ?=)))?");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "idX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "idX",
                "[\\i-[:]][\\c-[:]]*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "idhrefX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "idhrefX",
                "[\\i-[:]][\\c-[:]]*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "idhrefsX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "idhrefsX",
                "[\\i-[:]][\\c-[:]]*( [\\i-[:]][\\c-[:]]*)*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "intX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "intX",
                "integerSubType",
                "signedInteger");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "integerX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "integerX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "languageX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes,
                        "languageX",
                        "([a-zA-Z]{2}|[iI]-[a-zA-Z]+|[xX]-[a-zA-Z]{1,8})(-[a-zA-Z]{1,8})*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "longX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "longX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "longX",
                "-9223372036854775808");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "longX",
                "9223372036854775807");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "nameX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes, "nameX", "\\i\\c*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "ncNameX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "ncNameX",
                "[\\i-[:]][\\c-[:]]*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "negativeIntegerX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "negativeIntegerX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "negativeIntegerX",
                "-1");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "nmTOKENX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil
                .assertPropertyPattern(ownedAttributes, "nmTOKENX", "\\c+");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "nmTOKENSX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "nmTOKENSX",
                "\\c+( \\c+)*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "nonNegativeIntegerX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "nonNegativeIntegerX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "nonNegativeIntegerX",
                "0");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "nonPositiveIntegerX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "nonPositiveIntegerX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "nonPositiveIntegerX",
                "0");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "normalizedStringX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "normalizedStringX",
                "[^\t\n\r]*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "positiveIntegerX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "positiveIntegerX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "positiveIntegerX",
                "1");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "qnameX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "qnameX",
                "([\\i-[:]][\\c-[:]]*:)?[\\i-[:]][\\c-[:]]*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "shortX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "shortX",
                "integerSubType",
                "signedInteger");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "shortX",
                "-32768");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "shortX",
                "32767");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "stringX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "timeX",
                PrimitivesUtil.BOM_PRIMITIVE_TIME_NAME);
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "tokenX",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertPropertyPattern(ownedAttributes,
                "tokenX",
                "[^ \\t\\n\\r]+([ ][^ \\t\\n\\r]+)*");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "unsignedByteX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "unsignedByteX",
                "integerSubType",
                "signedInteger");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "unsignedByteX",
                "0");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "unsignedByteX",
                "255");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "unsignedIntX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "unsignedIntX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "unsignedIntX",
                "0");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "unsignedIntX",
                "4294967295");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "unsignedLongX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "unsignedLongX",
                "integerSubType",
                "fixedLength");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "unsignedLongX",
                "0");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "unsignedLongX",
                "18446744073709551615");
        TransformUtil.assertAttributeInClass(ownedAttributes,
                "unsignedShortX",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertySubtype(ownedAttributes,
                "unsignedShortX",
                "integerSubType",
                "signedInteger");
        TransformUtil.assertPropertyIntegerLower(ownedAttributes,
                "unsignedShortX",
                "0");
        TransformUtil.assertPropertyIntegerUpper(ownedAttributes,
                "unsignedShortX",
                "65535");

    }

}
