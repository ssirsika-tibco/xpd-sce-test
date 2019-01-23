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
 * 
 * Junit test for protecting issue fixed in XPD-3623. Checks if XSD->BOM->XSD is
 * generated correctly when Simple content type is used in the input xsd. Also,
 * checks if the generated bom2xsd is correct
 * 
 * @author kthombar
 * @since 08-Jul-2013
 */
public class XSDBOM34_SupportSimpleContentSchemaTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "XSDBOM34_SupportSimpleContentSchemaTransformationTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE =
            "XSDBOM34_SupportSimpleContentSchemaTransformationTest.xsd";

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

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Class) {
                allClazzes.add((Class) packageableElement);
            } else {
                assertFalse("Unexpected element found", true);
            }
        }

        // Inspect the Class
        // We expect there to be 2
        Assert.assertEquals("Unexpected number of Class.", 2, allClazzes.size());

        for (Class eachClass : allClazzes) {
            if (eachClass.getName().equals("Description")) {

                // We have 1 attribute
                assertEquals("Unexpected number of attributes", 1, eachClass
                        .getOwnedAttributes().size());

                // Check is Attribute name is correct
                assertEquals("Unexpected Attribute name", "value", eachClass
                        .getOwnedAttributes().get(0).getName());

                // Check is attribute data type is correct
                assertEquals("Unexpected Attribute Type", "Text", eachClass
                        .getOwnedAttributes().get(0).getType().getName());

            } else if (eachClass.getName().equals("Text")) {

                Assert.assertEquals("Unexpected number of attributes.",
                        2,
                        eachClass.getOwnedAttributes().size());

                EList<Property> ownedAttr = eachClass.getOwnedAttributes();
                for (Property attr : ownedAttr) {
                    if (attr.getName().equals("isHtml")) {
                        // Check is attribute data type is correct
                        assertEquals("Unexpected Attribute Type",
                                "Object",
                                attr.getType().getName());
                    } else if (attr.getName().equals("value")) {
                        // Check is attribute data type is correct
                        assertEquals("Unexpected Attribute Type", "Text", attr
                                .getType().getName());
                    }
                }
            } else {
                // If none of the Classes match then Fail the test
                assertFalse("Class Name did not match", true);
            }
        }
    }

}
