/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.exports_bom_wsdl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 16 April 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class BOMWSDL06_OperationWithInputsNoOutputTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.wsdl";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/bom-wsdl";

    protected String MODEL_FILE = "BOMWSDL06OperationWithInputsNoOutput.bom";

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
        List<IStatus> result =
                exportBOMtoWSDL(modelFiles.get(0), ResourcesPlugin
                        .getWorkspace().getRoot().getProject(testProjectName)
                        .getFullPath().append(TEST_FILE_NAME));

        if (!result.isEmpty()) {
            // Check if any errors
            for (IStatus status : result) {
                if (status.getSeverity() == IStatus.ERROR) {
                    throw new Exception(
                            "This should be error due to wsdl validation");
                }
            }
        }
    }

}
