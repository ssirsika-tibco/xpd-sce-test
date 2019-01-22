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
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Junit test for protecting XPD-4323. Checks if the the round
 * trip[WSDL->BOM->XSD] is generated correctly.
 * 
 * @author rsawant
 * @since 27-Jun-2013
 */
public class WSDLBOM26_AnonymousComplexTypeTransformation_rt extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "com.gwl.MyClaimRepoCommonLookupRequest.bom";

    // "com.gwl.MyClaimRepoCommonLookupRequest.bom"

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/XPD4323";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE = "Test4323Wsdl.wsdl";

    protected String MODEL_XSD_FILE1 = "com.gwl.MyClaimIdType.xsd";

    protected String MODEL_XSD_FILE2 =
            "com.gwl.MyClaimRepoCommonLookupRequest.xsd";

    protected String MODEL_XSD_FILE3 = "com.gwl.MyRequestBaseType.xsd";

    protected String SUPPORT_FILE_CLAIMREPO =
            "MyClaimRepoCommonLookupRequest.xsd";

    protected String SUPPORT_FILE_ClAIMIDTYPE = "MyClaimIdType.xsd";

    protected String SUPPORT_FILE_REQUESTBASETYPE = "MyRequestBaseType.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder/XPD4323";

    private ArrayList<Class> allClazzes = new ArrayList<Class>();

    private ArrayList<Association> allAssociations =
            new ArrayList<Association>();

    private ArrayList<PrimitiveType> allPrimTypes =
            new ArrayList<PrimitiveType>();

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
        modelFileNames.add(SUPPORT_FILE_CLAIMREPO);
        modelFileNames.add(SUPPORT_FILE_ClAIMIDTYPE);
        modelFileNames.add(SUPPORT_FILE_REQUESTBASETYPE);
        goldFileNames.add(MODEL_XSD_FILE1);
        goldFileNames.add(MODEL_XSD_FILE2);
        goldFileNames.add(MODEL_XSD_FILE3);

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
        Issues issues = new IssuesImpl();
        IFile file =
                TransformUtil.getOutputWSDLFile(issues,
                        modelFiles.get(0),
                        outputSpecialFolder.getFolder());
        assertTrue("Cannot find newly exported BOM file", file.exists()); //$NON-NLS-1$

        WorkingCopy wc = XpdResourcesPlugin.getDefault().getWorkingCopy(file);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        bomFile = outputSpecialFolder.getFolder().getFile(TEST_FILE_NAME);
        wc = XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model = (Model) wc.getRootElement();

        // Validate BOM elements in this call.
        checkBOMElements(model);

        // Perform the export and validate the derived XSD. Make sure the Gold
        // files have been created and are in the correct location
        exportTest();
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

    /**
     * @param model
     * @throws Exception
     */
    private void checkBOMElements(Model model) throws Exception {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        // Collect all UML packaged elements
        for (PackageableElement pe : packagedElements) {
            if (pe instanceof Class) {
                allClazzes.add((Class) pe);
            } else if (pe instanceof Association) {
                allAssociations.add((Association) pe);
            } else if (pe instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) pe);
            }
        }

        checkPrimitiveTypes(packagedElements);
        checkClazzes(packagedElements);
    }

    /**
     * @param packagedElements
     * @throws Exception
     */
    private void checkClazzes(EList<PackageableElement> packagedElements)
            throws Exception {

        assertEquals("Number of Classes expected:", 3, allClazzes.size());

        Class classClaimRepoCommon = null;

        Property prop1 = null;
        Property prop2 = null;

        EList<Property> ownedAttributes = null;

        classClaimRepoCommon =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ClaimRepoCommonLookupRequestType");

        ownedAttributes = classClaimRepoCommon.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "claimId",
                        null);

        assertPropertyMultiplicity(prop1, 1, 1);
        Type type1 = prop1.getType();
        assertTrue("Property type should be a Class", type1 instanceof Class);

        Association assoc = prop1.getAssociation();
        assertNotNull("Association should exist", assoc);
        TransformUtil.assertIsComposition(assoc, classClaimRepoCommon);

        prop2 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "request",
                        null);
        assertPropertyMultiplicity(prop1, 1, 1);

        Type type2 = prop2.getType();
        assertTrue("Property type should be a Class", type2 instanceof Class);

    }

    /**
     * @param prop1
     * @param i
     * @param j
     */
    private void assertPropertyMultiplicity(Property prop, int lowerValue,
            int upperValue) {

        int actualLower = prop.getLower();
        int actualUpper = prop.getUpper();

        assertEquals("Check Property multiplicity lower value",
                actualLower,
                lowerValue);
        assertEquals("Check Property multiplicity upper value",
                actualUpper,
                upperValue);
    }

    /**
     * @param packagedElements
     */
    private void checkPrimitiveTypes(EList<PackageableElement> packagedElements) {
        // TODO Auto-generated method stub

    }

}
