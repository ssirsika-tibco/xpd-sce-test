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
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
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
public class XSDBOM25_DuplicateNamesSameComplexTypeTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM25_DuplicateNamesSameComplexTypeTransformationTest.xsd";

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

        checkClazzes(packagedElements);

        // exportTest();
    }

    private void checkClazzes(EList<PackageableElement> packagedElements)
            throws Exception {

        Class myComplex =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyComplex");

        Class myComplex2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyComplex2");

        Class myComplex3 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyComplex3");

        checkEnums(packagedElements);

        checkAttributes(packagedElements, myComplex, myComplex2, myComplex3);

    }

    private void checkEnums(EList<PackageableElement> packagedElements)
            throws Exception {

        Enumeration myEnum1 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "ElemmyRestenumType");

        EnumerationLiteral enumList1 =
                TransformUtil.assertEnumLiteralInEnum(myEnum1
                        .getOwnedLiterals(), "TEST");
        EnumerationLiteral enumList2 =
                TransformUtil.assertEnumLiteralInEnum(myEnum1
                        .getOwnedLiterals(), "TEST1");

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum1.getModel(),
                enumList1,
                "tEst",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum1.getModel(),
                enumList2,
                "test",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        Enumeration myEnum2 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "eLemmYRestPrimType");

        enumList1 =
                TransformUtil.assertEnumLiteralInEnum(myEnum2
                        .getOwnedLiterals(), "TE_ST1");
        enumList2 =
                TransformUtil.assertEnumLiteralInEnum(myEnum2
                        .getOwnedLiterals(), "TEST");

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum2.getModel(),
                enumList1,
                "teSt",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum2.getModel(),
                enumList2,
                "tEst",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        Enumeration myEnum3 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "ElemmyRestenum3Type");

        enumList1 =
                TransformUtil.assertEnumLiteralInEnum(myEnum3
                        .getOwnedLiterals(), "TEST3");
        enumList2 =
                TransformUtil.assertEnumLiteralInEnum(myEnum3
                        .getOwnedLiterals(), "TEST31");

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum3.getModel(),
                enumList1,
                "tEst3",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum3.getModel(),
                enumList2,
                "test3",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        Enumeration myEnum4 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "eLemmYRestPrim3Type");

        enumList1 =
                TransformUtil.assertEnumLiteralInEnum(myEnum4
                        .getOwnedLiterals(), "TE_ST31");
        enumList2 =
                TransformUtil.assertEnumLiteralInEnum(myEnum4
                        .getOwnedLiterals(), "TEST3");

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum4.getModel(),
                enumList1,
                "teSt3",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);

        TransformUtil.assertXSDBasedEnumerationLiteral(myEnum4.getModel(),
                enumList2,
                "tEst3",
                XsdStereotypeUtils.XSD_PROPERTY_VALUE);
    }

    private void checkAttributes(EList<PackageableElement> packagedElements,
            Class myComplex, Class myComplex2, Class myComplex3)
            throws Exception {
        Property property = null;

        // check myComplex
        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemMyComplex21", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyComplex2",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemmyComplex22", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyComplex2",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemmyComplex2", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyComplex2",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemMyPrim1", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyPrim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemmyPriM2", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyPriM",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemmyprim", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "Elemmyprim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemmyRestprim", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyRestprim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex
                        .getAllAttributes(), "elemmYRestPrim1", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "eLemmYRestPrim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        // check myComplex2
        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemMyComplex1", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyComplex",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemmyComplex2", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyComplex",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemmyComplex", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyComplex",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemMyPrim1", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyPrim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemmyPriM2", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyPriM",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemmyprim", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "Elemmyprim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemmyRestenum", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyRestenum",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex2
                        .getAllAttributes(), "elemmYRestPrim", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "eLemmYRestPrim",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        // check myComplex3
        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemMyComplex34", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyComplex3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyComplex35", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyComplex3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyComplex33", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyComplex3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemMyPrim34", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyPrim3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyPriM35", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyPriM3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyprim3", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "Elemmyprim3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyRestenum3", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyRestenum3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmYRestPrim3", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "eLemmYRestPrim3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemMyComplex34", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyComplex3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyComplex35", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyComplex3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyComplex33", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyComplex3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemMyPrim31", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemMyPrim3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyPriM32", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "elemmyPriM3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyprim3", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "Elemmyprim3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmyRestenum3", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "ElemmyRestenum3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

        property =
                TransformUtil.assertAttributeInClass(myComplex3
                        .getAllAttributes(), "elemmYRestPrim3", null);
        TransformUtil.assertXSDBasedPropertyValue(myComplex.getModel(),
                property,
                "eLemmYRestPrim3",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);

    }
}
