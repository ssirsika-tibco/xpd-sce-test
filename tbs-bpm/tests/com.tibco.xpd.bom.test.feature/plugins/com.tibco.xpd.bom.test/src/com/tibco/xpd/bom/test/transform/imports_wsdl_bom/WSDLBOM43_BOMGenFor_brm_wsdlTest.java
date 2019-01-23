/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Test that takes brm.wsdl the api wsdl and checks if there are any error
 * markers on the generated bom. Also checks the count of boms generated and
 * does the gold file comparison for generated xsd(s)
 * 
 * TODO: will be nice to check bom elements for some of the boms at least
 * (havent done due to time constraints)
 * 
 * @author bharge
 * @since 7 Apr 2014
 */
public class WSDLBOM43_BOMGenFor_brm_wsdlTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM43_BOMGenFor_brm_wsdlTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM43_BOMGenFor_brm_wsdl";

    protected String MODEL_FILE = "brm.wsdl";

    protected String MODEL_XSD_FILE1 = "brm.xsd";

    protected String MODEL_XSD_FILE2 = "brmdto.xsd";

    protected String MODEL_XSD_FILE3 = "brmexception.xsd";

    protected String MODEL_XSD_FILE4 = "comexception.xsd";

    protected String MODEL_XSD_FILE5 = "datamodel.xsd";

    protected String MODEL_XSD_FILE6 = "deexception.xsd";

    protected String MODEL_XSD_FILE7 = "organisation.xsd";

    protected String MODEL_XSD_FILE8 = "scriptdescriptor.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM43_BOMGenFor_brm_wsdl/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM43_BOMGenFor_brm_wsdl/gold/builder";

    protected String GOLD_XSD_FILE1 = "com.tibco.n2.brm.api.exception.xsd";

    protected String GOLD_XSD_FILE2 = "com.tibco.n2.brm.api.xsd";

    protected String GOLD_XSD_FILE3 = "com.tibco.n2.brm.services.xsd";

    protected String GOLD_XSD_FILE4 = "com.tibco.n2.common.api.exception.xsd";

    protected String GOLD_XSD_FILE5 = "com.tibco.n2.common.datamodel.xsd";

    protected String GOLD_XSD_FILE6 =
            "com.tibco.n2.common.organisation.api.xsd";

    protected String GOLD_XSD_FILE7 = "com.tibco.n2.de.api.exception.xsd";

    protected String GOLD_XSD_FILE8 = "com.tibco.XPD.ScriptDescriptor.xsd";

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
        modelFileNames.add(MODEL_XSD_FILE1);
        modelFileNames.add(MODEL_XSD_FILE2);
        modelFileNames.add(MODEL_XSD_FILE3);
        modelFileNames.add(MODEL_XSD_FILE4);
        modelFileNames.add(MODEL_XSD_FILE5);
        modelFileNames.add(MODEL_XSD_FILE6);
        modelFileNames.add(MODEL_XSD_FILE7);
        modelFileNames.add(MODEL_XSD_FILE8);

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE3);
        goldFileNames.add(GOLD_XSD_FILE4);
        goldFileNames.add(GOLD_XSD_FILE5);
        goldFileNames.add(GOLD_XSD_FILE6);
        goldFileNames.add(GOLD_XSD_FILE7);
        goldFileNames.add(GOLD_XSD_FILE8);

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
                bomFile =
                        outputSpecialFolder.getFolder()
                                .getFile(generatedBOMFile.getName());
            }
        }

        /*
         * check number of BOMs.
         */
        assertEquals(8, bomCount);

        exportTest();
    }

}
