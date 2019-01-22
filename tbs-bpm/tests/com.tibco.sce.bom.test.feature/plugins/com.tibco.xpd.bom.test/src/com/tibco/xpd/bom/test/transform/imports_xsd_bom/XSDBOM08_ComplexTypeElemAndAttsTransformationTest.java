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
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
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
public class XSDBOM08_ComplexTypeElemAndAttsTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM08_ComplexTypeElemAndAttsTransformationTest.xsd";

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

        exportTest(false);

    }

    private void checkBOMElements(Model model) throws Exception {
        // For this test we should have a single Class named Concept1
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 13, packagedElements.size()); //$NON-NLS-1$

        // check classes
        Class concept1Cls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "concept1"); //$NON-NLS-1$
        Class helpCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "Help"); //$NON-NLS-1$

        Class helpRequestCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "HelpRequest"); //$NON-NLS-1$

        Class itemPriceLookupCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ItemPriceLookup"); //$NON-NLS-1$

        Class typesTestComplexCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "TypesTestComplexType"); //$NON-NLS-1$

        // check attributes
        TransformUtil.assertAttributeSize(concept1Cls, 2);
        TransformUtil.assertAttributeSize(helpCls, 4);
        TransformUtil.assertAttributeSize(itemPriceLookupCls, 9);
        TransformUtil.assertAttributeSize(typesTestComplexCls, 6);

        Property prop1 =
                TransformUtil.assertAttributeInClass(concept1Cls
                        .getAllAttributes(),
                        "id",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertyMultiplicity(prop1, 1, 1);

        Property prop2 =
                TransformUtil.assertAttributeInClass(concept1Cls
                        .getAllAttributes(),
                        "id2",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertPropertyMultiplicity(prop2, 1, 1);

        prop1 =
                TransformUtil
                        .assertAttributeInClass(helpCls.getAllAttributes(),
                                "request",
                                "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop1, 1, -1);

        prop2 =
                TransformUtil
                        .assertAttributeInClass(helpCls.getAllAttributes(),
                                "request2",
                                "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop2, 1, 1);

        Property prop3 =
                TransformUtil
                        .assertAttributeInClass(helpCls.getAllAttributes(),
                                "request3",
                                "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop3, 0, -1);

        Property prop4 =
                TransformUtil
                        .assertAttributeInClass(helpCls.getAllAttributes(),
                                "request4",
                                "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop4, 2, -1);

        prop1 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(), "request", "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop1, 1, -1);

        prop2 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(), "request2", "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop2, 1, 1);

        prop3 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(), "request3", "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop3, 0, -1);

        prop4 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(), "request4", "HelpRequest");
        TransformUtil.assertPropertyMultiplicity(prop4, 2, -1);

        Property prop5 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(),
                        "uriWithDefault",
                        PrimitivesUtil.BOM_PRIMITIVE_URI_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                prop5,
                "http://www.tibco.com",
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);

        Property prop6 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(),
                        "textWithDefault",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                prop6,
                "mytextdefault",
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);

        Property prop7 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(),
                        "integerWithDefault",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                prop7,
                "1",
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);

        Property prop8 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(),
                        "decimalWithDefault",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                prop8,
                "1.1",
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);

        Property prop9 =
                TransformUtil.assertAttributeInClass(itemPriceLookupCls
                        .getAllAttributes(),
                        "booleanWithDefault",
                        PrimitivesUtil.BOM_PRIMITIVE_BOOLEAN_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                prop9,
                "true",
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);

        Property prop10 =
                TransformUtil.assertAttributeInClass(typesTestComplexCls
                        .getAllAttributes(),
                        "myDateTimeTZ",
                        PrimitivesUtil.BOM_PRIMITIVE_DATETIME_NAME);

        TransformUtil.assertPropertyPattern(typesTestComplexCls
                .getAllAttributes(), "myDateTimeTZ", ".+T.+(Z|[+-].+)");

        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop10,
                ".+T.+(Z|[+-].+)",
                XsdStereotypeUtils.XSD_PATTERN_VALUE);

        Property prop11 =
                TransformUtil.assertAttributeInClass(typesTestComplexCls
                        .getAllAttributes(),
                        "normString",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        TransformUtil.assertPropertyPattern(typesTestComplexCls
                .getAllAttributes(), "normString", "(0[1-9]|[1][0-2])");

        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop11,
                "(0[1-9]|[1][0-2])",
                XsdStereotypeUtils.XSD_PATTERN_VALUE);

        Property prop12 =
                TransformUtil.assertAttributeInClass(typesTestComplexCls
                        .getAllAttributes(),
                        "myLong",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        TransformUtil.assertPropertyIntegerLower(typesTestComplexCls
                .getAllAttributes(), "myLong", "121");
        TransformUtil.assertPropertyIntegerUpper(typesTestComplexCls
                .getAllAttributes(), "myLong", "249");

        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop12,
                "120",
                XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE);
        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop12,
                "250",
                XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE);

        Property prop13 =
                TransformUtil.assertAttributeInClass(typesTestComplexCls
                        .getAllAttributes(),
                        "myPosInteger",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        TransformUtil.assertPropertyIntegerLower(typesTestComplexCls
                .getAllAttributes(), "myPosInteger", "1");

        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop13,
                "1",
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE);

        Property prop14 =
                TransformUtil.assertAttributeInClass(typesTestComplexCls
                        .getAllAttributes(),
                        "myPosIntegerWR",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);

        TransformUtil.assertPropertyIntegerLower(typesTestComplexCls
                .getAllAttributes(), "myPosIntegerWR", "5");

        TransformUtil.assertPropertyIntegerUpper(typesTestComplexCls
                .getAllAttributes(), "myPosIntegerWR", "25");

        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop14,
                "5",
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE);
        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop14,
                "25",
                XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE);

        Property prop15 =
                TransformUtil.assertAttributeInClass(typesTestComplexCls
                        .getAllAttributes(),
                        "myDecimal",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertPropertyDecimalLower(typesTestComplexCls
                .getAllAttributes(), "myDecimal", "-12345678901234567890");
        TransformUtil.assertPropertyDecimalUpper(typesTestComplexCls
                .getAllAttributes(), "myDecimal", "12345678901234567890");

        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop15,
                "-12345678901234567890",
                XsdStereotypeUtils.XSD_MIN_EXCLUSIVE_VALUE);
        TransformUtil.assertXSDBasedRestrictionValue(model,
                prop15,
                "12345678901234567890",
                XsdStereotypeUtils.XSD_MAX_EXCLUSIVE_VALUE);

    }

}
