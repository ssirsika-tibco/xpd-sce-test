/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
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
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 24 Mar 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM21_UniqueIDForDuplicateNamesTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM21_UniqueIDForDuplicateNamesTransformationTest.xsd";

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

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 13, packagedElements.size()); //$NON-NLS-1$

        // check integer parts

        TransformUtil.assertPackagedElementClass(packagedElements,
                "branch1Type");

        TransformUtil
                .assertPackagedElementClass(packagedElements, "BranchType");

        TransformUtil.assertPackagedElementClass(packagedElements, "Tree");

        TransformUtil
                .assertPackagedElementClass(packagedElements, "apple1Type");

        TransformUtil.assertPackagedElementClass(packagedElements, "AppleType");

        TransformUtil.assertPackagedElementClass(packagedElements,
                "tranch1Type");

        TransformUtil
                .assertPackagedElementClass(packagedElements, "TranchType");

        Class class2 = TransformUtil.getClass(packagedElements, "branch1Type");

        Class class3 = TransformUtil.getClass(packagedElements, "BranchType");

        Class class4 = TransformUtil.getClass(packagedElements, "Tree");

        Class class5 = TransformUtil.getClass(packagedElements, "apple1Type");

        Class class6 = TransformUtil.getClass(packagedElements, "AppleType");

        Class class7 = TransformUtil.getClass(packagedElements, "tranch1Type");

        Class class8 = TransformUtil.getClass(packagedElements, "TranchType");

        String id1 = ((XMLResource) model.eResource()).getID(class2);
        Assert.assertEquals("treesbranchType", id1);

        String id2 = ((XMLResource) model.eResource()).getID(class3);
        Assert.assertEquals("treesBranchType", id2);

        String id3 = ((XMLResource) model.eResource()).getID(class4);
        Assert.assertEquals("treesTree", id3);

        String id4 = ((XMLResource) model.eResource()).getID(class5);
        Assert.assertEquals("treesappleType", id4);

        String id5 = ((XMLResource) model.eResource()).getID(class6);
        Assert.assertEquals("treesAppleType", id5);

        String id6 = ((XMLResource) model.eResource()).getID(class7);
        Assert.assertEquals("treestranchType", id6);

        String id7 = ((XMLResource) model.eResource()).getID(class8);
        Assert.assertEquals("treesTranchType", id7);

        Property property1 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "branch",
                        null);

        Property property2 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "branch1",
                        null);

        Property property3 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "tranch",
                        null);

        Property property4 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "tranch1",
                        null);

        Property property5 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "apple",
                        null);

        Property property6 =
                TransformUtil.assertAttributeInClass(class4.getAllAttributes(),
                        "apple1",
                        null);

        String id14 = ((XMLResource) model.eResource()).getID(property1);
        Assert.assertEquals("TreeBranchType", id14);

        String id15 = ((XMLResource) model.eResource()).getID(property2);
        Assert.assertEquals("TreebranchType", id15);

        String id16 = ((XMLResource) model.eResource()).getID(property3);
        Assert.assertEquals("TreeTranchType", id16);

        String id17 = ((XMLResource) model.eResource()).getID(property4);
        Assert.assertEquals("TreetranchType", id17);

        String id18 = ((XMLResource) model.eResource()).getID(property5);
        Assert.assertEquals("TreeAppleType", id18);

        String id19 = ((XMLResource) model.eResource()).getID(property6);
        Assert.assertEquals("TreeappleType", id19);

        exportTest();
    }

}
