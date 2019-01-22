/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * XPD-6038: If a schema has TLE with same name (TLE +"Type" after bom
 * generation) as that of a complex type and if the TLE is extending some base
 * complex type, then only one class for TLE+"Type" was getting generated.
 * 
 * For instance if TLE workResponse extends Success and workResponseType complex
 * type also extends Success then only two classes were getting generated
 * instead of three.
 * 
 * @author bharge
 * @since 25 Mar 2014
 */
public class WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest";

    // The name of the wsdl that is being tested
    protected String MODEL_FILE = "SimpleWsdl.wsdl";

    protected String MODEL_FILE1 = "SchemaWithTLEAndCompTypeExtSameBase.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM41_TLEAndCompTypeSameNameExtAnotherClassTest/gold/builder";

    protected String GOLD_XSD_FILE1 =
            "org.example.SchemaWithTLEAndCompTypeExtSameBase.xsd";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE1);

        goldFileNames.add(GOLD_XSD_FILE1);
        super.setUp();
    }

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#testTransformation()
     * 
     * @throws Exception
     */
    @Override
    public void testTransformation() throws Exception {

        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {
            throw new Exception(errors.get(0).getMessage());
        }
        // check resulting bom file is correct
        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {

            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$
                Model model = (Model) wc.getRootElement();
                if ("org.example.Schema1.bom"
                        .equals(generatedBOMFile.getName())) {

                    checkBomElements(model);
                }
            }
        }
        exportTest();
    }

    /**
     * @param model
     */
    private void checkBomElements(Model model) {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        ArrayList<Class> allClazzes = new ArrayList<Class>();

        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }

        /* We expect 4 Classes */
        Assert.assertEquals("Unexpected number of Classes.",
                3,
                allClazzes.size());
        for (Class cls : allClazzes) {

            if ("workResponseType1".equals(cls.getName())) {

                Classifier general = cls.getGeneral("Success");
                if (null == general) {

                    assertEquals("Expected Base Class", general, null);
                }
            }
            if ("workResponseType".equals(cls.getName())) {

                Classifier general = cls.getGeneral("Success");
                if (null == general) {

                    assertEquals("Expected Base Class", general, null);
                }
            }
        }
    }

}
