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
 * 
 * 
 * @author rsawant
 * @since 03-Jul-2013
 */
public class XSDBOM31_ComplexTypeWithMixedConstruct extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "TLEWithComplexType.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/xsd";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE = "XSDBOM31_ComplexTypeWithMixedConstruct.xsd";

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

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#testTransformation()
     * 
     * @throws Exception
     */
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
     * @param model
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    private void checkBOMElements(Model model) throws Exception {

        ArrayList<Class> allClazzes = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        Class myTLE1TypeClass = null;
        Class myTLE3TypeClass = null;
        Class myTLE4TypeClass = null;
        Class myTLE2TypeClass = null;
        Class baseClass = null;

        for (PackageableElement packageableElement : packagedElements) {
            if (packageableElement instanceof Class) {
                allClazzes.add((Class) packageableElement);
            }
        }

        Assert.assertEquals("Unexpected number of Classes.",
                5,
                allClazzes.size());
        baseClass =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "Base");
        assertNotNull("Could not find class", baseClass);

        // Check if mixed stereo type is present and set to 'false' for
        // MyTLE1Type
        myTLE1TypeClass =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyTLE1Type");
        assertNotNull("Could not find class", myTLE1TypeClass);

        Stereotype xsdBasedModelStereotype =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationStereotype(myTLE1TypeClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
        assertNotNull("Missing StereoType", xsdBasedModelStereotype);
        Object notationProperty =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationProperty(myTLE1TypeClass,
                                xsdBasedModelStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_MIXED);
        assertNotNull("\'Mixed\' property missing", notationProperty);
        assertTrue("\'Mixed\' property should be set to \'false\'",
                String.valueOf(notationProperty).equalsIgnoreCase(Boolean.FALSE
                        .toString()));

        // Check if mixed stereo type is present and set to 'true' for
        // MyTLE2Type
        myTLE2TypeClass =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyTLE2Type");
        assertNotNull("Could not find class", myTLE2TypeClass);

        xsdBasedModelStereotype =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationStereotype(myTLE2TypeClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
        assertNotNull("Missing StereoType", xsdBasedModelStereotype);
        notationProperty =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationProperty(myTLE2TypeClass,
                                xsdBasedModelStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_MIXED);
        assertNotNull("\'Mixed\' property missing", notationProperty);
        assertTrue("\'Mixed\' property should be set to \'true\'",
                String.valueOf(notationProperty).equalsIgnoreCase(Boolean.TRUE
                        .toString()));

        // Check if required mixed stereo type is applied for MyTLE3Type
        myTLE3TypeClass =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyTLE3Type");
        assertNotNull("Could not find class", myTLE3TypeClass);

        xsdBasedModelStereotype =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationStereotype(myTLE3TypeClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
        assertNotNull("Missing StereoType", xsdBasedModelStereotype);
        notationProperty =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationProperty(myTLE3TypeClass,
                                xsdBasedModelStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_MIXED);
        assertNotNull("\'Mixed\' missing", notationProperty);
        assertTrue("\'Mixed\' property should be \'true\'",
                String.valueOf(notationProperty).equalsIgnoreCase(Boolean.TRUE
                        .toString()));

        // Check if required mixed stereo type is applied for MyTLE4Type
        myTLE4TypeClass =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "MyTLE4Type");
        assertNotNull("Could not find class", myTLE4TypeClass);

        xsdBasedModelStereotype =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationStereotype(myTLE4TypeClass,
                                XsdStereotypeUtils.XSD_BASED_CLASS);
        assertNotNull("Missing StereoType", xsdBasedModelStereotype);
        notationProperty =
                com.tibco.xpd.bom.xsdtransform.exports.template.TransformHelper
                        .getXSDNotationProperty(myTLE4TypeClass,
                                xsdBasedModelStereotype,
                                XsdStereotypeUtils.XSD_PROPERTY_MIXED);
        assertNotNull("\'Mixed\' missing", notationProperty);
        assertTrue("\'Mixed\' property should be \'true\'",
                String.valueOf(notationProperty).equalsIgnoreCase(Boolean.TRUE
                        .toString()));

        // Now check generalizations
        assertTrue("There should be only one generaliztion", myTLE3TypeClass
                .getGenerals().size() == 1);
        TransformUtil.assertGeneralization(myTLE3TypeClass.getGeneralizations()
                .get(0), baseClass);

        assertTrue("There should be only one generalization", myTLE4TypeClass
                .getGenerals().size() == 1);
        TransformUtil.assertGeneralization(myTLE4TypeClass.getGeneralizations()
                .get(0), baseClass);

    }

}
