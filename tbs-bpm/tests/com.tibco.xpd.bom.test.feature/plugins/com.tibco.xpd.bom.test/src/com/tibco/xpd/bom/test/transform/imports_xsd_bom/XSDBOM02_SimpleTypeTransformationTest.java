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
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Checks if custom primitive types based on (xs:ID, xs:normalizedStirng) are
 * converted correctly. Additionally checks if facets (maxLenght, minLength) are
 * conveyed to BOM and back to XSD.
 * 
 * <p/>
 * TODO: JA Needs to be extended to check all possible standard schema primitive
 * types which would result in BOM text type. Also should test if other facets
 * are conveyed.
 * 
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM02_SimpleTypeTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE = "XSDBOM02_SimpleTypeTransformationTest.xsd";

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
        PrimitiveType mySimplePrimType =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "mySimple"); //$NON-NLS-1$

        PrimitiveType simplePrimType2 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "middleNameType"); //$NON-NLS-1$

        PrimitiveType simplePrimType3 =
                TransformUtil
                        .assertPackagedElementPrimitiveType(packagedElements,
                                "personalInfoType"); //$NON-NLS-1$

        TransformUtil.assertGeneralizationSize(mySimplePrimType, 1);
        TransformUtil.assertGeneralizationSize(simplePrimType2, 1);
        TransformUtil.assertGeneralizationSize(simplePrimType3, 1);

        Type idType =
                PrimitivesUtil.getStandardPrimitiveTypeByName(mySimplePrimType
                        .eResource().getResourceSet(),
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertGeneralization(mySimplePrimType
                .getGeneralizations().get(0), idType);

        TransformUtil.assertGeneralization(simplePrimType3.getGeneralizations()
                .get(0), idType);

        TransformUtil.assertGeneralization(simplePrimType2.getGeneralizations()
                .get(0), simplePrimType3);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                mySimplePrimType,
                "10",
                XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                simplePrimType2,
                "1",
                XsdStereotypeUtils.XSD_MIN_LENGTH_VALUE);
        TransformUtil.assertXSDBasedRestrictionValue(model,
                simplePrimType2,
                "4",
                XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                simplePrimType3,
                "1",
                XsdStereotypeUtils.XSD_MIN_LENGTH_VALUE);
        TransformUtil.assertXSDBasedRestrictionValue(model,
                simplePrimType3,
                "40",
                XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);

    }

}
