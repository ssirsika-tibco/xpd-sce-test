/*
 * Copyright (c) TIBCO Software Inc 2004, 2014. All rights reserved.
 */

package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Test that takes MyWsdl1.wsdl and MyWsdl2.wsdl both import the same schema
 * MySchema.xsd. Checks if all the classes are generated correctly with their
 * element types references generated correctly.
 * 
 * @author bharge
 * @since 7 Apr 2014
 */
public class WSDLBOM51_BOMGenFor_XPD7132 extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM51_BOMGenFor_XPD7132";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM51_BOMGenFor_XPD7132";

    protected String MODEL_FILE = "MyWsdl1.wsdl";

    protected String MODEL_FILE2 = "MyWsdl2.wsdl";

    protected String MODEL_XSD_FILE1 = "MySchema.xsd";

    protected String GOLD_XSD_FILE1 = "org.example.MySchema.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM51_BOMGenFor_XPD7132/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM51_BOMGenFor_XPD7132/gold/builder";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformWizardGoldFilesBase = PLATFORM_WIZARD_GOLD_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);
        modelFileNames.add(MODEL_XSD_FILE1);

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

        List<IStatus> statusArr2 =
                importWSDLtoBOM(new File(modelFiles.get(1).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));

        if (!statusArr2.isEmpty()) {

            statusArr.addAll(statusArr2);
        }

        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {

            throw new Exception(errors.get(0).getMessage());
        }

        int bomCount = 0;

        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource member : members) {
            if (member instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(member
                            .getFileExtension())) {

                bomCount++;

                IFile generatedBOMFile = (IFile) member;
                WorkingCopy wc =
                        XpdResourcesPlugin.getDefault()
                                .getWorkingCopy(generatedBOMFile);
                assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$
                assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$
                Model model = (Model) wc.getRootElement();
                if ("org.example.MySchema.bom".equals(generatedBOMFile
                        .getName())) {

                    checkBomElements(model);
                    bomFile =
                            outputSpecialFolder.getFolder()
                                    .getFile(generatedBOMFile.getName());
                    assertTrue("Cannot find newly exported BOM file",
                            bomFile.exists());
                }
            }
        }

        /*
         * check number of BOMs.
         */
        assertEquals(3, bomCount);
        /*
         * TODO: calling this for a round trip test to compare the generated xsd
         * is failing the test. Need to investigate!
         */
        // exportTest();
    }

    /**
     * Check for bom elements in com.vietinbank.tama._1.bom
     * 
     * @param model
     */
    private void checkBomElements(Model model) {

        ArrayList<Class> allClazzes = new ArrayList<Class>();
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }
        assertEquals(5, allClazzes.size());
        for (Class cls : allClazzes) {

            /*
             * Check the number of attributes and their types in ProductType
             * class. Expected: two attributes of type ProductOptionType and
             * ProductCharacteristicsType
             */
            if ("ProductType".equals(cls.getName())) {

                EList<Property> allAttributes = cls.getAllAttributes();
                assertEquals(2, allAttributes.size());
                for (Property property : allAttributes) {

                    if ("productOptionType".equals(property.getName())) {

                        Type type = property.getType();
                        if (type instanceof Class) {

                            Class class1 = (Class) type;
                            assertEquals("ProductOptionTypeType",
                                    class1.getName());
                            EList<Property> allAttributes2 =
                                    class1.getAllAttributes();
                            assertEquals(1, allAttributes2.size());
                            for (Property property2 : allAttributes2) {

                                if ("productBase".equals(property2.getName())) {

                                    Type type2 = property2.getType();
                                    if (type2 instanceof Class) {

                                        Class class2 = (Class) type2;
                                        assertEquals("ProductBaseType",
                                                class2.getName());
                                        assertEquals(1, class2
                                                .getAllAttributes().size());
                                    }
                                }
                            }
                        }
                    } else if ("productCharacteristics".equals(property
                            .getName())) {

                        Type type = property.getType();
                        if (type instanceof Class) {

                            Class class1 = (Class) type;
                            assertEquals("ProductCharacteristicsType",
                                    class1.getName());
                            EList<Property> allAttributes2 =
                                    class1.getAllAttributes();
                            assertEquals(1, allAttributes2.size());
                            for (Property property2 : allAttributes2) {

                                if ("productCharacteristic".equals(property2
                                        .getName())) {

                                    Type type2 = property2.getType();
                                    if (type2 instanceof Class) {

                                        Class class2 = (Class) type2;
                                        assertEquals("ProductCharacteristicType",
                                                class2.getName());
                                        assertEquals(1, class2
                                                .getAllAttributes().size());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
