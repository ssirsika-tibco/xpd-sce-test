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

import com.tibco.xpd.bom.test.transform.common.TransformationTest;

/**
 * 
 * Junit test for protecting issue fixed in XPD-4781. Resolves the issue where
 * BOM autogeneration used to fail in case XSD is present in a certain folder
 * structure.
 * 
 * @author sajain
 * @since Jul 4, 2013
 */
public class WSDLBOM20_SchemasInCertainFolderHierarchyTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE = "testNew.wsdl";

    protected String DEPENDENCY_FILE1 = "XSDOne/impOne.xsd";

    protected String DEPENDENCY_FILE2 = "XSDOne/XSDTwo/impTwo.xsd";

    protected String DEPENDENCY_FILE3 = "XSDOneB/XSDTwo/impTwoIncl.xsd";

    protected String DEPENDENCY_FILE4 =
            "XSDOneB/XSDTwo/XSDThree/impThreeIncl.xsd";

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
        modelFileNames.add(DEPENDENCY_FILE1);
        modelFileNames.add(DEPENDENCY_FILE2);
        modelFileNames.add(DEPENDENCY_FILE3);
        modelFileNames.add(DEPENDENCY_FILE4);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }

        /*
         * Check if the number of BOMs generated is equal to the number of
         * Target namespaces i.e., 3 in this case.
         */
        IResource[] members = outputSpecialFolder.getFolder().members();
        int numOfBoms = 0;

        for (IResource currMember : members) {
            if (null != currMember.getFileExtension()) {
                if (currMember.getFileExtension().equalsIgnoreCase("bom")) {
                    numOfBoms++;
                }
            }
        }

        assertTrue("Number of BOMs generated not as expected!", numOfBoms == 3);
    }
}
