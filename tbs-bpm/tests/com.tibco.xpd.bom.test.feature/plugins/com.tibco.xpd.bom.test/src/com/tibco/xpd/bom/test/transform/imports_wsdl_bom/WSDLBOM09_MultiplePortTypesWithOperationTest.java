/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

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
public class WSDLBOM09_MultiplePortTypesWithOperationTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE =
            "WSDLBOM09_MultiplePortTypesWithOperation.wsdl";

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
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        // check resulting bom file is correct
        Issues issues = new IssuesImpl();
        IFile file =
                TransformUtil.getOutputWSDLFile(issues,
                        modelFiles.get(0),
                        outputSpecialFolder.getFolder());
        assertTrue("Cannot find newly exported BOM file", file.exists()); //$NON-NLS-1$

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 4, packagedElements.size()); //$NON-NLS-1$

        // check classes
        org.eclipse.uml2.uml.Class childCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ChildClass"); //$NON-NLS-1$

        org.eclipse.uml2.uml.Class parentCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ParentClass"); //$NON-NLS-1$

        org.eclipse.uml2.uml.Class childPortTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ChildClassPortType"); //$NON-NLS-1$

        org.eclipse.uml2.uml.Class parentPortTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ParentClassPortType"); //$NON-NLS-1$

        // check attributes
        TransformUtil.assertAttributeSize(childCls, 1);
        TransformUtil.assertAttributeSize(parentCls, 1);

        // check operations
        TransformUtil.assertOperationSize(childPortTypeCls, 1);
        TransformUtil.assertOperationInClass(childPortTypeCls
                .getAllOperations(), "operation2", null); //$NON-NLS-1$
        TransformUtil.assertOperationSize(parentPortTypeCls, 1);
        TransformUtil.assertOperationInClass(parentPortTypeCls
                .getAllOperations(), "operation1", null); //$NON-NLS-1$
    }
}
