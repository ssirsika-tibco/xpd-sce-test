/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM09_ComplexTypeCompositionElementTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM09_ComplexTypeCompositionElementTransformationTest.xsd";

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

        checkBOMElements(model);

        exportTest();
    }

    private void checkBOMElements(Model model) throws Exception {
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 3, packagedElements.size()); //$NON-NLS-1$

        // check classes
        Class conceptCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "concept2");
        Class fileCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "file2");

        EList<Property> conceptClsOwnedAttributes =
                conceptCls.getOwnedAttributes();
        assertEquals("Check class contains expected number of attributes",
                3,
                conceptClsOwnedAttributes.size());
        TransformUtil.assertAttributeInClass(conceptClsOwnedAttributes,
                "id",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertAttributeInClass(conceptClsOwnedAttributes,
                "name",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        // The next attribute is related to the Composition Association
        Property prop =
                TransformUtil.assertAttributeInClass(conceptClsOwnedAttributes,
                        "file",
                        null);

        Type type = prop.getType();
        assertEquals("Property type should be a Class", fileCls, type);
        Association assoc = prop.getAssociation();
        assertNotNull("Association should exist", assoc);
        TransformUtil.assertIsComposition(assoc, fileCls);
        TransformUtil.assertIsComposition(conceptCls.getAssociations().get(0),
                fileCls);

        // Now check the integrity of the Composition Association
        // Now check the Association has the correct end members i.e. class1 and
        // class2
        EList<Property> memberEnds = assoc.getMemberEnds();
        Property end0 = memberEnds.get(0);
        Property end1 = memberEnds.get(1);
        assertTrue("End Members should not be the same object",
                end0.getType() != end1.getType());
        assertTrue("Association source:", (end0.getType() == conceptCls)
                || (end0.getType() == fileCls));
        assertTrue("Association target:", (end1.getType() == conceptCls)
                || (end1.getType() == fileCls));

        // And that it has an owned end of Concept2 (because Association is a
        // Composition)
        EList<Property> ownedEnds = assoc.getOwnedEnds();
        assertEquals("Size of owned ends should be 1", 1, ownedEnds.size());
        assertEquals("Owned end should be a Property of type Class", ownedEnds
                .get(0).getType(), conceptCls);

    }

}
