/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.issues.IssuesImpl;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * Junit test for protecting issue fixed in XPD-4356. Resolves the issue where
 * Complex type with same name as anonymous-type-top-level-element in other
 * schema was being incorrectly tagged as anonymous type (later causing
 * StrinOutOfBoundsException while performing BOM2XSD transformation)
 * 
 * @author sajain
 * @since Jul 4, 2013
 */
public class WSDLBOM23_ComplexTypeAndElementHavingSameNameTransformationTest
        extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "org.example.WSDLBOM23_mainXSD.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/migrated";

    protected String MODEL_FILE = "WSDLBOM23.wsdl";

    protected String DEPENDENCY_FILE1 = "WSDLBOM23_mainXSD.xsd";

    protected String DEPENDENCY_FILE2 = "WSDLBOM23_referenceXSD.xsd";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder/XPD-4356";

    protected String GOLD_XSD_FILE1 = "org.example.WSDLBOM23_mainXSD.xsd";

    protected String GOLD_XSD_FILE2 = "org.example.WSDLBOM23_referenceXSD.xsd";

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
        modelFileNames.add(DEPENDENCY_FILE1);
        modelFileNames.add(DEPENDENCY_FILE2);

        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE1);

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

        /*
         * Verify WSDLBOM23_mainXSD.xsd file
         */
        IFile modelFile =
                outputSpecialFolder.getFolder().getFile(DEPENDENCY_FILE1);
        OawXSDResource resource =
                new OawXSDResource(URI.createURI(modelFile.getFullPath()
                        .toPortableString()));
        resource.load(Collections.EMPTY_MAP);
        XSDSchema schema = resource.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), schema.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        IPath outputBOMPath =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);
        List<IStatus> result =
                importXSDtoBOM(new File(modelFiles.get(1).getLocationURI()),
                        outputBOMPath);
        List<IStatus> errorsInXSD = getErrors(result);
        if (!errorsInXSD.isEmpty()) {
            throw new Exception(errorsInXSD.get(0).getMessage());
        }

        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        // check resulting bom file is correct
        WorkingCopy wc1 =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        Model model1 = (Model) wc1.getRootElement();

        // check BOM elements for WSDLBOM23_mainXSD.xsd
        checkBOMElementsForMainXSD(model1);

        /*
         * Verify WSDLBOM23_referenceXSD.xsd file
         */
        modelFile = outputSpecialFolder.getFolder().getFile(DEPENDENCY_FILE2);
        resource =
                new OawXSDResource(URI.createURI(modelFile.getFullPath()
                        .toPortableString()));
        resource.load(Collections.EMPTY_MAP);
        schema = resource.getSchema();
        TEST_FILE_NAME =
                XSDUtil.getJavaPackageNameFromNamespaceURI(modelFile
                        .getProject(), schema.getTargetNamespace())
                        + ".bom"; //$NON-NLS-1$

        outputBOMPath =
                outputSpecialFolder.getFolder().getFullPath()
                        .append(TEST_FILE_NAME);
        result =
                importXSDtoBOM(new File(modelFiles.get(2).getLocationURI()),
                        outputBOMPath);
        errorsInXSD = getErrors(result);
        if (!errorsInXSD.isEmpty()) {
            throw new Exception(errorsInXSD.get(0).getMessage());
        }

        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        // check resulting bom file is correct
        wc1 = XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

        model1 = (Model) wc1.getRootElement();

        // check BOM elements for WSDLBOM23_referenceXSD.xsd
        checkBOMElementsForReferenceXSD(model1);

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

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                allClazzes.add((org.eclipse.uml2.uml.Class) packageableElement);
            }
        }

        // check for 1 class
        assertEquals("Number of classes in model", 1, allClazzes.size()); //$NON-NLS-1$

        // check name of that class
        org.eclipse.uml2.uml.Class classNote =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "NoteType"); //$NON-NLS-1$

        ownedAttributes = classNote.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "ele1",
                        "Note");

        // check for one association
        assertEquals("Number of associations in model", 1, allAssociations.size()); //$NON-NLS-1$

        // Its name to be "Composition0"
        Assert.assertEquals("Unexpected name for Composition.",
                "Composition0",
                allAssociations.get(0).getName());

        // Member End composition should be customer
        Assert.assertEquals("Unexpected Composition Owned End.",
                "ele1",
                allAssociations.get(0).getMemberEnds().get(0).getName());
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

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<Property> ownedAttributes = null;

        Property prop1 = null;

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof org.eclipse.uml2.uml.Class) {
                allClazzes.add((org.eclipse.uml2.uml.Class) packageableElement);
            }
        }

        // check for 1 class
        assertEquals("Number of classes in model", 1, allClazzes.size()); //$NON-NLS-1$

        // check name of that class
        org.eclipse.uml2.uml.Class classNote =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "Note"); //$NON-NLS-1$

        ownedAttributes = classNote.getOwnedAttributes();
        assertEquals("Check number of owned Attributes:",
                1,
                ownedAttributes.size());
        prop1 =
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "el1",
                        "Text");

        // there shouldn't be any associations
        assertEquals("Number of associations in model", 0, allAssociations.size()); //$NON-NLS-1$
    }

}
