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
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting XPD-2569. Checks if Ecore name is supported.
 * 
 * @author kthombar
 * @since 18-Jul-2013
 */
public class XSDBOM39_SupportEcoreNameForEvementsTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "XSDBOM39_SupportEcoreNameForEvementsTransformationTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE =
            "XSDBOM39_SupportEcoreNameForEvementsTransformationTest.xsd";

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

        ArrayList<PrimitiveType> allPrimTypes = new ArrayList<PrimitiveType>();

        ArrayList<Enumeration> allEnumerations = new ArrayList<Enumeration>();

        ArrayList<Association> allAssociations = new ArrayList<Association>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof PrimitiveType) {
                allPrimTypes.add((PrimitiveType) packageableElement);
            } else if (packageableElement instanceof Class) {
                allClazzes.add((Class) packageableElement);
            } else if (packageableElement instanceof Enumeration) {
                allEnumerations.add((Enumeration) packageableElement);
            } else if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else {
                assertFalse("Unexpected element  found", true);
            }
        }

        // Inspect the PrimitiveType

        // We expect there to be 7
        Assert.assertEquals("Unexpected number of PrimitiveTypes.",
                7,
                allPrimTypes.size());

        // Inspect the Enumerations

        // We expect there to be 4
        Assert.assertEquals("Unexpected number of Enumerations.",
                4,
                allEnumerations.size());

        // Inspect the Classes , their attributes and if the attribute types are
        // correct
        checkClassesAndAttributes(allClazzes);

        checkAssociations(allAssociations);

    }

    void checkAssociations(ArrayList<Association> allAssociations) {
        // Inspect the Association

        // We expect there to be only one
        Assert.assertEquals("Unexpected number of Association[Composition].",
                1,
                allAssociations.size());

        for (Association eachAssociation : allAssociations) {
            if (eachAssociation.getName().equals("Composition0")) {
                assertEquals("Unexpected Member End",
                        "elementInCTWithAnonCT",
                        eachAssociation.getMemberEnds().get(0).getName());
            } else {
                assertFalse("Expected Association not found", true);
            }

        }

    }

    void checkClassesAndAttributes(ArrayList<Class> allClazzes) {

        // Inspect the Class
        // We expect there to be 3
        Assert.assertEquals("Unexpected number of Class.", 3, allClazzes.size());

        for (Class eachClass : allClazzes) {
            if (eachClass.getName().equals("TLEComplexType")) {
                Assert.assertEquals("Unexpected number of Attributes.",
                        5,
                        eachClass.getOwnedAttributes().size());
                EList<Property> ownedAttributes =
                        eachClass.getOwnedAttributes();

                for (Property prop : ownedAttributes) {
                    if (prop.getName().equals("TLESimpleInt")) {
                        Assert.assertEquals("Unexpected Type.",
                                "TLESimpleIntType",
                                prop.getType().getName());
                    }
                }

            } else if (eachClass.getName().equals("ElementInCTWithAnonCTType")) {
                Assert.assertEquals("Unexpected number of Attributes.",
                        1,
                        eachClass.getOwnedAttributes().size());

            } else if (eachClass.getName().equals("TopLevelComplexType")) {
                Assert.assertEquals("Unexpected number of Attributes.",
                        7,
                        eachClass.getOwnedAttributes().size());
                EList<Property> ownedAttributes =
                        eachClass.getOwnedAttributes();

                for (Property prop : ownedAttributes) {
                    if (prop.getName().equals("tlSimpleAttr")) {
                        Assert.assertEquals("Unexpected Type.",
                                "TopLevelNonEnumSimple",
                                prop.getType().getName());
                    } else if (prop.getName().equals("ElementInCTWithAnonEnum")) {
                        Assert.assertEquals("Unexpected Type.",
                                "ElementInCTWithAnonEnumType",
                                prop.getType().getName());
                    }
                }

            } else {
                // expected classes weren't found
                assertFalse("Expected Class not found", true);
            }
        }

    }

}
