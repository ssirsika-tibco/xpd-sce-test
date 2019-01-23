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
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * XPD-4041: Test that checks for bom generation of same named enumeration in
 * two schemas
 * 
 * @author bharge
 * @since 28 Feb 2014
 */
public class WSDLBOM35_SameNameEnumerationInTwoSchemasTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "WSDLBOM35_SameNameEnumerationInTwoSchemasTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM35_SameNameEnumInTwoSchemas";

    // The name of the XML Schema that is being tested
    protected String MODEL_FILE = "MySimpWsdlForSR1-DWL75D.wsdl";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM35_SameNameEnumInTwoSchemas/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM35_SameNameEnumInTwoSchemas/gold/builder";

    protected String GOLD_XSD_FILE1 = "myschema.xsd";

    protected String GOLD_XSD_FILE2 = "myschema2.xsd";

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

                if ("myschema.bom".equals(generatedBOMFile.getName())) {

                    checkBOMElements(model);

                } else if ("myschema2.bom".equals(generatedBOMFile.getName())) {

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

        ArrayList<Class> allClazzes = new ArrayList<Class>();
        ArrayList<Enumeration> allEnums = new ArrayList<Enumeration>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClazzes.add((Class) packageableElement);
            }
            if (packageableElement instanceof Enumeration) {

                allEnums.add((Enumeration) packageableElement);
            }
        }

        /* We expect 2 Classes */
        Assert.assertEquals("Unexpected number of Classes.",
                2,
                allClazzes.size());
        /* We expect 1 Enumeration */
        Assert.assertEquals("Unexpected number of Enumerations.",
                1,
                allEnums.size());
        for (Class class1 : allClazzes) {

            if ("MyComplex".equals(class1.getName())) {

                EList<Property> ownedAttributes = class1.getOwnedAttributes();
                for (Property property : ownedAttributes) {

                    if (property.getType() instanceof Enumeration) {

                        assertEquals("Unexpected Enumeration type", allEnums
                                .get(0).getName(), property.getType().getName());
                    }
                }
            }
        }
    }
}
