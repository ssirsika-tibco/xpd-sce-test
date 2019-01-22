/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
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
 * If two wsdls have same schemas and one is imported after the other we merge
 * the schema of other wsdl into the existing bom model. While merging we remove
 * elements that are not indexed. In the wsdls used here there are element
 * declarations having simple type definitions. And we dont index them. So they
 * were getting removed during merge. Fixed this in the indexer by not looking
 * for primitive types while looking for removing elements/types. This junit
 * test captures this scenario of merge.
 * 
 * @author bharge
 * @since 25 Jul 2013
 */
public class WSDLBOM33_XPD5167_AssocToSimpTypeMissDuringMerge extends
        TransformationTestRoundtrip {

    protected String TEST_BOM_FILE_NAME =
            "com.vietinbank.serviceenvelope.commonheader.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/36XPD5167";

    protected String MODEL_FILE = "AccountHist.wsdl";

    protected String MODEL_FILE2 = "AccountInq.wsdl";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/36XPD5167/goldXsds";

    protected String GOLD_XSD_FILE1 =
            "com.vietinbank.serviceenvelope.commonheader.xsd";

    /**
     * @see com.tibco.xpd.bom.test.transform.common.TransformationTest#setUp()
     * 
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {

        modelFileNames = new ArrayList<String>();
        modelFiles = new ArrayList<IFile>();

        platformExampleFilesBase = PLATFORM_EXAMPLE_FILES_BASE;
        platformBuilderGoldFilesBase = PLATFORM_BUILDER_GOLD_FILES_BASE;

        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);

        goldFileNames.add(GOLD_XSD_FILE1);
        super.setUp();
    }

    @Override
    public void testTransformation() throws Exception {

        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_BOM_FILE_NAME));
        List<IStatus> errors = getErrors(statusArr);
        if (!errors.isEmpty()) {

            throw new Exception(errors.get(0).getMessage());
        }

        statusArr =
                importWSDLtoBOM(new File(modelFiles.get(1).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_BOM_FILE_NAME));
        // check resulting bom file is correct
        IResource[] members = outputSpecialFolder.getFolder().members();
        for (IResource mem : members) {

            if (mem instanceof IFile
                    && BOMResourcesPlugin.BOM_FILE_EXTENSION.equals(mem
                            .getFileExtension())) {

                if (TEST_BOM_FILE_NAME.equals(mem.getName())) {

                    bomFile = (IFile) mem;
                    assertTrue("Cannot find newly exported BOM file", bomFile.exists()); //$NON-NLS-1$

                    WorkingCopy wc =
                            XpdResourcesPlugin.getDefault()
                                    .getWorkingCopy(bomFile);
                    assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wc); //$NON-NLS-1$

                    assertTrue("Root element is null or not of type Model", wc.getRootElement() instanceof Model); //$NON-NLS-1$

                    Model model = (Model) wc.getRootElement();

                    checkBOMElements(model);

                }
            }
        }
        exportTest();
    }

    /**
     * @param model
     */
    private void checkBOMElements(Model model) {

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

        for (Class cls : allClasses) {

            assertEquals("Unexpected number of attributes", 2, cls
                    .getOwnedAttributes().size());
            if ("CommonDetailType".equals(cls.getName())) {

                for (Property prop : cls.getOwnedAttributes()) {

                    if ("businessDomain".equals(prop.getName())) {

                        assertEquals("Unexpected attribute type",
                                "BusinessDomainType",
                                prop.getType().getName());
                    } else if ("serviceVersion".equals(prop.getName())) {

                        assertEquals("Unexpected attribute type",
                                "ServiceVersionType",
                                prop.getType().getName());
                    }
                }
            } else {

                assertFalse("Unexpected Class Found.", true);
            }

        }

        assertEquals("Unexpected number of primitive types",
                3,
                allPrimTypes.size());
    }
}
