/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_xsd_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xsd.XSDSchema;
import org.openarchitectureware.xsd.builder.OawXSDResource;

import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Tests simple types with enumeration restrictions. Also checking that
 * duplicating names will be converted correctly for anonymous simple types.
 * 
 * TODO: Would be good to example where anonymous simple type (with enums) is
 * for attribute.
 * <p>
 * <i>Created: 06 Feb 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM03_SimpleTypeEnumerationTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM03_SimpleTypeEnumerationTransformationTest.xsd";

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
        // For this test we should have a single Class named concept3
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 7, packagedElements.size()); //$NON-NLS-1$

        // check classes
        Enumeration mySimpleEnum =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "mySimpleEnum"); //$NON-NLS-1$       

        ArrayList<Enumeration> enumerations = new ArrayList<Enumeration>();
        Enumeration newEnumType =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "typeType"); //$NON-NLS-1$   
        enumerations.add(newEnumType);
        Enumeration newEnumType2 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "typeType1", enumerations); //$NON-NLS-1$
        enumerations.add(newEnumType2);
        Enumeration newEnumType3 =
                TransformUtil
                        .assertPackagedElementEnumeration(packagedElements,
                                "typeType2", enumerations); //$NON-NLS-1$

        // check attributes
        TransformUtil.assertEnumLiteralCount(mySimpleEnum, 2);

        TransformUtil.assertEnumLiteralInEnum(mySimpleEnum.getOwnedLiterals(),
                "ENUM1");
        TransformUtil.assertEnumLiteralInEnum(mySimpleEnum.getOwnedLiterals(),
                "ENUM2");

        TransformUtil.assertEnumLiteralCount(newEnumType, 2);

        TransformUtil.assertEnumLiteralInEnum(newEnumType.getOwnedLiterals(),
                "ACTIVATION");
        TransformUtil.assertEnumLiteralInEnum(newEnumType.getOwnedLiterals(),
                "REFILL");

        TransformUtil.assertEnumLiteralCount(newEnumType2, 2);

        TransformUtil.assertEnumLiteralInEnum(newEnumType2.getOwnedLiterals(),
                "ACTIVATION2");
        TransformUtil.assertEnumLiteralInEnum(newEnumType2.getOwnedLiterals(),
                "REFILL2");

        TransformUtil.assertEnumLiteralCount(newEnumType3, 2);

        TransformUtil.assertEnumLiteralInEnum(newEnumType3.getOwnedLiterals(),
                "ACTIVATION3");
        TransformUtil.assertEnumLiteralInEnum(newEnumType3.getOwnedLiterals(),
                "ACTIVATION3");

        org.eclipse.uml2.uml.Class mySimpleCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "couponDetailsType"); //$NON-NLS-1$

        Assert.assertEquals(mySimpleCls.getAllAttributes().get(0).getType(),
                newEnumType);

        org.eclipse.uml2.uml.Class mySimpleCls2 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "couponDetailsType2"); //$NON-NLS-1$

        Assert.assertEquals(mySimpleCls2.getAllAttributes().get(0).getType(),
                newEnumType2);

        org.eclipse.uml2.uml.Class mySimpleCls3 =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "couponDetailsType3"); //$NON-NLS-1$

        Assert.assertEquals(mySimpleCls3.getAllAttributes().get(0).getType(),
                newEnumType3);

        Stereotype stereotype = null;
        Iterator<Stereotype> stereoIter =
                mySimpleCls.getAllAttributes().get(0).getAppliedStereotypes()
                        .iterator();
        while (stereoIter.hasNext()) {
            stereotype = stereoIter.next();
            if (stereotype.getName()
                    .equals(XsdStereotypeUtils.XSD_BASED_PROPERTY)) {
                break;
            }
        }
        Assert.assertNotNull(stereotype);
        Object objValue =
                mySimpleCls
                        .getAllAttributes()
                        .get(0)
                        .getValue(stereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_ENUM_LITERALS);
        Assert.assertNotNull(objValue);
        Assert.assertEquals("activation, refill", objValue); //$NON-NLS-1$    
    }

}
