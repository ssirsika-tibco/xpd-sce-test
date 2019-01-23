/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.validator.BOMValidatorActivator;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Two wsdls have same inline schemas which when imported must get merged into
 * one bom model. This test checks ONLY for the bom generation without any
 * errors (doesn't look into the elements generated in the bom). Basically to
 * check if the bom generation is fine without any errors (as a coverage for
 * XPD-6071)
 * 
 * @author bharge
 * @since 3 Apr 2014
 */
public class WSDLBOM42_MergeSchemasTest extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM42_MergeSchemasTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM42_MergeSchemasTest";

    // The name of the wsdl that is being tested
    protected String MODEL_FILE = "wsdl1.wsdl";

    protected String MODEL_FILE1 = "wsdl2.wsdl";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM42_MergeSchemasTest/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM42_MergeSchemasTest/gold/builder";

    protected String GOLD_XSD_FILE1 =
            "macquarietelecom.customerorder.customerorder_v1_0.xsd";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE1);

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

                String[] destIds =
                        new String[] {
                                BOMValidatorActivator.VALIDATION_DEST_ID_XSD,
                                BOMValidatorActivator.VALIDATION_DEST_ID_BOMGENERIC };
                boolean hasErrorMarker =
                        TransformUtil.isMarkerSeverityExist(destIds,
                                modelFiles.get(0),
                                IMarker.SEVERITY_ERROR);
                assertEquals("There are XSD or BOM GENERIC errors on the BOM",
                        false,
                        hasErrorMarker);

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
        assertEquals(3, bomCount);

        exportTest();
    }

}
