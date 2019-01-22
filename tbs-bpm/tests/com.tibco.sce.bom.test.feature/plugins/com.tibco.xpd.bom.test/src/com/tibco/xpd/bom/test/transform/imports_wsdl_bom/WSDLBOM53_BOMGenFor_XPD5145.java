/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPropertyStore;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Test that takes MyWsdl1.wsdl and Wsdl2.wsdl both import the same schema
 * Schema.xsd. Schema.xsd has two simple types. When two wsdls import the same
 * schema with simple type, on second wsdls import, merge was deleting the
 * primitive types. This test is to ensure that primitive types are not deleted.
 * 
 * The same problem could be reproduced with large schemas using brm.wsdl and
 * wp.wsdl
 * 
 * @author bharge
 * @since 11 Sep 2015
 */
public class WSDLBOM53_BOMGenFor_XPD5145 extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM53_BOMGenFor_XPD5145";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM53_BOMGenFor_XPD5145";

    protected String MODEL_FILE = "MySimpWsdlForSR1-DWL75D.wsdl";

    protected String GOLD_XSD_FILE1 = "myschema.MyColor1.xsd";

    protected String GOLD_XSD_FILE2 = "myschema.MyColor2.xsd";

    protected String GOLD_XSD_FILE3 = "org.example.MySimpWsdlForSR1.DWL75D.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM53_BOMGenFor_XPD5145/goldXsds";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM53_BOMGenFor_XPD5145/goldXsds";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE3);

        super.setUp();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTest#setupPreferencesAndWait()
     *
     */
    @Override
    protected void setupPreferencesAndWait() {
        // Set project property to keep namespace file extensions in BOM package
        // name.
        BomGenPropertyStore store = new BomGenPropertyStore(project, null);
        store.setProjectProperty(BOMResourcesPlugin.P_KEEP_NAMESPACE_FILE_EXTENSION_BOM_GENERATION,
                Boolean.TRUE.toString());
        super.setupPreferencesAndWait();
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

        int bomCount = 0;
        boolean foundExpectedBom = false;

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {
            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                bomCount++;

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$
                Model model = (Model) wc.getRootElement();
                if ("myschema.MyColor1.bom".equals(generatedBOMFile.getName())) {
                    foundExpectedBom = true;

                    checkBomElements(model);
                    bomFile =
                            outputSpecialFolder.getFolder()
                                    .getFile(generatedBOMFile.getName());
                    assertTrue("Cannot find newly exported BOM file",
                            bomFile.exists());
                }
            }
        }

        assertTrue("myschema.MyColor1.bom was not generated.", foundExpectedBom);
        /*
         * check number of BOMs.
         */
        assertEquals(3, bomCount);
        exportTest();
    }

    /**
     * Check that the package name includes the "file extension" part of the
     * namespace.
     * 
     * @param model
     *            The model.
     */
    private void checkBomElements(Model model) {

        assertEquals("myschema.MyColor1", model.getName());

    }

}
