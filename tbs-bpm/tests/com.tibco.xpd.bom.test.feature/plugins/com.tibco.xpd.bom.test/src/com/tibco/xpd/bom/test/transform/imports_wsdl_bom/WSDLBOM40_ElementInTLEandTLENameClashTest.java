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

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.test.transform.common.TransformationTestRoundtrip;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * The problem was the name clash between TLE having same name as an element in
 * another TLE (i.e. If an element name in a TopLevelElement clashes with the
 * anonymous complex type). For instance consider a TLE with name "e1" for which
 * a complex type "e1Type" gets generated. Another TLE say "TopElem1" if has an
 * element with name "e1Type" then this was a problem.
 * 
 * When "TopElem1" was being parsed all its elements are parsed to check if any
 * of its elements are anonymous complex types. This check was done by going
 * through all the packages to get all anonymous classes and the name was being
 * compared with element name. So in the above kind of scenario when "e1Type" in
 * "TopElem1" is parsed, it thinks "e1Type" is a anonymous class because it gets
 * confused with the other TLE "e1Type".
 * 
 * An element can be an anonymous complex type if it does not have the type set.
 * So modified the condition in the oaw code to parse the anonymous class only
 * if the element has no type set
 * 
 * @author bharge
 * @since 25 Mar 2014
 */
public class WSDLBOM40_ElementInTLEandTLENameClashTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME =
            "WSDLBOM40_ElementInTLEandTLENameClashTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM40_ElementInTLEandTLENameClashTest";

    // The name of the wsdl that is being tested
    protected String MODEL_FILE = "WSDL1.wsdl";

    protected String MODEL_FILE1 = "Schema1.xsd";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM40_ElementInTLEandTLENameClashTest/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM40_ElementInTLEandTLENameClashTest/gold/builder";

    protected String GOLD_XSD_FILE1 = "org.example.Schema1.xsd";

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
     * @throws Exception
     */
    private void checkBomElements(Model model) throws Exception {

        ArrayList<Class> allClasses = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof Class) {

                allClasses.add((Class) packageableElement);
            }
        }
        assertEquals("Unexpected number of classes", 2, 2);
        for (Class cls : allClasses) {

            if ("e1Type".equals(cls.getName())) {

                assertEquals("Unexpected number of attributes", 1, 1);
                EList<Property> ownedAttributes = cls.getOwnedAttributes();

                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "abc",
                        "Text");
            } else if ("TopElem1Type".equals(cls.getName())) {

                assertEquals("Unexpected number of attributes", 1, 1);
                EList<Property> ownedAttributes = cls.getOwnedAttributes();

                assertEquals("Unexpected number of attributes", 1, 1);
                TransformUtil.assertAttributeInClass(ownedAttributes,
                        "e1Type",
                        "Text");
            }
        }
    }

}
