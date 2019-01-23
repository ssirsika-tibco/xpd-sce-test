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
 * Test that takes de.wsdl the api wsdl and checks if there are any error
 * markers on the generated bom. Also checks the count of boms generated and
 * does the gold file comparison for generated xsd(s)
 * 
 * TODO: will be nice to check bom elements for some of the boms at least
 * (havent done due to time constraints)
 * 
 * @author bharge
 * @since 7 Apr 2014
 */
public class WSDLBOM42_BOMGenFor_de_wsdlTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM42_BOMGenFor_de_wsdlTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM42_BOMGenFor_de_wsdl";

    protected String MODEL_FILE = "de.wsdl";

    protected String MODEL_XSD_FILE1 = "channeltype.xsd";

    protected String MODEL_XSD_FILE2 = "comexception.xsd";

    protected String MODEL_XSD_FILE3 = "de.xsd";

    protected String MODEL_XSD_FILE4 = "de-admin-service.xsd";

    protected String MODEL_XSD_FILE5 = "de-attribute-service.xsd";

    protected String MODEL_XSD_FILE6 = "de-browse-service.xsd";

    protected String MODEL_XSD_FILE7 = "de-container-service.xsd";

    protected String MODEL_XSD_FILE8 = "deexception.xsd";

    protected String MODEL_XSD_FILE9 = "de-exporter-service.xsd";

    protected String MODEL_XSD_FILE10 = "de-ldap.xsd";

    protected String MODEL_XSD_FILE11 = "de-ldap-service.xsd";

    protected String MODEL_XSD_FILE12 = "de-mapping-service.xsd";

    protected String MODEL_XSD_FILE13 = "de-resolver-service.xsd";

    protected String MODEL_XSD_FILE14 = "de-resourcequery-service.xsd";

    protected String MODEL_XSD_FILE15 = "de-security-service.xsd";

    protected String MODEL_XSD_FILE16 = "de-usersettings-service.xsd";

    protected String MODEL_XSD_FILE17 = "organisation.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM42_BOMGenFor_de_wsdl/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM42_BOMGenFor_de_wsdl/gold/builder";

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
        modelFileNames.add(MODEL_XSD_FILE9);
        modelFileNames.add(MODEL_XSD_FILE10);
        modelFileNames.add(MODEL_XSD_FILE11);
        modelFileNames.add(MODEL_XSD_FILE12);
        modelFileNames.add(MODEL_XSD_FILE13);
        modelFileNames.add(MODEL_XSD_FILE14);
        modelFileNames.add(MODEL_XSD_FILE15);
        modelFileNames.add(MODEL_XSD_FILE16);
        modelFileNames.add(MODEL_XSD_FILE17);

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
            }
        }

        /*
         * check number of BOMs.
         */
        assertEquals(18, bomCount);

        exportTest();
    }

}
