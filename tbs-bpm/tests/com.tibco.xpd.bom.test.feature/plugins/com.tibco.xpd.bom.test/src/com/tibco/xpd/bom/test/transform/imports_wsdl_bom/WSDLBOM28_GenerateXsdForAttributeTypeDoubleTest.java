/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Junit test for protecting XPD-3917. Checks if the input XSD has attribute of
 * type Double, then the generated XSD in the .bom2xsd folder gets generated
 * without any errors.
 * 
 * @author kthombar
 * @since 24-Jul-2013
 */
public class WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "org.example.math.types.bom";

    // "com.gwl.MyClaimRepoCommonLookupRequest.bom"

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE =
            "WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest.wsdl";

    protected String MODEL_XSD_FILE1 = "Simpl.xsd";

    protected String MODEL_XSD_FILE2 = "Simpl2.xsd";

    // protected String MODEL_XSD_FILE3 = "brm.xsd";

    protected String GOLD_XSD_FILE1 =
            "org.example.WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest.xsd";

    protected String GOLD_XSD_FILE2 = "org.example.math.types.xsd";

    protected String GOLD_XSD_FILE3 = "org.example.math.types2.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM28_GenerateXsdForAttributeTypeDoubleTest/gold/builder";

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
        modelFileNames.add(MODEL_XSD_FILE1);
        modelFileNames.add(MODEL_XSD_FILE2);
        // modelFileNames.add(MODEL_XSD_FILE3);

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE3);

        super.setUp();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#testTransformation()
     * 
     * @throws Exception
     */
    @Override
    public void testTransformation() throws Exception {

        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        // check resulting bom file is correct

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

                Model model = (Model) wc.getRootElement();
                if (generatedBOMFile.getName()
                        .equals("org.example.math.types.bom")) {

                    checkBOMElements1(model);

                } else if (generatedBOMFile.getName()
                        .equals("org.example.math.types2.bom")) {

                    checkBOMElements2(model);

                }
            }
        }
        exportTest();
    }

    /**
     * @param model
     */
    private void checkBOMElements1(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Unexpected number of Packaged elements",
                2,
                packagedElements.size());

        // Collect all UML packaged elements
        for (PackageableElement packagedElement : packagedElements) {
            if (packagedElement instanceof Class) {
                allClazzes.add((Class) packagedElement);
            } else {
                assertFalse("Unexpected Element Found", true);
            }
        }

        assertEquals("Unexpected number of Classes", 2, allClazzes.size());

        for (Class eachClass : allClazzes) {
            if (eachClass.getName().equals("MathInput")) {
                assertEquals("Unexpected number of attributes", 3, eachClass
                        .getOwnedAttributes().size());
                for (Property attribute : eachClass.getOwnedAttributes()) {
                    if (attribute.getName().equals("x")) {
                        assertEquals("Unexpected Attribute type",
                                "Decimal",
                                attribute.getType().getName());

                    } else if (attribute.getName().equals("y")) {
                        assertEquals("Unexpected Attribute type",
                                "Decimal",
                                attribute.getType().getName());
                    } else if (attribute.getName().equals("description")) {
                        assertEquals("Unexpected Attribute type",
                                "Text",
                                attribute.getType().getName());
                    }
                }
            } else if (eachClass.getName().equals("MathOutput")) {
                assertEquals("Unexpected number of attributes", 1, eachClass
                        .getOwnedAttributes().size());
                for (Property attribute : eachClass.getOwnedAttributes()) {
                    if (attribute.getName().equals("result")) {
                        assertEquals("Unexpected Attribute type",
                                "Decimal",
                                attribute.getType().getName());
                    }
                }
            } else {
                assertFalse("Unexpected Class Found.", true);
            }
        }

    }

    /**
     * @param model
     */
    private void checkBOMElements2(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Unexpected number of Packaged elements",
                1,
                packagedElements.size());

        // Collect all UML packaged elements
        for (PackageableElement packagedElement : packagedElements) {
            if (packagedElement instanceof Class) {
                allClazzes.add((Class) packagedElement);
            } else {
                assertFalse("Unexpected Element Found", true);
            }
        }

        assertEquals("Unexpected number of Classes", 1, allClazzes.size());

        for (Class eachClass : allClazzes) {
            if (eachClass.getName().equals("MathsRestrictedInput")) {

                assertEquals("Incorrect Generalization", "MathInput", eachClass
                        .getGeneralizations().get(0).getGeneral().getName());

                assertEquals("Unexpected number of attributes", 3, eachClass
                        .getOwnedAttributes().size());

                for (Property attribute : eachClass.getOwnedAttributes()) {
                    if (attribute.getName().equals("x")) {
                        assertEquals("Unexpected Attribute type",
                                "Decimal",
                                attribute.getType().getName());

                    } else if (attribute.getName().equals("y")) {
                        assertEquals("Unexpected Attribute type",
                                "Decimal",
                                attribute.getType().getName());
                    } else if (attribute.getName().equals("description")) {
                        assertEquals("Unexpected Attribute type",
                                "Text",
                                attribute.getType().getName());
                    }
                }
            } else {
                assertFalse("Unexpected Class Found.", true);
            }
        }

    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#exportTest()
     * 
     * @throws Exception
     */
    @Override
    public void exportTest() throws Exception {
        super.exportTest();
    }

}
