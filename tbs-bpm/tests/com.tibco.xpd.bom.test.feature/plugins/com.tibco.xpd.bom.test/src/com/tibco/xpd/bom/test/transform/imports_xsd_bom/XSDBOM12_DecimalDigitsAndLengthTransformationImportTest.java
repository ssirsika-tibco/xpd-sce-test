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
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 018 Mar 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM12_DecimalDigitsAndLengthTransformationImportTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM12_DecimalDigitsAndLengthTransformationTest.xsd";

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

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 21, packagedElements.size()); //$NON-NLS-1$

        // check decimals
        TransformUtil.assertPackagedElementClass(packagedElements,
                "FamilyMountainBikeSizes");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "testComplex");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "testMeSimpleContent");

        PrimitiveType tmpPrimAlone =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "testAlone");

        PrimitiveType tmpPrim1 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "test1");

        PrimitiveType tmpPrim2 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "test2");

        Enumeration enum1 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "mountainbikesize");

        Class class2 =
                TransformUtil.getClass(packagedElements,
                        "FamilyMountainBikeSizes");

        Class class3 = TransformUtil.getClass(packagedElements, "testComplex");

        Class class4 =
                TransformUtil.getClass(packagedElements, "testMeSimpleContent");

        Property prop1 =
                TransformUtil.assertAttributeInClass(class2.getAllAttributes(),
                        "familyMember",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertAttributeInClass(class2.getAllAttributes(),
                "value",
                enum1.getName());

        Property prop2 =
                TransformUtil.assertAttributeInClass(class3.getAllAttributes(),
                        "attr",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        Property prop3 =
                TransformUtil.assertAttributeInClass(class3.getAllAttributes(),
                        "attr2",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        Property prop4 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "ga",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        Property prop5 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "value",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertDecimalLength(tmpPrimAlone, "10");
        TransformUtil.assertDecimalPlaces(tmpPrimAlone, "2");

        TransformUtil.assertDecimalLength(tmpPrim1, "10");
        TransformUtil.assertDecimalPlaces(tmpPrim1, "2");

        TransformUtil.assertDecimalLength(tmpPrim2, "10");
        TransformUtil.assertDecimalPlaces(tmpPrim2, "2");

        TransformUtil.assertDecimalLength(prop1, "10");
        TransformUtil.assertDecimalPlaces(prop1, "2");

        TransformUtil.assertDecimalLength(prop2, "10");
        TransformUtil.assertDecimalPlaces(prop2, "2");

        TransformUtil.assertDecimalLength(prop3, "10");
        TransformUtil.assertDecimalPlaces(prop3, "2");

        TransformUtil.assertDecimalLength(prop4, "10");
        TransformUtil.assertDecimalPlaces(prop4, "2");

        TransformUtil.assertDecimalLength(prop5, "10");
        TransformUtil.assertDecimalPlaces(prop5, "2");

        // check floats
        TransformUtil.assertPackagedElementClass(packagedElements,
                "FamilyMountainBikeSizesFloat");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "testComplexFloat");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "testMeSimpleContentFloat");

        tmpPrimAlone =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "testAloneFloat");

        tmpPrim1 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "test1Float");

        tmpPrim2 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "test2Float");

        enum1 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "mountainbikesizeFloat");

        class2 =
                TransformUtil.getClass(packagedElements,
                        "FamilyMountainBikeSizesFloat");

        class3 = TransformUtil.getClass(packagedElements, "testComplexFloat");

        class4 =
                TransformUtil.getClass(packagedElements,
                        "testMeSimpleContentFloat");

        prop1 =
                TransformUtil.assertAttributeInClass(class2.getAllAttributes(),
                        "familyMemberFloat",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertAttributeInClass(class2.getAllAttributes(),
                "value",
                enum1.getName());

        prop2 =
                TransformUtil.assertAttributeInClass(class3.getAllAttributes(),
                        "attrFloat",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop3 =
                TransformUtil.assertAttributeInClass(class3.getAllAttributes(),
                        "attr2Float",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop4 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "gaFloat",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop5 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "value",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertDecimalLength(tmpPrimAlone, "10");
        TransformUtil.assertDecimalPlaces(tmpPrimAlone, "2");

        TransformUtil.assertDecimalLength(tmpPrim1, "10");
        TransformUtil.assertDecimalPlaces(tmpPrim1, "2");

        TransformUtil.assertDecimalLength(tmpPrim2, "10");
        TransformUtil.assertDecimalPlaces(tmpPrim2, "2");

        TransformUtil.assertDecimalLength(prop1, "10");
        TransformUtil.assertDecimalPlaces(prop1, "2");

        TransformUtil.assertDecimalLength(prop2, "10");
        TransformUtil.assertDecimalPlaces(prop2, "2");

        TransformUtil.assertDecimalLength(prop3, "10");
        TransformUtil.assertDecimalPlaces(prop3, "2");

        TransformUtil.assertDecimalLength(prop4, "10");
        TransformUtil.assertDecimalPlaces(prop4, "2");

        TransformUtil.assertDecimalLength(prop5, "10");
        TransformUtil.assertDecimalPlaces(prop5, "2");

        // check doubles
        TransformUtil.assertPackagedElementClass(packagedElements,
                "FamilyMountainBikeSizesDouble");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "testComplexDouble");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "testMeSimpleContentDouble");

        tmpPrimAlone =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "testAloneDouble");

        tmpPrim1 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "test1Double");

        tmpPrim2 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "test2Double");

        enum1 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "mountainbikesizeDouble");

        class2 =
                TransformUtil.getClass(packagedElements,
                        "FamilyMountainBikeSizesDouble");

        class3 = TransformUtil.getClass(packagedElements, "testComplexDouble");

        class4 =
                TransformUtil.getClass(packagedElements,
                        "testMeSimpleContentDouble");

        prop1 =
                TransformUtil.assertAttributeInClass(class2.getAllAttributes(),
                        "familyMemberDouble",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertAttributeInClass(class2.getAllAttributes(),
                "value",
                enum1.getName());

        prop2 =
                TransformUtil.assertAttributeInClass(class3.getAllAttributes(),
                        "attrDouble",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop3 =
                TransformUtil.assertAttributeInClass(class3.getAllAttributes(),
                        "attr2Double",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop4 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "gaDouble",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        prop5 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "value",
                        PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);

        TransformUtil.assertDecimalLength(tmpPrimAlone, "10");
        TransformUtil.assertDecimalPlaces(tmpPrimAlone, "2");

        TransformUtil.assertDecimalLength(tmpPrim1, "10");
        TransformUtil.assertDecimalPlaces(tmpPrim1, "2");

        TransformUtil.assertDecimalLength(tmpPrim2, "10");
        TransformUtil.assertDecimalPlaces(tmpPrim2, "2");

        TransformUtil.assertDecimalLength(prop1, "10");
        TransformUtil.assertDecimalPlaces(prop1, "2");

        TransformUtil.assertDecimalLength(prop2, "10");
        TransformUtil.assertDecimalPlaces(prop2, "2");

        TransformUtil.assertDecimalLength(prop3, "10");
        TransformUtil.assertDecimalPlaces(prop3, "2");

        TransformUtil.assertDecimalLength(prop4, "10");
        TransformUtil.assertDecimalPlaces(prop4, "2");

        TransformUtil.assertDecimalLength(prop5, "10");
        TransformUtil.assertDecimalPlaces(prop5, "2");

        exportTest();
    }

}
