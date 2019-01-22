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
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * 
 * 
 * @author bharge
 * @since 13 Mar 2014
 */
public class WSDLBOM39_Base64BinarySimpleTypeTest extends
        TransformationTestRoundtrip {

    protected String TEST_FILE_NAME = "WSDLBOM39_Base64BinarySimpleTypeTest";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM39_Base64BinarySimpleTypeTest";

    // The name of the wsdl that is being tested
    protected String MODEL_FILE = "TestServiceProvider.wsdl";

    protected String PLATFORM_WIZARD_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM39_Base64BinarySimpleTypeTest/gold/builder";

    protected String PLATFORM_BUILDER_GOLD_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl/WSDLBOM39_Base64BinarySimpleTypeTest/gold/builder";

    protected String GOLD_XSD_FILE1 = "org.example.TestServiceProvider.xsd";

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
                if ("org.example.TestServiceProvider.bom"
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

        ArrayList<PrimitiveType> allPrimitiveTypes =
                new ArrayList<PrimitiveType>();
        ArrayList<Class> allClasses = new ArrayList<Class>();

        EList<PackageableElement> packagedElements =
                model.getPackagedElements();
        for (PackageableElement packageableElement : packagedElements) {

            if (packageableElement instanceof PrimitiveType) {

                allPrimitiveTypes.add((PrimitiveType) packageableElement);
            } else if (packageableElement instanceof Class) {

                allClasses.add((Class) packageableElement);
            }
        }
        PrimitiveType base64BinaryType = null;
        for (PrimitiveType primType : allPrimitiveTypes) {

            if ("MyBase64Binary".equals(primType.getName())) {

                TransformUtil.assertXSDBasedRestrictionValue(model,
                        primType,
                        "12",
                        XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);
                base64BinaryType = primType;
            } else if ("MyString".equals(primType.getName())) {

                TransformUtil.assertXSDBasedRestrictionValue(model,
                        primType,
                        "50",
                        XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);
            } else if ("MyHexBinary".equals(primType.getName())) {

                TransformUtil.assertXSDBasedRestrictionValue(model,
                        primType,
                        "100",
                        XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);
            }
        }
        for (Class cls : allClasses) {

            if ("NewOperationResponseType".equals(cls.getName())) {

                EList<Property> allAttributes = cls.getAllAttributes();
                for (Property property : allAttributes) {

                    if ("out".equals(property.getName())) {
                        if (null != base64BinaryType) {

                            assertEquals("Unexpected Primitive type",
                                    base64BinaryType.getName(),
                                    property.getType().getName());
                            assertEquals("Unexpected Primitive type",
                                    base64BinaryType.getQualifiedName(),
                                    property.getType().getQualifiedName());
                        }
                        TransformUtil.assertXSDBasedRestrictionValue(model,
                                (PrimitiveType) property.getType(),
                                "12",
                                XsdStereotypeUtils.XSD_MAX_LENGTH_VALUE);
                    }
                }
            }
        }
    }

}
