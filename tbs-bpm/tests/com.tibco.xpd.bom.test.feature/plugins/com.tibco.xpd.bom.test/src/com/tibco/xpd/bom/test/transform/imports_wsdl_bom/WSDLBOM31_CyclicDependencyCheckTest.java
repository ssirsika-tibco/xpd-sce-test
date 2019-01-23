/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * This is a Junit test for testing if Cyclic dependency is not allowed in the
 * provided xsd's. Example, if xsd1 imports xsd2 then xsd2 should not import
 * xsd1. This shows cyclic dependency in the generated BOM's, which is not
 * allowed. So, basically this test should check if there is cyclic dependency,
 * if not then the test should fail, as the input wsdl provided has xsd's which
 * have cyclic dependency.
 * 
 * @author kthombar
 * @since 24-Jul-2013
 */
public class WSDLBOM31_CyclicDependencyCheckTest extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE = "WSDLBOM31_CyclicDependencyCheckTest.wsdl";

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
        modelFileNames.add(MODEL_FILE);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);

        // There should be errors, as the input wsdl that we provided has
        // schemas
        // which have cyclic dependency. Hence is errors.isEmpty() is true, then
        // in that case the test should FAIL.

        if (errors.isEmpty()) {
            assertFalse("Cyclic Dependency not detected.\nThe input Wsdl '"
                    + MODEL_FILE + "' has XSD's which have cyclic dependency.",
                    true);
        }

    }
}
