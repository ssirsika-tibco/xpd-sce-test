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
import org.eclipse.uml2.uml.Parameter;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test to protect XPD-5203.
 * 
 * @author kthombar
 * @since 08-Aug-2013
 */
public class WSDLBOM34_MessageInOneWsdlImplInOtherWsdlTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "org.example.WSDL3.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM34_MessageInOneWsdlImplInOtherWsdlTest";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE = "WSDL1.wsdl";

    protected String MODEL_XSD_FILE1 = "WSDL3.wsdl";

    protected String GOLD_XSD_FILE1 = "org.example.WSDL3.xsd";

    protected String GOLD_XSD_FILE2 = "com.example.xmlns._1374840436418.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM34_MessageInOneWsdlImplInOtherWsdlTest/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM34_MessageInOneWsdlImplInOtherWsdlTest/gold/builder";

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

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);

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
                        .equals("com.example.xmlns._1374840436418.bom")) {

                    checkBOMElements1(model);

                } else if (generatedBOMFile.getName()
                        .equals("org.example.WSDL3.bom")) {

                    checkBOMElements2(model);

                } else {
                    assertFalse("Unexpected class found", true);

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
            if (eachClass.getName().equals("PortType")) {
                assertEquals("Unexpected number of operations", 1, eachClass
                        .getOwnedOperations().size());

                assertEquals("Unexpected operation name",
                        "InOutOperation",
                        eachClass.getOwnedOperations().get(0).getName());

                EList<Parameter> ownedParameters =
                        eachClass.getOwnedOperations().get(0)
                                .getOwnedParameters();
                assertEquals("Unexpected operation name",
                        2,
                        ownedParameters.size());

                for (Parameter ownedParam : ownedParameters) {

                    if (ownedParam.getName().equals("request")) {
                        assertEquals("Unexpected Parameter type",
                                "Cust",
                                ownedParam.getType().getName());
                    } else if (ownedParam.getName().equals("response")) {
                        assertEquals("Unexpected Parameter type",
                                "Addr",
                                ownedParam.getType().getName());
                    } else {
                        assertFalse("Expected parameter not found", true);
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
            if (eachClass.getName().equals("Cust")) {

                assertEquals("Unexpected number of attributes", 1, eachClass
                        .getOwnedAttributes().size());

                assertEquals("Unexpected attribute name", "in1", eachClass
                        .getOwnedAttributes().get(0).getName());

            } else if (eachClass.getName().equals("Addr")) {
                assertEquals("Unexpected number of attributes", 1, eachClass
                        .getOwnedAttributes().size());

                assertEquals("Unexpected attribute name", "out", eachClass
                        .getOwnedAttributes().get(0).getName());
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
