/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 15 July 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class WSDLBOM19_SchemasWithNoTargetNamespaceTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE =
            "WSDLBOM19_SchemasWithNoTargetNamespaceTest.wsdl";

    protected String MODEL_FILE2 =
            "WSDLBOM19_SchemasWithNoTargetNamespaceTest.xsd";

    protected String MODEL_FILE3 =
            "WSDLBOM19_SchemasWithNoTargetNamespaceTest2.xsd";

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
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_FILE3);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));

        /*
         * XPD-5118 - changed to check = ZERO errors rather than 2
         * "Target namespace not set errors" as we now support this.
         */
        assertEquals(0, getErrors(new ArrayList(statusArr)).size());
    }
}
