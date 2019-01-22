/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.test.transform.imports_wsdl_bom;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.bom.xsdtransform.XsdStereotypeUtils;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 16 April 2009</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class WSDLBOM14_IncludesQualifiedAndUnqualifiedTest extends
        TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE =
            "WSDLBOM14_IncludesQualifiedAndUnqualified.wsdl";

    protected String MODEL_FILE2 =
            "WSDLBOM14_IncludesQualifiedAndUnqualified.xsd";

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
        modelFileNames.add(MODEL_FILE);
        modelFileNames.add(MODEL_FILE2);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        assertEquals(0, getErrors(new ArrayList(statusArr)).size());

        IFile wsdlFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
        Collection<String> outputBOMNames =
                WSDLTransformUtil.getOutputBOMNames(wsdlFile);

        IFile xsdBOMfile =
                outputSpecialFolder.getFolder().getFile(outputBOMNames
                        .toArray(new String[] {})[1]);
        WorkingCopy xsdBOMwc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(xsdBOMfile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", xsdBOMwc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", xsdBOMwc.getRootElement() instanceof Model); //$NON-NLS-1$
        Model xsdBOMModel = (Model) xsdBOMwc.getRootElement();

        IFile wsdlBOMfile =
                outputSpecialFolder.getFolder().getFile(outputBOMNames
                        .toArray(new String[] {})[0]);
        WorkingCopy wsdlBOMwc =
                XpdResourcesPlugin.getDefault().getWorkingCopy(wsdlBOMfile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wsdlBOMwc); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wsdlBOMwc.getRootElement() instanceof Model); //$NON-NLS-1$
        Model wsdlBOMModel = (Model) wsdlBOMwc.getRootElement();

        EList<PackageableElement> packagedElements =
                xsdBOMModel.getPackagedElements();
        assertEquals("Number of packaged elements in model", 12, packagedElements.size()); //$NON-NLS-1$

        Class airlinePreference =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AirlinePreference");

        Class frequentFlier =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "FrequentFlier");

        Class correlationIdType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "correlationIdType");

        Class password =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "password");

        Class requestInfoType =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "requestInfoType");

        Class requestName =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "requestName");

        Class userName =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "userName");

        Property seating =
                TransformUtil.assertAttributeInClass(airlinePreference
                        .getAllAttributes(),
                        "seating",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                seating,
                "unqualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property mealType =
                TransformUtil.assertAttributeInClass(airlinePreference
                        .getAllAttributes(),
                        "mealType",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                mealType,
                "unqualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property specialRequest =
                TransformUtil.assertAttributeInClass(airlinePreference
                        .getAllAttributes(),
                        "specialRequest",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                specialRequest,
                "unqualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property frequentFlierAtr =
                TransformUtil.assertAttributeInClass(airlinePreference
                        .getAllAttributes(), "frequentFlier", "FrequentFlier");

        Property airline =
                TransformUtil.assertAttributeInClass(frequentFlier
                        .getAllAttributes(),
                        "airline",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                airline,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property freqFlierNumber =
                TransformUtil.assertAttributeInClass(frequentFlier
                        .getAllAttributes(),
                        "freqFlierNumber",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                freqFlierNumber,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property correlationId =
                TransformUtil.assertAttributeInClass(correlationIdType
                        .getAllAttributes(),
                        "correlationId",
                        PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                correlationId,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property passwordProp =
                TransformUtil.assertAttributeInClass(password
                        .getAllAttributes(),
                        "password",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                passwordProp,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property reqNameProp =
                TransformUtil.assertAttributeInClass(requestInfoType
                        .getAllAttributes(), "requestName", null);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                reqNameProp,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property passwordProp2 =
                TransformUtil.assertAttributeInClass(requestInfoType
                        .getAllAttributes(), "password", null);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                passwordProp2,
                "unqualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property userNameProp =
                TransformUtil.assertAttributeInClass(requestInfoType
                        .getAllAttributes(), "userName", null);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                userNameProp,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property correlationIdProp =
                TransformUtil.assertAttributeInClass(requestInfoType
                        .getAllAttributes(), "correlationId", null);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                correlationIdProp,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property reqNameProp2 =
                TransformUtil.assertAttributeInClass(requestName
                        .getAllAttributes(),
                        "requestName",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                reqNameProp2,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);

        Property userNameProp2 =
                TransformUtil.assertAttributeInClass(userName
                        .getAllAttributes(),
                        "userName",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertXSDBasedPropertyValue(xsdBOMModel,
                userNameProp2,
                "qualified",
                XsdStereotypeUtils.XSD_PROPERTY_FORM);
    }
}
