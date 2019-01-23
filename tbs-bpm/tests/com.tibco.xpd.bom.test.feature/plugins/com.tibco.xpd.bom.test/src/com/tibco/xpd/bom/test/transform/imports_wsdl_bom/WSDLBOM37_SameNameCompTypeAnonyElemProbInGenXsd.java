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
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * XPD-4700: Schema1 has complex type 'StatusType' Schema2 has anonymous element
 * 'Status' (which gets a complex type 'StatusType' generated) in 'ProviderType'
 * complex type . Schema3 references 'StatusType' from Schema1 and
 * 'ProviderType' from Schema2. Generated xsd for Schema2 has problems. After
 * XPD-4700 fix this problem should not occur, so adding this junit to cover
 * this scenario
 * 
 * @author bharge
 * @since 10 Mar 2014
 */
public class WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd";

    // The name of the wsdl that is being tested
    protected String MODEL_FILE = "ReproduceWsdl.wsdl";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM37_SameNameCompTypeAnonyElemProbInGenXsd/gold/builder";

    protected String GOLD_XSD_FILE1 = "com.gwl.Provider.xsd";

    protected String GOLD_XSD_FILE2 = "com.gwl.Status.xsd";

    protected String GOLD_XSD_FILE3 =
            "com.gwl.ReceiveMatchProviderRateSheetRequest.xsd";

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

        goldFileNames.add(GOLD_XSD_FILE1);
        goldFileNames.add(GOLD_XSD_FILE2);
        goldFileNames.add(GOLD_XSD_FILE3);
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
        Model providerBomModel = null;
        Model statusBomModel = null;
        Model requestBomModel = null;
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

                if ("com.gwl.Provider.bom".equals(generatedBOMFile.getName())) {

                    providerBomModel = model;
                } else if ("com.gwl.Status.bom".equals(generatedBOMFile
                        .getName())) {

                    statusBomModel = model;
                } else if ("com.gwl.ReceiveMatchProviderRateSheetRequest.bom"
                        .equals(generatedBOMFile.getName())) {

                    requestBomModel = model;
                }
            }
        }
        checkProviderBOMElements(providerBomModel);
        checkStatusBOMElements(statusBomModel);
        checkRequestBOMElements(requestBomModel,
                providerBomModel,
                statusBomModel);
        exportTest();
    }

    /**
     * @param requestBomModel
     * @param statusBomModel
     * @param providerBomModel
     */
    private void checkRequestBOMElements(Model requestBomModel,
            Model providerBomModel, Model statusBomModel) {

        if (null != requestBomModel && null != providerBomModel
                && null != statusBomModel) {

            ArrayList<Class> allClasses = new ArrayList<Class>();

            EList<PackageableElement> packagedElements =
                    requestBomModel.getPackagedElements();
            assertEquals("Number of packaged elements in model", 4, packagedElements.size()); //$NON-NLS-1$

            for (PackageableElement packageableElement : packagedElements) {

                if (packageableElement instanceof Class) {

                    allClasses.add((Class) packageableElement);
                }
            }
            Class providerType = getProviderType(providerBomModel);
            Class statusType = getStatusType(statusBomModel);
            for (Class cls : allClasses) {

                if ("ReceiveMatchProviderRateSheetRequestType".equals(cls
                        .getName())) {

                    EList<Property> attributes = cls.getAttributes();
                    assertEquals("Unexpected number of attributes",
                            3,
                            attributes.size());
                    for (Property property : attributes) {

                        if ("status".equals(property.getName())) {

                            assertEquals("Unexpected Status Type",
                                    property.getType().getQualifiedName(),
                                    statusType.getQualifiedName());
                        }
                        if ("provider".equals(property.getName())) {

                            assertEquals("Unexpected Provider Type",
                                    property.getType().getQualifiedName(),
                                    providerType.getQualifiedName());
                        }
                    }
                }
            }
        } else {

            if (null == requestBomModel) {

                assertNull("Request BOM Model is null", requestBomModel);
            }
            if (null == statusBomModel) {

                assertNull("Status BOM Model is null", statusBomModel);
            }
            if (null == providerBomModel) {

                assertNull("Provider BOM Model is null", providerBomModel);
            }
        }
    }

    /**
     * @param model
     */
    private void checkStatusBOMElements(Model model) {

        ArrayList<Class> allClasses = new ArrayList<Class>();

        ArrayList<PrimitiveType> allPrimTypes = new ArrayList<PrimitiveType>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 2, packagedElements.size()); //$NON-NLS-1$

        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClasses.add((Class) packageableElement);
            } else if (packageableElement instanceof PrimitiveType) {

                allPrimTypes.add((PrimitiveType) packageableElement);
            }
        }
        for (Class cls : allClasses) {

            if ("StatusType".equals(cls.getName())) {

                EList<Property> attributes = cls.getAttributes();
                assertEquals("Unexpected number of attributes",
                        1,
                        attributes.size());
            }
        }
    }

    /**
     * @param model
     */
    private void checkProviderBOMElements(Model model) {

        ArrayList<Class> allClasses = new ArrayList<Class>();

        ArrayList<PrimitiveType> allPrimTypes = new ArrayList<PrimitiveType>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        assertEquals("Number of packaged elements in model", 4, packagedElements.size()); //$NON-NLS-1$

        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClasses.add((Class) packageableElement);
            } else if (packageableElement instanceof PrimitiveType) {

                allPrimTypes.add((PrimitiveType) packageableElement);
            }
        }

        Class compositionTypeClass = null;
        for (Class cls : allClasses) {

            if ("StatusType".equals(cls.getName())) {

                EList<Property> attributes = cls.getAttributes();
                assertEquals("Unexpected number of attributes",
                        3,
                        attributes.size());
                compositionTypeClass = cls;
            }
        }
        for (Class cls : allClasses) {

            if ("ProviderType".equals(cls.getName())) {

                EList<Property> attributes = cls.getAttributes();
                assertEquals("Unexpected number of attributes",
                        1,
                        attributes.size());
                if (null != compositionTypeClass) {

                    for (Property property : attributes) {

                        assertEquals("Unexpected Type",
                                property.getType().getQualifiedName(),
                                compositionTypeClass.getQualifiedName());
                    }
                }
            }
        }
    }

    private Class getProviderType(Model model) {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                Class cls = (Class) packageableElement;
                if ("ProviderType".equals(cls.getName())) {

                    return cls;
                }
            }
        }
        return null;
    }

    private Class getStatusType(Model model) {

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                Class cls = (Class) packageableElement;
                if ("StatusType".equals(cls.getName())) {

                    return cls;
                }
            }
        }
        return null;
    }
}
