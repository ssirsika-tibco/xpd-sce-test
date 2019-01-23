/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting XPD-4301. Checks if a Class is related to another
 * class by association, then the correct related class from the correct class
 * gets picked up.
 * 
 * @author kthombar
 * @since 21-Jul-2013
 */
public class WSDLBOM29_ClassAssociatingWrongPackageElementTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE =
            "WSDLBOM29_ClassAssociatingWrongPackageElementTest.wsdl";

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

        checkBOMElements(model);

    }

    private void checkBOMElements(Model model) throws Exception {

        ArrayList<Class> allClasses = new ArrayList<Class>();

        ArrayList<Association> allAssociation = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 6, packagedElements.size()); //$NON-NLS-1$

        for (PackageableElement packagedElement : packagedElements) {

            if (packagedElement instanceof Class) {
                allClasses.add((Class) packagedElement);
            } else if (packagedElement instanceof Association) {
                allAssociation.add((Association) packagedElement);
            } else {
                assertFalse("Unexpected element found", true);
            }
        }

        // Assert all classes
        checkClasses(allClasses);

        // Assert all Associations
        checkAssociation(allAssociation);

    }

    private void checkClasses(ArrayList<Class> allClasses) {

        // We expect 5 classes
        assertEquals("Unexpected number of Classes", 5, allClasses.size());

        for (Class eachClass : allClasses) {
            if (eachClass.getName().equals("MyClass")) {

                assertEquals("Unexpected number of Attributes", 1, eachClass
                        .getOwnedAttributes().size());

                assertEquals("Unexpected name of Attributes",
                        "element",
                        eachClass.getOwnedAttributes().get(0).getName());

                assertEquals("Unexpected name of Attributes",
                        "element",
                        eachClass.getOwnedAttributes().get(0).getName());

                assertEquals("Unexpected name of Attributes",
                        "AssociatedClass",
                        eachClass.getOwnedAttributes().get(0).getType()
                                .getName());

            }

        }

    }

    private void checkAssociation(ArrayList<Association> allAssociation) {

        // We expect 1 Association
        assertEquals("Unexpected number of Associations",
                1,
                allAssociation.size());
        assertEquals("Unexpected name of Associations",
                "Composition0",
                allAssociation.get(0).getName());
        assertEquals("Unexpected member end of Associations",
                "element",
                allAssociation.get(0).getMemberEnds().get(0).getName());

    }

}
