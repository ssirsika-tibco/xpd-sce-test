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
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit Test for protecting XPD-3768. Checks if there is Association between
 * classes then the correct class from the correct package gets picked up as the
 * Associated Class.
 * 
 * @author kthombar
 * @since 22-Jul-2013
 */
public class WSDLBOM30_AsociationReferringDiffrentClassProblemTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE =
            "WSDLBOM30_AsociationReferringDiffrentClassProblemTest.wsdl";

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
        IResource[] members = outputSpecialFolder.getFolder().members();

        int countGeneratedBoms = 0;
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {
                countGeneratedBoms++;
                IFile generatedBOMFile = (IFile) member;

                // BOM
                // 'nl.alfam.xmlns.xsd.nap.napservices.napdb.v1.getproceslogresponse.bom'
                // and BOM
                // 'nl.alfam.xmlns.xsd.nap.services.amkr.v1.storeproceslogrequest.bom'
                // are only under consideration, so checking generated BOM
                // elements only
                // for these two BOM's

                if (generatedBOMFile
                        .getName()
                        .equals("nl.alfam.xmlns.xsd.nap.napservices.napdb.v1.getproceslogresponse.bom")) {

                    checkBomElements(getBomModel(generatedBOMFile), 4, 3);

                } else if (generatedBOMFile
                        .getName()
                        .equals("nl.alfam.xmlns.xsd.nap.services.amkr.v1.storeproceslogrequest.bom")) {

                    checkBomElements(getBomModel(generatedBOMFile), 3, 2);
                    // checkBomElements2(getBomModel(generatedBOMFile));

                }

            }
        }
        assertEquals("Number of Generated Bom's is not correct",
                5,
                countGeneratedBoms);
    }

    /**
     * Returns the Model for the passed BOM File.
     * 
     * @param generatedBOMFile
     * @return
     */
    private Model getBomModel(IFile generatedBOMFile) {
        WorkingCopy wc =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(generatedBOMFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        return (Model) wc.getRootElement();
    }

    /**
     * Checks if the passed model has correct BOM elements.
     * 
     * @param model
     */
    private void checkBomElements(Model model, int packagedElementSize,
            int classCount) {

        ArrayList<Class> allClasses = new ArrayList<Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Unexpected number of Packaged elements",
                packagedElementSize,
                packagedElements.size());

        for (PackageableElement packagedElement : packagedElements) {
            if (packagedElement instanceof Class) {
                allClasses.add((Class) packagedElement);
            } else if (packagedElement instanceof Association) {
                allAssociations.add((Association) packagedElement);
            } else {
                assertFalse("Unexpected element found", true);
            }
        }

        // Check if the number of generated classes are correct.
        assertEquals("Unexpected number of Classes",
                classCount,
                allClasses.size());

        // Inspecting the Class and its elements
        checkClasses(allClasses);

        // Check Associations
        checkAssociations(allAssociations);

    }

    /**
     * Checks if Association is generated correctly.
     * 
     * @param allAssociations
     */
    private void checkAssociations(ArrayList<Association> allAssociations) {

        assertEquals("Unexpected number of Associations",
                1,
                allAssociations.size());
        assertEquals("Unexpected Member end of Associations",
                "procesLog",
                allAssociations.get(0).getMemberEnds().get(0).getName());

    }

    /**
     * Checks if Classes and its attributes are generated correctly.
     * 
     * @param allClasses
     */
    private void checkClasses(ArrayList<Class> allClasses) {

        for (Class eachClass : allClasses) {

            if (eachClass.getName().equals("ProcesLogType")) {
                assertEquals("Expected number of attributes not found.",
                        9,
                        eachClass.getOwnedAttributes().size());

            } else if (eachClass.getName().equals("ProcesLogCollectieType")) {
                assertEquals("Expected number of attributes not found.",
                        1,
                        eachClass.getOwnedAttributes().size());
                assertEquals("Unexpected Attribute name.",
                        "procesLog",
                        eachClass.getOwnedAttributes().get(0).getName());
                assertEquals("Unexpected Attribute type.",
                        "ProcesLogType",
                        eachClass.getOwnedAttributes().get(0).getType()
                                .getName());

            } else if (eachClass.getName().equals("GetProcesLogResponseType")) {

                assertEquals("Expected number of attributes not found.",
                        1,
                        eachClass.getOwnedAttributes().size());
                assertEquals("Unexpected Attribute name.",
                        "procesLogCollectie",
                        eachClass.getOwnedAttributes().get(0).getName());
                assertEquals("Unexpected Attribute type.",
                        "ProcesLogCollectieType",
                        eachClass.getOwnedAttributes().get(0).getType()
                                .getName());

            }

        }

    }
}
