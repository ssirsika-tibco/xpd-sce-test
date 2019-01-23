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
import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
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
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 22 Mar 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class XSDBOM04_PropertyAttributesTransformationTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    protected String MODEL_FILE =
            "XSDBOM04_PropertyAttributesTransformationTest.xsd";

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
        // check that all attributes have the type steretype value filled
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
        Class class1 =
                TransformUtil.assertPackagedElementClass(model
                        .getPackagedElements(), "Class1");

        PrimitiveType myprim =
                TransformUtil.assertPackagedElementPrimitiveType(model
                        .getPackagedElements(), "myprim");

        PrimitiveType myprimWR =
                TransformUtil.assertPackagedElementPrimitiveType(model
                        .getPackagedElements(), "myprimWR");

        Property attribute1 =
                TransformUtil.assertAttributeInClass(class1.getAllAttributes(),
                        "attr2",
                        null);
        TransformUtil.assertPropertyMultiplicity(attribute1, 0, 1);

        Property attribute2 =
                TransformUtil.assertAttributeInClass(class1.getAllAttributes(),
                        "myClass2",
                        null);
        TransformUtil.assertPropertyMultiplicity(attribute2, 0, 1);

        Property elemWithMinInc =
                TransformUtil.assertAttributeInClass(class1.getAllAttributes(),
                        "elemWithMinInc",
                        null);
        TransformUtil.assertPropertyMultiplicity(elemWithMinInc, 1, 1);

        Property elemWithMinIncSimple =
                TransformUtil.assertAttributeInClass(class1.getAllAttributes(),
                        "elemWithMinIncSimple",
                        null);
        TransformUtil.assertPropertyMultiplicity(elemWithMinIncSimple, 1, 1);

        Property elemWithMinIncSimpleOveride =
                TransformUtil.assertAttributeInClass(class1.getAllAttributes(),
                        "elemWithMinIncSimpleOveride",
                        null);
        TransformUtil.assertPropertyMultiplicity(elemWithMinIncSimpleOveride,
                1,
                1);

        Object facetPropertyValue =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) elemWithMinInc
                                .getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                elemWithMinInc);
        Assert.assertEquals("1", facetPropertyValue);

        facetPropertyValue =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) elemWithMinIncSimple
                                .getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                elemWithMinIncSimple);
        Assert.assertEquals("1", facetPropertyValue);

        facetPropertyValue =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) elemWithMinIncSimpleOveride
                                .getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER,
                                elemWithMinIncSimpleOveride);
        Assert.assertEquals("2", facetPropertyValue);

        facetPropertyValue =
                PrimitivesUtil
                        .getFacetPropertyValue((PrimitiveType) elemWithMinIncSimpleOveride
                                .getType(),
                                PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER,
                                elemWithMinIncSimpleOveride);
        Assert.assertEquals("10", facetPropertyValue);

        facetPropertyValue =
                PrimitivesUtil.getFacetPropertyValue(myprimWR,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_LOWER);
        Assert.assertEquals("5", facetPropertyValue);

        facetPropertyValue =
                PrimitivesUtil.getFacetPropertyValue(myprimWR,
                        PrimitivesUtil.BOM_PRIMITIVE_FACET_INTEGER_UPPER);
        Assert.assertEquals("15", facetPropertyValue);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                elemWithMinIncSimpleOveride,
                "2",
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                elemWithMinIncSimpleOveride,
                "10",
                XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                myprim,
                "1",
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                myprimWR,
                "5",
                XsdStereotypeUtils.XSD_MIN_INCLUSIVE_VALUE);

        TransformUtil.assertXSDBasedRestrictionValue(model,
                myprimWR,
                "15",
                XsdStereotypeUtils.XSD_MAX_INCLUSIVE_VALUE);

        // Check XSD stereotype values
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                "attr2",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                "string",
                XsdStereotypeUtils.XSD_PROPERTY_TYPE);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                "unqualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                "defval",
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_ID);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_FIXED);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_REF);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                "optional",
                XsdStereotypeUtils.XSD_PROPERTY_USE);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                false,
                XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_EXTENSION);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                false,
                XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_RESTRICTION);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_ENUM_LITERALS);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute1,
                "false",
                XsdStereotypeUtils.XSD_PROPERTY_NILLABLE);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                "myClass2",
                XsdStereotypeUtils.XSD_PROPERTY_NAME);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                "string",
                XsdStereotypeUtils.XSD_PROPERTY_TYPE);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_DEFAULT);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_ID);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                "false",
                XsdStereotypeUtils.XSD_PROPERTY_FIXED);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_REF);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_USE);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                false,
                XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_EXTENSION);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                false,
                XsdStereotypeUtils.XSD_PROPERTY_IS_SIMPLE_CONTENT_RESTRICTION);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                null,
                XsdStereotypeUtils.XSD_PROPERTY_ENUM_LITERALS);
        TransformUtil.assertXSDBasedPropertyValue(model,
                attribute2,
                "true",
                XsdStereotypeUtils.XSD_PROPERTY_NILLABLE);

    }

}
