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
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Junit test for protecting XPD-2730. Checks if multiple complex types have
 * complex types contained in them[having same name], then in this case, a
 * separate class is generated for each containment[no names should clash].
 * 
 * @author kthombar
 * @since 21-Jul-2013
 */
public class XSDBOM40_GeneratedClassDuplicateNamesTransformation extends
        TransformationTestRoundtrip {

    private final String[] allClassNames = new String[] {
            "AssociatedClassType", "AssociatedClassType1",
            "AssociatedClassType2", "AssociatedClassType3",
            "AssociatedClassType4", "AssociatedClassType5",
            "AssociatedClassType6", "AssociatedClassType7",
            "AssociatedClassType8", "AssociatedClassType9", "Class1", "Class2",
            "Class3", "Class4", "Class5", "Class6", "Class7", "Class8",
            "Class9", "Class10" };

    protected String TEST_FILE_NAME =
            "XSDBOM40_GeneratedClassDuplicateNamesTransformation";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE =
            "XSDBOM40_GeneratedClassDuplicateNamesTransformation.xsd";

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

        assertEquals("Unexpected number of packaged elements.",
                30,
                packagedElements.size());

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Class) {
                allClazzes.add((Class) packageableElement);
            } else if (packageableElement instanceof Association) {
                allAssociations.add((Association) packageableElement);
            } else {
                assertFalse("Unexpected element found", true);
            }
        }

        // Inspect the Class
        checkClasses(allClazzes);

        // We expect 10 Associations
        assertEquals("Unexpected number of Associations",
                10,
                allAssociations.size());

    }

    private void checkClasses(ArrayList<Class> allClazzes) {

        // We expect 20 Classes
        Assert.assertEquals("Unexpected number of Classes.",
                20,
                allClazzes.size());

        // Check if the Class names are correct
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, allClassNames);
        for (Class eachClass : allClazzes) {
            if (!list.contains(eachClass.getName())) {
                assertFalse("Expected Class '" + eachClass.getName()
                        + "' not found", true);
            }
        }
    }

}
