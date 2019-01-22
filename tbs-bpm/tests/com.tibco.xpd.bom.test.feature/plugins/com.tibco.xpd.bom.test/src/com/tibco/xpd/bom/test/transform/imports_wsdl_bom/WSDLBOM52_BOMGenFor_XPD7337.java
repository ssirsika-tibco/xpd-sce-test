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
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;

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
public class WSDLBOM52_BOMGenFor_XPD7337 extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM52_BOMGenFor_XPD7337";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM52_BOMGenFor_XPD7337";

    protected String MODEL_FILE = "MyWSDL1.wsdl";

    protected String MODEL_FILE2 = "WSDL2.wsdl";

    protected String MODEL_XSD_FILE1 = "Schema1.xsd";

    protected String GOLD_XSD_FILE1 = "org.example.Schema1.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM52_BOMGenFor_XPD7337/goldXsds";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM52_BOMGenFor_XPD7337/goldXsds";

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
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_XSD_FILE1);

        goldFileNames.add(GOLD_XSD_FILE1);

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

        List<IStatus> statusArr2 =
                importWSDLtoBOM(new File(modelFiles.get(1).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));

        if (!statusArr2.isEmpty()) {

            statusArr.addAll(statusArr2);
        }

        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {

            throw new Exception(errors.get(0).getMessage());
        }

        int bomCount = 0;

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
                if ("org.example.MySchema.bom".equals(generatedBOMFile
                        .getName())) {

                    checkBomElements(model);
                    bomFile =
                            outputSpecialFolder.getFolder()
                                    .getFile(generatedBOMFile.getName());
                    assertTrue("Cannot find newly exported BOM file",
                            bomFile.exists());
                }
            }
        }

        /*
         * check number of BOMs.
         */
        assertEquals(3, bomCount);
        exportTest();
    }

    /**
     * @param model
     */
    private void checkBomElements(Model model) {

        ArrayList<PrimitiveType> allPrimTypes = new ArrayList<PrimitiveType>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 2, packagedElements.size()); //$NON-NLS-1$

        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof PrimitiveType) {

                allPrimTypes.add((PrimitiveType) packageableElement);
            }
        }

        assertEquals("Unexpected number of primitive types",
                2,
                allPrimTypes.size());
    }

}
