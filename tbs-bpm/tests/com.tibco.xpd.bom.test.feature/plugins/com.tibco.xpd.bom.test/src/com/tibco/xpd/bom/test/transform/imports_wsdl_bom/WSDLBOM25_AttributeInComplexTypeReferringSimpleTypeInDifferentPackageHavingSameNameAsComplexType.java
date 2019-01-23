/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting issue fixed in XPD-5138. Resolves the issue where
 * XSD-->BOM was unable to deal with Complex Type having attribute that refers
 * to a Simple Type defined in other package with same name as the Complex Type.
 * 
 * @author sajain
 * @since Jul 17, 2013
 */
public class WSDLBOM25_AttributeInComplexTypeReferringSimpleTypeInDifferentPackageHavingSameNameAsComplexType
        extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "de.telekom.diagss.oss.v1.mycomplex.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE = "WsdlFor5138Junit.wsdl";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder/XPD-5138";

    protected String GOLD_XSD_FILE1 = "org.example.WsdlFor5138Junit.xsd";

    protected String GOLD_XSD_FILE2 = "de.telekom.diagss.oss.v1.mycomplex.xsd";

    protected String GOLD_XSD_FILE3 = "de.telekom.diagss.oss.v1.mysimple.xsd";

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
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE3);
        goldFileNames.add(GOLD_XSD_FILE2);

        super.setUp();
    }

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

        IResource[] members = outputSpecialFolder.getFolder().members();
        int flagToDistinguishMainBOMAndReferencedBOM = 0;
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {
                flagToDistinguishMainBOMAndReferencedBOM++;
                IFile generatedBOMFile = (IFile) member;

                // check resulting bom file is correct
                bomFile = generatedBOMFile;
                wc = XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

                Model model = (Model) wc.getRootElement();

                if (flagToDistinguishMainBOMAndReferencedBOM == 1) {
                    // check BOM elements for Main BOM (having complex types)
                    checkBOMElementsForMainXSD(model);
                } else if (flagToDistinguishMainBOMAndReferencedBOM == 2) {
                    // check BOM elements for Referenced BOM (having simple
                    // types)
                    checkBOMElementsForReferenceXSD(model);
                }
            }
        }

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
    private void checkBOMElementsForMainXSD(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        ArrayList<org.eclipse.uml2.uml.Class> allClazzes =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<Property> ownedAttributes = null;

        Property prop1 = null;

        Property prop2 = null;

        Property prop3 = null;

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                allClazzes.add((org.eclipse.uml2.uml.Class) packageableElement);
            }
        }

        // check for 12 packaged elements (7 classes + 5 associations)
        assertEquals("Number of packaged elements in model", 12, packagedElements.size()); //$NON-NLS-1$

        // check for 7 classes
        assertEquals("Number of classes in model", 7, allClazzes.size()); //$NON-NLS-1$

        // check names of classes and their respective attributes
        org.eclipse.uml2.uml.Class classAuftraggeberType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AuftraggeberType"); //$NON-NLS-1$
        ownedAttributes = classAuftraggeberType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "auftraggebernummer",
                        "AuftraggebernummerType");
        prop2 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "leistungsnummer",
                        "LeistungsnummerType");

        org.eclipse.uml2.uml.Class classMeldungspositionOhneAttributeType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MeldungspositionOhneAttributeType"); //$NON-NLS-1$
        ownedAttributes =
                classMeldungspositionOhneAttributeType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "meldungscode",
                        "MeldungscodeType");
        prop2 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "meldungstext",
                        "MeldungstextType");

        org.eclipse.uml2.uml.Class classMeldungspositionenType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "meldungspositionenType"); //$NON-NLS-1$
        ownedAttributes = classMeldungspositionenType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "position",
                        "MeldungspositionOhneAttributeType");

        org.eclipse.uml2.uml.Class classMessageTEQType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MessageTEQType"); //$NON-NLS-1$
        ownedAttributes = classMessageTEQType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                3,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "externeAuftragsnummer",
                        "ExterneAuftragsnummerType");
        prop2 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "auftraggebernummer",
                        "AuftraggebernummerType");
        prop3 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "meldungspositionen",
                        "meldungspositionenType");

        org.eclipse.uml2.uml.Class classGeschaeftsfallType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "GeschaeftsfallType"); //$NON-NLS-1$
        ownedAttributes = classGeschaeftsfallType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                0,
                ownedAttributes.size());

        org.eclipse.uml2.uml.Class classVersionType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "VersionType"); //$NON-NLS-1$
        ownedAttributes = classVersionType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "majorVersion",
                        "VersionType");
        prop2 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "minorVersion",
                        "VersionType");

        org.eclipse.uml2.uml.Class classServiceControlType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "ServiceControlType"); //$NON-NLS-1$
        ownedAttributes = classServiceControlType.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                2,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "zeitstempelVersand",
                        "Time");
        prop2 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "version",
                        "VersionType");

        // check for 5 associations
        assertEquals("Number of associations in model", 5, allAssociations.size()); //$NON-NLS-1$

        // check names and ends of respective compositions
        Assert.assertEquals("Unexpected name for Composition.",
                "Composition0",
                allAssociations.get(0).getName());
        Assert.assertEquals("Unexpected Composition Member End.",
                "position",
                allAssociations.get(0).getMemberEnds().get(0).getName());

        Assert.assertEquals("Unexpected name for Composition.",
                "Composition1",
                allAssociations.get(1).getName());
        Assert.assertEquals("Unexpected Composition Member End.",
                "meldungspositionen",
                allAssociations.get(1).getMemberEnds().get(0).getName());

        Assert.assertEquals("Unexpected name for Composition.",
                "Composition2",
                allAssociations.get(2).getName());
        Assert.assertEquals("Unexpected Composition Member End.",
                "majorVersion",
                allAssociations.get(2).getMemberEnds().get(0).getName());

        Assert.assertEquals("Unexpected name for Composition.",
                "Composition3",
                allAssociations.get(3).getName());
        Assert.assertEquals("Unexpected Composition Member End.",
                "minorVersion",
                allAssociations.get(3).getMemberEnds().get(0).getName());

        Assert.assertEquals("Unexpected name for Composition.",
                "Composition4",
                allAssociations.get(4).getName());
        Assert.assertEquals("Unexpected Composition Member End.",
                "version",
                allAssociations.get(4).getMemberEnds().get(0).getName());
    }

    /**
     * @param model
     * @throws Exception
     */
    private void checkBOMElementsForReferenceXSD(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        ArrayList<org.eclipse.uml2.uml.Class> allClazzes =
                new ArrayList<org.eclipse.uml2.uml.Class>();

        ArrayList<PrimitiveType> allPrimitiveTypes =
                new ArrayList<PrimitiveType>();

        EList<Property> ownedAttributes = null;

        Property prop1 = null;

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof PrimitiveType) {
                allPrimitiveTypes.add((PrimitiveType) packageableElement);
            }
        }

        // check for 6 packaged elements
        assertEquals("Number of packaged elements in model", 6, packagedElements.size()); //$NON-NLS-1$

        // check for 6 primitive types
        assertEquals("Number of primitive types in model", 6, allPrimitiveTypes.size()); //$NON-NLS-1$

        // check names of primitive types
        org.eclipse.uml2.uml.PrimitiveType ptAuftraggebernummerType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "AuftraggebernummerType"); //$NON-NLS-1$

        org.eclipse.uml2.uml.PrimitiveType ptExterneAuftragsnummerType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "ExterneAuftragsnummerType"); //$NON-NLS-1$

        org.eclipse.uml2.uml.PrimitiveType ptLeistungsnummerType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "LeistungsnummerType"); //$NON-NLS-1$

        org.eclipse.uml2.uml.PrimitiveType ptMeldungscodeType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MeldungscodeType"); //$NON-NLS-1$

        org.eclipse.uml2.uml.PrimitiveType ptMeldungstextType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "MeldungstextType"); //$NON-NLS-1$

        org.eclipse.uml2.uml.PrimitiveType ptVersionType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "VersionType"); //$NON-NLS-1$
    }
}
