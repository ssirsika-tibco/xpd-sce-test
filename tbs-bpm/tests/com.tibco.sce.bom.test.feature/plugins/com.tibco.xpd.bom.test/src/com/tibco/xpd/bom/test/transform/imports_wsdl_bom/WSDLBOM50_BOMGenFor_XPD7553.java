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
 * Test that takes MyAccountInq.wsdl and checks if all the classes are generated
 * correctly with their element types references generated correctly.
 * MyAccountInq.wsdl has duplicate complex types and top level elements in the
 * schema set.
 * 
 * @author bharge
 * @since 7 Apr 2014
 */
public class WSDLBOM50_BOMGenFor_XPD7553 extends TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM50_BOMGenFor_XPD7553";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM50_BOMGenFor_XPD7553";

    protected String MODEL_FILE = "MyAccountInq.wsdl";

    protected String GOLD_XSD_FILE1 = "com.vietinbank.casa._1.xsd";

    protected String GOLD_XSD_FILE2 = "com.vietinbank.tama._1.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM50_BOMGenFor_XPD7553/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM50_BOMGenFor_XPD7553/gold/builder";

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

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);

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
                if ("com.vietinbank.casa._1.bom".equals(generatedBOMFile
                        .getName())) {

                    checkBomElements1(model);
                    bomFile =
                            outputSpecialFolder.getFolder()
                                    .getFile(generatedBOMFile.getName());
                    assertTrue("Cannot find newly exported BOM file",
                            bomFile.exists());
                    exportTest();
                } else if ("com.vietinbank.tama._1.bom".equals(generatedBOMFile
                        .getName())) {

                    checkBomElements2(model);
                    bomFile =
                            outputSpecialFolder.getFolder()
                                    .getFile(generatedBOMFile.getName());
                    assertTrue("Cannot find newly exported BOM file",
                            bomFile.exists());
                    exportTest();
                }
            }
        }

        /*
         * check number of BOMs.
         */
        assertEquals(3, bomCount);
    }

    /**
     * Check for bom elements in com.vietinbank.tama._1.bom
     * 
     * @param model
     */
    private void checkBomElements2(Model model) {

        ArrayList<Class> allClazzes = new ArrayList<Class>();
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }
        assertEquals(2, allClazzes.size());
        for (Class cls : allClazzes) {

            if ("AccountBalanceInqReqType".equals(cls.getName())) {

                EList<Property> allAttributes = cls.getAllAttributes();
                for (Property property : allAttributes) {

                    if ("requestPayLoadAccountInq".equals(property.getName())) {

                        Type type = property.getType();
                        if (type instanceof Class) {

                            Class typeCls = (Class) type;
                            if ("RequestPayLoadAccountInqType".equals(typeCls
                                    .getName())) {

                                Model typesModel = typeCls.getModel();
                                if (typesModel == model) {

                                    assertEquals("Unexpected type reference",
                                            model,
                                            typesModel);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check for bom elements in com.vietinbank.casa._1.bom
     * 
     * @param model
     */
    private void checkBomElements1(Model model) {

        ArrayList<Class> allClazzes = new ArrayList<Class>();
        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
        }
        assertEquals(3, allClazzes.size());
        for (Class cls : allClazzes) {

            if ("AccountBalanceInqReqType".equals(cls.getName())) {

                EList<Property> allAttributes = cls.getAllAttributes();
                for (Property property : allAttributes) {

                    if ("requestPayLoadAccountInq".equals(property.getName())) {

                        Type type = property.getType();
                        if (type instanceof Class) {

                            Class typeCls = (Class) type;
                            if ("RequestPayLoadAccountInqType".equals(typeCls
                                    .getName())) {

                                Model typesModel = typeCls.getModel();
                                if (typesModel == model) {

                                    assertEquals("Unexpected type reference",
                                            model,
                                            typesModel);
                                }
                                EList<Property> allAttributes2 =
                                        typeCls.getAllAttributes();
                                for (Property property2 : allAttributes2) {

                                    if ("bankAccount".equals(property2
                                            .getName())) {

                                        Type type2 = property2.getType();
                                        if (type2 instanceof Class) {

                                            Class class1 = (Class) type2;
                                            if ("BankAccountType".equals(class1
                                                    .getName())) {

                                                EList<Property> allAttributes3 =
                                                        class1.getAllAttributes();
                                                assertEquals("Unexpected number of attributes",
                                                        2,
                                                        allAttributes3.size());
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
    }

}
