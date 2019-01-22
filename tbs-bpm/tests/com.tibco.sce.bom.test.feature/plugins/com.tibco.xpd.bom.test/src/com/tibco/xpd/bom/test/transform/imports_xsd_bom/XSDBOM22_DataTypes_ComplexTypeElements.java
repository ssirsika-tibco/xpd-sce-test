/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
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
public class XSDBOM22_DataTypes_ComplexTypeElements extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE = "XSDBOM22_DataTypes_ComplexTypeElements.xsd";

    private ArrayList<Enumeration> allEnums = new ArrayList<Enumeration>();

    private ArrayList<Class> allClazzes = new ArrayList<Class>();

    private ArrayList<Association> allAssociations =
            new ArrayList<Association>();

    private ArrayList<PrimitiveType> allPrimTypes =
            new ArrayList<PrimitiveType>();

    private EList<PackageableElement> allPackagedElements;

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
        allPackagedElements = model.getPackagedElements();

        // Collect all UML packaged elements
        for (PackageableElement pe : allPackagedElements) {
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

        checkClazzes(allPackagedElements);

    }

    private void checkClazzes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of elements expected", 44, packagedElements.size());

        // Perform check for class with text type attributes
        Class class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "CTText");

        checkClassWithTextTypes(class1, packagedElements);

        class1 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "CTSignedInteger");

        checkClassWithSignedIntegerTypes(class1, packagedElements);

    }

    private void checkClassWithSignedIntegerTypes(Class cl,
            EList<PackageableElement> packagedElements) throws Exception {
        Property prop = null;
        PropertySummary propSumm = null;
        PrimitiveType bomType = null;

        // Attribute 1
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr1byte",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        propSumm = new PropertySummary(prop);

        propSumm.isValid("attr1byte",
                null,
                null,
                null,
                null,
                "Signed Integer",
                null);

    }

    private void checkClassWithTextTypes(Class cl,
            EList<PackageableElement> packagedElements) throws Exception {
        Property prop = null;
        PropertySummary propSumm = null;
        PrimitiveType bomType = null;

        // Attribute 1
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr1Text",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr1Text", null, "City", null, null);

        // Attribute 1 typed
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "textType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr1TextType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr1TextType1", bomType, null, 30, "XYZ");

        // Attribute 2
        // //////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr2ID",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr2ID", null, null, null, "[\\i-[:]][\\c-[:]]*");

        // Attribute 2 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "idType1");
        prop = TransformUtil.assertPropertyInClass(cl, "attr2IdType1", bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr2IdType1", bomType, null, 30, "XYZ");

        // Attribute 3
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr3IDREF",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr3IDREF",
                null,
                "defaultIDREF",
                null,
                "[\\i-[:]][\\c-[:]]*");

        // Attribute 3 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "idrefType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr3IdrefType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr3IdrefType1", bomType, null, 30, "XYZ");

        // Attribute 4
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr4IDREFS",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr4IDREFS",
                null,
                "defaultIDREFS",
                null,
                "[\\i-[:]][\\c-[:]]*( [\\i-[:]][\\c-[:]]*)*");

        // Attribute 4 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "idrefsType1");

        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr4IdrefsType1",
                        bomType);

        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr4IdrefsType1", bomType, null, 30, "XYZ");

        // Attribute 5
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr5gDay",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr5gDay",
                null,
                null,
                null,
                "\\-\\-\\-(0[1-9]|[12][0-9]|3[01])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");

        // Attribute 5 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "gDayType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr5gDayType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr5gDayType1", bomType, null, null, "XYZ");

        // Attribute 6
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr6gMonth",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr6gMonth",
                null,
                null,
                null,
                "\\-\\-(0[1-9]|[1][0-2])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");

        // Attribute 6 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "gMonthType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr6gMonthType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr6gMonthType1", bomType, null, null, "XYZ");

        // Attribute 7
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr7gMonthDay",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr7gMonthDay",
                null,
                null,
                null,
                "\\-\\-(0[1-9]|[1][0-2])\\-(0[1-9]|[12][0-9]|3[01])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");

        // Attribute 7 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "gMonthDayType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr7gMonthDayType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr7gMonthDayType1", bomType, null, null, "XYZ");

        // Attribute 8
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr8gYear",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr8gYear",
                null,
                null,
                null,
                "(\\-\\d{4,}|\\d{4,})(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");

        // Attribute 8 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "gYearType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr8gYearType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr8gYearType1", bomType, null, null, "XYZ");

        // Attribute 9
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr9gYearMonth",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr9gYearMonth",
                null,
                null,
                null,
                "(\\-\\d{4,}|\\d{4,})(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))\\-(0[1-9]|[1][0-2])(|Z|[+-](0[0-9]|[1][0-3]):(0[0-9]|[12345][0-9]))");

        // Attribute 9 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "gYearMonthType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr9gYearMonthType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr9gYearMonthType1", bomType, null, null, "XYZ");

        // Attribute 10
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr10language",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr10language",
                null,
                "en",
                null,
                "([a-zA-Z]{2}|[iI]-[a-zA-Z]+|[xX]-[a-zA-Z]{1,8})(-[a-zA-Z]{1,8})*");

        // Attribute 10 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "languageType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr10languageType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr10languageType1", bomType, null, 30, "XYZ");

        // Attribute 11
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr11Name",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr11Name", null, "defaultName", null, "\\i\\c*");

        // Attribute 11 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "nameType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr11nameType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr11nameType1", bomType, null, 30, "XYZ");

        // Attribute 12
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr12ncName",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr12ncName",
                null,
                "defaultNCName",
                null,
                "[\\i-[:]][\\c-[:]]*");

        // Attribute 12 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "ncNameType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr12ncNameType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr12ncNameType1", bomType, null, 30, "XYZ");

        // Attribute 13
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr13nmToken",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr13nmToken",
                null,
                "defaultNMTOKEN",
                null,
                "\\c+");

        // Attribute 13 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "nmTokenType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr13nmTokenType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr13nmTokenType1", bomType, null, 30, "XYZ");

        // Attribute 14
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr14nmTokens",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr14nmTokens",
                null,
                "defaultNMTOKENS",
                null,
                "\\c+( \\c+)*");

        // Attribute 14 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "nmTokensType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr14nmTokensType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr14nmTokensType1", bomType, null, 30, "XYZ");

        // Attribute 15
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr15normalizedString",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr15normalizedString",
                null,
                "defaultNormString",
                null,
                null);

        // Attribute 15 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "normalizedStringType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr15normalizedStringType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr15normalizedStringType1",
                bomType,
                null,
                30,
                "XYZ");

        // Attribute 16
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr16qname",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr16qname",
                null,
                "defaultQName",
                null,
                "([\\i-[:]][\\c-[:]]*:)?[\\i-[:]][\\c-[:]]*");

        // Attribute 16 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "qNameType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr16qnameType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr16qnameType1", bomType, null, 30, "XYZ");

        // Attribute 17
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr17entity",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr17entity",
                null,
                "defaultEntity",
                null,
                "[\\i-[:]][\\c-[:]]*");

        // Attribute 17 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "entityType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr17entityType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr17entityType1", bomType, null, 30, "XYZ");

        // Attribute 18
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr18entities",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr18entities",
                null,
                "defaultEntities",
                null,
                "[\\i-[:]][\\c-[:]]*( [\\i-[:]][\\c-[:]]*)*");

        // Attribute 18 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "entitiesType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr18entitiesType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr18entitiesType1", bomType, null, 30, "XYZ");

        // Attribute 19
        // ///////////////////////////////////////////////////////////////////
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr19token",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr19token",
                null,
                "defaultToken",
                null,
                "[^ \\t\\n\\r]+([ ][^ \\t\\n\\r]+)*");

        // Attribute 19 typed
        // ///////////////////////////////////////////////////////////////////
        bomType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "tokenType1");
        prop =
                TransformUtil.assertPropertyInClass(cl,
                        "attr19tokenType1",
                        bomType);
        propSumm = new PropertySummary(prop);
        propSumm.isValidText("attr19tokenType1", bomType, null, 30, "XYZ");
    }

    private class PropertySummary {

        private Property prop = null;

        private Integer textLength;

        private String defaultValue;

        private String pattern;

        public PropertySummary(Property pty) {
            prop = pty;
            initialize();
        }

        private void initialize() {
            defaultValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) prop
                                    .getType(),
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE,
                                    prop);

            textLength =
                    (Integer) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) prop
                                    .getType(),
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH,
                                    prop);

            pattern =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue((PrimitiveType) prop
                                    .getType(),
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE,
                                    prop);

        }

        /**
         * @param String
         *            name
         * @param Type
         *            type
         * @param String
         *            default value
         * @param Integer
         *            length
         * @param String
         *            pattern
         * @return boolean
         */
        public boolean isValidText(String name, Type type, String defVal,
                Integer len, String patt) {

            // Check name
            if (name != null) {
                assertEquals("Text name:", name, prop.getName());
            }

            // Check type
            if (type != null) {
                assertEquals("Attribute Type:", type, prop.getType());
            }

            if (len != null) {
                assertEquals("Text max length:", len, textLength);
            }

            if (defVal != null) {
                assertEquals("Text default value:", defVal, defaultValue);
            }

            if (patt != null) {
                assertEquals("Text pattern value:", patt, pattern);
            }
            return true;

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
                String subty, Type type) {

            if (type != null) {
                Assert.assertTrue("Property type:", prop.getType() == type);

            } else {
                Type baseType = prop.getType();

                Assert.assertTrue("Base Primitive Type:",
                        PrimitivesUtil
                                .getBasePrimitiveType((PrimitiveType) baseType)
                                .getName()
                                .equals(PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME));
            }

            if (name != null) {
                Assert.assertEquals("Property name:", name, prop.getName());
            }
            // if (upperLim != null) {
            // Assert.assertTrue("Integer upper limit: ", upperLimit
            // .equals(upperLim));
            //
            // }
            // if (lowerLim != null) {
            // Assert.assertTrue("Integer lower limit: ", lowerLimit
            // .equals(lowerLim));
            // }
            // if (totalDig != null) {
            // Assert.assertEquals("Integer total digits:",
            // totalDig,
            // totalDigits);
            // }
            // if (defVal != null) {
            // Assert.assertTrue("Integer default value:", defaultValue
            // .equals(defVal));
            // }
            // if (subty != null) {
            // Assert
            // .assertEquals("Integer subtype", subty, subType
            // .getName());
            // }

            return true;

        }

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
        TextTypeSummary typeSumm = null;
        final String PATTERN_TEST_STRING = "XYZ";

        // xsd:string
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdString");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("Text_XsdString", null, null, null);

        // xsd:string with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdStringWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("Text_XsdStringWR", null, 20, PATTERN_TEST_STRING);

        // xsd:id
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdId");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdId", null, null, null);

        // xsd:id with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdIdWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdIdWR", null, 20, PATTERN_TEST_STRING);

        // xsd:gDay
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGDay");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGDay", null, null, null);

        // xsd:gDay with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGDayWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGDayWR", null, 20, PATTERN_TEST_STRING);

        // xsd:gMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGMonth");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGMonth", null, null, null);

        // xsd:gMonth with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGMonthWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGMonthWR", null, 20, PATTERN_TEST_STRING);

        // xsd:gYear
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGYear");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGYear", null, null, null);

        // xsd:gYear with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGYearWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGYearWR", null, 20, PATTERN_TEST_STRING);

        // xsd:gYearMonth
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGYearMonth");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGYearMonth", null, null, null);

        // xsd:gYearMonth with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdGYearMonthWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdGYearMonthWR", null, 20, PATTERN_TEST_STRING);

        // xsd: IDREF
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdIdRef");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdIDREF", null, null, null);

        // xsd:IDREF with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdIdRefWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdIdRefWR", null, 20, PATTERN_TEST_STRING);

        // xsd:IDREFS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdIdRefs");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdIdRefs", null, null, null);

        // xsd:IDREFS with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdIdRefsWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdIdRefsWR", null, 20, PATTERN_TEST_STRING);

        // xsd:Name
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdName");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdName", null, null, null);

        // xsd:Name with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNameWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNameWR", null, 20, PATTERN_TEST_STRING);

        // xsd:NCName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNCName");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNCName", null, null, null);

        // xsd:NCName with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNCNameWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNCNameWR", null, 30, PATTERN_TEST_STRING);

        // xsd:NMTOKEN
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNMToken");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNMToken", null, null, null);

        // xsd:NMTOKEN with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNMTokenWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNMTokenWR", null, 30, PATTERN_TEST_STRING);

        // xsd:NMTOKENS
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNMTokens");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNMTokens", null, null, null);

        // xsd:NMTOKENS with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNMTokensWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNMTokensWR", null, 30, PATTERN_TEST_STRING);

        // xsd:normaizedString
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNormalizedString");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNormalizedString", null, null, null);

        // xsd:normalizedString with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdNormalizedStringWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdNormalizedStringWR",
                null,
                30,
                PATTERN_TEST_STRING);

        // xsd:QName
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdQName");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdQName", null, null, null);

        // xsd:QName with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdQNameWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdQNameWR", null, 30, PATTERN_TEST_STRING);

        // xsd:ENTITY
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdEntity");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdEntity", null, null, null);

        // xsd:ENTITY with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdEntityWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdEntityWR", null, 30, PATTERN_TEST_STRING);

        // xsd:ENTITIES
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdEntities");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdEntities", null, null, null);

        // xsd:ENTITIES with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdEntitiesWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdEntitiesWR", null, 30, PATTERN_TEST_STRING);

        // xsd:token
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdToken");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdToken", null, null, null);

        // xsd:token with restrictions
        // ///////////////////////////////////////////////////////////////////
        pt =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "TextXsdTokenWR");

        typeSumm = new TextTypeSummary(pt);
        typeSumm.isValid("TextXsdTokenWR", null, 30, PATTERN_TEST_STRING);

    }

    private class TextTypeSummary {

        private Integer textLength;

        private String defaultValue;

        private String pattern;

        private PrimitiveType primType;

        public TextTypeSummary(PrimitiveType pt) {
            if (pt != null) {
                primType = pt;
                initialize();
            }
        }

        private void initialize() {

            defaultValue =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue(primType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_DEFAULT_VALUE);

            textLength =
                    (Integer) PrimitivesUtil.getFacetPropertyValue(primType,
                            PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_LENGTH);

            pattern =
                    (String) PrimitivesUtil
                            .getFacetPropertyValue(primType,
                                    PrimitivesUtil.BOM_PRIMITIVE_FACET_TEXT_PATTERN_VALUE);

        }

        /**
         * @param name
         * @param defVal
         * @param len
         * @param patt
         * @return
         */
        public boolean isValid(String name, String defVal, Integer len,
                String patt) {

            assertTrue("Base Primitive Type:",
                    PrimitivesUtil.getBasePrimitiveType(primType).getName()
                            .equals(PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME));

            if (name != null) {
                assertEquals("Text name:", primType.getName(), name);
            }
            if (len != null) {
                assertEquals("Text max length:", textLength, len);
            }
            if (defVal != null) {
                assertTrue("Text default value:", defaultValue.equals(defVal));
            }
            if (patt != null) {
                assertTrue("Text pattern value:", pattern.equals(patt));
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

    private boolean isDecimalSubTypeFloatingPoint(PrimitiveType pt) {

        boolean ret = false;

        Object objSubType =
                PrimitivesUtil.getFacetPropertyValue(pt,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

        if (objSubType instanceof EnumerationLiteral) {
            EnumerationLiteral enl = (EnumerationLiteral) objSubType;

            if (enl.getName()
                    .equals(PrimitivesUtil.DECIMAL_SUBTYPE_FLOATINGPOINT)) {
                ret = true;
            }
        }

        return ret;
    }

    private boolean isDecimalSubTypeFixedPoint(PrimitiveType pt) {

        boolean ret = false;

        Object objSubType =
                PrimitivesUtil.getFacetPropertyValue(pt,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_DECIMAL_SUBTYPE);

        if (objSubType instanceof EnumerationLiteral) {
            EnumerationLiteral enl = (EnumerationLiteral) objSubType;

            if (enl.getName().equals(PrimitivesUtil.DECIMAL_SUBTYPE_FIXEDPOINT)) {
                ret = true;
            }
        }

        return ret;
    }

}
