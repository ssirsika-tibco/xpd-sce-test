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
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PackageableElement;

import com.tibco.xpd.bom.test.transform.common.TransformationTest;
import com.tibco.xpd.bom.test.transform.util.TransformUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.bom.wsdltransform.api.WSDLTransformUtil;
import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * Its a very base transformation test it only test for now if transformation
 * will run without exceptions. You can later test manually the result in the
 * junit-workspace.
 * <p>
 * <i>Created: 15 July 2010</i>
 * </p>
 * 
 * @author glewis
 */
@SuppressWarnings("nls")
public class WSDLBOM17_MixedImportsAndIncludesTest extends TransformationTest {

    protected String TEST_FILE_NAME = "myTest.bom";

    protected String PLATFORM_EXAMPLE_FILES_BASE =
            "platform:/plugin/com.tibco.xpd.bom.test/test-resources/wsdl";

    protected String MODEL_FILE = "WSDLBOM17_MixedImportsAndIncludes.wsdl";

    protected String MODEL_FILE2 = "WSDLBOM17_MixedImportsAndIncludes.xsd";

    protected String MODEL_FILE3 = "WSDLBOM17_MixedImportsAndIncludes2.xsd";

    protected String MODEL_FILE4 = "WSDLBOM17_MixedImportsAndIncludes3.xsd";

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
        modelFileNames.add(MODEL_FILE3);
        modelFileNames.add(MODEL_FILE4);

        super.setUp();
    }

    public void testTransformation() throws Exception {
        List<IStatus> statusArr =
                importWSDLtoBOM(new File(modelFiles.get(0).getLocationURI()),
                        outputSpecialFolder.getFolder().getFullPath()
                                .append(TEST_FILE_NAME));
        assertEquals(0, getErrors(new ArrayList(statusArr)).size());

        TestUtil.waitForJobs();

        IFile wsdlFile = outputSpecialFolder.getFolder().getFile(MODEL_FILE);
        Collection<String> outputBOMNames =
                WSDLTransformUtil.getOutputBOMNames(wsdlFile);

        IFile xsdCommonBOMfile =
                outputSpecialFolder.getFolder().getFile(outputBOMNames
                        .toArray(new String[] {})[0]);
        WorkingCopy xsdCommonWC =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(xsdCommonBOMfile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", xsdCommonWC); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", xsdCommonWC.getRootElement() instanceof Model); //$NON-NLS-1$
        Model xsdCommonBOMModel = (Model) xsdCommonWC.getRootElement();

        IFile wsdlBOMfile =
                outputSpecialFolder.getFolder().getFile(outputBOMNames
                        .toArray(new String[] {})[1]);
        WorkingCopy wsdlWC =
                XpdResourcesPlugin.getDefault().getWorkingCopy(wsdlBOMfile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", wsdlWC); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", wsdlWC.getRootElement() instanceof Model); //$NON-NLS-1$
        Model wsdlBOMModel = (Model) wsdlWC.getRootElement();

        IFile xsdPOCommonBOMfile =
                outputSpecialFolder.getFolder().getFile(outputBOMNames
                        .toArray(new String[] {})[2]);
        WorkingCopy xsdPOCommonWC =
                XpdResourcesPlugin.getDefault()
                        .getWorkingCopy(xsdPOCommonBOMfile);
        assertNotNull("Cannot create WorkingCopy for newly exported BOM file", xsdPOCommonWC); //$NON-NLS-1$
        assertTrue("Root element is null or not of type Model", xsdPOCommonWC.getRootElement() instanceof Model); //$NON-NLS-1$
        Model xsdPOCommonBOMModel = (Model) xsdPOCommonWC.getRootElement();

        EList<PackageableElement> packagedElements =
                xsdCommonBOMModel.getPackagedElements();
        assertEquals("Number of packaged elements in model",
                3,
                packagedElements.size()); //$NON-NLS-1$

        EList<PackageableElement> packagedElements2 =
                wsdlBOMModel.getPackagedElements();
        assertEquals("Number of packaged elements in model",
                1,
                packagedElements2.size()); //$NON-NLS-1$

        EList<PackageableElement> packagedElements3 =
                xsdPOCommonBOMModel.getPackagedElements();
        assertEquals("Number of packaged elements in model",
                9,
                packagedElements3.size()); //$NON-NLS-1$

        org.eclipse.uml2.uml.Class wsincludeCls =
                TransformUtil.assertPackagedElementClass(packagedElements2,
                        "wsinclude");

        org.eclipse.uml2.uml.Class addressTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements,
                        "AddressType");

        org.eclipse.uml2.uml.Class lineItemsTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements3,
                        "LineItemsType");

        org.eclipse.uml2.uml.Class lineItemTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements3,
                        "LineItemType");

        org.eclipse.uml2.uml.Class partyTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements3,
                        "PartyType");

        org.eclipse.uml2.uml.Class purchaseOrderTypeCls =
                TransformUtil.assertPackagedElementClass(packagedElements3,
                        "PurchaseOrderType");

        TransformUtil.assertOperationInClass(wsincludeCls.getAllOperations(),
                "GetPO",
                purchaseOrderTypeCls);

        TransformUtil.assertAttributeInClass(addressTypeCls.getAllAttributes(),
                "city",
                PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);

        TransformUtil.assertAttributeInClass(lineItemsTypeCls
                .getAllAttributes(), "lineItem", lineItemTypeCls.getName());

        TransformUtil
                .assertAttributeInClass(lineItemTypeCls.getAllAttributes(),
                        "prodiuctID",
                        "ProductIDType");
        TransformUtil
                .assertAttributeInClass(lineItemTypeCls.getAllAttributes(),
                        "productName",
                        PrimitivesUtil.BOM_PRIMITIVE_TEXT_NAME);
        TransformUtil.assertAttributeInClass(partyTypeCls.getAllAttributes(),
                "id",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertAttributeInClass(partyTypeCls.getAllAttributes(),
                "address",
                addressTypeCls.getName());
        TransformUtil.assertAttributeInClass(purchaseOrderTypeCls
                .getAllAttributes(),
                "version",
                PrimitivesUtil.BOM_PRIMITIVE_DECIMAL_NAME);
        TransformUtil.assertAttributeInClass(purchaseOrderTypeCls
                .getAllAttributes(),
                "poNumber",
                PrimitivesUtil.BOM_PRIMITIVE_INTEGER_NAME);
        TransformUtil.assertAttributeInClass(purchaseOrderTypeCls
                .getAllAttributes(),
                "poDate",
                PrimitivesUtil.BOM_PRIMITIVE_DATE_NAME);
        TransformUtil.assertAttributeInClass(purchaseOrderTypeCls
                .getAllAttributes(), "billtoParty", partyTypeCls.getName());
        TransformUtil.assertAttributeInClass(purchaseOrderTypeCls
                .getAllAttributes(), "shipToType", partyTypeCls.getName());
        TransformUtil
                .assertAttributeInClass(purchaseOrderTypeCls.getAllAttributes(),
                        "lineItemType",
                        lineItemsTypeCls.getName());
    }
}
