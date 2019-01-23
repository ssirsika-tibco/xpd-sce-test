/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

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
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting the fix made in XPD-3939. Checks if Correct
 * Composition is generated when a Complex type[c] and its element[e] has same
 * name; and , the element[e] refers to a different Complex type [c2]
 * 
 * @author kthombar
 * @since 04-Jul-2013
 */
public class XSDBOM35_SimilarComplesTypeAndAttributeNameReferencingComplexTypeTransformationTest
        extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "XSDBOM35_SimilarComplesTypeAndAttributeNameReferencingComplexTypeTransformationTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE =
            "XSDBOM35_SimilarComplesTypeAndAttributeNameReferencingComplexTypeTransformationTest.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/wizard";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd/gold/builder";

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
        goldFileNames.add(MODEL_FILE);

        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {
        IFile modelFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
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
                importXSDtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputBOMPath);
        List<IStatus> errors = getErrors(result);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        // check resulting bom file is correct
        bomFile =
                ResourcesPlugin.getWorkspace().getRoot().getFile(outputBOMPath);
        assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

        WorkingCopy wc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(bomFile);
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
     * Main method for starting validation of Generated BOM elements
     * 
     * @param model
     * @throws Exception
     */
    private void checkBOMElements(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else if (packageableElement instanceof Class) {
                allClazzes.add((Class) packageableElement);
            }
        }

        // Inspect the Association

        // We expect there to be only one
        Assert.assertEquals("Unexpected number of Association[Composition].",
                1,
                allAssociations.size());

        // And its name to be "Composition0"
        Assert.assertEquals("Unexpected name for Composition.",
                "Composition0",
                allAssociations.get(0).getName());

        // Owened End composition should be customer
        Assert.assertEquals("Unexpected Composition Owned End.",
                "customer",
                allAssociations.get(0).getOwnedEnds().get(0).getName());

        // Inspect the Class
        // We expect there to be only 2
        Assert.assertEquals("Unexpected number of Classes.",
                2,
                allClazzes.size());

        // Check if the Classes are generated correctly
        for (Class eachClass : allClazzes) {
            if (eachClass.getName().equals("Customer")) {
                EList<Property> allAttributes = eachClass.getOwnedAttributes();
                assertEquals("Unexpected Number of Attributes",
                        2,
                        allAttributes.size());
                for (Property eachAtttribute : allAttributes) {
                    if (eachAtttribute.getName().equals("customer")) {

                        // Attribute customer should have Aggregation =
                        // composite
                        assertEquals("Unexpected type of Aggregation",
                                "composite",
                                eachAtttribute.getAggregation().getName());

                        // Attribute customer should have type = Person
                        assertEquals("Unexpected Attribute type",
                                "Person",
                                eachAtttribute.getType().getName());
                    }
                }

            } else if (eachClass.getName().equals("Person")) {
                assertEquals("Unexpected Number of Attributes", 2, eachClass
                        .getOwnedAttributes().size());

            } else {
                // If Class not found , test should fail
                assertFalse("Class Name did not match", true);
            }

        }
    }

}
