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

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting XPD-4034. Checks if the WSDL->BOM->XSD round trip
 * works correctly.
 * 
 * @author kthombar
 * @since 23-Jul-2013
 */
public class WSDLBOM27_GenerateBomForBRMWsdl extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "org.example.RepWsdl.bom";

    // "com.gwl.MyClaimRepoCommonLookupRequest.bom"

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM27_GenerateBomForBRMWsdl";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE = "RepWsdl.wsdl";

    protected String MODEL_XSD_FILE1 = "brmexception.xsd";

    protected String MODEL_XSD_FILE2 = "brmdto.xsd";

    protected String MODEL_XSD_FILE3 = "brm.xsd";

    protected String GOLD_XSD_FILE1 = "org.example.brmexception.xsd";

    protected String GOLD_XSD_FILE2 = "org.example.brm.xsd";

    protected String GOLD_XSD_FILE3 = "org.example.RepWsdl.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM27_GenerateBomForBRMWsdl/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM27_GenerateBomForBRMWsdl/gold/builder";

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
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_XSD_FILE1);
        modelFileNames.add(MODEL_XSD_FILE2);
        modelFileNames.add(MODEL_XSD_FILE3);

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE3);

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
        // check resulting bom file is correct

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$
                Model model = (Model) wc.getRootElement();

                if (generatedBOMFile.getName()
                        .equals("org.example.RepWsdl.bom")) {

                    checkBOMElements1(model);

                } else if (generatedBOMFile.getName()
                        .equals("org.example.brm.bom")) {

                    checkBOMElements2(model);

                } else if (generatedBOMFile.getName()
                        .equals("org.example.brmexception.bom")) {
                    checkBOMElements3(model);

                }

            }
        }
        exportTest();
    }

    /**
     * @param model
     */
    private void checkBOMElements1(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Unexpected number of Packaged elements",
                1,
                packagedElements.size());

        // Collect all UML packaged elements
        for (PackageableElement pe : packagedElements) {
            if (pe instanceof Class) {
                allClazzes.add((Class) pe);
            } else {
                assertFalse("Unexpected Element Found", true);
            }
        }

        assertEquals("Unexpected number of Classes", 1, allClazzes.size());

        assertEquals("Unexpected number of Class operations", 1, allClazzes
                .get(0).getOwnedOperations().size());

        assertEquals("Unexpected name of Class operations",
                "NewOperation",
                allClazzes.get(0).getOwnedOperations().get(0).getName());

        assertEquals("Unexpected Operation return type",
                "getWorkListItemsResponseType",
                allClazzes.get(0).getOwnedOperations().get(0).getType()
                        .getName());

        assertEquals("Unexpected Operation parameter type",
                "getWorkListItemsType",
                allClazzes.get(0).getOwnedOperations().get(0)
                        .getOwnedParameters().get(0).getType().getName());

    }

    /**
     * @param model
     * @throws Exception
     */
    private void checkBOMElements2(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Unexpected number of Packaged elements",
                7,
                packagedElements.size());

        // Collect all UML packaged elements
        for (PackageableElement packagedElement : packagedElements) {
            if (packagedElement instanceof Class) {
                allClazzes.add((Class) packagedElement);
            } else if (packagedElement instanceof Association) {
                allAssociations.add((Association) packagedElement);
            } else {
                assertFalse("Unexpected Element Found", true);
            }
        }

        checkGeneratedClasses(allClazzes);
        checkGeneratedAssociations(allAssociations);

    }

    /**
     * @param allAssociations
     */
    private void checkGeneratedAssociations(
            ArrayList<Association> allAssociations) {

        assertEquals("Unexpected number of Association",
                2,
                allAssociations.size());
        for (Association eachAssociation : allAssociations) {

            if (eachAssociation.getName().equals("Composition0")) {
                assertEquals("Unexpected Association member end",
                        "id",
                        eachAssociation.getMemberEnds().get(0).getName());
            } else if (eachAssociation.getName().equals("Composition1")) {
                assertEquals("Unexpected Association member end",
                        "workItems",
                        eachAssociation.getMemberEnds().get(0).getName());
            } else {
                assertFalse("Unexpected Association Found", true);
            }

        }
    }

    /**
     * @param allClazzes
     */
    private void checkGeneratedClasses(ArrayList<Class> allClazzes) {

        assertEquals("Unexpected number of Classes", 5, allClazzes.size());

        for (Class eachClass : allClazzes) {
            if (eachClass.getName().equals("ManagedObjectID")) {
                assertEquals("Unexpected number of attributes", 1, eachClass
                        .getOwnedAttributes().size());
            } else if (eachClass.getName().equals("WorkItem")) {
                assertEquals("Unexpected number of attributes", 2, eachClass
                        .getOwnedAttributes().size());
            } else if (eachClass.getName()
                    .equals("getWorkListItemsResponseType")) {
                assertEquals("Unexpected number of attributes", 4, eachClass
                        .getOwnedAttributes().size());
            } else if (eachClass.getName().equals("ObjectID")) {
                assertEquals("Unexpected number of attributes", 1, eachClass
                        .getOwnedAttributes().size());
            } else if (eachClass.getName().equals("getWorkListItemsType")) {
                assertEquals("Unexpected number of attributes", 5, eachClass
                        .getOwnedAttributes().size());
            } else {
                assertFalse("Unexpected Class Found", true);
            }
        }

    }

    /**
     * @param model
     */
    private void checkBOMElements3(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        assertEquals("Unexpected number of Packaged elements",
                2,
                packagedElements.size());

        // Collect all UML packaged elements
        for (PackageableElement packagedElement : packagedElements) {
            if (packagedElement instanceof Class) {
                allClazzes.add((Class) packagedElement);
            } else if (packagedElement instanceof Association) {
                allAssociations.add((Association) packagedElement);
            } else {
                assertFalse("Unexpected Element Found", true);
            }
        }

        assertEquals("Unexpected number of Classes", 1, allClazzes.size());

        assertEquals("Unexpected Class name", "ww", allClazzes.get(0).getName());

        assertEquals("Unexpected number of Attributes", 1, allClazzes.get(0)
                .getOwnedAttributes().size());

        assertEquals("Unexpected Attribute name", "managedObjectId", allClazzes
                .get(0).getOwnedAttributes().get(0).getName());

        assertEquals("Unexpected Attribute Type", "ManagedObjectID", allClazzes
                .get(0).getOwnedAttributes().get(0).getType().getName());

        assertEquals("Unexpected number of Associations",
                1,
                allAssociations.size());

        assertEquals("Unexpected Association member end",
                "managedObjectId",
                allAssociations.get(0).getMemberEnds().get(0).getName());
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#exportTest()
     * 
     * @throws Exception
     */
    @Override
    public void exportTest() throws Exception {
        super.exportTest();
    }

}
